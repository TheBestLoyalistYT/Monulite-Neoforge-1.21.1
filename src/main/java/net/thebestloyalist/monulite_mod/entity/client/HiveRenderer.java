package net.thebestloyalist.monulite_mod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.thebestloyalist.monulite_mod.MonuliteMod;
import net.thebestloyalist.monulite_mod.entity.custom.HiveEntity;

public class HiveRenderer extends MobRenderer<HiveEntity, HiveModel<HiveEntity>> {
    public HiveRenderer(EntityRendererProvider.Context context) {
        super(context, new HiveModel<>(context.bakeLayer(HiveModel.LAYER_LOCATION)), 0.25f);
    }

    @Override
    public ResourceLocation getTextureLocation(HiveEntity entity) {
        return ResourceLocation.fromNamespaceAndPath(MonuliteMod.MOD_ID, "textures/entity/hive/hive.png");
    }

    @Override
    public void render(HiveEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        if(entity.isBaby()) {
            poseStack.scale(0.45f, 0.45f, 0.45f);
        } else {
            poseStack.scale(1f, 1f, 1f);
        }

        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }
}