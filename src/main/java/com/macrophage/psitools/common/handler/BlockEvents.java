package com.macrophage.psitools.common.handler;

import com.macrophage.psitools.common.item.ItemBlockPsiCore;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

public class BlockEvents {
    @SubscribeEvent
    public void onPlayerBreakBlock(PlayerEvent.ItemPickupEvent event)
    {
        IInventory playerInventory = event.getPlayer().inventory;
        List<ItemStack> blockPsiCores = new ArrayList<>();
        //ItemStack blockPsiCore = null;
        for (int i = 0; i < playerInventory.getSizeInventory(); i++)
        {
            ItemStack stack = playerInventory.getStackInSlot(i);
            if (stack.getItem() instanceof ItemBlockPsiCore)
            {
                blockPsiCores.add(stack);
            }
        }

        if (blockPsiCores.size() > 0)
        {
            Block block = Block.getBlockFromItem(event.getStack().getItem().getItem());

            for (ItemStack blockPsiCore : blockPsiCores) {
                if (blockPsiCore.hasTag() && block != null && blockPsiCore.getTag().getInt("block_count") < ItemBlockPsiCore.BLOCK_MAX) {
                    boolean active = blockPsiCore.getTag().getBoolean("active");

                    if (!active)
                        return;
                    String core_block_name = blockPsiCore.getTag().getString("block_registry_name");
                    Block core_block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(core_block_name));
                    assert core_block != null;
                    if (core_block.getDefaultState().equals(block.getDefaultState())) {
                        ((ItemBlockPsiCore) blockPsiCore.getItem()).add(blockPsiCore, event.getStack().getCount());
                        removeStackFromInventory(playerInventory, event.getStack());
                    }
                }
            }
        }
    }

    public void removeStackFromInventory(IInventory inventory, ItemStack stack)
    {
        for (int i = 0; i < inventory.getSizeInventory(); i++)
        {
            ItemStack stack2 = inventory.getStackInSlot(i);
            if (stack2.equals(stack, false))
            {
                inventory.removeStackFromSlot(i);
                break;
            }
        }
    }
}