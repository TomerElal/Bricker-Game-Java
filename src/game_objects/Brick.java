package src.game_objects;

import src.brick_strategies.CollisionStrategy;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

public class Brick extends GameObject {

    private CollisionStrategy strategy;
    private Counter counter;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     * @param strategy the strategy that will be used when the brick breaks.
     * @param counter the current number of bricks that left in the game
     */
    public Brick(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, CollisionStrategy strategy, Counter counter) {

        super(topLeftCorner, dimensions, renderable);
        this.strategy = strategy;
        this.counter = counter;
    }

    /**
     *
     * @param other The GameObject with which a collision occurred.
     * @param collision Information regarding this collision.
     *                  A reasonable elastic behavior can be achieved with:
     *                  setVelocity(getVelocity().flipped(collision.getNormal()));
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        strategy.onCollision(this, other, counter);
    }
}
