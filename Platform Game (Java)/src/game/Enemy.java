package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

/**
 * Creates Enemy npcs with different attributes and different behaviour
 */
public class Enemy extends Walker {
    private String type;
    private int health;
    private float walkDistance;
    private Vec2 startPosition;
    private float speed;
    private float walkSpeed;
    private boolean cooldown = false;
    private String enemyDirection;
    private Boolean alive = true;
    private String resetDirection = null;

    /**
     * Creates enemy places them in the world with attributes given by the parameters and adds a step listener FlipCharacter to this enemy
     *
     * @param world         world the enemy is being placed in
     * @param type          the type of enemy that is need for the level, determines its behaviour such as movement and attacks
     * @param walkDistance  distance the enemy is able to move from its start position
     * @param startPosition start p[osition of the enemy being placed in the world
     */
    public Enemy(World world, String type, float walkDistance, Vec2 startPosition) {

        super(world);
        this.walkDistance = walkDistance;
        this.type = type;
        this.startPosition = startPosition;
        this.setPosition(startPosition);
        Game.getGameLevel().addStepListener(new FlipCharacter(this));
        EnemyType();

    }

    /**
     * Based on the type of enemy given in the constructor the correct shape, images, speeds (if applicable) and step listener Attack which controls enemies' attack behaviour is applied
     */
    private void EnemyType() {
        if (type == "Walker") {
            health = 1;
            this.walkSpeed = -2;
            Shape walkerShape = new PolygonShape(-0.438f, 1.005f, -0.558f, -0.415f, -0.308f, -1.22f, 0.312f, -1.205f, 0.512f, -0.41f, 0.452f, 1.01f, 0.232f, 1.235f, -0.213f, 1.23f);
            SolidFixture medWalkerBody = new SolidFixture(this, walkerShape);
            this.addImage(new BodyImage("data/Enemy/MedWalkerLeft.png", 2.5f));
            this.startWalking(walkSpeed);
        } else if (type == "Archer") {
            health = 2;
            this.setGravityScale(500);
            Shape archerShape = new PolygonShape(-0.384f, 1.083f, 0.293f, 1.083f, 0.637f, 0.026f, 0.586f, -0.909f, -0.657f, -0.889f, -0.794f, 0.006f);
            SolidFixture archerBody = new SolidFixture(this, archerShape);
            this.addImage(new BodyImage("data/Enemy/ArcherLeft.png", 2.5f));
            Game.getGameLevel().addStepListener(new Attack(this));
        } else if (type == "Shield") {
            health = 1;
            this.setGravityScale(50);
            this.speed = 15;
            this.walkSpeed = 5;
            Shape MedShieldBody = new PolygonShape(0.29f, 1.48f, -0.3f, 1.48f, -1.19f, 0.81f, -1.29f, -1.45f, 1.27f, -1.45f, 1.19f, 0.81f);
            SolidFixture shieldBody = new SolidFixture(this, MedShieldBody);
            this.addImage(new BodyImage("data/Enemy/ShieldLeft.png", 3));
            Game.getGameLevel().addStepListener(new Attack(this));
        }


    }

    /**
     * Retrieves Health of the current character
     *
     * @return health
     */
    public int getHealth() {
        return health;
    }

    /**
     * Sets the health of a character to the given parameter, used to damage enemies after collisions, If the enemy's health reaches 0 it then destroys the enemy and sets their alive variable to false for later saving
     *
     * @param health new health of the enemy
     */
    public void setHealth(int health) {
        this.health = health;
        if (health == 0) {
            this.destroy();
            alive = false;
        }
    }

    /**
     * Sets the image for the enemy looking towards the right based on the type of the enemy
     */
    public void setRightImage() {
        if (getType() == "Walker") {
            this.addImage(new BodyImage("data/Enemy/MedWalkerRight.png", 2.5f));

        } else if (getType() == "Archer") {
            this.addImage(new BodyImage("data/Enemy/ArcherRight.PNG", 2.5f));

        } else if (getType() == "Shield") {
            this.addImage(new BodyImage("data/Enemy/ShieldRight.png", 3));
        }
    }

    /**
     * Sets the image for the enemy looking towards the left based on the type of the enemy
     */
    public void setLeftImage() {
        if (getType() == "Walker") {
            this.addImage(new BodyImage("data/Enemy/MedWalkerLeft.png", 2.5f));
        } else if (getType() == "Archer") {
            this.addImage(new BodyImage("data/Enemy/ArcherLeft.PNG", 2.5f));
        } else if (getType() == "Shield") {
            this.addImage(new BodyImage("data/Enemy/ShieldLeft.png", 3));
        }
    }

    /**
     * Retrieves the type of the enemy
     *
     * @return type of enemy
     */
    public String getType() {
        return type;
    }

    /**
     * Retrieves the start position of the enemy, used to set ensure the enemy doesn't move further than the set walk distance.
     *
     * @return start position of the enemy
     */
    public Vec2 getStartPosition() {
        return startPosition;
    }

    /**
     * Retrieves the allowed walk distance of the enemy
     *
     * @return walk distance of the enemy
     */
    public float getWalkDistance() {
        return walkDistance;
    }

    /**
     * Retrieves fast movement speed of an enemy, used for enemies of type shield
     *
     * @return speed
     */
    public float getSpeed() {
        return speed;
    }

    /**
     * Retrieves the walking speed of an enemy
     *
     * @return walkSpeed
     */
    public float getWalkSpeed() {
        return walkSpeed;
    }

    /**
     * Sets the walk speed of an enemy character to the given parameter, used to make the enemy walk in the opposite direction when it has reached its boundary
     *
     * @param walkSpeed new walk speed being assigned to the character
     */
    public void setWalkSpeed(float walkSpeed) {
        this.walkSpeed = walkSpeed;
    }

    /**
     * Retrieves if the enemy is on cooldown after an attack
     *
     * @return cooldown
     */
    public boolean getCooldown() {
        return cooldown;
    }

    /**
     * Sets the cooldown value of the enemy to the given parameter, used after an enemy attack to set cooldown to true or when an enemy's cooldown method is over it is set to false enabling it to attack again
     *
     * @param cooldown new cooldown value of the enemy
     */
    public void setCooldown(boolean cooldown) {
        this.cooldown = cooldown;
    }

    /**
     * Retrieves which direction the enemy is facing
     *
     * @return enemy direction
     */
    public String getEnemyDirection() {
        return enemyDirection;
    }

    /**
     * Set the direction the enemy is facing to the given parameter
     *
     * @param enemyDirection new Direction the enmy is facing
     */
    public void setEnemyDirection(String enemyDirection) {
        this.enemyDirection = enemyDirection;
    }

    /**
     * Retrieves the Reset direction of the enemy, used for enemies of type shield to return to their origin position after they have performed an attack
     *
     * @return resetDirection
     */
    public String getResetDirection() {
        return resetDirection;
    }

    /**
     * Sets the Reset direction of the enemy to the given parameter
     *
     * @param resetDirection direction the enemy has to walk to be reset
     */
    public void setResetDirection(String resetDirection) {
        this.resetDirection = resetDirection;
    }

    /**
     * Retrieves whether the enemy is currently alive, used when saving levels to the files
     *
     * @return alie state of the enemy
     */
    public Boolean getAlive() {
        return alive;
    }

}
