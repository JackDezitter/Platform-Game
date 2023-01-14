package game;

import city.cs.engine.StepEvent;
import city.cs.engine.StepListener;

/**
 * Manages attack mechanisms for enemies besides collisions
 */
public class Attack implements StepListener {
    private Enemy enemy;
    private int cooldownCounter;
    private float speed;
    private static Avatar mainCharacter;
    private String chargeDirection;
    private Boolean charging = false;

    public Attack(Enemy enemy) {
        this.enemy = enemy;

    }

    /**
     * Retrieves the current playable character in the world
     *
     * @param stepEvent
     */
    @Override
    public void preStep(StepEvent stepEvent) {
        mainCharacter = GameLevel.getMainCharacter();
    }

    /**
     * Checks for conditions to satisfy the npc attack status
     *
     * @param stepEvent
     */
    @Override
    public void postStep(StepEvent stepEvent) {
        //Checks if the enemy is currently on cooldown from a previous attack
        if (enemy.getCooldown() == true) {
            //Checks what type the enemy is
            if (enemy.getType() == "Shield") {
                //Shield enemy
                //If the enemy is currently charging stop the character
                if (charging == true) {
                    charging = false;
                    enemy.startWalking(0);
                }
                //Causes character to walk back to their start position if they have reached the edge of their charge zone
                if (Math.round(enemy.getPosition().x) != Math.round(enemy.getStartPosition().x)) {
                    if ((chargeDirection == "left") && (enemy.getLinearVelocity().y == 0.0)) {
                        enemy.setResetDirection("right");
                        enemy.startWalking(enemy.getWalkSpeed());
                    } else if (chargeDirection == "right") {
                        enemy.setResetDirection("left");
                        enemy.startWalking(-enemy.getWalkSpeed());
                    }
                }
                //When the character returns to their rough start position, stop the character and set their cooldown to false.
                else if (Math.round(enemy.getPosition().x) == Math.round(enemy.getStartPosition().x)) {
                    enemy.setResetDirection(null);
                    enemy.setCooldown(false);
                    enemy.startWalking(0);
                }
            }
            //If enemy is not of type shield add a 1 second cooldown count, when the count reaches 60 (1 count is 1/60th of a second) cooldown is set to false so the character can attack again, count is reset to 0
            else {
                cooldownCounter++;
                if (cooldownCounter == 60) {
                    cooldownCounter = 0;
                    enemy.setCooldown(false);
                }
            }

        }
        //If the enemy's cooldown is false
        else if (enemy.getCooldown() == false && mainCharacter.getAlive() == true) {
            //If the enemy is of type archer and they are still alive
            if (enemy.getType() == "Archer" && enemy.getAlive() == true) {
                //Shoot if the playable character is at in line on the y axis roughly as the archer
                if (Math.ceil(mainCharacter.getPosition().y) == Math.ceil(enemy.getPosition().y)) {
                    new Shoot(enemy);
                }

            }
            //If the enemy is of type Shield
            else if (enemy.getType() == "Shield") {
                //Checks if enemy is within its walk perimeter and if the playable character is also in its attack region (Same y level and within the walk distance)
                if (((enemy.getPosition().x >= (enemy.getStartPosition().x - enemy.getWalkDistance())) && (enemy.getPosition().x <= (enemy.getStartPosition().x + enemy.getWalkDistance()))) && ((Math.ceil(mainCharacter.getPosition().y) == Math.ceil(enemy.getPosition().y)))) {
                    //Sets charging to being true
                    charging = true;
                    //Sets which direction the shield enemy must charge in to reach the playable character
                    if ((mainCharacter.getPosition().x >= (enemy.getStartPosition().x - enemy.getWalkDistance())) && (mainCharacter.getPosition().x < enemy.getStartPosition().x)) {
                        enemy.startWalking(-enemy.getSpeed());
                        chargeDirection = "left";
                    } else if ((mainCharacter.getPosition().x <= (enemy.getStartPosition().x + enemy.getWalkDistance())) && (mainCharacter.getPosition().x > enemy.getStartPosition().x)) {
                        enemy.startWalking(enemy.getSpeed());
                        chargeDirection = "right";
                    }

                }
                //When the enemy reaches the edge of their attack region set the cooldown to be true so they reset to their original start point
                else if ((enemy.getPosition().x <= (enemy.getStartPosition().x - enemy.getWalkDistance())) || (enemy.getPosition().x >= (enemy.getStartPosition().x + enemy.getWalkDistance()))) {
                    enemy.setCooldown(true);
                }

            }
        }

    }
}
