package dev.yopa.playsu.client.renderers;

import dev.yopa.playsu.PlaySUMod;
import dev.yopa.playsu.client.models.ChampSUModel;
import dev.yopa.playsu.entities.ChampSU;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class ChampSURenderer extends MobRenderer<ChampSU, ChampSUModel<ChampSU>> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(PlaySUMod.MODID, "textures/entity/champsu.png");
    public ChampSURenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new ChampSUModel(ctx.bakeLayer(ChampSUModel.LAYER_LOCATION)), 0.5f);
    }

    @NotNull
    @Override
    public ResourceLocation getTextureLocation(ChampSU champsu) {
        return TEXTURE;
    }
}
