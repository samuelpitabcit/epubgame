package ca.bcit.httphacks.epub;

import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

/**
 * Scene interface.
 *
 * @author Samuel Pita
 * @version 1.0
 */
public interface Scene
{
    void initLogic();
    void updateLogic();
    void endLogic();

    default void initCanvas(final Canvas mainCanvas) {}
    default void updateCanvas(final Canvas mainCanvas) {}
    default void endCanvas(final Canvas mainCanvas) {}

    NativeKeyListener getInputManager();
}
