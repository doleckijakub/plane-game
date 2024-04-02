package engine.math;

import java.util.Locale;

public class vec2 {

    public float x, y;

    public vec2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public vec2(float x) {
        this(x, x);
    }

    public static vec2 random() {
        return new vec2((float) Math.random(), (float) Math.random());
    }

    public vec2 times(float v) {
        return new vec2(x * v, y * v);
    }

    public vec2 plus(vec2 v) {
        return new vec2(x + v.x, y + v.y);
    }

    @Override
    public String toString() {
        return String.format(
                Locale.ENGLISH,
                "[ % .3f % .3f ]",
                x, y);
    }

}
