package org.dahoppe.aoc.solutions.day2;

import org.dahoppe.aoc.util.Files;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Day2Test {

    private final static String TEST_INPUT =
            "11-22,95-115,998-1012,1188511880-1188511890,222220-222224," +
            "1698522-1698528,446443-446449,38593856-38593862,565653-565659," +
            "824824821-824824827,2121212118-2121212124";

    @Test
    void partATest() {
        assertThat(Day2.solveForInput(TEST_INPUT, Day2::isInvalidPartA)).isEqualTo(1227775554L);
    }

    @Test
    void partAPuzzle() {
        assertThat(Day2.solveForInput(Files.read("day2.txt"), Day2::isInvalidPartA)).isEqualTo(28146997880L);
    }

    @Test
    void partBTest() {
        assertThat(Day2.solveForInput(TEST_INPUT, Day2::isInvalidPartB)).isEqualTo(4174379265L);
    }

    @Test
    void partBPuzzle() {
        assertThat(Day2.solveForInput(Files.read("day2.txt"), Day2::isInvalidPartB)).isEqualTo(40028128307L);
    }

}