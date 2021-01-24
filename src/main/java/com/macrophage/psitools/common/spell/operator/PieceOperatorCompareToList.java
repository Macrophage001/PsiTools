package com.macrophage.psitools.common.spell.operator;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import vazkii.psi.api.spell.Spell;
import vazkii.psi.api.spell.SpellContext;
import vazkii.psi.api.spell.SpellParam;
import vazkii.psi.api.spell.SpellRuntimeException;
import vazkii.psi.api.spell.param.ParamEntity;
import vazkii.psi.api.spell.param.ParamEntityListWrapper;
import vazkii.psi.api.spell.piece.PieceOperator;
import vazkii.psi.api.spell.wrapper.EntityListWrapper;

public class PieceOperatorCompareToList extends PieceOperator {
    SpellParam<Entity> entityParam;
    SpellParam<EntityListWrapper> entityList;
    LivingEntity entityTarget;
    EntityListWrapper entityListWrapper;

    public PieceOperatorCompareToList(Spell spell) {
        super(spell);
    }

    @Override
    public void initParams() {
        super.initParams();
        this.addParam(entityParam = new ParamEntity("Target", SpellParam.GREEN, false, false));
        this.addParam(entityList = new ParamEntityListWrapper("List", SpellParam.RED, false, false));
    }

    @Override
    public Object execute(SpellContext context) throws SpellRuntimeException {
        entityTarget = (LivingEntity) this.getParamValue(context, entityParam);
        entityListWrapper = (EntityListWrapper) this.getParamValue(context, entityList);

        double i = 0;
        if (entityListWrapper.size() == 0)
        {
            throw new SpellRuntimeException("psi.spellerror.nulltarget");
        }
        else
        {
            for (Entity entity : entityListWrapper) {
                i = entity.getDisplayName().toString().compareTo(entityTarget.getDisplayName().toString()) == 0 ? 1.0 : 0.0;
            }
        }
        return i;
    }

    @Override
    public Class<?> getEvaluationType() {
        return Double.class;
    }
}