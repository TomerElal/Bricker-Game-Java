package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.util.Counter;

import java.util.Random;

/**
 * A brick with this behavior will trigger a behavior from among the 5 possible behaviors
 * (including another layer of double behavior, and without
 * The "normal" behavior.
 */
public class DoubleStrategy extends CollisionStrategy {
    private final CollisionStrategy[] strategies;
    private final int maxBrickStrategies;
    int counter;

    /**
     *
     * @param gameObjects a collection of game objects that belongs the game in the current state
     * @param strategies an Array of collison strategies that each one can operate within a
     *                   collision with a brick
     * @param maxBrickStrategies a maximum number of collison behaviors that a brick will posses. (not
     *                           include the double and the standard behavior of removing the brick)
     */
    public DoubleStrategy(GameObjectCollection gameObjects, CollisionStrategy[] strategies,
                          int maxBrickStrategies) {
        super(gameObjects);
        this.strategies = strategies;
        this.maxBrickStrategies = maxBrickStrategies;
        this.counter = 0;
    }


    /**
     * This function will work recursively while inserting behaviors in bricks as long as it below the max
     * parameter of bricks strategies.
     * @param collidedObj - - the object that was collided (the brick)
     * @param colliderObj - - the object that collided with the brick (the ball).
     * @param bricksCounter - - a counter for counting the current number of bricks left in the game in
     *                      each frame
     */
    @Override
    public void onCollision(GameObject collidedObj, GameObject colliderObj, Counter bricksCounter) {
        if (counter == 0) {
            super.onCollision(collidedObj, colliderObj, bricksCounter);
        }
        executeOneStrategy(collidedObj, colliderObj);
        executeOneStrategy(collidedObj, colliderObj);
    }

    /**
     * This function is a helper of onCollision function and as long as the counter < maxBrickStrategies it
     * keeps generating a random strategy for the brick (assuming the double strategy was chosen)
     * @param collidedObj - - the object that was collided (the brick)
     * @param collidedObj - - the object that was collided (the brick)
     */
    private void executeOneStrategy(GameObject collidedObj, GameObject colliderObj) {
        if (counter < maxBrickStrategies){
            Random rand = new Random();
            int numOfStrategies = strategies.length + 1;
            int randNum = rand.nextInt(numOfStrategies);
            if (randNum == numOfStrategies - 1) {
                this.onCollision(collidedObj, colliderObj, null);
                return;
            }
            if (counter < maxBrickStrategies) {
                strategies[randNum].onCollision(collidedObj, colliderObj, null);
                counter++;
            }
        }
    }

}
