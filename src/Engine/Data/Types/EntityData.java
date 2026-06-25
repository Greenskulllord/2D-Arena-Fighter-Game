package Engine.Data.Types;
import com.google.gson.annotations.SerializedName;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//class to hold a bunch of data during the game loop
public class EntityData {
    //entity data
    @SerializedName("width")
    public int width;

    @SerializedName("height")
    public int height;

    @SerializedName("health")
    public int health;

    @SerializedName("max_health")
    public int maxHealth;

    @SerializedName("damage")
    public int damage;

    @SerializedName("speed")
    public double speed;

    @SerializedName("attack_speed")
    public double attackSpeed;

    @SerializedName("attack_force")
    public double attackForce;

    @SerializedName("dash_speed")
    public double dashSpeed;

    @SerializedName("dash_duration")
    public double dashDuration;

    @SerializedName("collide_white_list")
    public String[] collisionWhiteList;

    @SerializedName("image")
    public Image image;


    //a record class is a simple data carrier
    public record EntityList(String entityTemplateFile, String entityList) {};
    //list containing entity templates(data) and names
    public static ArrayList<EntityList> entityLists = new ArrayList<>();

    public EntityData(
            /* Entity Data */ int width, int height, String[] collisionWhiteList, Image image, int health, int damage,
                              int maxHealth, double speed, double attackSpeed, double attackForce,
                              double dashSpeed, double dashDuration) {

        //entity data
        this.image = image;
        this.collisionWhiteList = collisionWhiteList;
        this.width = width;
        this.height = height;
        this.health = health;
        this.maxHealth = maxHealth;
        this.damage = damage;
        this.speed = speed;
        this.attackSpeed = attackSpeed;
        this.attackForce = attackForce;
        this.dashSpeed = dashSpeed;
        this.dashDuration = dashDuration;

    }

    //helper method to add entities to the master template lists
    public static void addToEntityList(String File, String entity) {
        entityLists.add(new EntityList(File, entity));
    }
}
