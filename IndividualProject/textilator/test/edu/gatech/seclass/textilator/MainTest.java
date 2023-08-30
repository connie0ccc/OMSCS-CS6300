package edu.gatech.seclass.textilator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

// DO NOT ALTER THIS CLASS. Use it as an example for MyMainTest.java

@Timeout(value = 1, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
public class MainTest {
    private final String usageStr =
        "Usage: textilator [ -s number | -x substring | -c case | -e num | -a | -p prefix ] FILE"
            + System.lineSeparator();

    @TempDir
    Path tempDirectory;

    @RegisterExtension
    OutputCapture capture = new OutputCapture();

    /*
     * Test Utilities
     */

    private Path createFile(String contents) {
        return createFile(contents, "sample.txt");
    }

    private Path createFile(String contents, String fileName) {
        Path file = tempDirectory.resolve(fileName);

        try {
            Files.write(file, contents.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return file;
    }

    private String getFileContent(Path file) {
        try {
            return Files.readString(file, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /*
     * Test Cases
     */

    @Test
    public void exampleTest1() {
        String input = "";

        Path inputFile = createFile(input);
        String[] args = {inputFile.toString()};
        Main.main(args);

        Assertions.assertTrue(capture.stdout().isEmpty());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    public void exampleTest2() {
        String input = "I AM SHOUTING!" + System.lineSeparator();
        String expected = "i am shouting!" + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-c", "lower", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    public void exampleTest3() {
        String input = " is tomorrow." + System.lineSeparator()
            + " has been a day." + System.lineSeparator()
            + " is another day." + System.lineSeparator();
        String expected = "Today is tomorrow." + System.lineSeparator()
            + "Today has been a day." + System.lineSeparator()
            + "Today is another day." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-p", "Today", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    public void exampleTest4() {
        String input = "The Krabby Patty Secret Formula is..." + System.lineSeparator();
        String expected = "The Krabby Patty Secret Formula is..." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-e", "24", "-e", "0", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    public void exampleTest5() {
        String input = "This is a normal text file." + System.lineSeparator()
            + "Perhaps too normal..." + System.lineSeparator();


        Path inputFile = createFile(input);
        String[] args = {"-s", inputFile.toString()};
        Main.main(args);

        Assertions.assertTrue(capture.stdout().isEmpty());
        Assertions.assertEquals(usageStr, capture.stderr());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    public void exampleTest6() {
        String input = "This list is really important and should NOT get deleted." + System.lineSeparator()
            + "* watermelon" + System.lineSeparator()
            + "* sunflower" + System.lineSeparator()
            + "* community center" + System.lineSeparator()
            + "* pelican town" + System.lineSeparator();
        String expected = "this list is really important and should not get deleted." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-x", "*", "-c", "lower", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    public void exampleTest7() {
        String input = "This is a list of items that start with the \"c\":" + System.lineSeparator()
            + "Krabby patty" + System.lineSeparator()
            + "Chocolate chip cookie" + System.lineSeparator()
            + "Pineapple" + System.lineSeparator()
            + "Computer" + System.lineSeparator();
        String expected = "-This is a list of items that start with the \"c\":" + System.lineSeparator()
            + "-Chocolate chip cookie" + System.lineSeparator()
            + "-Computer" + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-s", "0", "-p", "-", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    public void exampleTest8() {
        String input = "This is fine." + System.lineSeparator();
        String expected = "84 104 105 115 32 105 115 32 102 105 110 101 46 " + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-a", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

}
