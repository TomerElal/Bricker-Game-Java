package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.game_objects.ExtraLife;

/**
 * When breaking a brick with this behavior, a heart object will fall from the center
 * of the brick, which the puck must "pick up" in order to return disqualification.
 */
public class ExtraLifeStrategy extends CollisionStrategy{
    private final Renderable graphicLifeCounterImage;
    private final Counter livesCounter;
    private final Vector2 graphicHeartSize;
    private final Vector2 windowDimensions;

    /**
     *
     * @param gameObjects a collection of game objects that belongs the game in the current state
     * @param graphicLifeCounterImage the image that presents the extra life object
     * @param livesCounter counts the current lives left in the game in every frame
     * @param graphicHeartSize the size of the extra heart object
     * @param windowDimensions The dimensions of the game screen
     */
    public ExtraLifeStrategy(GameObjectCollection gameObjects, Renderable graphicLifeCounterImage,
                             Counter livesCounter, Vector2 graphicHeartSize,
                             Vector2 windowDimensions) {
        super(gameObjects);
        this.graphicLifeCounterImage = graphicLifeCounterImage;
        this.livesCounter = livesCounter;
        this.graphicHeartSize = graphicHeartSize;
        this.windowDimensions = windowDimensions;
    }

    /**
     *
     * @param collidedObj - - the object that was collided (the brick)
     * @param colliderObj - - the object that collided with the brick (the ball).
     * @param bricksCounter - - a counter for counting the current number of bricks left in the game in
     *                      each frame
     */
    @Override
    public void onCollision(GameObject collidedObj, GameObject colliderObj, Counter bricksCounter) {
        super.onCollision(collidedObj, colliderObj, bricksCounter);
        Vector2 brickCenter = colliderObj.getCenter();
        ExtraLife extraLife = new ExtraLife(Vector2.ZERO,
                graphicHeartSize, livesCounter, graphicLifeCounterImage, gameObjects,
                livesCounter.value(), graphicHeartSize, windowDimensions);
        extraLife.setCenter(brickCenter);
        extraLife.setVelocity(new Vector2(0, 100));
        gameObjects.addGameObject(extraLife, Layer.DEFAULT);
    }
}
