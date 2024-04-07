package engine;

import engine.events.KeyPressedEvent;
import engine.events.KeyReleasedEvent;

import static org.lwjgl.glfw.GLFW.*;

public class Keyboard {

    private static final boolean[] keys = new boolean[GLFW_KEY_LAST + 1];

    public static void onKeyPressed(KeyPressedEvent e) { keys[e.keycode()] = true; }

    public static void onKeyReleased(KeyReleasedEvent e) { keys[e.keycode()] = false; }

    public static boolean isKeyPressed(int keycode) { return keys[keycode]; }

    public static boolean isAnyKeyPressed(int[] keys) {
        for (int key : keys) {
            if (isKeyPressed(key)) return true;
        }

        return false;
    }
}
