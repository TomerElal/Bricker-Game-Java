package src.game_objects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

/**
 * an object of extra paddle in the game which will may appear at colliding a brick
 */
public class ExtraPaddle extends Paddle {

    private int collisionsCounter;
    private final GameObjectCollection gameObjects;
    private final Counter counter;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner    Position of the object, in window coordinates (pixels).
     *                         Note that (0,0) is the top-left corner of the window.
     * @param dimensions       Width and height in window coordinates.
     * @param renderable       The renderable representing the object. Can be null, in which case
     *                         the GameObject will not be rendered.
     * @param inputListener Contains a single method: isKeyPressed, which returns whether
     *                      a given key is currently pressed by the user or not. See its
     *                      documentation.
     * @param windowDimensions The dimensions of the game screen
     * @param minDistFromEdge the minimum distance of the paddle from the game borders
     */
    public ExtraPaddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                       UserInputListener inputListener, Vector2 windowDimensions, int minDistFromEdge,
                       GameObjectCollection gameObjects, Counter counter) {
        super(topLeftCorner, dimensions, renderable, inputListener, windowDimensions, minDistFromEdge);
        this.gameObjects = gameObjects;
        this.counter = counter;
        collisionsCounter = 0;
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
        collisionsCounter++;
        if (collisionsCounter > 3){
            if(gameObjects.removeGameObject(this, Layer.STATIC_OBJECTS)){
                counter.decrement();
            }
        }
    }
}
