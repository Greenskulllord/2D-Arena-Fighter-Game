package Engine.Input;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.util.BitSet;


//method to get keyboard input
public class InputControls {
    //a bit set can hold a bunch of codes as booleans
    private final BitSet keyboardBitSet = new BitSet();

    //special keys
    private final KeyCode enter = KeyCode.ENTER;
    private final KeyCode shift = KeyCode.SHIFT;
    private final KeyCode control = KeyCode.CONTROL;
    private final KeyCode alt = KeyCode.ALT;
    private final KeyCode space = KeyCode.SPACE;
    private final KeyCode tab = KeyCode.TAB;
    private final KeyCode caps = KeyCode.CAPS;
    private final KeyCode escape = KeyCode.ESCAPE;
    private final KeyCode backspace = KeyCode.BACK_SPACE;
    private final KeyCode delete = KeyCode.DELETE;
    private final KeyCode insert = KeyCode.INSERT;
    private final KeyCode home = KeyCode.HOME;
    private final KeyCode end = KeyCode.END;
    private final KeyCode pageUp = KeyCode.PAGE_UP;
    private final KeyCode pageDown = KeyCode.PAGE_DOWN;
    private final KeyCode up = KeyCode.UP;
    private final KeyCode down = KeyCode.DOWN;
    private final KeyCode left = KeyCode.LEFT;
    private final KeyCode right = KeyCode.RIGHT;
    private final KeyCode f1 = KeyCode.F1;
    private final KeyCode f2 = KeyCode.F2;
    private final KeyCode f3 = KeyCode.F3;
    private final KeyCode f4 = KeyCode.F4;
    private final KeyCode f5 = KeyCode.F5;
    private final KeyCode f6 = KeyCode.F6;
    private final KeyCode f7 = KeyCode.F7;
    private final KeyCode f8 = KeyCode.F8;
    private final KeyCode f9 = KeyCode.F9;
    private final KeyCode f10 = KeyCode.F10;
    private final KeyCode f11 = KeyCode.F11;
    private final KeyCode f12 = KeyCode.F12;



    //keys on keyboard
    private final KeyCode moveUp = KeyCode.W;
    private final KeyCode moveDown = KeyCode.S;
    private final KeyCode moveRight = KeyCode.D;
    private final KeyCode moveLeft= KeyCode.A;
    private final KeyCode r = KeyCode.R;
    private final KeyCode q = KeyCode.Q;
    private final KeyCode e = KeyCode.E;
    private final KeyCode t = KeyCode.T;
    private final KeyCode f = KeyCode.F;
    private final KeyCode g = KeyCode.G;
    private final KeyCode z = KeyCode.Z;
    private final KeyCode x = KeyCode.X;
    private final KeyCode c = KeyCode.C;
    private final KeyCode v = KeyCode.V;
    private final KeyCode b = KeyCode.B;
    private final KeyCode n = KeyCode.N;
    private final KeyCode m = KeyCode.M;
    private final KeyCode h = KeyCode.H;
    private final KeyCode j = KeyCode.J;
    private final KeyCode k = KeyCode.K;
    private final KeyCode l = KeyCode.L;
    private final KeyCode y = KeyCode.Y;
    private final KeyCode u = KeyCode.U;
    private final KeyCode i = KeyCode.I;
    private final KeyCode o = KeyCode.O;
    private final KeyCode p = KeyCode.P;


    //mouse codes
    private final MouseButton leftClick = MouseButton.PRIMARY;
    private final MouseButton rightClick = MouseButton.SECONDARY;
    private final MouseButton middleCLick = MouseButton.MIDDLE;
    private final MouseButton forwardClick = MouseButton.FORWARD;



    //get mouse X and Y constantly
    public double mouseX;
    public double mouseY;
    private final int mouseCodeOffset = 1000;
    private boolean mouseRight = false;
    private boolean mouseLeft = false;

    public InputControls() {}
    public void reset() {
        keyboardBitSet.clear();
    } //resets inputs


