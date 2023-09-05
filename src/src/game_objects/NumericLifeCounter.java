package src.game_objects;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.awt.*;

/**
 * This class inherits from GameObject, and describes the textual disqualification counter in the game.
 */
public class NumericLifeCounter extends GameObject {
    private final Counter livesCounter;
    private GameObjectCollection gameObjectsCollection;

    /**
     *
     * @param livesCounter  The counter of how many lives are left right now.
     * @param topLeftCorner the top left corner of the position of the text object
     * @param dimensions the size of the text object
     * @param gameObjectCollection - - the collection of all game objects currently in the game
     */
    public NumericLifeCounter(Counter livesCounter, Vector2 topLeftCorner, Vector2 dimensions, GameObjectCollection gameObjectCollection) {
        super(topLeftCorner, dimensions, new TextRenderable("", "algerian"));
        this.livesCounter = livesCounter;
        this.gameObjectsCollection = gameObjectCollection;
    }

    /**
     * In case the player is disqualified, this function updates the textual disqualification counter
     * according to the amount of disqualifications the player has accumulated so far
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
        TextRenderable textRenderable = (TextRenderable) renderer().getRenderable();
        int counter = livesCounter.value();
        switch (counter) {
            case 4:
                textRenderable.setString("Four lives left");
                textRenderable.setColor(Color.CYAN);
                setCenter(new Vector2(520, 480));
                break;
            case 3:
                textRenderable.setString("Three lives left");
                textRenderable.setColor(Color.GREEN);
                break;
            case 2:
                textRenderable.setString("Two lives left");
                textRenderable.setColor(Color.YELLOW);
                setCenter(new Vector2(530, 480));
                break;
            case 1:
                textRenderable.setString("One life left");
                textRenderable.setColor(Color.RED);
                setCenter(new Vector2(550, 480));
                break;
        }

    }
}