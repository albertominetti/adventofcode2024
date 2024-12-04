package aminetti.adventofcode2024.day04;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class Day04 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day04.class);
    private List<String> input;
    private int ROWS;
    private int COLS;

    public Day04() {
    }

    public void parseInput(List<String> input) {
        this.input = input;

        ROWS = input.size();
        COLS = input.getFirst().length();
    }

    public long solvePart1() {
        long sum = 0;
        for (int i = 0; i < input.size(); i++) {
            String l = input.get(i);
            for (int j = 0; j < l.length(); j++) {
                char c = l.charAt(j);
                if (c == 'X') {
                    sum += countXmas(i, j);
                }
            }
        }
        return sum;
    }

    private long countXmas(int i, int j) {
        long count = 0;

        // NW, SW, SE, NE
        count += lookingFor("MAS", i - 1, j - 1, -1, -1);
        count += lookingFor("MAS", i + 1, j + 1, +1, +1);
        count += lookingFor("MAS", i - 1, j + 1, -1, +1);
        count += lookingFor("MAS", i + 1, j - 1, +1, -1);

        // N, S, W, O
        count += lookingFor("MAS", i - 1, j, -1, 0);
        count += lookingFor("MAS", i + 1, j , +1, 0);
        count += lookingFor("MAS", i , j + 1, 0, +1);
        count += lookingFor("MAS", i , j - 1, 0, -1);

        return count;
    }

    private long lookingFor(String search, int i, int j, int dirI, int dirJ) {
        if (search.isEmpty()) {
            return 1;
        }
        if (i < 0 || i >= ROWS || j < 0 || j >= COLS) {
            return 0;
        }
        LOGGER.info("Index {} and {}", i, j);
        if (search.charAt(0) == input.get(i).charAt(j)) {
            return lookingFor(search.substring(1), i + dirI, j + dirJ, dirI, dirJ);
        }

        return 0;
    }

    public long solvePart2() {

        return 0;
    }
}
