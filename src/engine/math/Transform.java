package engine.math;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Transform {

    private Vector3f position;
    private Vector3f rotation;
    private float scale;

    private Matrix4f matrix;

    public Transform(Vector3f position, Vector3f rotation) {
        this.position = position;
        this.rotation = rotation;
        this.scale = 1.f;

        updateMatrix();
    }

    public void updateMatrix() {
        this.matrix = new Matrix4f().identity()
                .translate(position)
                .rotateXYZ(rotation)
                .scale(scale);
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public float getScale() {
        return scale;
    }

    public Matrix4f getMatrix() {
        return matrix;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
        updateMatrix();
    }

    public void setRotation(Vector3f rotation) {
        this.rotation = rotation;
        updateMatrix();
    }

    public void setScale(float scale) {
        this.scale = scale;
        updateMatrix();
    }

    public Vector3f forward() {
        float yaw = rotation.y;
        float pitch = rotation.x;

        float x = -Mathf.sin(yaw) * Mathf.cos(pitch);
        float y = Mathf.sin(pitch);
        float z = Mathf.cos(yaw) * Mathf.cos(pitch);

        return new Vector3f(x, y, z);
    }

    public Vector3f up() {
        float yaw = rotation.y;
        float pitch = rotation.x;
        float roll = rotation.x;

        float x = -Mathf.sin(roll) * Mathf.sin(yaw) + Mathf.cos(roll) * Mathf.sin(pitch) * Mathf.cos(yaw);
        float y = Mathf.cos(roll) * Mathf.cos(pitch);
        float z = Mathf.cos(roll) * Mathf.sin(yaw) + Mathf.sin(roll) * Mathf.sin(pitch) * Mathf.cos(yaw);

        return new Vector3f(x, y, z);
    }

}
