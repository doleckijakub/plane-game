package engine.api;

import engine.math.Transform;
import engine.math.vec3;

public abstract class Entity {

    private Transform transform;

    public Entity(vec3 position) {
        this.transform = new Transform(position, vec3.ZERO);
    }

    public abstract void update(float deltaTime);
    public abstract void render();

}
