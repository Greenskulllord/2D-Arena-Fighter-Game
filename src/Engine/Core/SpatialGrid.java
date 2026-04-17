package Engine.Core;

import javafx.scene.layout.Pane;

public class SpatialGrid {
    /*todo when game can't handle all of the collision math
        write this code called spatial hashing or spatial grids
        link: https://www.scitepress.org/Papers/2024/124157/124157.pdf
        link 2: https://cpoli.live/blog/2025/spatial-hashing/
     */

    private int cellSize;
    private Pane pane;

    SpatialGrid(Pane pane, int cellSize) {
        this.pane = pane;
        this.cellSize = cellSize;

    }

}
