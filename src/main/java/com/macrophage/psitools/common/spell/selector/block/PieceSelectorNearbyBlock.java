package com.macrophage.psitools.common.spell.selector.block;

import com.macrophage.psitools.common.spell.BlockListWrapper;
import com.macrophage.psitools.common.spell.BlockWrapper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import vazkii.psi.api.internal.Vector3;
import vazkii.psi.api.spell.*;
import vazkii.psi.api.spell.param.ParamNumber;
import vazkii.psi.api.spell.param.ParamVector;
import vazkii.psi.api.spell.piece.PieceSelector;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public abstract class PieceSelectorNearbyBlock extends PieceSelector {
    SpellParam<Vector3> position;
    SpellParam<Number> radius;

    public PieceSelectorNearbyBlock(Spell spell) {
        super(spell);
    }

    public void initParams() {
        this.addParam(this.position = new ParamVector("psi.spellparam.position", SpellParam.BLUE, true, false));
        this.addParam(this.radius = new ParamNumber("psi.spellparam.radius", SpellParam.GREEN, true, true));
    }

    public void addToMetadata(SpellMetadata meta) throws SpellCompilationException {
        super.addToMetadata(meta);
        Double radiusVal = ((Number)this.getParamEvaluationeOrDefault(this.radius, 64.0D)).doubleValue();
        if (radiusVal <= 0.0D) {
            throw new SpellCompilationException("psi.spellerror.nonpositivevalue", this.x, this.y);
        }
    }

    @Override
    public Object execute(SpellContext context) throws SpellRuntimeException {
        Vector3 positionVal = (Vector3)this.getParamValueOrDefault(context, this.position, Vector3.fromVec3d(context.focalPoint.getPositionVec()));
        double radiusVal = ((Number)this.getParamValueOrDefault(context, this.radius, 64.0D)).doubleValue();
        if (!context.isInRadius(positionVal)) {
            throw new SpellRuntimeException("psi.spellerror.outsideradius");
        } else {
            Predicate<Block> pred = this.getTargetPredicate(context);
            List<BlockWrapper> list = new ArrayList<>();
            for (double z = positionVal.z - radiusVal; z < positionVal.z + radiusVal; z++)
            {
                for (double y = positionVal.y - radiusVal; y < positionVal.y + radiusVal; y++)
                {
                    for (double x = positionVal.x - radiusVal; x < positionVal.x + radiusVal; x++)
                    {
                        BlockState state = context.caster.getEntityWorld().getBlockState(new BlockPos(x, y, z));
                        if (state != null && !state.isAir(context.caster.world, new BlockPos(x, y, z)) && pred.test(state.getBlock()))
                        {
                            list.add(new BlockWrapper(state.getBlock(), new BlockPos(x, y, z)));
                        }
                    }
                }
            }
            return BlockListWrapper.make(list);
        }
    }

    public abstract Predicate<Block> getTargetPredicate(SpellContext var1);

    @Override
    public Class<?> getEvaluationType() {
        return BlockListWrapper.class;
    }
}