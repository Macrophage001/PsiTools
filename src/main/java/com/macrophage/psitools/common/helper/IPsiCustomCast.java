package com.macrophage.psitools.common.helper;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import vazkii.psi.api.cad.EnumCADStat;
import vazkii.psi.api.cad.ICAD;
import vazkii.psi.api.internal.PsiRenderHelper;
import vazkii.psi.api.internal.Vector3;
import vazkii.psi.api.spell.*;
import vazkii.psi.common.Psi;
import vazkii.psi.common.core.handler.PlayerDataHandler;
import vazkii.psi.common.core.handler.PsiSoundHandler;
import vazkii.psi.common.item.ItemCAD;

import java.util.function.Consumer;

public interface IPsiCustomCast {
    static boolean cast(World world, PlayerEntity player, PlayerDataHandler.PlayerData data, ItemStack bullet, ItemStack cad, int cd, int particles, float sound, double cost_modifier, Consumer<SpellContext> predicate) {
        if (!data.overflowed && data.getAvailablePsi() > 0 && !cad.isEmpty() && !bullet.isEmpty() && ISpellAcceptor.hasSpell(bullet) && ItemCAD.isTruePlayer(player)) {
            ISpellAcceptor spellContainer = ISpellAcceptor.acceptor(bullet);
            Spell spell = spellContainer.getSpell();
            SpellContext context = (new SpellContext()).setPlayer(player).setSpell(spell);
            if (predicate != null) {
                predicate.accept(context);
            }

            if (context.isValid()) {
                if (context.cspell.metadata.evaluateAgainst(cad)) {
                    int cost = getRealCost(cad, bullet, (Integer)context.cspell.metadata.stats.get(EnumSpellStat.COST), cost_modifier);

                    // Reduce the potency of the spell being cast.
                    int potency = context.cspell.metadata.stats.get(EnumSpellStat.POTENCY);

                    PreSpellCastEvent event = new PreSpellCastEvent(cost, sound, particles, cd, spell, context, player, data, cad, bullet);
                    if (MinecraftForge.EVENT_BUS.post(event)) {
                        String cancelMessage = event.getCancellationMessage();
                        if (cancelMessage != null && !cancelMessage.isEmpty()) {
                            player.sendMessage((new TranslationTextComponent(cancelMessage, new Object[0])).setStyle((new Style()).setColor(TextFormatting.RED)));
                        }

                        return false;
                    }

                    cd = event.getCooldown();
                    particles = event.getParticles();
                    sound = event.getSound();
                    cost = event.getCost();
                    spell = event.getSpell();
                    context = event.getContext();
                    if (cost > 0) {
                        data.deductPsi(cost, cd, true);
                    }

                    if (cost != 0 && sound > 0.0F) {
                        if (!world.isRemote) {
                            world.playSound((PlayerEntity)null, player.getPosX(), player.getPosY(), player.getPosZ(), PsiSoundHandler.cadShoot, SoundCategory.PLAYERS, sound, (float)(0.5D + Math.random() * 0.5D));
                        } else {
                            int color = Psi.proxy.getColorForCAD(cad);
                            float r = (float) PsiRenderHelper.r(color) / 255.0F;
                            float g = (float)PsiRenderHelper.g(color) / 255.0F;
                            float b = (float)PsiRenderHelper.b(color) / 255.0F;

                            for(int i = 0; i < particles; ++i) {
                                double x = player.getPosX() + (Math.random() - 0.5D) * 2.1D * (double)player.getWidth();
                                double y = player.getPosY() - player.getYOffset();
                                double z = player.getPosZ() + (Math.random() - 0.5D) * 2.1D * (double)player.getWidth();
                                float grav = -0.15F - (float)Math.random() * 0.03F;
                                Psi.proxy.sparkleFX(x, y, z, r, g, b, grav, 0.25F, 15);
                            }

                            double x = player.getPosX();
                            double y = player.getPosY() + (double)player.getEyeHeight() - 0.1D;
                            double z = player.getPosZ();
                            Vector3 lookOrig = new Vector3(player.getLookVec());

                            for(int i = 0; i < 25; ++i) {
                                Vector3 look = lookOrig.copy();
                                double spread = 0.25D;
                                look.x += (Math.random() - 0.5D) * spread;
                                look.y += (Math.random() - 0.5D) * spread;
                                look.z += (Math.random() - 0.5D) * spread;
                                look.normalize().multiply(0.15D);
                                Psi.proxy.sparkleFX(x, y, z, r, g, b, (float)look.x, (float)look.y, (float)look.z, 0.3F, 5);
                            }
                        }
                    }

                    if (!world.isRemote) {
                        spellContainer.castSpell(context);
                    }

                    MinecraftForge.EVENT_BUS.post(new SpellCastEvent(spell, context, player, data, cad, bullet));
                    return true;
                }

                if (!world.isRemote) {
                    player.sendMessage((new TranslationTextComponent("psimisc.weak_cad", new Object[0])).setStyle((new Style()).setColor(TextFormatting.RED)));
                }
            }
        }

        return false;
    }

    static int getRealCost(ItemStack stack, ItemStack bullet, int cost, double modifier) {
        if (!stack.isEmpty() && stack.getItem() instanceof ICAD) {
            int eff = ((ICAD)stack.getItem()).getStatValue(stack, EnumCADStat.EFFICIENCY);
            if (eff == -1) {
                return -1;
            } else if (eff == 0) {
                return cost;
            } else {
                double effPercentile = (double)eff / 100.0D;
                double procCost = (double)cost / effPercentile;
                if (!bullet.isEmpty() && ISpellAcceptor.isContainer(bullet)) {
                    procCost *= ISpellAcceptor.acceptor(bullet).getCostModifier();
                }

                procCost *= modifier;
                return (int)procCost;
            }
        } else {
            return cost;
        }
    }

    default Double costModifier() {
        return 0.833D;
    }
}