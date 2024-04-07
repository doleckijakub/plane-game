package engine;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL20.*;

public class VertexBuffer<Vertex_t extends Vertex> implements AutoCloseable {

    private final int id;

    public VertexBuffer(Vertex_t[] vertices) {
        this.id = glGenBuffers();
        bind();
        glBufferData(GL_ARRAY_BUFFER, serializeVertices(vertices), GL_STATIC_DRAW);
        unbind();
    }

    @Override
    public void close() throws Exception {
        glDeleteBuffers(this.id);
    }

    private float[] serializeVertices(Vertex_t[] vertices) {
        List<float[]> serialized = new ArrayList<>();

        for (Vertex_t vertex : vertices) {
            serialized.add(vertex.serialize());
        }

        float[] result = new float[serialized.get(0).length * serialized.size()];

        int i = 0;
        for (float[] vertex : serialized) {
            System.arraycopy(vertex, 0, result, i, vertex.length);
            i += vertex.length;
        }

        return result;
    }

    public void bind() {
        glBindBuffer(GL_ARRAY_BUFFER, this.id);
    }

    public void unbind() {
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }
}
