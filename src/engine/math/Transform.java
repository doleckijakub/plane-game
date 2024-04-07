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

    public vec3 forward() {
        float yaw = rotation.y;
        float pitch = rotation.x;

        float x = -Mathf.sin(yaw) * Mathf.cos(pitch);
        float y = Mathf.sin(pitch);
        float z = Mathf.cos(yaw) * Mathf.cos(pitch);

        return new vec3(x, y, z);
    }

    public vec3 up() {
        float yaw = rotation.y;
        float pitch = rotation.x;
        float roll = rotation.x;

        float x = -Mathf.sin(roll) * Mathf.sin(yaw) + Mathf.cos(roll) * Mathf.sin(pitch) * Mathf.cos(yaw);
        float y = Mathf.cos(roll) * Mathf.cos(pitch);
        float z = Mathf.cos(roll) * Mathf.sin(yaw) + Mathf.sin(roll) * Mathf.sin(pitch) * Mathf.cos(yaw);

        return new vec3(x, y, z);
    }

    public void rotateAround(vec3 position, vec3 up, float radians) {
        vec3 toPosition = this.position.minus(this.position);
        vec3 newToPosition = toPosition.rotate(up, radians);
        this.position = this.position.plus(newToPosition);
        updateMatrix();
    }

    public void lookAt(vec3 at, vec3 up) {
        vec3 newForward = at.minus(position).normalized();
        vec3 newRight = vec3.cross(up, newForward).normalized();
        vec3 newUp = vec3.cross(newForward, newRight).normalized();

        float yaw = Mathf.atan2(newForward.z, newForward.x);
        float pitch = Mathf.asin(-newForward.y);
        float roll = 0.0f;

        this.rotation = new vec3(pitch, yaw, roll);

        updateMatrix();
    }

}
