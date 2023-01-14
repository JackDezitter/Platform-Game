package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

import java.lang.Math;

/**
 * Makes all character's images correspond to the direction of which they are looking and aids some enemy movement attributes
 */
public class FlipCharacter implements StepListener {
    private static Avatar mainCharacter;
    private String mainCharacterDirection;
    private Enemy enemy;

    /**
     * StepListener that causes the main character's image to be facing the direction of movement
     *
     * @param mainCharacter current main character/playable character
     */
    public FlipCharacter(Avatar mainCharacter) {
        FlipCharacter.mainCharacter = mainCharacter;
    }

    /**
     * Causes enemy image and direction to face the direction they are moving or the directing the main character is from their position based upon which type the enemy is.
     * If the type of enemy allows it this controls the enemies walking movement for returning to a point or walking back and forth
     *
     * @param enemy current enemy
     */
    public FlipCharacter(Enemy enemy) {
        this.enemy = enemy;
    }

    @Override
    public void preStep(StepEvent stepEvent) {
        //Retrieves current direction of the main character
        mainCharacterDirection = mainCharacter.getDirection();
    }

    @Override
    public void postStep(StepEvent stepEvent) {
        //PLaces the corresponding image to the body of the main character based on the direction they are facing
        if (mainCharacterDirection == "left") {
            mainCharacter.setAvatarImage(new BodyImage("data/Player/AvatarLeft.png", 2.5f));
        } else if (mainCharacterDirection == "right") {
            mainCharacter.setAvatarImage(new BodyImage("data/Player/AvatarRight.png", 2.5f));
        }
        //If there is an enemy present and the enemy is alive
        if (enemy != null) {
            if (enemy.getAlive() == true) {
                //The type of enemy is retrieved
                //If enemy is of type walker
                if (enemy.getType() == "Walker") {
                    //Checks if the enemy has reached the limit of its walk distance
                    // If true it sets the enemy to walk in the opposite direction and sets it's direction to the new movement direction
                    //Left boundary
                    if (enemy.getPosition().x <= (enemy.getStartPosition().x - enemy.getWalkDistance())) {
                        enemy.setWalkSpeed(-enemy.getWalkSpeed());
                        enemy.setEnemyDirection("right");
                        enemy.startWalking(0);
                        enemy.startWalking(enemy.getWalkSpeed());

                    }
                    //Right boundary
                    else if (enemy.getPosition().x >= (enemy.getStartPosition().x + enemy.getWalkDistance())) {
                        enemy.setWalkSpeed(-enemy.getWalkSpeed());
                        enemy.setEnemyDirection("left");
                        enemy.startWalking(0);
                        enemy.startWalking(enemy.getWalkSpeed());


                    }

                }
                //Checks if the enemy is being reset to a position
                else if (enemy.getResetDirection() == null) {
                    //Checks if the main character has roughly the same y position as the enemy and is alive
                    if (Math.ceil(enemy.getPosition().y) == Math.ceil(mainCharacter.getPosition().y) && mainCharacter.getAlive() == true) {
                        //Compares x positions of the playable character and the enemy and sets the enemy direction to face the playable character
                        //Left
                        if (enemy.getPosition().x > mainCharacter.getPosition().x) {
                            enemy.setEnemyDirection("left");
                        }
                        //Right
                        else if (enemy.getPosition().x < mainCharacter.getPosition().x) {
                            enemy.setEnemyDirection("right");
                        }
                    }
                }
                //If the enemy is being reset the reset direction is assigned to the enemies direction
                else if (enemy.getResetDirection() != null) {
                    enemy.setEnemyDirection(enemy.getResetDirection());
                }
            }
            //If the direction is right it removes all images from the body and sets the right facing image
            if (enemy.getEnemyDirection() == "right") {
                enemy.removeAllImages();
                enemy.setRightImage();
            }
            //If the direction is lift it removes all images from the body and sets the left facing image
            else if (enemy.getEnemyDirection() == "left") {
                enemy.removeAllImages();
                enemy.setLeftImage();
            }
        }
    }
}


