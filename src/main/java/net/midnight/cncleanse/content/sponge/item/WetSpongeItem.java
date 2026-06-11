package net.midnight.cncleanse.content.sponge.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.Nullable;

public class WetSpongeItem extends Item {

    private final SpongeItemColor color;

    // --- fields / enum ---

    public WetSpongeItem(Properties properties, SpongeItemColor color) {
        super(properties);
        this.color = color;
    }

    public SpongeItemColor getColor() {
        return color;
    }

    public enum MudConversionSource {
        PLAYER,
        DISPENSER
    }

    // --- static API ---

    public static boolean canConvertToMud(BlockState state, Direction clickedFace) {
        if (clickedFace == Direction.DOWN) {
            return false;
        }
        return state.is(BlockTags.CONVERTABLE_TO_MUD);
    }

    public static boolean canConvertToMudFromDispenser(BlockState state) {
        return state.is(BlockTags.CONVERTABLE_TO_MUD);
    }

    public static void applyMudConversion(
            Level level,
            BlockPos pos,
            @Nullable Player player,
            MudConversionSource source) {
        if (level.isClientSide()) {
            return;
        }

        playMudSounds(level, pos, source);

        if (level instanceof ServerLevel serverLevel) {
            for (int i = 0; i < 5; i++) {
                serverLevel.sendParticles(
                        ParticleTypes.SPLASH,
                        pos.getX() + level.random.nextDouble(),
                        pos.getY() + 1,
                        pos.getZ() + level.random.nextDouble(),
                        1, 0.0, 0.0, 0.0, 1.0);
            }
        }

        level.gameEvent(player, GameEvent.FLUID_PLACE, pos);
        level.setBlockAndUpdate(pos, Blocks.MUD.defaultBlockState());
    }

    private static void playMudSounds(Level level, BlockPos pos, MudConversionSource source) {
        if (source == MudConversionSource.PLAYER) {
            level.playSound(null, pos, SoundEvents.GENERIC_SPLASH, SoundSource.BLOCKS, 1.0F, 1.0F);
        }
        level.playSound(null, pos, SoundEvents.SPONGE_ABSORB, SoundSource.BLOCKS, 1.0F, 1.0F);
    }

    // --- use on ---

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = level.getBlockState(pos);
        Player player = context.getPlayer();
        ItemStack held = context.getItemInHand();
        Direction clickedFace = context.getClickedFace();

        if (!canConvertToMud(state, clickedFace)) {
            return InteractionResult.PASS;
        }
        if (player != null && !player.mayUseItemAt(pos, clickedFace, held)) {
            return InteractionResult.FAIL;
        }

        applyMudConversion(level, pos, player, MudConversionSource.PLAYER);

        if (!level.isClientSide()) {
            ItemStack dry = new ItemStack(color.dry().get());
            if (player != null) {
                player.setItemInHand(context.getHand(), ItemUtils.createFilledResult(held, player, dry));
                player.awardStat(Stats.ITEM_USED.get(this));
            } else {
                held.shrink(1);
            }
        }

        return InteractionResult.sidedSuccess(level.isClientSide());
    }
}