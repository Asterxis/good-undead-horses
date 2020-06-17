package com.asterxis.entity.ai.goal;

import com.asterxis.entity.SkeletonHorseEntityNew;
import com.asterxis.entity.ZombieHorseEntityNew;
import com.asterxis.init.EntityInit;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.monster.SkeletonEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.server.ServerWorld;

public class TriggerNewSkeletonTrapGoal extends Goal {
	private final SkeletonHorseEntityNew horse;

	   public TriggerNewSkeletonTrapGoal(SkeletonHorseEntityNew horseIn) {
	      this.horse = horseIn;
	   }

	   /**
	    * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
	    * method as well.
	    */
	   public boolean shouldExecute() {
	      return this.horse.world.isPlayerWithin(this.horse.getPosX(), this.horse.getPosY(), this.horse.getPosZ(), 10.0D);
	   }

	   /**
	    * Keep ticking a continuous task that has already been started
	    */
	   public void tick() {
	      DifficultyInstance difficultyinstance = this.horse.world.getDifficultyForLocation(new BlockPos(this.horse));
	      this.horse.setTrap(false);
	      this.horse.setHorseTamed(true);
	      this.horse.setGrowingAge(0);
	      ((ServerWorld)this.horse.world).addLightningBolt(new LightningBoltEntity(this.horse.world, this.horse.getPosX(), this.horse.getPosY(), this.horse.getPosZ(), true));
	      SkeletonEntity skeletonentity = this.createSkeleton(difficultyinstance, this.horse);
	      skeletonentity.startRiding(this.horse);
	      
	      AbstractHorseEntity otherSkeletonHorse = this.createHorse(difficultyinstance);
	      SkeletonEntity skeletonentity1 = this.createSkeleton(difficultyinstance, otherSkeletonHorse);
	      skeletonentity1.startRiding(otherSkeletonHorse);
	      otherSkeletonHorse.addVelocity(this.horse.getRNG().nextGaussian() * 0.5D, 0.0D, this.horse.getRNG().nextGaussian() * 0.5D);
	      
	      for(int i = 0; i < 2; ++i) {
	    	  AbstractHorseEntity zombieHorseEntity = this.createZHorse(difficultyinstance);
	    	  ZombieEntity zombieentity1 = this.createZombie(difficultyinstance, zombieHorseEntity);
	    	  zombieentity1.startRiding(zombieHorseEntity);
	    	  zombieHorseEntity.addVelocity(this.horse.getRNG().nextGaussian() * 0.5D, 0.0D, this.horse.getRNG().nextGaussian() * 0.5D);
		  }

	   }
	   /**
	    * Skeleton Horseman
	    */
	   private AbstractHorseEntity createHorse(DifficultyInstance p_188515_1_) {
	      SkeletonHorseEntityNew skeletonhorseentity = EntityInit.SKELETON_HORSE.create(this.horse.world);
	      skeletonhorseentity.onInitialSpawn(this.horse.world, p_188515_1_, SpawnReason.TRIGGERED, (ILivingEntityData)null, (CompoundNBT)null);
	      skeletonhorseentity.setPosition(this.horse.getPosX(), this.horse.getPosY(), this.horse.getPosZ());
	      skeletonhorseentity.hurtResistantTime = 60;
	      skeletonhorseentity.enablePersistence();
	      skeletonhorseentity.setHorseTamed(true);
	      skeletonhorseentity.setGrowingAge(0);
	      skeletonhorseentity.world.addEntity(skeletonhorseentity);
	      return skeletonhorseentity;
	   }

