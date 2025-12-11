package org.dahoppe.aoc.solutions.day6;

import org.dahoppe.aoc.util.Parsing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class WorkSheet {

    private static final Logger log = LoggerFactory.getLogger(WorkSheet.class);
    List<Problem> problems;

    record Problem(List<Long> numbers, Operation operation) {
        long solution() {
            return switch (operation) {
                case ADD -> {
                    long sum = 0;
                    for (long number : numbers) {
                        sum += number;
                    }
                    yield sum;
                }
                case MULTIPLY -> {
                    long product = 1;
                    for (long number : numbers) {
                        product *= number;
                    }
                    yield product;
                }
            };
        }
    }

    enum Operation {
        MULTIPLY, ADD;

        static Operation fromSymbol(String symbol) {
            return switch (symbol) {
                case "*" -> MULTIPLY;
                case "+" -> ADD;
                default -> throw new IllegalArgumentException("Symbol %s is not * or +".formatted(symbol));
            };
        }
    }

    enum MathType {
        HUMAN, CEPHALOPOD;
    }

    WorkSheet(String input, MathType mathType) {
        switch (mathType) {
            case HUMAN -> {
                List<String> lines = Parsing.splitOnNewLine(input).collect(Collectors.toList());
                List<Operation> symbols = Parsing.split(lines.removeLast(), " +")
                        .map(Operation::fromSymbol)
                        .toList();
                List<List<Long>> numbers = lines.stream()
                        .map(line -> Parsing.split(line, " +")
                                .filter(Predicate.not(String::isEmpty))
                                .map(Long::parseLong)
                                .toList())
                        .toList();
                problems = IntStream.range(0, numbers.getFirst().size())
                        .mapToObj(column -> new Problem(
                                IntStream.range(0, numbers.size())
                                        .boxed()
                                        .map(row -> numbers.get(row).get(column))
                                        .toList(),
                                symbols.get(column)
                        ))
                        .toList();
            }
            case CEPHALOPOD -> {
                List<String> lines = Parsing.splitOnNewLine(input).toList();
                long maxLineLength = lines.stream().mapToLong(String::length).max().getAsLong();
                problems = new ArrayList<>();
                Operation operation = null;
                List<Long> longs = null;
                for (int column=0; column<maxLineLength; column++) {
                    String operatorChar = getCharOrElseBlank(lines.getLast(), column);
                    if (!operatorChar.equals(" ")) {
                        if (operation != null) {
                            problems.add(new Problem(longs, operation));
                        }
                        operation = Operation.fromSymbol(operatorChar);
                        longs = new ArrayList<>();
                    }
                    int finalColumn = column;
                    String newLong = IntStream.range(0, lines.size() - 1)
                            .mapToObj(row -> getCharOrElseBlank(lines.get(row), finalColumn))
                            .filter(character -> !character.isBlank())
                            .collect(Collectors.joining());
                    if (!newLong.isEmpty()) {
                        longs.add(Long.parseLong(newLong));
                    }
                }
                problems.add(new Problem(longs, operation));
            }
            default -> throw new IllegalArgumentException();
        }
    }

    private static String getCharOrElseBlank(String string, int index) {
        if (index >= string.length()) {
            return " ";
        }
        return String.valueOf(string.charAt(index));
    }

    long grandTotal() {
        log.info("Solving problems: {}", problems);
        return problems.stream()
                .mapToLong(Problem::solution)
                .sum();
    }

}
