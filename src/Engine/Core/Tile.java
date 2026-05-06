package Engine.Core;
import javafx.scene.image.Image;

/**
 * The {@code Tile} class represents a static visual element in the environment,
 * used for backgrounds or non-interactive terrain.
 */
public class Tile {
    /** The image texture associated with this tile. */
    public Image image;

    /**
     * Constructs a new {@code Tile} with a specified image.
     *
     * @param tileImage The {@link Image} to be used as the tile's texture.
     */
    public Tile(Image tileImage){
        image = tileImage;
    }
}