package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Controls playable characters movement by keys pressed
 */
public class MovementController extends KeyAdapter {
    //Sets walking and jumping speed
    private static float defaultJumpSpeed = 25;
    private static final float JUMPING_SPEED = defaultJumpSpeed;
    private static float defaultWalkSpeed = 6;
    private static float WALKING_SPEED = defaultWalkSpeed;


    //Creates walker body (Avatar)
    private Avatar mainCharacter;

    //Sets movement for this instance
    public MovementController(Avatar mainCharacter) {
        this.mainCharacter = mainCharacter;
    }

    /**
     * Key listener
     *
     * @param pressedKey the key that was pressed and activated the listener
     */
    @Override
    //If a key is pressed
    public void keyPressed(KeyEvent pressedKey) {
        int code = pressedKey.getKeyCode();
        //If the key pressed is D avatar walks to the right at a speed of default
        if (code == KeyEvent.VK_D) {
            //Causes main character to move right
            mainCharacter.setDirection("right");
            mainCharacter.startWalking(WALKING_SPEED);
        }
        //If key pressed is A avatar walks to the left at default speed
        else if (code == KeyEvent.VK_A) {
            mainCharacter.setDirection("left");
            //Causes main character to move left
            mainCharacter.startWalking(-WALKING_SPEED);
        }
        //If space is pressed Character jumps
        else if (code == KeyEvent.VK_SPACE) {
            Vec2 currentJumpVelocity = mainCharacter.getLinearVelocity();
            if (Math.abs(currentJumpVelocity.y) <= 0) {
                mainCharacter.jump(JUMPING_SPEED);
            }

        } else if (code == KeyEvent.VK_S) {
            if (mainCharacter.getLinearVelocity().y != 0) {
                float FALLING_SPEED = mainCharacter.getLinearVelocity().y;
                mainCharacter.setLinearVelocity(new Vec2(0, FALLING_SPEED - 20));
            }
        }
    }

    //If a key is release
    public void keyReleased(KeyEvent releasedKey) {
        int code = releasedKey.getKeyCode();
        //If the key released was D or A
        if ((code == KeyEvent.VK_D) || (code == KeyEvent.VK_A)) {
            //the walk speed will set to 0 stops walking,
            //stopWalking caused a sliding effect
            mainCharacter.startWalking(0);
        }

    }

    public void setMainCharacter(Avatar mainCharacter) {
        this.mainCharacter = mainCharacter;
    }
}
