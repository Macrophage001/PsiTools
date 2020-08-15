package com.macrophage.psitools.common.item;

import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

public class ItemBlockPsiCore extends ItemMod implements IPsiCoreCapturable<Block>{
    public static final int BLOCK_MAX = Integer.MAX_VALUE;

    @Override
    public void setCaptured(ItemStack stack, Block obj) {
        if (!stack.hasTag())
        {
            CompoundNBT nbt = new CompoundNBT();
            nbt.putString("block_registry_name", obj.getRegistryName().toString());
            nbt.putLong("block_count", 1);
            stack.setTag(nbt);
        }
    }

    public void setActive(ItemStack stack, boolean active)
    {
        if (stack.hasTag())
        {
            stack.getTag().putBoolean("active", active);
        }
    }

    @Override
    public void add(ItemStack stack, int amount) {
        if (stack.hasTag() && stack.getTag().contains("block_count"))
        {
            int count = stack.getTag().getInt("block_count");
            count += amount;
            stack.getTag().putLong("block_count", count);
        }
    }

    @Override
    public void rem(ItemStack stack, int amount) {
        if (stack.hasTag() && stack.getTag().contains("block_count"))
        {
            int count = stack.getTag().getInt("block_count");
            count -= amount;
            stack.getTag().putLong("block_count", count);
        }
    }

    /*
        Until I figure out how to place blocks straight from the item, this'll do...
     */
    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        ItemStack blockPsiCore = context.getItem();
        if (!(blockPsiCore.getItem() instanceof ItemBlockPsiCore))
            return null;

        if (!context.getWorld().isRemote) {
            if (blockPsiCore.hasTag()) {
                Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(blockPsiCore.getTag().getString("block_registry_name")));
                boolean active = blockPsiCore.getTag().getBoolean("active");
                if (!active)
                {
                    int block_count = blockPsiCore.getTag().getInt("block_count");
                    int extract_amount = 0;

                    if (block_count > 0) {
                        if (block_count > 64) {
                            extract_amount = 64;
                        } else {
                            extract_amount = blockPsiCore.getTag().getInt("block_count");
                        }

                        ItemStack itemStack = new ItemStack(block, extract_amount);
                        ItemEntity itemEntity = new ItemEntity(context.getPlayer().world, context.getPlayer().getPositionVec().x, context.getPlayer().getPositionVec().y, context.getPlayer().getPositionVec().z, itemStack);

                        context.getWorld().addEntity(itemEntity);

                        ((ItemBlockPsiCore) blockPsiCore.getItem()).rem(blockPsiCore, extract_amount);
                    }
                }
            }
        }

        return super.onItemUse(context);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemStack = playerIn.getHeldItem(handIn);
        if (itemStack.getItem() instanceof ItemBlockPsiCore)
        {
            if (itemStack.hasTag())
            {
                if (playerIn.isSneaking())
                {
                    boolean active = itemStack.getTag().getBoolean("active");
                    active = !active;
                    itemStack.getTag().putBoolean("active", active);
                }
            }
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        if (stack.hasTag())
        {
            tooltip.add(new StringTextComponent("Count: " + stack.getTag().getLong("block_count")));
            tooltip.add(new StringTextComponent("Active: " + String.valueOf(stack.getTag().getBoolean("active"))));
        }
    }

    @Override
    public boolean hasEffect(ItemStack stack) {
        return stack.getTag().getBoolean("active");
    }

    @Override
    public Block getCaptured(ItemStack stack, World world) {
        if (stack.hasTag())
        {
            return ForgeRegistries.BLOCKS.getValue(new ResourceLocation(stack.getTag().getString("block_registry_name")));
        }
        else
        {
            return null;
        }
    }

    public boolean getActive(ItemStack stack)
    {
        return stack.getTag().getBoolean("active");
    }
}