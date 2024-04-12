package engine.math;

import org.joml.Quaternionf;
import org.joml.Vector3f;

public class Mathf {

    public static final float PI = (float) Math.PI;

    public static float lerp(float a, float b, float t) {
        return a + (b - a) * t;
    }

    public static float pow(float a, float b) {
        return (float) Math.pow(a, b);
    }

    public static float sin(float a) {
        return (float) Math.sin(a);
    }

    public static float cos(float a) {
        return (float) Math.cos(a);
    }

    public static float asin(float a) {
        return (float) Math.asin(a);
    }

    public static float acos(float a) {
        return (float) Math.acos(a);
    }

    public static float atan2(float z, float x) {
        return (float) Math.atan2(z, x);
    }

    public static float toRadians(float a) {
        return (float) Math.toRadians(a);
    }

    public static float sqrt(float v) {
        return (float) Math.sqrt(v);
    }

    public static Quaternionf fromToRotation(Vector3f fromDirection, Vector3f toDirection) {
        fromDirection.normalize();
        toDirection.normalize();

        float dot = fromDirection.dot(toDirection);
        float angle = (float) Math.acos(dot);

        Vector3f axis = new Vector3f();
        fromDirection.cross(toDirection, axis).normalize();

        Quaternionf quaternion = new Quaternionf();
        quaternion.setAngleAxis(angle, axis.x, axis.y, axis.z);

        return quaternion;
    }

}
