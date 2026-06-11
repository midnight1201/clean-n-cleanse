package net.midnight.cncleanse.content.sponge;

import net.midnight.cncleanse.content.sponge.item.SpongeItemColor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;

import java.util.ArrayDeque;
import java.util.HashSet;

import static net.minecraft.world.level.block.Block.dropResources;

public final class SpongeAbsorption {

    // --- constants ---
    public static final int BLOCK_RADIUS = 6;
    public static final int BLOCK_LIMIT = 65;

    public static final int ITEMS_PER_BLOCK = 4;
    public static final int ITEM_LIMIT = BLOCK_LIMIT / ITEMS_PER_BLOCK;
    private static final double ITEM_RANGE = BLOCK_RADIUS / (double) ITEMS_PER_BLOCK;
    private static final double ITEM_RANGE_SQR = ITEM_RANGE * ITEM_RANGE;

    private SpongeAbsorption() {}

    // --- public API --

    public static boolean absorbFromBlock(Level level, BlockPos spongePos, BlockState spongeState) {
        return absorbBlock(level, spongePos, spongeState) > 1;
    }
    public static boolean wouldAbsorbFromItem(Level level, BlockPos origin, SpongeItemColor color) {
        return tryAbsorbFromItem(level, origin, color, true);
    }
    public static boolean tryAbsorbFromItem(Level level, BlockPos origin, SpongeItemColor color) {
        return tryAbsorbFromItem(level, origin, color, false);
    }
    public static boolean isAquaticPlant(BlockState state) {
        return state.is(Blocks.KELP)
                || state.is(Blocks.KELP_PLANT)
                || state.is(Blocks.SEAGRASS)
                || state.is(Blocks.TALL_SEAGRASS);
    }

    // --- block absorption ---

    private static int absorbBlock(Level level, BlockPos origin, BlockState hydrationReference) {
        return BlockPos.breadthFirstTraversal(
                origin,
                BLOCK_RADIUS,
                BLOCK_LIMIT,
                SpongeAbsorption::enqueueNeighbors,
                target -> tryRemoveWater(level, origin, hydrationReference, target, true, false)
        );
    }

    // --- item absorption ---

    private static boolean tryAbsorbFromItem(Level level, BlockPos origin, SpongeItemColor color, boolean simulate) {
        if (isAquaticPlant(level.getBlockState(origin))) {
            return false;
        }
        return absorbItem(level, origin, hydrationReference(color), simulate) > 0;
    }
    private static int absorbItem(
            Level level,
            BlockPos origin,
            BlockState hydrationReference,
            boolean simulate) {
        int absorbed = 0;
        var queue = new ArrayDeque<BlockPos>();
        var visited = new HashSet<BlockPos>();

        queue.add(origin);
        visited.add(origin);

        while (!queue.isEmpty() && absorbed < ITEM_LIMIT) {
            BlockPos current = queue.poll();

            if (tryRemoveWater(level, origin, hydrationReference, current, false, simulate)) {
                absorbed++;
            }

            enqueueNeighbors(current, next -> {
                if (visited.add(next) && withinItemRange(origin, next)) {
                    queue.add(next);
                }
            });
        }

        return absorbed;
    }

    // --- shared ---

    public static BlockState hydrationReference(SpongeItemColor color) {
        return color.toBlockColor()
                .map(c -> c.dry().get().defaultBlockState())
                .orElse(Blocks.SPONGE.defaultBlockState());
    }
    private static void enqueueNeighbors(BlockPos current, java.util.function.Consumer<BlockPos> enqueue) {
        for (Direction direction : Direction.values()) {
            enqueue.accept(current.relative(direction));
        }
    }
    private static boolean withinItemRange(BlockPos origin, BlockPos target) {
        return origin.distSqr(target) <= ITEM_RANGE_SQR;
    }
    private static boolean tryRemoveWater(
            Level level,
            BlockPos spongePos,
            BlockState spongeState,
            BlockPos target,
            boolean skipOriginRemoval,
            boolean simulate) {
        if (skipOriginRemoval && target.equals(spongePos)) {
            return true;
        }

        BlockState state = level.getBlockState(target);
        FluidState fluid = level.getFluidState(target);
        if (!spongeState.canBeHydrated(level, spongePos, fluid, target)) {
            return false;
        }

        Block block = state.getBlock();
        if (block instanceof BucketPickup pickup) {
            if (simulate) {
                if (!fluid.isEmpty()) {
                    return true;
                }
            } else if (!pickup.pickupBlock(null, level, target, state).isEmpty()) {
                return true;
            }
        }

        if (block instanceof LiquidBlock) {
            if (!simulate) {
                level.setBlock(target, Blocks.AIR.defaultBlockState(), 3);
            }
            return true;
        }

        if (!isAquaticPlant(state)) {
            return false;
        }

        if (!simulate) {
            BlockEntity blockEntity = state.hasBlockEntity() ? level.getBlockEntity(target) : null;
            dropResources(state, level, target, blockEntity);
            level.setBlock(target, Blocks.AIR.defaultBlockState(), 3);
        }
        return true;
    }
}