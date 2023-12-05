package days;
import java.util.Arrays;

public class Day04 {
    public static void main(String[] args) {
        String[] input = AocUtils.readFileLines("./input/day04.txt");

        System.out.println("Puzzle 1: " + puzzle1(input));
        System.out.println("Puzzle 2: " + puzzle2(input));
        
    }

    public static String puzzle1(String[] input) {
        int sumOfPoints = 0;
        for (int i = 0; i < input.length; i++) {
            sumOfPoints += matchesToPoints(matchingNumbers(input[i].split(":")[1]));
        }
        return ""+sumOfPoints;
    }

    public static String puzzle2(String[] input) {
        // TODO implement
        int[] matches = new int[input.length];
        for (int i = 0; i < input.length; i++) {
            matches[i] = matchingNumbers(input[i].split(":")[1]);
        }
        int[] copies = new int[input.length];
        Arrays.fill(copies, 1);
        int cards = 0;
        for (int i = 0; i < input.length; i++) {
            int currentCopies = copies[i];
            cards += currentCopies;
            for (int j = 1; j <= matches[i]; j++) {
                int k = i + j;
                copies[k] += currentCopies;
            }
        }
        
        return ""+cards;
    }

    public static int matchingNumbers(String card) {
        String[] parts = card.split(" \\| ");
        
        int[] winning = parseIntArray(parts[0].trim().split(" +", 0));
        int[] gotten = parseIntArray(parts[1].trim().split(" +", 0));
        int matches = 0;
        for (int i = 0; i < winning.length; i++) {
            if (contains(gotten, winning[i])) {
                matches++;
            }
        }
        
        return matches;
    }

    public static int matchesToPoints(int x) {
        if (x <= 1) {
            return x;
        }
        int res = 1;
        for (int i = 0; i < x - 1; i++) {
            res <<= 1;
        }
        return res;
    }

    public static int[] parseIntArray(String[] in) {
        int[] res = new int[in.length];
        for (int i = 0; i < in.length; i++) {
            res[i] = Integer.parseInt(in[i]);
        }
        return res;
    }

    public static boolean contains(int[] list, int element) {
        for (int i = 0; i < list.length; i++) {
            if (list[i] == element) {
                return true;
            }
        }
        return false;
    }
}
