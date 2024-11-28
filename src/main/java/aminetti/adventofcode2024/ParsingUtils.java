package aminetti.adventofcode2024;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Long.parseLong;

public class ParsingUtils {

    public static List<Long> readNumbers(String line) {
        List<Long> results = new ArrayList<>();
        Pattern pattern = Pattern.compile("(-?\\d+)");

        Matcher matcher = pattern.matcher(line);

        while (matcher.find()) {
            results.add(parseLong(matcher.group()));
        }
        return results;
    }


}
