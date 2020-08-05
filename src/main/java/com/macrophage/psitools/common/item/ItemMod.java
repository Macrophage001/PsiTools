package com.macrophage.psitools.common.item;

import com.macrophage.psitools.common.core.PsiToolsCreativeTab;
import net.minecraft.item.Item;
import net.minecraft.item.Rarity;

public class ItemMod extends Item {
    public ItemMod() {
        super(new Item.Properties().rarity(Rarity.RARE).group(PsiToolsCreativeTab.INSTANCE));
    }
}
