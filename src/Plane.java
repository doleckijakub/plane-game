import engine.Entity;
import engine.Keyboard;
import engine.math.Color;
import engine.math.Mathf;
import engine.math.vec3;
import engine.Shader;
import glm_.vec3.Vec3;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;

public class Plane extends Entity {

    private static final float FLIGHT_RADIUS = 2.f;

    private static final float TURN_SPEED = Mathf.toRadians(30); // radians/second
    private float turn = 0.f;
    private float angleY = 0, angleX = 0;

    private static final float SPEED = Mathf.toRadians(10); // radians/second

    public Plane() {
        super(new vec3(0, 0, FLIGHT_RADIUS), new vec3(Mathf.toRadians(90), 0, 0));
    }

    @Override
    public void update(float deltaTime) {
        if (Keyboard.isKeyPressed(GLFW_KEY_A)) turn -= TURN_SPEED * deltaTime;
        if (Keyboard.isKeyPressed(GLFW_KEY_D)) turn += TURN_SPEED * deltaTime;

        angleY += SPEED * deltaTime * Mathf.sin(turn);
        angleX += SPEED * deltaTime * Mathf.cos(turn);

        transform.setPosition(new Vec3(
                Mathf.sin(angleX) * Mathf.cos(angleY),
                Mathf.cos(angleX),
                Mathf.sin(angleX) * Mathf.sin(angleY)
        ));
    }

    public static final Shader shader = new Shader("src/shaders/plane.vert", "src/shaders/plane.frag");

    @Override
    public void render() {
        new Line(transform.getPosition(), transform.getPosition().plus(transform.up()), Color.RED).render();
    }

}
