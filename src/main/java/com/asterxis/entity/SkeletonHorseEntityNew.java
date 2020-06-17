package com.asterxis.entity;

import java.util.UUID;

import javax.annotation.Nullable;

import com.asterxis.entity.ai.goal.TriggerNewSkeletonTrapGoal;
import com.asterxis.init.EntityInit;

import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.passive.horse.SkeletonHorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.HorseArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class SkeletonHorseEntityNew extends SkeletonHorseEntity{
	private final TriggerNewSkeletonTrapGoal skeletonTrapAI = new TriggerNewSkeletonTrapGoal(this);
	private boolean skeletonTrap;
	private static final UUID ARMOR_MODIFIER_UUID = UUID.fromString("556E1665-8B10-40C8-8F9D-CF9B1667F295");

	public SkeletonHorseEntityNew(EntityType<? extends SkeletonHorseEntity> p_i50235_1_, World p_i50235_2_) {
		super(p_i50235_1_, p_i50235_2_);
	}
	
	@Override
	public void writeAdditional(CompoundNBT compound) {
	      super.writeAdditional(compound);
	      //compound.putBoolean("SkeletonTrap", this.isTrap());
	      //compound.putInt("SkeletonTrapTime", this.skeletonTrapTime);
	      if (!this.horseChest.getStackInSlot(1).isEmpty()) {
	         compound.put("ArmorItem", this.horseChest.getStackInSlot(1).write(new CompoundNBT()));
	      }

	}
	
	@Override
	public boolean canBeLeashedTo(PlayerEntity player) {
	      return super.canBeLeashedTo(player);
	   }
	
	@Override
	public void readAdditional(CompoundNBT compound) {
		super.readAdditional(compound);
		//this.setTrap(compound.getBoolean("SkeletonTrap"));
	    //this.skeletonTrapTime = compound.getInt("SkeletonTrapTime");
	    if (compound.contains("ArmorItem", 10)) {
	    	ItemStack itemstack = ItemStack.read(compound.getCompound("ArmorItem"));
	        if (!itemstack.isEmpty() && this.isArmor(itemstack)) {
	        	this.horseChest.setInventorySlotContents(1, itemstack);
	        }
	    }

	    this.updateHorseSlots();
	}
	
	@Override
	protected void updateHorseSlots() {
	      super.updateHorseSlots();
	      this.func_213804_l(this.horseChest.getStackInSlot(1));
	      this.setDropChance(EquipmentSlotType.CHEST, 0.0F);
	}
	@Override
	public void setTrap(boolean trap) {
	      if (trap != this.skeletonTrap) {
	         this.skeletonTrap = trap;
	         if (trap) {
	            this.goalSelector.addGoal(1, this.skeletonTrapAI);
	         } else {
	            this.goalSelector.removeGoal(this.skeletonTrapAI);
	         }

	      }
	   }

	@Override @Nullable
	   public AgeableEntity createChild(AgeableEntity ageable) {
	      return EntityInit.SKELETON_HORSE.create(this.world);
	   }
	
	private void func_213804_l(ItemStack p_213804_1_) {
	      this.func_213805_k(p_213804_1_);
	      if (!this.world.isRemote) {
	         this.getAttribute(SharedMonsterAttributes.ARMOR).removeModifier(ARMOR_MODIFIER_UUID);
	         if (this.isArmor(p_213804_1_)) {
	            int i = ((HorseArmorItem)p_213804_1_.getItem()).func_219977_e();
	            if (i != 0) {
	               this.getAttribute(SharedMonsterAttributes.ARMOR).applyModifier((new AttributeModifier(ARMOR_MODIFIER_UUID, "Horse armor bonus", (double)i, AttributeModifier.Operation.ADDITION)).setSaved(false));
	            }
	         }
	      }

	   }

	   /**
	    * Called by InventoryBasic.onInventoryChanged() on a array that is never filled.
	    */
	   public void onInventoryChanged(IInventory invBasic) {
	      ItemStack itemstack = this.func_213803_dV();
	      super.onInventoryChanged(invBasic);
	      ItemStack itemstack1 = this.func_213803_dV();
	      if (this.ticksExisted > 20 && this.isArmor(itemstack1) && itemstack != itemstack1) {
	         this.playSound(SoundEvents.ENTITY_HORSE_ARMOR, 0.5F, 1.0F);
	      }

	   }
	   
	   private void func_213805_k(ItemStack p_213805_1_) {
		      this.setItemStackToSlot(EquipmentSlotType.CHEST, p_213805_1_);
		      this.setDropChance(EquipmentSlotType.CHEST, 0.0F);
		   }
	   
	   public ItemStack func_213803_dV() {
		      return this.getItemStackFromSlot(EquipmentSlotType.CHEST);
		   }
	   
	   @Override
	   public void tick(){
		   super.tick();
		   ItemStack stack = this.horseChest.getStackInSlot(1);
		   if (isArmor(stack)) stack.onHorseArmorTick(world, this);
	   }
	   
	   public boolean wearsArmor() {
		      return true;
	   }
	   public boolean isArmor(ItemStack stack) {
		   return stack.getItem() instanceof HorseArmorItem;
	   }
	   
	   @Override
	   public boolean processInteract(PlayerEntity player, Hand hand) {
		      ItemStack itemstack = player.getHeldItem(hand);
		      boolean flag = !itemstack.isEmpty();
		      if (flag && itemstack.getItem() instanceof SpawnEggItem) {
		         return super.processInteract(player, hand);
		      } else {
		         if (!this.isChild()) {
		            if (this.isTame() && player.isSecondaryUseActive()) {
		               this.openGUI(player);
		               return true;
		            }

		            if (this.isBeingRidden()) {
		               return super.processInteract(player, hand);
		            }
		         }

		         if (flag) {
		            if (this.handleEating(player, itemstack)) {
		               if (!player.abilities.isCreativeMode) {
		                  itemstack.shrink(1);
		               }

		               return true;
		            }

		            if (itemstack.interactWithEntity(player, this, hand)) {
		               return true;
		            }

		            if (!this.isTame()) {
		               this.makeMad();
		               return true;
		            }

		            boolean flag1 = !this.isChild() && !this.isHorseSaddled() && itemstack.getItem() == Items.SADDLE;
		            if (this.isArmor(itemstack) || flag1) {
		               this.openGUI(player);
		               return true;
		            }
		         }

		         if (this.isChild()) {
		            return super.processInteract(player, hand);
		         } else {
		            this.mountTo(player);
		            return true;
		         }
		      }
		   }
}
