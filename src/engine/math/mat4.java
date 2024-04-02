package engine.math;

import engine.api.Engine;

import java.util.Locale;

public class mat4 {

    public final float[][] matrix;

    public mat4() {
        this.matrix = new float[4][4];
        this.matrix[0][0] = 1.f;
        this.matrix[1][1] = 1.f;
        this.matrix[2][2] = 1.f;
        this.matrix[3][3] = 1.f;
    }

    public void set(int row, int col, float v) {
        matrix[row][col] = v;
    }

    public float get(int row, int col) {
        return matrix[row][col];
    }

    public static mat4 multiply(mat4 a, mat4 b) {
        mat4 result = new mat4();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                float sum = 0;
                for (int k = 0; k < 4; k++) {
                    sum += a.get(i, k) * b.get(k, j);
                }
                result.set(i, j, sum);
            }
        }

        return result;
    }

    public void translate(vec3 translation) {
        this.set(0, 3, this.get(0, 3) + translation.x);
        this.set(1, 3, this.get(1, 3) + translation.y);
        this.set(2, 3, this.get(2, 3) + translation.z);
    }

    private void rotate(float radians, vec3 axis) {
        float x = axis.x;
        float y = axis.y;
        float z = axis.z;

        float cosTheta = (float) Math.cos(radians);
        float sinTheta = (float) Math.sin(radians);
        float oneMinusCos = 1.0f - cosTheta;

        mat4 rotationMatrix = new mat4();
        rotationMatrix.set(0, 0, cosTheta + x * x * oneMinusCos);
        rotationMatrix.set(0, 1, x * y * oneMinusCos - z * sinTheta);
        rotationMatrix.set(0, 2, x * z * oneMinusCos + y * sinTheta);
        rotationMatrix.set(0, 3, 0.0f);

        rotationMatrix.set(1, 0, y * x * oneMinusCos + z * sinTheta);
        rotationMatrix.set(1, 1, cosTheta + y * y * oneMinusCos);
        rotationMatrix.set(1, 2, y * z * oneMinusCos - x * sinTheta);
        rotationMatrix.set(1, 3, 0.0f);

        rotationMatrix.set(2, 0, z * x * oneMinusCos - y * sinTheta);
        rotationMatrix.set(2, 1, z * y * oneMinusCos + x * sinTheta);
        rotationMatrix.set(2, 2, cosTheta + z * z * oneMinusCos);
        rotationMatrix.set(2, 3, 0.0f);

        rotationMatrix.set(3, 0, 0.0f);
        rotationMatrix.set(3, 1, 0.0f);
        rotationMatrix.set(3, 2, 0.0f);
        rotationMatrix.set(3, 3, 1.0f);

        mat4 result = mat4.multiply(this, rotationMatrix);

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                matrix[i][j] = result.get(i, j);
            }
        }
    }

    public mat4 scale(vec3 scale) {
        matrix[0][0] *= scale.x;
        matrix[1][1] *= scale.y;
        matrix[2][2] *= scale.z;
        return this;
    }

    public static mat4 projection(float fov, float near, float far) {
        float aspect = (float) Engine.getWindow().getWidth() / (float) Engine.getWindow().getHeight();
        float scaleY = (float) ((1.f / Math.tan(Math.toRadians(fov / 2.f))) * aspect);
        float scaleX = scaleY / aspect;
        float frustumLength = far - near;

        mat4 result = new mat4();
        result.set(0, 0, scaleX);
        result.set(1, 1, scaleY);
        result.set(2, 2, -((far + near) / frustumLength));
        result.set(3, 2, -1);
        result.set(2, 3, -((2 * far * near) / frustumLength));
        result.set(3, 3, 0);
        return result;
    }

    public static mat4 view(vec3 eye, vec3 rotation) {
        mat4 result = new mat4();
        result.translate(eye.times(-1));
        result.rotate((float) Math.toRadians(rotation.x), new vec3(1, 0, 0));
        result.rotate((float) Math.toRadians(rotation.y), new vec3(0, 1, 0));
        result.rotate((float) Math.toRadians(rotation.z), new vec3(0, 0, 1));
        return result;
    }

    public static mat4 transform(vec3 translation, vec3 rotation, vec3 scale) {
        mat4 result = new mat4();
        result.translate(translation);
        result.rotate((float) Math.toRadians(rotation.x), new vec3(1, 0, 0));
        result.rotate((float) Math.toRadians(rotation.y), new vec3(0, 1, 0));
        result.rotate((float) Math.toRadians(rotation.z), new vec3(0, 0, 1));
        result.scale(scale);
        return result;
    }

    public float[] toArray() {
        float[] array = new float[16];
        int index = 0;
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                array[index++] = matrix[row][col];
            }
        }
        return array;
    }

    @Override
    public String toString() {
        return String.format(
                Locale.ENGLISH,
                "[" + ("\n\t" + "% .3f ".repeat(4)).repeat(4) + "\n]",
                matrix[0][0], matrix[0][1], matrix[0][2], matrix[0][3],
                matrix[1][0], matrix[1][1], matrix[1][2], matrix[1][3],
                matrix[2][0], matrix[2][1], matrix[2][2], matrix[2][3],
                matrix[3][0], matrix[3][1], matrix[3][2], matrix[3][3]);
    }

}
