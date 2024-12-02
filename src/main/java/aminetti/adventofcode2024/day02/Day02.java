package aminetti.adventofcode2024.day02;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day02 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day02.class);
    private List<String> input;

    public Day02() {
    }

    public void parseInput(List<String> input) {
        this.input = input;
    }

    public long solvePart1() {
        Pattern p = Pattern.compile("(\\d+)");

        List<List<Long>> allLists = new ArrayList<>(input.size());

        for (String s : input) {
            Matcher matcher = p.matcher(s);
            List<Long> list = new ArrayList<>();

            while (matcher.find()) {
                Long a = Long.valueOf(matcher.group(1));
                LOGGER.debug("Reading: {}", a);
                list.add(a);
            }

            allLists.add(list);
        }

        return allLists.stream()
                .filter(this::isSafe)
                .count();

    }

    private boolean isSafe(List<Long> l) {

        LOGGER.info("Checking {}", l);
        Long prev = null;
        Boolean ascending = null;
        for (Long i : l) {
            if (prev == null) {
                prev = i;
                continue;
            }

            if (prev == i) {
                LOGGER.info(" has 2 same values {}", i);
                return false;
            }

            if (ascending == null) {
                ascending = prev < i;
                LOGGER.info(" is {}", ascending ? "ascending" : "descending");
            } else {

                if (ascending && prev >= i) {
                    LOGGER.info(" started as ascending, but is not!");
                    return false;
                }
                if (!ascending && prev <= i) {
                    LOGGER.info(" started as descending, but is not!");
                    return false;
                }
            }

            if (Math.abs(prev - i) > 3 && Math.abs(prev - i) > 0) {
                LOGGER.info(" has too big gap between {} and {}", prev, i);
                return false;
            }

            prev = i;
        }

        LOGGER.info(" is safe");
        return true;
    }

    public long solvePart2() {
        Pattern p = Pattern.compile("(\\d+)");

        List<List<Long>> allLists = new ArrayList<>(input.size());

        for (String s : input) {
            Matcher matcher = p.matcher(s);
            List<Long> list = new ArrayList<>();

            while (matcher.find()) {
                Long a = Long.valueOf(matcher.group(1));
                LOGGER.debug("Reading: {}", a);
                list.add(a);
            }

            allLists.add(list);
        }

        return allLists.stream()
                .filter(this::isSafeWithoutOneLevel)
                .count();
    }

    private boolean isSafeWithoutOneLevel(List<Long> l) {
        List<List<Long>> possibleCombinations = new ArrayList<>();
        for (int i = 0; i < l.size(); i++) {
            List<Long> l1 = new ArrayList<>(l);
            l1.remove(i);
            possibleCombinations.add(l1);
        }
        return possibleCombinations.stream().anyMatch(this::isSafe);
    }
}
