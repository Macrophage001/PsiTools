package com.macrophage.psitools.common.item;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityType;
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
            nbt.putString("entity_name", entityLivingBase.getDisplayName().getString());
            nbt.putFloat("entity_health", entity_health);

            CompoundNBT entity_data = entityLivingBase.serializeNBT();
            nbt.put("entity_data", entity_data);

            stack.setTag(nbt);
        }
    }

    public void setCapturedEntity(ItemStack psiCore, ItemStack stack) {
        if (!stack.hasTag())
        {
            stack.setTag(psiCore.getTag());
        }
    }

    public LivingEntity getCapturedEntity(ItemStack stack, World world) { return (LivingEntity) EntityType.loadEntityUnchecked(stack.getTag().getCompound("entity_data"), world).get(); }
}