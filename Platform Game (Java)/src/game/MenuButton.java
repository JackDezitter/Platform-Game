package game;

import city.cs.engine.World;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

/**
 * Key listener for when the user presses ESC key a menu opens
 */
public class MenuButton extends KeyAdapter {
    game.Game game;

    /**
     * Creates a menu for the current game
     *
     * @param game current game
     */
    public MenuButton(Game game) {
        this.game = game;
    }

    /**
     * If ESC key is pressed a menu will appear, allowing people to restart, save and load levels or quit the game
     *
     * @param pressedKey the key that is pressed
     */
    @Override
    public void keyPressed(KeyEvent pressedKey) {
        int code = pressedKey.getKeyCode();

        if (code == KeyEvent.VK_ESCAPE) {
            Game.getGameLevel().stop();
            Menu menu = new Menu(game);
            System.out.println("Menu");

        }
    }
}
