package com.macrophage.psitools.common.spell.trick;

import com.macrophage.psitools.common.item.ItemPsiCore;
import com.macrophage.psitools.common.item.ItemStabilizedPsiCore;
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
    SpellParam<Number> lootingLevel;

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
        int index = 0;

        for (int i = 0; i < playerInventory.getSizeInventory(); i++) {
            if (playerInventory.getStackInSlot(i).getItem() instanceof ItemPsiCore) {
                psiCore = playerInventory.getStackInSlot(i);
                index = i;
                break;
            }
        }

        LivingEntity livingEntity = null;

        assert psiCore != null;
        if (psiCore.getItem() instanceof ItemStabilizedPsiCore) {
            long entity_count = psiCore.getTag().getLong("entity_count");
            if (entity_count > 0)
            {
                livingEntity = ((ItemStabilizedPsiCore) psiCore.getItem()).getCapturedEntity(psiCore, context.caster.world);
                ((ItemStabilizedPsiCore) psiCore.getItem()).remove(psiCore, 1);
            }
            else
            {
                throw new SpellRuntimeException("Stabilized core has no entities to kill!");
            }
        } else {
            livingEntity = ((ItemPsiCore) psiCore.getItem()).getCapturedEntity(psiCore, context.caster.world);
            playerInventory.removeStackFromSlot(index);
        }

        if (livingEntity != null)
        {
            livingEntity.setPosition(context.caster.getPosX(), context.caster.getPosY() + 1.D, context.caster.getPosZ());
            livingEntity.attackEntityFrom(DamageSource.causePlayerDamage(context.caster), Float.MAX_VALUE);
        }
        else
        {
            throw new SpellRuntimeException("Could not spawn entity!");
        }

        return super.execute(context);
    }
}