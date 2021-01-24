package com.macrophage.psitools.common.core;

import com.macrophage.psitools.common.init.ModItems;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import javax.annotation.Nonnull;

public class PsiToolsCreativeTab extends ItemGroup {
    public static final PsiToolsCreativeTab INSTANCE = new PsiToolsCreativeTab();
    private NonNullList<ItemStack> list;

    public PsiToolsCreativeTab() {
        super("PsiTools");
        this.setNoTitle();
        this.setBackgroundImageName("psigem_ring.png");
    }

    @Nonnull
    public ItemStack createIcon() {
        return new ItemStack(ModItems.bauble_controller.get());
    }

    public boolean hasSearchBar() {
        return true;
    }
}