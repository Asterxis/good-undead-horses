package com.asterxis.entity;

import java.util.Map;

import com.asterxis.init.EntityInit;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import net.minecraft.client.renderer.entity.AbstractHorseRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.HorseModel;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class NewUndeadHorseRenderer extends AbstractHorseRenderer<AbstractHorseEntity, HorseModel<AbstractHorseEntity>> {
   private static final Map<EntityType<?>, ResourceLocation> UNDEAD_HORSE_TEXTURES = Maps.newHashMap(ImmutableMap.of(EntityInit.ZOMBIE_HORSE, new ResourceLocation("textures/entity/horse/horse_zombie.png"), EntityInit.SKELETON_HORSE, new ResourceLocation("textures/entity/horse/horse_skeleton.png")));

   public NewUndeadHorseRenderer(EntityRendererManager renderManagerIn) {
      super(renderManagerIn, new HorseModel<>(0.0F), 1.1F);
      this.addLayer(new LeatherHorseArmorLayerNew(this));
   }

   /**
    * Returns the location of an entity's texture.
    */
   public ResourceLocation getEntityTexture(AbstractHorseEntity entity) {
      return UNDEAD_HORSE_TEXTURES.get(entity.getType());
   }
}
