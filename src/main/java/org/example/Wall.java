package org.example;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class Wall implements Structure {
    private final List<Block> blocks;

    public Wall(List<Block> blocks) {
        this.blocks = blocks;
    }

    @Override
    public Optional<Block> findBlockByColor(String color) {
        return blocks.stream()
                .flatMap(this::flatten)
                .filter(block -> block.getColor().equals(color))
                .findAny();
    }

    @Override
    public List<Block> findBlocksByMaterial(String material) {
        return blocks.stream()
                .flatMap(this::flatten)
                .filter(block -> block.getMaterial().equals(material))
                .toList();
    }

    @Override
    public int count() {
        return (int) blocks.stream()
                .flatMap(this::flatten)
                .count();
    }

    private Stream<Block> flatten(Block block) {
        if (block instanceof CompositeBlock) {
            return Stream.concat(
                    Stream.of(block),
                    ((CompositeBlock) block).getBlocks().stream().flatMap(this::flatten)
            );
        } else {
            return Stream.of(block);
        }
    }
}
