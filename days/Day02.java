package days;

public class Day02 {
    public static void main(String[] a) {
        String[] lines = AocUtils.readFileLines("./input/day02.txt");
        String[] l = "Game 4: 1 blue, 2 green, 5 red; 10 red, 1 blue, 3 green; 14 red".split(": ");
        AocUtils.printStringArray(l);
        String[] l2 = l[1].split("; ");
        AocUtils.printStringArray(l2);
        System.out.println("Puzzle 1: " + puzzle1(lines));
        System.out.println("Puzzle 2: " + puzzle2(lines));
    }

    private static String puzzle1(String[] lines) {
        int idSum = 0;
        for (int i = 0; i < lines.length; i++) {
            // the first part of the line is "Game <id>: ..." which we don't need, 
            // because we have <id> = i + 1. So we just ignore "Game <id>"
            String line = lines[i].split(": ")[1];
            if (isLineValid(line)) {
                idSum += i + 1;
            }
        }
        return ""+idSum;
    }

    private static boolean isLineValid(String line) {
        for (String grab: line.split("; ")) {
            if (!isGrabPossible(grab)) {
                return false;
            }
        }
        return true;
    }

    private static boolean isGrabPossible(String grab) {
        CubeSet g = new CubeSet(grab);
        if (g.red > 12 || g.green > 13 || g.blue > 14) {
            return false;
        }
        return true;
    }

    private static String puzzle2(String[] lines) {
        int minimumPowerSum = 0;
        for (int i = 0; i < lines.length; i++) {
            // the first part of the line is "Game <id>: ..." which we don't need
            String line = lines[i].split(": ")[1];

            minimumPowerSum += minimumGrab(line).power();
        }
        return ""+minimumPowerSum;
    }

    private static CubeSet minimumGrab(String line) {
        String[] grabs = line.split("; ");
        CubeSet min = new CubeSet(0, 0, 0);
        for (String grabString: grabs) {
            CubeSet grab = new CubeSet(grabString);
            min = min.max(grab);
        }
        return min;
    }


    static class CubeSet {
        public int red = 0;
        public int green = 0;
        public int blue = 0;

        public CubeSet(int r, int g, int b) {
            red = r;
            green = g;
            blue = b;
        }

        public CubeSet(String grab) {
            int num = 0;
            for (String word: grab.split(", ", 0)) {
                // here, the first element is a number and the second is one of ("red", "green", "blue")
                String[] countAndColor = word.split(" ", 0);
                num = Integer.parseInt(countAndColor[0]);
                switch (countAndColor[1]) {
                    case "red":
                        red = num;
                        break;
                    case "green":
                        green = num;
                        break;
                    case "blue":
                        blue = num;
                        break;
                    default:
                        break;
                }
            }
        }

        private static int max(int a, int b) {
            return a > b ? a : b;
        }

        public CubeSet max(CubeSet other) {
            return new CubeSet(max(red, other.red), max(green, other.green), max(blue, other.blue));
        }
        
        private int power() {
            return red * green * blue;
        }
    }
}

