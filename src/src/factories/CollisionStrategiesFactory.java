package src.factories;

import danogl.GameManager;
import danogl.collisions.GameObjectCollection;
import danogl.gui.*;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.brick_strategies.*;

/**
 * This class instantiates collision strategies
 */
public class CollisionStrategiesFactory {
    private static final float HEART_SIZE = 25;
    private final ImageReader imageReader;
    private final SoundReader soundReader;
    private final GameObjectCollection gameObjects;
    private final UserInputListener inputListener;
    private final Counter paddleCounter;
    private final Counter livesCounter;
    private final WindowController windowController;
    private final Vector2 windowDimensions;
    private final GameManager gameManager;

    /**
     * constructor
     * @param imageReader Contains a single method: readImage, which reads an image from disk.
     *                 See its documentation for help.
     * @param soundReader Contains a single method: readSound, which reads a wav file from
     *                    disk. See its documentation for help.
     * @param gameObjects a collection of game objects that belongs the game in the current state
     * @param userInputListener Contains a single method: isKeyPressed, which returns whether
     *                      a given key is currently pressed by the user or not. See its
     *                      documentation.
     * @param paddleCounter counts the current number of paddles in the game ( include the main paddle )
     * @param livesCounter counts the current lives left in the game in every frame
     * @param windowController Contains an array of helpful, self-explanatory methods
     *                         concerning the window.
     * @param gameManager an instance of the specific class that manages the game
     */

    public CollisionStrategiesFactory(ImageReader imageReader, SoundReader soundReader, GameObjectCollection
            gameObjects, UserInputListener userInputListener, Counter paddleCounter, Counter livesCounter,
                                      WindowController windowController, GameManager gameManager) {
        this.imageReader = imageReader;
        this.soundReader = soundReader;
        this.gameObjects = gameObjects;
        this.inputListener = userInputListener;
        this.paddleCounter = paddleCounter;
        this.livesCounter = livesCounter;
        this.windowController = windowController;
        this.windowDimensions = windowController.getWindowDimensions();
        this.gameManager = gameManager;
    }

    /**
     * The actual function that creates the collision strategies
     * @return an Array of collison strategies that each one can operate within a collision with a brick
     */
    public CollisionStrategy[] createBrickCollisonStrategies() {
        CollisionStrategy regularStrategy = new CollisionStrategy(gameObjects);
        Renderable extraBallImage = imageReader.readImage("assets/mockBall.png", true);
        Sound onCollisionSound = soundReader.readSound("assets/blop_cut_silenced.wav");
        CollisionStrategy extraBallsStrategy = new ExtraBallsStrategy(gameObjects, extraBallImage,
                onCollisionSound, 1);
        Renderable extraPaddleImage = imageReader.readImage("assets/botGood.png", false);

        CollisionStrategy extraPaddleStrategy = new ExtraPaddleStrategy(gameObjects, extraPaddleImage,
                inputListener, windowDimensions, paddleCounter);

        CollisionStrategy changeCameraStrategy = new ChangeCameraStrategy(gameObjects, gameManager,
                windowController);

        Renderable extraLifeImage = imageReader.readImage("assets/heart.png", true);
        CollisionStrategy extraLifeStrategy = new ExtraLifeStrategy(gameObjects, extraLifeImage,
                livesCounter, new Vector2(HEART_SIZE, HEART_SIZE), windowDimensions);
        return new CollisionStrategy[]{regularStrategy, extraBallsStrategy, extraPaddleStrategy,
                changeCameraStrategy, extraLifeStrategy};
    }
}
