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
public class MyMainTest {
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

        // Place all  of your tests in this class, optionally using MainTest.java as an example
        @Test
        public void textilatorTest1() {
            String input = "";

            Path inputFile = createFile(input);
            String[] args = {inputFile.toString()};
            Main.main(args);

            Assertions.assertTrue(capture.stdout().isEmpty());
            Assertions.assertTrue(capture.stderr().isEmpty());
            Assertions.assertEquals(input, getFileContent(inputFile));
        }

        @Test
        public void textilatorTest2() {
            String input = "The Krabby Patty Secret Formula is..." + System.lineSeparator();
            String expected = "0The Krabby Patty Secret Formula is..." + System.lineSeparator();

            Path inputFile = createFile(input, "haha.txt");
            String[] args = {"-p", "24", "-p", "0", inputFile.toString()};
            Main.main(args);

            Assertions.assertEquals(expected, capture.stdout());
            Assertions.assertEquals(input, getFileContent(inputFile));
        }

        @Test
        public void textilatorTest3() {
            String input = "This is fine." + System.lineSeparator();
            String expected = "this is fine." + System.lineSeparator();

            Path inputFile = createFile(input);
            String[] args = {"-c","upper","-c","lower", inputFile.toString()};
            Main.main(args);

            Assertions.assertEquals(expected, capture.stdout());
            Assertions.assertTrue(capture.stderr().isEmpty());
            Assertions.assertEquals(input, getFileContent(inputFile));
        }

        @Test
        public void textilatorTest4()throws Exception {
            String input = "This is fine." + System.lineSeparator();
            String[] args = {"-c", "haha", "-e", "1"};
            Path inputFile = createFile(input);
            Main.main(args);

            Assertions.assertTrue(capture.stdout().isEmpty());
            Assertions.assertEquals(usageStr, capture.stderr());
            Assertions.assertEquals(input, getFileContent(inputFile));
        }

        @Test
        public void textilatorTest5() {
            String input = "This is fine." + System.lineSeparator();
            String[] args = {"-c", "-e", "5"};
            Path inputFile = createFile(input);
            Main.main(args);

            Assertions.assertTrue(capture.stdout().isEmpty());
            Assertions.assertEquals(usageStr, capture.stderr());
            Assertions.assertEquals(input, getFileContent(inputFile));
        }

        @Test
        public void textilatorTest6() {
            String input = "a" + System.lineSeparator();
            String expected = "b" + System.lineSeparator();

            Path inputFile = createFile(input);
            String[] args = {"-e","6","-e","1", inputFile.toString()};
            Main.main(args);

            Assertions.assertEquals(expected, capture.stdout());
            Assertions.assertTrue(capture.stderr().isEmpty());
            Assertions.assertEquals(input, getFileContent(inputFile));
        }

        @Test
        public void textilatorTest7() {
            String input = "This is fine." + System.lineSeparator();
            String[] args = {"-e"};
            Path inputFile = createFile(input);
            Main.main(args);

            Assertions.assertTrue(capture.stdout().isEmpty());
            Assertions.assertEquals(usageStr, capture.stderr());
            Assertions.assertEquals(input, getFileContent(inputFile));
        }

        @Test
        public void textilatorTest8() {
            String input = "This is fine." + System.lineSeparator();
            String[] args = {"-e","99"};
            Path inputFile = createFile(input);
            Main.main(args);

            Assertions.assertTrue(capture.stdout().isEmpty());
            Assertions.assertEquals(usageStr, capture.stderr());
            Assertions.assertEquals(input, getFileContent(inputFile));
        }
        @Test
        public void textilatorTest9() {
            String input = "This is fine." + System.lineSeparator();
            String[] args = {"-e","A"};
            Path inputFile = createFile(input);
            Main.main(args);

            Assertions.assertTrue(capture.stdout().isEmpty());
            Assertions.assertEquals(usageStr, capture.stderr());
            Assertions.assertEquals(input, getFileContent(inputFile));
        }

