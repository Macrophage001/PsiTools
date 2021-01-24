package com.macrophage.psitools.common.item.curio;

import net.minecraft.item.ItemStack;

public class ItemPsigemAmulet extends ItemPsiCurio {
    public ItemPsigemAmulet()
    {
        super();
    }
    public ItemPsigemAmulet(ItemStack stack) {
        super(stack);
    }

    @Override
    public Double costModifier() {
        return 0.90;
    }
}