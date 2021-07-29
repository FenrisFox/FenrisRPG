package com.FenrisFox86.fenrisrpg.core.init;

import com.FenrisFox86.fenrisrpg.FenrisRPG;
import com.FenrisFox86.fenrisrpg.common.blocks.BlockItemBase;
import com.FenrisFox86.fenrisrpg.common.items.*;
import com.FenrisFox86.fenrisrpg.core.util.tools.ModArmorMaterial;
import com.FenrisFox86.fenrisrpg.core.util.tools.ModItemTier;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class ItemInit {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, FenrisRPG.MOD_ID);
    public static Map<String, Item> ITEM_MAP = new HashMap<>();

    static Item.Properties ItemTabProps() {
        return new Item.Properties().tab(FenrisRPG.MOD_TAB);
    }

    public static RegistryObject<Item> addItem(String name) {
        Item item = new Item(ItemTabProps());
        ITEM_MAP.put(name, item);
        return ITEMS.register(name, () -> item);
    }

    public static RegistryObject<Item> addItem(String name, Item item) {
        ITEM_MAP.put(name, item);
        return ITEMS.register(name, () -> item);
    }

    public static Map<String, RegistryObject<Item>> addToolSet(String name, IItemTier tier) {
        Map<String, RegistryObject<Item>> MAP = new HashMap() {};
        MAP.put("SWORD", addItem(name + "_sword", new SwordItem(
                tier, 2, -1.0F, new Item.Properties().tab(FenrisRPG.MOD_TAB))));
        MAP.put("AXE", addItem(name + "_axe", new AxeItem(
                tier, 3, -2.0F, new Item.Properties().tab(FenrisRPG.MOD_TAB))));
        MAP.put("PICKAXE", addItem(name + "_pickaxe", new PickaxeItem(
                tier, 2, -2.0F, new Item.Properties().tab(FenrisRPG.MOD_TAB))));
        MAP.put("SHOVEL", addItem(name + "_shovel", new ShovelItem(
                tier, 1, -2.0F, new Item.Properties().tab(FenrisRPG.MOD_TAB))));
        MAP.put("HOE", addItem(name + "_hoe", new HoeItem(
                tier, 0, -2.0F, new Item.Properties().tab(FenrisRPG.MOD_TAB))));
        MAP = completeToolSet(name, tier, MAP);

        return MAP;
    }

    public static Map<String, RegistryObject<Item>> completeToolSet(String name, IItemTier tier, @Nullable Map map) {
        Map<String, RegistryObject<Item>> MAP = map;
        if (map == null) { MAP = new HashMap() {}; }
        MAP.put("HAMMER", addItem(name+ "_hammer", new HammerItem(
                tier, 4, -1.5F, new Item.Properties().tab(FenrisRPG.MOD_TAB))));
        MAP.put("DAGGER", addItem(name+ "_dagger", new SingleHandedSwordItem(
                tier, 0, -1.0F, new Item.Properties().tab(FenrisRPG.MOD_TAB))));
        MAP.put("KATANA", addItem(name+ "_katana", new SingleHandedSwordItem(
                tier, 2, -1.5F, new Item.Properties().tab(FenrisRPG.MOD_TAB))));
        MAP.put("BROADSWORD", addItem(name+ "_broadsword", new BroadswordItem(
                tier, 6, -3.0F, new Item.Properties().tab(FenrisRPG.MOD_TAB))));

        return map;
    }

    public static Map<String, RegistryObject<Item>> addArmorSet(String name, IArmorMaterial material) {
        Map<String, RegistryObject<Item>> MAP = new HashMap() {};
        MAP.put("HELMET", addItem(name + "_helmet", new ArmorItem(
                material, EquipmentSlotType.HEAD, new Item.Properties().tab(FenrisRPG.MOD_TAB))));
        MAP.put("CHESTPLATE", addItem(name + "_chestplate", new ArmorItem(
                material, EquipmentSlotType.CHEST, new Item.Properties().tab(FenrisRPG.MOD_TAB))));
        MAP.put("LEGGINGS", addItem(name + "_leggings", new ArmorItem(
                material, EquipmentSlotType.LEGS, new Item.Properties().tab(FenrisRPG.MOD_TAB))));
        MAP.put("BOOTS", addItem(name + "_boots", new ArmorItem(
                material, EquipmentSlotType.FEET, new Item.Properties().tab(FenrisRPG.MOD_TAB))));
        return MAP;
    }

    public static void ItemInit() {
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static final RegistryObject<Item>

        RUBY = addItem("ruby"),
        SAPPHIRE = addItem("sapphire"),
        PYRITE = addItem("pyrite"),
        LEAD = addItem("lead"),
        TIN = addItem("tin"),
        CASSITERITE = addItem("cassiterite"),
        GALENITE = addItem("galenite"),

        CORE_VESSEL = addItem("core_vessel"),
        CORE_VESSEL_HELMET = addItem("core_vessel_helmet"),
        CORE_VESSEL_CHESTPLATE = addItem("core_vessel_chestplate"),
        CORE_VESSEL_LEGGINGS = addItem("core_vessel_leggings"),
        CORE_VESSEL_BOOTS = addItem("core_vessel_boots"),
        CORE_VESSEL_SWORD = addItem("core_vessel_sword"),
        CORE_VESSEL_PICKAXE = addItem("core_vessel_pickaxe"),
        CORE_VESSEL_AXE = addItem("core_vessel_axe"),
        CORE_VESSEL_SHOVEL = addItem("core_vessel_shovel"),
        CORE_VESSEL_HOE = addItem("core_vessel_hoe"),
        CORE_VESSEL_HAMMER = addItem("core_vessel_hammer"),
        CORE_VESSEL_KATANA = addItem("core_vessel_katana"),
        CORE_VESSEL_DAGGER = addItem("core_vessel_dagger"),
        CORE_VESSEL_BROADSWORD = addItem("core_vessel_broadsword"),

        BRONZE_INGOT = addItem("bronze_ingot"),
        BRONZE_NUGGET = addItem("bronze_nugget"),
        SILVER_INGOT = addItem("silver_ingot"),
        SILVER_NUGGET = addItem("silver_nugget"),
        COPPER_INGOT = addItem("copper_ingot"),

        ESSENCE = addItem("essence"),
        ESSENCE_INGOT = addItem("essence_ingot"),

        IRON_ROD = addItem("iron_rod"),
        SILVER_ROD = addItem("silver_rod"),
        BRONZE_ROD = addItem("bronze_rod"),
        GOLD_ROD = addItem("gold_rod"),

        DYNAMO_CORE = addItem("dynamo_core", new DynamoCore()),

        DYNAMO_CORE_SWORD = addItem("dynamo_core_sword", new DynamoCoreSword(
                ModItemTier.DYNAMO_CORE, 2, -1.0F)),
        DYNAMO_CORE_AXE = addItem("dynamo_core_axe", new DynamoCoreAxe(
                ModItemTier.DYNAMO_CORE, 3, -2.0F)),
        DYNAMO_CORE_PICKAXE = addItem("dynamo_core_pickaxe", new DynamoCorePickaxe(
                ModItemTier.DYNAMO_CORE, 2, -2.0F)),
        DYNAMO_CORE_HOE = addItem("dynamo_core_hoe", new DynamoCoreHoe(
                ModItemTier.DYNAMO_CORE, 0, -2.0F)),
        DYNAMO_CORE_SHOVEL = addItem("dynamo_core_shovel", new DynamoCoreShovel(
                ModItemTier.DYNAMO_CORE, 1, -2.0F)),
        DYNAMO_CORE_KATANA = addItem("dynamo_core_katana", new DynamoCoreKatana(
                ModItemTier.DYNAMO_CORE, 2, -1.5F)),
        DYNAMO_CORE_HAMMER = addItem("dynamo_core_hammer", new DynamoCoreHammer(
                ModItemTier.DYNAMO_CORE, 4, -1.5F)),
        DYNAMO_CORE_BROADSWORD = addItem("dynamo_core_broadsword", new DynamoCoreBroadsword(
                ModItemTier.DYNAMO_CORE, 6, -3.0F)),
        DYNAMO_CORE_DAGGER = addItem("dynamo_core_dagger", new DynamoCoreDagger(
                ModItemTier.DYNAMO_CORE, 0, -1.0F)),
        DYNAMO_CORE_HELMET = addItem("dynamo_core_helmet", new DynamoCoreHelmet(
                ModArmorMaterial.DYNAMO_CORE_ARMOR)),
        DYNAMO_CORE_CHESTPLATE = addItem("dynamo_core_chestplate", new DynamoCoreChestplate(
                ModArmorMaterial.DYNAMO_CORE_ARMOR)),
        DYNAMO_CORE_LEGGINGS = addItem("dynamo_core_leggings", new DynamoCoreLeggings(
                ModArmorMaterial.DYNAMO_CORE_ARMOR)),
        DYNAMO_CORE_BOOTS = addItem("dynamo_core_boots", new DynamoCoreBoots(
                ModArmorMaterial.DYNAMO_CORE_ARMOR));

    public static final Map<String, RegistryObject<Item>>
        RUBY_TOOLSET = addToolSet("ruby", ModItemTier.RUBY),
        SAPPHIRE_TOOLSET = addToolSet("sapphire", ModItemTier.SAPPHIRE),
        BRONZE_TOOLSET = addToolSet("bronze", ModItemTier.BRONZE),
        SILVER_TOOLSET = addToolSet("silver", ModItemTier.SILVER),
        ESSENCE_TOOLSET = addToolSet("essence", ModItemTier.ESSENCE),

        RUBY_ARMOR_SET = addArmorSet("ruby", ModArmorMaterial.RUBY_ARMOR),
        SAPPHIRE_ARMOR_SET = addArmorSet("sapphire", ModArmorMaterial.SAPPHIRE_ARMOR),
        BRONZE_ARMOR_SET = addArmorSet("bronze", ModArmorMaterial.BRONZE_ARMOR),
        SILVER_ARMOR_SET = addArmorSet("silver", ModArmorMaterial.SILVER_ARMOR),
        ESSENCE_ARMOR_SET = addArmorSet("essence", ModArmorMaterial.ESSENCE_ARMOR),

        WOOD_TOOLSET = completeToolSet("wood", ItemTier.WOOD, null),
        STONE_TOOLSET = completeToolSet("stone", ItemTier.STONE, null),
        IRON_TOOLSET = completeToolSet("iron", ItemTier.IRON, null),
        GOLD_TOOLSET = completeToolSet("gold", ItemTier.GOLD, null),
        DIAMOND_TOOLSET = completeToolSet("diamond", ItemTier.DIAMOND, null),
        NETHERITE_TOOLSET = completeToolSet("netherite", ItemTier.NETHERITE, null);
}