        @Test
        public void textilatorTest10() {
            String input = "is tomorrow." + System.lineSeparator()
            + "has been a day." + System.lineSeparator()
            + " - is another day." + System.lineSeparator();
            String expected = "is tomorrow." + System.lineSeparator()
            + "has been a day." + System.lineSeparator();

            Path inputFile = createFile(input);
            String[] args = {"-x", "0", "-x", "-", inputFile.toString()};
            Main.main(args);

            Assertions.assertEquals(expected, capture.stdout());
            Assertions.assertTrue(capture.stderr().isEmpty());
            Assertions.assertEquals(input, getFileContent(inputFile));
        }
        @Test
        public void textilatorTest11() {
            String input = "This is fine." + System.lineSeparator();
            Path inputFile = createFile(input);
            String[] args = {"-x"};
            Main.main(args);

            Assertions.assertTrue(capture.stdout().isEmpty());
            Assertions.assertEquals(usageStr, capture.stderr());
            Assertions.assertEquals(input, getFileContent(inputFile));
        }
        @Test
        public void textilatorTest12() {
            String input = "This is fine." + System.lineSeparator();
            Path inputFile = createFile(input);
            String[] args = {"-s","4","-x","4"};
            Main.main(args);
            
            Assertions.assertTrue(capture.stdout().isEmpty());
            Assertions.assertEquals(usageStr, capture.stderr());
            Assertions.assertEquals(input, getFileContent(inputFile));
        }

        @Test
        public void textilatorTest13() {
            String input = "is tomorrow." + System.lineSeparator()
            + "has been a day." + System.lineSeparator()
            + "is another day." + System.lineSeparator();
            String expected = "is tomorrow." + System.lineSeparator()
            + "is another day." + System.lineSeparator();

            Path inputFile = createFile(input);
            String[] args = {"-s", "1", "-s", "0", inputFile.toString()};
            Main.main(args);

            Assertions.assertEquals(expected, capture.stdout());
            Assertions.assertTrue(capture.stderr().isEmpty());
            Assertions.assertEquals(input, getFileContent(inputFile));
        }

        @Test
        public void textilatorTest14() {
            String input = "This is fine." + System.lineSeparator();
            Path inputFile = createFile(input);
            String[] args = {"-s"};
            Main.main(args);
            Assertions.assertTrue(capture.stdout().isEmpty());
            Assertions.assertEquals(usageStr, capture.stderr());
            Assertions.assertEquals(input, getFileContent(inputFile));
        }

        @Test
        public void textilatorTest15() {
            String input = "This is fine." + System.lineSeparator();
            Path inputFile = createFile(input);
            String[] args = {"-s","4"};
            Main.main(args);
            Assertions.assertTrue(capture.stdout().isEmpty());
            Assertions.assertEquals(usageStr, capture.stderr());
            Assertions.assertEquals(input, getFileContent(inputFile));
        }

        @Test
        public void textilatorTest16() {
            String input = "This is fine." + System.lineSeparator();
            Path inputFile = createFile(input);
            String[] args = {"-e","4","-a"};
            Main.main(args);
            Assertions.assertTrue(capture.stdout().isEmpty());
            Assertions.assertEquals(usageStr, capture.stderr());
            Assertions.assertEquals(input, getFileContent(inputFile));
        }
        
        @Test
        public void textilatorTest17() {
            String input = "The Krabby Patty Secret Formula is..." + System.lineSeparator();
            String expected = "0The Krabby Patty Secret Formula is..." + System.lineSeparator();

            Path inputFile = createFile(input);
            String[] args = {"-p", "24", "-p", "0", inputFile.toString()};
            Main.main(args);

            Assertions.assertEquals(expected, capture.stdout());
            Assertions.assertTrue(capture.stderr().isEmpty());
            Assertions.assertEquals(input, getFileContent(inputFile));
        }

        @Test
        public void textilatorTest18() {
            String input = "This is fine." + System.lineSeparator();
            Path inputFile = createFile(input);
            String[] args = {"-p"," "};
            Main.main(args);
            Assertions.assertTrue(capture.stdout().isEmpty());
            Assertions.assertEquals(usageStr, capture.stderr());
            Assertions.assertEquals(input, getFileContent(inputFile));
        
        }

        @Test
        public void textilatorTest19() {
            String input = "This is fine." + System.lineSeparator();
            Path inputFile = createFile(input);
            String[] args = {"-p"};
            Main.main(args);
            Assertions.assertTrue(capture.stdout().isEmpty());
            Assertions.assertEquals(usageStr, capture.stderr());
            Assertions.assertEquals(input, getFileContent(inputFile));
        }

