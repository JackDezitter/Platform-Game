package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

import java.util.ArrayList;
import java.util.List;

/**
 * Creates Level 1 and places all features into the world
 */
public class Level1 extends GameLevel {

    private ArrayList<Enemy> levelEnemies = new ArrayList<>();
    private ArrayList<BonusLife> levelBonusLives = new ArrayList<>();
    private ArrayList<Ammo> levelBonusAmmo = new ArrayList<>();

    /**
     * Populates the game with all attributes of level 1
     *
     * @param game current game
     */
    public void populate(Game game) {
        super.populate(game);

        //Retrieves lists of enemies, bonus lives and ammo crates from the loaded level if the game has been loaded from a level
        List<String> enemyHealthList = game.getEnemyHealthList();
        List<String> livesCollectedList = game.getLivesCollected();
        List<String> ammoCollectedList = game.getAmmoCollected();

        //Creates first platform of length 6m
        Shape platformShape6m = new BoxShape(3, 0.25f);
        Body platform1 = new StaticBody(this, platformShape6m);
        platform1.setPosition(new Vec2(-5, -9));

        //Creates 10m long platform
        Shape platformShape10m = new BoxShape(5, 0.25f);
        Body platform2 = new StaticBody(this, platformShape10m);
        platform2.setPosition(new Vec2(5, -5));

        //Creates small 3m platform
        Shape platform3m = new BoxShape(1.5f, 0.25f);
        Body platform3 = new StaticBody(this, platform3m);
        platform3.setPosition(new Vec2(-1.5f, -1));

        //Creates 10m long platform
        Body doorPlatform = new StaticBody(this, platformShape10m);
        doorPlatform.setPosition(new Vec2(8, 8));

        //Creates a 10m platform
        Body platform5 = new StaticBody(this, platformShape10m);
        platform5.setPosition(new Vec2(-10, 2));

        //Creates a 3m platform
        Body platform6 = new StaticBody(this, platform3m);
        platform6.setPosition(new Vec2(-1.5f, 6));

        //Creates small platform where bonus life will be generated
        Shape bonusPlatform = new BoxShape(1.5f, 0.25f);
        Body bonusLifePlatform1 = new StaticBody(this, bonusPlatform);
        bonusLifePlatform1.setPosition(new Vec2(-12.5f, -3));

        //Places bonus life on platform
        BonusLife bonusLife = new BonusLife(this);
        bonusLife.setPosition((new Vec2(-13, -2)));
        //Checks if the game has been loaded from a level, if the level loaded is the current level and if this life was saved has been collected
        if (game.getLoaded() == true && (game.getLoadedLevel() == 1) && (Boolean.valueOf(livesCollectedList.get(0)) == true)) {
            //If the loaded level is the current level and this life has been saved as being collected the life is set to being collected and is removed from the level
            bonusLife.setCollected(true);
        }
        //Adds the life to the array list of bonus lives in the level
        levelBonusLives.add(bonusLife);

        //Adds ammo crate to the level
        Ammo ammoPickup = new Ammo(this);
        ammoPickup.setPosition((new Vec2(13, -14f)));
        //Checks if the game has been loaded from a level, if the level loaded is the current level and if this ammo crate was saved has been collected
        if (game.getLoaded() == true && (game.getLoadedLevel() == 1) && (Boolean.valueOf(ammoCollectedList.get(0)) == true)) {
            //If the loaded level is the current level and this life has been saved as being collected the ammo crate is set to being collected and is removed from the level
            ammoPickup.setCollected(true);
        }
        //Adds the ammo crate to the array list of ammo crates in the level
        levelBonusAmmo.add(ammoPickup);


        //Spawns enemy of type walker
        Enemy enemy1 = new Enemy(this, "Walker", 8, new Vec2(7.5f, -13));
        if (game.getLoaded() == true && (game.getLoadedLevel() == 1)) {
            enemy1.setHealth(Integer.parseInt(enemyHealthList.get(0)));
        }
        levelEnemies.add(enemy1);
        //Spawns enemy of type walker
        Enemy enemy2 = new Enemy(this, "Walker", 3, new Vec2(-10, 3));
        if (game.getLoaded() == true && (game.getLoadedLevel() == 1)) {
            enemy2.setHealth(Integer.parseInt(enemyHealthList.get(1)));
        }
        levelEnemies.add(enemy2);
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
        return new Vec2(12, 9.7f);
    }

    /**
     * Retrieves the level number for this level
     *
     * @return 1 as this level is level 1
     */
    @Override
    public int getLevelNumber() {
        return 1;
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
