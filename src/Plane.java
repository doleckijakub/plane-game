import engine.Entity;
import engine.Keyboard;
import engine.math.Color;
import engine.Shader;
import engine.math.Mathf;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Plane extends Entity {

    private final static float PITCH_SPEED = 0.1f;
    private final static float PITCH_LERP_FACTOR = 0.9f;

    private final static float TURN_SPEED = 0.1f;
    private final static float TURN_LERP_FACTOR = 0.9f;

    private final static float FLIGHT_SPEED = 0.1f;

    private float pitch = 0;
    private float turn = 0;

    public Plane(Vector3f position) {
        super(position, new Vector3f(0));
    }

    @Override
    public void update(float deltaTime) {
        { // pitch
            float desiredPitch = 0;
            if (Keyboard.isKeyPressed(GLFW_KEY_KP_8)) desiredPitch -= 1;
            if (Keyboard.isKeyPressed(GLFW_KEY_KP_5)) desiredPitch += 1;
            desiredPitch *= PITCH_SPEED * deltaTime;
            pitch = Mathf.lerp(pitch, desiredPitch, 1 - Mathf.pow(PITCH_LERP_FACTOR, deltaTime));
            if (pitch != 0) {
                transform.rotate(pitch, 0, 0);
                System.out.println(transform.getRotation());
            }
        }

        { // turn
            float desiredTurn = 0;
            if (Keyboard.isKeyPressed(GLFW_KEY_KP_4)) desiredTurn -= 1;
            if (Keyboard.isKeyPressed(GLFW_KEY_KP_6)) desiredTurn += 1;
            desiredTurn *= TURN_SPEED * deltaTime;
            turn = Mathf.lerp(turn, desiredTurn, 1 - Mathf.pow(TURN_LERP_FACTOR, deltaTime));
            if (turn != 0) {
                transform.rotate(0, 0, turn);
                System.out.println(transform.getRotation());
            }
        }

        // forward
        if (Keyboard.isKeyPressed(GLFW_KEY_R)) {
            transform.getPosition().add(transform.forward().mul(deltaTime * FLIGHT_SPEED));
        }

        transform.updateMatrix();

        System.out.println("transform.up(): " + transform.up());
        System.out.println("transform.forward(): " + transform.forward());
    }

    public static final Shader shader = new Shader("src/shaders/plane.vert", "src/shaders/plane.frag");

    @Override
    public void render() {
        glDisable(GL_DEPTH_TEST);
        new Line(transform.getPosition(), new Vector3f(transform.getPosition()).add(1, 0, 0), Color.RED).render();
        new Line(transform.getPosition(), new Vector3f(transform.getPosition()).add(0, 1, 0), Color.GREEN).render();
        new Line(transform.getPosition(), new Vector3f(transform.getPosition()).add(0, 0, 1), Color.BLUE).render();

        new Line(transform.getPosition(), new Vector3f(transform.getPosition()).add(transform.up().mul(0.2f)), Color.MAGENTA).render();
        new Line(transform.getPosition(), new Vector3f(transform.getPosition()).add(transform.forward()), Color.YELLOW).render();
        glEnable(GL_DEPTH_TEST);
    }

}
