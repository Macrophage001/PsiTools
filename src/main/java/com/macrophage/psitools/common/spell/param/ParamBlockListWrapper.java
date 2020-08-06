package com.macrophage.psitools.common.spell.param;

import com.macrophage.psitools.common.spell.BlockListWrapper;
import vazkii.psi.api.spell.param.ParamSpecific;

public class ParamBlockListWrapper extends ParamSpecific<BlockListWrapper> {
    public ParamBlockListWrapper(String name, int color, boolean canDisable, boolean constant) {
        super(name, color, canDisable, constant);
    }

    @Override
    protected Class<BlockListWrapper> getRequiredType() {
        return BlockListWrapper.class;
    }
}