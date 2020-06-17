package com.asterxis.init;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ObjectHolder;

public class ItemInit {
	public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS,"undead_horse_re");
	@ObjectHolder("minecraft:zombie_horse_spawn_egg")
	public static final Item ZOMBIE_HORSE_SPAWN_EGG = register("zombie_horse_spawn_egg", new SpawnEggItem(EntityInit.ZOMBIE_HORSE, 3232308, 9945732, (new Item.Properties()).group(ItemGroup.MISC)));
	
	@ObjectHolder("minecraft:skeleton_horse_spawn_egg")
	public static final Item SKELETON_HORSE_SPAWN_EGG = register("skeleton_horse_spawn_egg", new SpawnEggItem(EntityInit.SKELETON_HORSE, 6842447, 15066584, (new Item.Properties()).group(ItemGroup.MISC)));
    
	
	private static Item register(ResourceLocation key, Item itemIn) {
	      return Registry.register(Registry.ITEM, key, itemIn);
	   }
	
	private static Item register(String key, Item itemIn) {
	      return register(new ResourceLocation(key), itemIn);
	   }
	
}
