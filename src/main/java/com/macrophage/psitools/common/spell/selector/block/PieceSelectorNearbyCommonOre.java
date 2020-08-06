package com.macrophage.psitools.common.spell.selector.block;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.tags.TagCollection;
import net.minecraft.util.ResourceLocation;
import vazkii.psi.api.spell.Spell;
import vazkii.psi.api.spell.SpellContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class PieceSelectorNearbyCommonOre extends PieceSelectorNearbyBlock {
    private final List<Block> COMMON_ORE_LIST = new ArrayList<>();

    public PieceSelectorNearbyCommonOre(Spell spell) {
        super(spell);
        TagCollection<Block> tags = BlockTags.getCollection();
        ResourceLocation oreCopperTagId = new ResourceLocation("forge", "ores/copper");
        ResourceLocation oreTinTagId = new ResourceLocation("forge", "ores/tin");
        ResourceLocation oreSilverTagId = new ResourceLocation("forge", "ores/silver");
        ResourceLocation oreLeadTagId = new ResourceLocation("forge", "ores/lead");

        Tag<Block> copperTag = tags.get(oreCopperTagId);
        Tag<Block> tinTag = tags.get(oreTinTagId);
        Tag<Block> silverTag = tags.get(oreSilverTagId);
        Tag<Block> leadTag = tags.get(oreLeadTagId);

        COMMON_ORE_LIST.add(Blocks.IRON_ORE);
        COMMON_ORE_LIST.add(Blocks.COAL_ORE);

        if (copperTag != null) COMMON_ORE_LIST.addAll(Objects.requireNonNull(copperTag).getAllElements());
        if (tinTag != null) COMMON_ORE_LIST.addAll(Objects.requireNonNull(tinTag).getAllElements());
        if (silverTag != null) COMMON_ORE_LIST.addAll(Objects.requireNonNull(silverTag).getAllElements());
        if (leadTag != null) COMMON_ORE_LIST.addAll(Objects.requireNonNull(leadTag).getAllElements());
    }

    @Override
    public Predicate<Block> getTargetPredicate(SpellContext var1) {
        return COMMON_ORE_LIST::contains;
    }
}