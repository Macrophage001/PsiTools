package com.macrophage.psitools.common.spell.trick;

import com.macrophage.psitools.common.init.ModItems;
import com.macrophage.psitools.common.item.ItemPsiCore;
import com.macrophage.psitools.common.item.ItemStabilizedPsiCore;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PotionItem;
import net.minecraft.util.text.StringTextComponent;
import vazkii.psi.api.spell.*;
import vazkii.psi.api.spell.param.ParamEntity;
import vazkii.psi.api.spell.piece.PieceTrick;

public class PieceTrickCapture extends PieceTrick {

    SpellParam<Entity> entityParam;
    Entity entityTarget;

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
        entityTarget = (Entity) this.getParamValue(context, this.entityParam);

        if (!(entityTarget instanceof LivingEntity) || entityTarget instanceof PlayerEntity || !entityTarget.isNonBoss()) {
            throw new SpellRuntimeException("Invalid Entity!");
        }
        else if (entityTarget instanceof WitherEntity)
        {
            throw new SpellRuntimeException("Entity cannot be contained!");
        }

        ItemStack stabilizedPsiCore = null;
        IInventory playerInventory = context.caster.inventory;
        for (int i = 0; i < playerInventory.getSizeInventory(); i++) {
            if (playerInventory.getStackInSlot(i).getItem() instanceof ItemStabilizedPsiCore) {
                stabilizedPsiCore = playerInventory.getStackInSlot(i);
                break;
            }
        }

        if (stabilizedPsiCore != null &&
                (entityTarget.getDisplayName().getString().compareTo(stabilizedPsiCore.getTag().getString("entity_name")) == 0) &&
                    stabilizedPsiCore.getTag().getLong("entity_count") < ItemStabilizedPsiCore.ENTITY_MAX)
        {
            ((ItemStabilizedPsiCore) stabilizedPsiCore.getItem()).add(stabilizedPsiCore, 1);
            entityTarget.remove();
        }
        else
        {
            ItemStack psiCore = new ItemStack(ModItems.psi_core.get(), 1);
            ((ItemPsiCore) psiCore.getItem()).setCapturedEntity( (LivingEntity) entityTarget, psiCore);
            psiCore.setDisplayName(new StringTextComponent("Psi Core (" + entityTarget.getDisplayName().getString() + ")"));

            ItemEntity psiCoreEntityItem = new ItemEntity(context.caster.world, entityTarget.getPosition().getX(), entityTarget.getPosition().getY(), entityTarget.getPosition().getZ(), psiCore);
            context.caster.world.addEntity(psiCoreEntityItem);
            entityTarget.remove();
        }
        return super.execute(context);
    }
}