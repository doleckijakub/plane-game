package engine;

import engine.math.Transform;
import org.joml.Vector3f;

public abstract class Entity {

    protected Transform transform;

    public Entity(Vector3f position, Vector3f rotation) {
        this.transform = new Transform(position, rotation);
    }

    public abstract void update(float deltaTime);
    public abstract void render();

    public Transform getTransform() {
        return transform;
    }
}
