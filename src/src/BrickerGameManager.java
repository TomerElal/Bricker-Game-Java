package src;

import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.components.CoordinateSpace;
import src.brick_strategies.*;
import danogl.GameManager;
import danogl.GameObject;
import danogl.gui.*;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.factories.CollisionStrategiesFactory;
import src.game_objects.*;

import java.awt.event.KeyEvent;
import java.util.Random;

/**
 * This class manages the whole brick game
 */
public class BrickerGameManager extends GameManager {

    private static final float BALL_SPEED = 200;
    private static final float BRICK_LENGTH = 600 / 7F;
    private static final float BRICK_WIDTH = 160 / 8F;
    private static final float HEART_SIZE = 25;
    private static final int PADDLE_LENGTH = 200;
    private static final int WINDOW_LENGTH = 700;
    private static final int WINDOW_WIDTH = 500;
    private static final float BALL_LOCATION = 0.5F;
    private static final int TARGET_FRAME_PARAMETER = 100;
    private static final int PADDLE_VELOCITY = 600;
    private static final int PLAYER_LIFE_AMOUNT = 3;
    private static final int MAX_STRATEGIES_PER_BRICK = 3;
    private GameObject ball;
    private Vector2 windowDimensions;
    private WindowController windowController;
    private Counter livesCounter;
    private Counter bricksCounter;
    private UserInputListener inputListener;
    private Counter paddleCounter;
    private CollisionStrategiesFactory strategiesFactory;

    //constructor
    public BrickerGameManager(String windowTitle, Vector2 windowDimensions) {
        super(windowTitle, windowDimensions);
    }