    //handlers for pressed events
    private EventHandler<KeyEvent>  keyPressedHandler = keyEvent -> keyboardBitSet.set(keyEvent.getCode().ordinal(), true);
    private EventHandler<KeyEvent>  keyReleasedHandler = keyEvent -> keyboardBitSet.set(keyEvent.getCode().ordinal(), false);
    private EventHandler<MouseEvent> mousePressedHandler = mouseEvent -> {
        keyboardBitSet.set(mouseEvent.getButton().ordinal() + mouseCodeOffset, true);
        mouseLeft = true;
        mouseRight = true;

    };
    private EventHandler<MouseEvent> mouseReleasedHandler = mouseEvent -> {
        keyboardBitSet.set(mouseEvent.getButton().ordinal() + mouseCodeOffset, false);
        mouseLeft = false;
        mouseRight = false;
    };
    private EventHandler<MouseEvent> mouseMovedHandler = new EventHandler<>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            mouseX = mouseEvent.getX();
            mouseY = mouseEvent.getY();
        }
    };

    //special keys
    public boolean isKeyEnter() {
        return keyboardBitSet.get(enter.ordinal());
    }
    public boolean isKeyShift() {
        return keyboardBitSet.get(shift.ordinal());
    }
    public boolean isKeyControl() {
        return keyboardBitSet.get(control.ordinal());
    }
    public boolean isKeyAlt() {
        return keyboardBitSet.get(alt.ordinal());
    }
    public boolean isKeySpace() {
        return keyboardBitSet.get(space.ordinal());
    }
    public boolean isKeyTab() {
        return keyboardBitSet.get(tab.ordinal());
    }
    public boolean isKeyCaps() {
        return keyboardBitSet.get(caps.ordinal());
    }
    public boolean isKeyEscape() {
        return keyboardBitSet.get(escape.ordinal());
    }
    public boolean isKeyBackspace() {
        return keyboardBitSet.get(backspace.ordinal());
    }
    public boolean isKeyDelete() {
        return keyboardBitSet.get(delete.ordinal());
    }
    public boolean isKeyInsert() {
        return keyboardBitSet.get(insert.ordinal());
    }
    public boolean isKeyHome() {
        return keyboardBitSet.get(home.ordinal());
    }
    public boolean isKeyEnd() {
        return keyboardBitSet.get(end.ordinal());
    }
    public boolean isKeyPageUp() {
        return keyboardBitSet.get(pageUp.ordinal());
    }
    public boolean isKeyPageDown() {
        return keyboardBitSet.get(pageDown.ordinal());
    }
    public boolean isKeyUp() {
        return keyboardBitSet.get(up.ordinal());
    }
    public boolean isKeyDown() {
        return keyboardBitSet.get(down.ordinal());
    }
    public boolean isKeyLeft() {
        return keyboardBitSet.get(left.ordinal());
    }
    public boolean isKeyRight() {
        return keyboardBitSet.get(right.ordinal());
    }
    public boolean isKeyF1() {
        return keyboardBitSet.get(f1.ordinal());
    }
    public boolean isKeyF2() {
        return keyboardBitSet.get(f2.ordinal());
    }
    public boolean isKeyF3() {
        return keyboardBitSet.get(f3.ordinal());
    }
    public boolean isKeyF4() {
        return keyboardBitSet.get(f4.ordinal());
    }
    public boolean isKeyF5() {
        return keyboardBitSet.get(f5.ordinal());
    }
    public boolean isKeyF6() {
        return keyboardBitSet.get(f6.ordinal());
    }
    public boolean isKeyF7() {
        return keyboardBitSet.get(f7.ordinal());
    }
    public boolean isKeyF8() {
        return keyboardBitSet.get(f8.ordinal());
    }
    public boolean isKeyF9() {
        return keyboardBitSet.get(f9.ordinal());
    }
    public boolean isKeyF10() {
        return keyboardBitSet.get(f10.ordinal());
    }
    public boolean isKeyF11() {
        return keyboardBitSet.get(f11.ordinal());
    }
    public boolean isKeyF12() {
        return keyboardBitSet.get(f12.ordinal());
    }


    //base keyboard input
    public boolean isKeyW() {
        return keyboardBitSet.get(moveUp.ordinal()) && !keyboardBitSet.get(moveDown.ordinal());
    }
    public boolean isKeyS() {
        return keyboardBitSet.get(moveDown.ordinal()) && !keyboardBitSet.get(moveUp.ordinal());
    }
    public boolean isKeyD() {
        return keyboardBitSet.get(moveRight.ordinal()) && !keyboardBitSet.get(moveLeft.ordinal());
    }
    public boolean isKeyA() {
        return keyboardBitSet.get(moveLeft.ordinal()) && !keyboardBitSet.get(moveRight.ordinal());
    }
    public boolean isKeyR() {
        return keyboardBitSet.get(r.ordinal());
    }
    public boolean isKeyQ() {
        return keyboardBitSet.get(q.ordinal());
    }
    public boolean isKeyE() {
        return keyboardBitSet.get(e.ordinal());
    }
    public boolean isKeyT() {
        return keyboardBitSet.get(t.ordinal());
    }
    public boolean isKeyF() {
        return keyboardBitSet.get(f.ordinal());
    }
    public boolean isKeyG() {
        return keyboardBitSet.get(g.ordinal());
    }
    public boolean isKeyZ() {
        return keyboardBitSet.get(z.ordinal());
    }
    public boolean isKeyX() {
        return keyboardBitSet.get(x.ordinal());
    }
    public boolean isKeyC() {
        return keyboardBitSet.get(c.ordinal());
    }
    public boolean isKeyV() {
        return keyboardBitSet.get(v.ordinal());
    }
    public boolean isKeyB() {
        return keyboardBitSet.get(b.ordinal());
    }
    public boolean isKeyN() {
        return keyboardBitSet.get(n.ordinal());
    }
    public boolean isKeyM() {
        return keyboardBitSet.get(m.ordinal());
    }
    public boolean isKeyH() {
        return keyboardBitSet.get(h.ordinal());
    }
    public boolean isKeyJ() {
        return keyboardBitSet.get(j.ordinal());
    }
    public boolean isKeyK() {
        return keyboardBitSet.get(k.ordinal());
    }
    public boolean isKeyL() {
        return keyboardBitSet.get(l.ordinal());
    }
    public boolean isKeyY() {
        return keyboardBitSet.get(y.ordinal());
    }
    public boolean isKeyU() {
        return keyboardBitSet.get(u.ordinal());
    }
    public boolean isKeyI() {
        return keyboardBitSet.get(i.ordinal());
    }
    public boolean isKeyO() {
        return keyboardBitSet.get(o.ordinal());
    }
    public boolean isKeyP() {
        return keyboardBitSet.get(p.ordinal());
    }


    //mouse input booleans
    public boolean onLeftClick() {
        if (mouseLeft) {
            mouseLeft = false;
            return keyboardBitSet.get(leftClick.ordinal() + mouseCodeOffset);
        }

        return false;
    }
    public boolean onLeftHold() {

     return keyboardBitSet.get(leftClick.ordinal() + mouseCodeOffset);
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

    //mouse coords
    public double getMouseX() { return mouseX; }
    public double getMouseY() { return mouseY; }


    //handlers
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
