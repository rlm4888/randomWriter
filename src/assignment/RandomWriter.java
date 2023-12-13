package assignment; 
import java.io.*;
import java.util.ArrayList;
/*
 * CS 314H Assignment 2 - Random Writing
 *
 * Your task is to implement this RandomWriter class
 */

public class RandomWriter implements TextProcessor {
    private String sourceText;
    private int level;
    private String initialSeed;
    private String nextSeed;
    private char nextCharacter;


    public static void main(String[] args) {
        String input = args[0];
        String output = args[1];
        int k = Integer.parseInt(args[2]);
        int length = Integer.parseInt(args[3]);

        //checks if level is negative, prints an error, and exits if it is
        if (k < 0) {
          System.err.println("Level must be non-negative.");
          return;
        }
        

        //checks if length is negative, prints an error, and exits if it is
        //how many seeds are left that haven't been appended yet
        if(length < 0){
          System.err.println("Length must be non-negative.");
          return;
        }

        if (input.length() <= k) {
            System.err.println("Source text does not contain more than k characters.");
            return;
        }
  
      //creates a RandomWriter object named writer with the specified level
      RandomWriter writer = new RandomWriter(k);
        try {
            ///reads text from input file
            writer.readText(input);

            //writes random text of specified length to output file
            writer.writeText(output, length);
        
        //checks that source can be opened for reading and that result can be opened for writing and prints error message if an exception occurs
        } catch (IOException x) {
            System.err.println("Source text cannot be read or output file cannot be written to.");
            return;
        }
    }


 // Unless you need extra logic here, you might not have to touch this method
    public static TextProcessor createProcessor(int level) {
        return new RandomWriter(level);
    }
//change back to private
    public RandomWriter(int level) {
      // Do whatever you want here

      //initializes level
      this.level = level;
      this.initialSeed = "";
    }

    public void readText(String inputFilename) throws IOException {
        if (level < 0) {
          System.err.println("Level must be non-negative.");
          return;
        }
        
        //creates a StringBuilder object named builder to store contents of input text
        StringBuilder builder = new StringBuilder();

        //opens input file for reading and creates a BufferedReader object reader to read from it
        //found BufferedReader and FileReader documentation from Stack Overflow
        //test inputLength, test index chosen, print out seed, entire arraylist, seed chosen, each iteration of reader, print source, sourcelenght, first random int, initial seed, count for each char
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilename))) {
            String line;       
            //reads lines from input file until the end, where null is returned
            while ((line = reader.readLine()) != null) {

                //appends current line to builder
                builder.append(line);
            }
        }
        //converts builder content to one string and stores it in sourceText
        sourceText = builder.toString();
        if (sourceText.length() >= level) {
            initialSeed = sourceText.substring(0, level);
        } else {
            System.err.println("Source text does not contain more than k characters.");
            initialSeed = "";
        }
    }

    public void writeText(String outputFilename, int length) throws IOException {
          if(length < 0){
          System.err.println("Length must be non-negative.");
          return;
        }
        nextSeed = "";
        nextCharacter = ' ';
        nextSeed = initialSeed;

        //initializes a FileWriter object named writer
        //found FileWriter and try block documentation from Stack Overflow
        //try block automatically closes the 'FileWriter' when the block is exited, so 'writer.close()' doesn't need to be explicitly called
        try (FileWriter writer = new FileWriter(outputFilename)) {
            int sourceLength = sourceText.length();
        //base case: when level is 0
        if (level == 0) {
            for (int i = 0; i < length; i++) {
                //generates a random index within the source text length
                int randomIndex = (int) (Math.random() * sourceLength);
                //gets the character at the random index and stores it in nextChar
                char nextChar = sourceText.charAt(randomIndex);
                //writes it to output file
                writer.write(nextChar);
               
            }

        } else {
            int initialIndex = (int) (Math.random() * (sourceLength - level));
            
            //creates a seed string that is used to generate the next characters in output
            String seed = sourceText.substring(initialIndex, initialIndex + level);
            //writes seed string to output file
            writer.write(seed);

            for (int i = 0; i < length - level; i++) {
                ArrayList<Character> nextLets = new ArrayList<>();
                for (int j = 0; j < sourceLength - level; j++) {
                    //finds characters in sourceText that follow the seed and adds them to nextLets
                    if (sourceText.substring(j, j + level).equals(seed)) {
                        nextLets.add(sourceText.charAt(j + level));
                    }
                }
                if (nextLets.isEmpty()) {
                    initialIndex = (int) (Math.random() * (sourceLength - level));
                    seed = sourceText.substring(initialIndex, initialIndex + level);
                } else {
                    //selects a random character from nextLets and writes it to output file
                    char nextChar = nextLets.get((int) (Math.random() * nextLets.size()));
                    writer.write(nextChar);
                
                    //updates seed for next iteration
                    seed = seed.substring(1) + nextChar;

                }
            }
        }
    }
     
}
//getter methods for Tester

    public String getSourceText() {
        return sourceText;
    }

    public String getInitialSeed() {
        return initialSeed;
    }

    public String getNextSeed() {
        return nextSeed;
    }

    public char getNextCharacter() {
        return nextCharacter;
    }
}

