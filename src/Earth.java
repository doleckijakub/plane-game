import engine.*;
import engine.vertices.Vertex3p;
import engine.math.Mathf;
import org.joml.Vector2f;
import org.joml.Vector3f;

import static org.lwjgl.opengl.GL11.GL_FLOAT;

public class Earth extends Entity {

    public static final Shader shader = new Shader("src/shaders/earth.vert", "src/shaders/earth.frag");

    private static final boolean HIGH_RES = true;

    public static final Texture globeHeightmapTexture = new Texture(
            HIGH_RES
                    ? "assets/heightmaps/gebco_08_rev_elev_21600x10800_with_normals.png"
                    : "assets/heightmaps/gebco_08_rev_elev_2160x1080_with_normals.png"
    );
    public static final Texture globeColorTexture = new Texture(
            "assets/colormaps/world.200408.3x2160x1080.png"
    );

    static {
        shader.setUniform("globeHeightmap", globeHeightmapTexture, 0);
        shader.setUniform("globeColormap", globeColorTexture, 1);
    }

    private static class Face {

        private static final int RES = 1024;
        private static final int INDEX_COUNT = (RES - 1) * (RES - 1) * 6;

        private final VertexArray vao;
        private final IndexBuffer ibo;

        private Face(Vector3f normal) {
            Vector3f axisA = new Vector3f(normal.y, normal.z, normal.x);
            Vector3f axisB = new Vector3f(normal).cross(axisA);

            Vertex3p[] vertices = new Vertex3p[RES * RES];
            int[] indices = new int[INDEX_COUNT];

            for (int i = 0, y = 0; y < RES; y++) {
                for (int x = 0; x < RES; x++) {
                    int vertexIndex = x + y * RES;
                    Vector2f t = new Vector2f(x, y).mul(1.f / (RES - 1.f));
                    Vector3f p = new Vector3f(normal)
                            .add(new Vector3f(axisA).mul(2 * t.x - 1))
                            .add(new Vector3f(axisB).mul(2 * t.y - 1));
                    Vector3f p2 = new Vector3f(p.x * p.x, p.y * p.y, p.z * p.z);
                    Vector3f pn = new Vector3f(
                            p.x * Mathf.sqrt(1f - (p2.y + p2.z) / 2f + (p2.y * p2.z) / 3),
                            p.y * Mathf.sqrt(1f - (p2.z + p2.x) / 2f + (p2.z * p2.x) / 3),
                            p.z * Mathf.sqrt(1f - (p2.x + p2.y) / 2f + (p2.x * p2.y) / 3)
                    );
                    // Vector2f uv = new Vector2f((float) (1 - (Math.atan2(pn.z, pn.x) / Math.PI + 1) / 2), (float) (Math.asin(pn.y) / Math.PI + 0.5));
                    vertices[vertexIndex] = new Vertex3p(pn);

                    if (x != RES - 1 && y != RES - 1) {
                        indices[i++] = vertexIndex;
                        indices[i++] = vertexIndex + RES + 1;
                        indices[i++] = vertexIndex + RES;
                        indices[i++] = vertexIndex;
                        indices[i++] = vertexIndex + 1;
                        indices[i++] = vertexIndex + RES + 1;
                    }
                }
            }

            this.vao = new VertexArray();
            VertexBuffer<Vertex3p> vbo = new VertexBuffer<>(vertices);
            vbo.bind();
            vao.linkAttrib(vbo, 0, 3, GL_FLOAT, false, 3 * 4, 0);

            this.ibo = new IndexBuffer(indices);
        }

        public void render() {
            this.vao.bind();
            this.ibo.bind();

            shader.bind();
            Renderer.drawTriangles(INDEX_COUNT);
        }
    }

    private static float pointOnSphereToHeight(Vector3f p) {
        return 0;
    }

    Face[] faces;

    public Earth() {
        super(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0));

        faces = new Face[] {
                new Face(new Vector3f(0, +1, 0)),
                new Face(new Vector3f(0, -1, 0)),
                new Face(new Vector3f(+1, 0, 0)),
                new Face(new Vector3f(-1, 0, 0)),
                new Face(new Vector3f(0, 0, +1)),
                new Face(new Vector3f(0, 0, -1))
        };
    }

    @Override
    public void update(float deltaTime) {}

    @Override
    public void render() {
//        shader.bind();
        shader.setUniform("transformation", transform.getMatrix());

        for (Face face : faces) {
            face.render();
        }
    }

}