	   private SkeletonEntity createSkeleton(DifficultyInstance p_188514_1_, AbstractHorseEntity p_188514_2_) {
	      SkeletonEntity skeletonentity = EntityType.SKELETON.create(p_188514_2_.world);
	      skeletonentity.onInitialSpawn(p_188514_2_.world, p_188514_1_, SpawnReason.TRIGGERED, (ILivingEntityData)null, (CompoundNBT)null);
	      skeletonentity.setPosition(p_188514_2_.getPosX(), p_188514_2_.getPosY(), p_188514_2_.getPosZ());
	      skeletonentity.hurtResistantTime = 60;
	      skeletonentity.enablePersistence();
	      skeletonentity.isChild();
	      if (skeletonentity.getItemStackFromSlot(EquipmentSlotType.HEAD).isEmpty()) {
	         skeletonentity.setItemStackToSlot(EquipmentSlotType.HEAD, new ItemStack(Items.CHAINMAIL_HELMET));
	         if(p_188514_2_.world.getDifficulty().getId() >= 2){
	        	 skeletonentity.setItemStackToSlot(EquipmentSlotType.CHEST, new ItemStack(Items.CHAINMAIL_CHESTPLATE));
	    		  if(p_188514_2_.world.getDifficulty().getId() == 3){
	    			  skeletonentity.setItemStackToSlot(EquipmentSlotType.LEGS, new ItemStack(Items.CHAINMAIL_LEGGINGS));
	    			  skeletonentity.setItemStackToSlot(EquipmentSlotType.FEET, new ItemStack(Items.CHAINMAIL_BOOTS));
	    			  skeletonentity.setItemStackToSlot(EquipmentSlotType.LEGS, EnchantmentHelper.addRandomEnchantment(skeletonentity.getRNG(), skeletonentity.getItemStackFromSlot(EquipmentSlotType.LEGS), (int)(5.0F + p_188514_1_.getClampedAdditionalDifficulty() * (float)skeletonentity.getRNG().nextInt(18)), false));
	    			  skeletonentity.setItemStackToSlot(EquipmentSlotType.FEET, EnchantmentHelper.addRandomEnchantment(skeletonentity.getRNG(), skeletonentity.getItemStackFromSlot(EquipmentSlotType.FEET), (int)(5.0F + p_188514_1_.getClampedAdditionalDifficulty() * (float)skeletonentity.getRNG().nextInt(18)), false));
		    	  }
	    		  skeletonentity.setItemStackToSlot(EquipmentSlotType.CHEST, EnchantmentHelper.addRandomEnchantment(skeletonentity.getRNG(), skeletonentity.getItemStackFromSlot(EquipmentSlotType.CHEST), (int)(5.0F + p_188514_1_.getClampedAdditionalDifficulty() * (float)skeletonentity.getRNG().nextInt(18)), false));
	    	  }
	      }

	      skeletonentity.setItemStackToSlot(EquipmentSlotType.MAINHAND, EnchantmentHelper.addRandomEnchantment(skeletonentity.getRNG(), skeletonentity.getHeldItemMainhand(), (int)(5.0F + p_188514_1_.getClampedAdditionalDifficulty() * (float)skeletonentity.getRNG().nextInt(18)), false));
	      skeletonentity.setItemStackToSlot(EquipmentSlotType.HEAD, EnchantmentHelper.addRandomEnchantment(skeletonentity.getRNG(), skeletonentity.getItemStackFromSlot(EquipmentSlotType.HEAD), (int)(5.0F + p_188514_1_.getClampedAdditionalDifficulty() * (float)skeletonentity.getRNG().nextInt(18)), false));
	      skeletonentity.world.addEntity(skeletonentity);
	      return skeletonentity;
	   }
	   /**
	    * Zombie Horseman
	    */
	   private AbstractHorseEntity createZHorse(DifficultyInstance p_188515_1_) {
		      ZombieHorseEntityNew zombieHorseEntity = EntityInit.ZOMBIE_HORSE.create(this.horse.world);
		      zombieHorseEntity.onInitialSpawn(this.horse.world, p_188515_1_, SpawnReason.TRIGGERED, (ILivingEntityData)null, (CompoundNBT)null);
		      zombieHorseEntity.setPosition(this.horse.getPosX(), this.horse.getPosY(), this.horse.getPosZ());
		      zombieHorseEntity.hurtResistantTime = 60;
		      zombieHorseEntity.enablePersistence();
		      zombieHorseEntity.setHorseTamed(true);
		      zombieHorseEntity.setGrowingAge(0);
		      zombieHorseEntity.world.addEntity(zombieHorseEntity);
		      return zombieHorseEntity;
		   }

