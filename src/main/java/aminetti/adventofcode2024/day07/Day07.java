package aminetti.adventofcode2024.day07;

import com.google.common.collect.Iterables;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static aminetti.adventofcode2024.day07.Day07.Op.MULT;
import static com.google.common.collect.Lists.newArrayList;

public class Day07 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day07.class);
    private List<String> input;
    private final List<Long> results = new ArrayList<>();
    private final List<List<Long>> components = new ArrayList<>();

    public Day07() {
    }

    public void parseInput(List<String> input) {
        this.input = input;

        for (String s : input) {
            String[] split = s.split(":");
            results.add(Long.parseLong(split[0]));
            String[] split1 = split[1].split("\\s+");
            components.add(Arrays.stream(split1).filter(StringUtils::isNotBlank).map(Long::parseLong).toList());
        }
    }

    public long solvePart1() {
        long sum = 0;
        for (int i = 0; i < results.size(); i++) {
            Long result = results.get(i);
            long count = countOfCorrectOperators(result, components.get(i), List.of(), newArrayList());
            LOGGER.debug("count for {}: {}", result, count);
            if (count > 0) {
                sum += result;
            }
        }
        return sum;
    }

    public enum Op {
        SUM, MULT, CONCAT;

        public String print() {
            return switch (this) {
                case SUM -> "+";
                case MULT -> "*";
                case CONCAT -> "||";
            };
        }
    }


    private long countOfCorrectOperators(long result, List<Long> longs, List<Op> operators, List<Long> components) {
        if (longs.size() == 1) {
            Long lastEl = Iterables.getOnlyElement(longs);
            if (result == lastEl) {

                String fullOperation = "(".repeat(operators.size()) + lastEl;

                ArrayList<Op> mutableOps = newArrayList(operators);
                ArrayList<Long> mutableComponents = newArrayList(components);
                while (!mutableOps.isEmpty()) {
                    fullOperation += mutableOps.removeLast().print() + "" + mutableComponents.removeLast() + ")";
                }

                LOGGER.debug("Operation: {}", fullOperation);
                LOGGER.info("Found correct operators");
                return 1;
            }

            return 0;

        }

        if (result <= 0 || longs.isEmpty()) {
            return 0;
        }


        ArrayList<Op> opsForSum = newArrayList(operators);
        opsForSum.add(Op.SUM);

        ArrayList<Op> opsForMult = newArrayList(operators);
        opsForMult.add(MULT);

        ArrayList<Long> next = newArrayList(longs);
        Long component = next.removeLast();

        ArrayList<Long> nextComponents = newArrayList(components);
        nextComponents.add(component);

        long sumMult = 0;
        if (result % component == 0) {
            sumMult += countOfCorrectOperators(result / component, next, opsForMult, nextComponents);
        }
        return sumMult
                + countOfCorrectOperators(result - component, next, opsForSum, nextComponents);
    }

    public long solvePart2() {
        long sum = 0;
        for (int i = 0; i < results.size(); i++) {
            Long result = results.get(i);

            LOGGER.debug("Checking {}", result);
            long count = countOfCorrectOperatorsWithConcat(result, components.get(i), List.of(), newArrayList());
            LOGGER.debug("count for {}: {}", result, count);
            if (count > 0) {
                sum += result;
            }
        }
        return sum;
    }


    private long countOfCorrectOperatorsWithConcat(long result, List<Long> longs, List<Op> operators, List<Long> components) {
        if (longs.size() == 1) {
            Long lastEl = Iterables.getOnlyElement(longs);
            if (result == lastEl) {

                String fullOperation = "(".repeat(operators.size()) + lastEl;

                ArrayList<Op> mutableOps = newArrayList(operators);
                ArrayList<Long> mutableComponents = newArrayList(components);
                while (!mutableOps.isEmpty()) {
                    fullOperation += mutableOps.removeLast().print() + "" + mutableComponents.removeLast() + ")";
                }

                LOGGER.debug("Operation: {}", fullOperation);
                LOGGER.info("Found correct operators");
                return 1;
            }

            return 0;

        }

        if (result <= 0 || longs.isEmpty()) {
            return 0;
        }


        ArrayList<Op> opsForSum = newArrayList(operators);
        opsForSum.add(Op.SUM);



        ArrayList<Long> next = newArrayList(longs);
        Long component = next.removeLast();

        ArrayList<Long> nextComponents = newArrayList(components);
        nextComponents.add(component);

        long sumConcat = 0;
        String stringResult = "" + result;
        String stringComponent = "" + component;

        LOGGER.info("Result: {} - longs: {}", result, longs);
        LOGGER.info("Result: {} - stringComponent: {}", stringResult, stringComponent);

        if (stringResult.length() > stringComponent.length() && stringResult.endsWith(stringComponent)) {
            LOGGER.info("Trailing matches: Result: {} - stringComponent: {}", stringResult, stringComponent);

            ArrayList<Op> opsForConcat = newArrayList(operators);
            opsForConcat.add(Op.CONCAT);

            long expectedResult = Long.parseLong(stringResult.substring(0, stringResult.length() - stringComponent.length()));
            LOGGER.info("expectedResult: {}", expectedResult);
            sumConcat += countOfCorrectOperatorsWithConcat(
                    expectedResult,
                    next, opsForConcat, nextComponents);
        }

        long sumMult = 0;
        if (result % component == 0) {
            ArrayList<Op> opsForMult = newArrayList(operators);
            opsForMult.add(MULT);
            sumMult += countOfCorrectOperatorsWithConcat(result / component, next, opsForMult, nextComponents);
        }
        return sumMult + sumConcat
                + countOfCorrectOperatorsWithConcat(result - component, next, opsForSum, nextComponents);
    }

}
