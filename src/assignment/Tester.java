package assignment;
import java.io.*;

public class Tester{
    public static void main(String[] args) {
        try (FileWriter outputFile = new FileWriter("test_books/whiteboxtest.txt")) {
            PrintWriter writer = new PrintWriter(outputFile);
            
            // Test readText method with different levels and input files
            testReadText("test_books/PrideandPrejudice.txt", 0, writer);
            testReadText("test_books/PrideandPrejudice.txt", 3, writer);
            
            // Test writeText method with different levels, lengths, and output files
            testWriteText("test_books/whiteboxtest.txt", 0, 100, writer);
            testWriteText("test_books/whiteboxtest.txt", 2, 150, writer);
            
            // Test edge cases
            testEdgeCases(writer);
            
            // Close the PrintWriter and FileWriter
            writer.close();
            outputFile.close();
        } catch (IOException e) {
            System.err.print(e);
        }
    }

    private static void testReadText(String inputFile, int level, PrintWriter writer) {
        try {
            RandomWriter rw = new RandomWriter(level);
            rw.readText(inputFile);
            
            writer.println("Testing readText method with level " + level);
            writer.println("Input Length: " + inputFile.length());
            
            // Check if getSourceText() returns null and handle it
            String sourceText = rw.getSourceText();
            if (sourceText != null) {
                writer.println("Source Length: " + sourceText.length());
                writer.println("Initial Seed: " + rw.getInitialSeed());
            } else {
                writer.println("Source Text is null.");
            }
            
            writer.println();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static void testWriteText(String outputFile, int level, int length, PrintWriter writer) {
        try {
            RandomWriter rw = new RandomWriter(level);
            rw.readText("test_books/PrideandPrejudice.txt"); // Replace with an appropriate input file
            rw.writeText(outputFile, length); // Use the provided outputFile parameter
            writer.println("Testing writeText method with level " + level + ", length " + length);
            writer.println("Initial Seed: " + rw.getInitialSeed());
            writer.println("Next Seed: " + rw.getNextSeed());
            writer.println("Next Character: " + rw.getNextCharacter());
            writer.println();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static void testEdgeCases(PrintWriter writer) {
        writer.println("Testing edge cases:");
        
        // Test negative level
        testReadText("test_books/PrideandPrejudice.txt", -1, writer);
        
        // Test negative length
        testWriteText("test_books/whiteboxtest.txt", 2, -5, writer);
    }
}



