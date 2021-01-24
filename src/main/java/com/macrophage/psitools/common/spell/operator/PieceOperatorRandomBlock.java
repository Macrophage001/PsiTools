package com.macrophage.psitools.common.spell.operator;

import com.macrophage.psitools.common.spell.BlockListWrapper;
import com.macrophage.psitools.common.spell.BlockWrapper;
import com.macrophage.psitools.common.spell.param.ParamBlockListWrapper;
import net.minecraft.block.Block;
import vazkii.psi.api.spell.Spell;
import vazkii.psi.api.spell.SpellContext;
import vazkii.psi.api.spell.SpellParam;
import vazkii.psi.api.spell.SpellRuntimeException;
import vazkii.psi.api.spell.piece.PieceOperator;

public class PieceOperatorRandomBlock extends PieceOperator {
    SpellParam<BlockListWrapper> list;

    public PieceOperatorRandomBlock(Spell spell) {
        super(spell);
    }

    @Override
    public void initParams() {
        super.initParams();
        this.addParam(list = new ParamBlockListWrapper("List", SpellParam.GREEN, false, false));
    }

    @Override
    public Object execute(SpellContext context) throws SpellRuntimeException {
        BlockListWrapper listWrapper = (BlockListWrapper) this.getParamValue(context, list);
        if (listWrapper.size() == 0)
        {
            throw new SpellRuntimeException("psi.spellerror.nulltarget");
        }
        else
        {
            return listWrapper.get(context.caster.getEntityWorld().rand.nextInt(listWrapper.size()));
        }
    }

    @Override
    public Class<?> getEvaluationType() {
        return BlockWrapper.class;
    }
}