package net.midnight.cncleanse.data;

import com.simibubi.create.api.equipment.potatoCannon.PotatoCannonProjectileType;
import com.simibubi.create.api.registry.CreateRegistries;
import com.simibubi.create.content.equipment.potatoCannon.AllPotatoProjectileBlockHitActions.PlaceBlockOnGround;
import net.midnight.cncleanse.CnCleanse;
import net.midnight.cncleanse.register.CnCleanseBlocks;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class CnCleansePotatoProjectiles {

    public static void bootstrap(BootstrapContext<PotatoCannonProjectileType> ctx) {
        drySponge(ctx, "yellow_sponge_block", Blocks.SPONGE);
        drySponge(ctx, "white_sponge_block", CnCleanseBlocks.WHITE_SPONGE_BLOCK.get());
        drySponge(ctx, "red_sponge_block", CnCleanseBlocks.RED_SPONGE_BLOCK.get());
        drySponge(ctx, "lime_sponge_block", CnCleanseBlocks.LIME_SPONGE_BLOCK.get());
        drySponge(ctx, "light_blue_sponge_block", CnCleanseBlocks.LIGHT_BLUE_SPONGE_BLOCK.get());
    }

    private static void drySponge(BootstrapContext<PotatoCannonProjectileType> ctx, String name, Block block) {
        ctx.register(
                ResourceKey.create(CreateRegistries.POTATO_PROJECTILE_TYPE, CnCleanse.asResource(name)),
                new PotatoCannonProjectileType.Builder()
                        .damage(2)
                        .reloadTicks(12)
                        .knockback(0.3f)
                        .velocity(1.1f)
                        .drag(0.95f)
                        .gravity(0.85f)
                        .soundPitch(1.2f)
                        .renderTumbling()
                        .onBlockHit(new PlaceBlockOnGround(block))
                        .dropStack(new ItemStack(block))
                        .addItems(block)
                        .build());
    }
}