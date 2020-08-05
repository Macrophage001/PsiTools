package com.macrophage.psitools.common.spell.operator;

import vazkii.psi.api.spell.Spell;
import vazkii.psi.api.spell.SpellContext;
import vazkii.psi.api.spell.SpellParam;
import vazkii.psi.api.spell.SpellRuntimeException;
import vazkii.psi.api.spell.param.ParamNumber;
import vazkii.psi.api.spell.piece.PieceOperator;

import java.util.Objects;

public class PieceOperatorEqualTo extends PieceOperator {
    SpellParam number1;
    SpellParam number2;

    public PieceOperatorEqualTo(Spell spell) {
        super(spell);
    }

    @Override
    public void initParams() {
        this.addParam(number1 = new ParamNumber("psi.spellparam.number1", SpellParam.GREEN, false, false));
        this.addParam(number2 = new ParamNumber("psi.spellparam.number2", SpellParam.GREEN, false, false));
        super.initParams();
    }

    @Override
    public Object execute(SpellContext context) throws SpellRuntimeException {
        Double num1 = (Double) this.getParamValue(context, number1);
        Double num2 = (Double) this.getParamValue(context, number2);

        return Objects.equals(num1, num2) ? 1.0 : 0.0;
    }

    @Override
    public Class<?> getEvaluationType() {
        return Double.class;
    }
}
