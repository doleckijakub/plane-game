package engine.math;

import org.joml.*;

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

    private Vector3f direction(float x, float y, float z) {
        Quaternionf quaternion = new Quaternionf().rotateXYZ(rotation.x, rotation.y, rotation.z);
        return quaternion.transform(new Vector3f(x, y, z));
    }

    public Vector3f left() {
        return direction(-1, 0, 0);
    }

    public Vector3f right() {
        return direction(1, 0, 0);
    }

    public Vector3f down() {
        return direction(0, -1, 0);
    }

    public Vector3f up() {
        return direction(0, 1, 0);
    }

    public Vector3f forward() {
        return direction(0, 0, -1);
    }

    public Vector3f backward() {
        return direction(0, 0, 1);
    }

}
