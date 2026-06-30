package net.midnight.cncleanse.mixin;

import com.simibubi.create.content.processing.basin.BasinBlock;
import com.simibubi.create.content.processing.basin.BasinBlockEntity;
import net.midnight.cncleanse.content.sponge.item.DrySpongeItem;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = BasinBlock.class, remap = false)
public class BasinSpongeItem {

    @Inject(
            method = "lambda$useItemOn$1(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/InteractionHand;Lnet/minecraft/core/BlockPos;Lcom/simibubi/create/content/processing/basin/BasinBlockEntity;)Lnet/minecraft/world/ItemInteractionResult;",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    private static void cncleanse$drySpongeAbsorbsFromBasin(
            ItemStack stack, Level level, Player player, InteractionHand hand,
            BlockPos pos, BasinBlockEntity be,
            CallbackInfoReturnable<ItemInteractionResult> cir) {

        if (!(stack.getItem() instanceof DrySpongeItem dry)) {
            return;
        }

        IFluidHandler fluidHandler = level.getCapability(Capabilities.FluidHandler.BLOCK, pos, null);
        if (fluidHandler == null) {
            return;
        }

        FluidStack request = new FluidStack(Fluids.WATER, 250);
        FluidStack simulated = fluidHandler.drain(request, IFluidHandler.FluidAction.SIMULATE);
        if (simulated.getAmount() < 250) {
            return;
        }

        if (!level.isClientSide()) {
            fluidHandler.drain(request, IFluidHandler.FluidAction.EXECUTE);

            stack.shrink(1);
            ItemStack wet = new ItemStack(dry.getColor().wet().get());
            player.getInventory().placeItemBackInInventory(wet);

            be.notifyUpdate();
        }

        level.gameEvent(player, GameEvent.FLUID_PICKUP, pos);
        cir.setReturnValue(ItemInteractionResult.sidedSuccess(level.isClientSide()));
    }
}
