package ca.bcit.httphacks.epub;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;

/**
 * Reads words from a TXT file or converts EPUB if needed.
 */
public class BookReader {

    /**
     * Reads all words from the book (EPUB or TXT).
     * Automatically converts if TXT file does not exist.
     */
    public static List<String> loadWords(String epubPath) throws IOException {
        Path txtPath = Path.of(epubPath.replaceAll("(?i)\\.epub$", ".txt"));

        // Convert EPUB to TXT if missing
        if (!Files.exists(txtPath)) {
            System.out.println("TXT not found — converting EPUB...");
            BookConverter.convertToTxt(epubPath, txtPath.toString());
        }

        // Read text file
        String text = Files.readString(txtPath)
            .replaceAll("\\s+", " ")
            .trim();

        if (text.isEmpty()) return Collections.emptyList();
        return Arrays.asList(text.split(" "));
    }

    /**
     * Returns a small section of the book for preview.
     */
    public static List<String> getSlice(List<String> words, int start, int count) {
        if (words.isEmpty()) return Collections.emptyList();
        int end = Math.min(start + count, words.size());
        if (start < 0 || start >= words.size()) {
            throw new IllegalArgumentException("Start index out of range.");
        }
        return words.subList(start, end);
    }
}
