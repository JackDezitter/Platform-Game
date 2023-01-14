package game;

import city.cs.engine.*;

/**
 * Creates a dynamic body with a gravity scale of 0 and a given speed/ velocity
 */
public class Projectile extends DynamicBody {
    private int speed = 20;

    /**
     * Places projectile body in world with the shape provided and gives the body a gravity scale of 0
     *
     * @param world           world the projectile is being placed in
     * @param projectileShape shape of the projectile being places
     */
    public Projectile(World world, Shape projectileShape) {
        super(world, projectileShape);
        this.setGravityScale(0);
    }

    /**
     * Sets the speed of the projectile to the given parameter
     *
     * @param speed new speed of the projectile
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    /**
     * Retrieves the speed of the projectile
     *
     * @return speed of projectil
     */
    public int getSpeed() {
        return speed;
    }
}
