package aminetti.adventofcode2024.day07;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

import static aminetti.adventofcode2024.day07.Day07.Op.*;
import static com.google.common.collect.Lists.newArrayList;

public class Day07 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day07.class);
    private final List<Long> results = new ArrayList<>();
    private final List<List<Long>> components = new ArrayList<>();

    public Day07() {
    }

    public void parseInput(List<String> input) {
        for (String s : input) {
            String[] split = s.split(":");
            results.add(Long.parseLong(split[0]));
            String[] split1 = split[1].split("\\s+");
            components.add(Arrays.stream(split1).filter(StringUtils::isNotBlank).map(Long::parseLong).toList());
        }
    }

    public long solvePart1() {
        EnumSet<Op> allowedOps = EnumSet.of(SUM, MULT);
        return countValidEquations(allowedOps);
    }

    private long countValidEquations(EnumSet<Op> allowedOps) {
        long sum = 0;
        for (int i = 0; i < results.size(); i++) {
            Long result = results.get(i);

            boolean valid = isValid(allowedOps, result, components.get(i), List.of(), newArrayList());
            LOGGER.debug("Is {}: valid? {}", result, valid);

            if (valid) {
                sum += result;
            }
        }
        return sum;
    }

    public enum Op {
        SUM, MULT, CONCAT;

        public String toString() {
            return switch (this) {
                case SUM -> "+";
                case MULT -> "*";
                case CONCAT -> "||";
            };
        }
    }

    public long solvePart2() {
        EnumSet<Op> allowedOps = EnumSet.allOf(Op.class);
        return countValidEquations(allowedOps);
    }


    private boolean isValid(final EnumSet<Op> allowedOps, long result, List<Long> lefts, List<Op> operators, List<Long> rights) {
        if (lefts.isEmpty()) {
            return false;
        }

        ArrayList<Long> nextLeft = newArrayList(lefts);
        Long current = nextLeft.removeLast();

        if (lefts.size() == 1) {
            if (result == current) {
                String fullOperation = printOperation(operators, rights, current);
                LOGGER.debug("Found correct operators, operations: {}", fullOperation);
                return true;
            }
            return false;
        }


        ArrayList<Long> nextRights = newArrayList(rights);
        nextRights.add(current);

        if (allowedOps.contains(MULT) && result % current == 0) { // divisions are the fastest to decrease a value
            ArrayList<Op> opsForMult = newArrayList(operators);
            opsForMult.add(MULT);
            if (isValid(allowedOps, result / current, nextLeft, opsForMult, nextRights)) {
                return true;
            }
        }

        String stringResult = "" + result;
        String stringComponent = "" + current;
        if (allowedOps.contains(CONCAT) && stringResult.length() > stringComponent.length() && stringResult.endsWith(stringComponent)) {
            // split a number (~divide by 10^log10(current) ) could be slower than divisions but faster than subtractions to decrease a value
            ArrayList<Op> opsForConcat = newArrayList(operators);
            opsForConcat.add(CONCAT);

            long expectedResult = Long.parseLong(stringResult.substring(0, stringResult.length() - stringComponent.length()));
            if (isValid(allowedOps, expectedResult, nextLeft, opsForConcat, nextRights)) {
                return true;
            }
        }

        if (allowedOps.contains(SUM) && result - current > 0) {
            ArrayList<Op> opsForSum = newArrayList(operators);
            opsForSum.add(SUM);
            if (isValid(allowedOps, result - current, nextLeft, opsForSum, nextRights)) {
                return true;
            }
        }



        return false;
    }

    private static String printOperation(List<Op> operators, List<Long> components, Long lastEl) {
        StringBuilder fullOperation = new StringBuilder("(".repeat(operators.size()) + lastEl);

        ArrayList<Op> mutableOps = newArrayList(operators);
        ArrayList<Long> mutableComponents = newArrayList(components);

        while (!mutableOps.isEmpty()) {
            fullOperation.append(mutableOps.removeLast())
                    .append(mutableComponents.removeLast()).append(")");
        }

        return fullOperation.toString();
    }

}
