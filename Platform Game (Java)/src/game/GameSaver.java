package game;

import org.jbox2d.common.Vec2;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Saves game state to file  such as level, player position
 */
public class GameSaver {

    private String fileName;

    /**
     * Loads the name of the file to be saved
     *
     * @param fileName name of the save file
     */
    public GameSaver(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Take the Game level and saves attributes of it to a text file
     *
     * @param gameLevel current game level
     * @throws IOException
     */
    public void writeGameState(GameLevel gameLevel) throws IOException {
        FileWriter writer = null;
        //Retrieves the lists of enemies, bonus lives and ammo crates present in the gameLevel
        List<Enemy> enemyList = gameLevel.getEnemyList();
        List<BonusLife> lifeList = gameLevel.getBonusLifeList();
        List<Ammo> ammoList = gameLevel.getAmmoList();
        try {
            //Overwrites the save file
            writer = new FileWriter(fileName, false);
            //Writes the game level, main character's position, main character's life count and ammo count to the save file
            writer.write(gameLevel.getLevelNumber() + ";" + GameLevel.getMainCharacter().getPosition().x + ";" + GameLevel.getMainCharacter().getPosition().y + ";" + GameLevel.getMainCharacter().getLifeCount()
                    + ";" + GameLevel.getMainCharacter().getAmmoCount() + ";");
            //Writes the health state of all enemies present in the enemy list separated by ","
            for (int counter = 0; counter <= enemyList.size() - 1; counter++) {
                writer.write(enemyList.get(counter).getHealth() + ",");
            }
            //If the enemyList is empty null is written where the list would be saved so when the game is loaded all tokens are in the correct position
            if (enemyList.size() == 0) {
                writer.write("null" + ",");
            }
            writer.write(";");
            //Writes the collected state of all Bonus lives present in the lifeList separated by ","
            for (int counter = 0; counter <= lifeList.size() - 1; counter++) {
                writer.write(lifeList.get(counter).getCollected() + ",");
            }
            //If the lifeList is empty null is written where the list would be saved so when the game is loaded all tokens are in the correct position
            if (lifeList.size() == 0) {
                writer.write("null" + ",");
            }
            writer.write(";");
            //Writes the collected state of all ammo crates present in the ammoList separated by ","
            for (int counter = 0; counter <= ammoList.size() - 1; counter++) {
                writer.write(ammoList.get(counter).getCollected() + ",");
            }
            //If the ammoList is empty null is written where the list would be saved so when the game is loaded all tokens are in the correct position
            if (ammoList.size() == 0) {
                writer.write("null" + ",");
            }


        }
        //When there are no lines left to write the writer closes
        finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

}
