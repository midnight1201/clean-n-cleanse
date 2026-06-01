package net.midnight.cncleanse.mixin;

import com.simibubi.create.content.processing.basin.BasinBlock;
import net.midnight.cncleanse.register.CnCleanseTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = BasinBlock.class, remap = false)
public class BasinSpongeDrain {

    @Redirect(
            method = "lambda$useItemOn$1(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/InteractionHand;Lnet/minecraft/core/BlockPos;Lcom/simibubi/create/content/processing/basin/BasinBlockEntity;)Lnet/minecraft/world/ItemInteractionResult;",
            remap = false,
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/lang/Object;equals(Ljava/lang/Object;)Z"
            )
    )
    private static boolean cncleanse$basinAcceptsSpongeBlock(Object item, Object other) {
        if (item.equals(other)) {
            return true;
        }
        return other == Items.SPONGE
                && item instanceof Item spongeItem
                && spongeItem.builtInRegistryHolder().is(CnCleanseTags.Items.SPONGE_BLOCKS);
    }
}