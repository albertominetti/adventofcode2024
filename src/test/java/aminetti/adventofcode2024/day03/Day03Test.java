package aminetti.adventofcode2024.day03;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.apache.commons.io.IOUtils.readLines;
import static org.apache.commons.io.IOUtils.resourceToString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class Day03Test {

    @Test
    void actualInputPart1() throws IOException {
        // given
        List<String> input = readLines(resourceToString("/day03/day03_input.txt", UTF_8));

        // when
        Day03 solver = new Day03();
        solver.parseInput(input);
        long l = solver.solvePart1();

        // then
        assertThat(l, is(179834255L));
    }

    @Test
    void testInputPart1() throws IOException {
        // given
        List<String> input = readLines(resourceToString("/day03/day03_input_test.txt", UTF_8));

        // when
        Day03 solver = new Day03();
        solver.parseInput(input);
        long l = solver.solvePart1();

        // then
        assertThat(l, is(161L));
    }

    @Test
    void actualInputPart2() throws IOException {
        // given
        List<String> input = readLines(resourceToString("/day03/day03_input.txt", UTF_8));

        // when
        Day03 solver = new Day03();
        solver.parseInput(input);
        long l = solver.solvePart2();

        // then
        assertThat(l, is(80570939L));
    }

    @Test
    void testInputPart2() throws IOException {
        // given
        List<String> input = readLines(resourceToString("/day03/day03_input_test2.txt", UTF_8));

        // when
        Day03 solver = new Day03();
        solver.parseInput(input);
        long l = solver.solvePart2();

        // then
        assertThat(l, is(48L));
    }


}