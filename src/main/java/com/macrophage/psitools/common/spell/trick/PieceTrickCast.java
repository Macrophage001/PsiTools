package com.macrophage.psitools.common.spell.trick;

import com.macrophage.psitools.common.helper.IPsiCustomCast;
import net.minecraft.item.ItemStack;
import vazkii.psi.api.PsiAPI;
import vazkii.psi.api.cad.ISocketable;
import vazkii.psi.api.spell.*;
import vazkii.psi.api.spell.param.ParamNumber;
import vazkii.psi.api.spell.piece.PieceTrick;
import vazkii.psi.common.core.handler.PlayerDataHandler;
import vazkii.psi.common.core.handler.capability.CADData;

public class PieceTrickCast extends PieceTrick implements IPsiCustomCast {
    SpellParam<Number> spellSlot;

    public PieceTrickCast(Spell spell) {
        super(spell);
    }

    @Override
    public void initParams() {
        this.addParam(this.spellSlot = new ParamNumber("psi.spellparam.number", SpellParam.BLUE, false, false));
    }

    public void addToMetadata(SpellMetadata meta) throws SpellCompilationException {
        super.addToMetadata(meta);
        meta.addStat(EnumSpellStat.POTENCY, 175);
        meta.addStat(EnumSpellStat.COST, 500);
    }

    @Override
    public Object execute(SpellContext context) throws SpellRuntimeException {
        int slot = ((Number)this.getParamValue(context, spellSlot)).intValue();

        ItemStack cad = PsiAPI.getPlayerCAD(context.caster);

        ISocketable sockets = getSocketable(cad);
        ItemStack bullet = sockets.getBulletInSocket(slot);

        IPsiCustomCast.cast(context.caster.world, context.caster, PlayerDataHandler.get(context.caster), bullet, cad, 40, 0, 0, costModifier(), null);

        return super.execute(context);
    }

    private ISocketable getSocketable(ItemStack stack) {
        return (ISocketable)stack.getCapability(PsiAPI.SOCKETABLE_CAPABILITY).orElseGet(() -> {
            return new CADData(stack);
        });
    }
}
