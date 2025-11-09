package ca.bcit.httphacks.epub;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;

import java.util.List;


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
    /**
     * Main function.
     * <br>
     * The current code is absolute trash at the moment; will be reworked soon.
     *
     * @param args This is where the .epub file will be supplied.
     */
    public static void main (String[] args)
    {
        // -- FileLoader Stuff HERE --- //
        FileLoader loader = new FileLoader();
        List<String> lines = loader.loadText("test.txt");
        String joinedText = String.join("\n", lines);

        // Register the native hook.
        try {
            final java.util.logging.Logger logger =
                java.util.logging.Logger.getLogger(GlobalScreen.class.getPackage().getName());

            logger.setLevel(java.util.logging.Level.OFF);
            logger.setUseParentHandlers(false);

            GlobalScreen.registerNativeHook();
        }
        catch (NativeHookException e)
        {
            System.err.println("Hook registration failed: " + e.getMessage());
            System.exit(1);
            throw new RuntimeException(e);
        }

        final Canvas canvas = new Canvas(6);
        final Game game = new Game(joinedText);
        final InputManager inputManager = new InputManager(game);


        GlobalScreen.addNativeKeyListener(inputManager);

        // This should be the game loop (for now)
        while (GlobalScreen.isNativeHookRegistered()) {
            Terminal.debugSleep(100);
            Terminal.clear();

            game.writeToCanvasDebug(canvas);
            Terminal.write(canvas);

//            canvas.writeLine("Hello world!");
//            canvas.writeLine("Testing build.");
//
//            Terminal.write(canvas.getContent());
//            Terminal.debugSleep(1000);
//            Terminal.clear();
//
//            canvas.reset();
//            canvas.writeLine("I'm gonna cum inside of myself.");
//
//            Terminal.write(canvas.getContent());
//            Terminal.debugSleep(1000);
//            Terminal.clear();
        }
    }
}
