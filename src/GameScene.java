import engine.api.*;
import engine.math.*;

import static org.lwjgl.glfw.GLFW.*;

public class GameScene extends Scene {

    private final static float axisLength = 100.f;
    private final Axis axisX, axisY, axisZ;

    private final Earth earth;

    private final Camera camera;

    public GameScene() {
        this.axisX = new Axis(new vec3(axisLength, 0, 0), Color.RED);
        this.axisY = new Axis(new vec3(0, axisLength, 0), Color.GREEN);
        this.axisZ = new Axis(new vec3(0, 0, axisLength), Color.BLUE);

        this.earth = new Earth();

        this.camera = new Camera(
                new vec3(1, 1, 4),
                new vec3(0, 0, 0),
                70,
                0.1f,
                1000f
        );
    }

    @Override
    public void update(float deltaTime) {
        this.camera.setPosition(this.camera.getPosition().plus(
                new vec3(
                    (Keyboard.isKeyPressed(GLFW_KEY_D) ? 1 : 0) - (Keyboard.isKeyPressed(GLFW_KEY_A) ? 1 : 0),
                    (Keyboard.isKeyPressed(GLFW_KEY_Q) ? 1 : 0) - (Keyboard.isKeyPressed(GLFW_KEY_E) ? 1 : 0),
                    (Keyboard.isKeyPressed(GLFW_KEY_S) ? 1 : 0) - (Keyboard.isKeyPressed(GLFW_KEY_W) ? 1 : 0)
                ).times(deltaTime)
        ));

        if (Keyboard.isAnyKeyPressed(new int[] { GLFW_KEY_D, GLFW_KEY_A, GLFW_KEY_Q, GLFW_KEY_E, GLFW_KEY_W, GLFW_KEY_S })) {
            System.out.print("pos: ");
            System.out.println(this.camera.getPosition());
        }

        this.camera.setRotation(this.camera.getRotation().plus(
                new vec3(
                        (Keyboard.isKeyPressed(GLFW_KEY_UP) ? 1 : 0)    - (Keyboard.isKeyPressed(GLFW_KEY_DOWN) ? 1 : 0),
                        (Keyboard.isKeyPressed(GLFW_KEY_RIGHT) ? 1 : 0) - (Keyboard.isKeyPressed(GLFW_KEY_LEFT) ? 1 : 0),
                        0
                ).times(30 * deltaTime)
        ));

        if (Keyboard.isAnyKeyPressed(new int[] { GLFW_KEY_UP, GLFW_KEY_DOWN, GLFW_KEY_RIGHT, GLFW_KEY_LEFT })) {
            System.out.print("rot: ");
            System.out.println(this.camera.getRotation());
        }

        this.camera.setFov(
                this.camera.getFov()
                        + (Keyboard.isKeyPressed(GLFW_KEY_EQUAL) ? 1 : 0)
                        - (Keyboard.isKeyPressed(GLFW_KEY_MINUS) ? 1 : 0)
        );

        if (Keyboard.isAnyKeyPressed(new int[] { GLFW_KEY_EQUAL, GLFW_KEY_MINUS })) {
            System.out.print("fov: ");
            System.out.println(this.camera.getFov());
        }
    }

    @Override
    public void render() {
        Renderer.clearDepthBuffer();
        Renderer.clearColorBuffer(new Color(0.17f, 0.23f, 0.97f));

        Axis.shader.setUniform("projection", camera.getProjection());
        Axis.shader.setUniform("view", camera.getView());
        {
            this.axisX.render();
            this.axisY.render();
            this.axisZ.render();
        }

        Earth.shader.setUniform("projection", camera.getProjection());
        Earth.shader.setUniform("view", camera.getView());
        {
            this.earth.render();
        }
    }

}
