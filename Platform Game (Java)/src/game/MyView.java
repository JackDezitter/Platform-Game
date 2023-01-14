package game;

import city.cs.engine.*;

import javax.swing.*;
import java.awt.*;

/**
 * View of the world with painted back ground set by the levels and fore ground showing how many lives the player has left and how much ammo they have
 */
public class MyView extends UserView {
    private Image background;
    private Image lifeIcon;
    private Image ammoIcon;
    private int x = 0;
    private int y = 0;
    private int lifeCounter = 0;

    /**
     * Creates the view of the current world with the size of the given parameters
     *
     * @param world  current world being viewed
     * @param width  width of view
     * @param height height of view
     */
    public MyView(World world, int width, int height) {
        super(world, width, height);
        //Creates initial background image and life and ammo icons
        background = new ImageIcon("data/Background/WoodsBackground.jpg").getImage();
        lifeIcon = new ImageIcon("data/Object/Heart.png").getImage();
        ammoIcon = new ImageIcon("data/Object/Ammo.png").getImage();


    }

    /**
     * Sets background for the view, used at the start of generating a level to set a new background image
     *
     * @param imagePath where the image of the new back ground is stored
     */
    public void setBackgroundImage(String imagePath) {
        this.background = new ImageIcon(imagePath).getImage();
    }

    /**
     * Paints the background image of the view to the selected image
     *
     * @param g graphics
     */
    @Override
    protected void paintBackground(Graphics2D g) {
        g.drawImage(background, 0, 0, this);
    }

    /**
     * Paints the foreground with the given aspects
     *
     * @param g graphics
     */
    @Override
    protected void paintForeground(Graphics2D g) {
        //Retrieves player's current ammo count
        int ammoCount = GameLevel.getMainCharacter().getAmmoCount();
        //Sets text colour of foreground to white
        g.setColor(Color.WHITE);

        //Retrieves players current life count
        int currentLifeCount = GameLevel.getMainCharacter().getLifeCount();

        //Draws the life icon for the number of lives the player has
        while (lifeCounter < currentLifeCount) {
            g.drawImage(lifeIcon, x, y, 40, 40, this);
            //Sets x position for next drawing
            x += 40;
            lifeCounter++;
        }
        lifeCounter = 0;
        x = 0;
        //Draws ammo icon
        g.drawImage(ammoIcon, 0, 40, 40, 40, this);
        //Writes ammo amount besides ammo Icon
        g.scale(1.5, 1.5);
        g.drawString(String.valueOf(ammoCount), 30, 45);
        if (Game.getGameLevel().getLevelNumber() == 4) {
            g.drawString("CONGRATULATION YOU SAVED ME", 130, 200);
            g.drawString("NOW LETS GET OUT OF HERE", 145, 215);
        }

    }

}

