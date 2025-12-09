package org.dahoppe.aoc.solutions.day3;

import org.dahoppe.aoc.util.Files;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Day3Test {

    private final static String TEST_INPUT = """
            987654321111111
            811111111111119
            234234234234278
            818181911112111
            """;

    @Test
    void partATest() {
        assertThat(Day3.solveForInput(TEST_INPUT, 2)).isEqualTo(357);
    }

    @Test
    void partAPuzzle() {
        assertThat(Day3.solveForInput(Files.read("Day3.txt"), 2)).isEqualTo(17301);
    }

    @Test
    void partBTest() {
        assertThat(Day3.solveForInput(TEST_INPUT, 12)).isEqualTo(3121910778619L);
    }

    @Test
    void partBPuzzle() {
        assertThat(Day3.solveForInput(Files.read("Day3.txt"), 12)).isEqualTo(172162399742349L);
    }

}