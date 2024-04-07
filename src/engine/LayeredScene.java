package engine;

public class LayeredScene extends Scene {

    public static abstract class Layer {

        public abstract void update(float deltaTime);
        public abstract void render();

    }

    private final Layer[] layers;

    public LayeredScene(Layer[] layers) {
        this.layers = layers;
    }

    @Override
    public void update(float deltaTime) {
        for (Layer layer : layers) {
            layer.update(deltaTime);
        }
    }

    @Override
    public void render() {
        for (Layer layer : layers) {
            layer.render();
        }
    }

//    @Override
//    public void onKeyPressed(KeyPressedEvent e) {
//
//    }
//
//    @Override
//    public void onKeyReleased(KeyReleasedEvent e) {}
//
//    @Override
//    public void onKeyTyped(KeyTypedEvent e) {}
//
//    @Override
//    public void onMouseButtonPressed(MouseButtonPressedEvent e) {}
//
//    @Override
//    public void onMouseButtonReleased(MouseButtonReleasedEvent e) {}

}
