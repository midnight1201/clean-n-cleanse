package net.midnight.cncleanse.content.sponge.block;

import net.midnight.cncleanse.content.sponge.SpongeAbsorption;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SpongeBlock;
import net.minecraft.world.level.block.state.BlockState;

public class DrySpongeBlock extends SpongeBlock {

    private final SpongeBlockColor color;

    public DrySpongeBlock(Properties properties, SpongeBlockColor color) {
        super(properties);
        this.color = color;
    }

    public BlockState wetState() {
        return color.wetState();
    }

    @Override
    protected void tryAbsorbWater(Level level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        if (!SpongeAbsorption.absorbFromBlock(level, pos, state)) {
            return;
        }
        level.setBlock(pos, color.wetState(), 2);
        level.playSound(null, pos, SoundEvents.SPONGE_ABSORB, SoundSource.BLOCKS, 1.0F, 1.0F);
    }
}