package org.dahoppe.aoc.solutions.day3;

import org.dahoppe.aoc.util.Parsing;

import java.util.Collections;
import java.util.List;

class Day3 {

    record Bank(List<Integer> batteries) {
        long largestJoltage(int numBatteriesToTurnOn) {
            List<Integer> remainingBatteries = List.copyOf(batteries);
            long totalJoltage = 0;
            for (int currentDigitPosition=numBatteriesToTurnOn; currentDigitPosition>0; currentDigitPosition--) {
                List<Integer> allButTheLastN = remainingBatteries.subList(0, remainingBatteries.size() - currentDigitPosition + 1);
                int newDigit = Collections.max(allButTheLastN);
                remainingBatteries = remainingBatteries.subList(allButTheLastN.indexOf(newDigit) + 1, remainingBatteries.size());
                totalJoltage += (long) Math.pow(10, currentDigitPosition - 1) * newDigit;
            }
            return totalJoltage;
        }

        static Bank fromInputLine(String inputLine) {
            return new Bank(Parsing.treatAsUnseparatedIntegers(inputLine));
        }
    }

    static List<Bank> parseInput(String input) {
        return Parsing.parseLineByLine(input, Bank::fromInputLine);
    }

    static long solveForInput(String input, int batteriesToTurnOn) {
        List<Bank> banks = parseInput(input);
        return banks.stream()
                .mapToLong(bank -> bank.largestJoltage(batteriesToTurnOn))
                .sum();
    }

}
