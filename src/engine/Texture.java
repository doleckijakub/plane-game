package engine;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL30.GL_RG;
import static org.lwjgl.stb.STBImage.*;

public class Texture implements AutoCloseable {

    private final int id;

    static {
        stbi_set_flip_vertically_on_load(true);
    }

    public Texture(String filename) {
        int[] width = new int[1];
        int[] height = new int[1];
        int[] channels = new int[1];
        ByteBuffer buffer = stbi_load(filename, width, height, channels, 4);

        this.id = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, this.id);
        glTexImage2D(GL_TEXTURE_2D, 0, channelsToFormat(channels[0]), width[0], height[0], 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glBindTexture(GL_TEXTURE_2D, 0);

        stbi_image_free(buffer);
    }

    private static int channelsToFormat(int channels) {
        switch (channels) {
            case 1: return GL_R;
            case 2: return GL_RG;
            case 3: return GL_RGB;
            case 4: return GL_RGBA;
        }

        throw new RuntimeException("Unknown format for " + channels + " channels");
    }

    @Override
    public void close() throws Exception {
        glDeleteTextures(this.id);
    }

    public void bind(int binding) {
        glActiveTexture(GL_TEXTURE0 + binding);
        glBindTexture(GL_TEXTURE_2D, this.id);
    }

}
