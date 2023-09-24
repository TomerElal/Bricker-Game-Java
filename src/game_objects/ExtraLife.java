package src.game_objects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

/**
 * an object of extra life in the game which will may appear at colliding a brick
 */
public class ExtraLife extends GraphicLifeCounter {
    private final Renderable renderable;
    private final Vector2 graphicHeartSize;
    private final Vector2 windowDimensions;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param counter       the counter which holds current lives count
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     * @param gameObjects   the collection of all game objects currently in the game
     * @param numOfLives    - number of current lives
     */
    public ExtraLife(Vector2 topLeftCorner, Vector2 dimensions, Counter counter, Renderable renderable,
                     GameObjectCollection gameObjects, int numOfLives, Vector2 graphicHeartSize,
                     Vector2 windowDimensions) {
        super(topLeftCorner, dimensions, counter, renderable, gameObjects, numOfLives);
        this.renderable = renderable;
        this.graphicHeartSize = graphicHeartSize;
        this.windowDimensions = windowDimensions;
    }

    /**
     *
     * @param deltaTime The time elapsed, in seconds, since the last frame. Can
     *                  be used to determine a new position/velocity by multiplying
     *                  this delta with the velocity/acceleration respectively
     *                  and adding to the position/velocity:
     *                  velocity += deltaTime*acceleration
     *                  pos += deltaTime*velocity
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        float extraLifeHeight = this.getCenter().y();
        if(extraLifeHeight > windowDimensions.y()){
            gameObjects.removeGameObject(this, Layer.DEFAULT);
        }
    }

    /**
     *
     * @param other The other GameObject.
     * @return true if the collided object was exactly the main paddle (and not the extra paddle)
     */
    @Override
    public boolean shouldCollideWith(GameObject other) {
        return other instanceof Paddle && (!(other instanceof ExtraPaddle));
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
        gameObjects.removeGameObject(this, Layer.DEFAULT);
        if (counter.value() <= 3){
            GraphicLifeCounter extraGraphicLifeCounter = new GraphicLifeCounter(Vector2.ZERO, graphicHeartSize,
                    counter, renderable, gameObjects, numOfLives + 1);
            extraGraphicLifeCounter.setCenter(new Vector2(16 + graphicHeartSize.x() * counter.value(),
                    windowDimensions.y() - 16));
            counter.increment();
            gameObjects.addGameObject(extraGraphicLifeCounter, Layer.UI);
        }
    }
}
