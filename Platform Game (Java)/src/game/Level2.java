package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

import java.util.ArrayList;
import java.util.List;

/**
 * Creates Level 2 and places all features into the world
 */
public class Level2 extends GameLevel {
    private ArrayList<Enemy> levelEnemies = new ArrayList<>();
    private ArrayList<BonusLife> levelBonusLives = new ArrayList<>();
    private ArrayList<Ammo> levelBonusAmmo = new ArrayList<>();

    /**
     * Populates the game with all attributes of level 2
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
        view.setBackgroundImage("data/Background/CastleBackground.gif");

        //Creates Platforms for the level
        Shape platformShape10m = new BoxShape(5, 0.25f);
        Body platform1 = new StaticBody(this, platformShape10m);
        platform1.setPosition(new Vec2(8, -10));

        Shape platformShape3m = new BoxShape(1.5f, 0.25f);
        Body platform2 = new StaticBody(this, platformShape3m);
        platform2.setPosition(new Vec2(16.5f, -4.8f));

        Shape platformShape5m = new BoxShape(2.5f, 0.25f);
        Body platform3 = new StaticBody(this, platformShape5m);
        platform3.setPosition(new Vec2(12.5f, 1));

        Shape platformShape20m = new BoxShape(10, 0.25f);
        Body platform4 = new StaticBody(this, platformShape20m);
        platform4.setPosition(new Vec2(-6, -2.5f));

        Body platform5 = new StaticBody(this, platformShape3m);
        platform5.setPosition(new Vec2(16.5f, 6));

        Body platform6 = new StaticBody(this, platformShape20m);
        platform6.setPosition(new Vec2(0, 10));

        //Creates a bonus life for the level, if the level was loaded from a save file it is checked if this life was collected on save, if true the life is set as collected and removed from the level
        BonusLife bonusLife = new BonusLife(this);
        bonusLife.setPosition(new Vec2(-15, -1.5f));
        if (game.getLoaded() == true && (game.getLoadedLevel() == 2) && (Boolean.valueOf(livesCollectedList.get(0)) == true)) {
            bonusLife.setCollected(true);
        }
        //Adds the life to the list of bonus lives in the level
        levelBonusLives.add(bonusLife);

        //Creates an ammo crate for the level, if the level was loaded from a save file it is checked if this ammo crate was collected on save, if true the ammo is set as collected and removed from the level
        Ammo ammoPickup = new Ammo(this);
        ammoPickup.setPosition(new Vec2(16.5f, -4));
        if (game.getLoaded() == true && (game.getLoadedLevel() == 2) && (Boolean.valueOf(ammoCollectedList.get(0)) == true)) {
            ammoPickup.setCollected(true);
        }
        //Adds the ammo crate to the list of ammo crates in the level
        levelBonusAmmo.add(ammoPickup);

        //Creates enemies of types Archer and Walker and places them in the world, each enemy is added to the array list of enemies in the level
        //If the level was loaded from a save file the enemies health count is set to what it was when the level was saved
        Enemy enemy1 = new Enemy(this, "Archer", 0, new Vec2(13, -13));
        if (game.getLoaded() == true && (game.getLoadedLevel() == 2)) {
            enemy1.setHealth(Integer.parseInt(game.getEnemyHealthList().get(0)));
        }
        levelEnemies.add(enemy1);
        Enemy enemy2 = new Enemy(this, "Archer", 0, new Vec2(-7.5f, -1));
        if (game.getLoaded() == true && (game.getLoadedLevel() == 2)) {
            enemy2.setHealth(Integer.parseInt(enemyHealthList.get(1)));
        }
        levelEnemies.add(enemy2);

        Enemy enemy3 = new Enemy(this, "Walker", 10, new Vec2(0, 11.5f));
        if (game.getLoaded() == true && (game.getLoadedLevel() == 2)) {
            enemy3.setHealth(Integer.parseInt(enemyHealthList.get(2)));
        }
        levelEnemies.add(enemy3);

        //Sets the game's loaded state to false
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
        return new Vec2(0, 11.75f);
    }

    /**
     * Retrieves the level number for this level
     *
     * @return 2 as this level is level 2
     */
    @Override
    public int getLevelNumber() {
        return 2;
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


