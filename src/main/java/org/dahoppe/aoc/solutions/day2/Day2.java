package org.dahoppe.aoc.solutions.day2;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import org.dahoppe.aoc.util.Parsing;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

class Day2 {

    static boolean isInvalidPartA(String id) {
        return
                id.length() % 2 == 0 &&
                id.substring(0, id.length() / 2).equals(id.substring(id.length() / 2));
    }

    static boolean isInvalidPartB(String id) {
        for (int chunks=2; chunks<=id.length(); chunks++) {
            if (id.length() % chunks == 0) {
                List<String> split = Lists.newArrayList(Splitter.fixedLength(id.length() / chunks).split(id));
                if (split.stream().allMatch(s -> s.equals(split.getFirst()))) {
                    return true;
                }
            }
        }
        return false;
    }

    record Range (long firstId, long lastId) {
        List<Long> getInvalidIds(Predicate<String> isInvalidPredicate) {
            return LongStream.range(firstId, lastId + 1)
                    .boxed()
                    .filter(id -> isInvalidPredicate.test(String.valueOf(id)))
                    .collect(Collectors.toList());
        }
    }

    static List<Range> parseInput(String input) {
        return Parsing.split(input, ",")
                .map(range -> {
                    List<Long> longs = Parsing.split(range, "-")
                            .map(Long::parseLong)
                            .toList();
                    assert !longs.isEmpty();
                    return new Range(longs.getFirst(), longs.getLast());
                })
                .toList();
    }

    static long solveForInput(String input, Predicate<String> isInvalidPredicate) {
        List<Day2.Range> parsedInput = Day2.parseInput(input);
        return parsedInput.stream()
                .map(range -> range.getInvalidIds(isInvalidPredicate))
                .mapToLong(invalidIds -> invalidIds.stream().mapToLong(i -> i).sum())
                .sum();
    }

}
