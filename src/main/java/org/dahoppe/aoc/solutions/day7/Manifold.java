package org.dahoppe.aoc.solutions.day7;

import org.dahoppe.aoc.util.Parsing;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class Manifold {

    List<List<Point>> points;
    List<List<Long>> timelines;
    int height;
    int width;
    
    enum Point {
        START, BEAM, SPLITTER, EMPTY;

        static Point fromCharacter(Character character) {
            return switch (character) {
                case 'S' -> Point.START;
                case '^' -> Point.SPLITTER;
                case '|' -> Point.BEAM;
                case '.' -> Point.EMPTY;
                default -> throw new IllegalStateException("Unexpected value: " + character);
            };
        }

        @Override
        public String toString() {
            return switch (this) {
                case START -> "S";
                case BEAM -> "|";
                case SPLITTER -> "^";
                case EMPTY -> ".";
            };
        }
    }
    
    record Coordinate(int x, int y) {
        Coordinate down() {
            return new Coordinate(x, y+1);
        }
        Stream<Coordinate> split(int width) {
            return Stream.of(new Coordinate(x-1, y+1), new Coordinate(x+1, y+1))
                    .filter(coordinate -> coordinate.x() >= 0 && coordinate.x() < width);
        }
    }
    
    Point getPoint(Coordinate coordinate) {
        return points.get(coordinate.y()).get(coordinate.x());    
    }

    void setPoint(Coordinate coordinate, Point point) {
        points.get(coordinate.y()).set(coordinate.x(), point);
    }

    long getTimelines(Coordinate coordinate) {
        return timelines.get(coordinate.y()).get(coordinate.x());
    }

    void incrementTimelines(Coordinate coordinate, long increment) {
        timelines.get(coordinate.y()).set(coordinate.x(), getTimelines(coordinate) + increment);
    }
    
    Manifold(String inputString) {
        points = Parsing.parseLineByLine(
                inputString,
                line -> Parsing.toListOfCharacters(line)
                        .stream()
                        .map(Point::fromCharacter)
                        .collect(Collectors.toList())
        );
        height = points.size();
        width = points.getFirst().size();
        timelines = IntStream.range(0, height)
                .mapToObj(y -> IntStream.range(0, width)
                        .mapToObj(x -> 0L)
                        .collect(Collectors.toList()))
                .toList();
    }

    @Override
    public String toString() {
        return "\n" + points.stream()
                .map(lineOfPoints -> lineOfPoints.stream()
                        .map(Point::toString)
                        .collect(Collectors.joining()))
                .collect(Collectors.joining("\n"));
    }

    void simulateBeams() {
        for (int y=0; y<height-1; y++) {
            for (int x=0; x<width; x++) {
                Coordinate currentCoordinate = new Coordinate(x, y);
                Point here = getPoint(currentCoordinate);
                Point down = getPoint(currentCoordinate.down());
                if (here == Point.START || here == Point.BEAM) {
                    if (here == Point.START) {
                        incrementTimelines(currentCoordinate, 1);
                    }
                    if (down == Point.EMPTY) {
                        setPoint(currentCoordinate.down(), Point.BEAM);
                        incrementTimelines(currentCoordinate.down(), getTimelines(currentCoordinate));
                    }
                    if (down == Point.BEAM) {
                        incrementTimelines(currentCoordinate.down(), getTimelines(currentCoordinate));
                    }
                    if (down == Point.SPLITTER) {
                        currentCoordinate.split(width)
                                .forEach(coordinateToSplitTo -> {
                                    Point pointToSplitTo = getPoint(coordinateToSplitTo);
                                    if (pointToSplitTo == Point.EMPTY) {
                                        setPoint(coordinateToSplitTo, Point.BEAM);
                                        incrementTimelines(coordinateToSplitTo, getTimelines(currentCoordinate));
                                    }
                                    if (pointToSplitTo == Point.BEAM) {
                                        incrementTimelines(coordinateToSplitTo, getTimelines(currentCoordinate));
                                    }
                                });
                    }
                }
            }
        }
    }

    long solveA() {
        simulateBeams();
        return IntStream.range(0, height - 1)
                .boxed()
                .flatMap(y -> IntStream.range(0, width).boxed()
                        .map(x -> new Coordinate(x, y)))
                .filter(coordinate -> getPoint(coordinate) == Point.BEAM && getPoint(coordinate.down()) == Point.SPLITTER)
                .count();
    }

    long solveB() {
        simulateBeams();
        return IntStream.range(0, width)
                .mapToLong(x -> getTimelines(new Coordinate(x, height - 1)))
                .sum();
    }

}
