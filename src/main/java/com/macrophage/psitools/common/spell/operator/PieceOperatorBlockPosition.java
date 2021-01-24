package com.macrophage.psitools.common.spell.operator;

import com.macrophage.psitools.common.spell.BlockWrapper;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import vazkii.psi.api.internal.Vector3;
import vazkii.psi.api.spell.Spell;
import vazkii.psi.api.spell.SpellContext;
import vazkii.psi.api.spell.SpellParam;
import vazkii.psi.api.spell.SpellRuntimeException;
import vazkii.psi.api.spell.param.ParamSpecific;
import vazkii.psi.api.spell.piece.PieceOperator;

public class PieceOperatorBlockPosition extends PieceOperator {
    SpellParam<BlockWrapper> target;

    public PieceOperatorBlockPosition(Spell spell) {
        super(spell);
    }

    @Override
    public void initParams() {
        super.initParams();
        this.addParam(target = new ParamSpecific<BlockWrapper>("Target", SpellParam.GREEN, false, false) {
            @Override
            protected Class<BlockWrapper> getRequiredType() {
                return BlockWrapper.class;
            }
        });
    }

    @Override
    public Object execute(SpellContext context) throws SpellRuntimeException {
        BlockWrapper b = (BlockWrapper)this.getParamValue(context, this.target);
        if (b == null) {
            throw new SpellRuntimeException("psi.spellerror.nulltarget");
        } else {
            Vector3 vec = Vector3.fromBlockPos(b.getBlockPos());
            return vec;
        }
    }

    @Override
    public Class<?> getEvaluationType() {
        return Vector3.class;
    }
}