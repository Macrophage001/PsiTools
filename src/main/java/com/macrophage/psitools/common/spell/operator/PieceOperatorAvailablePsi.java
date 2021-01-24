package com.macrophage.psitools.common.spell.operator;

import vazkii.psi.api.spell.Spell;
import vazkii.psi.api.spell.SpellContext;
import vazkii.psi.api.spell.SpellRuntimeException;
import vazkii.psi.api.spell.piece.PieceOperator;
import vazkii.psi.common.core.handler.PlayerDataHandler;

public class PieceOperatorAvailablePsi extends PieceOperator {
    public PieceOperatorAvailablePsi(Spell spell) {
        super(spell);
    }

    @Override
    public Object execute(SpellContext context) throws SpellRuntimeException {
        return (double) PlayerDataHandler.get(context.caster).getAvailablePsi();
    }

    @Override
    public Class<?> getEvaluationType() {
        return Double.class;
    }
}
