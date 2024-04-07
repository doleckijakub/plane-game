package engine.vertices;

import engine.Vertex;
import org.joml.Vector3f;

public class Vertex3p extends Vertex {
    public Vector3f position;

    public Vertex3p(Vector3f position) {
        this.position = position;
    }

    @Override
    public float[] serialize() {
        return new float[] {
                position.x, position.y, position.z,
        };
    }
}
