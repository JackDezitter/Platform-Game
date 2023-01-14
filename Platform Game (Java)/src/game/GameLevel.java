package game;

import city.cs.engine.*;
import com.sun.nio.file.SensitivityWatchEventModifier;
import org.jbox2d.common.Vec2;

import java.util.List;

/**
 * Main level class, populates level with the floor and outer walls and creates abstract functions
 */
public abstract class GameLevel extends World {
    private static Avatar mainCharacter;
    private static Doorway door;

    /**
     * Populates the game level with the ground platform, left and right boundary walls, a new main character and exit door
     * Adds collision and step listeners to the new main character body
     * Sets the door and main character position to that given by the specific game level
     *
     * @param game current game
     */
    public void populate(Game game) {

        //Creates Ground
        Shape groundShape = new BoxShape(20, 0.5f);
        Body ground = new StaticBody(this, groundShape);
        ground.setPosition(new Vec2(0, -15));

        //Prevents user going outside of the world
        //Left Boundary
        Shape bound = new BoxShape(0.5f, 20);
        Body boundLeft = new StaticBody(this, bound);
        boundLeft.setPosition(new Vec2(-19, 0));

        //Right Boundary
        Body boundRight = new StaticBody(this, bound);
        boundRight.setPosition(new Vec2(19, 0));

        //Creates new main character of type Avatar
        mainCharacter = new Avatar(this);
        mainCharacter.setPosition(startPosition());
        //Adds step listener and collision listener to the new main character
        this.addStepListener(new FlipCharacter(mainCharacter));
        mainCharacter.addCollisionListener(new Damage());

        //Creates Doorway to proceed to the next level
        door = new Doorway(this, game);
        //Sets the door to a position specified by the game level
        door.setPosition(doorPosition());

    }

    /**
     * Retrieves the current playable character in the game level
     *
     * @return mainCharacter
     */
    public static Avatar getMainCharacter() {
        return mainCharacter;
    }

    /**
     * Abstract method that sets the start position of the main character
     *
     * @return start position of main character
     */
    public abstract Vec2 startPosition();

    /**
     * Abstract method that sets the position of the doorway in the level
     *
     * @return position of the door in the level
     */
    public abstract Vec2 doorPosition();

    /**
     * Abstract method that sets the level number of that game level, used when saving the game so the correct level is loaded back from the file
     *
     * @return levelNumber
     */
    public abstract int getLevelNumber();

    /**
     * Abstract method that returns a list of all enemies within the level,
     * used to save which enemies are currently alive in the level so the correct game state is loaded when loading the game from a save file
     *
     * @return levelEnemies
     */
    public abstract List<Enemy> getEnemyList();

    /**
     * Abstract method that returns a list of all collectible ammo crates in the level,
     * used to save which ammo crates are currently collectible in the level so the correct game state is loaded when loading the game from a save file
     *
     * @return levelBonusAmmo
     */
    public abstract List<Ammo> getAmmoList();

    /**
     * Abstract method that returns all the collectible bonus lives in the level,
     * used to save which bonus Lives are currently collectible in the level so the correct game state is loaded when loading the game from a save file
     *
     * @return levelBonusLives
     */
    public abstract List<BonusLife> getBonusLifeList();

    public static Doorway getDoor() {
        return door;
    }
}

