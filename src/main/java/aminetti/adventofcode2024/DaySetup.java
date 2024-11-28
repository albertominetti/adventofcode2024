package aminetti.adventofcode2024;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpCookie;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Month;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.StandardOpenOption.*;

public class DaySetup {
    private static final Logger LOGGER = LoggerFactory.getLogger(DaySetup.class);

    public static void main(String... args) throws IOException, InterruptedException {
        prepareCookie();

        int year = LocalDate.now().getYear();
        int day = getCurrentDay();

        cleanCodeAndTestAndInputForDay(year, day);

        downloadDayInput(year, day);
        prepareCodeForDay(year, day);
        prepareTestsForDay(year, day);

    }

    static void cleanCodeAndTestAndInputForDay(int year, int day) throws IOException, InterruptedException {
        String basePackageName = String.format("adventofcode%d", year);
        String packageName = String.format("day%02d", day);

        String testClassName = String.format("Day%02dTest", day);
        Path testClassPath = Paths.get("src", "test", "java", "aminetti", basePackageName, packageName, testClassName + ".java");
        Files.deleteIfExists(testClassPath);


        String className = String.format("Day%02d", day);
        Path classPath = Paths.get("src", "test", "java", "aminetti", basePackageName, packageName, className + ".java");
        Files.deleteIfExists(classPath);

        String folderName = String.format("day%02d", day);
        String fileName = String.format("day%02d_input.txt", day);
        Path inputFile = Paths.get("src", "test", "resources", folderName, fileName);
        Files.deleteIfExists(inputFile);

        String testFileName = String.format("day%02d_input_test.txt", day);
        Path testInputFile = Paths.get("src", "test", "resources", folderName, testFileName);
        Files.deleteIfExists(testInputFile);

        LOGGER.info("All the files related to day {} has been deleted.", day);

    }

    static String getInput(int year, int day) throws IOException, InterruptedException {
        URI uri = URI.create(String.format("https://adventofcode.com/%d/day/%d/input", year, day));

        HttpRequest req = HttpRequest.newBuilder()
                .uri(uri)
                .header("User-Agent", "github.com/albertominetti/")
                .GET().build();

        try (HttpClient client = HttpClient.newBuilder()
                .cookieHandler(CookieHandler.getDefault())
                .version(HttpClient.Version.HTTP_2)
                .connectTimeout(Duration.ofSeconds(10))
                .build()) {
            return client.send(req, HttpResponse.BodyHandlers.ofString()).body();
        }
    }

    static void prepareCookie() throws IOException {
        CookieManager cookieManager = new CookieManager();
        String textCookie = IOUtils.resourceToString("/cookie.txt", UTF_8);
        if (textCookie == null) {
            throw new IllegalArgumentException("Missing cookie file, please check your cookie.txt file in the resource directory.");
        }
        Pattern patternForCookie = Pattern.compile("session=([a-f0-9]+)");
        Matcher matcher = patternForCookie.matcher(textCookie);
        if (matcher.matches()) {
            HttpCookie sessionCookie = new HttpCookie("session", matcher.group(1));
            sessionCookie.setPath("/");
            sessionCookie.setVersion(0);
            cookieManager.getCookieStore()
                    .add(URI.create("https://adventofcode.com"), sessionCookie);
        } else {
            throw new IllegalArgumentException("Invalid cookie format, please check your cookie.txt file.");
        }

        CookieHandler.setDefault(cookieManager);
    }


    static void prepareTestsForDay(int year, int day) throws IOException, InterruptedException {
        String basePackageName = String.format("adventofcode%d", year);
        String packageName = String.format("day%02d", day);
        String testClassName = String.format("Day%02dTest", day);

        Path testClassPath = Paths.get("src", "test", "java", "aminetti", basePackageName, packageName, testClassName + ".java");
        Path templateClassPath = Paths.get("src", "test", "java", "aminetti", "adventofcode2024", "dayXX", "DayXXTest.java");

        if (Files.exists(testClassPath)) {
            LOGGER.warn("The test class file {} is already existent.", testClassPath.toAbsolutePath());
            return;
        }

        Files.createDirectories(testClassPath.getParent());
        String templateClassContent = Files.readString(templateClassPath);


        String className = String.format("Day%02d", day);
        String classContent = templateClassContent
                .replaceAll("adventofcode[0-9]+", basePackageName)
                .replace("DayXX", className)
                .replace("DayXXTest", testClassName)
                .replaceAll("dayXX", packageName);
        Files.writeString(testClassPath, classContent, WRITE, TRUNCATE_EXISTING, CREATE);

        LOGGER.info("The test java source file {} has been created.", testClassPath.toAbsolutePath());

    }

    static void prepareCodeForDay(int year, int day) throws IOException, InterruptedException {
        String basePackageName = String.format("adventofcode%d", year);
        String packageName = String.format("day%02d", day);
        String className = String.format("Day%02d", day);

        Path classPath = Paths.get("src", "main", "java", "aminetti", basePackageName, packageName, className + ".java");
        Path templateClassPath = Paths.get("src", "main", "java", "aminetti", "adventofcode2024", "dayXX", "DayXX.java");

        if (Files.exists(classPath)) {
            LOGGER.warn("The class file {} is already existent.", classPath.toAbsolutePath());
            return;
        }

        Files.createDirectories(classPath.getParent());
        String templateClassContent = Files.readString(templateClassPath);
        String classContent = templateClassContent
                .replaceAll("adventofcode[0-9]+", basePackageName)
                .replace("DayXX", className)
                .replaceAll("dayXX", packageName);
        Files.writeString(classPath, classContent, WRITE, TRUNCATE_EXISTING, CREATE);

        LOGGER.info("The java source file {} has been created.", classPath.toAbsolutePath());

    }


    static void downloadDayInput(int year, int day) throws IOException, InterruptedException {
        String folderName = String.format("day%02d", day);
        String fileName = String.format("day%02d_input.txt", day);
        Path path = Paths.get("src", "test", "resources", folderName, fileName);

        if (Files.exists(path)) {
            LOGGER.warn("The input file {} is already existent.", path.toAbsolutePath());
            return;
        }

        Files.createDirectories(path.getParent());
        Path testInput = path.getParent().resolve(String.format("day%02d_input_test.txt", day));
        Files.createFile(testInput);
        LOGGER.info("The input file {} has been created.", testInput.toAbsolutePath());

        String dayInput = getInput(year, day);
        Files.writeString(path, dayInput, WRITE, TRUNCATE_EXISTING, CREATE);
        LOGGER.info("The input file {} has been created.", path.toAbsolutePath());
    }

    static int getCurrentDay() {
        LocalDate now = LocalDate.now();
        if (now.getMonth() != Month.DECEMBER) {
            throw new IllegalArgumentException("It is still not December.");
        }

        return now.getDayOfMonth();
    }
}