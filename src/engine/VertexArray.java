package engine;

import static org.lwjgl.opengl.GL30.*;

public class VertexArray implements AutoCloseable {

    private final int id;

    public VertexArray() {
        this.id = glGenVertexArrays();
    }

    @Override
    public void close() throws Exception {
        glDeleteVertexArrays(this.id);
    }

    public void linkAttrib(VertexBuffer vertexBuffer, int index, int numComponents, int type, boolean normalized, int stride, int offset) {
        bind();
        vertexBuffer.bind();
        glVertexAttribPointer(index, numComponents, type, normalized, stride, offset);
        glEnableVertexAttribArray(index);
        vertexBuffer.unbind();
        unbind();
    }

    public void bind() {
        glBindVertexArray(this.id);
    }

    public void unbind() {
        glBindVertexArray(0);
    }
}
