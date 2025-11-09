package ca.bcit.httphacks.epub;

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;


public class OptionsInputManager extends InputManager
{
    private final Options options;

    public OptionsInputManager(final Main main,
                               final Options options)
    {
        super(main);

        validateOptions(options);
        this.options = options;
    }


    private static void validateOptions(final Object o)
    {
        if (o == null)
        {
            throw new IllegalArgumentException(
                "Options class has to exist");
        }
    }


    @Override
    public void nativeKeyPressed(final NativeKeyEvent e)
    {
        switch (e.getKeyCode())
        {
            case NativeKeyEvent.VC_UP ->
            {
                options.previousOption();
                main.update();
            }
            case NativeKeyEvent.VC_DOWN ->
            {
                options.nextOption();
                main.update();
            }
            case NativeKeyEvent.VC_ENTER -> options.executeSelectedOption();
            case NativeKeyEvent.VC_ESCAPE -> main.changeScene(options.getGameScene());
        }
    }


    @Override
    public void nativeKeyTyped(final NativeKeyEvent e)
    {
    }

    @Override
    public void nativeKeyReleased(final NativeKeyEvent e)
    {
    }
}
