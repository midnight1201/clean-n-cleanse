package net.midnight.cncleanse.event;

import net.midnight.cncleanse.CnCleanse;
import net.midnight.cncleanse.content.LimeSulfurBottle;
import net.midnight.cncleanse.content.sponge.item.DrySpongeItem;
import net.midnight.cncleanse.content.sponge.item.SpongeItemColor;
import net.midnight.cncleanse.content.sponge.item.WetSpongeItem;
import net.midnight.cncleanse.register.CnCleanseItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.DispenserBlockEntity;
import net.minecraft.world.level.gameevent.GameEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

@EventBusSubscriber(modid = CnCleanse.ID)
public final class CnCleanseDispenserBehaviors {

    private CnCleanseDispenserBehaviors() {}

    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            DispenserBlock.registerBehavior(
                    CnCleanseItems.LIME_SULFUR_BOTTLE.get(),
                    new LimeSulfurDispenseBehavior());

            DrySpongeDispenseBehavior drySponge = new DrySpongeDispenseBehavior();
            WetSpongeDispenseBehavior wetSponge = new WetSpongeDispenseBehavior();
            for (SpongeItemColor color : SpongeItemColor.values()) {
                DispenserBlock.registerBehavior(color.dry().get(), drySponge);
                DispenserBlock.registerBehavior(color.wet().get(), wetSponge);
            }
        });
    }
    private static final class LimeSulfurDispenseBehavior extends OptionalDispenseItemBehavior {
        @Override
        protected ItemStack execute(BlockSource source, ItemStack stack) {
            Level level = source.level();
            Direction facing = source.state().getValue(DispenserBlock.FACING);
            BlockPos target = source.pos().relative(facing);

            var action = LimeSulfurBottle.resolveAction(level.getBlockState(target));
            if (action.isEmpty()) {
                return stack;
            }

            LimeSulfurBottle.applyEffect(level, target, action.get());
            setSuccess(true);
            return replaceOne(source, stack, new ItemStack(Items.GLASS_BOTTLE));
        }
    }
    private static final class DrySpongeDispenseBehavior extends OptionalDispenseItemBehavior {
        @Override
        protected ItemStack execute(BlockSource source, ItemStack stack) {
            if (!(stack.getItem() instanceof DrySpongeItem dry)) {
                return stack;
            }

            Level level = source.level();
            Direction facing = source.state().getValue(DispenserBlock.FACING);
            BlockPos target = source.pos().relative(facing);

            if (!DrySpongeItem.canAbsorbAt(level, target)) {
                return stack;
            }

            BlockState absorbState = level.getBlockState(target);
            if (!(absorbState.getBlock() instanceof BucketPickup pickup)
                    || pickup.pickupBlock(null, level, target, absorbState).isEmpty()) {
                return stack;
            }

            level.playSound(null, target, SoundEvents.SPONGE_ABSORB, SoundSource.BLOCKS, 1.0F, 1.0F);
            level.gameEvent(null, GameEvent.FLUID_PICKUP, target);

            ItemStack wet = new ItemStack(dry.getColor().wet().get());
            setSuccess(true);
            return replaceOne(source, stack, wet);
        }
    }
    private static final class WetSpongeDispenseBehavior extends OptionalDispenseItemBehavior {
        @Override
        protected ItemStack execute(BlockSource source, ItemStack stack) {
            if (!(stack.getItem() instanceof WetSpongeItem wet)) {
                return stack;
            }

            Level level = source.level();
            Direction facing = source.state().getValue(DispenserBlock.FACING);
            BlockPos target = source.pos().relative(facing);
            BlockState state = level.getBlockState(target);

            if (!WetSpongeItem.canConvertToMudFromDispenser(state)) {
                return stack;
            }

            WetSpongeItem.applyMudConversion(level, target, null, WetSpongeItem.MudConversionSource.DISPENSER);

            ItemStack dry = new ItemStack(wet.getColor().dry().get());
            setSuccess(true);
            return replaceOne(source, stack, dry);
        }
    }
    private static void insertOrDrop(BlockSource source, ItemStack stack) {
        if (stack.isEmpty()) {
            return;
        }
        Level level = source.level();
        BlockPos pos = source.pos();
        DispenserBlockEntity dispenser = source.blockEntity();

        for (int i = 0; i < dispenser.getContainerSize(); i++) {
            ItemStack slot = dispenser.getItem(i);
            if (slot.isEmpty()) {
                dispenser.setItem(i, stack);
                return;
            }
            if (ItemStack.isSameItemSameComponents(slot, stack)) {
                int room = slot.getMaxStackSize() - slot.getCount();
                if (room > 0) {
                    int move = Math.min(room, stack.getCount());
                    slot.grow(move);
                    stack.shrink(move);
                    dispenser.setItem(i, slot);
                    if (stack.isEmpty()) {
                        return;
                    }
                }
            }
        }
        Block.popResource(level, pos, stack);
    }
    private static ItemStack replaceOne(BlockSource source, ItemStack stack, ItemStack result) {
        if (stack.getCount() == 1) {
            return result;
        }
        stack.shrink(1);
        insertOrDrop(source, result);
        return stack;
    }
}