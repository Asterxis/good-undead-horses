package com.asterxis.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.HorseModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.item.DyeableHorseArmorItem;
import net.minecraft.item.HorseArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LeatherHorseArmorLayerNew extends LayerRenderer<AbstractHorseEntity, HorseModel<AbstractHorseEntity>> {
   private final HorseModel<AbstractHorseEntity> field_215341_a = new HorseModel<>(0.1F);

   public LeatherHorseArmorLayerNew(IEntityRenderer<AbstractHorseEntity, HorseModel<AbstractHorseEntity>> p_i50937_1_) {
      super(p_i50937_1_);
   }

   @Override
	public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, AbstractHorseEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
	   ItemStack itemstack = null;
	   if(entitylivingbaseIn instanceof SkeletonHorseEntityNew){itemstack = ((SkeletonHorseEntityNew) entitylivingbaseIn).func_213803_dV();}
	   else if(entitylivingbaseIn instanceof ZombieHorseEntityNew){itemstack = ((ZombieHorseEntityNew) entitylivingbaseIn).func_213803_dV();}
	   if (itemstack.getItem() instanceof HorseArmorItem) {
       HorseArmorItem horsearmoritem = (HorseArmorItem)itemstack.getItem();
       this.getEntityModel().copyModelAttributesTo(this.field_215341_a);
       this.field_215341_a.setLivingAnimations(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks);
       this.field_215341_a.setRotationAngles(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
       float f;
       float f1;
       float f2;
       if (horsearmoritem instanceof DyeableHorseArmorItem) {
          int i = ((DyeableHorseArmorItem)horsearmoritem).getColor(itemstack);
          f = (float)(i >> 16 & 255) / 255.0F;
          f1 = (float)(i >> 8 & 255) / 255.0F;
          f2 = (float)(i & 255) / 255.0F;
       } else {
          f = 1.0F;
          f1 = 1.0F;
          f2 = 1.0F;
       }

       IVertexBuilder ivertexbuilder = bufferIn.getBuffer(RenderType.getEntityCutoutNoCull(horsearmoritem.func_219976_d()));
       this.field_215341_a.render(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, f, f1, f2, 1.0F);
    }
}

}
