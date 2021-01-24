package com.macrophage.psitools.common.item;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

import java.util.List;

public class ItemStabilizedPsiCore extends ItemPsiCore {
    private long ENTITY_COUNT = 0;
    public static final long ENTITY_MAX = 4096;

    public void add(ItemStack stack, long amount)
    {
        if (stack.hasTag())
        {
            CompoundNBT nbt = stack.getTag();
            if (nbt.contains("entity_count")) ENTITY_COUNT = nbt.getLong("entity_count");
            ENTITY_COUNT++;
            nbt.putLong("entity_count", ENTITY_COUNT);
            stack.setTag(nbt);
        }
    }

    public void remove(ItemStack stack, long amount)
    {
        if (stack.hasTag())
        {
            CompoundNBT nbt = stack.getTag();
            if (nbt.contains("entity_count")) ENTITY_COUNT = nbt.getLong("entity_count");
            if ((ENTITY_COUNT -= amount) < 0)
                ENTITY_COUNT = 0;
            nbt.putLong("entity_count", ENTITY_COUNT);
            stack.setTag(nbt);
        }
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        if (stack.hasTag()) {
            tooltip.add(new StringTextComponent("Count: " + stack.getTag().getLong("entity_count")));
        }
    }

    @Override
    public boolean hasEffect(ItemStack stack) {
        return true;
    }

    public long getEntityCount(ItemStack stack) {
        if (stack.hasTag())
        {
            if (stack.getTag().contains("entity_count"))
            {
                ENTITY_COUNT = stack.getTag().getLong("entity_count");
            }
        }
        return ENTITY_COUNT;
    }
}