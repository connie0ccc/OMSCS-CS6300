package edu.gatech.seclass;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Junit test class created for use in Georgia Tech CS6300.
 * <p>
 * This class is provided to interpret your grades via junit tests
 * and as a reminder, should NOT be posted in any public repositories,
 * even after the class has ended.
 */

public class MyStringTest {

    private MyStringInterface mystring;

    @BeforeEach
    public void setUp() {
        mystring = new MyString();
    }

    @BeforeEach
    public void tearDown() {
        mystring = null;
    }

    @Test
    @Timeout(value = 5000, unit = TimeUnit.MILLISECONDS)
    // Description: First count number example in the interface documentation
    public void testCountAlphabeticWords1() {
        mystring.setString("My numbers are 11, 96, and thirteen");
        assertEquals(5, mystring.countAlphabeticWords());
    }

    @Test
    @Timeout(value = 5000, unit = TimeUnit.MILLISECONDS)
    // Description: Second count number example in the interface documentation
    public void testCountAlphabeticWords2() {
        mystring.setString("i#love 2 pr00gram.");
        assertEquals(4, mystring.countAlphabeticWords());
    }


    @Test
    @Timeout(value = 5000, unit = TimeUnit.MILLISECONDS)
    // Description: <Test of one word>
    public void testCountAlphabeticWords3() {
        mystring.setString("WOW!");
        assertEquals(1, mystring.countAlphabeticWords());
    }
    @Test
    @Timeout(value = 5000, unit = TimeUnit.MILLISECONDS)
    // Description:Start with a number and end with a number, they do not affect counting of alphabetic words.
    public void testCountAlphabeticWords4() {
        mystring.setString(" 10 samples were tested on day 5");
        assertEquals(5, mystring.countAlphabeticWords());
    }
    @Test
    @Timeout(value = 5000, unit = TimeUnit.MILLISECONDS)
    // Description: If the string set is null,  throw a NullPointerException.
    public void testSetString1() {
        assertThrows(NullPointerException.class,
                ()-> {
                    mystring.setString(null);
                });
    }

    @Test
    @Timeout(value = 5000, unit = TimeUnit.MILLISECONDS)
    // Description: First example provided in the interface documentation
    public void testEncrypt1() {
        mystring.setString("Cat & 5 DogS");
        assertEquals("tdK & O ylHL", mystring.encrypt(5, 3));
    }

    @Test
    @Timeout(value = 5000, unit = TimeUnit.MILLISECONDS)
    // Description: Description: If the string set is null,  throw a NullPointerException.
    public void testEncrypt2() {
        assertThrows(NullPointerException.class,
                ()-> {
                    mystring.setString(null);
                    mystring.encrypt(5, 3);
                });
    }

    @Test
    @Timeout(value = 5000, unit = TimeUnit.MILLISECONDS)
    // Description: Throws IllegalArgumentException as arg2 is co-prime to 62
    public void testEncrypt3() {
        assertThrows(IllegalArgumentException.class,
                ()-> {
                    mystring.setString("What A Wonderful World.");
                    mystring.encrypt(14, 44);
                });
    }

    @Test
    @Timeout(value = 5000, unit = TimeUnit.MILLISECONDS)
    // Description: Throws IllegalArgumentException because arg1 is outs of range.
    public void testEncrypt4() {
        assertThrows(IllegalArgumentException.class,
                ()-> {
                    mystring.setString("What A Wonderful World.");
                    mystring.encrypt(5, 100);
                });
    }

    @Test
    @Timeout(value = 5000, unit = TimeUnit.MILLISECONDS)
    // Description: Special characters are retained
    public void testEncrypt5() {
        mystring.setString("What$ A $Wonderful $World$");
        assertEquals("HIni$ D $H30wzcClU $H3cUw$", mystring.encrypt(3, 13));
    }

    @Test
    @Timeout(value = 5000, unit = TimeUnit.MILLISECONDS)
    // Description: Upper and lower cases converted differently
    public void testEncrypt6() {
        mystring.setString("HAHA haha");
        assertEquals("YDYD InIn", mystring.encrypt(3, 13));
    }

    @Test
    @Timeout(value = 5000, unit = TimeUnit.MILLISECONDS)
    // Description: First convert digits example in the interface documentation
    public void testConvertDigitsToNamesInSubstring1() {
        mystring.setString("I'd b3tt3r put s0me d161ts in this 5tr1n6, right?");
        mystring.convertDigitsToNamesInSubstring(17, 23);
        assertEquals("I'd b3tt3r put sZerome dOneSix1ts in this 5tr1n6, right?", mystring.getString());
    }

    @Test
    @Timeout(value = 5000, unit = TimeUnit.MILLISECONDS)
    // Description: If final position is greater the length, throw a MyIndexOutOfBoundsException.
    public void testConvertDigitsToNamesInSubstring2() {
        assertThrows(MyIndexOutOfBoundsException.class,
                ()-> {
                    mystring.setString("What A Wonderful World");
                    mystring.convertDigitsToNamesInSubstring(2, 40);
                });
    }

    @Test
    @Timeout(value = 5000, unit = TimeUnit.MILLISECONDS)
    // DescriptionIf "firstPosition" > "finalPosition", throw an IllegalArgumentException.
    public void testConvertDigitsToNamesInSubstring3() {
        assertThrows(IllegalArgumentException.class,
                ()-> {
                    mystring.setString("What A Wonderful World");
                    mystring.convertDigitsToNamesInSubstring(7, 3);
                });
    }

    @Test
    @Timeout(value = 5000, unit = TimeUnit.MILLISECONDS)
    // Description: If "firstposition" is a negative number, throw an IllegalArgumentException.
    public void testConvertDigitsToNamesInSubstring4() {
        assertThrows(IllegalArgumentException.class,
                ()-> {
                    mystring.setString("What A Wonderful World");
                    mystring.convertDigitsToNamesInSubstring(-10, 7);
                });
    }

    @Test
    @Timeout(value = 5000, unit = TimeUnit.MILLISECONDS)
    // Description: Conversion of multiple digits in a range selected.
    public void testConvertDigitsToNamesInSubstring5() {
        mystring.setString("A W0nderfu1 World");
        mystring.convertDigitsToNamesInSubstring(3, 12);
        assertEquals("A WZeronderfuOne World", mystring.getString());
    }
}


