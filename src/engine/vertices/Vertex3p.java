package engine.vertices;

import engine.math.vec3;
import engine.Vertex;

public class Vertex3p extends Vertex {
    public vec3 position;

    public Vertex3p(vec3 position) {
        this.position = position;
    }

    @Override
    public float[] serialize() {
        return new float[] {
                position.x, position.y, position.z,
        };
    }
}
