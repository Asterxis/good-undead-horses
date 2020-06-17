package com.asterxis.init;

import com.asterxis.entity.SkeletonHorseEntityNew;
import com.asterxis.entity.ZombieHorseEntityNew;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ObjectHolder;

public class EntityInit {
	
	public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = new DeferredRegister<>(ForgeRegistries.ENTITIES,"undead_horse_re");
	
	@ObjectHolder("minecraft:zombie_horse")
	public static final EntityType<ZombieHorseEntityNew> ZOMBIE_HORSE = register("zombie_horse", EntityType.Builder.create(ZombieHorseEntityNew::new, EntityClassification.CREATURE).size(1.3964844F, 1.6F));
	
	@ObjectHolder("minecraft:skeleton_horse")
	public static final EntityType<SkeletonHorseEntityNew> SKELETON_HORSE = register("skeleton_horse", EntityType.Builder.create(SkeletonHorseEntityNew::new, EntityClassification.CREATURE).size(1.3964844F, 1.6F));
	
	private static <T extends Entity> EntityType<T> register(String key, EntityType.Builder<T> builder) {
	      return Registry.register(Registry.ENTITY_TYPE, key, builder.build(key));
	   }

}
