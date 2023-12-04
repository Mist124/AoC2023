package days;
import java.util.Optional;


public class Day01 {
    public static void main(String[] args) {
        String[] lines = AocUtils.readFileLines("./input/day01.txt");
        
        System.out.println("Puzzle 1: "+ puzzle1(lines));
        System.out.println("Puzzle 2: "+ puzzle2(lines));
    }

    private static String puzzle1(String[] lines) {
        int sum = 0;
        for (String line: lines) {
            sum += digitFromLine(line);
        }
        return Integer.toString(sum);
    }

    private static int digitFromLine(String line) {
        int num1 = -1;
        int num2 = -1;
        char[] chars = line.toCharArray();

        // first digit in line
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (Character.isDigit(c)) {
                num1 = Character.digit(c, 10);
                break;
            }
        }
        // last digit in line
        for (int i = chars.length - 1; i >= 0; i--) {
            char c = chars[i];
            if (Character.isDigit(c)) {
                num2 = Character.digit(c, 10);
                break;
            }
        }
        
        return num1*10 + num2;
    }

    private static String puzzle2(String[] lines) {
        int sum = 0;
        for (String line: lines) {
            sum += digitOrNumWordFromLine(line);
        }
        return ""+sum;
    }

    private static int digitOrNumWordFromLine(String line) {
        int num1 = -1;
        int num2 = -1;
        char[] charLine = line.toCharArray();

        // first occurrence of a number in the line
        for (int i = 0; i < charLine.length; i++) {
            Optional<Integer> res = numAt(charLine, i);
            if (res.isPresent()) {
                num1 = res.get();
                break;
            }
        }

        // last occurrence of a number in the line
        for (int i = charLine.length - 1; i >= 0; i--) {
            Optional<Integer> res = numAt(charLine, i);
            if (res.isPresent()) {
                num2 = res.get();
                break;
            }
        }

        return num1 * 10 + num2;
    }

    private static Optional<Integer> numAt(char[] a, int idxInA) {
        String[] stringNums = {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};
        if (Character.isDigit(a[idxInA])) {
            return Optional.of(Character.digit(a[idxInA], 10));
        }
        for (int num = 0; num < 9; num++) {
            boolean shouldContinue = false;
            char[] charNum = stringNums[num].toCharArray();
            if (a.length < charNum.length + idxInA) {
                continue;
            }
            for (int i = 0; i < charNum.length; i++) {
                if (a[idxInA + i] != charNum[i]) {
                    shouldContinue = true;
                    continue;
                }
            }
            if (shouldContinue) {
                continue;
            }
            return Optional.of(num+1);
        }
        return Optional.empty();
    }
}
