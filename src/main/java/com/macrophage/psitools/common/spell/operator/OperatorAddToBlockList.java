package com.macrophage.psitools.common.spell.operator;

import vazkii.psi.api.spell.Spell;
import vazkii.psi.api.spell.piece.PieceOperator;

public class OperatorAddToBlockList extends PieceOperator {
    public OperatorAddToBlockList(Spell spell) {
        super(spell);
    }

    @Override
    public Class<?> getEvaluationType() {
        return null;
    }
}