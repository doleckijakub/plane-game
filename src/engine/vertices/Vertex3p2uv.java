package engine.vertices;

import engine.Vertex;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class Vertex3p2uv extends Vertex {
    public Vector3f position;
    public Vector2f uv;

    public Vertex3p2uv(Vector3f position, Vector2f uv) {
        this.position = position;
        this.uv = uv;
    }

    @Override
    public float[] serialize() {
        return new float[] {
                position.x, position.y, position.z,
                uv.x, uv.y,
        };
    }
}