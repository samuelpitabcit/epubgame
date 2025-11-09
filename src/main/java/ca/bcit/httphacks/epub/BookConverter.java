package ca.bcit.httphacks.epub;

import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.sax.BodyContentHandler;

import java.io.*;

/**
 * Converts an EPUB file into a plain text (.txt) file.
 */
public class BookConverter {

    public static void convertToTxt(String epubPath, String txtPath) throws IOException {
        try (FileInputStream fis = new FileInputStream(epubPath);
             FileWriter writer = new FileWriter(txtPath)) {

            BodyContentHandler handler = new BodyContentHandler(-1);
            AutoDetectParser parser = new AutoDetectParser();
            parser.parse(fis, handler, new Metadata(), new ParseContext());

            // Clean up excessive whitespace
            String cleanText = handler.toString()
                .replaceAll("\\s+", " ")
                .trim();

            writer.write(cleanText);
            System.out.println("EPUB converted to TXT successfully  " + txtPath);

        } catch (Exception e) {
            throw new IOException("Conversion failed: " + e.getMessage());
        }
    }
}
