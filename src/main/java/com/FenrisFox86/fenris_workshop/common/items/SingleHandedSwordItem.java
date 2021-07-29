package com.FenrisFox86.fenris_workshop.common.items;

import com.FenrisFox86.fenris_workshop.common.capabilities.CapabilityReader;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.IVanishable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SingleHandedSwordItem extends TieredItem implements IVanishable {
    private final float attackDamage;
    private final Multimap<Attribute, AttributeModifier> attributeModifiers;

    public SingleHandedSwordItem(IItemTier tier, int attackDamageIn, float attackMOVEMENT_SPEEDIn, Item.Properties builderIn) {
        super(tier, builderIn);
        this.attackDamage = (float)attackDamageIn + tier.getAttackDamageBonus();
        Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", (double)this.attackDamage, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", (double)attackMOVEMENT_SPEEDIn, AttributeModifier.Operation.ADDITION));
        this.attributeModifiers = builder.build();
    }

    @Override
    public UseAction getUseAnimation(ItemStack stack) {
        return UseAction.NONE;
    }

    //Same as swordItem
    public float getAttackDamageBonus() {
        return this.attackDamage;
    }

    //Same as swordItem
    public boolean canPlayerBreakBlockWhileHolding(BlockState state, World worldIn, BlockPos pos, PlayerEntity player) {
        return !player.isCreative();
    }

    //Can only destroy cobwebs faster
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        if (state.is(Blocks.COBWEB)) {
            return 15.0F;
        } else {
            return 0.5F;
        }
    }

    //Will now send the break animation for the hand the stack is in
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.hurtAndBreak(1, attacker, livingEntity -> {livingEntity.broadcastBreakEvent(Hand.MAIN_HAND);});
        return true;
    }

    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker, Hand hand) {
        stack.hurtAndBreak(1, attacker, (entity) -> {
            attacker.broadcastBreakEvent(hand);
        });
        return true;
    }

    //Damages Dagger more than swords. Double wield compatibility included.
    public boolean mineBlock(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        stack.hurtAndBreak(3, entityLiving, (entity) -> {
            if (entityLiving.getItemInHand(Hand.OFF_HAND) == stack) {
                entityLiving.broadcastBreakEvent(Hand.OFF_HAND);
            } else {
                entityLiving.broadcastBreakEvent(Hand.MAIN_HAND);
            }
        });

        return true;
    }

    //Same as swordItem
    public boolean canHarvestBlock(BlockState blockIn) {
        return blockIn.is(Blocks.COBWEB);
    }

    //Allows double wielding
    @Override
    public ActionResultType interactLivingEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity target, Hand hand) {
        ItemStack offStack = playerIn.getItemInHand(Hand.OFF_HAND);
        if (offStack.getItem() instanceof SingleHandedSwordItem) {
            SingleHandedSwordItem offItem = (SingleHandedSwordItem) offStack.getItem();

            if (offStack == stack) {
                CapabilityReader.setFenrisState((PlayerEntity) playerIn, "offhand_used", 1f);

                target.hurt(DamageSource.playerAttack(playerIn), offItem.getAttackDamageBonus());
                offItem.hurtEnemy(stack, target, playerIn, hand);

                return ActionResultType.SUCCESS;
            } else {
                offItem.interactLivingEntity(offStack, playerIn, target, Hand.OFF_HAND);
            }
        }
        return ActionResultType.FAIL;
    }

    //display the right animations
    public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {

        if (handIn == Hand.OFF_HAND) {
            CapabilityReader.setFenrisState(playerIn, "offhand_used", 1f);
            return ActionResult.success(playerIn.getOffhandItem());
        }
        return ActionResult.fail(playerIn.getMainHandItem());
    }

    //Double wield expansion
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType equipmentSlot, ItemStack stack) {
        return equipmentSlot == EquipmentSlotType.MAINHAND || equipmentSlot == EquipmentSlotType.OFFHAND ? this.attributeModifiers : super.getAttributeModifiers(equipmentSlot, stack);
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, PlayerEntity player, Entity entity) {
        if (stack == player.getMainHandItem()) {
            CapabilityReader.setFenrisState(player, "offhand_used", 0f);
            return false;
        }
        return false;
    }
}
