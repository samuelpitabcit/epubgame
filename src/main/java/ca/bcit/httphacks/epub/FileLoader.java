package ca.bcit.httphacks.epub;

import java.nio.file.*;
import java.io.IOException;
import java.util.List;

/**
 * Handles reading text files into memory.
 * This class provides a simple method to load all lines from a file path
 * into a list of strings.
 */
public class FileLoader {

    /**
     * Reads all lines from a given text file.
     *
     * @param filePath the relative or absolute path of the file to read
     * @return a list of strings, each representing one line of the file;
     *         returns an empty list if the file cannot be read
     */
    public List<String> loadText(String filePath) {
        try {
            // Read every line from the specified file path and return them as a list
            return Files.readAllLines(Paths.get(filePath));
        } catch (IOException e) {
            // Print error details and return an empty list if the file cannot be accessed
            e.printStackTrace();
            return List.of();
        }
    }
}
