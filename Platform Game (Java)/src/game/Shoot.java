package game;

import city.cs.engine.*;
import city.cs.engine.Shape;
import org.jbox2d.common.Vec2;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Manages the shooting mechanism for both npcs and for the playable character
 */
public class Shoot extends MouseAdapter {
    private World world;
    private StaticBody object;
    private Avatar mainCharacter;
    private Enemy enemy;

    /**
     * Takes the world and body that the Shoot class is being applied to
     *
     * @param world         current world
     * @param mainCharacter body that is shooting
     */
    public Shoot(GameLevel world, Avatar mainCharacter) {
        this.world = world;
        this.mainCharacter = mainCharacter;
    }

    /**
     * Take the enemy that is shooting and creates, places and adds a collision listener to the projectile being shot
     *
     * @param enemy enemy that is shooting
     */
    public Shoot(Enemy enemy) {
        this.enemy = enemy;
        //Checks the type of enemy
        if (enemy.getType() == "Archer") {
            //Creates a body for the arrow being shot and sets it's speed to 10
            Shape arrowShape = new BoxShape(0.1f, 0.1f);
            Projectile arrow = new Projectile(enemy.getWorld(), arrowShape);
            arrow.setSpeed(10);
            //Checks the direction the arrow will travel in
            if (enemy.getEnemyDirection() == "left") {
                //If the enemy is facing left the speed velocity is set to the negative speed so the body travels left
                arrow.setLinearVelocity(new Vec2(-arrow.getSpeed(), 0));
                //The left arrow image is applied to the body and is positioned to the left side of the enemy body
                arrow.addImage(new BodyImage("data/Object/ArrowLeft.png", 0.4f));
                arrow.setPosition(new Vec2(enemy.getPosition().x - 1, enemy.getPosition().y - 0.1f));
            } else if (enemy.getEnemyDirection() == "right") {
                //If the enemy is facing right the linear velocity is set to the speed of the arrow
                arrow.setLinearVelocity(new Vec2(arrow.getSpeed(), 0));
                //The right arrow image is placed on the body and is placed to the right of the enemy body
                arrow.addImage(new BodyImage("data/Object/arrowRight.png", 0.4f));
                arrow.setPosition(new Vec2(enemy.getPosition().x + 1, enemy.getPosition().y - 0.1f));
            }
            //Collision listener is added to the projectile
            arrow.addCollisionListener(new Damage());
            //Enemy's cooldown is set to true
            enemy.setCooldown(true);

        }

    }

    /**
     * Mouse listener which causes the playable character to shoot
     *
     * @param mouseClicked mouse click event
     */
    @Override
    public void mousePressed(MouseEvent mouseClicked) {
        //Checks if the main character has ammo
        if (mainCharacter.getAmmoCount() != 0) {
            //Creates a projectile of shape bulletShape
            Shape bulletShape = new BoxShape(0.25f, 0.1f);
            Projectile bullet = new Projectile(mainCharacter.getWorld(), bulletShape);

            //Gets the direction the main character is facing
            if (mainCharacter.getDirection() == "right") {
                //If the character is facing right the right image is placed on the body
                bullet.addImage(new BodyImage("data/Object/bulletRight.png", 0.2f));
                //Bullet is place to the right ogf the main character
                bullet.setPosition(new Vec2(mainCharacter.getPosition().x + 0.98f, mainCharacter.getPosition().y + 0.2f));
            } else if (mainCharacter.getDirection() == "left") {
                //If the character is facing left the left image of the bullet is placed on the body
                bullet.addImage(new BodyImage("data/Object/bulletLeft.png", 0.2f));
                //Speed of the bullet is set to the negative speed so it travels left
                bullet.setSpeed(-bullet.getSpeed());
                //Bullet is placed to the left of the main character's body
                bullet.setPosition(new Vec2(mainCharacter.getPosition().x - 0.94f, mainCharacter.getPosition().y + 0.2f));
            }
            //Adds a collision listener to the bullet
            bullet.addCollisionListener(new Damage());
            //Sets the bullet to travel at its speed
            bullet.setLinearVelocity(new Vec2(bullet.getSpeed(), 0));
            //Reduces the player's ammo count
            mainCharacter.setAmmoCount(mainCharacter.getAmmoCount() - 1);
        }
        //If the main character is out of ammo it alerts the user
        else {
            System.out.println("Out of Ammo");
        }

    }

    /**
     * Sets a new main character body the mouse listener will be applied to, used when changing level
     *
     * @param mainCharacter new main character body
     */
    public void setMainCharacter(Avatar mainCharacter) {
        this.mainCharacter = mainCharacter;
    }
}


