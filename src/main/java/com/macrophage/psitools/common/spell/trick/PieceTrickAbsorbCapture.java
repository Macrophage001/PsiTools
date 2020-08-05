package com.macrophage.psitools.common.spell.trick;

import vazkii.psi.api.spell.*;
import vazkii.psi.api.spell.piece.PieceTrick;
import vazkii.psi.common.core.handler.PlayerDataHandler;

public class PieceTrickAbsorbCapture extends PieceTrick {
    public PieceTrickAbsorbCapture(Spell spell) {
        super(spell);
    }

    @Override
    public void addToMetadata(SpellMetadata meta) throws SpellCompilationException {
        super.addToMetadata(meta);
        meta.addStat(EnumSpellStat.POTENCY, 150);
        meta.addStat(EnumSpellStat.COST, 200);
    }

    @Override
    public Object execute(SpellContext context) throws SpellRuntimeException {
        PlayerDataHandler.PlayerData playerData = PlayerDataHandler.get(context.caster);
        playerData.totalPsi += 500;
        return super.execute(context);
    }
}