package net.midnight.cncleanse.sponge;

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
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class SpongeItem extends Item {

    private final Item wetItem;

    public SpongeItem(Properties properties, Item wetItem) {
        super(properties);
        this.wetItem = wetItem;
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack itemstack = player.getItemInHand(usedHand);
        BlockHitResult blockhitresult = getPlayerPOVHitResult(level, player, ClipContext.Fluid.SOURCE_ONLY);
        if (blockhitresult.getType() == HitResult.Type.MISS) {
            return InteractionResultHolder.pass(itemstack);
        } else if (blockhitresult.getType() != HitResult.Type.BLOCK) {
            return InteractionResultHolder.pass(itemstack);
        } else {
            BlockPos blockPos = blockhitresult.getBlockPos();
            Direction direction = blockhitresult.getDirection();
            BlockPos relative = blockPos.relative(direction);
            if (level.mayInteract(player, blockPos) && player.mayUseItemAt(relative, direction, itemstack)) {
                BlockState blockState;
                ItemStack itemStack;
                    blockState = level.getBlockState(blockPos);
                    Block block = blockState.getBlock();
                if (level.getFluidState(blockPos).is(FluidTags.WATER)) {
                    if (block instanceof BucketPickup bucketpickup) {
                        itemStack = bucketpickup.pickupBlock(player, level, blockPos, blockState);
                        if (!itemStack.isEmpty()) {
                            player.awardStat(Stats.ITEM_USED.get(this));
                            level.playSound(player, player.getX(), player.getY(), player.getZ(),
                                    SoundEvents.SPONGE_ABSORB, SoundSource.NEUTRAL, 1.0F, 1.0F);
                            level.gameEvent(player, GameEvent.FLUID_PICKUP, blockPos);
                            ItemStack transformedItem = new ItemStack(wetItem);

                            return InteractionResultHolder.sidedSuccess(this.turnDryIntoWet(itemstack, player,
                                    transformedItem), level.isClientSide());
                        }
                    }
                }
            }
            return InteractionResultHolder.fail(itemstack);
        }
    }

    protected ItemStack turnDryIntoWet(ItemStack dry, Player player, ItemStack wet) {
        player.awardStat(Stats.ITEM_USED.get(this));
        return ItemUtils.createFilledResult(dry, player, wet);
    }
}
