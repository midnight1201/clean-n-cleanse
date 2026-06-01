package net.midnight.cncleanse.content;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.DataMapHooks;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class LimeSulfurBottle extends Item {

    public LimeSulfurBottle(Properties properties) {
        super(properties);
    }

    public record BottleAction(BlockState newState, SoundEvent blockSound, float volume, float pitch) {
        public static BottleAction of(BlockState state, SoundEvent sound) {
            return new BottleAction(state, sound, 1.0F, 1.0F);
        }

        public static BottleAction copperOxidize(BlockState state) {
            return new BottleAction(state, SoundEvents.FIRE_EXTINGUISH, 0.2F, 1.2F);
        }
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Optional<BottleAction> action = resolveAction(level.getBlockState(pos));
        if (action.isEmpty()) {
            return InteractionResult.PASS;
        }

        applyAndConsume(
                level,
                pos,
                context.getItemInHand(),
                context.getPlayer(),
                context.getHand(),
                action.get());

        return InteractionResult.sidedSuccess(level.isClientSide());
    }

    public static Optional<BottleAction> resolveAction(BlockState state) {
        if (state.is(Blocks.MYCELIUM)) {
            return Optional.of(BottleAction.of(Blocks.DIRT.defaultBlockState(), SoundEvents.HOE_TILL));
        }
        if (isWaxedCopper(state)) {
            return Optional.empty();
        }
        if (state.getBlock() instanceof WeatheringCopper weathering) {
            return weathering.getNext(state).map(BottleAction::copperOxidize);
        }
        return Optional.empty();
    }

    public static void applyEffect(Level level, BlockPos pos, BottleAction action) {
        if (level.isClientSide()) {
            return;
        }
        level.setBlockAndUpdate(pos, action.newState());
        level.playSound(null, pos, action.blockSound(), SoundSource.BLOCKS, action.volume(), action.pitch());
        level.playSound(null, pos, SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS, 1f, 1f);
    }

    public static void applyAndConsume(
            Level level,
            BlockPos pos,
            ItemStack stack,
            @Nullable Player player,
            @Nullable InteractionHand hand,
            BottleAction action) {
        applyEffect(level, pos, action);
        if (level.isClientSide() || player == null || hand == null) {
            return;
        }
        ItemStack result = ItemUtils.createFilledResult(stack, player, new ItemStack(Items.GLASS_BOTTLE));
        player.setItemInHand(hand, result);
    }

    private static boolean isWaxedCopper(BlockState state) {
        return DataMapHooks.INVERSE_WAXABLES_DATAMAP.containsKey(state.getBlock());
    }
}