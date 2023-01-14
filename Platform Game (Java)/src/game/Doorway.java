package game;

import city.cs.engine.*;

/**
 * Doorway of type sensor to transport between levels
 */
public class Doorway extends StaticBody implements SensorListener {
    private Game game;

    private static final Shape doorShape = new PolygonShape(0.0f, 1.19f, -0.45f, 1.07f, -0.62f, 0.77f, -0.62f, -1.49f, 0.63f, -1.49f, 0.63f, 0.77f, 0.45f, 1.07f
    );
    private static BodyImage doorImage =
            new BodyImage("data/Object/woodenDoor.png", 3);

    /**
     * Creates sensor, adds an image to the sensor, adds a sensor listener and places the door in the current world
     *
     * @param world current world
     * @param game  current game
     */
    public Doorway(World world, Game game) {
        super(world);
        Sensor sensor = new Sensor(this, doorShape);
        addImage(doorImage);
        sensor.addSensorListener(this);
        this.game = game;

    }

    /**
     * When there is contact between the sensor and a body of instance Avatar the game goes to the next level
     *
     * @param sensorEvent sensor event
     */
    @Override
    public void beginContact(SensorEvent sensorEvent) {
        if (sensorEvent.getContactBody() instanceof Avatar) {
            System.out.println("Level Up");
            game.nextLevel();
        }
    }

    @Override
    public void endContact(SensorEvent sensorEvent) {

    }

    public void setDoorImage(BodyImage doorImage) {
        Doorway.doorImage = doorImage;
        removeAllImages();
        addImage(doorImage);
    }
}
