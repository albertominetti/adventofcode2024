package aminetti.adventofcode2024.day06;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIf;

import java.io.IOException;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.apache.commons.io.IOUtils.readLines;
import static org.apache.commons.io.IOUtils.resourceToString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;

class Day06Test {

    @Test @EnabledIf("inputExists")
    void actualInputPart1() throws IOException {
        // given
        List<String> input = readLines(resourceToString("/day06/day06_input.txt", UTF_8));

        // when
        Day06 solver = new Day06();
        solver.parseInput(input);
        long l = solver.solvePart1();

        // then
        assertThat(l, is(4977L));
    }

    @Test
    void testInputPart1() throws IOException {
        // given
        List<String> input = readLines(resourceToString("/day06/day06_input_test.txt", UTF_8));

        // when
        Day06 solver = new Day06();
        solver.parseInput(input);
        long l = solver.solvePart1();

        // then
        assertThat(l, is(41L));
    }

    @Test @EnabledIf("inputExists")
    void actualInputPart2() throws IOException {
        // given
        List<String> input = readLines(resourceToString("/day06/day06_input.txt", UTF_8));

        // when
        Day06 solver = new Day06();
        solver.parseInput(input);
        long l = solver.solvePart2();

        // then
        assertThat(l, greaterThan(1650L));
        assertThat(l, greaterThan(1651L));
        assertThat(l, is(1729L));
    }

    @Test
    void testInputPart2() throws IOException {
        // given
        List<String> input = readLines(resourceToString("/day06/day06_input_test.txt", UTF_8));

        // when
        Day06 solver = new Day06();
        solver.parseInput(input);
        long l = solver.solvePart2();

        // then
        assertThat(l, is(6L));
    }

    public static boolean inputExists() {
        return Day06Test.class.getResource("/dayXX/dayXX_input.txt") != null;
    }
}