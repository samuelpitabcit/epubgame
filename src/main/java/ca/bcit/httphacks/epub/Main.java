package ca.bcit.httphacks.epub;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;

/**
 * Main class.
 *
 * @author Samuel Pita
 * @author Rodrigo Sanchez
 * @author Jaspreet Bath
 * @version 1.0
 */
public class Main
{
    private static final int PROGRAM_ERROR_EXIT_CODE = 1;
    private static final int CANVAS_CONTENT_LINES = 8;

    private final Canvas mainCanvas;
    private Scene scene;

    public Main()
    {
        this.mainCanvas = new Canvas(CANVAS_CONTENT_LINES);
        this.scene      = null;
    }

    private void initEvent()
    {
        // Logic Setup
        GlobalScreen.addNativeKeyListener(this.scene.getInputManager());
        this.scene.initLogic();

        // Canvas Setup
        this.scene.initCanvas(this.mainCanvas);
        Terminal.cycle(this.mainCanvas);
    }

    private void endEvent()
    {
        // Logic Conclusion
        this.scene.endLogic();
        GlobalScreen.removeNativeKeyListener(this.scene.getInputManager());

        // Canvas Conclusion
        this.scene.endCanvas(this.mainCanvas);
        Terminal.cycle(this.mainCanvas);
    }

    void start(final Scene scene)
    {
        if (this.scene != null)
        {
            throw new IllegalStateException("Program has started already");
        }

        this.scene = scene;
        initEvent();
    }

    void update()
    {
        if (this.scene == null)
        {
            throw new IllegalStateException("Program hasn't started yet");
        }

        this.scene.updateLogic();
        this.scene.updateCanvas(this.mainCanvas);
        Terminal.cycle(this.mainCanvas);
    }

    void end()
    {
        if (this.scene == null)
        {
            throw new IllegalStateException("Program hasn't started yet, or has already ended");
        }

        endEvent();
    }

    void changeScene(final Scene newScene)
    {
        if (this.scene == null)
        {
            throw new IllegalStateException("Program hasn't started yet");
        }

        endEvent();
        this.scene = newScene;
        initEvent();
    }

    private static void initializeInputHook()
    {
        try
        {
            // Disables logging for the GlobalScreen class.
            final java.util.logging.Logger logger =
                java.util.logging.Logger.getLogger(
                    GlobalScreen.class
                        .getPackage()
                        .getName());
            logger.setLevel(java.util.logging.Level.OFF);
            logger.setUseParentHandlers(false);

            // Registers the native hook.
            GlobalScreen.registerNativeHook();
        }
        catch (final NativeHookException e)
        {
            System.err.println("Hook registration failed: " + e.getMessage());
            System.exit(PROGRAM_ERROR_EXIT_CODE);

            throw new RuntimeException(e);
        }
    }

    /**
     * Main function.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(final String[] args)
    {
        initializeInputHook();

        final Main main = new Main();
        final Scene game = new Game("Test content", main);

        main.start(game);
    }
}
