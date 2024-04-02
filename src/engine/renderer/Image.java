package engine.renderer;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.stb.STBImage.*;

public class Image implements AutoCloseable {

    static {
        stbi_set_flip_vertically_on_load(true);
    }

    private final int width, height, channels;
    private final ByteBuffer buffer;

    public Image(String filename) {
        int[] width = new int[1];
        int[] height = new int[1];
        int[] channels = new int[1];
        this.buffer = stbi_load(filename, width, height, channels, 4);
        this.width = width[0];
        this.height = height[0];
        this.channels = channels[0];
    }

    @Override
    public void close() throws Exception {
        stbi_image_free(buffer);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getChannels() {
        return channels;
    }

    public int getFormat() {
        switch (channels) {
            case 1: return GL_R;
            case 2: return GL_RG;
            case 3: return GL_RGB;
            case 4: return GL_RGBA;
        }

        throw new RuntimeException("Unknown format for " + channels + " channels");
    }

    public ByteBuffer getBuffer() {
        return buffer;
    }

}
