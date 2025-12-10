package org.dahoppe.aoc.solutions.day5;

import org.dahoppe.aoc.solutions.day5.Day5.Range;
import org.dahoppe.aoc.util.Files;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class Day5Test {

    private final static String TEST_INPUT = """
            3-5
            10-14
            16-20
            12-18
            
            1
            5
            8
            11
            17
            32
            """;

    private final static String EDGE_CASE = """
            1-10
            1-10
            1-10
            3-5
            1-10
            1-10
            1-10
            
            1
            """;

    @Test
    void partATest() {
        assertThat(Day5.solveAForInput(TEST_INPUT)).isEqualTo(3);
    }

    @Test
    void partAPuzzle() {
        assertThat(Day5.solveAForInput(Files.read("Day5.txt"))).isEqualTo(611);
    }

    @Test
    void partBTest() {
        assertThat(Day5.solveBForInput(TEST_INPUT)).isEqualTo(14);
    }

    @Test
    void partBPuzzle() {
        assertThat(Day5.solveBForInput(Files.read("Day5.txt"))).isEqualTo(345995423801866L);
    }

    @Test
    void testPartBEdgeCase() {
        assertThat(Day5.solveBForInput(EDGE_CASE)).isEqualTo(10);
    }

    @ParameterizedTest
    @MethodSource
    void testOverlap(Range a, Range b, boolean shouldOverlap, Range resultOfMerge) {
        assertThat(a.overlaps(b)).isEqualTo(shouldOverlap);
        assertThat(b.overlaps(a)).isEqualTo(shouldOverlap);
        if (shouldOverlap) {
            assertThat(a.merge(b)).isEqualTo(resultOfMerge);
            assertThat(b.merge(a)).isEqualTo(resultOfMerge);
        }
    }

    public static Stream<Arguments> testOverlap() {
        return Stream.of(
                Arguments.of(new Range(1, 10), new Range(11, 20), false, null),
                Arguments.of(new Range(1, 10), new Range(10, 20), true, new Range(1, 20)),
                Arguments.of(new Range(1, 10), new Range(5, 20), true, new Range(1, 20)),
                Arguments.of(new Range(1, 10), new Range(1, 10), true, new Range(1, 10)),
                Arguments.of(new Range(1, 1), new Range(2, 2), false, null),
                Arguments.of(new Range(1, 1), new Range(1, 2), true, new Range(1, 2)),
                Arguments.of(new Range(1, 10), new Range(3, 4), true, new Range(1, 10))
        );
    }

}