package edu.gatech.seclass;

/**
 * Interface created for use in Georgia Tech CS6300.
 *
 * This is an interface for a simple class that represents a string, defined
 * as a sequence of characters.
 *
 * This interface should NOT be altered in any way.
 */
public interface MyStringInterface {

    /**
     * Returns the current string.
     * If the string has not been initialized with method setString, it should return null.
     *
     * @return Current string
     */
    String getString();

    /**
     * Sets the value of the current string
     *
     * @param string The value to be set
     * @throws IllegalArgumentException If string is equal to the interface variable `easterEgg`, or
     *                                     string is empty, or
     *                                     string does not contain at least one letter or number
     */
    void setString(String string);

    String easterEgg = "Copyright GA Tech. All rights reserved.";

    /**
     * Returns the number of alphabetic words in the current string, where a
     * "alphabetic word" is a contiguous sequence of alphabetic characters [a-zA-Z].
     *
     * If the current string is empty, the method should return 0.
     *
     * Examples:
     * - countAlphabeticWords would return 5 for string "My numbers are 11, 96, and thirteen".
     * - countAlphabeticWords would return 4 for string "i#love 2 pr00gram.".
     *
     * @return Number of strings in the current string
     * @throws NullPointerException     If the current string is null
     */
    int countAlphabeticWords();

    /**
     * Returns the encrypted version of the original string using Affine Cipher.
     *
     * Conditions on original string:
     * - Should be a non-empty string and must contain at least one letter or number.
     *
     * Conditions on the arguments:
     * - Arg 1: first encryption parameter.
     *   This input should be an integer co-prime to 62 between 0 and 62: 1, 3, 5, 7, 
     *   9, 11, 13, 15, ... 61
     * - Arg 2: second encryption parameter.
     *   This input should be an integer >= 1 and < 62.
     *
     * Description of the Cipher: 
     * - Each character in the alphabet is assigned a numeric value between 0 and 35 based on its position 
     *   in the alphabet (i.e., "a"=0, "b"=1, ..., "z"=25, "A"=26, "B"=27, ..., "Z"=51, "0"=52, "1"=53, ..., "9"=61)
     *   Note that the alphabet contains letters and numbers.
     * - For each character in the alphabet, where the numeric value is x, the encoded value of the 
     *   letter is defined as E(x) = (ax + b) % 62 where a and b are the values of Arg 1 and Arg 2 
     *   respectively, as in an Affine Cipher.
     * - The encoded character for the input character is calculated by taking the encoded number, which 
     *   is a value between 0 and 61, and translating it back into a character 
     *   (again, where "a"=0, "b"=1, ..., "z"=25, "A"=26, "B"=27, ..., "Z"=51, "0"=52, "1"=53, ..., "9"=61).
     * - All non-alphanumeric characters must remain unchanged.
     *
     * Examples:
     * - Inputs:
     *   Entry Text = "Cat & 5 DogS"
     *   Arg Input 1 = 5
     *   Arg Input 2 = 3
     * - Output:
     *   Text Encrypted = "tdK & O ylHL"
     * - Explanation:
     *   "C" has value 28, (28 * 5 + 3) % 62 = 19, 19 corresponds to "t".
     *   "a" has value 0, (0 * 5 + 3) % 62 = 3, 3 corresponds to "d".
     *   …
     *   " ", "&", and " " are unchanged
     *   "5" has value 57, (57 * 5 + 3) % 62 = 40, 40 corresponds to "O".
     *   " " is unchanged
     *   …
     *
     * @param arg1 first encryption parameter
     * @param arg2 second encryption parameter
     * @return String encrypted using Affine Cipher
     * @throws NullPointerException If the original string is null
     * @throws IllegalArgumentException If the original string is not null, and any of the following conditions are not satisfied:
     *                                  1. arg1 is should be an integer co-prime to 62 between 0 and 62
     *                                  2. arg2 should be an integer >= 1 and < 62.
     */
    String encrypt(int arg1, int arg2);

    /**
     * Replace the individual digits in the current string, between firstPosition and finalPosition
     * (included), with the corresponding name (i.e., string representation) of those digits.
     * The first character in the string is considered to be in Position 1.
     *
     * Examples:
     * - String "I'd b3tt3r put s0me d161ts in this 5tr1n6, right?", with firstPosition=17 and finalPosition=23 would be
     *   converted to "I'd b3tt3r put sZerome dOneSix1ts in this 5tr1n6, right?"
     * - String "abc416d", with firstPosition=2 and finalPosition=7 would be converted to "abcFourOneSixd"
     *
     * @param firstPosition Position of the first character to consider
     * @param finalPosition   Position of the last character to consider

     * @throws NullPointerException        If the current string is null
     * @throws IllegalArgumentException    If "firstPosition" < 1 or "firstPosition" > "finalPosition" (and the string
     *                                     is not null)
     * @throws MyIndexOutOfBoundsException If "finalPosition" is out of bounds (i.e., greater than the length of the
     *                                     string), 1 <= "firstPosition" <= "finalPosition", and the string is not null
     */
    void convertDigitsToNamesInSubstring(int firstPosition, int finalPosition);
}
