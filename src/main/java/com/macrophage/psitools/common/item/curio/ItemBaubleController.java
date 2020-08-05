package com.macrophage.psitools.common.item.curio;

import com.macrophage.psitools.PsiTools;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import top.theillusivec4.curios.api.CuriosAPI;
import top.theillusivec4.curios.api.capability.ICurio;
import top.theillusivec4.curios.api.capability.ICurioItemHandler;
import vazkii.psi.api.PsiAPI;
import vazkii.psi.api.cad.ISocketable;
import vazkii.psi.api.cad.ISocketableController;
import vazkii.psi.common.core.PsiCreativeTab;
import vazkii.psi.common.core.handler.PsiSoundHandler;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class ItemBaubleController extends Item implements ISocketableController {
    private static final String TAG_SELECTED_CONTROL_SLOT = "selectedControlSlot";

    public ItemBaubleController() {
        super(new Item.Properties().maxStackSize(1).rarity(Rarity.RARE).group(PsiCreativeTab.INSTANCE));
    }

    @Nonnull
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, @Nonnull Hand hand) {
        ItemStack itemStackIn = playerIn.getHeldItem(hand);
        if (!playerIn.isSneaking()) {
            return new ActionResult(ActionResultType.PASS, itemStackIn);
        } else {
            if (!worldIn.isRemote) {
                worldIn.playSound((PlayerEntity)null, playerIn.getPosX(), playerIn.getPosY(), playerIn.getPosZ(), PsiSoundHandler.compileError, SoundCategory.PLAYERS, 0.25F, 1.0F);
            } else {
                playerIn.swingArm(hand);
            }

            ItemStack[] stacks = this.getControlledStacks(playerIn, itemStackIn);
            ItemStack[] var6 = stacks;
            int var7 = stacks.length;

            for(int var8 = 0; var8 < var7; ++var8) {
                ItemStack stack = var6[var8];
                stack.getCapability(PsiAPI.SOCKETABLE_CAPABILITY).ifPresent((c) -> {
                    c.setSelectedSlot(3);
                });
            }

            return new ActionResult(ActionResultType.SUCCESS, itemStackIn);
        }
    }

    public ItemStack[] getControlledStacks(PlayerEntity player, ItemStack stack) {
        List<ItemStack> stacks = new ArrayList();

        ICurioItemHandler curioItemHandler = CuriosAPI.getCuriosHandler(player).orElse(null);
        ItemStack[] curios = {
                curioItemHandler.getStackInSlot("necklace", 0),
                curioItemHandler.getStackInSlot("ring", 0),
                curioItemHandler.getStackInSlot("ring", 1),
                curioItemHandler.getStackInSlot("belt", 0)
        };

        for (ItemStack stack1 : curios)
        {
            if (!stack.isEmpty() && ISocketable.isSocketable(stack1))
            {
                stacks.add(stack1);
            }
        }

        /*
        for(int i = 0; i < 4; ++i) {
            ItemStack armor = (ItemStack)player.inventory.armorInventory.get(3 - i);
            if (!armor.isEmpty() && ISocketable.isSocketable(armor)) {
                stacks.add(armor);
            }
        }
        */

        return stacks.toArray(new ItemStack[0]);
    }

    public int getDefaultControlSlot(ItemStack stack) {
        return stack.getOrCreateTag().getInt("selectedControlSlot");
    }

    public void setSelectedSlot(PlayerEntity player, ItemStack stack, int controlSlot, int slot) {
        stack.getOrCreateTag().putInt("selectedControlSlot", controlSlot);
        ItemStack[] stacks = this.getControlledStacks(player, stack);
        if (controlSlot < stacks.length && !stacks[controlSlot].isEmpty()) {
            stacks[controlSlot].getCapability(PsiAPI.SOCKETABLE_CAPABILITY).ifPresent((cap) -> {
                cap.setSelectedSlot(slot);
            });
        }
    }
}
