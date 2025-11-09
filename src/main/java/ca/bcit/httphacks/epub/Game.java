package ca.bcit.httphacks.epub;

/**
 * Game class.
 *
 * @author Samuel Pita
 * @author Rodrigo Sanchez
 * @author Jaspreet Bath
 * @version 1.0
 */
public class Game
{
    private final String textContent;
    private final StringBuilder input;

    public Game(final String textContent)
    {
        this.textContent = textContent;
        this.input       = new StringBuilder();
    }

    /**
     * Validates the Game's text content.
     *
     * @param textContent Cannot be null or blank.
     */
    private static void validateTextContent(final String textContent)
    {
        if (textContent == null)
        {
            throw new IllegalArgumentException("Text content must not be set to null");
        }
        if (textContent.isBlank())
        {
            throw new IllegalArgumentException("Text content must not be blank");
        }
    }

    public void inputDeleteLastChar()
    {
        if (this.input.isEmpty())
        {
            return;
        }
        this.input.deleteCharAt(this.input.length() - 1);
    }

    public void inputDeleteLastWord()
    {
        if (this.input.isEmpty())
        {
            return;
        }

        final String temp =
            this.input.toString().replaceAll("\\S+\\s*$", "");

        this.input.setLength(0);
        this.input.append(temp);
    }

    public void inputAdd(final char c)
    {
        this.input.append(c);
    }

    public void writeToCanvasDebug(final Canvas canvas) {
        canvas.reset();
        // Display only the current page content
        canvas.writeLine(this.textContent);
        canvas.writeLine("Input: " + this.input.toString());
        canvas.writeLine("Typed characters: " + this.input.length());
    }
}
