import engine.api.Entity;
import engine.api.Renderer;
import engine.math.vec2;
import engine.math.vec3;
import engine.renderer.*;
import engine.renderer.vertices.Vertex3p;

import static org.lwjgl.opengl.GL11.GL_FLOAT;

public class Earth extends Entity {

    public static final Shader shader = new Shader("src/shaders/earth.vert", "src/shaders/earth.frag");

    public static final Image globeHeightmapImage = new Image("assets/heightmaps/gebco_08_rev_elev_2160x1080_with_normals.png");
    public static final Texture globeHeightmapTexture = new Texture(globeHeightmapImage);

    public static final Image globeColormapImage = new Image("assets/colormaps/world.200408.3x2160x1080.png");
    public static final Texture globeColorTexture = new Texture(globeColormapImage);

    static {
        shader.setUniform("globeHeightmap", globeHeightmapTexture, 0);
        shader.setUniform("globeColormap", globeColorTexture, 1);
    }

    private static class Face {

        private static final int RES = 128;
        private static final int INDEX_COUNT = (RES - 1) * (RES - 1) * 6;

        private final VertexArray vao;
        private final IndexBuffer ibo;

        private Face(vec3 normal) {
            vec3 axisA = new vec3(normal.y, normal.z, normal.x);
            vec3 axisB = vec3.cross(normal, axisA);

            Vertex3p[] vertices = new Vertex3p[RES * RES];
            int[] indices = new int[INDEX_COUNT];

            for (int i = 0, y = 0; y < RES; y++) {
                for (int x = 0; x < RES; x++) {
                    int vertexIndex = x + y * RES;
                    vec2 t = new vec2(x, y).times(1.f / (RES - 1.f));
                    vec3 p = normal
                            .plus(axisA.times(2 * t.x - 1))
                            .plus(axisB.times(2 * t.y - 1));
                    vec3 p2 = new vec3(p.x * p.x, p.y * p.y, p.z * p.z);
                    vec3 pn = new vec3(
                            p.x * (float) Math.sqrt(1f - (p2.y + p2.z) / 2f + (p2.y * p2.z) / 3),
                            p.y * (float) Math.sqrt(1f - (p2.z + p2.x) / 2f + (p2.z * p2.x) / 3),
                            p.z * (float) Math.sqrt(1f - (p2.x + p2.y) / 2f + (p2.x * p2.y) / 3)
                    );
                    vec2 uv = new vec2((float) (1 - (Math.atan2(pn.z, pn.x) / Math.PI + 1) / 2), (float) (Math.asin(pn.y) / Math.PI + 0.5));
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

    private static float pointOnSphereToHeight(vec3 p) {
        return 0;
    }

    Face[] faces;

    public Earth() {
        super(vec3.ZERO);

        faces = new Face[] {
                new Face(vec3.UP),
                new Face(vec3.DOWN),
                new Face(vec3.LEFT),
                new Face(vec3.RIGHT),
                new Face(vec3.FORWARD),
                new Face(vec3.BACK)
        };
    }

    @Override
    public void update(float deltaTime) {}

    @Override
    public void render() {
        for (Face face : faces) {
            face.render();
        }
    }

}