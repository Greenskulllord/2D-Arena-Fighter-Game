package Game.Initialization;
import Engine.Data.DataBase;
import Engine.Data.EntityData;
import Game.Main.Game;
import javafx.application.Application;
import java.io.FileNotFoundException;

public class Initialization {
    public static void main(String[] args) throws FileNotFoundException {

        /*
        ==============================
           Load everything in game
        ==============================
        */



        //add entity templates
        EntityData.addToEntityList("EntitiesTemplates/player_template.json", "player");
        EntityData.addToEntityList("EntitiesTemplates/enemy_template.json", "enemy");
        EntityData.addToEntityList("EntitiesTemplates/wall_template.json", "wall");
        EntityData.addToEntityList("EntitiesTemplates/attackHitBox_template.json", "attackBox");

        //add tile sets
        EntityData.addToEntityList("tile_set_template.json", "tile");

        //add entity sets
        EntityData.addToEntityList("entity_set_template.json", "entity");

        //add rooms
        EntityData.addToEntityList("Rooms/test_room.json", "room");
        EntityData.addToEntityList("Rooms/room_1.json", "room0");

        //animation sets
        EntityData.addToEntityList("Animation/slash_keyframes.json", "animation_slash");

        //load database
        DataBase.loadDatabase();

        //really nice that javafx has this built in method
        Application.launch(Game.class, args);
    }
}
