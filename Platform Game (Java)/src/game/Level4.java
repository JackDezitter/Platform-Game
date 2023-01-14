package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

import java.util.ArrayList;
import java.util.List;

/**
 * Creates Level 4, The final level only contains 1 door which has a new image of baby yoda trapped in a cage (Which when collided with means you have won the game)
 */
public class Level4 extends GameLevel {
    private ArrayList<Enemy> levelEnemies = new ArrayList<>();
    private ArrayList<BonusLife> levelBonusLives = new ArrayList<>();
    private ArrayList<Ammo> levelBonusAmmo = new ArrayList<>();

    /**
     * Populates the game with all attributes of level 4
     *
     * @param game current game
     */
    public void populate(Game game) {
        super.populate(game);

        List<String> enemyHealthList = game.getEnemyHealthList();
        List<String> livesCollectedList = game.getLivesCollected();
        List<String> ammoCollectedList = game.getAmmoCollected();

        //Sets background to a new image for the level
        MyView view = (MyView) game.getView();
        view.setBackgroundImage("data/Background/CastleHallBackground.png");

        //Changes image of the door
        getDoor().setDoorImage(new BodyImage("data/Object/TrappedYoda.png", 3));
        game.setLoaded(false);

    }

    /**
     * Sets the start position of the main character in this level
     *
     * @return start position of the main character in this level
     */
    @Override
    public Vec2 startPosition() {
        return new Vec2(-13, -14);
    }

    /**
     * Sets the position of the door in this level to move to the next level
     *
     * @return position of the doorway in the level
     */
    @Override
    public Vec2 doorPosition() {
        return new Vec2(0, -13);
    }

    /**
     * Retrieves the level number for this level
     *
     * @return 4 as this level is level 4
     */
    @Override
    public int getLevelNumber() {
        return 4;
    }

    /**
     * Returns the list of enemies present in this level
     *
     * @return list of enemies in this level
     */
    @Override
    public List<Enemy> getEnemyList() {
        return levelEnemies;
    }

    /**
     * Returns the list of ammo crates in this level
     *
     * @return list of collectible ammo crates
     */
    @Override
    public List<Ammo> getAmmoList() {
        return levelBonusAmmo;
    }

    /**
     * Returns the list of bonus lives present in this level
     *
     * @return list of collectible bonus lives in the level
     */
    @Override
    public List<BonusLife> getBonusLifeList() {
        return levelBonusLives;
    }

}
