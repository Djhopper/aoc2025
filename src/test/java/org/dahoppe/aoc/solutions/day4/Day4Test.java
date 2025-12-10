package org.dahoppe.aoc.solutions.day4;

import org.dahoppe.aoc.util.Files;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Day4Test {

    private final static String TEST_INPUT = """
            ..@@.@@@@.
            @@@.@.@.@@
            @@@@@.@.@@
            @.@@@@..@.
            @@.@@@@.@@
            .@@@@@@@.@
            .@.@.@.@@@
            @.@@@.@@@@
            .@@@@@@@@.
            @.@.@@@.@.
            """;

    @Test
    void partATest() {
        assertThat(Day4.solveAForInput(TEST_INPUT)).isEqualTo(13);
    }

    @Test
    void partAPuzzle() {
        assertThat(Day4.solveAForInput(Files.read("Day4.txt"))).isEqualTo(1518);
    }

    @Test
    void partBTest() {
        assertThat(Day4.solveBForInput(TEST_INPUT)).isEqualTo(43);
    }

    @Test
    void partBPuzzle() {
        assertThat(Day4.solveBForInput(Files.read("Day4.txt"))).isEqualTo(8665);
    }

}