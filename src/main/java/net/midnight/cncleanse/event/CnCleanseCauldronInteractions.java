package net.midnight.cncleanse.event;

import net.midnight.cncleanse.content.sponge.item.SpongeItemColor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.Nullable;

public final class CnCleanseCauldronInteractions {
    private CnCleanseCauldronInteractions() {}

    @Nullable
    public static BlockState getNextStateForDeposit(BlockState state) {
        if (state.is(Blocks.CAULDRON)) {
            return Blocks.WATER_CAULDRON.defaultBlockState()
                    .setValue(LayeredCauldronBlock.LEVEL, 1);
        }
        if (state.is(Blocks.WATER_CAULDRON)) {
            int current = state.getValue(LayeredCauldronBlock.LEVEL);
            if (current >= 3) {
                return null;
            }
            return state.setValue(LayeredCauldronBlock.LEVEL, current + 1);
        }
        return null;
    }

    @Nullable
    public static BlockState getNextStateForTake(BlockState state) {
        if (!state.is(Blocks.WATER_CAULDRON)) {
            return null;
        }
        int current = state.getValue(LayeredCauldronBlock.LEVEL);
        return current <= 1
                ? Blocks.CAULDRON.defaultBlockState()
                : state.setValue(LayeredCauldronBlock.LEVEL, current - 1);
    }

    public static CauldronInteraction deposit(SpongeItemColor color) {
        return (state, level, pos, player, hand, stack) ->
                interactDeposit(state, level, pos, player, hand, stack, color);
    }

    public static CauldronInteraction take(SpongeItemColor color) {
        return (state, level, pos, player, hand, stack) ->
                interactTake(state, level, pos, player, hand, stack, color);
    }

    private static ItemInteractionResult interactDeposit(
            BlockState state,
            Level level,
            BlockPos pos,
            Player player,
            InteractionHand hand,
            ItemStack stack,
            SpongeItemColor color) {
        BlockState next = getNextStateForDeposit(state);
        if (next == null) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }
        if (level.isClientSide()) {
            return ItemInteractionResult.SUCCESS;
        }

        level.setBlock(pos, next, 3);
        level.playSound(null, pos, SoundEvents.SPONGE_ABSORB, SoundSource.BLOCKS, 1.0F, 1.0F);
        level.gameEvent(player, GameEvent.FLUID_PLACE, pos);

        ItemStack dry = new ItemStack(color.dry().get());
        player.setItemInHand(hand, ItemUtils.createFilledResult(stack, player, dry));
        player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
        return ItemInteractionResult.CONSUME;
    }

    private static ItemInteractionResult interactTake(
            BlockState state,
            Level level,
            BlockPos pos,
            Player player,
            InteractionHand hand,
            ItemStack stack,
            SpongeItemColor color) {
        BlockState next = getNextStateForTake(state);
        if (next == null) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }
        if (level.isClientSide()) {
            return ItemInteractionResult.SUCCESS;
        }

        level.setBlock(pos, next, 3);
        level.playSound(null, pos, SoundEvents.SPONGE_ABSORB, SoundSource.BLOCKS, 1.0F, 1.0F);
        level.gameEvent(player, GameEvent.FLUID_PICKUP, pos);

        ItemStack wet = new ItemStack(color.wet().get());
        player.setItemInHand(hand, ItemUtils.createFilledResult(stack, player, wet));
        player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
        return ItemInteractionResult.CONSUME;
    }
}