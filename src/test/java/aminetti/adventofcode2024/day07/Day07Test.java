package aminetti.adventofcode2024.day07;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIf;

import java.io.IOException;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.apache.commons.io.IOUtils.readLines;
import static org.apache.commons.io.IOUtils.resourceToString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class Day07Test {

    @Test @EnabledIf("inputExists")
    void actualInputPart1() throws IOException {
        // given
        List<String> input = readLines(resourceToString("/day07/day07_input.txt", UTF_8));

        // when
        Day07 solver = new Day07();
        solver.parseInput(input);
        long l = solver.solvePart1();

        // then
        assertThat(l, is(lessThan(15766644995031L)));
        assertThat(l, is(14711933466277L));
    }

    @Test
    void testInputPart1() throws IOException {
        // given
        List<String> input = readLines(resourceToString("/day07/day07_input_test.txt", UTF_8));

        // when
        Day07 solver = new Day07();
        solver.parseInput(input);
        long l = solver.solvePart1();

        // then
        assertThat(l, is(3749L));
    }

    @Test @EnabledIf("inputExists")
    void actualInputPart2() throws IOException {
        // given
        List<String> input = readLines(resourceToString("/day07/day07_input.txt", UTF_8));

        // when
        Day07 solver = new Day07();
        solver.parseInput(input);
        long l = solver.solvePart2();

        // then
        assertThat(l, is(0L));
    }

    @Test
    void testInputPart2() throws IOException {
        // given
        List<String> input = readLines(resourceToString("/day07/day07_input_test.txt", UTF_8));

        // when
        Day07 solver = new Day07();
        solver.parseInput(input);
        long l = solver.solvePart2();

        // then
        assertThat(l, is(0L));
    }

    public static boolean inputExists() {
        return Day07Test.class.getResource("/day07/day07_input.txt") != null;
    }
}