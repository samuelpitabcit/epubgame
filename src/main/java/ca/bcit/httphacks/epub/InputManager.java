package ca.bcit.httphacks.epub;

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

/**
 * Abstract input manager class.
 *
 * @author Samuel Pita
 * @version 1.0
 */
public abstract class InputManager implements NativeKeyListener
{
    protected final Main main;

    public InputManager(final Main main)
    {
        if (main == null)
        {
            throw new IllegalArgumentException("Main parameter cannot be null");
        }

        this.main = main;
    }

    @Override
    public abstract void nativeKeyTyped(final NativeKeyEvent e);

    @Override
    public abstract void nativeKeyPressed(final NativeKeyEvent e);

    @Override
    public abstract void nativeKeyReleased(final NativeKeyEvent e);
}
