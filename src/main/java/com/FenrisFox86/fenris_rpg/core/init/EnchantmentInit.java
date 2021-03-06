package com.FenrisFox86.fenris_rpg.core.init;

import com.FenrisFox86.fenris_rpg.FenrisRPG;
import com.FenrisFox86.fenris_rpg.common.enchantments.*;
import com.FenrisFox86.fenris_rpg.common.enchantments.logic.MagmaWalkerLogic;
import com.google.common.collect.Iterables;
import it.unimi.dsi.fastutil.objects.ObjectCollection;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Predicate;

@Mod.EventBusSubscriber
public class EnchantmentInit {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, FenrisRPG.MOD_ID);

    public static void EnchantmentInit() {
        ENCHANTMENTS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static final EnchantmentType AXE_TYPE = EnchantmentType.create("axe", new Predicate<Item>() {
        @Override
        public boolean test(Item item) {
            return item instanceof AxeItem;
        }
    });

    //Enchantments
    public static final RegistryObject<Enchantment> DYNAMO_REPAIR = ENCHANTMENTS.register("dynamo_repair", ()
            -> new DynamoRepair(
            Enchantment.Rarity.VERY_RARE,
            EnchantmentType.BREAKABLE,
            new EquipmentSlotType[]{
                    EquipmentSlotType.MAINHAND,
                    EquipmentSlotType.FEET,
                    EquipmentSlotType.LEGS,
                    EquipmentSlotType.CHEST,
                    EquipmentSlotType.HEAD,
                    EquipmentSlotType.OFFHAND
            }));

    public static final RegistryObject<Enchantment> VAMPIRIC_REPAIR = ENCHANTMENTS.register("vampiric_repair", ()
            -> new VampiricRepair(
            Enchantment.Rarity.VERY_RARE,
            EnchantmentType.BREAKABLE,
            new EquipmentSlotType[]{
                    EquipmentSlotType.MAINHAND,
                    EquipmentSlotType.FEET,
                    EquipmentSlotType.LEGS,
                    EquipmentSlotType.CHEST,
                    EquipmentSlotType.HEAD,
                    EquipmentSlotType.OFFHAND
            }));

    public static final RegistryObject<Enchantment> MAGMA_WALKER = ENCHANTMENTS.register("magma_walker", ()
            -> new MagmaWalker(
            Enchantment.Rarity.RARE,
            EnchantmentType.ARMOR_FEET,
            new EquipmentSlotType[]{
                    EquipmentSlotType.FEET
            }));

    public static final RegistryObject<Enchantment> SPECTRAL = ENCHANTMENTS.register("spectral", ()
            -> new Spectral(
            Enchantment.Rarity.UNCOMMON,
            EnchantmentType.WEAPON,
            new EquipmentSlotType[]{
                    EquipmentSlotType.MAINHAND
            }));

    public static final RegistryObject<Enchantment> SMELTING = ENCHANTMENTS.register("smelting", ()
            -> new Smelting(
            Enchantment.Rarity.RARE,
            EnchantmentType.DIGGER,
            new EquipmentSlotType[]{
                    EquipmentSlotType.MAINHAND
            }));

    public static final RegistryObject<Enchantment> CRUSHING = ENCHANTMENTS.register("crushing", ()
            -> new Crushing(
            Enchantment.Rarity.RARE,
            EnchantmentType.DIGGER,
            new EquipmentSlotType[]{
                    EquipmentSlotType.MAINHAND
            }));

    public static final RegistryObject<Enchantment> LUMBERJACK = ENCHANTMENTS.register("lumberjack", ()
            -> new Lumberjack(
            Enchantment.Rarity.UNCOMMON,
            AXE_TYPE,
            new EquipmentSlotType[]{
                    EquipmentSlotType.MAINHAND
            }));

    public static final RegistryObject<Enchantment> LEVITATION = ENCHANTMENTS.register("levitation", ()
            -> new Levitation(
            Enchantment.Rarity.UNCOMMON,
            EnchantmentType.WEAPON,
            new EquipmentSlotType[]{
                    EquipmentSlotType.MAINHAND
            }));

    public static final RegistryObject<Enchantment> VAMPIRIC = ENCHANTMENTS.register("vampiric", ()
            -> new Vampiric(
            Enchantment.Rarity.RARE,
            EnchantmentType.WEAPON,
            new EquipmentSlotType[]{
                    EquipmentSlotType.MAINHAND
            }));

    public static final RegistryObject<Enchantment> DARK_CONTRACT = ENCHANTMENTS.register("dark_contract", ()
            -> new DarkContract(
            Enchantment.Rarity.RARE,
            EnchantmentType.WEAPON,
            new EquipmentSlotType[]{
                    EquipmentSlotType.MAINHAND
            }));

    //Effects
    @SubscribeEvent
    public static void OnUpdate(LivingEvent.LivingUpdateEvent event) {
        LivingEntity living = event.getEntityLiving();
        World world = event.getEntity().level;
        Iterable<ItemStack> equipment = living.getAllSlots();

        //Magma Walker
        for(ItemStack stack: equipment) {
            if (EnchantmentHelper.getItemEnchantmentLevel(EnchantmentInit.MAGMA_WALKER.get(), stack) > 0) {
                MagmaWalkerLogic.replaceField(BlockInit.MAGMA_FLOOR.get().defaultBlockState(), living.blockPosition().below(), world, 2, 1);
            }
        }


        if(world.getDayTime()%20 == 0) {

            //Dynamo
            if(living.isSprinting()) {
                for(ItemStack stack: equipment) {
                    int level = EnchantmentHelper.getItemEnchantmentLevel(DYNAMO_REPAIR.get(), stack);
                    if(level > 0) {
                        stack.hurtAndBreak((int) -Math.pow(2, level-1), living, p ->
                                p.broadcastBreakEvent(stack.getEquipmentSlot()));
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void OnHurtEntity(LivingHurtEvent event) throws Throwable {
        Entity entity = event.getSource().getEntity();
        if(entity instanceof LivingEntity) {

            LivingEntity living = (LivingEntity) entity;
            LivingEntity target = event.getEntityLiving();

            Hand hand = Hand.MAIN_HAND;
            ItemStack stack = living.getItemInHand(hand);

            //Spectral
            int level = EnchantmentHelper.getItemEnchantmentLevel(SPECTRAL.get(), stack);
            if(level > 0) {
                target.addEffect(new EffectInstance(Effects.GLOWING, level * 100));
            }

            //Vampiric Repair
            Iterable<ItemStack> equipment = living.getAllSlots();

            for (ItemStack equippedStack: equipment) {
                level = EnchantmentHelper.getItemEnchantmentLevel(VAMPIRIC_REPAIR.get(), equippedStack);
                if(level > 0) {
                    stack.hurtAndBreak((int) (-(event.getAmount()/3)*level), living, p ->
                            p.broadcastBreakEvent(Objects.requireNonNull(equippedStack.getEquipmentSlot())));
                }
            }

            //Levitation
            level = EnchantmentHelper.getItemEnchantmentLevel(LEVITATION.get(), stack);
            if(level > 0) {
                target.addEffect(new EffectInstance(Effects.LEVITATION, level * 100));
            }

            //Dark Contract
            level = EnchantmentHelper.getItemEnchantmentLevel(DARK_CONTRACT.get(), stack);
            if(level > 0) {
                target.hurt(DamageSource.GENERIC, level*2);
                living.hurt(DamageSource.indirectMagic(living, null), level);
            }

            //Vampiric
            level = EnchantmentHelper.getItemEnchantmentLevel(VAMPIRIC.get(), stack);
            float regen = (event.getAmount()/5.0f)*level;
            if(level > 0) {
                if (living instanceof PlayerEntity) {
                    PlayerEntity player = (PlayerEntity) living;
                    int saturation = player.getFoodData().getFoodLevel();
                    int hunger = Math.max(20 - saturation, 0);
                    float hunger_regen = Math.min(regen, hunger);
                    player.getFoodData().setFoodLevel((int) (player.getFoodData().getFoodLevel()+hunger_regen));
                    player.heal(Math.max(regen-hunger_regen, 0));
                } else {
                    living.heal(regen);
                }
            }

            if (event.getSource().getEntity() instanceof PlayerEntity) {

            }
        }
    }
}
