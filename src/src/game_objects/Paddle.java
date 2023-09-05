package src.game_objects;

import danogl.GameObject;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;

public class Paddle extends GameObject {

    private static final float MOVEMENT_SPEED = 300;
    private final UserInputListener inputListener;
    private Vector2 windowDimensions;
    private int minDistFromEdge;

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
    public Paddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, UserInputListener inputListener, Vector2 windowDimensions, int minDistFromEdge) {
        super(topLeftCorner, dimensions, renderable);
        this.inputListener = inputListener;
        this.windowDimensions = windowDimensions;
        this.minDistFromEdge = minDistFromEdge;
    }

    /**
     *This method is overwritten from GameObject. If right and/or left key is recognised as pressed by the
     * input listener, it moves the paddle, and check that it doesn't move past the borders.
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
        Vector2 movementDirection = Vector2.ZERO;

        if (this.getCenter().x() > windowDimensions.x() - minDistFromEdge){
            if(inputListener.isKeyPressed(KeyEvent.VK_LEFT)){
                movementDirection = movementDirection.add(Vector2.LEFT);
            }
        }
        else if (this.getCenter().x() < minDistFromEdge){
            if (inputListener.isKeyPressed(KeyEvent.VK_RIGHT)){
                movementDirection = movementDirection.add(Vector2.RIGHT);
            }
        }
        else if (inputListener.isKeyPressed(KeyEvent.VK_LEFT)){
            movementDirection = movementDirection.add(Vector2.LEFT);
        }
        else if (inputListener.isKeyPressed(KeyEvent.VK_RIGHT)){
            movementDirection = movementDirection.add(Vector2.RIGHT);
        }

        this.setVelocity(movementDirection.mult(MOVEMENT_SPEED));
    }
}
