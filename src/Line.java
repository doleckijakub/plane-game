import engine.api.Entity;
import engine.api.Renderer;
import engine.math.Color;
import engine.math.vec3;
import engine.renderer.IndexBuffer;
import engine.renderer.Shader;
import engine.renderer.VertexArray;
import engine.renderer.VertexBuffer;
import engine.renderer.vertices.Vertex3p;

import static org.lwjgl.opengl.GL11.GL_FLOAT;

public class Axis extends Entity {

    public static final Shader shader = new Shader("src/shaders/axis.vert", "src/shaders/uniformColor.frag");

    private Color color;

    private final VertexArray vao;
    private final IndexBuffer ibo;

    public Axis(vec3 start, Color color) {
        super(vec3.ZERO);

        this.color = color;

        Vertex3p[] vertices = new Vertex3p[] {
                new Vertex3p(start),
                new Vertex3p(start.times(-1)),
        };

        int[] indices = new int[] {
                0, 1,
        };

        this.vao = new VertexArray();
        this.vao.bind();
        VertexBuffer<Vertex3p> vbo = new VertexBuffer<>(vertices);
        vbo.bind();
        vao.linkAttrib(vbo, 0, 3, GL_FLOAT, false, 6 * 4, 0);

        this.ibo = new IndexBuffer(indices);
    }

    @Override
    public void update(float deltaTime) {

    }

    @Override
    public void render() {
        this.vao.bind();
        this.ibo.bind();
        shader.setUniform("color", color);
        Renderer.drawLines(2);
    }

}