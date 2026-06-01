package net.midnight.cncleanse.mixin;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.midnight.cncleanse.register.CnCleanseTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AbstractFurnaceBlockEntity.class)
public class SpongeSmelting {

    @Definition(id = "is", method = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z")
    @Definition(id = "WET_SPONGE", field = "Lnet/minecraft/world/level/block/Blocks;WET_SPONGE:Lnet/minecraft/world/level/block/Block;")
    @Definition(id = "asItem", method = "Lnet/minecraft/world/level/block/Block;asItem()Lnet/minecraft/world/item/Item;")
    @Expression("?.is(WET_SPONGE.asItem())")
    @WrapOperation(method = "burn", at = @At("MIXINEXTRAS:EXPRESSION"))
    private static boolean addWetSponge(ItemStack instance, Item item, Operation<Boolean> original) {
        return original.call(instance, item) || instance.is(CnCleanseTags.Items.WET_SPONGE_BLOCKS);
    }
}
