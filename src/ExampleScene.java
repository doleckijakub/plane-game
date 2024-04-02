//import engine.api.Engine;
//import engine.api.Keyboard;
//import engine.api.Renderer;
//import engine.api.Scene;
//import engine.events.KeyPressedEvent;
//import engine.math.Color;
//import engine.math.float3;
//import engine.renderer.*;
//
//import static org.lwjgl.glfw.GLFW.*;
//import static org.lwjgl.opengl.GL20.*;
//
//public class ExampleScene extends Scene {
//
//    private final Shader shader;
//    private final VertexArray vao;
//    private final Color background;
//
//    public ExampleScene() {
//        float3 a = float3.random(); a.z = 0;
//        float3 b = float3.random(); b.z = 0;
//        float3 c = float3.random(); c.z = 0;
//
//        Vertex3p3c[] vertices = new Vertex3p3c[] {
//                new Vertex3p3c(a.times(2).plus(new float3(-1, -1, -1)), Color.random()),
//                new Vertex3p3c(b.times(2).plus(new float3(-1, -1, -1)), Color.random()),
//                new Vertex3p3c(c.times(2).plus(new float3(-1, -1, -1)), Color.random()),
//        };
//
//        int[] indices = new int[] {
//                0, 1, 2,
//        };
//
//        this.shader = new Shader("src/shaders/cube.vert", "src/shaders/cube.frag");
//        this.shader.bind();
//
//        this.vao = new VertexArray();
//        this.vao.bind();
//
//        VertexBuffer<Vertex3p3c> vbo = new VertexBuffer<>(vertices);
//        vbo.bind();
//
//        IndexBuffer ibo = new IndexBuffer(indices);
//        ibo.bind();
//
//        vao.linkAttrib(vbo, 0, 3, GL_FLOAT, false, 6 * 4, 0);
//        vao.linkAttrib(vbo, 1, 3, GL_FLOAT, false, 6 * 4, 3 * 4);
//
//        this.background = Color.random();
//    }
//
//    private float scale = 1.0f;
//
//    @Override
//    public void update(float deltaTime) {
//        if (Keyboard.isKeyPressed(GLFW_KEY_W)) {
//            scale += deltaTime;
//        }
//
//        if (Keyboard.isKeyPressed(GLFW_KEY_S)) {
//            scale -= deltaTime;
//        }
//    }
//
//    @Override
//    public void render() {
//        Renderer.clearDepthBuffer();
//        Renderer.clearColorBuffer(this.background);
//
//        this.vao.bind();
//        this.shader.setUniform("scale", scale);
//        glDrawElements(GL_TRIANGLES, 9, GL_UNSIGNED_INT, 0);
//    }
//
//    @Override
//    public void onKeyPressed(KeyPressedEvent e) {
//        if (e.keycode() == GLFW_KEY_N) {
//            Engine.setScene(new ExampleScene());
//        }
//    }
//
//}
