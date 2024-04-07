package engine;

import engine.events.*;

public abstract class Scene {

    public abstract void update(float deltaTime);
    public abstract void render();

    public void onKeyPressed(KeyPressedEvent e) {}
    public void onKeyReleased(KeyReleasedEvent e) {}
    public void onKeyTyped(KeyTypedEvent e) {}
    public void onMouseButtonPressed(MouseButtonPressedEvent e) {}
    public void onMouseButtonReleased(MouseButtonReleasedEvent e) {}
}
