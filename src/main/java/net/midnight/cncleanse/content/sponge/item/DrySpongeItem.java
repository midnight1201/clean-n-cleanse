package net.midnight.cncleanse.content.sponge.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class DrySpongeItem extends Item {

    private final SpongeItemColor color;

    public DrySpongeItem(Properties properties, SpongeItemColor color) {
        super(properties);
        this.color = color;
    }

    public SpongeItemColor getColor() {
        return color;
    }

    public static boolean canAbsorbAt(Level level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        return level.getFluidState(pos).is(FluidTags.WATER)
                && state.getBlock() instanceof BucketPickup;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack held = player.getItemInHand(hand);
        BlockHitResult hit = getPlayerPOVHitResult(level, player, ClipContext.Fluid.SOURCE_ONLY);

        if (hit.getType() != HitResult.Type.BLOCK) {
            return InteractionResultHolder.pass(held);
        }

        BlockPos pos = hit.getBlockPos();
        Direction face = hit.getDirection();
        if (!level.mayInteract(player, pos) || !player.mayUseItemAt(pos.relative(face), face, held)) {
            return InteractionResultHolder.fail(held);
        }

        if (!canAbsorbAt(level, pos)) {
            return InteractionResultHolder.fail(held);
        }

        if (level.isClientSide()) {
            return InteractionResultHolder.sidedSuccess(held, true);
        }

        BlockState state = level.getBlockState(pos);
        BucketPickup pickup = (BucketPickup) state.getBlock();
        if (pickup.pickupBlock(player, level, pos, state).isEmpty()) {
            return InteractionResultHolder.fail(held);
        }

        level.playSound(null, pos, SoundEvents.SPONGE_ABSORB, SoundSource.BLOCKS, 1.0F, 1.0F);
        level.gameEvent(player, GameEvent.FLUID_PICKUP, pos);
        player.awardStat(Stats.ITEM_USED.get(this));

        ItemStack wet = new ItemStack(color.wet().get());
        return InteractionResultHolder.sidedSuccess(ItemUtils.createFilledResult(held, player, wet), false);
    }
}