import engine.Engine;

public class Main {

    public static void main(String[] args) {
        Engine.setWindowSize(800, 800);
        Engine.setWindowTitle("test");
        Engine.init();
        Engine.setScene(new GameScene());
        Engine.run();
    }

}