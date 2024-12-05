package aminetti.adventofcode2024.day05;

import aminetti.adventofcode2023.day05.SeedFertilizer;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day05 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day05.class);
    private List<String> input;

    private HashMap<String, List<String>> rules = new HashMap<>();
    private List<List<String>> allPages = new ArrayList<>();

    public Day05() {
    }

    public void parseInput(List<String> input) {
        this.input = input;


        var firstInputPart = true;
        for (String s : input) {
            if (firstInputPart) {
                if (s.isEmpty()) {
                    firstInputPart = false;
                    continue;
                }
                //LOGGER.debug("Line: {}", s);
                String[] split = s.split("\\|");
                List<String> currentRule = rules.getOrDefault(split[0], new ArrayList<>());
                currentRule.add(split[1]);
                rules.put(split[0], currentRule);
            } else {
                String[] split = s.split(",");
                allPages.add(Arrays.stream(split).toList());
            }

        }
    }

    public long solvePart1() {
        LOGGER.info("Rules: {}", rules);
        LOGGER.info("All pages: {}", allPages);

        long sum = 0;
        for (List<String> pageList : allPages) {
            if (isInOrder(pageList)) {
                String middlePage = pageList.get(pageList.size() / 2);
                LOGGER.info("Found ordered page: {}; adding {} to the sum", pageList, middlePage);
                sum += Long.parseLong(middlePage);
            }
        }


        return sum;
    }

    private boolean isInOrder(List<String> pageList) {
        Map<String, Integer> inEdgeCount = pageList.stream().collect(Collectors.toMap(Function.identity(), (x) -> 0));
        for (String p : rules.keySet()) {
            for (String v : rules.get(p)) {
                if (pageList.contains(v) && pageList.contains(p)) {
                    inEdgeCount.put(v, inEdgeCount.get(v) + 1);
                }
            }
        }
        LOGGER.info("The pages {} have this inEdgeCount: {}", pageList, inEdgeCount);
        List<String> firstElements = inEdgeCount.entrySet().stream()
                .filter(e -> e.getValue() == 0)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        List<List<String>> partialOrder = new ArrayList<>();
        partialOrder.add(firstElements);

        for (int i = 0; i < partialOrder.size(); i++) {
            List<String> level = partialOrder.get(i);
            if (level.isEmpty()) {
                break;
            }
            List<String> nextLevel = new ArrayList<>();
            partialOrder.add(nextLevel);
            for (String e : level) {
                if (!rules.containsKey(e)) {
                    continue;
                }
                for (String v : rules.get(e)) {
                    if (!pageList.contains(v)) {
                        continue;
                    }
                    Integer inEdgesForV = inEdgeCount.get(v);
                    inEdgesForV--;
                    inEdgeCount.put(v, inEdgesForV);
                    if (inEdgesForV == 0) {
                        nextLevel.add(v);
                    }
                }
            }
        }

        int currentLevel = 0;
        for (String p : pageList) {
            while (!partialOrder.get(currentLevel).contains(p)) {
                LOGGER.info("{} is not in level [{}] {}, so switching to the next level", p, currentLevel, partialOrder.get(currentLevel));
                currentLevel++;
                if (currentLevel > partialOrder.size() - 1) {
                    LOGGER.info("{} are NOT ordered", pageList);
                    LOGGER.info(" current level {} and partialOrder {}", currentLevel, partialOrder);

                    return false;
                }
            }

        }

        LOGGER.info("{} are ordered", pageList);
        return true;
    }


    public long solvePart2() {

        return 0;
    }
}
