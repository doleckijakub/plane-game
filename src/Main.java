import engine.Engine;

public class Main {

    public static void main(String[] args) {
        Engine.setWindowSize(1024, 1024);
        Engine.setWindowTitle("Plane Game");
        Engine.init();
        Engine.setScene(new GameScene());
        Engine.run();
    }

}