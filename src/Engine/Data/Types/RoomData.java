package Engine.Data.Types;
import javafx.scene.image.Image;
import java.util.HashMap;

public class RoomData {

    //room data
    public Image[] image;
    public int[] layout;
    public int roomWidth;
    public int roomHeight;
    public int tileSize;

    //entity room data
    public HashMap<Integer, String> entityData;
    public int[] entityLayout;

    RoomData(Image[] tile, int[] layout, HashMap<Integer, String> entityData, int[] entityLayout, int roomWidth, int roomHeight, int tileSize) {
        this.image = tile;
        this.layout = layout;
        this.entityData = entityData;
        this.entityLayout = entityLayout;
        this.roomWidth = roomWidth;
        this.roomHeight = roomHeight;
        this.tileSize = tileSize;




    }

}
