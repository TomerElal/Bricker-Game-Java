package src.game_objects;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

/**
 * This class inherits from GameObject, and describes the heart
 * (the amount of graphical disqualifications) in the game.
 */
public class GraphicLifeCounter extends GameObject {
    protected Counter counter;
    protected GameObjectCollection gameObjects;
    protected int numOfLives;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param counter  the counter which holds current lives count
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     * @param gameObjects  the collection of all game objects currently in the game
     * @param numOfLives - number of current lives
     */
    public GraphicLifeCounter(Vector2 topLeftCorner, Vector2 dimensions, Counter counter,
                              Renderable renderable, GameObjectCollection gameObjects, int numOfLives) {
        super(topLeftCorner, dimensions, renderable);
        this.counter = counter;
        this.gameObjects = gameObjects;
        this.numOfLives = numOfLives;
    }

    /**
     * This method is overwritten from GameObject It removes
     * hearts from the screen if there are more hearts than there are lives left
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
        if (numOfLives > counter.value()){
            gameObjects.removeGameObject(this, Layer.UI);
        }
    }
}
