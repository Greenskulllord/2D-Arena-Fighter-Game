package Game.Scenes;

public enum Style {
    //all the style options

    //fonts
    FONT_SIZE("-fx-font-size"),
    FONT_FAMILY("-fx-font-family"),
    FONT_WEIGHT("-fx-font-weight"),
    TEXT_FILL("-fx-text-fill"),
    TEXT_ALIGNMENT("-fx-text-alignment"),


    //background and border
    BACKGROUND_COLOR("-fx-background-color"),
    BACKGROUND_RADIUS("-fx-background-radius"),
    BORDER_COLOR("-fx-border-color"),
    BORDER_WIDTH("-fx-border-width"),
    BORDER_RADIUS("-fx-border-radius"),


    //spacing and size
    PADDING("-fx-padding"),
    SPACING("-fx-spacing"),
    MIN_WIDTH("-fx-min-width"),
    MAX_WIDTH("-fx-max-width"),
    PREF_WIDTH("-fx-pref-width"),
    MIN_HEIGHT("-fx-min-height"),
    MAX_HEIGHT("-fx-max-height"),
    PREF_HEIGHT("-fx-pref-height"),


    //effects
    OPACITY("-fx-opacity"),
    CURSOR("-fx-cursor");


    //getting the enum
    private final String value;

    Style(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public String of(Style property, String value) {
        return property.getValue() + ": " + value + "; ";
    }
}


