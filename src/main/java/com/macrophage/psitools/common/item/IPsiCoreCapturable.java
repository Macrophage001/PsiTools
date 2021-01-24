package com.macrophage.psitools.common.item;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface IPsiCoreCapturable<T> {
    void setCaptured(ItemStack stack, T obj);
    void add(ItemStack stack, int amount);
    void rem(ItemStack stack, int amount);

    T getCaptured(ItemStack stack, World world);
}