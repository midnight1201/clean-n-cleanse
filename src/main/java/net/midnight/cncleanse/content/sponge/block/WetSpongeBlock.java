package net.midnight.cncleanse.content.sponge.block;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class WetSpongeBlock extends net.minecraft.world.level.block.WetSpongeBlock {

    private final SpongeBlockColor color;

    public WetSpongeBlock(Properties properties, SpongeBlockColor color) {
        super(properties);
        this.color = color;
    }

    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
        if (!level.dimensionType().ultraWarm()) {
            return;
        }

        level.setBlock(pos, color.dryState(), 3);
        level.levelEvent(2009, pos, 0);
        level.playSound(
                null,
                pos,
                SoundEvents.WET_SPONGE_DRIES,
                SoundSource.BLOCKS,
                1.0F,
                (1.0F + level.getRandom().nextFloat() * 0.2F) * 0.7F
        );
    }
}