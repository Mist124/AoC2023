package days;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class AocUtils {
    public static void main(String[] a) {
        System.out.println(readFileLines("./input/day01.txt"));
    }

    public static String[] readFileLines(String pathString) {
        Path path = Paths.get(pathString);
        File f = path.toFile();
        Scanner s = null;
        try {
            s = new Scanner(f);
        } catch (java.io.FileNotFoundException e) {
            System.err.println("wrong path");
            System.exit(1);
        }
        ArrayList<String> r = new ArrayList<String>();
        
        while (s.hasNextLine()) {
            r.add(s.nextLine());
        }
        return r.toArray(new String[r.size()]);
    }

    public static char[][] stringsToCharArrays(String[] input) {
        char[][] res = new char[input.length][];
        for (int i = 0; i < res.length; i++) {
                res[i] = input[i].toCharArray();
        }
        return res;
    }

    public static void printStringArray(String[] s) {
        for (int i = 0; i < s.length; i++) {
            System.out.printf("[%d]: \"%s\"\n", i, s[i]);
        }
    }

    static class Coordinate<T extends Number> {
        public T first;
        public T second;
        public Coordinate(T first, T second) {
            this.first = first;
            this.second = second;
        }
    }
}