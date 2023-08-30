package edu.gatech.seclass.textilator;

/**
  * Signals that an error occurred when running Textilator.
  */

public class TextilatorException extends Exception {
    /**
      * Constructs a TextilatorException with the specified message describing the error.
      *
      * @param s the error message
      */
    TextilatorException(String s) {
        super(s);
    }
}
