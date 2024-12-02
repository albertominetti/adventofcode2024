package aminetti.adventofcode2024.day02;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.apache.commons.io.IOUtils.readLines;
import static org.apache.commons.io.IOUtils.resourceToString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class Day02Test {

    @Test
    void actualInputPart1() throws IOException {
        // given
        List<String> input = readLines(resourceToString("/day02/day02_input.txt", UTF_8));

        // when
        Day02 solver = new Day02();
        solver.parseInput(input);
        long l = solver.solvePart1();

        // then
        assertThat(l, is(663L));
    }

    @Test
    void testInputPart1() throws IOException {
        // given
        List<String> input = readLines(resourceToString("/day02/day02_input_test.txt", UTF_8));

        // when
        Day02 solver = new Day02();
        solver.parseInput(input);
        long l = solver.solvePart1();

        // then
        assertThat(l, is(2L));
    }

    @Test
    void actualInputPart2() throws IOException {
        // given
        List<String> input = readLines(resourceToString("/day02/day02_input.txt", UTF_8));

        // when
        Day02 solver = new Day02();
        solver.parseInput(input);
        long l = solver.solvePart2();

        // then
        assertThat(l, is(692L));
    }

    @Test
    void testInputPart2() throws IOException {
        // given
        List<String> input = readLines(resourceToString("/day02/day02_input_test.txt", UTF_8));

        // when
        Day02 solver = new Day02();
        solver.parseInput(input);
        long l = solver.solvePart2();

        // then
        assertThat(l, is(4L));
    }


}