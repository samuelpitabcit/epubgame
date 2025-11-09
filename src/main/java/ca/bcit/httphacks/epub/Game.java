package ca.bcit.httphacks.epub;

import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

/**
 * Game class.
 *
 * @author Samuel Pita
 * @author Rodrigo Sanchez
 * @author Jaspreet Bath
 * @version 1.0
 */
public class Game implements Scene
{
    private final String textContent;
    private final StringBuilder input;
    private final GameInputManager gameInputManager;

    /**
     * Game constructor.
     *
     * @param textContent The content for the player to type on.
     */
    public Game(final String textContent,
                final Main main)
    {
        this.textContent      = textContent;
        this.input            = new StringBuilder();
        this.gameInputManager = new GameInputManager(main, this);
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

    @Override
    public void initCanvas(final Canvas mainCanvas) {
        mainCanvas.reset();
        mainCanvas.writeLine("Begin typing!");
        mainCanvas.writeLine("length = " + this.input.length());
    }

    @Override
    public void updateCanvas(final Canvas mainCanvas)
    {
        mainCanvas.reset();
        mainCanvas.writeLine(this.input.toString());
        mainCanvas.writeLine("length = " + this.input.length());
    }

    @Override
    public NativeKeyListener getInputManager()
    {
        return this.gameInputManager;
    }
}
