package src.game_objects;

import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class ResetCamera extends GameObject {
    private GameManager gameManager;
    private Ball ball;
    private int collisionBeginNumber;
    private GameObjectCollection gameObjects;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     */
    public ResetCamera(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                       GameManager gameManager, Ball ball, int collisionBeginNumber,
                       GameObjectCollection gameObjects) {
        super(topLeftCorner, dimensions, renderable);
        this.gameManager = gameManager;
        this.ball = ball;
        this.collisionBeginNumber = collisionBeginNumber;
        this.gameObjects = gameObjects;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        int currentCollisionNumber = ball.getCollisionCount();
//        if(currentCollisionNumber == (collisionBeginNumber + 1)%4){
//            int k = 0;
//        }
        if (currentCollisionNumber == (collisionBeginNumber)) {
            gameManager.setCamera(null);
            gameObjects.removeGameObject(this, Layer.FOREGROUND);
        }
    }
}
