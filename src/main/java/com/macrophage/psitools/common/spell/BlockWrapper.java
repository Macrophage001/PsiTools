package com.macrophage.psitools.common.spell;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;

public class BlockWrapper {
    private Block block;
    private BlockPos blockPos;

    public BlockWrapper(Block block, BlockPos blockPos)
    {
        this.block = block;
        this.blockPos = blockPos;
    }

    public Block getBlock() { return block; }
    public BlockPos getBlockPos() { return blockPos; }
}