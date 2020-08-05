package com.macrophage.psitools.common.item;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import java.util.List;

public class ItemPsiCore extends ItemMod {
    LivingEntity capturedEntity;

    public ItemPsiCore() {
        capturedEntity = null;
    }

    public void setCapturedEntity(LivingEntity entityLivingBase, ItemStack stack) {
        if (!stack.hasTag()) {
            CompoundNBT nbt = new CompoundNBT();
            Float entity_health = entityLivingBase.getHealth();
            nbt.putString("entity_name", entityLivingBase.getDisplayName().getFormattedText());
            nbt.putFloat("entity_health", entity_health);

            CompoundNBT entity_data = entityLivingBase.serializeNBT();
            nbt.put("entity_data", entity_data);

            stack.setTag(nbt);
        }
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);

        if (stack.hasTag()) {
            if (stack.getTag().get("entity_data") != null) {
                tooltip.add(new StringTextComponent("Name: " + stack.getTag().getString("entity_name")));
                tooltip.add(new StringTextComponent("Health: " + stack.getTag().getFloat("entity_health")));
            } else if (stack.getTag().get("block_data") != null) {
                tooltip.add(new StringTextComponent("Name: " + stack.getTag().getString("block_name")));
            }
        }
    }

    public LivingEntity getCapturedEntity() { return capturedEntity; }
}