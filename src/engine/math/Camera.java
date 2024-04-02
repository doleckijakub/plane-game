package engine.math;

public class Camera {

    private vec3 position;
    private vec3 rotation;
    private float fov, near, far;

    private mat4 projection, view;

    public Camera(vec3 position, vec3 rotation, float fov, float near, float far) {
        this.position = position;
        this.rotation = rotation;
        this.fov = fov;
        this.near = near;
        this.far = far;

        updateProjection();
        updateView();
    }

    private void updateProjection() {
        this.projection = mat4.projection(fov, near, far);
    }

    private void updateView() {
        this.view = mat4.view(position, rotation);
    }

    public vec3 getPosition() {
        return position;
    }

    public vec3 getRotation() {
        return rotation;
    }

    public float getFov() {
        return fov;
    }

    public float getNear() {
        return near;
    }

    public float getFar() {
        return far;
    }

    public mat4 getProjection() {
        return projection;
    }

    public mat4 getView() {
        return view;
    }

    public void setPosition(vec3 position) {
        this.position = position;
        updateView();
    }

    public void setRotation(vec3 rotation) {
        this.rotation = rotation;
        updateView();
    }

    public void setFov(float fov) {
        this.fov = fov;
        updateProjection();
    }

    public void setNear(float near) {
        this.near = near;
        updateProjection();
    }

    public void setFar(float far) {
        this.far = far;
        updateProjection();
    }
}
