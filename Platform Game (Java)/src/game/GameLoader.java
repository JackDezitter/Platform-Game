package game;

import org.jbox2d.common.Vec2;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Loads game state from a text file
 */
public class GameLoader {

    private String fileName;
    private Game game;
    private int level = 0;
    private List<String> enemyHealthList = null;
    private List<String> lifeCollectedList = null;
    private List<String> ammoCollectedList = null;

    /**
     * Loads the game from a save file
     *
     * @param fileName the name of the save file being loaded
     * @param game     the current game
     */
    public GameLoader(String fileName, Game game) {
        this.fileName = fileName;
        this.game = game;
    }

    /**
     * If there is no IO exception the state of the game is loaded from a file
     *
     * @throws IOException
     */
    public void loadGame() throws IOException {
        FileReader fr = null;
        BufferedReader reader = null;
        try {
            fr = new FileReader(fileName);
            reader = new BufferedReader(fr);
            String line = reader.readLine();

            //If the file is present but is empty the game resumes and outputs that there is no data to load
            if (line == null) {
                System.out.println("No data saved in the save file");
                Game.getGameLevel().start();
            }

            //If the file is not empty
            while (line != null) {
                //Splits the line into tokens where they are separated by ";"
                String[] tokens = line.split(";");

                //Retrieves the level as the token 0 and sets the game level to this
                int level = Integer.parseInt(tokens[0]);
                this.level = level;
                game.setLevel(level);
                //Tells the game it is being loaded from a file
                game.setLoaded(true);
                //Tells the game which level is being loaded from a file
                game.setLoadedLevel(level);

                //Takes token 5 as being the enemyAlive list of the level and transfers it into being a List from a string when each element has been separated by "," in the string
                List<String> enemyHealthList = Arrays.asList(tokens[5].split(","));
                this.enemyHealthList = enemyHealthList;
                //Sets the enemyAlive list in game to the loaded list
                game.setEnemyHealthList(enemyHealthList);

                //Takes token 6 as being the lifeCollected list of the level and transfers it into being a List from a string when each element has been separated by "," in the string
                List<String> lifeCollectedList = Arrays.asList(tokens[6].split(","));
                this.lifeCollectedList = lifeCollectedList;
                //Sets the LivedCollected list in game to the loaded list
                game.setLivesCollected(lifeCollectedList);

                //Takes token 7 as being the ammoCollected list of the level and transfers it into being a List from a string when each element has been separated by "," in the string
                List<String> ammoCollectedList = Arrays.asList(tokens[7].split(","));
                this.ammoCollectedList = ammoCollectedList;
                //Sets the AmmoCollected list in game to the loaded list
                game.setAmmoCollected(ammoCollectedList);

                //Refreshes the new Level to be created by game
                game.refreshLevel();

                //Loads the playable character's position from the save file and sets the character's position in the game to the loaded position
                float mainCharacterPositionx = Float.parseFloat(tokens[1]);
                float mainCharacterPositiony = Float.parseFloat(tokens[2]);
                GameLevel.getMainCharacter().setPosition(new Vec2(mainCharacterPositionx, mainCharacterPositiony));

                //Loads the life and ammo count from the save file and sets the playable character's life and ammo count to that load
                int lives = Integer.parseInt(tokens[3]);
                int ammo = Integer.parseInt(tokens[4]);
                GameLevel.getMainCharacter().setLifeCount(lives);
                GameLevel.getMainCharacter().setAmmoCount(ammo);


                line = reader.readLine();
            }
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (fr != null) {
                fr.close();
            }
        }
    }

}