    @Test
    public void textilatorTest20() {
        String input = "This is a test." + System.lineSeparator()
                + "Hello, world!" + System.lineSeparator()
                + "Test successful." + System.lineSeparator();
        String expected = "Encoded: guvf vf n grfg." + System.lineSeparator()
                + "Encoded: grfg fhpprffshy." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-s", "0", "-c", "lower", "-e", "13", "-p", "Encoded: ", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    public void textilatorTest21() {
        String input = "This is a test." + System.lineSeparator()
                + "Hello, world!" + System.lineSeparator()
                + "Test successful." + System.lineSeparator();
        String expected = "uryyb, jbeyq!" + System.lineSeparator()
                + "grfg fhpprffshy." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-c", "lower", "-e", "13", "-x", "This", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }
    @Test
    public void textilatorTest22() {
        String input = "Some text here." + System.lineSeparator()
            + "Another line of text." + System.lineSeparator()
            + "Third line." + System.lineSeparator()
            + "Yet another line." + System.lineSeparator();
        String expected = "TESTuqog vgzv jgtg." + System.lineSeparator()
            + "TESTvjktf nkpg." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-c", "lower", "-e", "2", "-s", "0", "-p", "TEST", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }
    @Test
    public void textilatorTest23() {
        String input = "This is a test." + System.lineSeparator()
            + "Another line of text." + System.lineSeparator()
            + "Third line of content." + System.lineSeparator()
            + "Yet another line." + System.lineSeparator();
        String expected = "THIS IS A TEST." + System.lineSeparator()
            + "THIRD LINE OF CONTENT." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-c", "upper", "-e", "0", "-s", "0", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    public void textilatorTest24() {
        String input = "This is a test." + System.lineSeparator()
            + "Another line of text." + System.lineSeparator()
            + "Third line of content." + System.lineSeparator()
            + "Yet another line." + System.lineSeparator();
        String expected = "Prefix: THIS IS A TEST." + System.lineSeparator()
            + "Prefix: ANOTHER LINE OF TEXT." + System.lineSeparator()
            + "Prefix: THIRD LINE OF CONTENT." + System.lineSeparator()
            + "Prefix: YET ANOTHER LINE." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-c", "upper", "-e", "0", "-p", "Prefix: ", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }
    @Test
    public void textilatorTest25() {
        String input = "This is a test." + System.lineSeparator()
            + "Another line of text." + System.lineSeparator()
            + "Third line of content." + System.lineSeparator()
            + "Yet another line." + System.lineSeparator();
        String expected = "wklv lv d whvw." + System.lineSeparator()
            + "dqrwkhu olqh ri whaw." + System.lineSeparator()
            + "wklug olqh ri frqwhqw." + System.lineSeparator()
            + "bhw dqrwkhu olqh." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-c", "lower", "-e", "3", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    // Test Case 25
    @Test
    public void textilatorTest26() {
        String input = "This is a test." + System.lineSeparator()
            + "Another line of text." + System.lineSeparator()
            + "Third line of content." + System.lineSeparator()
            + "Yet another line." + System.lineSeparator();
        String expected = "A84 72 73 83 32 73 83 32 65 32 84 69 83 84 46 " + System.lineSeparator()
            + "A65 78 79 84 72 69 82 32 76 73 78 69 32 79 70 32 84 69 88 84 46 " + System.lineSeparator()
            + "A84 72 73 82 68 32 76 73 78 69 32 79 70 32 67 79 78 84 69 78 84 46 " + System.lineSeparator()
            + "A89 69 84 32 65 78 79 84 72 69 82 32 76 73 78 69 46 " + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-c", "upper", "-x", "reverse", "-a", "-p", "A",inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }
    @Test
    public void textilatorTest27() {
        String input = "This is a test." + System.lineSeparator()
            + "Another line of text." + System.lineSeparator()
            + "Third line of content." + System.lineSeparator()
            + "Yet another line." + System.lineSeparator();
        String expected = "84 72 73 83 32 73 83 32 65 32 84 69 83 84 46 " + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-c", "upper", "-x", "line", "-a",inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    public void textilatorTest28() {
        String input = "This is a test." + System.lineSeparator()
            + "line of content." + System.lineSeparator();
        String expected = "0THIS IS A TEST." + System.lineSeparator()
            + "0LINE OF CONTENT." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-c", "upper", "-x", "0", "-p", "0", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }
    @Test
    public void textilatorTest29() {
        String input = "This is a test." + System.lineSeparator()
            + "line of content." + System.lineSeparator();
        String expected = "THIS IS A TEST." + System.lineSeparator()
            + "LINE OF CONTENT." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-c", "upper", "-x", "0", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }
    // Test Case 29
    @Test
    public void textilatorTest30() {
        String input = "This is a test." + System.lineSeparator()
            + "Another line of text." + System.lineSeparator()
            + "Third line of content." + System.lineSeparator()
            + "Yet another line." + System.lineSeparator();
        String expected = "P84 72 73 83 32 73 83 32 65 32 84 69 83 84 46 " + System.lineSeparator()
            + "P84 72 73 82 68 32 76 73 78 69 32 79 70 32 67 79 78 84 69 78 84 46 " + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-c", "upper", "-s", "0", "-a", "-p", "P", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
    }

    @Test
    public void textilatorTest31() {
        String input = "This is a test." + System.lineSeparator()
            + "Another line of text." + System.lineSeparator()
            + "Third line of content." + System.lineSeparator()
            + "Yet another line." + System.lineSeparator();
        String expected = "84 72 73 83 32 73 83 32 65 32 84 69 83 84 46 " + System.lineSeparator()
            + "84 72 73 82 68 32 76 73 78 69 32 79 70 32 67 79 78 84 69 78 84 46 " + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-c", "upper", "-s", "0", "-a", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
    }
    @Test
    public void textilatorTest32() {
        String input = "This is a test." + System.lineSeparator()
            + "line of content." + System.lineSeparator();
        String expected = "0THIS IS A TEST." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-c", "upper", "-s", "0", "-p","0",inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }
    // Test Case 32
    @Test
    public void textilatorTest33() {
        String input = "This is a test." + System.lineSeparator()
            + "Another line of text." + System.lineSeparator()
            + "Third line of content." + System.lineSeparator()
            + "Yet another line." + System.lineSeparator();
        String expected = "THIS IS A TEST." + System.lineSeparator()
            + "THIRD LINE OF CONTENT." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-c", "upper", "-s", "0", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    // Test Case 33
    @Test
    public void textilatorTest34() {
        String input = "This is a test." + System.lineSeparator()
            + "Another line of text." + System.lineSeparator()
            + "Third line of content." + System.lineSeparator()
            + "Yet another line." + System.lineSeparator();
        String expected = "Haha84 72 73 83 32 73 83 32 65 32 84 69 83 84 46 " + System.lineSeparator()
            + "Haha65 78 79 84 72 69 82 32 76 73 78 69 32 79 70 32 84 69 88 84 46 " + System.lineSeparator()
            + "Haha84 72 73 82 68 32 76 73 78 69 32 79 70 32 67 79 78 84 69 78 84 46 " + System.lineSeparator()
            + "Haha89 69 84 32 65 78 79 84 72 69 82 32 76 73 78 69 46 " + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-c", "upper", "-a", "-p", "Haha", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }
    @Test
    public void textilatorTest35() {
        String input = "This is a test." + System.lineSeparator()
            + "Another line of text." + System.lineSeparator()
            + "Third line of content." + System.lineSeparator()
            + "Yet another line." + System.lineSeparator();
            String expected = "84 72 73 83 32 73 83 32 65 32 84 69 83 84 46 " + System.lineSeparator()
            + "65 78 79 84 72 69 82 32 76 73 78 69 32 79 70 32 84 69 88 84 46 " + System.lineSeparator()
            + "84 72 73 82 68 32 76 73 78 69 32 79 70 32 67 79 78 84 69 78 84 46 " + System.lineSeparator()
            + "89 69 84 32 65 78 79 84 72 69 82 32 76 73 78 69 46 " + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-c", "upper", "-a", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    // Test Case 35
    @Test
    public void textilatorTest36() {
        String input = "This is a test." + System.lineSeparator()
            + "Another line of text." + System.lineSeparator()
            + "Third line of content." + System.lineSeparator()
            + "Yet another line." + System.lineSeparator();
        String expected = "YLINthis is a test." + System.lineSeparator()
            + "YLINanother line of text." + System.lineSeparator()
            + "YLINthird line of content." + System.lineSeparator()
            + "YLINyet another line." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-c", "lower", "-p", "YLIN", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
    }


    // Test Case 36
    @Test
    public void textilatorTest37() {
        String input = "This is a test." + System.lineSeparator()
            + "Another line of text." + System.lineSeparator()
            + "Third line of content." + System.lineSeparator()
            + "Yet another line." + System.lineSeparator();
        String expected = "this is a test." + System.lineSeparator()
            + "another line of text." + System.lineSeparator()
            + "third line of content." + System.lineSeparator()
            + "yet another line." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-c", "lower", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
    }

    @Test
    public void textilatorTest38() {
        String input = "This is a test." + System.lineSeparator()
            + "Another line of text." + System.lineSeparator()
            + "Third line of content." + System.lineSeparator()
            + "Yet another line." + System.lineSeparator();
        String expected = "HaWklv lv d whvw." + System.lineSeparator()
            + "HaDqrwkhu olqh ri whaw." + System.lineSeparator()
            + "HaWklug olqh ri frqwhqw." + System.lineSeparator()
            + "HaBhw dqrwkhu olqh." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-e", "3","-x", "Haha","-p","Ha", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }
    @Test
    public void textilatorTest39() {
        String input = "This is a test." + System.lineSeparator()
            + "Another line of text." + System.lineSeparator()
            + "Third line of content." + System.lineSeparator()
            + "Yet another line." + System.lineSeparator();
        String expected = "Wklv lv d whvw." + System.lineSeparator()
            + "Dqrwkhu olqh ri whaw." + System.lineSeparator()
            + "Wklug olqh ri frqwhqw." + System.lineSeparator()
            + "Bhw dqrwkhu olqh." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-e", "3","-x", "Haha", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    public void textilatorTest40() {
        String input = "This is a test." + System.lineSeparator()
            + "Another line of text." + System.lineSeparator()
            + "Third line of content." + System.lineSeparator()
            + "Yet another line." + System.lineSeparator();
        String expected = "HahaWklv lv d whvw." + System.lineSeparator()
            + "HahaWklug olqh ri frqwhqw." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-e", "3","-s","0","-p", "Haha", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }
    @Test
    public void textilatorTest41() {
        String input = "This is a test." + System.lineSeparator()
            + "Another line of text." + System.lineSeparator()
            + "Third line of content." + System.lineSeparator()
            + "Yet another line." + System.lineSeparator();
        String expected = "Wklv lv d whvw." + System.lineSeparator()
            + "Wklug olqh ri frqwhqw." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-e", "3","-s","0", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }
    @Test
    public void textilatorTest42() {
        String input = "This is a test." + System.lineSeparator()
            + "Another line of text." + System.lineSeparator()
            + "Third line of content." + System.lineSeparator()
            + "Yet another line." + System.lineSeparator();
        String expected = "XWklv lv d whvw." + System.lineSeparator()
            + "XDqrwkhu olqh ri whaw." + System.lineSeparator()
            + "XWklug olqh ri frqwhqw." + System.lineSeparator()
            + "XBhw dqrwkhu olqh." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-e", "3","-p", "X", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }
    @Test
    public void textilatorTest43() {
        String input = "This is a test." + System.lineSeparator()
            + "Another line of text." + System.lineSeparator()
            + "Third line of content." + System.lineSeparator()
            + "Yet another line." + System.lineSeparator();
        String expected = "Wklv lv d whvw." + System.lineSeparator()
            + "Dqrwkhu olqh ri whaw." + System.lineSeparator()
            + "Wklug olqh ri frqwhqw." + System.lineSeparator()
            + "Bhw dqrwkhu olqh." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-e", "3", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));

    }
    @Test
    public void textilatorTest44() {
        String input = "This is a test." + System.lineSeparator()
            + "Another line of text." + System.lineSeparator()
            + "Third line of content." + System.lineSeparator()
            + "Yet another line." + System.lineSeparator();
        String expected = "A84 104 105 115 32 105 115 32 97 32 116 101 115 116 46 " + System.lineSeparator()
            + "A65 110 111 116 104 101 114 32 108 105 110 101 32 111 102 32 116 101 120 116 46 " + System.lineSeparator()
            + "A84 104 105 114 100 32 108 105 110 101 32 111 102 32 99 111 110 116 101 110 116 46 " + System.lineSeparator()
            + "A89 101 116 32 97 110 111 116 104 101 114 32 108 105 110 101 46 " + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-x", "upper", "-a","-p","A",inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    public void textilatorTest45() {
        String input = "This is a test." + System.lineSeparator()
            + "Another line of text." + System.lineSeparator()
            + "Third line of content." + System.lineSeparator()
            + "Yet another line." + System.lineSeparator();
        String expected = "84 104 105 115 32 105 115 32 97 32 116 101 115 116 46 " + System.lineSeparator()
            + "65 110 111 116 104 101 114 32 108 105 110 101 32 111 102 32 116 101 120 116 46 " + System.lineSeparator()
            + "84 104 105 114 100 32 108 105 110 101 32 111 102 32 99 111 110 116 101 110 116 46 " + System.lineSeparator()
            + "89 101 116 32 97 110 111 116 104 101 114 32 108 105 110 101 46 " + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-x", "upper", "-a",inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    public void textilatorTest46() {
        String input = "This is a test." + System.lineSeparator()
                + "Hello, world!" + System.lineSeparator()
                + "Test successful." + System.lineSeparator();
        String expected = "Encoded: This is a test." + System.lineSeparator()
                + "Encoded: Test successful." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = { "-x", "Hello", "-p", "Encoded: ", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected,capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    public void textilatorTest47() {
        String input = "This is a test." + System.lineSeparator()
                + "Hello, world!" + System.lineSeparator()
                + "Test successful." + System.lineSeparator();
        String expected = "This is a test." + System.lineSeparator()
                + "Test successful." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = { "-x", "Hello", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected,capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    public void textilatorTest48() {
        String input = "This is a test." + System.lineSeparator()
            + "Another line of text." + System.lineSeparator()
            + "Third line of content." + System.lineSeparator()
            + "Yet another line." + System.lineSeparator();
        String expected = "A84 104 105 115 32 105 115 32 97 32 116 101 115 116 46 " + System.lineSeparator()
        + "A84 104 105 114 100 32 108 105 110 101 32 111 102 32 99 111 110 116 101 110 116 46 " + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-s","0","-a","-p","A",inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }
    @Test
    public void textilatorTest49() {
        String input = "This is a test." + System.lineSeparator()
            + "Another line of text." + System.lineSeparator()
            + "Third line of content." + System.lineSeparator()
            + "Yet another line." + System.lineSeparator();
        String expected = "84 104 105 115 32 105 115 32 97 32 116 101 115 116 46 " + System.lineSeparator()
            + "84 104 105 114 100 32 108 105 110 101 32 111 102 32 99 111 110 116 101 110 116 46 " + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-s", "0", "-a",inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }
    @Test
    public void textilatorTest50() {
        String input = "This is a test." + System.lineSeparator()
                + "Hello, world!" + System.lineSeparator()
                + "Test successful." + System.lineSeparator();
        String expected = "0This is a test." + System.lineSeparator()
                + "0Test successful." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = { "-s", "0","-p","0",inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected,capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    public void textilatorTest51() {
        String input = "This is a test." + System.lineSeparator()
                + "Hello, world!" + System.lineSeparator()
                + "Test successful." + System.lineSeparator();
        String expected = "This is a test." + System.lineSeparator()
                + "Test successful." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = { "-s", "0",inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected,capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    public void textilatorTest52() {
        String input = "This is a test." + System.lineSeparator()
            + "Another line of text." + System.lineSeparator()
            + "Third line of content." + System.lineSeparator()
            + "Yet another line." + System.lineSeparator();
        String expected = "upper84 104 105 115 32 105 115 32 97 32 116 101 115 116 46 " + System.lineSeparator()
            + "upper65 110 111 116 104 101 114 32 108 105 110 101 32 111 102 32 116 101 120 116 46 " + System.lineSeparator()
            + "upper84 104 105 114 100 32 108 105 110 101 32 111 102 32 99 111 110 116 101 110 116 46 " + System.lineSeparator()
            + "upper89 101 116 32 97 110 111 116 104 101 114 32 108 105 110 101 46 " + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-p", "upper", "-a",inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }
    @Test
    public void textilatorTest53() {
        String input = "This is a test." + System.lineSeparator()
            + "Another line of text." + System.lineSeparator()
            + "Third line of content." + System.lineSeparator()
            + "Yet another line." + System.lineSeparator();
        String expected = "84 104 105 115 32 105 115 32 97 32 116 101 115 116 46 " + System.lineSeparator()
            + "65 110 111 116 104 101 114 32 108 105 110 101 32 111 102 32 116 101 120 116 46 " + System.lineSeparator()
            + "84 104 105 114 100 32 108 105 110 101 32 111 102 32 99 111 110 116 101 110 116 46 " + System.lineSeparator()
            + "89 101 116 32 97 110 111 116 104 101 114 32 108 105 110 101 46 " + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-a",inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }
    @Test
    public void textilatorTest54() {
        String input = "This is a test." + System.lineSeparator()
            + "Another line of text." + System.lineSeparator()
            + "Third line of content." + System.lineSeparator()
            + "Yet another line." + System.lineSeparator();
        String expected = "0This is a test." + System.lineSeparator()
        + "0Another line of text." + System.lineSeparator()
        + "0Third line of content." + System.lineSeparator()
        + "0Yet another line." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-p", "0",inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }
    @Test
    public void textilatorTest55() {
        String input = "This is fine." + System.lineSeparator();
        String[] args = {};
        Path inputFile = createFile(input);
        Main.main(args);

        Assertions.assertTrue(capture.stdout().isEmpty());
        Assertions.assertEquals(usageStr, capture.stderr());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }
    
    @Test
    public void textilatorTest56() {
        String input = "This is a test." + System.lineSeparator()
            + "Another line of text." + System.lineSeparator()
            + "Third line of content." + System.lineSeparator()
            + "Yet another line." + System.lineSeparator();
        String expected ="";

        Path inputFile = createFile(input);
        String[] args = {"-x","",inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }
    @Test
    public void textilatorTest57() {
        String input = "This is a test." + System.lineSeparator()
                + "Hello, world!" + System.lineSeparator()
                + "Test successful." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = { "-s", "1.0",inputFile.toString()};
        Main.main(args);

        Assertions.assertTrue(capture.stdout().isEmpty());
        Assertions.assertEquals(usageStr, capture.stderr());
        Assertions.assertEquals(input, getFileContent(inputFile));
}

    @Test
    public void textilatorTest58() {
        String input = "This is a test." + System.lineSeparator()
                + "Hello, world!" + System.lineSeparator()
                + "Test successful." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = { "-s", "1a!bc",inputFile.toString()};
        Main.main(args);

        Assertions.assertTrue(capture.stdout().isEmpty());
        Assertions.assertEquals(usageStr, capture.stderr());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    public void textilatorTest59() {
        String input = "This is a test." + System.lineSeparator()
            + "Another line of text." + System.lineSeparator()
            + "Third line of content." + System.lineSeparator()
            + "Yet another line." + System.lineSeparator();

        String expected ="This is a test." + System.lineSeparator()
        + "Another line of text." + System.lineSeparator()
        + "Third line of content." + System.lineSeparator()
        + "Yet another line." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-x","/t",inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }
    @Test
    public void textilatorTest60(){
        String input = "This is fine." + System.lineSeparator();
        String[] args = {"-c", "", "-e", "1"};
        Path inputFile = createFile(input);
        Main.main(args);

        Assertions.assertTrue(capture.stdout().isEmpty());
        Assertions.assertEquals(usageStr, capture.stderr());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    public void textilatorTest61(){
        String input = "This is fine." + System.lineSeparator();
        String[] args = {"-c", "?d", "-e", "1"};
        Path inputFile = createFile(input);
        Main.main(args);

        Assertions.assertTrue(capture.stdout().isEmpty());
        Assertions.assertEquals(usageStr, capture.stderr());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }
    @Test
    public void textilatorTest62(){
        String input = "This is fine." + System.lineSeparator();
        String[] args = {"-c", "1", "-e", "1"};
        Path inputFile = createFile(input);
        Main.main(args);

        Assertions.assertTrue(capture.stdout().isEmpty());
        Assertions.assertEquals(usageStr, capture.stderr());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }
    @Test
    public void textilatorTest63(){
        String input = "This is fine." + System.lineSeparator();
        String[] args = {"-e", "A?D"};
        Path inputFile = createFile(input);
        Main.main(args);

        Assertions.assertTrue(capture.stdout().isEmpty());
        Assertions.assertEquals(usageStr, capture.stderr());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }
    @Test
    public void textilatorTest64(){
        String input = "This is fine." + System.lineSeparator();
        String[] args = {"-e", "5.3"};
        Path inputFile = createFile(input);
        Main.main(args);

        Assertions.assertTrue(capture.stdout().isEmpty());
        Assertions.assertEquals(usageStr, capture.stderr());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }
    @Test
    public void textilatorTest65(){
        String input = "This is fine." + System.lineSeparator();
        String[] args = {"-e", ""};
        Path inputFile = createFile(input);
        Main.main(args);

        Assertions.assertTrue(capture.stdout().isEmpty());
        Assertions.assertEquals(usageStr, capture.stderr());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    public void textilatorTest66(){
        String input = "This is fine." + System.lineSeparator();
        String[] args = {"-p", ""};
        Path inputFile = createFile(input);
        Main.main(args);

        Assertions.assertTrue(capture.stdout().isEmpty());
        Assertions.assertEquals(usageStr, capture.stderr());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }
    @Test
    public void textilatorTest67(){
        String input = "This is fine." + System.lineSeparator();
        String[] args = {"-p", "?#!a"};
        Path inputFile = createFile(input);
        Main.main(args);

        Assertions.assertTrue(capture.stdout().isEmpty());
        Assertions.assertEquals(usageStr, capture.stderr());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }
    @Test
    public void textilatorTest68(){
        String input = "This is fine." + System.lineSeparator();
        String[] args = {"-e", "/t"};
        Path inputFile = createFile(input);
        Main.main(args);

        Assertions.assertTrue(capture.stdout().isEmpty());
        Assertions.assertEquals(usageStr, capture.stderr());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }
    @Test
    public void textilatorTest69(){
        String input = "This is fine." + System.lineSeparator();
        String[] args = {"-a", "/t"};
        Path inputFile = createFile(input);
        Main.main(args);

        Assertions.assertTrue(capture.stdout().isEmpty());
        Assertions.assertEquals(usageStr, capture.stderr());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    public void textilatorTest70(){
        String input = "This is fine." + System.lineSeparator();
        String[] args = {"-z", "/t"};
        Path inputFile = createFile(input);
        Main.main(args);

        Assertions.assertTrue(capture.stdout().isEmpty());
        Assertions.assertEquals(usageStr, capture.stderr());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }
    
    @Test
    public void textilatorTest71() {
        String input = "This is a test." + System.lineSeparator()
            + "Another line of text." + System.lineSeparator()
            + "Third line of content." + System.lineSeparator()
            + "Yet another line." + System.lineSeparator();
        String expected = "Another line of text." + System.lineSeparator()
            + "Yet another line." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-s", "1", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }
    @Test
    public void textilatorTest72() {
        String input = "This is a test." + System.lineSeparator()
            + "Another line of text." + System.lineSeparator()
            + "Third line of content." + System.lineSeparator()
            + "Yet another line." + System.lineSeparator();
        String expected = "/tWklv lv d whvw." + System.lineSeparator()
            + "/tDqrwkhu olqh ri whaw." + System.lineSeparator()
            + "/tWklug olqh ri frqwhqw." + System.lineSeparator()
            + "/tBhw dqrwkhu olqh." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-e", "3","-x", "Haha","-p","/t", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }
    @Test
    public void textilatorTest73() {
        String input = "This is a test." + System.lineSeparator()
            + "Another line of text." + System.lineSeparator()
            + "Third line of content." + System.lineSeparator()
            + "Yet another line." + System.lineSeparator();
        String expected = "??!Wklv lv d whvw." + System.lineSeparator()
            + "??!Dqrwkhu olqh ri whaw." + System.lineSeparator()
            + "??!Wklug olqh ri frqwhqw." + System.lineSeparator()
            + "??!Bhw dqrwkhu olqh." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-e", "3","-x", "Haha","-p","??!", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }
    @Test
    public void textilatorTest74() {
        String input = "1" + System.lineSeparator()
                + "2" + System.lineSeparator()
                + "3" + System.lineSeparator();
        String expected = "1" + System.lineSeparator()
                + "3" + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = { "-s", "0", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    public void textilatorTest75() {
        String input = "" + System.lineSeparator();

        String expected = "" + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = { "-a", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }
    @Test
    public void textilatorTest76() {
        String input = "test" + System.lineSeparator();

        String expected = "ozno" + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = { "-e","-5", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    public void textilatorTest77() {
        String input = "test." + System.lineSeparator();


        Path inputFile = createFile(input);
        String[] args = { "-e","-55", inputFile.toString()};
        Main.main(args);

        Assertions.assertTrue(capture.stdout().isEmpty());
        Assertions.assertEquals(usageStr, capture.stderr());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    public void textilatorTest78() {
        String input = "test." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = { "-e","-4","-x","2","-s","0","-a",inputFile.toString()};
        Main.main(args);

        Assertions.assertTrue(capture.stdout().isEmpty());
        Assertions.assertEquals(usageStr, capture.stderr());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    public void textilatorTest79() {
        String input = " " + System.lineSeparator();

        String expected = " " + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = { "-e","1", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    }
