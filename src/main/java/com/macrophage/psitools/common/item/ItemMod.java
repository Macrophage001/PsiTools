package com.macrophage.psitools.common.item;

import net.minecraft.item.Item;
import net.minecraft.item.Rarity;
import vazkii.psi.common.core.PsiCreativeTab;

public class ItemMod extends Item {
    public ItemMod() {
        super(new Item.Properties().rarity(Rarity.RARE).group(PsiCreativeTab.INSTANCE));
    }
}
