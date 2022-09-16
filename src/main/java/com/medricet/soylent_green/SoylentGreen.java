package com.medricet.soylent_green;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.item.*;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SoylentGreen implements ModInitializer {
    // This logger is used to write text to the console and the log file.
    // It is considered best practice to use your mod id as the logger's name.
    // That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger("soylent_green");

    // forward declare so that the item group's icon can be set

    public static final Item AMBIGUOUS_MEAT = new Item(new FabricItemSettings().group(ItemGroup.FOOD));
    public static final Item NUTRIENT_PASTE = new Item(new FabricItemSettings().group(ItemGroup.MISC));

    public static final Item SOYLENT_GREEN = new Item(new FabricItemSettings().group(ItemGroup.FOOD).food(FoodComponents.GOLDEN_CARROT));

    public static final Identifier PLAYER_LOOT_TABLE_ID = EntityType.PLAYER.getLootTableId();

    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        Registry.register(Registry.ITEM, new Identifier("soylent_green", "ambiguous_meat"), AMBIGUOUS_MEAT);
        Registry.register(Registry.ITEM, new Identifier("soylent_green", "nutrient_paste"), NUTRIENT_PASTE);
        Registry.register(Registry.ITEM, new Identifier("soylent_green", "soylent_green"), SOYLENT_GREEN);

        // add ambiguous meat to the player loot table

        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
            if (source.isBuiltin() && (PLAYER_LOOT_TABLE_ID.equals(id))) {
                LootPool.Builder poolBuilder =
                        LootPool.builder().with(ItemEntry.builder(AMBIGUOUS_MEAT).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1, 3))));

                tableBuilder.pool(poolBuilder);
            }
        });

        LOGGER.info("Initialized.");
    }
}
