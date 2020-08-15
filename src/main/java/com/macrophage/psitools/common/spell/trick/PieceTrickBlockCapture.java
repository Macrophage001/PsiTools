package com.macrophage.psitools.common.spell.trick;

import com.macrophage.psitools.common.init.ModItems;
import com.macrophage.psitools.common.item.ItemBlockPsiCore;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import vazkii.psi.api.internal.Vector3;
import vazkii.psi.api.spell.*;
import vazkii.psi.api.spell.param.ParamVector;
import vazkii.psi.api.spell.piece.PieceTrick;

public class PieceTrickBlockCapture extends PieceTrick {
    SpellParam<Vector3> position;

    public PieceTrickBlockCapture(Spell spell) {
        super(spell);
    }

    @Override
    public void initParams() {
        super.initParams();
        this.addParam(position = new ParamVector("Position", SpellParam.GREEN, false, false));
    }

    @Override
    public void addToMetadata(SpellMetadata meta) throws SpellCompilationException, ArithmeticException {
        super.addToMetadata(meta);
        meta.addStat(EnumSpellStat.POTENCY, 75);
        meta.addStat(EnumSpellStat.COST, 25);
    }

    @Override
    public Object execute(SpellContext context) throws SpellRuntimeException {
        Vector3 positionVal = this.getParamValue(context, position);

        BlockPos blockPos = new BlockPos(positionVal.x, positionVal.y, positionVal.z);

        BlockState state = context.caster.world.getBlockState(blockPos);

        ItemStack blockPsiCore = new ItemStack(ModItems.block_psi_core.get(), 1);
        Block block = state.getBlock();
        if (block.getDefaultState().getBlockHardness(context.caster.world, blockPos) > 0)
        {
            ((ItemBlockPsiCore) blockPsiCore.getItem()).setCaptured(blockPsiCore, block);
            ((ItemBlockPsiCore) blockPsiCore.getItem()).setActive(blockPsiCore, true);
            blockPsiCore.setDisplayName(new StringTextComponent("Psi Core (" + block.getNameTextComponent().getFormattedText() + ")"));
            ItemEntity blockPsiCoreEntityItem = new ItemEntity(context.caster.world, blockPos.getX(), blockPos.getY(), blockPos.getZ(), blockPsiCore);
            context.caster.world.addEntity(blockPsiCoreEntityItem);
            context.caster.world.setBlockState(blockPos, Blocks.AIR.getDefaultState());
        }
        else
        {
            throw new SpellRuntimeException("Invalid Block");
        }

        return super.execute(context);
    }
}