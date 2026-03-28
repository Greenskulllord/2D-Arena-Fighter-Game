package keyboardInput;

import javafx.scene.input.KeyCode;

import java.util.BitSet;

public class Input {
    //a bit set can hold a bunch of codes as booleans
    private BitSet bitset = new BitSet();

    //keycodes simplified
    private KeyCode moveUp = KeyCode.W;
    private KeyCode moveDown = KeyCode.S;
    private KeyCode moveLeft = KeyCode.A;
    private KeyCode moveRight = KeyCode.D;


}
