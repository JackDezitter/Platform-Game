package game;

import city.cs.engine.*;
import city.cs.engine.Shape;
import org.jbox2d.common.Vec2;

/**
 * Creates and contains all fields for the playable character
 */

//Avatar inherits from Walker
public class Avatar extends Walker {
    private int lifeCount = 1;
    private String direction = "right";
    private Vec2 spawnpoint;
    private boolean cooldown;
    private boolean alive = true;
    private int ammoCount = 0;

    //Creates hit box of Avatar
    private static Shape avatarShape =
            new PolygonShape(-0.276f, 1.1f, -0.551f, 0.41f, -0.581f, -1.11f, 0.704f, -1.11f, 0.699f, 0.405f, 0.414f, 1.095f);


    //Attaches Image to hit box
    private static BodyImage avatarImage =
            new BodyImage("data/Player/AvatarRight.png", 2.5f);

    /**
     * Places Main avatar in world param and adds the image to it
     *
     * @param world current world
     */
    public Avatar(World world) {
        super(world, avatarShape);
        addImage(avatarImage);
        this.setGravityScale(5);
    }

    /**
     * Sets the image of the character to the parameter image. Used so the character faces the direction they are moving in
     *
     * @param avatarImage given image to set to the character
     */
    public void setAvatarImage(BodyImage avatarImage) {
        this.removeAllImages();
        Avatar.avatarImage = avatarImage;
        this.addImage(avatarImage);
    }

    /**
     * Increments the current character's life count
     */
    public void incrementLife() {
        lifeCount++;
        System.out.println("You now have " + this.getLifeCount() + " lives.");
    }

    /**
     * Retrieves how many lives the character has got, used to display the number of lives on screen for the user, save the correct life count when the game is saved.
     *
     * @return lifeCount the current number of lives the character has
     */
    public int getLifeCount() {
        return lifeCount;
    }

    /**
     * Assigns the characters life count to the given count, used when resetting the level, moving to the next level to preserve life count on the last level and used when restoring the main character from a save file.
     *
     * @param lifeCount life count being assigned
     */
    public void setLifeCount(int lifeCount) {
        this.lifeCount = lifeCount;
    }

    /**
     * Decreases the life count of the main character, used when a collision is detected between the player and an npc enemy to injure the player
     * If the life count reaches 0 the main character is destroyed as the player has lost
     * Sets a damage cooldown of 0.5 seconds so the player has the ability to remove the character from the damage causing situation to prevent further damage
     */
    public void decreaseLife() {
        lifeCount--;
        System.out.println("Lives remaining:" + this.getLifeCount());
        if (lifeCount == 0) {
            System.out.println("GAME OVER!");
            this.alive = false;
            this.destroy();
            setAlive(false);
        }
        this.setCooldown(true);
        System.out.println("Protection On");
        for (int counter = 0; counter < 30; counter++) {
        }
        this.setCooldown(false);
        System.out.println("Protection over");

    }

    /**
     * sets the character's direction to the given parameter, used to make the character have the direction they are moving in
     *
     * @param direction character's direction they are facing
     */
    public void setDirection(String direction) {
        this.direction = direction;
    }

    /**
     * Retrieves the direction the main character is facing, used to make the character shoot in the direction that they are facing
     *
     * @return direction the direction the main character is facing
     */
    public String getDirection() {
        return direction;
    }

    /**
     * Retrieves if the main character is currently on cooldown, used in the collision listener Damage
     *
     * @return the cooldown state of the main character
     */
    public boolean getCooldown() {
        return cooldown;
    }

    /**
     * Sets the cooldown of the main character to being the given parameter
     *
     * @param cooldown new damage cooldown of the main character
     */
    public void setCooldown(boolean cooldown) {
        this.cooldown = cooldown;
    }

    /**
     * Sets the alive state of the main character to the given parameter
     *
     * @param alive new alive state of the main character
     */
    public void setAlive(boolean alive) {
        alive = alive;
    }

    /**
     * Retrieves the alive state of the main character
     *
     * @return alive state of the character
     */
    public boolean getAlive() {
        return alive;
    }

    /**
     * Retrieves the ammo count of the main character, used when moving the character to the next level or saving the game state
     *
     * @return ammoCount the current ammo count of the character
     */
    public int getAmmoCount() {
        return ammoCount;
    }

    /**
     * Sets the ammo count of the current player to that of the given parameter
     *
     * @param ammoCount new ammo count of the main character
     */
    public void setAmmoCount(int ammoCount) {
        this.ammoCount = ammoCount;
    }
}
