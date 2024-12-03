package aminetti.adventofcode2024.day03;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day03 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day03.class);
    private List<String> input;

    public Day03() {
    }

    public void parseInput(List<String> input) {
        this.input = input;

    }

    public long solvePart1() {
        Pattern p = Pattern.compile("mul\\((\\d{1,3}),(\\d{1,3})\\)");

        long sum = 0L;
        for (String s : input) {
            Matcher matcher = p.matcher(s);

            while (matcher.find()) {
                Long a = Long.valueOf(matcher.group(1));
                Long b = Long.valueOf(matcher.group(2));
                sum += a * b;

            }
        }

        return sum;
    }

    public long solvePart2() {
        Pattern p = Pattern.compile("(?:mul\\((\\d{1,3}),(\\d{1,3})\\))|(?:do\\(\\))|(?:don't\\(\\))");

        long sum = 0L;
        boolean mulEnabled = true;
        for (String s : input) {
            Matcher matcher = p.matcher(s);

            while (matcher.find()) {
                String fullMatch = matcher.group(0);
                if (fullMatch.startsWith("mul")) {
                    if (mulEnabled) {
                        LOGGER.debug("Executing {}", fullMatch);
                        Long a = Long.valueOf(matcher.group(1));
                        Long b = Long.valueOf(matcher.group(2));
                        sum += a * b;
                    } else {
                        LOGGER.debug("Skipping {}", fullMatch);
                    }
                } else if (fullMatch.startsWith("do()")) {
                    LOGGER.debug("Enabling {}", fullMatch);
                    mulEnabled = true;
                } else if (fullMatch.startsWith("don't()")) {
                    LOGGER.debug("Disabling {}", fullMatch);
                    mulEnabled = false;
                }
            }
        }

        return sum;
    }
}
