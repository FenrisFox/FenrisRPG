package com.FenrisFox86.fenris_rpg.core.init;

import com.FenrisFox86.fenris_rpg.FenrisRPG;
import com.FenrisFox86.fenris_rpg.common.blocks.*;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.Map;

public class BlockInit {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, FenrisRPG.MOD_ID);
    public static Map<String, Block> BLOCK_MAP = new HashMap<>();

    static Item.Properties BlockTabProps() {
        return new Item.Properties().tab(FenrisRPG.MOD_TAB);
    }

    public static RegistryObject<Block> addBlock(String name, Block block) {
        BLOCK_MAP.put(name, block);
        final RegistryObject<Item> item = ItemInit.addItem(name, new BlockItemBase(block));
        return BLOCKS.register(name, () -> block);
    }

    public static void BlockInit() {
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());

    }

    // Blocks
    public static final RegistryObject<Block>
        RUBY_BLOCK = addBlock("ruby_block", new RubyBlock()),
        BRONZE_BLOCK = addBlock("bronze_block", new BronzeBlock()),
        SILVER_BLOCK = addBlock("silver_block", new SilverBlock()),
        SAPPHIRE_BLOCK = addBlock("sapphire_block", new SapphireBlock()),
        ESSENCE_BLOCK = addBlock("essence_block", new EssenceBlock()),

        DOLOMITE = addBlock("dolomite", new DolomiteBlock()),
        DOLOMITE_SAND = addBlock("dolomite_sand", new DolomiteSandBlock()),

        COPPER_ORE_BLOCK = addBlock("copper_ore", new OreBlockBase(Material.STONE, 3.0F, 4.0F,
                1, ToolType.PICKAXE, SoundType.STONE, 0)),
        SILVER_ORE_BLOCK = addBlock("silver_ore", new OreBlockBase(Material.STONE, 4.0F, 5.0F,
                2, ToolType.PICKAXE, SoundType.STONE, 0)),
        RUBY_ORE_BLOCK = addBlock("ruby_ore", new OreBlockBase(Material.STONE, 5.0F, 6.0F,
                3, ToolType.PICKAXE, SoundType.STONE, 10)),
        SAPPHIRE_ORE_BLOCK = addBlock("sapphire_ore", new OreBlockBase(Material.STONE, 5.0F, 6.0F,
                3, ToolType.PICKAXE, SoundType.STONE, 10)),
        CASSITERITE_BLOCK = addBlock("cassiterite_block", new OreBlockBase(Material.STONE, 4.0F, 4.0F,
                3, ToolType.PICKAXE, SoundType.STONE, 10)),
        GALENITE_BLOCK = addBlock("galenite_block", new OreBlockBase(Material.STONE, 4.0F, 4.0F,
                3, ToolType.PICKAXE, SoundType.STONE, 10)),
        ESSENCE_ORE_BLOCK = addBlock("essence_ore", new EssenceOreBlock()),

        CRUSHER_BLOCK = addBlock("crusher", new CrusherBlock(Block.Properties.of(Material.STONE).strength(2f).sound(SoundType.STONE).harvestLevel(1).harvestTool(ToolType.PICKAXE)));
}
