import engine.Entity;
import engine.Renderer;
import engine.math.Color;
import engine.math.vec3;
import engine.IndexBuffer;
import engine.Shader;
import engine.VertexArray;
import engine.VertexBuffer;
import engine.vertices.Vertex3p;

import static org.lwjgl.opengl.GL11.GL_FLOAT;

public class Line extends Entity {

    private Color color;

    private final VertexArray vao;
    private final IndexBuffer ibo;

    public Line(vec3 start, vec3 end, Color color) {
        super(vec3.ZERO, vec3.ZERO);

        this.color = color;

        Vertex3p[] vertices = new Vertex3p[] {
                new Vertex3p(start),
                new Vertex3p(end),
        };

        int[] indices = new int[] {
                0, 1,
        };

        this.vao = new VertexArray();
        this.vao.bind();
        VertexBuffer<Vertex3p> vbo = new VertexBuffer<>(vertices);
        vbo.bind();
        vao.linkAttrib(vbo, 0, 3, GL_FLOAT, false, 3 * 4, 0);

        this.ibo = new IndexBuffer(indices);
    }

    @Override
    public void update(float deltaTime) {

    }

    public static final Shader shader = new Shader("src/shaders/line.vert", "src/shaders/uniformColor.frag");

    @Override
    public void render() {
        shader.bind();

        this.vao.bind();
        this.ibo.bind();

        shader.setUniform("color", color);

        Renderer.drawLines(2);
    }

}