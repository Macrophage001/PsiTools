package com.macrophage.psitools.common.item.curio;

import com.macrophage.psitools.PsiTools;
import com.macrophage.psitools.common.core.PsiToolsCreativeTab;
import com.macrophage.psitools.common.helper.IPsiCustomCast;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import top.theillusivec4.curios.api.capability.ICurio;
import vazkii.psi.api.PsiAPI;
import vazkii.psi.api.cad.ISocketable;
import vazkii.psi.common.core.PsiCreativeTab;
import vazkii.psi.common.core.handler.PlayerDataHandler;
import vazkii.psi.common.item.tool.IPsimetalTool;
import vazkii.psi.common.item.tool.ToolSocketable;

import javax.annotation.Nullable;
import java.util.List;

public class ItemPsiCurio extends Item implements ICurio, IPsimetalTool, IPsiCustomCast {

    private ItemStack stack;

    public ItemPsiCurio()
    {
        super(new Item.Properties().group(PsiCreativeTab.INSTANCE));
        this.stack = new ItemStack(this);
    }

    public ItemPsiCurio(ItemStack stack) {
        super(new Item.Properties().group(PsiCreativeTab.INSTANCE));
        this.stack = stack;
    }

    @Override
    public void onCurioTick(String identifier, int index, LivingEntity livingEntity) {
        ISocketable sockets = ISocketable.socketable(stack);
        ItemStack bullet = sockets.getSelectedBullet();
        ItemStack cad = PsiAPI.getPlayerCAD((PlayerEntity) livingEntity);

        IPsiCustomCast.cast(livingEntity.world, (PlayerEntity) livingEntity, PlayerDataHandler.get((PlayerEntity) livingEntity), bullet, cad, 0, 10, 0.05F, costModifier(), null);
    }

    @Override
    public void onEquipped(String identifier, LivingEntity livingEntity) {}

    @Override
    public void onUnequipped(String identifier, LivingEntity livingEntity) {}

    @Override
    public boolean canEquip(String identifier, LivingEntity livingEntity) {
        return true;
    }

    @Override
    public boolean canRightClickEquip() {
        return true;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);

        double eff = 1 - costModifier() * 100F;

        tooltip.add(new StringTextComponent("Efficiency: " + String.valueOf(eff) + "%"));
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        return new ToolSocketable(stack, 2);
    }
}