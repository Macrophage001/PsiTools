package com.macrophage.psitools.common.item.curio;

import net.minecraft.item.ItemStack;

public class ItemPsigemBelt extends ItemPsiCurio {
    public ItemPsigemBelt()
    {
        super();
    }
    public ItemPsigemBelt(ItemStack stack) {
        super(stack);
    }

    @Override
    public Double costModifier() {
        return 0.80;
    }
}