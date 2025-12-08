package org.dahoppe.aoc.solutions.day1;

import org.dahoppe.aoc.util.Files;
import org.dahoppe.aoc.util.Parsing;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class Day1Test {

    private static final String TEST_INPUT = """
            L68
            L30
            R48
            L5
            R60
            L55
            L1
            L99
            R14
            L82
            """;

    @Test
    void partATest() {
        List<Day1.Rotation> rotations = Parsing.parseLineByLine(TEST_INPUT, Day1.Rotation::new);
        assertThat(Day1.applyRotationsCountingZeroes(rotations)).isEqualTo(3);
    }

    @Test
    void partAPuzzle() {
        String inputString = Files.read("day1.txt");
        List<Day1.Rotation> rotations = Parsing.parseLineByLine(inputString, Day1.Rotation::new);
        assertThat(Day1.applyRotationsCountingZeroes(rotations)).isEqualTo(1139);
    }

    @Test
    void partBTest() {
        List<Day1.Rotation> rotations = Parsing.parseLineByLine(TEST_INPUT, Day1.Rotation::new);
        assertThat(Day1.applyRotationsCountingZeroesPartB(rotations)).isEqualTo(6);
    }

    @Test
    void partBPuzzle() {
        String inputString = Files.read("day1.txt");
        List<Day1.Rotation> rotations = Parsing.parseLineByLine(inputString, Day1.Rotation::new);
        assertThat(Day1.applyRotationsCountingZeroesPartB(rotations)).isEqualTo(6684);
    }

}