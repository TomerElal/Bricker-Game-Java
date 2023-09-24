package src.brick_strategies;

import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.WindowController;
import danogl.gui.rendering.Camera;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.game_objects.Ball;
import src.game_objects.ResetCamera;

/**
 * When breaking a brick holding this behavior,
 * the game camera will follow the ball until the ball hits 4 things.
 */
public class ChangeCameraStrategy extends CollisionStrategy{
    private final GameManager gameManager;
    private final WindowController windowController;

    /**
     *
     * @param gameObjects a collection of game objects that belongs the game in the current state
     * @param gameManager an instance of the specific class that manages the game
     * @param windowController Contains an array of helpful, self-explanatory methods
     *                         concerning the window.
     */
    public ChangeCameraStrategy(GameObjectCollection gameObjects, GameManager gameManager,
                                WindowController windowController) {
        super(gameObjects);
        this.gameManager = gameManager;
        this.windowController = windowController;
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
//        if(!colliderObj.getTag().equals("extraBall") && gameManager.getCamera() == null){
//            Ball ball = (Ball) colliderObj;
//            gameManager.setCamera(
//                    new Camera(
//                            ball, //object to follow
//                            Vector2.ZERO, //follow the center of the object
//                            windowController.getWindowDimensions().mult(1.2f), //widen the frame a bit
//                            windowController.getWindowDimensions() //share the window dimensions
//                    )
//            );
//            GameObject resetCamera = new ResetCamera(Vector2.ZERO, Vector2.ZERO, null, gameManager, ball,
//                    ball.getCollisionCount(), gameObjects);
//            gameObjects.addGameObject(resetCamera, Layer.FOREGROUND);
//        }
    }
}
