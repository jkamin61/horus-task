package org.example;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

public class Wall implements Structure {
    private final List<Block> blocks;

    public Wall(List<Block> blocks) {
        this.blocks = blocks;
    }

    @Override
    public Optional<Block> findBlockByColor(String color) {
        return findBlockByColor(color, blocks);
    }

    private Optional<Block> findBlockByColor(String color, List<Block> blocks) {
        for (Block block : blocks) {
            if (block.getColor().equals(color)) {
                return Optional.of(block);
            }
            if (block instanceof CompositeBlock) {
                Optional<Block> found = findBlockByColor(color, ((CompositeBlock) block).getBlocks());
                if (found.isPresent()) {
                    return found;
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Block> findBlocksByMaterial(String material) {
        List<Block> result = new ArrayList<>();
        findBlocksByMaterial(material, blocks, result);
        return result;
    }

    private void findBlocksByMaterial(String material, List<Block> blocks, List<Block> result) {
        for (Block block : blocks) {
            if (block.getMaterial().equals(material)) {
                result.add(block);
            }
            if (block instanceof CompositeBlock) {
                findBlocksByMaterial(material, ((CompositeBlock) block).getBlocks(), result);
            }
        }
    }

    @Override
    public int count() {
        return count(blocks);
    }

    private int count(List<Block> blocks) {
        int totalCount = 0;
        for (Block block : blocks) {
            totalCount++;
            if (block instanceof CompositeBlock) {
                totalCount += count(((CompositeBlock) block).getBlocks());
            }
        }
        return totalCount;
    }
}
