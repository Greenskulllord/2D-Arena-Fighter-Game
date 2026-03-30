package KeyboardInput;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import java.util.BitSet;


//method to get keyboard input
public class InputControls {
    //a bit set can hold a bunch of codes as booleans
    private BitSet keyboardBitSet = new BitSet();

    //declares keys
    private KeyCode moveUp = KeyCode.W;
    private KeyCode moveDown = KeyCode.S;
    private KeyCode moveRight = KeyCode.D;
    private KeyCode moveLeft= KeyCode.A;
    private final KeyCode sprint = KeyCode.SHIFT;



    public InputControls() {

    }



    //handler for key pressed events
 private EventHandler<KeyEvent>  keyPressedHandler = new EventHandler<KeyEvent>() {
     @Override
     public void handle(KeyEvent keyEvent) {
         keyboardBitSet.set(keyEvent.getCode().ordinal(), true);
     }
 };

    //handler for key released events
 private EventHandler<KeyEvent>  keyReleasedHandler = new EventHandler<KeyEvent>() {
     @Override
     public void handle(KeyEvent keyEvent) {
            keyboardBitSet.set(keyEvent.getCode().ordinal(), false);
     }
 };

    //create what the input variable do as booleans
    public boolean isMoveUp() {
        return keyboardBitSet.get(moveUp.ordinal()) && !keyboardBitSet.get(moveDown.ordinal());
    }

    public boolean isMoveDown() {
        return keyboardBitSet.get(moveDown.ordinal()) && !keyboardBitSet.get(moveUp.ordinal());
    }

    public boolean isMoveRight() {
        return keyboardBitSet.get(moveRight.ordinal()) && !keyboardBitSet.get(moveLeft.ordinal());
    }

    public boolean isMoveLeft() {
        return keyboardBitSet.get(moveLeft.ordinal()) && !keyboardBitSet.get(moveRight.ordinal());
    }

    public boolean isSprinting() {
        return keyboardBitSet.get(sprint.ordinal());
    }

    /*
    =========================
        Getters and Setter
    =========================
     */
    public BitSet getKeyboardBitSet() {
        return keyboardBitSet;
    }

    public void setKeyboardBitSet(BitSet keyboardBitSet) {
        this.keyboardBitSet = keyboardBitSet;
    }

    public KeyCode getMoveUp() {
        return moveUp;
    }

    public void setMoveUp(KeyCode moveUp) {
        this.moveUp = moveUp;
    }

    public KeyCode getMoveDown() {
        return moveDown;
    }

    public void setMoveDown(KeyCode moveDown) {
        this.moveDown = moveDown;
    }

    public KeyCode getMoveRight() {
        return moveRight;
    }

    public void setMoveRight(KeyCode moveRight) {
        this.moveRight = moveRight;
    }

    public KeyCode getMoveLeft() {
        return moveLeft;
    }

    public void setMoveLeft(KeyCode moveLeft) {
        this.moveLeft = moveLeft;
    }

    public EventHandler<KeyEvent> getKeyPressedHandler() {
        return keyPressedHandler;
    }

    public void setKeyPressedHandler(EventHandler<KeyEvent> keyPressedHandler) {
        this.keyPressedHandler = keyPressedHandler;
    }

    public EventHandler<KeyEvent> getKeyReleasedHandler() {
        return keyReleasedHandler;
    }

    public void setKeyReleasedHandler(EventHandler<KeyEvent> keyReleasedHandler) {
        this.keyReleasedHandler = keyReleasedHandler;
    }
}
