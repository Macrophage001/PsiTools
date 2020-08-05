package com.macrophage.psitools.common.spell.operator;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import vazkii.psi.api.internal.Vector3;
import vazkii.psi.api.spell.Spell;
import vazkii.psi.api.spell.SpellContext;
import vazkii.psi.api.spell.SpellParam;
import vazkii.psi.api.spell.SpellRuntimeException;
import vazkii.psi.api.spell.param.ParamVector;
import vazkii.psi.api.spell.piece.PieceOperator;

import java.util.Objects;

public class PieceOperatorCompareToBlock extends PieceOperator {
    SpellParam position;

    public PieceOperatorCompareToBlock(Spell spell) {
        super(spell);
    }

    @Override
    public void initParams() {
        super.initParams();
        this.addParam(position = new ParamVector("Position", SpellParam.BLUE, false, false));
    }

    @Override
    public Object execute(SpellContext context) throws SpellRuntimeException {
        Vector3 vec3 = (Vector3)this.getParamValue(context, this.position);
        if (vec3 == null) {
            throw new SpellRuntimeException("psi.spellerror.nullvector");
        } else if (!context.isInRadius(vec3)) {
            throw new SpellRuntimeException("psi.spellerror.outsideradius");
        } else {
            BlockPos blockPos = new BlockPos(vec3.x, vec3.y, vec3.z);
            BlockState blockState = context.caster.world.getBlockState(blockPos);
            Block block = blockState.getBlock();
            Block inventoryBlock = Block.getBlockFromItem(context.caster.inventory.getStackInSlot(context.targetSlot).getItem());

            return Objects.equals(block.getRegistryName(), inventoryBlock.getRegistryName()) ? 1.0 : 0.0;
        }
    }

    @Override
    public Class<?> getEvaluationType() {
        return Double.class;
    }

    @Override
    public String getUnlocalizedName() {
        return "psitools.spellpiece." + this.registryKey;
    }

    @Override
    public String getUnlocalizedDesc() {
        return "psitools.spellpiece." + this.registryKey + ".desc";
    }
}
