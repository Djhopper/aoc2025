package org.dahoppe.aoc.solutions.day4;

import org.dahoppe.aoc.util.Parsing;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day4 {

    enum Item {
        PAPER, NOTHING
    }

    record Coordinate(int x, int y) {}

    static class Map {
        List<List<Item>> map;

        Map(String inputString) {
            this.map = Parsing.parseLineByLine(inputString,
                    inputLine -> Parsing.toListOfCharacters(inputLine)
                            .stream()
                            .map(character -> switch (character) {
                                case '@' -> Item.PAPER;
                                case '.' -> Item.NOTHING;
                                default -> throw new IllegalStateException();
                            })
                            .collect(Collectors.toList()));
        }

        Item get(Coordinate coordinate) {
            return map.get(coordinate.x()).get(coordinate.y());
        }

        void remove(Coordinate coordinate) {
            map.get(coordinate.x()).set(coordinate.y(), Item.NOTHING);
        }

        List<Coordinate> adjacentCoordinates(Coordinate coordinate) {
            return IntStream.range(-1, 2)
                    .boxed()
                    .flatMap(dx -> IntStream.range(-1, 2)
                            .filter(dy -> dx != 0 || dy != 0)
                            .mapToObj(dy -> new Coordinate(coordinate.x() + dx, coordinate.y() + dy)))
                    .filter(c -> c.x() >= 0 && c.x() < map.size() && c.y() >= 0 && c.y() < map.getFirst().size())
                    .toList();
        }

        boolean isAccessible(Coordinate coordinate) {
            return adjacentCoordinates(coordinate)
                    .stream()
                    .filter(adjacentCoordinate -> get(adjacentCoordinate) == Item.PAPER)
                    .count() < 4;
        }

        Stream<Coordinate> allCoordinates() {
            return IntStream.range(0, map.size())
                    .boxed()
                    .flatMap(x -> IntStream.range(0, map.getFirst().size())
                            .mapToObj(y -> new Coordinate(x, y)));
        }

    }

    static long solveAForInput(String input) {
        Map map = new Map(input);
        return map.allCoordinates()
                .filter(coordinate -> map.get(coordinate) == Item.PAPER)
                .filter(map::isAccessible)
                .count();
    }

    static long solveBForInput(String input) {
        Map map = new Map(input);
        List<Coordinate> toRemove;
        int removed = 0;
        do {
            toRemove = map.allCoordinates()
                    .filter(coordinate -> map.get(coordinate) == Item.PAPER)
                    .filter(map::isAccessible)
                    .toList();
            toRemove.forEach(map::remove);
            removed += toRemove.size();
        } while (!toRemove.isEmpty());
        return removed;
    }

}