    /**
     * This method initializes a new game. It creates all game objects,
     * sets their values and initial positions and allow the start of a game.
     *
     * @param imageReader      Contains a single method: readImage, which reads an image from disk.
     *                         See its documentation for help.
     * @param soundReader      Contains a single method: readSound, which reads a wav file from
     *                         disk. See its documentation for help.
     * @param inputListener    Contains a single method: isKeyPressed, which returns whether
     *                         a given key is currently pressed by the user or not. See its
     *                         documentation.
     * @param windowController Contains an array of helpful, self-explanatory methods
     *                         concerning the window.
     */
    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader,
                               UserInputListener inputListener, WindowController windowController) {

        this.inputListener = inputListener;
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        this.windowController = windowController;
        this.windowDimensions = windowController.getWindowDimensions();
        windowController.setTargetFramerate(TARGET_FRAME_PARAMETER);
        livesCounter = new Counter(PLAYER_LIFE_AMOUNT);
        paddleCounter = new Counter(1);
        this.strategiesFactory = new CollisionStrategiesFactory(imageReader, soundReader, gameObjects(),
                inputListener, paddleCounter, livesCounter, windowController, this);

        createCeiling();
        createBricks(imageReader);
        createWalls();
        createGraphicLifeCounter(imageReader);
        createNumericLifeCounter();
        createBall(imageReader, soundReader);
        createPaddle(imageReader, inputListener);
        createBackground(imageReader);

    }

    /**
     * @param deltaTime The time, in seconds, that passed since the last invocation
     *                  of this method (i.e., since the last frame). This is useful
     *                  for either accumulating the total time that passed since some
     *                  event, or for physics integration (i.e., multiply this by
     *                  the acceleration to get an estimate of the added velocity or
     *                  by the velocity to get an estimate of the difference in position).
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        String prompt;
        float ballHeight = ball.getCenter().y();
        if (ballHeight > windowDimensions.y()) {
            // means disqualification
            livesCounter.decrement();
            if (livesCounter.value() == 0) {
                prompt = "you lost!";
                endGame(prompt);
                return;
            }
            setBallCenterAndVelocity(ball, windowDimensions.mult(BALL_LOCATION));
        }
        if (bricksCounter.value() == 0 || inputListener.isKeyPressed(KeyEvent.VK_W)) {
            prompt = "you won!";
            endGame(prompt);
        }
    }

    /**
     * @param prompt A message to print to the user in case he won\lost the game.
     */
    private void endGame(String prompt) {
        prompt += " play again?";
        if (windowController.openYesNoDialog(prompt)) {
            windowController.resetGame();
        } else {
            windowController.closeWindow();
        }
    }

    /**
     * creates textual disqualification counter in the game.
     */
    private void createNumericLifeCounter() {
        GameObject numericLifeCounter = new NumericLifeCounter(livesCounter, Vector2.ZERO, new Vector2(30,
                20), gameObjects());
        numericLifeCounter.setCenter(new Vector2(windowDimensions.x() - 195, windowDimensions.y() - 20));
        gameObjects().addGameObject(numericLifeCounter, Layer.UI);
    }

    /**
     * @param imageReader Contains a single method: readImage, which reads an image from disk.
     *                    See its documentation for help.
     */
    private void createBackground(ImageReader imageReader) {
        Renderable backgroundImage = imageReader.readImage("assets/background.jpeg", false);
        GameObject background = new GameObject(Vector2.ZERO, windowDimensions, backgroundImage);
        background.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        gameObjects().addGameObject(background, Layer.BACKGROUND);
    }

    /**
     * This function creates three hearts (the amount of graphical disqualifications) in the game.
     *
     * @param imageReader Contains a single method: readImage, which reads an image from disk.
     *                    See its documentation for help.
     */
    private void createGraphicLifeCounter(ImageReader imageReader) {
        Renderable heartImage = imageReader.readImage("assets/heart.png", true);
        for (int i = 0; i < PLAYER_LIFE_AMOUNT; i++) {
            int numOfLives = i + 1;
            GameObject graphicLifeCounter = new GraphicLifeCounter(Vector2.ZERO, new Vector2(HEART_SIZE,
                    HEART_SIZE), livesCounter, heartImage, gameObjects(), numOfLives);
            graphicLifeCounter.setCenter(new Vector2(16 + HEART_SIZE * i, windowDimensions.y() - 16));
            gameObjects().addGameObject(graphicLifeCounter, Layer.UI);
        }
    }

    /**
     * creates an upper boundary for the game so the ball won't pass it.
     */
    private void createCeiling() {
        GameObject ceilingOrFloor = new GameObject(Vector2.ZERO, new Vector2(700, 20), null);
        ceilingOrFloor.setCenter(new Vector2(windowDimensions.x() / 2, 0));
        gameObjects().addGameObject(ceilingOrFloor, Layer.STATIC_OBJECTS);
    }

    /**
     * creates left and right boundary for the game so the ball won't pass it.
     */
    private void createWalls() {
        float[] xCoordinates = {3, windowDimensions.x() - 6};
        for (float xCoord : xCoordinates) {
            GameObject wall = new GameObject(Vector2.ZERO, new Vector2(25, windowDimensions.y()),
                    null);
            wall.setCenter(new Vector2(xCoord, windowDimensions.y() / 2));
            gameObjects().addGameObject(wall, Layer.STATIC_OBJECTS);
        }
    }

    /**
     * @param imageReader Contains a single method: readImage, which reads an image from disk.
     *                    See its documentation for help.
     */
    private void createBricks(ImageReader imageReader) {
        CollisionStrategy[] strategies = strategiesFactory.createBrickCollisonStrategies();
        Renderable brickImage = imageReader.readImage("assets/brick.png", false);
        bricksCounter = new Counter();
        int strategyLength = strategies.length, randNum;

        for (int col = 0; col < 7; col++) {
            float brickCoordX = 50 + BRICK_LENGTH * col;
            for (int row = 0; row < 8; row++) {
                bricksCounter.increment();
                randNum = getRandNum(strategyLength);
                float brickCoordY = 40 + BRICK_WIDTH * row + row * 5;
                GameObject brick;
                CollisionStrategy randCollisionStrategy;
                if (randNum == strategyLength) {
                    if (row%4 == 0){
                        randCollisionStrategy = new DoubleStrategy(gameObjects(), strategies,
                                MAX_STRATEGIES_PER_BRICK);
                    }else{
                        randCollisionStrategy = strategies[0];
                    }
                } else {
                    randCollisionStrategy = strategies[randNum];
                }
                brick = new Brick(new Vector2(brickCoordX, brickCoordY),
                        new Vector2(BRICK_LENGTH, BRICK_WIDTH), brickImage, randCollisionStrategy, bricksCounter);
                gameObjects().addGameObject(brick, Layer.STATIC_OBJECTS);
            }
        }
    }

    private static int getRandNum(int strategyLength) {
        Random rand = new Random();
        return rand.nextInt(strategyLength + 1);
    }


    /**
     * @param imageReader   Contains a single method: readImage, which reads an image from disk.
     *                      See its documentation for help.
     * @param inputListener Contains a single method: isKeyPressed, which returns whether
     *                      a given key is currently pressed by the user or not. See its
     *                      documentation.
     */
    private void createPaddle(ImageReader imageReader, UserInputListener inputListener) {
        Renderable paddleImage = imageReader.readImage("assets/paddle.png", false);
        GameObject paddle = new Paddle(Vector2.ZERO, new Vector2(PADDLE_LENGTH, 20),
                paddleImage, inputListener, windowDimensions, PADDLE_LENGTH / 2);
        paddle.setVelocity(new Vector2(PADDLE_VELOCITY, 0));
        paddle.setCenter(new Vector2(windowDimensions.x() / 2, windowDimensions.y() - 20));
        gameObjects().addGameObject(paddle, Layer.DEFAULT);
    }

    /**
     * @param imageReader Contains a single method: readImage, which reads an image from disk.
     *                    See its documentation for help.
     * @param soundReader Contains a single method: readSound, which reads a wav file from
     *                    disk. See its documentation for help.
     */
    private void createBall(ImageReader imageReader, SoundReader soundReader) {
        Renderable ballImage = imageReader.readImage("assets/ball.png", true);
        Sound collisonSound = soundReader.readSound("assets/blop_cut_silenced.wav");
        ball = new Ball(Vector2.ZERO, new Vector2(20, 20), ballImage, collisonSound);
        setBallCenterAndVelocity(ball, windowDimensions.mult(BALL_LOCATION));
        gameObjects().addGameObject(ball, Layer.DEFAULT);
    }

    /**
     * This function determines the initial location of the ball, and it's initial movement which will be
     * determined randomly.
     */
    public static void setBallCenterAndVelocity(GameObject ball, Vector2 ballCenter) {
        float ballVellX = BALL_SPEED;
        Random rand = new Random();
        if (rand.nextBoolean()) {
            ballVellX *= -1;
        }
        ball.setVelocity(new Vector2(ballVellX, BALL_SPEED));
        ball.setCenter(ballCenter);
    }

    /**
     * runs the game
     *
     * @param args
     */
    public static void main(String[] args) {
        new BrickerGameManager("Bricker", new Vector2(WINDOW_LENGTH, WINDOW_WIDTH)).run();
    }
}
