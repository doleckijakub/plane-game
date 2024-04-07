import engine.Keyboard;
import engine.Renderer;
import engine.Scene;
import engine.math.*;
import engine.Shader;

import static org.lwjgl.glfw.GLFW.*;

public class GameScene extends Scene {

    private final Plane plane;
    private final Earth earth;
    private vec3 lightPosition;

    public GameScene() {
        this.plane = new Plane();

        this.earth = new Earth();

        Camera.setMainCamera(new Camera(
                new vec3(1, 1, 10),
                new vec3(0, 0, 0),
                30,
                0.1f,
                1000f
        ));

        this.lightPosition = new vec3(10, 5, 3);
        Earth.shader.setUniform("lightPosition", this.lightPosition);
    }

    @Override
    public void update(float deltaTime) {
        Camera mainCamera = Camera.getMainCamera();
        if (Keyboard.isAnyKeyPressed(new int[] { GLFW_KEY_UP, GLFW_KEY_DOWN, GLFW_KEY_LEFT, GLFW_KEY_RIGHT })) {
            mainCamera.setRotation(mainCamera.getRotation().plus(new vec3(
                    (Keyboard.isKeyPressed(GLFW_KEY_UP) ? 1 : 0) - (Keyboard.isKeyPressed(GLFW_KEY_DOWN) ? 1 : 0),
                    0,
                    (Keyboard.isKeyPressed(GLFW_KEY_LEFT) ? 1 : 0) - (Keyboard.isKeyPressed(GLFW_KEY_RIGHT) ? 1 : 0)
            )));
        }
//        earth.getTransform().setRotation(earth.getTransform().getRotation().plus(new vec3(0, 0, Mathf.toRadians(360 * deltaTime))));
//        plane.update(deltaTime);

//        vec3 cameraPosition = Camera.getMainCamera().getPosition();
//        cameraPosition.y = plane.getTransform().getPosition().y;
//        Camera.getMainCamera().setPosition(cameraPosition);
//
//        vec3 cameraRotation = Camera.getMainCamera().getRotation();
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

        new Line(vec3.ZERO, lightPosition, Color.CYAN).render();
        this.earth.render();
        plane.render();
    }

}