		   private ZombieEntity createZombie(DifficultyInstance p_188514_1_, AbstractHorseEntity p_188514_2_) {
		      ZombieEntity zombieentity = EntityType.ZOMBIE.create(p_188514_2_.world);
		      zombieentity.onInitialSpawn(p_188514_2_.world, p_188514_1_, SpawnReason.TRIGGERED, (ILivingEntityData)null, (CompoundNBT)null);
		      zombieentity.setPosition(p_188514_2_.getPosX(), p_188514_2_.getPosY(), p_188514_2_.getPosZ());
		      zombieentity.hurtResistantTime = 60;
		      zombieentity.enablePersistence();
		      zombieentity.setChild(false);
		      if (zombieentity.getItemStackFromSlot(EquipmentSlotType.HEAD).isEmpty()) {
		    	  zombieentity.setItemStackToSlot(EquipmentSlotType.HEAD, new ItemStack(Items.CHAINMAIL_HELMET));
		    	  zombieentity.setItemStackToSlot(EquipmentSlotType.MAINHAND, new ItemStack(Items.IRON_SWORD));
		    	  if(p_188514_2_.world.getDifficulty().getId() >= 2){
		    		  zombieentity.setItemStackToSlot(EquipmentSlotType.CHEST, new ItemStack(Items.CHAINMAIL_CHESTPLATE));
		    		  if(p_188514_2_.world.getDifficulty().getId() == 3){
		    			  zombieentity.setItemStackToSlot(EquipmentSlotType.LEGS, new ItemStack(Items.CHAINMAIL_LEGGINGS));
				    	  zombieentity.setItemStackToSlot(EquipmentSlotType.FEET, new ItemStack(Items.CHAINMAIL_BOOTS));
				    	  zombieentity.setItemStackToSlot(EquipmentSlotType.LEGS, EnchantmentHelper.addRandomEnchantment(zombieentity.getRNG(), zombieentity.getItemStackFromSlot(EquipmentSlotType.LEGS), (int)(5.0F + p_188514_1_.getClampedAdditionalDifficulty() * (float)zombieentity.getRNG().nextInt(18)), false));
					      zombieentity.setItemStackToSlot(EquipmentSlotType.FEET, EnchantmentHelper.addRandomEnchantment(zombieentity.getRNG(), zombieentity.getItemStackFromSlot(EquipmentSlotType.FEET), (int)(5.0F + p_188514_1_.getClampedAdditionalDifficulty() * (float)zombieentity.getRNG().nextInt(18)), false));
			    	  }
		    		  zombieentity.setItemStackToSlot(EquipmentSlotType.CHEST, EnchantmentHelper.addRandomEnchantment(zombieentity.getRNG(), zombieentity.getItemStackFromSlot(EquipmentSlotType.CHEST), (int)(5.0F + p_188514_1_.getClampedAdditionalDifficulty() * (float)zombieentity.getRNG().nextInt(18)), false));
		    	  }
		      }

		      zombieentity.setItemStackToSlot(EquipmentSlotType.MAINHAND, EnchantmentHelper.addRandomEnchantment(zombieentity.getRNG(), zombieentity.getHeldItemMainhand(), (int)(5.0F + p_188514_1_.getClampedAdditionalDifficulty() * (float)zombieentity.getRNG().nextInt(18)), false));
		      zombieentity.setItemStackToSlot(EquipmentSlotType.HEAD, EnchantmentHelper.addRandomEnchantment(zombieentity.getRNG(), zombieentity.getItemStackFromSlot(EquipmentSlotType.HEAD), (int)(5.0F + p_188514_1_.getClampedAdditionalDifficulty() * (float)zombieentity.getRNG().nextInt(18)), false));
		      
		      
		      zombieentity.world.addEntity(zombieentity);
		      return zombieentity;
		   }

}
