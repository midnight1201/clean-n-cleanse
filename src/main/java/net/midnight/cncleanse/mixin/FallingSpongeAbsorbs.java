package net.midnight.cncleanse.mixin;

import net.midnight.cncleanse.content.sponge.SpongeAbsorption;
import net.midnight.cncleanse.content.sponge.block.DrySpongeBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FallingBlockEntity.class)
public abstract class FallingSpongeAbsorbs {

    @Shadow private BlockState blockState;

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    private void cncleanse$absorbWhileFalling(CallbackInfo ci) {
        FallingBlockEntity self = (FallingBlockEntity) (Object) this;
        Level level = self.level();
        if (level.isClientSide) {
            return;
        }

        BlockState wet = cncleanse$wetVariant(blockState);
        if (wet == null) {
            return;
        }

        BlockPos pos = self.blockPosition();
        if (!SpongeAbsorption.absorbFromBlock(level, pos, blockState)) {
            return;
        }

        FallingBlockEntity wetFalling =
                FallingBlockInvoker.cncleanse$new(level, self.getX(), self.getY(), self.getZ(), wet);
        wetFalling.time = 1;
        wetFalling.setDeltaMovement(self.getDeltaMovement());
        wetFalling.fallDistance = self.fallDistance;
        level.addFreshEntity(wetFalling);

        level.playSound(null, pos, SoundEvents.SPONGE_ABSORB, SoundSource.BLOCKS, 1.0F, 1.0F);
        self.discard();
        ci.cancel();
    }

    @Unique @Nullable
    private static BlockState cncleanse$wetVariant(BlockState state) {
        if (state.getBlock() instanceof DrySpongeBlock dry) return dry.wetState();
        if (state.is(Blocks.SPONGE)) return Blocks.WET_SPONGE.defaultBlockState();
        return null;
    }
}