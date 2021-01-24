package com.macrophage.psitools.common.spell;

import net.minecraft.block.Block;

import javax.annotation.Nonnull;
import java.util.*;

public class BlockListWrapper implements Iterable<BlockWrapper> {
    private final List<BlockWrapper> list;
    public static final BlockListWrapper EMPTY = new BlockListWrapper(Collections.emptyList());

    private BlockListWrapper(@Nonnull List<BlockWrapper> list) {
        this.list = (List) Objects.requireNonNull(list);
    }

    public static BlockListWrapper make(@Nonnull List<BlockWrapper> list) {
        List<BlockWrapper> copy = new ArrayList();
        Iterator var2 = list.iterator();

        while(var2.hasNext()) {
            BlockWrapper e = (BlockWrapper)var2.next();
            if (e != null) {
                copy.add(e);
            }
        }

        copy.sort(BlockListWrapper::compareBlocks);
        return new BlockListWrapper(copy);
    }

    public static BlockListWrapper union(@Nonnull BlockListWrapper left, @Nonnull BlockListWrapper right) {
        List<BlockWrapper> l1 = left.list;
        List<BlockWrapper> l2 = right.list;
        List<BlockWrapper> blocks = new ArrayList(l1.size() + l2.size());
        int i = 0;
        int j = 0;

        while(i < l1.size() && j < l2.size()) {
            int cmp = compareBlocks((BlockWrapper)l1.get(i), (BlockWrapper)l2.get(j));
            if (cmp == 0) {
                ++i;
            } else {
                blocks.add(cmp < 0 ? (BlockWrapper)l1.get(i++) : (BlockWrapper)l2.get(j++));
            }
        }

        blocks.addAll(l1.subList(i, l1.size()));
        blocks.addAll(l2.subList(j, l2.size()));
        return new BlockListWrapper(blocks);
    }

    public static BlockListWrapper exclusion(@Nonnull BlockListWrapper list, @Nonnull BlockListWrapper remove) {
        List<BlockWrapper> result = new ArrayList();
        List<BlockWrapper> search = remove.list;
        Iterator var4 = list.iterator();

        while(var4.hasNext()) {
            BlockWrapper e = (BlockWrapper)var4.next();
            if (Collections.binarySearch(search, e, BlockListWrapper::compareBlocks) < 0) {
                result.add(e);
            }
        }

        return new BlockListWrapper(result);
    }

    public static BlockListWrapper intersection(@Nonnull BlockListWrapper left, @Nonnull BlockListWrapper right) {
        List<BlockWrapper> result = new ArrayList();
        List<BlockWrapper> search = right.list;
        Iterator var4 = left.iterator();

        while(var4.hasNext()) {
            BlockWrapper e = (BlockWrapper)var4.next();
            if (Collections.binarySearch(search, e, BlockListWrapper::compareBlocks) >= 0) {
                result.add(e);
            }
        }

        return new BlockListWrapper(result);
    }

    public static BlockListWrapper withAdded(@Nonnull BlockListWrapper base, @Nonnull BlockWrapper toAdd) {
        List<BlockWrapper> list = new ArrayList(base.list);
        int index = Collections.binarySearch(list, toAdd, BlockListWrapper::compareBlocks);
        if (index < 0) {
            list.add(~index, toAdd);
        }

        return new BlockListWrapper(list);
    }

    public static BlockListWrapper withRemoved(@Nonnull BlockListWrapper base, @Nonnull Block toRemove) {
        List<BlockWrapper> list = new ArrayList(base.list);
        list.remove(toRemove);
        return new BlockListWrapper(list);
    }

    public static int compareBlocks(BlockWrapper l, BlockWrapper r) {
        return l.getBlock().getRegistryName().compareTo(r.getBlock().getRegistryName());
    }

    @Override
    public Iterator<BlockWrapper> iterator() {
        return this.list.iterator();
    }

    public BlockWrapper get(int index) {
        return list.get(index);
    }
    public int size() { return this.list.size(); }

    public String toString() {
        String listToString = "[ ";
        for (BlockWrapper b : list)
        {
            listToString += b.getBlock().toString() + ", " + b.getBlockPos().toString();
        }
        listToString += " ]";
        return listToString;
    }
}