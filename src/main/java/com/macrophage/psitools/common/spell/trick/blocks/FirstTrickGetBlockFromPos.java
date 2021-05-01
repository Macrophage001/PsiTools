package com.macrophage.psitools.common.spell.trick.blocks;

import com.macrophage.psitools.common.spell.BlockWrapper;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import vazkii.psi.api.internal.Vector3;
import vazkii.psi.api.spell.Spell;
import vazkii.psi.api.spell.SpellContext;
import vazkii.psi.api.spell.SpellParam;
import vazkii.psi.api.spell.SpellRuntimeException;
import vazkii.psi.api.spell.param.ParamSpecific;
import vazkii.psi.api.spell.piece.PieceOperator;

public class FirstTrickGetBlockFromPos extends PieceOperator {
    SpellParam<Vector3> target;

    public FirstTrickGetBlockFromPos(Spell spell) {
        super(spell);
    }

    @Override
    public void initParams() {
        super.initParams();
        this.addParam(target = new ParamSpecific<Vector3>("Target", SpellParam.GREEN, false, false) {

            @Override
            protected Class<Vector3> getRequiredType() {
                return Vector3.class;
            }
        });
    }

    @Override
    public Object execute(SpellContext context) throws SpellRuntimeException {
        Vector3 vec3 = this.getParamValue(context,this.target);
        if (vec3 == null || context.caster.world.getBlockState(new BlockPos(vec3.toVec3D())).getBlock() == null) {
            throw new SpellRuntimeException("psi.spellerror.nulltarget");
        }else{
           Block b =  context.caster.world.getBlockState(new BlockPos(vec3.toVec3D())).getBlock();
           return b;
        }
    }

    @Override
    public Class<?> getEvaluationType() {
        return BlockWrapper.class;
    }
}
