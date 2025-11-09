package ca.bcit.httphacks.epub;

import java.io.IOException;

/**
 * Terminal wrapper class.
 *
 * @author Samuel Pita
 * @author Rodrigo Sanchez
 * @author Jaspreet Bath
 * @version 1.0
 */
public class Terminal
{
    //#region ANSI Function Codes

    /**
     * (Specific for Unix, or Unix-like systems) Clears the terminal.
     */
    public static final String ANSI_CLEAR_TERMINAL = "\033[H\033[2J";

    /**
     * Resets all text formatting (color, background, style).
     */
    public static final String ANSI_RESET_FORMATTING = "\u001b[0m";

    //#endregion

    //#region Font Weight and Styles

    /**
     * Sets the text to bold or increased intensity (Code 1).
     */
    public static final String ANSI_BOLD = "\u001b[1m";

    /**
     * Sets the text to faded/decreased intensity (Code 2).
     */
    public static final String ANSI_FAINT = "\u001b[2m";

    /**
     * Sets the text to italic (Code 3).
     */
    public static final String ANSI_ITALIC = "\u001b[3m";

    /**
     * Sets the text to underlined (Code 4).
     */
    public static final String ANSI_UNDERLINE = "\u001b[4m";

    /**
     * Swaps foreground and background colors (Code 7).
     */
    public static final String ANSI_REVERSE = "\u001b[7m";

    //#endregion

    //#region Foreground Text Colors

    /**
     * Sets foreground color to black.
     */
    public static final String ANSI_BLACK = "\u001b[30m";

    /**
     * Sets foreground color to red.
     */
    public static final String ANSI_RED = "\u001b[31m";

    /**
     * Sets foreground color to green.
     */
    public static final String ANSI_GREEN = "\u001b[32m";

    /**
     * Sets foreground color to yellow/brown.
     */
    public static final String ANSI_YELLOW = "\u001b[33m";

    /**
     * Sets foreground color to blue.
     */
    public static final String ANSI_BLUE = "\u001b[34m";

    /**
     * Sets foreground color to magenta.
     */
    public static final String ANSI_MAGENTA = "\u001b[35m";

    /**
     * Sets foreground color to cyan.
     */
    public static final String ANSI_CYAN = "\u001b[36m";

    /**
     * Sets foreground color to white/light gray.
     */
    public static final String ANSI_WHITE = "\u001b[37m";

    //#endregion

    //#region Background Text Colors

    /**
     * Sets background color to black.
     */
    public static final String ANSI_BG_BLACK = "\u001b[40m";

    /**
     * Sets background color to red.
     */
    public static final String ANSI_BG_RED = "\u001b[41m";

    /**
     * Sets background color to green.
     */
    public static final String ANSI_BG_GREEN = "\u001b[42m";

    /**
     * Sets background color to yellow/brown.
     */
    public static final String ANSI_BG_YELLOW = "\u001b[43m";

    /**
     * Sets background color to blue.
     */
    public static final String ANSI_BG_BLUE = "\u001b[44m";

    /**
     * Sets background color to magenta.
     */
    public static final String ANSI_BG_MAGENTA = "\u001b[45m";

    /**
     * Sets background color to cyan.
     */
    public static final String ANSI_BG_CYAN = "\u001b[46m";

    /**
     * Sets background color to white/light gray.
     */
    public static final String ANSI_BG_WHITE = "\u001b[47m";

    //#endregion

    /**
     * Clears the terminal.
     */
    public static void clear()
    {
        final String os = System.getProperty("os.name").toLowerCase();

        try
        {
//            if (os.contains("windows"))
//            {
//                new ProcessBuilder("cmd", "/c", "cls")
//                    .inheritIO()
//                    .start()
//                    .waitFor();
//            }
//            else
//            {
//                new ProcessBuilder("clear")
//                    .inheritIO()
//                    .start()
//                    .waitFor();
//            }

            // \033[H moves cursor to the top-left corner
            // \033[2J clears the visible screen
            System.out.print("\033[H\033[2J");
            System.out.flush();
        }
        catch (Exception e)
        {
            System.err.println(
                "Could not clear the console: " + e.getMessage());
        }
    }

    /**
     * Writes the contents of a given Canvas object to the Terminal.
     *
     * @param canvas Source canvas object.
     */
    public static void write(final Canvas canvas)
    {
        if (canvas == null)
        {
            throw new IllegalArgumentException("Source canvas cannot be set to null");
        }

        for (final String contentLine : canvas.getContent())
        {
            if (contentLine == null)
            {
                System.out.println();
            }
            else
            {
                System.out.println(contentLine);
            }
        }
    }

    /**
     * Clears, then writes the contents of a given Canvas object to the Terminal.
     *
     * @param canvas Source canvas object.
     */
    public static void cycle(final Canvas canvas)
    {
        clear();
        write(canvas);
    }

    /**
     * Thread.sleep wrapper method, with InterruptedException handled
     * as a RuntimeException. Use this for debugging purposes only.
     *
     * @param milliseconds Time to sleep in milliseconds.
     */
    public static void debugSleep(final long milliseconds)
    {
        try
        {
            Thread.sleep(milliseconds);
        }
        catch (InterruptedException e)
        {
            throw new RuntimeException(e);
        }
    }
}
