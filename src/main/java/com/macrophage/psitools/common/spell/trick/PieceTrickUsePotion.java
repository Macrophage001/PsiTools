package com.macrophage.psitools.common.spell.trick;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PotionItem;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.PotionUtils;
import vazkii.psi.api.spell.*;
import vazkii.psi.api.spell.param.ParamEntity;
import vazkii.psi.api.spell.param.ParamEntityListWrapper;
import vazkii.psi.api.spell.piece.PieceTrick;
import vazkii.psi.api.spell.wrapper.EntityListWrapper;

import java.util.List;

public class PieceTrickUsePotion extends PieceTrick {
    SpellParam<EntityListWrapper> target;

    public PieceTrickUsePotion(Spell spell) {
        super(spell);
    }

    @Override
    public void initParams() {
        super.initParams();
        this.addParam(target = new ParamEntityListWrapper("List", SpellParam.GREEN, false, false));
    }

    @Override
    public void addToMetadata(SpellMetadata meta) throws SpellCompilationException, ArithmeticException {
        super.addToMetadata(meta);
        meta.addStat(EnumSpellStat.POTENCY, 75);
        meta.addStat(EnumSpellStat.COST, 200);
    }

    @Override
    public Object execute(SpellContext context) throws SpellRuntimeException {
        IInventory playerInventory = context.caster.inventory;
        EntityListWrapper entityListWrapper = (EntityListWrapper) this.getParamValue(context, target);
        ItemStack potion = null;

        for (int i = 0; i < playerInventory.getSizeInventory(); i++) {
            if (playerInventory.getStackInSlot(i).getItem() instanceof PotionItem) {
                potion = playerInventory.removeStackFromSlot(i);
                break;
            }
        }

        if (!potion.isEmpty())
        {
            List<EffectInstance> potionEffects = PotionUtils.getEffectsFromStack(potion);
            for (Entity entity : entityListWrapper)
            {
                for(EffectInstance effect : potionEffects) {
                    if (effect.getPotion().isInstant()) {
                        effect.getPotion().affectEntity(context.caster, context.caster, (LivingEntity) entity, effect.getAmplifier(), 1.0D);
                    } else {
                        ((LivingEntity) entity).addPotionEffect(new EffectInstance(effect));
                    }
                }
            }

        }

        return super.execute(context);
    }
}