import engine.Entity;
import engine.math.Color;
import engine.Shader;
import engine.math.Mathf;
import org.joml.Quaternionf;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class Plane extends Entity {

    private final static float TURN_SPEED = 10;
    private final static float FLIGHT_SPEED = 0.1f;

    private float elevation = 0;
    private float turn = 0;
    private float speed = 0;

    public Plane() {
        super(new Vector3f(0, Earth.RADIUS, 0), new Vector3f(0));
    }

    @Override
    public void update(float deltaTime) {
        transform.getRotation().x += deltaTime;
        transform.getRotation().y += deltaTime;
        transform.getRotation().z += deltaTime;
        transform.updateMatrix();
    }

    public static final Shader shader = new Shader("src/shaders/plane.vert", "src/shaders/plane.frag");

    @Override
    public void render() {
//        System.out.println(transform.getPosition());
        new Line(transform.getPosition(), new Vector3f(transform.getPosition()).add(transform.right()), Color.CYAN).render();
        new Line(transform.getPosition(), new Vector3f(transform.getPosition()).add(transform.up()), Color.MAGENTA).render();
        new Line(transform.getPosition(), new Vector3f(transform.getPosition()).add(transform.backward()), Color.YELLOW).render();

        new Line(transform.getPosition(), new Vector3f(transform.getPosition()).add(1, 0, 0), Color.RED).render();
        new Line(transform.getPosition(), new Vector3f(transform.getPosition()).add(0, 1, 0), Color.GREEN).render();
        new Line(transform.getPosition(), new Vector3f(transform.getPosition()).add(0, 0, 1), Color.BLUE).render();
    }

}
