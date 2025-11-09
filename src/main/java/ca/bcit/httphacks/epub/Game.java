package ca.bcit.httphacks.epub;

import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

import java.util.List;


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
    private       String           textContent;
    private final StringBuilder    input;
    private final GameInputManager gameInputManager;
    private final Main             main;

    private final List<String> fullBookWords;
    private       long         startTime;

    /**
     * Game constructor.
     *
     * @param fullBookWords The text content to display for typing.
     * @param main          Reference to the main program.
     */
    public Game(final List<String> fullBookWords,
                final Main main)
    {
        if (fullBookWords == null || fullBookWords.isEmpty())
        {
            throw new IllegalArgumentException("Text content cannot be null or blank");
        }
        if (main == null)
        {
            throw new IllegalArgumentException("Main cannot be null");
        }

        this.fullBookWords    = fullBookWords;
        this.main             = main;
        this.input            = new StringBuilder();
        this.gameInputManager = new GameInputManager(main, this);

        generateNewContent();
    }


    /*
     * Method to make new random slice of texts from book.
     */
    public void generateNewContent()
    {
        this.textContent = BookReader.getRandomSlice(this.fullBookWords,
                                                     200);
        reset();
    }


    /*
     * Method to reset the game's state
     */
    public void reset()
    {
        this.input.setLength(0);
        this.startTime = 0;
    }


    /**
     * Deletes the last typed character.
     */
    public void inputDeleteLastChar()
    {
        if (!this.input.isEmpty())
        {
            this.input.deleteCharAt(this.input.length() - 1);
        }
    }

    /**
     * Deletes the last typed word.
     */
    public void inputDeleteLastWord()
    {
        if (!this.input.isEmpty())
        {
            final String temp = this.input.toString().replaceAll("\\S+\\s*$", "");
            this.input.setLength(0);
            this.input.append(temp);
        }
    }

    /**
     * Adds a character to the input buffer.
     */
    public void inputAdd(final char c)
    {
        if (this.startTime == 0)
        {
            this.startTime = System.currentTimeMillis();
        }
        this.input.append(c);
    }

    @Override
    public void initCanvas(final Canvas mainCanvas)
    {
//        mainCanvas.reset();
//        mainCanvas.writeLine("Typing Mode - Start typing below:");
//        mainCanvas.writeLine("--------------------------------");
//        mainCanvas.writeLine(textContent.substring(0, Math.min(100, textContent.length())) + "...");
//        mainCanvas.writeLine("");
//        mainCanvas.writeLine("Your input:");
//        mainCanvas.writeLine(this.input.toString());
        render(mainCanvas);
    }

    @Override
    public void updateCanvas(final Canvas mainCanvas)
    {
//        mainCanvas.reset();
//        mainCanvas.writeLine("Target:");
//        mainCanvas.writeLine(textContent.substring(0, Math.min(100, textContent.length())) + "...");
//        mainCanvas.writeLine("");
//        mainCanvas.writeLine("Your input:");
//        mainCanvas.writeLine(this.input.toString());
//        mainCanvas.writeLine("");
//        mainCanvas.writeLine("Characters typed: " + this.input.length());
        render(mainCanvas);
    }

    /*
     * Method to have scrolling with text.
     * Actually called the ticker-tape method!
     */
    private void render(final Canvas canvas)
    {
        canvas.reset();

        final int windowSize = 60;
        final int context = windowSize / 3;

        int startIndex;
        startIndex = Math.max(0,
                              this.input.length() - context);

        int targetEndIndex;
        targetEndIndex = Math.min(this.textContent.length(),
                                  startIndex + windowSize);

        String prefix;
        prefix = (startIndex > 0) ? "..." : "";

        String suffix;
        suffix = (targetEndIndex < this.textContent.length()) ? "..." : "";

        String textToShow;
        textToShow = this.textContent.substring(startIndex, targetEndIndex);

        String faintLayer;
        faintLayer = Terminal.ANSI_FAINT +
                     prefix + textToShow +
                     suffix + Terminal.ANSI_RESET_FORMATTING;


        // Colouring of the input layer
        StringBuilder coloredInput;
        coloredInput = new StringBuilder();

        int inputEndIndex;
        inputEndIndex = this.input.length();

        int correctChars;
        correctChars = 0;

        for (int i = startIndex; i < inputEndIndex; i++)
        {
            char typeChar;
            typeChar = this.input.charAt(i);

            char charToRender;
            charToRender = typeChar;

            // Forcing bold on the text after each loop
            // of the text.
            coloredInput.append(Terminal.ANSI_BOLD);


            if (i < this.textContent.length())
            {
                char targetChar;
                targetChar = this.textContent.charAt(i);

                if (typeChar == targetChar)
                {
                    // Green text for right
                    coloredInput.append(Terminal.ANSI_GREEN);
                    correctChars++;
                }
                else
                {
                    // Red text for incorrect
                    coloredInput.append(Terminal.ANSI_RED);

                    // accounts for 'space'
                    if (typeChar == ' ')
                    {
                        charToRender = '_';
                    }
                }
            }
            else
            {
                // handling for typing for too much.
                // the 'past' part of the text.
                coloredInput.append(Terminal.ANSI_RED);

                // shows extra space as underscores
                if (typeChar == ' ')
                {
                    charToRender = '_';
                }
            }

            // appending characters and reset.
            coloredInput.append(charToRender);
            coloredInput.append(Terminal.ANSI_RESET_FORMATTING);
        }


        // Applying a bold to the prefix.
        String brightPrefix;

        brightPrefix = Terminal.ANSI_BOLD +
                       prefix +
                       Terminal.ANSI_RESET_FORMATTING;

        // User input scrolling in BRIGHT opacity.
        // Make text bold.
        String brightLayer;
        brightLayer = brightPrefix +
                      coloredInput.toString();

        // Combine layers and use '\r' for rewind cursor
        String compositeLine;
        compositeLine = faintLayer +
                        "\r" +
                        brightLayer;

        canvas.writeLine("Type the text below:");
        canvas.writeLine("--------------------");
        canvas.writeLine(compositeLine);
        canvas.writeLine("--------------------");

        // Stats sections.
        int wpm;
        wpm = 0;

        String timeStr;
        timeStr = "00:00";

        int accuracy;
        accuracy = 100;

        if (this.startTime > 0 &&
            this.input.length() > 0)
        {
            long elapsedMillis;
            elapsedMillis = System.currentTimeMillis() - this.startTime;

            // avoiding division by zero
            double minutes;
            minutes = Math.max(0.0001, elapsedMillis / 60000.0);

            wpm = (int) ((this.input.length() / 5.0) / minutes);

            int totalCorrect;
            totalCorrect = 0;

            for (int i = 0; i < this.input.length(); i++)
            {
                if (i < this.textContent.length() &&
                    this.input.charAt(i) == this.textContent.charAt(i))
                {
                    totalCorrect++;
                }
            }
            // accuracy formula.
            accuracy = (int) ((totalCorrect / (double) this.input.length()) * 100);


            // Calculating time in MM:SS counter
            long timerSeconds;
            timerSeconds = (elapsedMillis / 1000) % 60;

            long timerMinutes;
            timerMinutes = (elapsedMillis / 60000);

            timeStr = String.format("%02d:%02d",
                                    timerMinutes,
                                    timerSeconds);
        }


        String statsLine = String.format(
            "Time: %s  |  " +
            Terminal.ANSI_CYAN + "Progress: %d/%d" + Terminal.ANSI_RESET_FORMATTING +
            "  |  " +
            Terminal.ANSI_YELLOW + "WPM: %d" + Terminal.ANSI_RESET_FORMATTING +
            "  |  " +
            Terminal.ANSI_GREEN + "Acc: %d%%" + Terminal.ANSI_RESET_FORMATTING,
            timeStr,
            this.input.length(),
            this.textContent.length(),
            wpm,
            accuracy
                                        );
        canvas.writeLine(statsLine);
    }

    @Override
    public NativeKeyListener getInputManager()
    {
        return this.gameInputManager;
    }
}
;