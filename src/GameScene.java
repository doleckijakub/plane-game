import engine.Keyboard;
import engine.Renderer;
import engine.Scene;
import engine.math.*;
import engine.Shader;
import org.joml.Vector2f;
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
                new Vector3f(0, 0, 10),
                new Vector3f(0, 0, 0),
                30f,
                0.1f,
                1000f
        ));

        this.lightPosition = new Vector3f(10, 5, 3);
        Earth.shader.setUniform("lightPosition", this.lightPosition);
    }

    private Vector2f cameraR = new Vector2f(0);

    @Override
    public void update(float deltaTime) {
        Camera camera = Camera.getMainCamera();
        if (Keyboard.isAnyKeyPressed(new int[] { GLFW_KEY_UP, GLFW_KEY_DOWN, GLFW_KEY_LEFT, GLFW_KEY_RIGHT })) {
            camera.getRotation().add(new Vector3f(
                    (Keyboard.isKeyPressed(GLFW_KEY_DOWN) ? 1 : 0) - (Keyboard.isKeyPressed(GLFW_KEY_UP) ? 1 : 0),
                    (Keyboard.isKeyPressed(GLFW_KEY_RIGHT) ? 1 : 0) - (Keyboard.isKeyPressed(GLFW_KEY_LEFT) ? 1 : 0),
                    0
            ).mul(deltaTime));
            camera.updateView();
        }

        if (Keyboard.isAnyKeyPressed(new int[] { GLFW_KEY_W, GLFW_KEY_S, GLFW_KEY_A, GLFW_KEY_D, GLFW_KEY_SPACE, GLFW_KEY_LEFT_SHIFT })) {
            camera.getPosition().add(new Vector3f(
                    (Keyboard.isKeyPressed(GLFW_KEY_D) ? 1 : 0) - (Keyboard.isKeyPressed(GLFW_KEY_A) ? 1 : 0),
                    (Keyboard.isKeyPressed(GLFW_KEY_SPACE) ? 1 : 0) - (Keyboard.isKeyPressed(GLFW_KEY_LEFT_SHIFT) ? 1 : 0),
                    (Keyboard.isKeyPressed(GLFW_KEY_S) ? 1 : 0) - (Keyboard.isKeyPressed(GLFW_KEY_W) ? 1 : 0)
            ).mul(deltaTime * (Keyboard.isKeyPressed(GLFW_KEY_LEFT_CONTROL) ? 5 : 1)));
            camera.updateView();
        }

        plane.update(deltaTime);
//        camera.setPosition(plane.getTransform().forward().mul(-2).add(plane.getTransform().up()));

        if (Keyboard.isAnyKeyPressed(new int[] { GLFW_KEY_J, GLFW_KEY_K })) {
            if (Keyboard.isKeyPressed(GLFW_KEY_J)) cameraR.x += deltaTime;
            if (Keyboard.isKeyPressed(GLFW_KEY_K)) cameraR.y += deltaTime;

            Earth.shader.setUniform("lightPosition", this.lightPosition = new Vector3f(
                    Mathf.sin(cameraR.y) * Mathf.sin(cameraR.x),
                    Mathf.cos(cameraR.x),
                    Mathf.cos(cameraR.y) * Mathf.sin(cameraR.x)
            ).mul(20));
        }
    }

    @Override
    public void render() {
        Renderer.clearDepthBuffer();
        // Renderer.clearColorBuffer(new Color(0.17f, 0.23f, 0.97f));
        Renderer.clearColorBuffer(new Color(0x181818));

        for (Shader shader : new Shader[] { Line.shader, Earth.shader, Plane.shader }) {
            shader.setUniform("projection", Camera.getMainCamera().getProjection());
            shader.setUniform("view", Camera.getMainCamera().getView());
        }

        new Line(new Vector3f(0, 0, 0), lightPosition, Color.CYAN).render();
        earth.render();
        plane.render();
    }

}
