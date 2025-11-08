package ca.bcit.httphacks.epub;

import java.util.ArrayList;
import java.util.List;

public class Canvas
{
    private static final int MIN_LINES = 1;
    private static final int MAX_LINES = 8;

    private final int contentLines;
    private final List<String> content;

    /**
     * Canvas constructor.
     *
     * @param contentLines The number of lines set for the text content array.
     */
    public Canvas(final int contentLines)
    {
        validateContentLines(contentLines);

        this.contentLines = contentLines;
        this.content      = new ArrayList<>();

        reset();
    }

    /**
     * Validates the number of lines set for the text content array.
     *
     * @param contentLines Must be between {@value MIN_LINES} and {@value MAX_LINES}.
     */
    private static void validateContentLines(final int contentLines)
    {
        if (contentLines < MIN_LINES || contentLines > MAX_LINES)
        {
            throw new IllegalArgumentException(
                "The number of set lines must be between " + MIN_LINES + " and " + MAX_LINES);
        }
    }

    /**
     * Resets the canvas by clearing the text content array with nulls.
     */
    public void reset()
    {
        this.content.clear();

        while (this.content.size() < this.contentLines)
        {
            this.content.add(null);
        }
    }

    /**
     * Writes a line of text to the canvas. The {@code leftSpaceCount} is included
     * for indenting.
     *
     * @param text           The source text.
     * @param lineIndex      The index to write to.
     * @param leftSpaceCount The number of spaces to be added to the left side of
     *                       the source text.
     */
    public void writeLine(final String text,
                          final int lineIndex,
                          final int leftSpaceCount)
    {
        if (lineIndex >= this.contentLines)
        {
            return;
        }
        this.content.set(lineIndex, " ".repeat(leftSpaceCount) + text);
    }

    /**
     * Writes a line of text to the canvas.
     *
     * @param text      The source text.
     * @param lineIndex The index to write to.
     */
    public void writeLine(final String text,
                          final int lineIndex)
    {
        if (lineIndex >= this.contentLines)
        {
            return;
        }
        this.content.set(lineIndex, text);
    }

    /**
     * Writes a line of text to the canvas. The line will be written to the first
     * occurring null in the text content array.
     *
     * @param text The source text.
     */
    public void writeLine(final String text)
    {
        final int indexOfFirstNull = this.content.indexOf(null);

        this.content.set(indexOfFirstNull, text);
    }

    /**
     * Gets the number of lines set for the text content array.
     *
     * @return The number of lines.
     */
    public int getContentLines()
    {
        return contentLines;
    }

    /**
     * Gets the text content array.
     *
     * @return The text content array.
     */
    public List<String> getContent()
    {
        return content;
    }
}
