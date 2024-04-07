import engine.Keyboard;
import engine.Renderer;
import engine.Scene;
import engine.math.*;
import engine.Shader;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.*;

public class GameScene extends Scene {

    private final Plane plane;
    private final Earth earth;
    private Vector3f lightPosition;

    public GameScene() {
        this.plane = new Plane();

        this.earth = new Earth();

        Camera.setMainCamera(new Camera(
                new Vector3f(1, 1, 10),
                new Vector3f(0, 0, 0),
                30,
                0.1f,
                1000f
        ));

        this.lightPosition = new Vector3f(10, 5, 3);
        Earth.shader.setUniform("lightPosition", this.lightPosition);
    }

    @Override
    public void update(float deltaTime) {
        Camera mainCamera = Camera.getMainCamera();
        if (Keyboard.isAnyKeyPressed(new int[] { GLFW_KEY_UP, GLFW_KEY_DOWN, GLFW_KEY_LEFT, GLFW_KEY_RIGHT })) {
            mainCamera.getRotation().add(new Vector3f(
                    (Keyboard.isKeyPressed(GLFW_KEY_DOWN) ? 1 : 0) - (Keyboard.isKeyPressed(GLFW_KEY_UP) ? 1 : 0),
                    (Keyboard.isKeyPressed(GLFW_KEY_RIGHT) ? 1 : 0) - (Keyboard.isKeyPressed(GLFW_KEY_LEFT) ? 1 : 0),
                    0
            ).mul(deltaTime));
            mainCamera.updateView();
        }
//        earth.getTransform().setRotation(earth.getTransform().getRotation().plus(new Vector3f(0, 0, Mathf.toRadians(360 * deltaTime))));
//        plane.update(deltaTime);

//        Vector3f cameraPosition = Camera.getMainCamera().getPosition();
//        cameraPosition.y = plane.getTransform().getPosition().y;
//        Camera.getMainCamera().setPosition(cameraPosition);
//
//        Vector3f cameraRotation = Camera.getMainCamera().getRotation();
//        cameraRotation.x = 1; // Mathf.asin(plane.getTransform().getPosition().y);
//        Camera.getMainCamera().setRotation(cameraRotation);
    }

    @Override
    public void render() {
        Renderer.clearDepthBuffer();
        Renderer.clearColorBuffer(new Color(0.17f, 0.23f, 0.97f));

        for (Shader shader : new Shader[] { Line.shader, Earth.shader, Plane.shader }) {
            shader.setUniform("projection", Camera.getMainCamera().getProjection());
            shader.setUniform("view", Camera.getMainCamera().getView());
        }

        new Line(new Vector3f(0, 0, 0), lightPosition, Color.CYAN).render();
        this.earth.render();
        plane.render();
    }

}
