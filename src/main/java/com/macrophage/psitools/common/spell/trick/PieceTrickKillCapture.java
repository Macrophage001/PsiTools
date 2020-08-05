package com.macrophage.psitools.common.spell.trick;

import com.macrophage.psitools.common.item.ItemPsiCore;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import vazkii.psi.api.spell.*;
import vazkii.psi.api.spell.piece.PieceTrick;

import java.util.Collection;

public class PieceTrickKillCapture extends PieceTrick {
    public PieceTrickKillCapture(Spell spell) {
        super(spell);
    }

    @Override
    public void addToMetadata(SpellMetadata meta) throws SpellCompilationException {
        super.addToMetadata(meta);
        meta.addStat(EnumSpellStat.POTENCY, 250);
        meta.addStat(EnumSpellStat.COST, 100);
    }

    @Override
    public Object execute(SpellContext context) throws SpellRuntimeException {
        IInventory playerInventory = context.caster.inventory;
        ItemStack psiCore = null;

        for (int i = 0; i < playerInventory.getSizeInventory(); i++) {
            if (playerInventory.getStackInSlot(i).getItem() instanceof ItemPsiCore) {
                psiCore = playerInventory.removeStackFromSlot(i);
                break;
            }
        }

        LivingEntity livingEntity = null;

        if (psiCore != null && !psiCore.isEmpty() && psiCore.hasTag()) {
            livingEntity = (LivingEntity) EntityType.loadEntityUnchecked(psiCore.getTag().getCompound("entity_data"), context.caster.world).get();
            livingEntity.setPosition(context.caster.getPosX(), context.caster.getPosY() + 1.D, context.caster.getPosZ());
            livingEntity.onKillCommand();
        }

        return super.execute(context);
    }
}