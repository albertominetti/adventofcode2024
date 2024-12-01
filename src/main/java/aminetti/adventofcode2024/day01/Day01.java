package aminetti.adventofcode2024.day01;

import com.google.common.collect.Streams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day01 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day01.class);
    private List<String> input;

    public Day01() {
    }

    public void parseInput(List<String> input) {
        this.input = input;
    }

    public long solvePart1() {
        Pattern p = Pattern.compile("(\\d+)\\s+(\\d+)");

        ArrayList<Integer> left = new ArrayList<>(input.size());
        ArrayList<Integer> right = new ArrayList<>(input.size());

        for (String s : input) {
            Matcher matcher = p.matcher(s);
            if (matcher.matches()) {
                Integer a = Integer.valueOf(matcher.group(1));
                Integer b = Integer.valueOf(matcher.group(2));
                LOGGER.debug("Reading: {} and {}", a, b);
                left.add(a);
                right.add(b);
            } else {
                throw new IllegalArgumentException("Invalid input: " + s);
            }
        }

        return Streams.zip(left.stream().sorted(), right.stream().sorted(),
                (a, b) -> Math.abs(a - b)).reduce(0, Integer::sum);
    }

    public long solvePart2() {
        Pattern p = Pattern.compile("(\\d+)\\s+(\\d+)");

        Map<Long, Long> left = new HashMap<>(input.size());
        Map<Long, Long> right = new HashMap<>(input.size());

        for (String s : input) {
            Matcher matcher = p.matcher(s);
            if (matcher.matches()) {
                Long a = Long.valueOf(matcher.group(1));
                Long b = Long.valueOf(matcher.group(2));
                LOGGER.debug("Reading: {} and {}", a, b);
                left.compute(a, (k, v) -> (v == null) ? 1 : v + 1);
                right.compute(b, (k, v) -> (v == null) ? 1 : v + 1);
            } else {
                throw new IllegalArgumentException("Invalid input: " + s);
            }

        }

        return left.keySet().stream().
                map((k) -> k * left.get(k) * right.getOrDefault(k, 0L))
                .reduce(0L, Long::sum);
    }
}
