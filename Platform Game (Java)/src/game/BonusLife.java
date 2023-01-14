package game;

import city.cs.engine.*;

/**
 * Manages collectible bonus lives
 */
public class BonusLife extends StaticBody implements SensorListener {

    private static final Shape lifeShape = new PolygonShape(-0.265f, 0.47f, -0.561f, 0.184f, -0.007f, -0.477f, 0.552f, 0.181f, 0.251f, 0.465f);
    private static final BodyImage lifeImage = new BodyImage("data/Object/Heart.png", 1);
    private Boolean collected = false;

    /**
     * Creates a sensor for the Bonus lives pick ups adds the images and listener
     *
     * @param world the current world
     */
    public BonusLife(World world) {
        super(world);
        Sensor sensor = new Sensor(this, lifeShape);
        addImage(lifeImage);
        sensor.addSensorListener(this);
    }

    /**
     * Sensor listener which when the main character collides will cause their life count to increment by 1
     *
     * @param sensorEvent the sensor collision
     */
    @Override
    public void beginContact(SensorEvent sensorEvent) {
        if (sensorEvent.getContactBody() instanceof Avatar) {
            ((Avatar) sensorEvent.getContactBody()).incrementLife();
            setCollected(true);
        }

    }

    /**
     * End of contact
     *
     * @param sensorEvent sensor event
     */
    @Override
    public void endContact(SensorEvent sensorEvent) {

    }

    /**
     * Sets the current bonus life to have the collected state so when the level is loaded from a save file the user does not have infinite health
     * if the bonus life is collected it will remove the bonus life from the world
     *
     * @param collected if the Bonus life has been collected
     */
    public void setCollected(Boolean collected) {
        this.collected = collected;
        if (collected == true) {
            this.destroy();
            this.removeAllImages();
        }
    }

    /**
     * Returns the collected value of the current bonus life
     *
     * @return collected if the bonus life has been collected or not
     */
    public Boolean getCollected() {
        return collected;
    }
}
