import Game.Game;
import JsonComponents.JsonGenerator;
import javafx.application.Application;

public class Main {

    public static void main(String[] args) {
        //really nice that javafx has this built in method
        Application.launch(Game.class, args);
    }
}
