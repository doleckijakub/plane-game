package engine;

import engine.math.Color;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.io.IOException;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL20.*;

public class Shader implements AutoCloseable {

    private final int id;

    public Shader(String vertFilename, String fragFilename) {
        try {
            String vertCode = Files.readString(Path.of(vertFilename));
            String fragCode = Files.readString(Path.of(fragFilename));

            int vertShader = glCreateShader(GL_VERTEX_SHADER);
            glShaderSource(vertShader, vertCode);
            glCompileShader(vertShader);
            if (glGetShaderi(vertShader, GL_COMPILE_STATUS) != GL_TRUE) {
                throw new RuntimeException(glGetShaderInfoLog(vertShader));
            }

            int fragShader = glCreateShader(GL_FRAGMENT_SHADER);
            glShaderSource(fragShader, fragCode);
            glCompileShader(fragShader);
            if (glGetShaderi(fragShader, GL_COMPILE_STATUS) != GL_TRUE) {
                throw new RuntimeException(glGetShaderInfoLog(fragShader));
            }

            this.id = glCreateProgram();
            glAttachShader(this.id, vertShader);
            glAttachShader(this.id, fragShader);

            glLinkProgram(this.id);

            glDeleteShader(vertShader);
            glDeleteShader(fragShader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() throws Exception {
        glDeleteProgram(this.id);
    }

    public void bind() {
        glUseProgram(this.id);
    }

    private final Map<String, Integer> uniformLocations = new HashMap<>();

    private int getUniformLocation(String name) {
        if (uniformLocations.containsKey(name)) {
            return uniformLocations.get(name);
        } else {
            int location = glGetUniformLocation(id, name);
            uniformLocations.put(name, location);
            if (location == -1) {
                System.out.println("Warning: " + toString() + ": Uniform '" + name + "' not found");
            }
            return location;
        }
    }

    public void setUniform(String name, float v0) {
        bind();
        glUniform1f(getUniformLocation(name), v0);
    }

    public void setUniform(String name, Texture texture, int binding) {
        bind();
        texture.bind(binding);
        glUniform1i(getUniformLocation(name), binding);
    }

    public void setUniform(String name, Matrix4f matrix) {
        bind();
        float[] array = new float[16];
        matrix.get(array);
        glUniformMatrix4fv(getUniformLocation(name), false, array);
    }

    public void setUniform(String name, Color color) {
        bind();
        glUniform3f(getUniformLocation(name), color.r, color.g, color.b);
    }

    public void setUniform(String name, Vector3f v) {
        bind();
        glUniform3f(getUniformLocation(name), v.x, v.y, v.z);
    }
}
