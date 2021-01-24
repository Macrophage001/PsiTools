package com.macrophage.psitools.common.spell.operator;

import vazkii.psi.api.spell.Spell;
import vazkii.psi.api.spell.SpellContext;
import vazkii.psi.api.spell.SpellParam;
import vazkii.psi.api.spell.SpellRuntimeException;
import vazkii.psi.api.spell.param.ParamEntityListWrapper;
import vazkii.psi.api.spell.piece.PieceOperator;

public class PieceOperatorFor extends PieceOperator {
    SpellParam list;

    public PieceOperatorFor(Spell spell) {
        super(spell);
    }

    @Override
    public void initParams() {
        super.initParams();
        this.addParam(list = new ParamEntityListWrapper("List", SpellParam.GREEN, false, false));
    }

    @Override
    public Object execute(SpellContext context) throws SpellRuntimeException {


        return super.execute(context);
    }

    @Override
    public Class<?> getEvaluationType() {
        return null;
    }
}