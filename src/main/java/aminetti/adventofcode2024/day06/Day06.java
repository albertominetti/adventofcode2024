package aminetti.adventofcode2024.day06;

import aminetti.adventofcode2023.day17.Day17;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static aminetti.adventofcode2024.day06.Day06.Direction.*;

public class Day06 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day06.class);
    private List<String> input;
    private int ROWS;
    private int COLS;
    private Pair<Integer, Integer> start;

    public Day06() {
    }

    public void parseInput(List<String> input) {
        this.input = input;
        ROWS = input.size();
        COLS = input.getFirst().length();
        start = findStart();
    }

    public long solvePart1() {
        Set<Pair<Integer, Integer>> steps = findSteps();
        return steps.size();
    }

    private Set<Pair<Integer, Integer>> findSteps() {
        Set<Pair<Integer, Integer>> steps = new HashSet<>();

        Pair<Integer, Integer> current = start;
        Direction d = UP;

        while (inMap(current)) {
            steps.add(current);

            Pair<Integer, Integer> next = new ImmutablePair<>(current.getLeft() + d.x, current.getRight() + d.y);

            while (inMap(next) && input.get(next.getLeft()).charAt(next.getRight()) == '#') {
                LOGGER.debug("Can't go {}, so rotating 90°", d);
                d = Direction.values()[(d.ordinal() + 1) % 4];
                next = new ImmutablePair<>(current.getLeft() + d.x, current.getRight() + d.y);
            }

            current = next;
            LOGGER.debug("Now position is {}", current);
        }
        return steps;
    }

    public enum Direction {
        RIGHT(0, 1), DOWN(1, 0), LEFT(0, -1), UP(-1, 0);

        public final int x;
        public final int y;

        Direction(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private boolean inMap(Pair<Integer, Integer> p) {
        return 0 <= p.getLeft() && p.getLeft() < ROWS && 0 <= p.getRight() && p.getRight() < COLS;
    }

    private Pair<Integer, Integer> findStart() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (input.get(i).charAt(j) == '^') {
                    return new ImmutablePair<>(i, j);
                }
            }
        }
        throw new IllegalArgumentException("Can't find start");
    }

    public long solvePart2() {
        Set<Pair<Integer, Integer>> obstructionsForLoops = new HashSet<>();
        for (Pair<Integer, Integer> step : findSteps()) {
            Pair<Integer, Integer> o = step;

            if (o.equals(start)) {
                continue;
            }
            LOGGER.info("Obstruction at {}", o);

            if (isLoop(start, o)) {
                obstructionsForLoops.add(o);
            }

        }

        return obstructionsForLoops.size();
    }

    private boolean isLoop(Pair<Integer, Integer> start, Pair<Integer, Integer> o) {
        Set<Triple<Integer, Integer, Direction>> steps = new HashSet<>();

        Pair<Integer, Integer> current = start;
        Direction d = UP;

        while (inMap(current)) {
            ImmutableTriple<Integer, Integer, Direction> posAndDir = new ImmutableTriple<>(current.getLeft(), current.getRight(), d);
            if (steps.contains(posAndDir)) {
                return true;
            }
            steps.add(posAndDir);

            Pair<Integer, Integer> next = new ImmutablePair<>(current.getLeft() + d.x, current.getRight() + d.y);

            while (inMap(next) && (
                    input.get(next.getLeft()).charAt(next.getRight()) == '#' || next.equals(o)
            )) {
                LOGGER.debug("Can't go {}, so rotating 90°", d);
                d = Direction.values()[(d.ordinal() + 1) % 4];
                next = new ImmutablePair<>(current.getLeft() + d.x, current.getRight() + d.y);
            }

            current = next;
            LOGGER.debug("Now position is {}", current);
        }

        return false;
    }
}
