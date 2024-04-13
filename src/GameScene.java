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
        this.plane = new Plane(new Vector3f(0, 0, 2));

        this.earth = new Earth();

        Camera.setMainCamera(new Camera(
                new Vector3f(0, 0, 10),
                new Vector3f(0, 0, 0),
                30f,
                0.1f,
                1000f
        ));

        System.out.println("i: " + Camera.getMainCamera().getPosition());

        this.lightPosition = new Vector3f(10, 5, 3);
        Earth.shader.setUniform("lightPosition", this.lightPosition);
    }

    private final Vector2f cameraR = new Vector2f(0);

    Vector3f cameraRelativeRotation = new Vector3f(0);
    Vector3f cameraRelativePosition = new Vector3f(0, 0, 10);

    @Override
    public void update(float deltaTime) {
        plane.update(deltaTime);

        Camera camera = Camera.getMainCamera();

        {
            if (Keyboard.isAnyKeyPressed(new int[]{GLFW_KEY_W, GLFW_KEY_S, GLFW_KEY_A, GLFW_KEY_D, GLFW_KEY_SPACE, GLFW_KEY_LEFT_SHIFT})) {
                cameraRelativePosition.add(new Vector3f(
                        (Keyboard.isKeyPressed(GLFW_KEY_D) ? 1 : 0) - (Keyboard.isKeyPressed(GLFW_KEY_A) ? 1 : 0),
                        (Keyboard.isKeyPressed(GLFW_KEY_SPACE) ? 1 : 0) - (Keyboard.isKeyPressed(GLFW_KEY_LEFT_SHIFT) ? 1 : 0),
                        (Keyboard.isKeyPressed(GLFW_KEY_S) ? 1 : 0) - (Keyboard.isKeyPressed(GLFW_KEY_W) ? 1 : 0)
                ).mul(deltaTime * (Keyboard.isKeyPressed(GLFW_KEY_LEFT_CONTROL) ? 5 : 1)));
                System.out.println("cameraRelativePosition: " + cameraRelativePosition);
            }

            camera.setPosition(new Vector3f(plane.getTransform().getPosition())
                    .add(plane.getTransform().right().mul(cameraRelativePosition.x))
                    .add(plane.getTransform().up().mul(cameraRelativePosition.y))
                    .add(plane.getTransform().backward().mul(cameraRelativePosition.z))
            );

            if (Keyboard.isAnyKeyPressed(new int[]{GLFW_KEY_UP, GLFW_KEY_DOWN, GLFW_KEY_LEFT, GLFW_KEY_RIGHT})) {
                cameraRelativeRotation.add(new Vector3f(
                        (Keyboard.isKeyPressed(GLFW_KEY_DOWN) ? 1 : 0) - (Keyboard.isKeyPressed(GLFW_KEY_UP) ? 1 : 0),
                        (Keyboard.isKeyPressed(GLFW_KEY_RIGHT) ? 1 : 0) - (Keyboard.isKeyPressed(GLFW_KEY_LEFT) ? 1 : 0),
                        0
                ).mul(deltaTime));
                System.out.println("cameraRelativeRotation: " + cameraRelativeRotation);
            }

            camera.setRotation(new Vector3f(plane.getTransform().getRotation())
                    .add(cameraRelativeRotation)
            );

            camera.updateView();
        }

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
