package game;

import city.cs.engine.World;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Creates an option menu with buttons for resuming, restarting, saving, loading and quiting the Game
 */
public class Menu implements ActionListener {

    private JFrame window;
    private JButton resumeButton;
    private JButton quitButton;
    private JButton restartButton;
    private JButton saveButton;
    private JButton loadButton;
    private Game game;

    /**
     * Creates a window titled "Menu" and places buttons with action listeners attached
     *
     * @param game current game
     */
    public Menu(Game game) {
        this.game = game;
        window = new JFrame("Menu");
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));

        //Resume Button
        resumeButton = new JButton("Resume");
        resumeButton.addActionListener(this);
        p.add(resumeButton);

        //Restart Level Button
        restartButton = new JButton("Restart");
        restartButton.addActionListener(this);
        p.add(restartButton);

        //Save current game button
        saveButton = new JButton("Save");
        saveButton.addActionListener(this);
        p.add(saveButton);

        //Load game from save file button
        loadButton = new JButton("Load");
        loadButton.addActionListener(this);
        p.add(loadButton);

        //Quit game button
        quitButton = new JButton("Quit");
        quitButton.addActionListener(this);
        p.add(quitButton);

        //Adds buttons to the window
        window.add(p, BorderLayout.CENTER);
        window.pack();
        window.setVisible(true);
    }

    /**
     * Action Listeners for all buttons
     *
     * @param buttonPressed which button was pressed
     */
    @Override
    public void actionPerformed(ActionEvent buttonPressed) {
        //If the resume button was pressed, the game starts from stop point and closes the menu window
        if (buttonPressed.getSource() == resumeButton) {
            System.out.println("Resume");
            Game.getGameLevel().start();
            window.dispose();
        }
        //If the restart button is pressed the game starts from the beginning of said level with the number of lives and ammo count that the player started said level with
        //Once the level is restarted the menu window closes
        else if (buttonPressed.getSource() == restartButton) {
            game.refreshLevel();
            window.dispose();
        }
        //Saves the game to a text file stored in data
        else if (buttonPressed.getSource() == saveButton) {
            GameSaver sw = new GameSaver("data/Save.txt");
            try {
                sw.writeGameState(Game.getGameLevel());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //Loads the game from the Save text file in data, if the file does not exist game resumes and outputs to the user that there is no save file
        else if (buttonPressed.getSource() == loadButton) {
            GameLoader sr = new GameLoader("data/Save.txt", game);
            try {
                sr.loadGame();
                window.dispose();
            } catch (IOException e) {
                System.out.println("No save file detected");
                Game.getGameLevel().start();
                window.dispose();
            }

        }
        //Quit button, if pressed the game closes and the system stops
        else if (buttonPressed.getSource() == quitButton) {
            System.exit(0);
        }

    }
}
