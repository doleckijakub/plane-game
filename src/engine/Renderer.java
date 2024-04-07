package engine;

import engine.math.Color;

import static org.lwjgl.opengl.GL11.*;

public final class Renderer {

    public static void clearColorBuffer(Color color) {
        glClearColor(color.r, color.g, color.b, 1.f);
        glClear(GL_COLOR_BUFFER_BIT);
    }

    public static void clearDepthBuffer() {
        glClear(GL_DEPTH_BUFFER_BIT);
    }

    public static void drawTriangles(int indexCount) {
        glDrawElements(GL_TRIANGLES, indexCount, GL_UNSIGNED_INT, 0);
    }

    public static void drawLines(int indexCount) {
        glDrawElements(GL_LINES, indexCount, GL_UNSIGNED_INT, 0);
    }
}
