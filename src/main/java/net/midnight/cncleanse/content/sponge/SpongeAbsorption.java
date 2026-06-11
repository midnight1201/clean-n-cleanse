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

import static net.minecraft.world.level.block.Block.dropResources;

public final class SpongeAbsorption {

    public static final int BLOCK_RADIUS = 6;
    public static final int BLOCK_LIMIT = 65;

    public static final int ITEM_RADIUS = 2;
    public static final int ITEM_LIMIT = 16;

    private SpongeAbsorption() {}

    public static boolean isAquaticPlant(BlockState state) {
        return state.is(Blocks.KELP)
                || state.is(Blocks.KELP_PLANT)
                || state.is(Blocks.SEAGRASS)
                || state.is(Blocks.TALL_SEAGRASS);
    }

    public static boolean isInvalidItemOrigin(BlockState state) {
        return isAquaticPlant(state);
    }

    public static BlockState hydrationReference(SpongeItemColor color) {
        return color.toBlockColor()
                .map(c -> c.dry().get().defaultBlockState())
                .orElse(Blocks.SPONGE.defaultBlockState());
    }

    public static boolean wouldAbsorbFromItem(Level level, BlockPos origin, SpongeItemColor color) {
        if (isInvalidItemOrigin(level.getBlockState(origin))) {
            return false;
        }
        return absorb(level, origin, hydrationReference(color), ITEM_RADIUS, ITEM_LIMIT, false, true) > 0;
    }

    public static boolean absorbFromItem(Level level, BlockPos origin, SpongeItemColor color) {
        if (isInvalidItemOrigin(level.getBlockState(origin))) {
            return false;
        }
        return absorb(level, origin, hydrationReference(color), ITEM_RADIUS, ITEM_LIMIT, false, false) > 0;
    }

    public static boolean absorbFromBlock(Level level, BlockPos spongePos, BlockState spongeState) {
        return absorb(level, spongePos, spongeState, BLOCK_RADIUS, BLOCK_LIMIT, true, false) > 1;
    }

    private static int absorb(
            Level level,
            BlockPos origin,
            BlockState hydrationReference,
            int radius,
            int limit,
            boolean skipOriginRemoval,
            boolean simulate) {
        return BlockPos.breadthFirstTraversal(
                origin,
                radius,
                limit,
                (current, enqueue) -> {
                    for (Direction direction : Direction.values()) {
                        enqueue.accept(current.relative(direction));
                    }
                },
                target -> tryRemoveWater(level, origin, hydrationReference, target, skipOriginRemoval, simulate)
        );
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