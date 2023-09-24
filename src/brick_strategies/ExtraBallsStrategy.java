package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.BrickerGameManager;
import src.game_objects.Ball;

/**
 * When breaking a brick that has the behavior of additional balls, ~numOfExtraBalls~ white balls will be
 * created in the center of the brick's location (in its place). their size is one ~numOfExtraBalls~ of the
 * length of the brick and the initial direction of each of them will be a random diagonal. The balls can
 * Collide with the other pucks, the puck, the bricks in the walls as well as the central ball itself.
 */
public class ExtraBallsStrategy extends CollisionStrategy{
    private final Renderable extraBallImage;
    private final Sound onCollisionSound;
    private final int numOfExtraBalls;

    /**
     *
     * @param gameObjects a collection of game objects that belongs the game in the current state
     * @param extraBallImage the image that presents the extra ball object
     * @param onCollisionSound a sound to activate while the pucks collied with other objects
     * @param numOfExtraBalls the number of extra balls to create when collision happens on a brick with this
     *                        strategy
     */
    public ExtraBallsStrategy(GameObjectCollection gameObjects, Renderable extraBallImage,
                              Sound onCollisionSound, int numOfExtraBalls) {
        super(gameObjects);
        this.extraBallImage = extraBallImage;
        this.onCollisionSound = onCollisionSound;
        this.numOfExtraBalls = numOfExtraBalls;
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
        Vector2 brickDimensions = collidedObj.getDimensions();
        Vector2 brickCenter = colliderObj.getCenter();
        Vector2 extraBallDimension = new Vector2(brickDimensions.x()*(1/5F), brickDimensions.y());
        for (int i = 1-numOfExtraBalls; i < numOfExtraBalls; i++) {
            Ball extraBall = new Ball(Vector2.ZERO, extraBallDimension, extraBallImage, onCollisionSound);
            float extraBallXDimension = brickCenter.x() + extraBallDimension.x()*i;
            BrickerGameManager.setBallCenterAndVelocity(extraBall, new Vector2(extraBallXDimension,
                    brickCenter.y()));
            extraBall.setTag("extraBall");
            gameObjects.addGameObject(extraBall, Layer.DEFAULT);
        }

    }

}
