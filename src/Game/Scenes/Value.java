package Game.Scenes;

public enum Value {

    //font sizes
    SMALL("12px"),
    MEDIUM("24px"),
    LARGE("48px"),

    //font families
    TIMES_NEW_ROMAN("'Times New Roman'"),
    ARIAL("Arial"),
    VERDANA("Verdana"),
    MONOSPACE("Monospace"),

    //font weight
    BOLD("bold"),
    NORMAL("normal"),

    //text alignment
    TEXT_LEFT("left"),
    TEXT_RIGHT("right"),
    TEXT_CENTER("center"),
    TEXT_JUSTIFY("justify"),

    //colors
    BLACK("black"),
    RED("red"),
    BLUE("blue"),
    WHITE("white"),
    YELLOW("yellow"),
    GREEN("green"),

    //cursor states
    CURSOR_DEFAULT("default"),
    CURSOR_POINTER("pointer"),
    CURSOR_CROSSHAIR("crosshair"),
    CURSOR_MOVE("move"),
    CURSOR_TEXT("text"),
    CURSOR_HELP("help"),
    CURSOR_WAIT("wait"),
    CURSOR_NONE("none");

    //getting the enum
    private final String value;

    public String getValue() {
        return value;
    }

    Value(String value) {
        this.value = value;
    }

}
