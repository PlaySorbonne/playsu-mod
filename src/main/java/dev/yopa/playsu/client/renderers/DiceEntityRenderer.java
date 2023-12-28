package dev.yopa.playsu.client.renderers;

import dev.yopa.playsu.PlaySUMod;
import dev.yopa.playsu.client.models.DiceEntityModel;
import dev.yopa.playsu.entities.DiceEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;

public class DiceEntityRenderer extends LivingEntityRenderer<DiceEntity, DiceEntityModel<DiceEntity>> {
    public static final ResourceLocation TEXTURE1 = new ResourceLocation(PlaySUMod.MODID, "textures/entity/dice1.png");
    public static final ResourceLocation TEXTURE2 = new ResourceLocation(PlaySUMod.MODID, "textures/entity/dice2.png");
    public static final ResourceLocation TEXTURE3 = new ResourceLocation(PlaySUMod.MODID, "textures/entity/dice3.png");
    public static final ResourceLocation TEXTURE4 = new ResourceLocation(PlaySUMod.MODID, "textures/entity/dice4.png");
    public static final ResourceLocation TEXTURE5 = new ResourceLocation(PlaySUMod.MODID, "textures/entity/dice5.png");
    public static final ResourceLocation TEXTURE6 = new ResourceLocation(PlaySUMod.MODID, "textures/entity/dice6.png");
    public DiceEntityRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new DiceEntityModel(ctx.bakeLayer(DiceEntityModel.LAYER_LOCATION)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(DiceEntity entity) {
        switch (entity.getEntityData().get(entity.getSideData())) {
            case 1:
                return TEXTURE1;
            case 2:
                return TEXTURE2;
            case 3:
                return TEXTURE3;
            case 4:
                return TEXTURE4;
            case 5:
                return TEXTURE5;
            case 6:
                return TEXTURE6;
        }
        return TEXTURE1;
    }

    @Override
    protected boolean shouldShowName(DiceEntity p_115333_) {
        return false;
    }
}

