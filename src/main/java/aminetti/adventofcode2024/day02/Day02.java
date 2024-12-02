package aminetti.adventofcode2024.day02;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day02 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day02.class);
    private List<List<Long>> allLists;

    public Day02() {
    }

    public void parseInput(List<String> input) {
        Pattern p = Pattern.compile("(\\d+)");
        allLists = new ArrayList<>(input.size());

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
    }

    public long solvePart1() {
        return allLists.stream()
                .filter(this::isSafe)
                .count();
    }

    private boolean isSafe(List<Long> l) {
        LOGGER.debug("Checking {}", l);
        Long prev = null;
        Boolean ascending = null;
        for (Long i : l) {
            if (prev == null) {
                prev = i;
                continue;
            }

            if (prev.compareTo(i) == 0) {
                LOGGER.debug(" has 2 same values {}", i);
                return false;
            }

            if (ascending == null) {
                ascending = prev.compareTo(i) < 0;
                LOGGER.debug(" is {}", ascending ? "ascending" : "descending");
            } else {

                if (ascending && prev.compareTo(i) > 0) {
                    LOGGER.debug(" started as ascending, but is not!");
                    return false;
                }
                if (!ascending && prev.compareTo(i) < 0) {
                    LOGGER.debug(" started as descending, but is not!");
                    return false;
                }
            }

            long absDiff = Math.abs(prev - i);
            if (absDiff > 3) {
                LOGGER.debug(" has too big gap between {} and {}", prev, i);
                return false;
            }

            prev = i;
        }

        LOGGER.debug(" is safe");
        return true;
    }

    public long solvePart2() {
        return allLists.stream()
                .filter(this::isSafeWithoutOneLevel)
                .count();
    }

    private boolean isSafeWithoutOneLevel(List<Long> l) {
        List<List<Long>> orderedSelectionWithOneElement = new ArrayList<>(l.size());
        for (int i = 0; i < l.size(); i++) {
            List<Long> l1 = new ArrayList<>(l);
            l1.remove(i);
            orderedSelectionWithOneElement.add(l1);
        }
        return orderedSelectionWithOneElement.stream().anyMatch(this::isSafe);
    }
}
