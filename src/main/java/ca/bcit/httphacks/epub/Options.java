package ca.bcit.httphacks.epub;

import java.util.List;

import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

public class Options implements Scene
{
    private final Main                main;
    private final Scene               gameScene;
    private final OptionsInputManager inputManager;
    private final List<String>        menuItems;
    private       int                 selectedIndex;


    private static final int MENU_MIN       = 0;
    private static final int MENU_ADD       = 1;
    private static final int MENU_SUB       = 1;
    private static final int MENU_INCREMENT = 1;


    /**
     * Constructor for Options.
     * Validates menuItems.
     *
     * @param main      from Main class
     * @param gameScene from Scene class
     */
    public Options(final Main main,
                   final Scene gameScene)
    {
        this.main         = main;
        this.gameScene    = gameScene;
        this.inputManager = new OptionsInputManager(main, this);
        menuItems         = List.of(
            "Resume",
            "Generate More",
            "Redo",
            "Main Menu",
            "Quit"
                                   );

        // After since menu exists now.
        validateSelection(selectedIndex);

        // Set selected to 0;
        this.selectedIndex = MENU_MIN;
    }


    /**
     * Validation Method for menuItems
     *
     * @param value int
     */
    private void validateSelection(final int value)
    {

        final int maxAllowed;
        maxAllowed = menuItems.size() - MENU_SUB;


        if (value < MENU_MIN ||
            value > maxAllowed)
        {
            throw new IllegalArgumentException(
                "Option cannot be less than " +
                MENU_MIN +
                " or more than " +
                maxAllowed);
        }
    }


    /**
     * Getter method for GameScene.
     *
     * @return gameScene
     */
    public Scene getGameScene()
    {
        return gameScene;
    }


    @Override
    public NativeKeyListener getInputManager()
    {
        return this.inputManager;
    }


    @Override
    public void initCanvas(final Canvas canvas)
    {
        // Initial render of scene starts
        updateCanvas(canvas);
    }

    /**
     * Method to render text to Canvas.
     *
     * @param canvas from Canvas class
     */
    public void updateCanvas(final Canvas canvas)
    {
        canvas.reset();
        canvas.writeLine("=== OPTIONS ===");

        for (int i = 0; i < menuItems.size(); i++)
        {
            // Drawing "> " next to the selected index item.
            String prefix;
            prefix = (i == selectedIndex) ? "> " : "  ";

            int lineNumber;
            lineNumber = i + MENU_INCREMENT;

            // Writing small offset with small line.
            if (i + MENU_INCREMENT < canvas.getContentLines())
            {
                canvas.writeLine(prefix + menuItems.get(i),
                                 lineNumber);
            }
        }
    }


    // ---------- MENU LOGIC ---------- //


    /*
     * Method for changing options forwards.
     * Mod usage for wrap around.
     */
    public void nextOption()
    {
        selectedIndex = (selectedIndex + MENU_ADD) % menuItems.size();
    }


    /*
     * Method for changing options previously.
     * Mod usage for wrap around.
     */
    public void previousOption()
    {
        selectedIndex = (selectedIndex - MENU_SUB + menuItems.size())
                        % menuItems.size();
    }


    public void executeSelectedOption()
    {
        final String selected;
        selected = menuItems.get(selectedIndex);

        if (selected.equals("Generate More") ||
            selected.equals("Redo"))
        {
            if (!(gameScene instanceof Game))
            {
                return;
            }
        }


        switch (selected)
        {
            case "Resume" -> main.changeScene(gameScene);

            case "Generate More" ->
            {
                ((Game) gameScene).generateNewContent();
                main.changeScene(gameScene);
            }
            case "Redo" ->
            {
                ((Game) gameScene).reset();
                main.changeScene(gameScene);
            }

            case "Main Menu" -> main.openMainMenu();

            case "Quit" ->
            {
                System.out.println("=== Bye ===");
                Terminal.clear();
                System.exit(0);
            }
        }
    }
}

