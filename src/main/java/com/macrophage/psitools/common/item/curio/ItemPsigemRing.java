package com.macrophage.psitools.common.item.curio;

import net.minecraft.item.ItemStack;

public class ItemPsigemRing extends ItemPsiCurio {
    public ItemPsigemRing()
    {
        super();
    }
    public ItemPsigemRing(ItemStack stack) {
        super(stack);
    }

    @Override
    public Double costModifier() {
        return 0.85;
    }
}