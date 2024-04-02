package engine.math;

public class Transform {

    private vec3 position;
    private vec3 rotation;
    private float scale;

    private mat4 matrix;

    public Transform(vec3 position, vec3 rotation) {
        this.position = position;
        this.rotation = rotation;
        this.scale = 1.f;

        updateMatrix();
    }

    private void updateMatrix() {
        this.matrix = mat4.transform(position, rotation, new vec3(scale));
    }

    public vec3 getPosition() {
        return position;
    }

    public vec3 getRotation() {
        return rotation;
    }

    public float getScale() {
        return scale;
    }

    public mat4 getMatrix() {
        return matrix;
    }

    public void setPosition(vec3 position) {
        this.position = position;
        updateMatrix();
    }

    public void setRotation(vec3 rotation) {
        this.rotation = rotation;
        updateMatrix();
    }

    public void setScale(float scale) {
        this.scale = scale;
        updateMatrix();
    }
}
