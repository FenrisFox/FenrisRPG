package com.FenrisFox86.fenris_rpg.world;

import com.FenrisFox86.fenris_rpg.core.init.BlockInit;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.OreBlock;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.WorldGenRegion;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.template.BlockMatchRuleTest;
import net.minecraft.world.gen.feature.template.RuleTest;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.placement.TopSolidRangeConfig;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class OreGeneration {

    @SubscribeEvent
    public static void generateOres(final BiomeLoadingEvent event) {
        if(!(event.getCategory().equals(Biome.Category.NETHER) || event.getCategory().equals(Biome.Category.THEEND))) {

            generateBlock(event.getGeneration(), OreFeatureConfig.FillerBlockType.NATURAL_STONE,
                    BlockInit.COPPER_ORE_BLOCK.get().defaultBlockState(),
                    10, 0, 80, 30);

            generateBlock(event.getGeneration(), OreFeatureConfig.FillerBlockType.NATURAL_STONE,
                    BlockInit.CASSITERITE_BLOCK.get().defaultBlockState(),
                    8, 0, 69, 20);

            /*generateBlock(event.getGeneration(), OreFeatureConfig.FillerBlockType.NATURAL_STONE,
                    BlockInit.GALENITE_BLOCK.get().defaultBlockState(),
                    8, 0, 69, 20);*/

            generateBlock(event.getGeneration(), OreFeatureConfig.FillerBlockType.NATURAL_STONE,
                    BlockInit.SILVER_ORE_BLOCK.get().defaultBlockState(),
                    10, 0, 40, 10);

            generateBlock(event.getGeneration(), OreFeatureConfig.FillerBlockType.NATURAL_STONE,
                    BlockInit.RUBY_ORE_BLOCK.get().defaultBlockState(),
                    8, 0, 15, 3);

            generateBlock(event.getGeneration(), OreFeatureConfig.FillerBlockType.NATURAL_STONE,
                    BlockInit.SAPPHIRE_ORE_BLOCK.get().defaultBlockState(),
                    8, 0, 15, 3);

            generateBlock(event.getGeneration(), OreFeatureConfig.FillerBlockType.NATURAL_STONE,
                    Blocks.SPONGE.defaultBlockState(),
                    1, 0, 255, 1);

            generateBlock(event.getGeneration(), OreFeatureConfig.FillerBlockType.NATURAL_STONE,
                    BlockInit.DOLOMITE.get().defaultBlockState(),
                    30, 0, 255, 20);

        } else if (event.getCategory().equals(Biome.Category.THEEND)) {
            generateBlock(event.getGeneration(), new BlockMatchRuleTest(Blocks.END_STONE),
                    BlockInit.ESSENCE_ORE_BLOCK.get().defaultBlockState(),
                    3, 24, 28, 20);
        }
    }
    private static void generateBlock(BiomeGenerationSettingsBuilder settings, RuleTest fillerType, BlockState state,
                                      int veinSize, int minHeight, int maxHeight, int amount) {
        settings.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES,
                Feature.ORE.configured(new OreFeatureConfig(fillerType, state, veinSize))
                        .decorated(Placement.RANGE.configured(new TopSolidRangeConfig(minHeight, 0, maxHeight)))
                        .squared().count(amount));
    }
}
