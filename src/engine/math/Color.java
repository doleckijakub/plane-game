package engine.math;

public class Color {

    public float r, g, b;

    public Color(float r, float g, float b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public Color(int r, int g, int b) {
        this(r / 255.f, g / 255.f, b / 255.f);
    }

    public Color(int color) {
        this((color >> 16) & 0xFF, (color >> 8) & 0xFF, color & 0xFF);
    }

    public static final Color RED   = new Color(0xFF0000);
    public static final Color GREEN = new Color(0x00FF00);
    public static final Color BLUE  = new Color(0x0000FF);
    public static final Color BLACK = new Color(0x000000);
    public static final Color CYAN  = new Color(0x00FFFF);

    public static Color random() {
        return new Color((float) Math.random(), (float) Math.random(), (float) Math.random());
    }
}
