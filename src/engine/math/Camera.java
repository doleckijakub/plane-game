package engine.math;

import engine.Engine;
import org.joml.*;

public class Camera {

    private static Camera mainCamera;

    public static void setMainCamera(Camera mainCamera) {
        Camera.mainCamera = mainCamera;
    }

    public static Camera getMainCamera() {
        return mainCamera;
    }

    private Vector3f position;
    private Vector3f rotation;
    private float fov; // degrees
    private float near, far;

    private Matrix4f projection, view;

    public Camera(Vector3f position, Vector3f rotation, float fov, float near, float far) {
        this.position = position;
        this.rotation = rotation;
        this.fov = fov;
        this.near = near;
        this.far = far;

        this.projection = new Matrix4f();
        this.view = new Matrix4f();

        updateProjection();
        updateView();
    }

    private void updateProjection() {
        this.projection.setPerspective(Mathf.toRadians(fov), Engine.getWindow().getAspectRatio(), near,  far);
    }

    public void updateView() {
        this.view = new Matrix4f().identity()
                .rotateXYZ(rotation)
                .translate(-position.x, -position.y, -position.z);
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getRotation() {
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

    public Matrix4f getProjection() {
        return projection;
    }

    public Matrix4f getView() {
        return view;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
        updateView();
    }

    public void setRotation(Vector3f rotation) {
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
