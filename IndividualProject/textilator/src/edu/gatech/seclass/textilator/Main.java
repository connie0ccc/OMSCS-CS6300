package edu.gatech.seclass.textilator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;


public class Main {
    // Empty Main class for compiling Individual Project
    // During Deliverable 1 and Deliverable 2, DO NOT ALTER THIS CLASS or implement it
    public static void main(String[] args) {
        try {
            // Create a TextilatorProcessor
            TextilatorProcessor processor = new TextilatorProcessor(args);
            // Process the input file 
            processor.processFile();
        } catch (Exception e) {
            usage();
        }
    }
    private static void usage() {
        System.err.println("Usage: textilator [ -s number | -x substring | -c case | -e num | -a | -p prefix ] FILE");
        
    }
    static class TextilatorProcessor {
        private String[] args;
        private String inputFilename;
        private List<String> lines;
    
        public TextilatorProcessor(String[] args) {
            this.args = args;
            // The input file name is the last argument
            this.inputFilename = args[args.length - 1];
            // Initialize the list to store lines from the input file
            this.lines = new ArrayList<>();
        }
    
        public void processFile() throws IOException {
            // Check if the input file exists
            File inputFile = new File(inputFilename);
            if (!inputFile.exists()) {
                throw new FileNotFoundException("Input file does not exist: " + inputFilename);
            }
            
            // Read the input file line by line and store it in the list
            try (BufferedReader br = new BufferedReader(new FileReader(inputFilename))) {
                String line;
                while ((line = br.readLine()) != null) {
                    lines.add(line);
                }
            }
    
            // Create a TextilatorOptions object with the command-line arguments
            TextilatorOptions options = new TextilatorOptions(args);
            // Apply the options to the lines
            options.applyOptions(lines);
    
            // Print the transformed lines to standard output
            for (String line : lines) {
                System.out.println(line);
            }
        }
    }
    
    static class TextilatorOptions {
        private String[] args;
        // Define variables to store option values
        private Integer skipLineString = null;
        private String excludeSubstring = null;
        private String letterCase = null;
        private Integer shiftAmount = null;
        private boolean a_flag = false;
        private String prefix = null;
        private boolean x_flag = false;
        private boolean emptyInput = false;
        private boolean invalidInput = false;
        public TextilatorOptions(String[] args) {
            this.args = args;
            processArgs();
        }
    
        private void processArgs() {
            //create list of all valid option
            List<String> validOptions = Arrays.asList("-s", "-x", "-c", "-e", "-a", "-p");
            // check for empty arguement
            if (args.length ==0){
                emptyInput = true;
                return;
            }

            // Iterate through the arguments and assign them to the corresponding variables
            for (int i = 0; i < args.length - 1; i++) {            
                if (args[i+1].equals(" ")) {
                emptyInput = true;
                break;
            }
                else if (!args[i].equals("-a") && i + 1 < args.length && validOptions.contains(args[i + 1])) {
                emptyInput = true;
                break;}
                else if (!args[i].equals("-a") && i + 2 == args.length) {
                    emptyInput = true;
                    break;
          } else switch (args[i]) {
                    case "-s":
                        // Skip lines option                           
                        skipLineString = Integer.parseInt(args[++i]);
                        if (skipLineString !=0 && skipLineString!=1){
                            invalidInput=true;
                        }                        
                        break;
                    case "-x":
                        // Exclude substring option
                        x_flag=true;
                        excludeSubstring = args[++i];
                        break;
                    case "-c":
                        // Letter case option
                        letterCase = args[++i].toLowerCase();
                        if (letterCase== ""){                
                            emptyInput = true;
                            break;}
                        else if (!"upper".equals(letterCase) && !"lower".equals(letterCase)) {
                            invalidInput=true;
                        }
                        break;
                    case "-e":
                        // Shift amount option
                        shiftAmount = Integer.parseInt(args[++i]);
                        break;
                    case "-a":
                        // Set the encode ASCII flag
                        a_flag = true;
                        break;
                    case "-p":
                        // Prefix option
                        prefix = args[++i];
                        if (prefix== ""){                
                            emptyInput = true;
                            break;}
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid option: " + args[i]);
                }
            }
        }
        
        public void applyOptions(List<String> lines) {
        // Return error if options can not co-exist
        if (Arrays.asList(args).containsAll(Arrays.asList("-x", "-s")) ||
        Arrays.asList(args).containsAll(Arrays.asList("-x", "-a", "-e")) ||
        Arrays.asList(args).containsAll(Arrays.asList("-s", "-a", "-x", "-e"))) {
        lines.clear();
        usage();
        return;
        } 
        // Return error if options has empty or invalid string
        if (emptyInput||invalidInput) {
            lines.clear();
            usage();
            return;
        }

        // Apply each option in the specified order
        // 1. Filter lines using -s or -x
        if (skipLineString != null || x_flag) {
            filterLines(lines);
        }
        // 2. Modify letter case using -c
        if (letterCase != null) {
            applyCase(lines);
        }
        // 3. Encode characters using -e or -a
        if (shiftAmount != null || a_flag) {
            encodeCharacters(lines);
        }
        // 4. Add prefix using -p
        if (prefix != null) {
            applyPrefix(lines);
        }
    }
    public boolean isEmptyInput() {
        return emptyInput;
    }

    // Filter lines based on -s and -x
    private void filterLines(List<String> lines) {
       
        List<String> filteredLines = new ArrayList<>();
        for (int i = 0; i < lines.size(); i++) {
            // Skip lines (0: even lines, 1: odd lines)
            if (skipLineString != null) {
                if (skipLineString == 1 && i % 2 == 0) {
                    continue;
                } else if (skipLineString == 0 && i % 2 == 1) {
                    continue;
                }
            }
            // Exclude lines containing excludeSubstring

            if (excludeSubstring != null && lines.get(i).contains(excludeSubstring)) {
                continue;
            }
            // Add the remaining lines to the filteredLines list
            filteredLines.add(lines.get(i));
        }
        // Replace the original lines list with the filtered lines
        lines.clear();
        lines.addAll(filteredLines);
    }

    // Modify letter case using -c
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

    // Encode characters using -e or -a
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
                } else if (a_flag) {
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

    // Add the prefix to each line
    private void applyPrefix(List<String> lines) {
        for (int i = 0; i < lines.size(); i++) {
            lines.set(i, prefix + lines.get(i));
        }
    }
}
}
