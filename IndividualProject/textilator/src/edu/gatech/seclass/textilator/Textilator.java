package edu.gatech.seclass.textilator;

import java.io.BufferedReader;

import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;


public class Textilator implements TextilatorInterface {
    private String filepath;
    private LineParity lineToSkip;
    private String excludeString;
    private String letterCase;
    private Integer shiftAmount;
    private boolean encodeLines;
    private String prefix;
    private boolean emptyInput = false;
    private boolean invalidInput = false;


    public void reset() {
        // Reset all the options to their default values.
        filepath = null;
        lineToSkip = null;
        excludeString = null;
        letterCase = null;
        shiftAmount = null;
        encodeLines = false; 
        prefix = null;
    }

    @Override
    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    @Override
    public void setLineToSkip(LineParity lineToSkip) {
        this.lineToSkip = lineToSkip;}

    @Override
    public void setExcludeString(String excludeString) {
        this.excludeString = excludeString;
        if (excludeString == null || excludeString.trim().isEmpty()) {
            emptyInput = true;
        }
    }

    @Override
    public void setLetterCase(TextilatorInterface.Case letterCase) {
        if (letterCase == TextilatorInterface.Case.upper) {
            this.letterCase = "upper";
        } else if (letterCase == TextilatorInterface.Case.lower) {
            this.letterCase = "lower";
        }
    }
    @Override
    public void setEncodeLines(boolean encodeLines) {
        this.encodeLines = encodeLines;
    }
    @Override
    public void setCipherText(int shiftAmount) {
        this.shiftAmount = shiftAmount;
        this.shiftAmount = shiftAmount;
        if (shiftAmount < -25 || shiftAmount > 25) {
            invalidInput = true;
        }
    }

    @Override
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public void textilator() {
        try {
            if (filepath == null) {
                throw new TextilatorException("File path not set");
            }

            List<String> lines = readFile(filepath);

            if (lines.isEmpty()) {
                throw new TextilatorException("File empty");
            }

            
            if (lineToSkip != null || excludeString != null) {
                filterLines(lines);
            }

            if (letterCase != null) {
                applyCase(lines);
            }

            if (shiftAmount != null || encodeLines) {
                encodeCharacters(lines);
            }

            if (prefix != null) {
                applyPrefix(lines);
            }

            printLines(lines);
        } catch (TextilatorException e) {
            System.err.println(e.getMessage());
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
    public void applyOptions(List<String> lines) throws TextilatorException {
    // Check if options can co-exist
    boolean hasX = excludeString != null;
    boolean hasS = lineToSkip != null;
    boolean hasA = shiftAmount != null;
    boolean hasE = encodeLines;
    if ((hasX && hasS) || (hasX && hasA && hasE) || (hasS && hasA && hasX && hasE)) {
        lines.clear();
        throw new TextilatorException("Invalid combination of options. See usage for valid options.");
    } 
    // Check if options have empty or invalid string
    if (emptyInput || invalidInput) {
        lines.clear();
        throw new TextilatorException("Invalid input value(s). See usage for valid options.");
    }
}
    private List<String> readFile(String filepath) {
        List<String> lines = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }

        return lines;
    }

    private void filterLines(List<String> lines) {
        List<String> filteredLines = new ArrayList<>();
        for (int i = 0; i < lines.size(); i++) {
            if (lineToSkip != null) {
                if (lineToSkip == LineParity.odd && i % 2 == 0) {
                    continue;
                } else if (lineToSkip == LineParity.even && i % 2 == 1) {
                    continue;
                }
            }

            if (excludeString != null && lines.get(i).contains(excludeString)) {
                continue;
            }

            filteredLines.add(lines.get(i));
        }

        lines.clear();
        lines.addAll(filteredLines);
    }

    private void applyCase(List<String> lines) {
        for (int i = 0; i < lines.size(); i++) {
            StringBuilder sb = new StringBuilder();
            for (char ch : lines.get(i).toCharArray()) {
                if ("upper".equals(letterCase) && ((ch >= 65 && ch <= 90) || (ch >= 97 && ch <= 122))) {
                    // Convert the character to uppercase if it is within the English alphabet
                    sb.append(Character.toUpperCase(ch));
                } else if ("lower".equals(letterCase) && ((ch >= 65 && ch <= 90) || (ch >= 97 && ch <= 122))) {
                    // Convert the character to lowercase if it is within the English alphabet
                    sb.append(Character.toLowerCase(ch));
                } else {
                    // Keep characters outside the English alphabet unchanged
                    sb.append(ch);
                }
            }
            lines.set(i, sb.toString());
        }
    }

    private void encodeCharacters(List<String> lines) {
        for (int i = 0; i < lines.size(); i++) {
            StringBuilder sb = new StringBuilder();
            for (char ch : lines.get(i).toCharArray()) {
                if (shiftAmount != null) {
                    // Return error if out of range
                    if (shiftAmount > 25 || shiftAmount < -25) {
                        lines.clear();
                        break;}
                    // Apply Caesar cipher encoding
                    else {sb.append(encodeCaesar(ch, shiftAmount));}
                } else if (encodeLines) {
                    // Convert the character to its ASCII value
                    if (ch >= 32 && ch <= 126) {
                        sb.append((int) ch).append(" ");
                    } else {
                        sb.append(ch);
                    }
                }
            }
            // Replace the original line with the encoded line
            lines.set(i, sb.toString());
        }
    }
    private char encodeCaesar(char ch, int shift) {
        if (Character.isUpperCase(ch) && ch >= 65 && ch <= 90) {
            // Encode uppercase characters
            return (char) (((ch - 'A' + shift + 26) % 26) + 'A');
        } else if (Character.isLowerCase(ch) && ch >= 97 && ch <= 122) {
            // Encode lowercase characters
            return (char) (((ch - 'a' + shift + 26) % 26) + 'a');
        } else {
            // Keep characters outside the English alphabet unchanged
            return ch;
        }
    }


    private void applyPrefix(List<String> lines) {
        for (int i = 0; i < lines.size(); i++) {
            lines.set(i, prefix + lines.get(i));
        }
    }

    private void printLines(List<String> lines) {
        for (String line : lines) {
            System.out.println(line);
        }
    }
}

