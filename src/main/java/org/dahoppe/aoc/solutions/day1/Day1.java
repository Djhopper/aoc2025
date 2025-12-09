package org.dahoppe.aoc.solutions.day1;

import java.util.List;

class Day1 {

    enum Direction {
        RIGHT, LEFT;
    }

    static class Rotation {
        private final Direction direction;
        private final Integer distance;

        Rotation(String inputStringLine) {
            if (inputStringLine.startsWith("L")) {
                direction = Direction.LEFT;
            } else {
                direction = Direction.RIGHT;
            }
            distance = Integer.parseInt(inputStringLine.substring(1));
        }

        private RotationOutput apply(Integer currentPosition) {
            return switch (direction) {
                case LEFT -> {
                    int endPosition = Math.floorMod(currentPosition - distance, 100);
                    int zeroes = (currentPosition != 0 && endPosition > currentPosition || endPosition == 0 ? 1 : 0) + distance / 100;
                    yield new RotationOutput(endPosition, zeroes);
                }
                case RIGHT -> {
                    int endPosition = Math.floorMod(currentPosition + distance, 100);
                    int zeroes = (endPosition < currentPosition ? 1 : 0) + distance / 100;
                    yield new RotationOutput(endPosition, zeroes);
                }
            };
        }

        @Override
        public String toString() {
            return direction.toString() + distance;
        }

        record RotationOutput(Integer endPosition, Integer numberOfTimesPast0) {}
    }

    static int applyRotationsCountingZeroes(List<Rotation> rotations) {
        int currentPosition = 50;
        int zeroesCount = 0;
        for (Rotation rotation : rotations) {
            currentPosition = rotation.apply(currentPosition).endPosition();
            if (currentPosition == 0) {
                zeroesCount += 1;
            }
        }
        return zeroesCount;
    }

    static int applyRotationsCountingZeroesPartB(List<Rotation> rotations) {
        int currentPosition = 50;
        int zeroesCount = 0;
        for (Rotation rotation : rotations) {
            Rotation.RotationOutput rotationOutput = rotation.apply(currentPosition);
//            log.debug("Rotation {} from {} to {} with {} zeroes",
//                    rotation, currentPosition, rotationOutput.endPosition(), rotationOutput.numberOfTimesPast0());
            currentPosition = rotationOutput.endPosition();
            zeroesCount += rotationOutput.numberOfTimesPast0();
        }
        return zeroesCount;
    }

}
