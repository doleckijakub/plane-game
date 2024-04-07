package engine.renderer.vertices;

import engine.math.vec2;
import engine.math.vec3;
import engine.renderer.Vertex;

public class Vertex3p2uv extends Vertex {
    public vec3 position;
    public vec2 uv;

    public Vertex3p2uv(vec3 position, vec2 uv) {
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