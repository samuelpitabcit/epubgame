package ca.bcit.httphacks.epub;

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

/**
 * Manages global key inputs, and applies them to methods that exist in the referring
 * game object, which has all the logic.
 *
 * @author Samuel Pita
 * @author Rodrigo Sanchez
 * @author Jaspreet Bath
 * @version 1.0
 */
public class InputManager implements NativeKeyListener
{
    private boolean keyControlActive;

    private final Game game;

    public InputManager(final Game game)
    {
        if (game == null)
        {
            throw new IllegalArgumentException("Game object cannot be null");
        }

        this.game             = game;
        this.keyControlActive = false;
    }

    @Override
    public void nativeKeyTyped(final NativeKeyEvent e)
    {
        if (!Character.isISOControl(e.getKeyChar()))
        {
            this.game.inputAdd(e.getKeyChar());
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
