package org.dahoppe.aoc.solutions.day5;

import org.dahoppe.aoc.util.Parsing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

public class Day5 {

    private static final Logger log = LoggerFactory.getLogger(Day5.class);

    static class InventoryManagementSystem {

        List<Range> freshRanges;
        List<Long> ingredientIds;

        InventoryManagementSystem(String input) {
            String[] split = Parsing.splitOnDoubleNewLine(input).toArray(String[]::new);
            freshRanges = Parsing.splitOnNewLine(split[0])
                    .map(Range::fromInputLine)
                    .collect(Collectors.toList());
            ingredientIds = Parsing.splitOnNewLine(split[1])
                    .map(Long::parseLong)
                    .collect(Collectors.toList());
        }

        boolean isFresh(long ingredientId) {
            return freshRanges.stream().anyMatch(range -> range.contains(ingredientId));
        }

        void mergeRanges() {
            while (mergeTwoRanges()) {}
        }

        /*
        Returns true if it managed to merge two ranges.
         */
        private boolean mergeTwoRanges() {
            for (Range a : freshRanges) {
                for (Range b : freshRanges) {
                    if (a != b && a.overlaps(b)) {
                        freshRanges.remove(a);
                        freshRanges.remove(b);
                        freshRanges.add(a.merge(b));
                        log.info("Merging {} and {}, producing {}", a, b, a.merge(b));
                        return true;
                    }
                }
            }
            return false;
        }

    }

    record Range(long from, long to) {
        boolean contains(long id) {
            return from <= id && id <= to;
        }

        boolean overlaps(Range range) {
            return contains(range.from()) || contains(range.to()) || range.contains(from) || range.contains(to);
        }

        Range merge(Range range) {
            return new Range(Math.min(range.from(), from), Math.max(range.to(), to));
        }

        long size() {
            return 1 + to() - from();
        }

        static Range fromInputLine(String inputLine) {
            String[] split = Parsing.split(inputLine, "-").toArray(String[]::new);
            return new Range(Long.parseLong(split[0]), Long.parseLong(split[1]));
        }
    }

    static long solveAForInput(String input) {
        InventoryManagementSystem inventoryManagementSystem = new InventoryManagementSystem(input);
        return inventoryManagementSystem.ingredientIds.stream()
                .filter(inventoryManagementSystem::isFresh)
                .count();
    }

    static long solveBForInput(String input) {
        InventoryManagementSystem inventoryManagementSystem = new InventoryManagementSystem(input);
        inventoryManagementSystem.mergeRanges();
        log.info("{} remaining ranges: {}", inventoryManagementSystem.freshRanges.size(), inventoryManagementSystem.freshRanges);
        return inventoryManagementSystem.freshRanges.stream().mapToLong(Range::size).sum();
    }

}
