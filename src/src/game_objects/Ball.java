package src.game_objects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class Ball extends GameObject {
    private final Sound collisionSound;
    private int collisionCount;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner  Position of the object, in window coordinates (pixels).
     *                       Note that (0,0) is the top-left corner of the window.
     * @param dimensions     Width and height in window coordinates.
     * @param renderable     The renderable representing the object. Can be null, in which case
     *                       the GameObject will not be rendered.
     * @param collisionSound the sound file object of the ball's collision.
     */
    public Ball(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, Sound collisionSound) {
        super(topLeftCorner, dimensions, renderable);
        this.collisionSound = collisionSound;
        this.collisionCount = 0;
    }

    /**
     * @param other     The GameObject with which a collision occurred.
     * @param collision Information regarding this collision.
     *                  A reasonable elastic behavior can be achieved with:
     *                  setVelocity(getVelocity().flipped(collision.getNormal()));
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        collisionCount++;
        collisionCount %= 5;
        Vector2 newVelocity = this.getVelocity().flipped(collision.getNormal());
        this.setVelocity(newVelocity);
        collisionSound.play();
    }

    /**
     * The purpose of the function is to allow the camera change strategy class to be in control of the number
     * of collisions made by the main ball in the game from the moment the strategy was activated.
     * @return the current number of collisions (modulo 5) that the particular ball performed
     */
    public int getCollisionCount() {
        return collisionCount;
    }
}
