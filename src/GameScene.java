import engine.api.Keyboard;
import engine.api.Renderer;
import engine.api.Scene;
import engine.math.*;
<<<<<<< HEAD
import engine.renderer.*;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class GameScene extends Scene {

    private static class Cube {

        public static class Vertex3p2uv extends Vertex {
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

        private final VertexArray vao;
        private final IndexBuffer ibo;

        public Cube(float s) {
            Vertex3p2uv[] vertices = new Vertex3p2uv[] {
                    new Vertex3p2uv(new vec3(-s, -s, +s), vec2.random()),
                    new Vertex3p2uv(new vec3(+s, -s, +s), vec2.random()),
                    new Vertex3p2uv(new vec3(+s, +s, +s), vec2.random()),
                    new Vertex3p2uv(new vec3(-s, +s, +s), vec2.random()),
                    new Vertex3p2uv(new vec3(-s, -s, -s), vec2.random()),
                    new Vertex3p2uv(new vec3(+s, -s, -s), vec2.random()),
                    new Vertex3p2uv(new vec3(+s, +s, -s), vec2.random()),
                    new Vertex3p2uv(new vec3(-s, +s, -s), vec2.random()),
            };

            int[] indices = new int[] {
                    // front
                    0, 1, 2,
                    2, 3, 0,
                    // right
                    1, 5, 6,
                    6, 2, 1,
                    // back
                    7, 6, 5,
                    5, 4, 7,
                    // left
                    4, 0, 3,
                    3, 7, 4,
                    // bottom
                    4, 5, 1,
                    1, 0, 4,
                    // top
                    3, 2, 6,
                    6, 7, 3,
            };

            this.vao = new VertexArray();
            this.vao.bind();
            VertexBuffer<Vertex3p2uv> vbo = new VertexBuffer<>(vertices);
            vbo.bind();
            vao.linkAttrib(vbo, 0, 3, GL_FLOAT, false, 5 * 4, 0);
            vao.linkAttrib(vbo, 1, 2, GL_FLOAT, false, 5 * 4, 3 * 4);

            this.ibo = new IndexBuffer(indices);
        }

        public void render() {
            this.vao.bind();
            this.ibo.bind();
            Renderer.drawTriangles(36);
        }
    }

    private static class Axis {

        public static class Vertex3p extends Vertex {
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

        private final VertexArray vao;
        private final IndexBuffer ibo;

        public Axis(vec3 start) {
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

        public void render() {
            this.vao.bind();
            this.ibo.bind();
            Renderer.drawLines(2);
        }

    }

    private final Shader cubeShader;
    private final Cube cube;

    private final Shader axisShader;
    private final Axis axisX, axisY, axisZ;

    private final Camera camera;
    private final Transform cubeTransform;

    public GameScene() {
        this.cubeShader = new Shader("src/shaders/cube.vert", "src/shaders/cube.frag");
        this.cube = new Cube(1f);

        float axisLength = 100.f;
        this.axisShader = new Shader("src/shaders/axis.vert", "src/shaders/axis.frag");
        this.axisX = new Axis(new vec3(axisLength, 0, 0));
        this.axisY = new Axis(new vec3(0, axisLength, 0));
        this.axisZ = new Axis(new vec3(0, 0, axisLength));

        this.camera = new Camera(
                new vec3(0, 0, 10),
=======

import static org.lwjgl.glfw.GLFW.*;

public class GameScene extends Scene {

    private final static float axisLength = 100.f;
    private final Axis axisX, axisY, axisZ;

    private final Earth earth;

    private final Camera camera;

    public GameScene() {
        this.axisX = new Axis(new vec3(axisLength, 0, 0), Color.RED);
        this.axisY = new Axis(new vec3(0, axisLength, 0), Color.GREEN);
        this.axisZ = new Axis(new vec3(0, 0, axisLength), Color.BLUE);

        this.earth = new Earth();

        this.camera = new Camera(
                new vec3(1, 1, 4),
>>>>>>> b07eb8b (Some updates idk really)
                new vec3(0, 0, 0),
                70,
                0.1f,
                1000f
        );
<<<<<<< HEAD

        this.cubeTransform = new Transform(
                new vec3(0, 0, 0),
                new vec3(0, 0, 0)
        );
=======
>>>>>>> b07eb8b (Some updates idk really)
    }

    @Override
    public void update(float deltaTime) {
<<<<<<< HEAD
//        this.cubeTransform.setRotation(this.cubeTransform.getRotation().plus(
//                new vec3(20 * deltaTime, 0, 0)
//        ));

=======
>>>>>>> b07eb8b (Some updates idk really)
        this.camera.setPosition(this.camera.getPosition().plus(
                new vec3(
                    (Keyboard.isKeyPressed(GLFW_KEY_D) ? 1 : 0) - (Keyboard.isKeyPressed(GLFW_KEY_A) ? 1 : 0),
                    (Keyboard.isKeyPressed(GLFW_KEY_Q) ? 1 : 0) - (Keyboard.isKeyPressed(GLFW_KEY_E) ? 1 : 0),
                    (Keyboard.isKeyPressed(GLFW_KEY_S) ? 1 : 0) - (Keyboard.isKeyPressed(GLFW_KEY_W) ? 1 : 0)
                ).times(deltaTime)
        ));

        if (Keyboard.isAnyKeyPressed(new int[] { GLFW_KEY_D, GLFW_KEY_A, GLFW_KEY_Q, GLFW_KEY_E, GLFW_KEY_W, GLFW_KEY_S })) {
            System.out.print("pos: ");
            System.out.println(this.camera.getPosition());
        }

        this.camera.setRotation(this.camera.getRotation().plus(
                new vec3(
                        (Keyboard.isKeyPressed(GLFW_KEY_UP) ? 1 : 0)    - (Keyboard.isKeyPressed(GLFW_KEY_DOWN) ? 1 : 0),
                        (Keyboard.isKeyPressed(GLFW_KEY_RIGHT) ? 1 : 0) - (Keyboard.isKeyPressed(GLFW_KEY_LEFT) ? 1 : 0),
                        0
                ).times(30 * deltaTime)
        ));

        if (Keyboard.isAnyKeyPressed(new int[] { GLFW_KEY_UP, GLFW_KEY_DOWN, GLFW_KEY_RIGHT, GLFW_KEY_LEFT })) {
            System.out.print("rot: ");
            System.out.println(this.camera.getRotation());
        }

        this.camera.setFov(
                this.camera.getFov()
                        + (Keyboard.isKeyPressed(GLFW_KEY_EQUAL) ? 1 : 0)
                        - (Keyboard.isKeyPressed(GLFW_KEY_MINUS) ? 1 : 0)
        );

        if (Keyboard.isAnyKeyPressed(new int[] { GLFW_KEY_EQUAL, GLFW_KEY_MINUS })) {
            System.out.print("fov: ");
            System.out.println(this.camera.getFov());
        }
    }

    @Override
    public void render() {
        Renderer.clearDepthBuffer();
        Renderer.clearColorBuffer(new Color(0.17f, 0.23f, 0.97f));

<<<<<<< HEAD
        this.cubeShader.bind();
        this.cubeShader.setUniform("projection", camera.getProjection());
        this.cubeShader.setUniform("view", camera.getView());
        {
            this.cubeShader.setUniform("transformation", cubeTransform.getMatrix());
            cube.render();
        }

        this.axisShader.bind();
        this.axisShader.setUniform("projection", camera.getProjection());
        this.axisShader.setUniform("view", camera.getView());
        {
            this.axisShader.setUniform("color", Color.RED);
            this.axisX.render();

            this.axisShader.setUniform("color", Color.GREEN);
            this.axisY.render();

            this.axisShader.setUniform("color", Color.BLUE);
            this.axisZ.render();
=======
        Axis.shader.setUniform("projection", camera.getProjection());
        Axis.shader.setUniform("view", camera.getView());
        {
            this.axisX.render();
            this.axisY.render();
            this.axisZ.render();
        }

        Earth.shader.setUniform("projection", camera.getProjection());
        Earth.shader.setUniform("view", camera.getView());
        {
            this.earth.render();
>>>>>>> b07eb8b (Some updates idk really)
        }
    }

}
