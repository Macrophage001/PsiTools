package com.macrophage.psitools.common.spell.trick;

import net.minecraft.entity.Entity;
import vazkii.psi.api.internal.Vector3;
import vazkii.psi.api.spell.*;
import vazkii.psi.api.spell.param.ParamEntity;
import vazkii.psi.api.spell.param.ParamVector;
import vazkii.psi.api.spell.piece.PieceTrick;

public class PieceTrickRecall extends PieceTrick {
    SpellParam target;
    SpellParam position;

    public PieceTrickRecall(Spell spell) {
        super(spell);
    }

    public void addToMetadata(SpellMetadata meta) throws SpellCompilationException {
        super.addToMetadata(meta);
        meta.addStat(EnumSpellStat.POTENCY, 150);
        meta.addStat(EnumSpellStat.COST, 225);
    }

    @Override
    public void initParams() {
        super.initParams();
        this.addParam(target = new ParamEntity("Target", SpellParam.YELLOW, false, false));
        this.addParam(position = new ParamVector("Position", SpellParam.RED, false, false));
    }

    @Override
    public Object execute(SpellContext context) throws SpellRuntimeException {
        Entity e = (Entity) this.getParamValue(context, target);
        Vector3 pos = (Vector3) this.getParamValue(context, position);

        context.verifyEntity(e);
        e.setPositionAndRotation(pos.x, pos.y, pos.z, e.rotationYaw, e.rotationPitch);
        return super.execute(context);
    }
}