package engine.renderer;

import static org.lwjgl.opengl.GL20.*;

public class IndexBuffer implements AutoCloseable {

    private final int id;

    public IndexBuffer(int[] indices) {
        this.id = glGenBuffers();
        bind();
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);
        unbind();
    }

    @Override
    public void close() throws Exception {
        glDeleteBuffers(this.id);
    }

    public void bind() {
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, this.id);
    }

    public void unbind() {
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
    }
}

