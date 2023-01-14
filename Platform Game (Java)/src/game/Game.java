package game;

import city.cs.engine.UserView;

import javax.swing.*;
import java.util.List;

/**
 * Main Game class which initialises levels and stores key aspects towards loading new levels and levels from game files
 */
public class Game {
    //Initialise gameLevel
    private static GameLevel gameLevel;

    //User view
    private MyView view;

    private MovementController movementController;
    private MenuButton menuButton;
    private Shoot mouseClick;
    private int mainCharacterLife = 1;
    private int mainCharacterAmmo = 0;
    private static int level = 1;
    private static JFrame frame;
    private Boolean loaded = false;
    private int loadedLevel = 0;
    private List<String> enemyHealthList = null;
    private List<String> livesCollected = null;
    private List<String> ammoCollected = null;

    /**
     * Creates the initial Game at the start level, adds the view and all mouse and key listeners as well as step listeners
     */
    public Game() {
        //Makes the gameLevel
        gameLevel = new Level1();
        gameLevel.populate(this);

        //Creates view of game in 700 by 600 screen
        view = new MyView(gameLevel, 700, 600);

        // Development grid
        //view.setGridResolution(1);

        //displays the view in the frame
        frame = new JFrame("Star Wars in Time");
        //adds view to gameLevel
        frame.add(view);
        //Disables frame to be resized
        frame.setResizable(false);
        //fits window to the view size 500x500
        frame.pack();

        //displays window
        frame.setVisible(true);

        //If mouse is clicked the main character will fire a bullet in the direction they are facing
        mouseClick = new Shoot(gameLevel, GameLevel.getMainCharacter());
        view.addMouseListener(mouseClick);

        //Detects if key is pressed
        movementController = new MovementController(GameLevel.getMainCharacter());
        frame.addKeyListener(movementController);
        menuButton = new MenuButton(this);
        frame.addKeyListener(menuButton);

        //Developer View
        //JFrame debugView = new DebugViewer(gameLevel, 700,600);

        //Starts gameLevel
        gameLevel.start();
    }

    /**
     * Increments level and stores the current life and ammo counter of the playable character. Calls the new level to be generated in refreshLevel()
     */
    public void nextLevel() {
        level++;
        //Retrieves current player Life count
        mainCharacterLife = GameLevel.getMainCharacter().getLifeCount();
        //Retrieves current player ammo Count
        mainCharacterAmmo = GameLevel.getMainCharacter().getAmmoCount();

        refreshLevel();
    }

    /**
     * Generates the level based on what level is currently assigned in the game. Adds all listeners back into the game and creates a new playable character in this world and assigns it the life and ammo count stored in Game.
     * Used to restart a specific level or generate the next level of the game
     */
    public void refreshLevel() {
        //Stops the current gameLevel
        gameLevel.stop();
        //Sets new gameLevel to next level
        if (level == 1) {
            gameLevel = new Level1();
        } else if (level == 2) {
            gameLevel = new Level2();
        } else if (level == 3) {
            gameLevel = new Level3();
        } else if (level == 4) {
            gameLevel = new Level4();
        } else {
            System.exit(0);
        }
        //Populates Level
        gameLevel.populate(this);
        //Sets life count of new character to the same as before the level change
        GameLevel.getMainCharacter().setLifeCount(mainCharacterLife);
        //Sets ammo count of new character to the same as before
        GameLevel.getMainCharacter().setAmmoCount(mainCharacterAmmo);
        //Gives new character movement and shooting
        movementController.setMainCharacter(GameLevel.getMainCharacter());
        mouseClick.setMainCharacter(GameLevel.getMainCharacter());
        //sets gameLevel view
        view.setWorld(gameLevel);
        //JFrame debugView = new DebugViewer(gameLevel, 600, 500);
        //Starts new gameLevel/Level
        gameLevel.start();

    }

    /**
     * Gets the current view of the world, used to add new background elements for each level and add foreground elements.
     *
     * @return view
     */
    public UserView getView() {
        return view;
    }

    /**
     * #
     * Retrieves the curent GameLevel world
     *
     * @return gameLevel
     */
    public static GameLevel getGameLevel() {
        return gameLevel;
    }

    /**
     * Sets the current level to the given parameter
     *
     * @param level new level taken from the loaded save file
     */
    public void setLevel(int level) {
        Game.level = level;
    }

    /**
     * Sets the loaded variable to the given parameter, used when the user loads a game from a save file, the loaded parameter if true only enables uncollected and alive enemies from the save point to respawn in level generation.
     *
     * @param loaded new loaded state of the game
     */
    public void setLoaded(Boolean loaded) {
        this.loaded = loaded;
    }

    /**
     * Retrieves current loaded variable value
     *
     * @return loaded variable to tell the system if the current level is being loaded from a file.
     */
    public Boolean getLoaded() {
        return loaded;
    }

    /**
     * Retrieves which level is being loaded from the file, used to prevent the removing enemies and collectables from the incorrect level
     *
     * @return loadedLevel the level the file is saved from
     */
    public int getLoadedLevel() {
        return loadedLevel;
    }

    /**
     * Sets the loaded level variable to the saved level in the file when the game is loaded from a save file
     *
     * @param loadedLevel which level has been loaded
     */
    public void setLoadedLevel(int loadedLevel) {
        this.loadedLevel = loadedLevel;
    }

    /**
     * Sets a list of all the enemies in the saved level's health state.
     *
     * @param enemyHealthList list if health states loaded from the save file
     */
    public void setEnemyHealthList(List<String> enemyHealthList) {
        this.enemyHealthList = enemyHealthList;
    }

    /**
     * Returns the current list of the enemy health states, used form level generation of the saved level
     *
     * @return enemyAlive list of enemy health states.
     */
    public List<String> getEnemyHealthList() {
        return enemyHealthList;
    }

    /**
     * Returns a list of which ammo drops in the saved file have been collected, used when loading levels from save file
     *
     * @return ammoCollected List of which ammo has been collected
     */
    public List<String> getAmmoCollected() {
        return ammoCollected;
    }

    /**
     * Sets the current list of current ammo drops in a level to be the same as the one in the loaded save file
     *
     * @param ammoCollected List of which ammo has been collected in the saved level
     */
    public void setAmmoCollected(List<String> ammoCollected) {
        this.ammoCollected = ammoCollected;
    }

    /**
     * Returns a list of which Bonus lives in the saved file have been collected, used when loading levels from save file
     *
     * @return collected states of all lives in loaded level
     */
    public List<String> getLivesCollected() {
        return livesCollected;
    }

    /**
     * Sets the current list of current Bonus lives in a level to be the same as the one in the loaded save file
     *
     * @param livesCollected List of which bonus lives has been collected in the saved level
     */
    public void setLivesCollected(List<String> livesCollected) {
        this.livesCollected = livesCollected;
    }

    /**
     * Initialises game
     *
     * @param args
     */
    public static void main(String[] args) {
        //Calls game to start
        new Game();
        System.out.println();
    }

}
