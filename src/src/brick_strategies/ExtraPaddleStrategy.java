package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.game_objects.ExtraPaddle;

/**
 * This class creates another paddle in the game (at most there will be two at any given time)
 */
public class ExtraPaddleStrategy extends CollisionStrategy{

    private static final int MAX_AMOUNT_OF_PADDLES = 2;
    private final Renderable extraPaddleImage;
    private final UserInputListener inputListener;
    private final Vector2 windowDimensions;
    private final Counter paddleCounter;

    /**
     *
     * @param gameObjects a collection of game objects that belongs the game in the current state
     * @param extraPaddleImage the image that presents the extra paddle object
     * @param inputListener Contains a single method: isKeyPressed, which returns whether
     *      *                      a given key is currently pressed by the user or not. See its
     *      *                      documentation.
     * @param windowDimensions The dimensions of the game screen
     * @param paddleCounter counts the current number of paddles in the game ( include the main paddle )
     */
    public ExtraPaddleStrategy(GameObjectCollection gameObjects, Renderable extraPaddleImage,
                               UserInputListener inputListener, Vector2 windowDimensions,
                               Counter paddleCounter) {
        super(gameObjects);
        this.extraPaddleImage = extraPaddleImage;
        this.inputListener = inputListener;
        this.windowDimensions = windowDimensions;
        this.paddleCounter = paddleCounter;
    }

    /**
     * Assuming there are no more paddles in the game than allowed, another paddle
     * will be created during the collision
     * @param collidedObj - - the object that was collided (the brick)
     * @param colliderObj - - the object that collided with the brick (the ball).
     * @param bricksCounter - - a counter for counting the current number of bricks left in the game in
     *                      each frame
     */
    @Override
    public void onCollision(GameObject collidedObj, GameObject colliderObj, Counter bricksCounter) {
        super.onCollision(collidedObj, colliderObj, bricksCounter);
        Vector2 brickDimensions = collidedObj.getDimensions();
        ExtraPaddle extraPaddle = new ExtraPaddle(Vector2.ZERO, brickDimensions, extraPaddleImage,
                inputListener, windowDimensions, (int) (brickDimensions.x() / 2), gameObjects, paddleCounter);
        extraPaddle.setCenter(colliderObj.getCenter());
        if(paddleCounter.value() < MAX_AMOUNT_OF_PADDLES){
            paddleCounter.increment();
            gameObjects.addGameObject(extraPaddle, Layer.STATIC_OBJECTS);
        }
    }
}
