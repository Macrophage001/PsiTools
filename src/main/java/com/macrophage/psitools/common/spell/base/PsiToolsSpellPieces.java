package com.macrophage.psitools.common.spell.base;

import com.macrophage.psitools.common.spell.operator.*;
import com.macrophage.psitools.common.spell.selector.block.PieceSelectorNearbyBlock;
import com.macrophage.psitools.common.spell.selector.block.PieceSelectorNearbyCommonOre;
import com.macrophage.psitools.common.spell.trick.*;
import com.macrophage.psitools.util.LibSpellPieceNames;
import net.minecraft.util.ResourceLocation;
import vazkii.psi.api.PsiAPI;
import vazkii.psi.api.spell.Spell;
import vazkii.psi.api.spell.SpellPiece;
import vazkii.psi.common.spell.base.ModSpellPieces;

public final class PsiToolsSpellPieces {
    public static ModSpellPieces.PieceContainer operatorCompareToBlock;
    public static ModSpellPieces.PieceContainer operatorEqualTo;
    public static ModSpellPieces.PieceContainer operatorLessThan;
    public static ModSpellPieces.PieceContainer operatorGreaterThan;
    public static ModSpellPieces.PieceContainer operatorOr;
    public static ModSpellPieces.PieceContainer operatorAnd;
    public static ModSpellPieces.PieceContainer operatorAvailablePsi;
    public static ModSpellPieces.PieceContainer operatorDirection;
    public static ModSpellPieces.PieceContainer operatorCompareToList;
    public static ModSpellPieces.PieceContainer operatorRandomBlock;
    public static ModSpellPieces.PieceContainer operatorBlockPosition;

    public static ModSpellPieces.PieceContainer selectorNearbyCommonOre;

    public static ModSpellPieces.PieceContainer trickVoidBlock;
    public static ModSpellPieces.PieceContainer trickRecall;
    public static ModSpellPieces.PieceContainer trickCast;
    public static ModSpellPieces.PieceContainer trickCapture;
    public static ModSpellPieces.PieceContainer trickCaptureBlock;
    public static ModSpellPieces.PieceContainer trickRelease;

    public static ModSpellPieces.PieceContainer trickKillCapture;
    public static ModSpellPieces.PieceContainer trickUsePotion;

    public PsiToolsSpellPieces() {
    }

    public static void init() {
        operatorCompareToBlock = register(PieceOperatorCompareToBlock.class, LibSpellPieceNames.OPERATOR_COMPARE_TO_BLOCK, "block_works");
        operatorEqualTo = register(PieceOperatorEqualTo.class, LibSpellPieceNames.OPERATOR_EQUAL_TO, "flow_control");
        operatorLessThan = register(PieceOperatorLessThan.class, LibSpellPieceNames.OPERATOR_LESS_THAN, "flow_control");
        operatorGreaterThan = register(PieceOperatorGreaterThan.class, LibSpellPieceNames.OPERATOR_GREATER_THAN, "flow_control");
        operatorOr = register(PieceOperatorOr.class, LibSpellPieceNames.OPERATOR_OR, "flow_control");
        operatorAnd = register(PieceOperatorAnd.class, LibSpellPieceNames.OPERATOR_AND, "flow_control");
        operatorAvailablePsi = register(PieceOperatorAvailablePsi.class, LibSpellPieceNames.OPERATOR_AVAILABLE_PSI, "flow_control");
        operatorDirection = register(PieceOperatorFacingVector.class, LibSpellPieceNames.OPERATOR_DIRECTION, "flow_control");
        operatorCompareToList = register(PieceOperatorCompareToList.class, LibSpellPieceNames.OPERATOR_COMPARE_TO_LIST, "flow_control");
        operatorRandomBlock = register(PieceOperatorRandomBlock.class, LibSpellPieceNames.OPERATOR_RANDOM_BLOCK, "block_works");
        operatorBlockPosition = register(PieceOperatorBlockPosition.class, LibSpellPieceNames.OPERATOR_BLOCK_POSITION, "block_works");

        selectorNearbyCommonOre = register(PieceSelectorNearbyCommonOre.class, LibSpellPieceNames.SELECTOR_NEARBY_COMMON_ORE, "block_works");

        trickVoidBlock = register(PieceTrickVoidBlock.class, LibSpellPieceNames.TRICK_VOID_BLOCK, "block_works");
        trickRecall = register(PieceTrickRecall.class, LibSpellPieceNames.TRICK_RECALL, "movement");
        trickCast = register(PieceTrickCast.class, LibSpellPieceNames.TRICK_CAST, "flow_control");

        trickCapture = register(PieceTrickCapture.class, LibSpellPieceNames.TRICK_CAPTURE, "movement");
        trickCaptureBlock = register(PieceTrickBlockCapture.class, LibSpellPieceNames.TRICK_CAPTURE_BLOCK, "movement");

        trickRelease = register(PieceTrickRelease.class, LibSpellPieceNames.TRICK_RELEASE, "movement");
        trickKillCapture = register(PieceTrickKillCapture.class, LibSpellPieceNames.TRICK_KILL_CAPTURE, "movement");

        trickUsePotion = register(PieceTrickUsePotion.class, LibSpellPieceNames.TRICK_USE_POTION, "flow_control");
    }

    public static ModSpellPieces.PieceContainer register(Class<? extends SpellPiece> clazz, String name, String group) {
        return register(clazz, name, group, false);
    }

    public static ModSpellPieces.PieceContainer register(Class<? extends SpellPiece> clazz, String name, String group, boolean main) {
        PsiAPI.registerSpellPieceAndTexture(new ResourceLocation("psitools", name), clazz);
        PsiAPI.addPieceToGroup(clazz, new ResourceLocation("psitools", group), main);
        return (s) -> {
            return SpellPiece.create(clazz, s);
        };
    }

    public interface PieceContainer {
        SpellPiece get(Spell var1);
    }
}