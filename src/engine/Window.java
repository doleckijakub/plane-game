package engine;

import java.util.Objects;

import engine.events.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Window implements AutoCloseable {

    private int width, height;
    private final long window;

    static void init(int width, int height, String title) {
        if (Engine.window != null) throw new RuntimeException("Double initialization of Window");
        Engine.window = new Window(width, height, title);
    }

    public Window(int width, int height, String title) {
        this.width = width;
        this.height = height;

        boolean success = glfwInit();
        assert success;

        GLFWErrorCallback.createPrint(System.err).set();

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);

        this.window = glfwCreateWindow(width, height, title, NULL, NULL);
        assert window != NULL;

        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            switch (action) {
                case GLFW_PRESS -> Engine.onKeyPressed(new KeyPressedEvent(key));
                case GLFW_RELEASE -> Engine.onKeyReleased(new KeyReleasedEvent(key));
                case GLFW_REPEAT -> { assert true; }
                default -> { throw new RuntimeException("unreachable"); }
            }
        });

        glfwSetCharCallback(window, (window, character) -> {
            Engine.onKeyTyped(new KeyTypedEvent(character));
        });

        glfwSetMouseButtonCallback(window, (window, button, action, mods) -> {
            switch (action) {
                case GLFW_PRESS -> Engine.onMouseButtonPressed(new MouseButtonPressedEvent(button));
                case GLFW_RELEASE -> Engine.onMouseButtonReleased(new MouseButtonReleasedEvent(button));
                default -> { throw new RuntimeException("unreachable"); }
            }
        });

        GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        assert vidMode != null;
        glfwSetWindowPos(window, (vidMode.width() - width) / 2, (vidMode.height() - height) / 2);

        glfwMakeContextCurrent(window);
        GL.createCapabilities();

        glViewport(0, 0, width, height);
        glEnable(GL_DEPTH_TEST);
        glfwMakeContextCurrent(window);
//        glfwSwapInterval(1); // vsync
        glfwShowWindow(window);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public void close() throws Exception {
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);
        glfwTerminate();
        Objects.requireNonNull(glfwSetErrorCallback(null)).free();
    }

    public boolean shouldClose() {
        return glfwWindowShouldClose(window);
    }

    public void clear() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    public void swapBuffers() {
        glfwSwapBuffers(window);
    }

    public void pollEvents() {
        glfwPollEvents();
    }
}
