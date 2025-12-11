package org.dahoppe.aoc.solutions.day6;

import org.dahoppe.aoc.solutions.day6.WorkSheet.MathType;
import org.dahoppe.aoc.util.Files;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class WorksheetTest {

    private final static String TEST_INPUT = """
            123 328  51 64
             45 64  387 23
              6 98  215 314
            *   +   *   +
            """;

    @Test
    void partATest() {
        assertThat(new WorkSheet(TEST_INPUT, MathType.HUMAN).grandTotal()).isEqualTo(4277556);
    }

    @Test
    void partAPuzzle() {
        assertThat(new WorkSheet(Files.read("Day6.txt"), MathType.HUMAN).grandTotal()).isEqualTo(6172481852142L);
    }

    @Test
    void partBTest() {
        assertThat(new WorkSheet(TEST_INPUT, MathType.CEPHALOPOD).grandTotal()).isEqualTo(3263827);
    }

    @Test
    void partBPuzzle() {
        assertThat(new WorkSheet(Files.read("Day6.txt"), MathType.CEPHALOPOD).grandTotal()).isEqualTo(10188206723429L);
    }

}