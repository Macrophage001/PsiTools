package com.macrophage.psitools.common.spell.trick;

import com.macrophage.psitools.common.init.ModItems;
import com.macrophage.psitools.common.item.ItemPsiCore;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import vazkii.psi.api.spell.*;
import vazkii.psi.api.spell.param.ParamEntity;
import vazkii.psi.api.spell.piece.PieceTrick;

public class PieceTrickCapture extends PieceTrick {
    SpellParam entityParam;
    LivingEntity entityTarget;

    public PieceTrickCapture(Spell spell) {
        super(spell);
    }

    @Override
    public void initParams() {
        super.initParams();
        this.addParam(entityParam = new ParamEntity("Target", SpellParam.BLUE, false, false));
    }

    @Override
    public void addToMetadata(SpellMetadata meta) throws SpellCompilationException {
        super.addToMetadata(meta);
        meta.addStat(EnumSpellStat.POTENCY, 100);
        meta.addStat(EnumSpellStat.COST, 200);
    }

    @Override
    public Object execute(SpellContext context) throws SpellRuntimeException {
        entityTarget = (LivingEntity) this.getParamValue(context, this.entityParam);

        if (entityTarget instanceof PlayerEntity) {
            throw new SpellRuntimeException("Invalid Entity!");
        }
        else if (entityTarget instanceof WitherEntity)
        {
            throw new SpellRuntimeException("Entity cannot be contained!");
        }

        ItemStack psiCore = new ItemStack(ModItems.psi_core.get(), 1);
        ((ItemPsiCore) psiCore.getItem()).setCapturedEntity(entityTarget, psiCore);
        ItemEntity psiCoreEntityItem = new ItemEntity(context.caster.world, entityTarget.getPosition().getX(), entityTarget.getPosition().getY(), entityTarget.getPosition().getZ(), psiCore);
        entityTarget.remove();
        context.caster.world.addEntity(psiCoreEntityItem);
        return null;
    }
}