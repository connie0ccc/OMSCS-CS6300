package edu.gatech.seclass.textilator;

/**
  * Interface created for use in Georgia Tech CS6300.
  *
  * IMPORTANT: This interface should NOT be altered in any way.
  */

public interface TextilatorInterface {
    /**
      * Parameter for letterCase.
      */
    enum Case {
        upper,
        lower
    }

    /**
      * Parameter for lineToSkip.
      */
    enum LineParity {
        even,
        odd
    }

    /**
      * Reset the Textilator object to its initial state, for reuse.
      */
    void reset();

    /**
      * Sets the path of the input file. This method has to be called
      * before invoking the {@link #textilator()} methods.
      *
      * @param filepath The file path to be set.
      */
    void setFilepath(String filepath);

    /**
      * Set to apply the skipping of lines based upon the supplied
      * parameter, lineToSkip. 0 is considered even and 1 is odd.
      * All files start with line 1.
      * This method has to be called before invoking the
      * {@link #textilator()} method.
      *
      * @param lineToSkip Determine which lines to skip.
      */
    void setLineToSkip(LineParity lineToSkip);

    /**
      * Set to exclude all lines containing the substring excludeString.
      * This method has to be called before invoking the
      * {@link #textilator()} method.
      *
      * @param excludeString The string to be excluded.
      */
    void setExcludeString(String excludeString);

    /**
      * Converts characters in the English alphabet to the case specified
      * by letterCase. This method has to be called before invoking the
      * {@link #textilator()} method.
      *
      * @param letterCase Determine which case to convert character to.
      */
    void setLetterCase(Case letterCase);

    /**
      * Shifts characters in the English alphabet by the amount provided
      * by cipherText, wrapping from 'z' to 'a' or, similarly, 'Z' to 'A'.
      * This method has to be called before invoking the
      * {@link #textilator()} method.
      *
      * @param shiftAmount Amount to shift character by.
      */
    void setCipherText(int shiftAmount);

    /**
      * Set to convert all ASCII printable characters (ASCII codes
      * 32-126, inclusive) to the corresponding ASCII code (for
      * example, a = 97) followed by a single space.
      * This method has to be called before invoking the
      * {@link #textilator()} method.
      *
      * @param encodeLines Flag to toggle functionality.
      */
    void setEncodeLines(boolean encodeLines);

    /**
      * Adds prefix at the start of each line.
      * This method has to be called before invoking the
      * {@link #textilator()} method.
      *
      * @param prefix The prefix to be set.
      */
    void setPrefix(String prefix);

    /**
      * Outputs a System.lineSeparator() delimited string that contains
      * selected parts of the lines in the file specified using {@link #setFilepath}
      * and according to the current configuration, which is set
      * through calls to the other methods in the interface.
      *
      * It throws a {@link TextilatorException} if an error condition
      * occurs (e.g., when the specified file does not exist).
      *
      * @throws TextilatorException thrown if an error condition occurs
      */
    void textilator() throws TextilatorException;
}
