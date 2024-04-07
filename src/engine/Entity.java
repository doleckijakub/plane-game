package engine.api;

import engine.math.Transform;
import engine.math.vec3;

public abstract class Entity {

    protected Transform transform;

    public Entity(vec3 position, vec3 rotation) {
        this.transform = new Transform(position, rotation);
    }

    public abstract void update(float deltaTime);
    public abstract void render();

    public Transform getTransform() {
        return transform;
    }
}
