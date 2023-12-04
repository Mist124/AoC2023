package days;

import days.AocUtils.Coordinate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Day03 {
    public static void main(String[] a) {
        char[][] input = AocUtils.stringsToCharArrays(AocUtils.readFileLines("./input/day03.txt"));
        System.out.println("Puzzle 1: " + puzzle1(input));
        System.out.println("Puzzle 2: " + puzzle2(input));
        String[] dummyInput = {
            "467..114..", 
            "...*......", 
            "..35..633.", 
            "......#...", 
            "617*......", 
            ".....+.58.", 
            "..592.....", 
            "......755.", 
            "...$.*....", 
            ".664.598.."
        }; 
        System.out.println(Arrays.toString(partNumbersIn(AocUtils.stringsToCharArrays(dummyInput))));
    }
    
    private static String puzzle1(char[][] input) {
        Number[] nums = partNumbersIn(input);
        int sum = 0;
        for (Number n: nums) {
            sum += n.value;
        }
        
        return "" + sum;
    }

    private static String puzzle2(char[][] input) {
        Optional<Number>[][] numberField = partNumberReferencesFrom(input);
        int sumOfGearRatios = 0;

        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input[i].length; j++) {
                if (input[i][j] == '*') {
                    Number[] neighNums = collectUniqueNeighbours(new Coordinate<Integer>(i, j), numberField);
                    if (neighNums.length == 2) {
                        sumOfGearRatios += neighNums[0].value * neighNums[1].value;
                    }
                }
            }
        }

        return ""+ sumOfGearRatios;
    }

    private static Number[] collectUniqueNeighbours(Coordinate<Integer> pos, Optional<Number>[][] input) {
        ArrayList<Number> res = new ArrayList<Number>();
        
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) {
                    continue;
                }
                int k = pos.first + i,
                    w = pos.second + j;
                if (k > input.length || k < 0 || w > input[k].length || w < 0) {
                    continue;
                }
                Optional<Number> n = input[k][w];
                if (n.isPresent()) {
                    res.add(n.get());
                }
            }
        }

        // make a list of unique (distinct) Numbers
        List<Number> r = res.stream().distinct().collect(Collectors.toList());
        return r.toArray(new Number[r.size()]);
    }

    private static Optional<Number>[][] partNumberReferencesFrom(char[][] input) {
        // returns an array with the same shape as <input> looking like this:
        // A = a number at (0, 5) with length 4;
        // B = a number at (2, 0) with length 3;
        // | | | | | | |A|A|A|A|
        // | | | | | | | | | | |
        // |B|B|B| | | | | | | |
        // 

        @SuppressWarnings("unchecked") // why java, why????
        Optional<Number>[][] numberReferences = (Optional<Number>[][]) new Optional[input.length][];

        for (int i = 0; i < numberReferences.length; i++) {
            numberReferences[i] = (Optional<Number>[]) new Optional[input[i].length];
            Arrays.fill(numberReferences[i], Optional.empty());
        }

        Number[] nums = partNumbersIn(input);

        for (Number n: nums) {
            for (int i = 0; i < n.length; i++) {
                numberReferences[n.start.first][n.start.second + i] = Optional.of(n);
            }
        }
        return numberReferences;
    }

    private static Number[] partNumbersIn(char[][] input) {
        // The resulting list is ordered after the position of the numbers;
        // top down, left to right

        // this list can have more than input.length elements, which is why it is an ArrayList
        ArrayList<Number> res = new ArrayList<Number>(input.length);
        
        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input[i].length; j++) {
                if (Character.isDigit(input[i][j])) {
                    Coordinate<Integer> c = new Coordinate<Integer>(i, j);
                    Number n = numberFromCoordinate(c, input);
                    if (isPartNumber(n, input)) {
                        res.add(n);
                    }
                    j += n.length - 1; // skip the rest of the digits
                }
            }
        }

        return res.toArray(new Number[res.size()]);
    }

    private static Number numberFromCoordinate(Coordinate<Integer> pos, char[][] field) {
        int i = pos.first;
        int j = pos.second;

        // this should probably be a StringBuilder if the numbers from the dataset were bigger 
        // or a different approach to the problem should be used.. but it is fine for these purposes 
        String num = ""; 

        // index in bounds checking in while condition
        while (j < field[i].length && Character.isDigit(field[i][j])) {
            num += field[i][j];
            j++;
        }
        
        return new Number(new Coordinate<Integer>(pos.first, pos.second), Integer.parseInt(num), num.length());
    }

    private static boolean isPartNumber(Number n, char[][] field) {
        //         n.second-1  n.second+n.length
        //                 |   |
        // n.first-1 ->    #####
        //                 #<n>#
        // n.first+1 ->    #####

        // in this nested for-loop i is the row offset and j is the column offset
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= n.length; j++) {
                Coordinate<Integer> pos = new Coordinate<Integer>(n.start.first + i, n.start.second + j);
                if (pos.first > 0 && pos.first < field.length
                            && pos.second > 0 && pos.second < field[pos.first].length) {
                    char c = field[pos.first][pos.second];
                    if (!Character.isDigit(c) && c != '.') {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static class Number {
        public Coordinate<Integer> start;
        public int value;
        public int length;

        Number(Coordinate<Integer> start, int value, int length) {
            this.start = start;
            this.value = value;
            this.length = length;
        }

        @Override
        public String toString() {
            return String.format("Number: {\n    Coordinate: (%d, %d),\n    value: %d,\n    length: %d\n}", start.first, start.second, value, length);
        }
    }
}
