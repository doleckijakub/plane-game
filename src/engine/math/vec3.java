package engine.math;

import java.util.Locale;

public class vec3 {

<<<<<<< HEAD
    public static final vec3 UP = new vec3(0, 1, 0);
=======
    public static final vec3 ZERO = new vec3(0, 0, 0);
    public static final vec3 UP = new vec3(0, 1, 0);
    public static final vec3 DOWN = new vec3(0, -1, 0);
    public static final vec3 LEFT = new vec3(-1, 0, 0);
    public static final vec3 RIGHT = new vec3(1, 0, 0);
    public static final vec3 FORWARD = new vec3(0, 0, 1);
    public static final vec3 BACK = new vec3(0, 0, -1);
>>>>>>> b07eb8b (Some updates idk really)

    public float x, y, z;

    public vec3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public vec3(float v) {
        this(v, v, v);
    }

    public static vec3 random() {
        return new vec3((float) Math.random(), (float) Math.random(), (float) Math.random());
    }

    public static vec3 cross(vec3 a, vec3 b) {
        float x = a.y * b.z - a.z * b.y;
        float y = a.z * b.x - a.x * b.z;
        float z = a.x * b.y - a.y * b.x;
        return new vec3(x, y, z);
    }

    public vec3 times(float a) {
        return new vec3(x * a, y * a, z * a);
    }

    public vec3 plus(vec3 a) {
        return new vec3(x + a.x, y + a.y, z + a.z);
    }

    public vec3 minus(vec3 a) {
        return new vec3(x - a.x, y - a.y, z - a.z);
    }

    public float lengthSquared() {
        return x * x + y * y + z * z;
    }

    public float length() {
        return (float) Math.sqrt(lengthSquared());
    }

    public vec3 normalized() {
        return this.times(1.f / this.length());
    }

    public static float dot(vec3 a, vec3 b) {
        return a.x * b.x + a.y * b.y + a.z * b.z;
    }

    @Override
    public String toString() {
        return String.format(
                Locale.ENGLISH,
                "[ % .3f % .3f % .3f ]",
                x, y, z);
    }

}
