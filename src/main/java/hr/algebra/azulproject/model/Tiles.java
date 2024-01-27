package hr.algebra.azulproject.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

public class Tiles implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private static final long SEED = 12345L;
    private static final Random random = new Random(SEED);
    private String color;
    private static final String[] COLORS = {"Black", "Blue", "Red", "Yellow", "LBlue"};
    private static final String[] IMAGE_PATHS = {
            "/images/Black.png",
            "/images/Blue.png",
            "/images/Red.png",
            "/images/Yellow.png",
            "/images/LBlue.png"
    };
    public Tiles(String color){

        this.color = color;
    }
    public Tiles(){
    }

    public String getColor(){
        return color;
    }
    public static String getImagePath(String color) {
        int index = getColorIndex(color);
        return IMAGE_PATHS[index];
    }
    private static int getColorIndex(String color){
        for (int i = 0; i < COLORS.length; i++){
            if (COLORS[i].equalsIgnoreCase(color)) {
                return i;
            }
        }
        return -1;
    }

    // Create list of tiles (100 pcs) and shuffle them
    public static List<Tiles> insertTiles(int totalTiles) {
        List<Tiles> tiles = new ArrayList<>();
        for (int i = 0; i < totalTiles; i++) {
            String color = COLORS[i % COLORS.length];
            tiles.add(new Tiles(color));
        }
        Collections.shuffle(tiles, random);
        return tiles;
    }
}
