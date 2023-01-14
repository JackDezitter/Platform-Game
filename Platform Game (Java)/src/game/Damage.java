package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

import java.util.concurrent.TimeUnit;

/**
 * Manages damage for the playable character and enemies
 */
public class Damage implements CollisionListener {

    //Retrieves current main character
    private Avatar mainCharacter = GameLevel.getMainCharacter();

    public Damage() {
    }

    /**
     * Detects collisions
     *
     * @param hit collision
     */
    public void collide(CollisionEvent hit) {
        //Projectile Collisions
        if (hit.getReportingBody() instanceof Projectile) {
            //If the projectile hit a static body it destroys the projectile
            if ((hit.getOtherBody() instanceof StaticBody)) {
                hit.getReportingBody().destroy();
                System.out.println("Projectile hit Static Body");
            }
            //If the projectile hits an instance of Avatar the projectile is destroyed and the instance of Avatar has its life decreased by 1
            else if (hit.getOtherBody() instanceof Avatar) {
                hit.getReportingBody().destroy();
                System.out.println("Projectile hit main character");
                mainCharacter.decreaseLife();
            }
            //If the projectile hits an instance of Enemy
            else if (hit.getOtherBody() instanceof Enemy) {
                //If the enemy hit is of type shield and the enemy is facing the direction of the projectile it will reflect the projectile in the opposite direction
                if ((((Enemy) hit.getOtherBody()).getType() == "Shield") && (((Enemy) hit.getOtherBody()).getEnemyDirection() == "right") && (((Projectile) hit.getReportingBody()).getSpeed()) < 0) {
                    ((Projectile) hit.getReportingBody()).setSpeed(-((Projectile) hit.getReportingBody()).getSpeed());
                    ((Projectile) hit.getReportingBody()).setLinearVelocity(new Vec2(((Projectile) hit.getReportingBody()).getSpeed(), 0));
                } else if ((((Enemy) hit.getOtherBody()).getType() == "Shield") && (((Enemy) hit.getOtherBody()).getEnemyDirection() == "left") && (((Projectile) hit.getReportingBody()).getSpeed()) > 0) {
                    ((Projectile) hit.getReportingBody()).setSpeed(-((Projectile) hit.getReportingBody()).getSpeed());
                    ((Projectile) hit.getReportingBody()).setLinearVelocity(new Vec2(((Projectile) hit.getReportingBody()).getSpeed(), 0));

                }
                //If the Shielded enemy is not facing the direction of the projectile or the projectile hits another type of enemy the bullet is destroyed and the health of the enemy is decreased by 1
                else {
                    hit.getReportingBody().destroy();
                    System.out.println("Projectile hit enemy");
                    ((Enemy) hit.getOtherBody()).setHealth(((Enemy) hit.getOtherBody()).getHealth() - 1);
                }
            }
            //If the projectile hits another projectile both projectiles are destroyed
            else if (hit.getOtherBody() instanceof Projectile) {
                hit.getOtherBody().destroy();
                hit.getReportingBody().destroy();
                System.out.println("Projectile hit projectile");
            }
        }
        //If the collision is between an instance of avatar and an instance of enemy the main character has its lif decreased
        else if ((hit.getReportingBody() instanceof Avatar) && (hit.getOtherBody() instanceof Enemy)) {
            if (mainCharacter.getCooldown() == false) {
                mainCharacter.decreaseLife();
                System.out.println("Enemy hit main character");
                //If the enemy is of type Shield it sets the shielded enemy's cooldown to true
                if (((Enemy) hit.getOtherBody()).getType() == "Shield") {
                    ((Enemy) hit.getOtherBody()).setCooldown(true);
                }

            }


        }

    }
}


