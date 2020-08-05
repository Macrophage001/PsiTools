package com.macrophage.psitools.common.spell.operator;

import net.minecraft.util.Direction;
import vazkii.psi.api.internal.Vector3;
import vazkii.psi.api.spell.Spell;
import vazkii.psi.api.spell.SpellContext;
import vazkii.psi.api.spell.SpellParam;
import vazkii.psi.api.spell.SpellRuntimeException;
import vazkii.psi.api.spell.param.ParamNumber;
import vazkii.psi.api.spell.piece.PieceOperator;

// 0 - Left of player, 1 - Right of player, 2 - In front of player, 3 - Behind the player.
public class PieceOperatorFacingVector extends PieceOperator {
    SpellParam direction;

    public PieceOperatorFacingVector(Spell spell) {
        super(spell);
    }

    @Override
    public void initParams() {
        super.initParams();
        this.addParam(direction = new ParamNumber("Direction", SpellParam.BLUE, false, false));
    }

    @Override
    public Object execute(SpellContext context) throws SpellRuntimeException {
        Direction enumFacing = context.caster.getHorizontalFacing();
        Integer dir = ((Double) getParamValue(context, direction)).intValue();

        if (dir != null) {
            switch (enumFacing) {
                case NORTH:
                    if (dir == 0) return new Vector3(-1, 0, 0);
                    else if (dir == 1) return new Vector3(1, 0, 0);
                    else if (dir == 2) return new Vector3(0, 0, -1);
                    else if (dir == 3) return new Vector3(0, 0, 1);
                    break;
                case SOUTH:
                    if (dir == 0) return new Vector3(1, 0, 0);
                    else if (dir == 1) return new Vector3(-1, 0, 0);
                    else if (dir == 2) return new Vector3(0, 0, 1);
                    else if (dir == 3) return new Vector3(0, 0, -1);
                    break;
                case EAST:
                    if (dir == 0) return new Vector3(0, 0, -1);
                    else if (dir == 1) return new Vector3(0, 0, 1);
                    else if (dir == 2) return new Vector3(1, 0, 0);
                    else if (dir == 3) return new Vector3(-1, 0, 0);
                    break;
                case WEST:
                    if (dir == 0) return new Vector3(0, 0, 1);
                    else if (dir == 1) return new Vector3(0, 0, -1);
                    else if (dir == 2) return new Vector3(-1, 0, 0);
                    else if (dir == 3) return new Vector3(1, 0, 0);
                    break;
            }
        } else {
            throw new SpellRuntimeException("No value given! Requires 0 or 1!");
        }

        return super.execute(context);
    }

    @Override
    public Class<?> getEvaluationType() {
        return Vector3.class;
    }
}
