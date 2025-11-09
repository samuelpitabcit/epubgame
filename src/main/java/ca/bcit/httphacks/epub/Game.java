package ca.bcit.httphacks.epub;

import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

/**
 * Game class.
 *
 * @author Samuel Pita
 * @author Rodrigo Sanchez
 * @author Jaspreet Bath
 * @version 1.6
 */
public class Game implements Scene
{
    private final String textContent;
    private final StringBuilder input;
    private final GameInputManager gameInputManager;
    private final Main main;

    /**
     * Game constructor.
     *
     * @param textContent The text content to display for typing.
     * @param main        Reference to the main program.
     */
    public Game(final String textContent, final Main main)
    {
        if (textContent == null || textContent.isBlank())
        {
            throw new IllegalArgumentException("Text content cannot be null or blank");
        }
        if (main == null)
        {
            throw new IllegalArgumentException("Main cannot be null");
        }

        this.textContent = textContent;
        this.main = main;
        this.input = new StringBuilder();
        this.gameInputManager = new GameInputManager(main, this);
    }

    /** Deletes the last typed character. */
    public void inputDeleteLastChar()
    {
        if (!this.input.isEmpty())
        {
            this.input.deleteCharAt(this.input.length() - 1);
        }
    }

    /** Deletes the last typed word. */
    public void inputDeleteLastWord()
    {
        if (!this.input.isEmpty())
        {
            final String temp = this.input.toString().replaceAll("\\S+\\s*$", "");
            this.input.setLength(0);
            this.input.append(temp);
        }
    }

    /** Adds a character to the input buffer. */
    public void inputAdd(final char c)
    {
        this.input.append(c);
    }

    @Override
    public void initCanvas(final Canvas mainCanvas)
    {
        mainCanvas.reset();
        mainCanvas.writeLine("Typing Mode - Start typing below:");
        mainCanvas.writeLine("--------------------------------");
        mainCanvas.writeLine(textContent.substring(0, Math.min(100, textContent.length())) + "...");
        mainCanvas.writeLine("");
        mainCanvas.writeLine("Your input:");
        mainCanvas.writeLine(this.input.toString());
    }

    @Override
    public void updateCanvas(final Canvas mainCanvas)
    {
        mainCanvas.reset();
        mainCanvas.writeLine("Target:");
        mainCanvas.writeLine(textContent.substring(0, Math.min(100, textContent.length())) + "...");
        mainCanvas.writeLine("");
        mainCanvas.writeLine("Your input:");
        mainCanvas.writeLine(this.input.toString());
        mainCanvas.writeLine("");
        mainCanvas.writeLine("Characters typed: " + this.input.length());
    }

    @Override
    public NativeKeyListener getInputManager()
    {
        return this.gameInputManager;
    }
}
