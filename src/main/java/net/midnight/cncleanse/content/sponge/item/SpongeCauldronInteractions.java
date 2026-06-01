package net.midnight.cncleanse.content.sponge.item;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.Nullable;

public final class SpongeCauldronInteractions {
    private SpongeCauldronInteractions() {}

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

    public static boolean depositWater(Level level, BlockPos pos, BlockState state, @Nullable Entity entity) {
        BlockState next = getNextStateForDeposit(state);
        if (next == null) {
            return false;
        }
        if (level.isClientSide()) {
            return true;
        }
        level.setBlock(pos, next, 3);
        level.playSound(null, pos, SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
        level.gameEvent(entity, GameEvent.FLUID_PLACE, pos);
        return true;
    }

    public static boolean takeWater(Level level, BlockPos pos, BlockState state, @Nullable Entity entity) {
        BlockState next = getNextStateForTake(state);
        if (next == null) {
            return false;
        }
        if (level.isClientSide()) {
            return true;
        }
        level.setBlock(pos, next, 3);
        level.playSound(null, pos, SoundEvents.BOTTLE_FILL, SoundSource.BLOCKS, 1.0F, 1.0F);
        level.gameEvent(entity, GameEvent.FLUID_PICKUP, pos);
        return true;
    }

    public static InteractionResult depositWater(UseOnContext ctx, SpongeItemColor color) {
        Level level = ctx.getLevel();
        BlockPos pos = ctx.getClickedPos();
        BlockState state = level.getBlockState(pos);
        ItemStack held = ctx.getItemInHand();

        if (getNextStateForDeposit(state) == null) {
            return InteractionResult.PASS;
        }

        Player player = ctx.getPlayer();
        if (player == null) {
            return InteractionResult.PASS;
        }
        if (!player.mayUseItemAt(pos, ctx.getClickedFace(), held)) {
            return InteractionResult.FAIL;
        }

        if (level.isClientSide()) {
            return InteractionResult.SUCCESS;
        }

        if (!depositWater(level, pos, state, player)) {
            return InteractionResult.FAIL;
        }

        ItemStack dry = new ItemStack(color.dry().get());
        player.setItemInHand(ctx.getHand(), ItemUtils.createFilledResult(held, player, dry));
        player.awardStat(Stats.ITEM_USED.get(held.getItem()));

        return InteractionResult.CONSUME;
    }

    public static InteractionResult takeWater(UseOnContext ctx, SpongeItemColor color) {
        Level level = ctx.getLevel();
        BlockPos pos = ctx.getClickedPos();
        BlockState state = level.getBlockState(pos);
        ItemStack held = ctx.getItemInHand();

        if (getNextStateForTake(state) == null) {
            return InteractionResult.PASS;
        }

        Player player = ctx.getPlayer();
        if (player == null) {
            return InteractionResult.PASS;
        }
        if (!player.mayUseItemAt(pos, ctx.getClickedFace(), held)) {
            return InteractionResult.FAIL;
        }

        if (level.isClientSide()) {
            return InteractionResult.SUCCESS;
        }

        if (!takeWater(level, pos, state, player)) {
            return InteractionResult.FAIL;
        }

        ItemStack wet = new ItemStack(color.wet().get());
        player.setItemInHand(ctx.getHand(), ItemUtils.createFilledResult(held, player, wet));
        player.awardStat(Stats.ITEM_USED.get(held.getItem()));

        return InteractionResult.CONSUME;
    }
}