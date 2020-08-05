package com.macrophage.psitools.common.spell.trick;

import com.macrophage.psitools.common.item.ItemPsiCore;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import vazkii.psi.api.internal.Vector3;
import vazkii.psi.api.spell.*;
import vazkii.psi.api.spell.param.ParamVector;
import vazkii.psi.api.spell.piece.PieceTrick;

public class PieceTrickRelease extends PieceTrick {
    SpellParam position;

    public PieceTrickRelease(Spell spell) {
        super(spell);
    }

    @Override
    public void addToMetadata(SpellMetadata meta) throws SpellCompilationException {
        super.addToMetadata(meta);
        meta.addStat(EnumSpellStat.POTENCY, 75);
        meta.addStat(EnumSpellStat.COST, 30);
    }

    @Override
    public void initParams() {
        super.initParams();
        this.addParam(position = new ParamVector("Position", SpellParam.BLUE, false, false));
    }

    @Override
    public Object execute(SpellContext context) throws SpellRuntimeException {
        IInventory playerInventory = context.caster.inventory;
        ItemStack psiCore = null;
        Vector3 vecPos = (Vector3) this.getParamValue(context, position);
        BlockPos blockPos = new BlockPos(vecPos.x, vecPos.y, vecPos.z);

        for (int i = 0; i < playerInventory.getSizeInventory(); i++) {
            if (playerInventory.getStackInSlot(i).getItem() instanceof ItemPsiCore) {
                psiCore = playerInventory.removeStackFromSlot(i);
                break;
            }
        }

        LivingEntity newEntity = null;

        if (!psiCore.isEmpty() && psiCore.hasTag()) {

            if (psiCore.getTag().get("entity_data") != null) {
                newEntity = (LivingEntity) EntityType.loadEntityUnchecked(psiCore.getTag().getCompound("entity_data"), context.caster.world).get();

                if (newEntity != null) {
                    newEntity.setPosition(blockPos.getX() + .5, blockPos.getY() + .5, blockPos.getZ() + .5);
                    context.caster.world.addEntity(newEntity);
                } else {
                    throw new SpellRuntimeException("Could not spawn entity!");
                }
            }
        }
        return super.execute(context);
    }
}