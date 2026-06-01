package net.midnight.cncleanse.content.sponge.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.SpongeBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;

public class DrySpongeBlock extends SpongeBlock {

    private static final int ABSORB_RADIUS = 6;
    private static final int ABSORB_LIMIT = 65;

    private final SpongeBlockColor color;

    public DrySpongeBlock(Properties properties, SpongeBlockColor color) {
        super(properties);
        this.color = color;
    }

    @Override
    protected void tryAbsorbWater(Level level, BlockPos pos) {
        if (!removeWaterBreadthFirstSearch(level, pos)) {
            return;
        }

        level.setBlock(pos, color.wetState(), 2);
        level.playSound(null, pos, SoundEvents.SPONGE_ABSORB, SoundSource.BLOCKS, 1.0F, 1.0F);
    }

    private boolean removeWaterBreadthFirstSearch(Level level, BlockPos spongePos) {
        BlockState spongeState = level.getBlockState(spongePos);

        return BlockPos.breadthFirstTraversal(
                spongePos,
                ABSORB_RADIUS,
                ABSORB_LIMIT,
                (current, enqueue) -> {
                    for (Direction direction : Direction.values()) {
                        enqueue.accept(current.relative(direction));
                    }
                },
                candidate -> tryRemoveWater(level, spongePos, spongeState, candidate)
        ) > 1;
    }

    private boolean tryRemoveWater(Level level, BlockPos spongePos, BlockState spongeState, BlockPos target) {
        if (target.equals(spongePos)) {
            return true;
        }

        BlockState state = level.getBlockState(target);
        FluidState fluid = level.getFluidState(target);
        if (!spongeState.canBeHydrated(level, spongePos, fluid, target)) {
            return false;
        }

        Block block = state.getBlock();
        if (block instanceof BucketPickup pickup && !pickup.pickupBlock(null, level, target, state).isEmpty()) {
            return true;
        }

        if (block instanceof LiquidBlock) {
            level.setBlock(target, Blocks.AIR.defaultBlockState(), 3);
            return true;
        }

        if (!state.is(Blocks.KELP)
                && !state.is(Blocks.KELP_PLANT)
                && !state.is(Blocks.SEAGRASS)
                && !state.is(Blocks.TALL_SEAGRASS)) {
            return false;
        }

        BlockEntity blockEntity = state.hasBlockEntity() ? level.getBlockEntity(target) : null;
        dropResources(state, level, target, blockEntity);
        level.setBlock(target, Blocks.AIR.defaultBlockState(), 3);
        return true;
    }
}