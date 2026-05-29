package net.midnight.cncleanse.sponge;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class WetSpongeBlock extends net.minecraft.world.level.block.WetSpongeBlock {
    private SpongeColor color;
    public WetSpongeBlock(Properties properties, SpongeColor color) {
        super(properties);
        this.color = color;
    }

    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
        if (level.dimensionType().ultraWarm()) {
            level.setBlock(pos, SpongeBlocks.forColor(color).dry().get().defaultBlockState(), 3);
            level.levelEvent(2009, pos, 0);
            level.playSound((Player)null, pos, SoundEvents.WET_SPONGE_DRIES, SoundSource.BLOCKS, 1.0F, (1.0F + level.getRandom().nextFloat() * 0.2F) * 0.7F);
        }
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        super.animateTick(state, level, pos, random);
    }
}
