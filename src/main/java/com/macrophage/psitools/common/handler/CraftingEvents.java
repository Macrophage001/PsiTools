package com.macrophage.psitools.common.handler;

import com.macrophage.psitools.common.item.ItemPsiCore;
import com.macrophage.psitools.common.item.ItemStabilizedPsiCore;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class CraftingEvents {

    @SubscribeEvent
    public void StabilizedCoreCrafted(PlayerEvent.ItemCraftedEvent evt)
    {
        if (evt.getCrafting().getItem() instanceof  ItemStabilizedPsiCore) {
            IInventory craftingMatrix = evt.getInventory();
            ItemStack psiCore = craftingMatrix.getStackInSlot(4);
            LivingEntity livingEntity = ((ItemPsiCore) psiCore.getItem()).getCapturedEntity(psiCore, evt.getEntity().world);
            ((ItemStabilizedPsiCore) evt.getCrafting().getItem()).setCapturedEntity(psiCore, evt.getCrafting());
            evt.getCrafting().setDisplayName(new StringTextComponent("Stabilized Psi Core (" + livingEntity.getDisplayName().getString() + ")"));
        }
    }
}