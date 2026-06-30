package net.midnight.cncleanse.mixin;

import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(FallingBlockEntity.class)
public interface FallingBlockInvoker {
    @Invoker("<init>")
    static FallingBlockEntity cncleanse$new(Level level, double x, double y, double z, BlockState state) {
        throw new AssertionError();
    }
}