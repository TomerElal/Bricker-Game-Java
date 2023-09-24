package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.util.Counter;

/**
 * This class represents a standard collision with a brick (the brick explodes and removed from the game)
 */
public class CollisionStrategy {
    protected GameObjectCollection gameObjects;

    /**
     *
     * @param gameObjects a collection of game objects that belongs the game in the current state
     */
    public CollisionStrategy(GameObjectCollection gameObjects) {
        this.gameObjects = gameObjects;
    }

    /**
     *
     * @param collidedObj - - the object that was collided (the brick)
     * @param colliderObj - - the object that collided with the brick (the ball).
     * @param bricksCounter - - a counter for counting the current number of bricks left in the game in
     *                      each frame
     */
    public void onCollision(GameObject collidedObj, GameObject colliderObj, Counter bricksCounter){
        if(bricksCounter != null){
            if(gameObjects.removeGameObject(collidedObj, Layer.STATIC_OBJECTS)){
                bricksCounter.decrement();
            }
        }
    }
}
