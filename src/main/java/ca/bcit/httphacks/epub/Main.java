package ca.bcit.httphacks.epub;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;

import java.io.IOException;
import java.util.List;

/**
 * Main class.
 *
 * @author Samuel Pita
 * @author Rodrigo Sanchez
 * @author Jaspreet Bath
 * @version 1.1
 */
public class Main
{
    private static final int PROGRAM_ERROR_EXIT_CODE = 1;
    private static final int CANVAS_CONTENT_LINES    = 8;

    private final Canvas mainCanvas;
    private       Scene  scene;

    public Main()
    {
        this.mainCanvas = new Canvas(CANVAS_CONTENT_LINES);
        this.scene      = null;
    }

    /**
     * Initialize JNativeHook keyboard listener.
     */
    private static void initializeInputHook()
    {
        try
        {
            // Disable jnativehook logging
            final java.util.logging.Logger logger =
                java.util.logging.Logger.getLogger(GlobalScreen.class.getPackage().getName());
            logger.setLevel(java.util.logging.Level.OFF);
            logger.setUseParentHandlers(false);

            GlobalScreen.registerNativeHook();
        }
        catch (final NativeHookException e)
        {
            System.err.println("Hook registration failed: " + e.getMessage());
            System.exit(PROGRAM_ERROR_EXIT_CODE);
        }
    }

    private void initEvent()
    {
        GlobalScreen.addNativeKeyListener(this.scene.getInputManager());
        this.scene.initLogic();

        this.scene.initCanvas(this.mainCanvas);
        Terminal.cycle(this.mainCanvas);
    }

    private void endEvent()
    {
        this.scene.endLogic();
        GlobalScreen.removeNativeKeyListener(this.scene.getInputManager());

        this.scene.endCanvas(this.mainCanvas);
        Terminal.cycle(this.mainCanvas);
    }

    public void start(final Scene scene)
    {
        if (this.scene != null)
        {
            throw new IllegalStateException("Program has already started");
        }

        this.scene = scene;
        initEvent();
    }

    public void update()
    {
        if (this.scene == null)
        {
            throw new IllegalStateException("Program hasn't started yet");
        }

        this.scene.updateLogic();
        this.scene.updateCanvas(this.mainCanvas);
        Terminal.cycle(this.mainCanvas);
    }

    public void end()
    {
        if (this.scene == null)
        {
            throw new IllegalStateException("Program hasn't started yet or already ended");
        }

        endEvent();
    }

    public void changeScene(final Scene newScene)
    {
        if (this.scene == null)
        {
            throw new IllegalStateException("Program hasn't started yet");
        }

        endEvent();
        this.scene = newScene;
        initEvent();
    }

    public boolean programActive()
    {
        return this.scene != null;
    }


    // ---------- Jay's Code ---------- //

    /*
     * Method to stop the Native listener so we can
     * use Scanner in Menu.
     */
    public void openMainMenu()
    {
        if (this.scene != null)
        {
            this.end();
        }

        try
        {
            // Stop JNativeHook so Scanner works
            GlobalScreen.unregisterNativeHook();
        }
        catch (NativeHookException e)
        {
            e.printStackTrace();
        }
        main(new String[0]);
    }


    /**
     * Program entry point.
     */
    public static void main(final String[] args)
    {
        try
        {
            // Run menu BEFORE initializing GlobalScreen
            Menu menu = new Menu();
            String epubPath = menu.show();
            if (epubPath == null)
            {
                System.out.println("Exiting program.");
                return;
            }

            System.out.println("Loading book...");
            List<String> words = BookReader.loadWords(epubPath);
            if (words.isEmpty())
            {
                System.out.println("Book is empty or unreadable.");
                return;
            }

            // Convert words to single string (limit optional)
            String textContent = String.join(" ", BookReader.getSlice(words, 0, 200));

            // Now safely start the key listener
            initializeInputHook();

            Main main = new Main();
            Scene game = new Game(words, main);
            main.start(game);


            // keeps the timer count without needing user
            // key press.
            while (main.programActive())
            {
                main.update();
                try
                {
                    // Sleep for 100ms (updates 10 times per second)
                    Thread.sleep(100);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        }
        catch (IOException e)
        {
            System.err.println("Error reading book: " + e.getMessage());
        }
    }
}
