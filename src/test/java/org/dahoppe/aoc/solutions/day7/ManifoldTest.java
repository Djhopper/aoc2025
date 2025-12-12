package org.dahoppe.aoc.solutions.day7;

import org.dahoppe.aoc.util.Files;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ManifoldTest {

    private final static String TEST_INPUT = """
            .......S.......
            ...............
            .......^.......
            ...............
            ......^.^......
            ...............
            .....^.^.^.....
            ...............
            ....^.^...^....
            ...............
            ...^.^...^.^...
            ...............
            ..^...^.....^..
            ...............
            .^.^.^.^.^...^.
            ...............
            """;

    @Test
    void partATest() {
        assertThat(new Manifold(TEST_INPUT).solveA()).isEqualTo(21);
    }

    @Test
    void partAPuzzle() {
        assertThat(new Manifold(Files.read("Day7.txt")).solveA()).isEqualTo(1573);
    }

    @Test
    void partBTest() {
        assertThat(new Manifold(TEST_INPUT).solveB()).isEqualTo(40);
    }

    @Test
    void partBPuzzle() {
        assertThat(new Manifold(Files.read("Day7.txt")).solveB()).isEqualTo(15093663987272L);
    }

}