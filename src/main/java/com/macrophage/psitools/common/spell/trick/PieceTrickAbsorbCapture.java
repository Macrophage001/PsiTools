package com.macrophage.psitools.common.spell.trick;

import com.macrophage.psitools.common.item.ItemPsiCore;
import com.macrophage.psitools.common.item.ItemStabilizedPsiCore;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import vazkii.psi.api.PsiAPI;
import vazkii.psi.api.spell.*;
import vazkii.psi.api.spell.piece.PieceTrick;
import vazkii.psi.common.core.handler.PlayerDataHandler;


/*
    Still need to figure out best way to accezs available psi because...
 */
public class PieceTrickAbsorbCapture extends PieceTrick {
    private final int ENTITY_COST = 1;
    private final int ABSORPTION_AMOUNT = 250;

    public PieceTrickAbsorbCapture(Spell spell) {
        super(spell);
    }

    @Override
    public void addToMetadata(SpellMetadata meta) throws SpellCompilationException {
        super.addToMetadata(meta);
        meta.addStat(EnumSpellStat.POTENCY, 150);
        meta.addStat(EnumSpellStat.COST, 0);
    }

    @Override
    public Object execute(SpellContext context) throws SpellRuntimeException {
        PlayerDataHandler.PlayerData data = PlayerDataHandler.get(context.caster);
        IInventory playerInventory = context.caster.inventory;
        ItemStack stabilizedPsiCore = null;
        for (int i = 0; i < playerInventory.getSizeInventory(); i++) {
            if (playerInventory.getStackInSlot(i).getItem() instanceof ItemStabilizedPsiCore) {
                stabilizedPsiCore = playerInventory.getStackInSlot(i);
                break;
            }
        }

        long entity_count = 0;
        if (stabilizedPsiCore != null)
        {
            entity_count = stabilizedPsiCore.getTag().getLong("entity_count");
            if (entity_count > 0 && data.availablePsi != data.totalPsi)
            {
                entity_count -= ENTITY_COST;
                data.availablePsi = data.availablePsi + ABSORPTION_AMOUNT; // ...this doesn't work...
                stabilizedPsiCore.getTag().putLong("entity_count", entity_count);
            }
        }
        else
        {
            throw new SpellRuntimeException("Must have a Stabilized Psi Core to work!");
        }

        return super.execute(context);
    }
}