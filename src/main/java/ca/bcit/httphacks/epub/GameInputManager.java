package ca.bcit.httphacks.epub;

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;

/**
 * Game input manager class.
 *
 * @author Samuel Pita
 * @version 1.0
 */
public class GameInputManager extends InputManager
{
    private       boolean keyControlActive;
    private final Game    game;

    public GameInputManager(final Main main,
                            final Game game)
    {
        super(main);

        if (game == null)
        {
            throw new IllegalArgumentException("Game parameter cannot be null");
        }

        this.game = game;
    }

    @Override
    public void nativeKeyTyped(final NativeKeyEvent e)
    {
        if (!Character.isISOControl(e.getKeyChar()))
        {
            this.game.inputAdd(e.getKeyChar());
        }

        if (this.main.programActive())
        {
            this.main.update();
        }
    }

    @Override
    public void nativeKeyPressed(final NativeKeyEvent e)
    {
        switch (e.getKeyCode())
        {
            case NativeKeyEvent.VC_BACKSPACE ->
            {
                if (this.keyControlActive)
                {
                    this.game.inputDeleteLastWord();
                }
                else
                {
                    this.game.inputDeleteLastChar();
                }
            }
            case NativeKeyEvent.VC_CONTROL -> this.keyControlActive = true;

            // Jay's Code Here For Options ESCAPE key
            case NativeKeyEvent.VC_ESCAPE ->
            {
                // Switch to the Options scene
                this.main.changeScene(new Options(this.main,
                                                  this.game));
            }
        }

        // Making sure canvas updates after key press
        if (this.main.programActive())
        {
            this.main.update();
        }
    }

    @Override
    public void nativeKeyReleased(final NativeKeyEvent e)
    {
        if (e.getKeyCode() == NativeKeyEvent.VC_CONTROL)
        {
            this.keyControlActive = false;
        }
    }
}
