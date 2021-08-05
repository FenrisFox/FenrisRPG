package com.FenrisFox86.fenris_rpg.common.items;

import com.FenrisFox86.fenris_rpg.common.capabilities.CapabilityReader;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import com.google.common.collect.Multimap;
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
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.TieredItem;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TwoHandedSwordItem extends TieredItem implements IVanishable {
    private final float attackDamage;
    private final Multimap<Attribute, AttributeModifier> attributeModifiers;
    private int cooldown;

    public TwoHandedSwordItem(IItemTier tier, int attackDamageIn, float attackMOVEMENT_SPEEDIn, Properties builderIn) {
        super(tier, builderIn);
        this.attackDamage = (float)attackDamageIn + tier.getAttackDamageBonus();
        Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", (double)this.attackDamage, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", (double)attackMOVEMENT_SPEEDIn, AttributeModifier.Operation.ADDITION));
        this.attributeModifiers = builder.build();
        this.cooldown = (int) -attackMOVEMENT_SPEEDIn*10;
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

    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.hurtAndBreak(1, attacker, (entity) -> {
            attacker.broadcastBreakEvent(Hand.MAIN_HAND);
        });
        if (attacker instanceof PlayerEntity) {
            CapabilityReader.setFenrisState((PlayerEntity) attacker, "offhand_used", 0f);
        }
        return true;
    }

    public boolean mineBlock(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        stack.hurtAndBreak(1, entityLiving, (entity) -> {
            entityLiving.broadcastBreakEvent(Hand.MAIN_HAND);
        });

        return true;
    }

    //Same as swordItem
    public boolean canHarvestBlock(BlockState blockIn) {
        return blockIn.is(Blocks.COBWEB);
    }

    @Override
    public ActionResultType interactLivingEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity target, Hand hand) {
        if (!playerIn.getCooldowns().isOnCooldown(this)) {
            //target.hurt(DamageSource.playerAttack(playerIn), offItem.getAttackDamageBonus());
            stack.getItem().hurtEnemy(stack, target, playerIn);
            if (target.hurt(DamageSource.playerAttack(playerIn), this.getAttackDamageBonus())) {
                this.hurtEnemy(stack, target, playerIn);
            }
            playerIn.getCooldowns().addCooldown(playerIn.getMainHandItem().getItem(),cooldown);
        }
        return ActionResultType.FAIL;
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, PlayerEntity player, Entity entity) {
        if (!player.getCooldowns().isOnCooldown(stack.getItem())){
            player.getCooldowns().addCooldown(player.getMainHandItem().getItem(), cooldown);
            return false;
        }
        return true;
    }

    //display the right animations
    public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if (!playerIn.getCooldowns().isOnCooldown(this)) {
            return ActionResult.success(playerIn.getItemInHand(handIn));
        }
        //return ActionResult.fail(playerIn.getItemInHand(handIn));
        return ActionResult.fail(playerIn.getMainHandItem());
    }

    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType equipmentSlot, ItemStack stack) {
        return equipmentSlot == EquipmentSlotType.MAINHAND ? this.attributeModifiers : super.getAttributeModifiers(equipmentSlot, stack);
    }
}
