package aminetti.adventofcode2024.day01;

import com.google.common.collect.Streams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
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
                LOGGER.info("Reading: {} and {}", a ,b);
                left.add(a);
                right.add(b);
            } else {
                throw new IllegalArgumentException("Invalid input: " + s);
            }
        }

        return Streams.zip(left.stream().sorted(), right.stream().sorted(),
                (a, b) -> Math.abs(a-b)).reduce(0, (a, b) -> a + b);
    }

    public long solvePart2() {

        return 0;
    }
}
