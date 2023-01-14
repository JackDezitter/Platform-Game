package game;

import city.cs.engine.*;

/**
 * Manages collectible ammo crates
 */

public class Ammo extends StaticBody implements SensorListener {
    private static Shape ammoShape = new PolygonShape(-0.317f, 0.481f, -0.525f, 0.261f, -0.525f, -0.482f, 0.518f, -0.489f, 0.518f, 0.256f, 0.309f, 0.481f);
    private static BodyImage ammoImage = new BodyImage("data/Object/Ammo.png", 1);
    private Boolean collected = false;


    /**
     * Creates a sensor for the ammo crate pick ups adds the images and listener
     *
     * @param w the current world
     */
    public Ammo(World w) {
        super(w);
        Sensor sensor = new Sensor(this, ammoShape);
        addImage(ammoImage);
        sensor.addSensorListener(this);
    }

    /**
     * Sensor listener which when the main character collides will set their ammo count to 10 bullets
     *
     * @param sensorEvent the sensor collision
     */
    @Override
    public void beginContact(SensorEvent sensorEvent) {
        if (sensorEvent.getContactBody() instanceof Avatar) {
            ((Avatar) sensorEvent.getContactBody()).setAmmoCount(10);
            setCollected(true);
        }

    }

    /**
     * End of contact
     *
     * @param sensorEvent sensor collision
     */
    @Override
    public void endContact(SensorEvent sensorEvent) {

    }

    /**
     * Sets the current ammo crate to have the collected state so when the level is loaded from a save file the user does not have infinite ammo
     * if the ammo crate is collected it will remove the crate from the world
     *
     * @param collected if the ammo crate has been collected
     */
    public void setCollected(Boolean collected) {
        this.collected = collected;
        if (this.collected == true) {
            this.destroy();
            this.removeAllImages();
        }
    }

    /**
     * Returns the collected value of the current ammo crate
     *
     * @return collected if the ammo crate has been collected or not
     */
    public Boolean getCollected() {
        return collected;
    }
}
