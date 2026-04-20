package Input;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.util.BitSet;


//method to get keyboard input
public class InputControls {
    //a bit set can hold a bunch of codes as booleans
    private BitSet keyboardBitSet = new BitSet();

    //declares keys and codes
    private KeyCode moveUp = KeyCode.W;
    private KeyCode moveDown = KeyCode.S;
    private KeyCode moveRight = KeyCode.D;
    private KeyCode moveLeft= KeyCode.A;
    private final KeyCode sprint = KeyCode.SHIFT;
    private KeyCode dash = KeyCode.ALT;

    //mouse codes
    private MouseButton leftClick = MouseButton.PRIMARY;
    private MouseButton rightClick = MouseButton.SECONDARY;
    private MouseButton middleCLick = MouseButton.MIDDLE;
    private final MouseButton forwardClick = MouseButton.FORWARD;

    //get mouse X and Y constantly
    public double mouseX;
    public double mouseY;
    private final int mouseCodeOffset = 1000;
    private boolean mouseRight = false;
    private boolean mouseLeft = false;

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

    //the event handlers for mouse input
    private EventHandler<MouseEvent> mousePressedHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            keyboardBitSet.set(mouseEvent.getButton().ordinal() + mouseCodeOffset, true);
            mouseLeft = true;
            mouseRight = true;

        }
    };

    //for on release
    private EventHandler<MouseEvent> mouseReleasedHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            keyboardBitSet.set(mouseEvent.getButton().ordinal() + mouseCodeOffset, false);
            mouseLeft = false;
            mouseRight = false;
        }
    };

    private EventHandler<MouseEvent> mouseMovedHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            mouseX = mouseEvent.getX();
            mouseY = mouseEvent.getY();
        }
    };

 public void reset() {
     keyboardBitSet.clear();
 }

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

    public boolean isDashing() {
     return keyboardBitSet.get(dash.ordinal());
    }

    //mouse input booleans
    public boolean onLeftClick() {
        if (mouseLeft) {
            mouseLeft = false;
            return keyboardBitSet.get(leftClick.ordinal() + mouseCodeOffset);
        }

        return false;
    }

    public boolean onRightClick() {

        if (mouseRight) {
            mouseRight = false;
            return keyboardBitSet.get(rightClick.ordinal() + mouseCodeOffset);
        }

        return false;
    }

    public boolean onMiddleClick() {
        return keyboardBitSet.get(middleCLick.ordinal() + mouseCodeOffset);
    }

    public boolean onForwardClick() {
        return keyboardBitSet.get(forwardClick.ordinal() + mouseCodeOffset);
    }

    /*
    =========================
        Getters and Setter
    =========================
     */

    public double getMouseX() { return mouseX; }
    public double getMouseY() { return mouseY; }

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

    public KeyCode getDash() {
        return dash;
    }

    public void setDash(KeyCode dash) {
        this.dash = dash;
    }

    public MouseButton getLeftClick() {
        return leftClick;
    }

    public void setLeftClick(MouseButton leftClick) {
        this.leftClick = leftClick;
    }

    public MouseButton getRightClick() {
        return rightClick;
    }

    public void setRightClick(MouseButton rightClick) {
        this.rightClick = rightClick;
    }

    public MouseButton getMiddleCLick() {
        return middleCLick;
    }

    public void setMiddleCLick(MouseButton middleCLick) {
        this.middleCLick = middleCLick;
    }

    public EventHandler<MouseEvent> getMouseReleasedHandler() {
        return mouseReleasedHandler;
    }

    public void setMouseReleasedHandler(EventHandler<MouseEvent> mouseReleasedHandler) {
        this.mouseReleasedHandler = mouseReleasedHandler;
    }

    public EventHandler<MouseEvent> getMousePressedHandler() {
        return mousePressedHandler;
    }

    public void setMousePressedHandler(EventHandler<MouseEvent> mousePressedHandler) {
        this.mousePressedHandler = mousePressedHandler;
    }

    public EventHandler<MouseEvent> getMouseMovedHandler() {
        return mouseMovedHandler;
    }

    public void setMouseMovedHandler(EventHandler<MouseEvent> mouseMovedHandler) {
        this.mouseMovedHandler = mouseMovedHandler;
    }
}
