package com.macrophage.psitools.common.spell.trick;

import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.BlockBlobConfig;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fluids.IFluidBlock;
import vazkii.psi.api.internal.Vector3;
import vazkii.psi.api.spell.*;
import vazkii.psi.api.spell.param.ParamVector;
import vazkii.psi.api.spell.piece.PieceTrick;

public class PieceTrickVoidBlock extends PieceTrick {
    SpellParam position;

    public PieceTrickVoidBlock(Spell spell) {
        super(spell);
    }

    public void initParams() {
        this.addParam(this.position = new ParamVector("psi.spellparam.position", SpellParam.BLUE, false, false));
    }

    public void addToMetadata(SpellMetadata meta) throws SpellCompilationException {
        super.addToMetadata(meta);
        meta.addStat(EnumSpellStat.POTENCY, 150);
        meta.addStat(EnumSpellStat.COST, 95);
    }

    public Object execute(SpellContext context) throws SpellRuntimeException {
        Vector3 positionVal = (Vector3)this.getParamValue(context, this.position);
        if(positionVal == null) {
            throw new SpellRuntimeException("psi.spellerror.nullvector");
        } else if(!context.isInRadius(positionVal)) {
            throw new SpellRuntimeException("psi.spellerror.outsideradius");
        } else {
            BlockPos pos = positionVal.toBlockPos();
            removeBlockWithDrops(context, context.caster, context.caster.world, context.tool, pos, true);
            return null;
        }
    }

    public static void removeBlockWithDrops(SpellContext context, PlayerEntity player, World world, ItemStack tool, BlockPos pos, boolean particles) {
        if(world.isBlockLoaded(pos) && (context.positionBroken == null || !pos.equals(context.positionBroken.getPos())) && world.isBlockModifiable(player, pos)) {
            BlockState state = world.getBlockState(pos);
            Block block = state.getBlock();
            if(!world.isRemote && block != null && !block.isAir(state, world, pos) && !(block instanceof ILiquidContainer) && !(block instanceof IFluidBlock) && block.getPlayerRelativeBlockHardness(state, player, world, pos) > 0.0F) {
                if(!ForgeHooks.canHarvestBlock(state, player, world, pos)) {
                    return;
                }

                BlockEvent.BreakEvent event = new BlockEvent.BreakEvent(world, pos, state, player);
                MinecraftForge.EVENT_BUS.post(event);
                if(!event.isCanceled()) {
                    if(!player.isCreative()) {
                        TileEntity tile = world.getTileEntity(pos);
                        world.getBlockState(pos);
                        block.removedByPlayer(state, world, pos, player, false, block.getFluidState(world.getBlockState(pos)));
                        block.onBlockHarvested(world, pos, state, player);
                    } else {
                        world.setBlockState(pos, Blocks.AIR.getDefaultState());
                    }
                }

                if(particles) {
                    world.playEvent(2001, pos, Block.getStateId(state));
                }
            }

        }
    }
}
