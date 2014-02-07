package tower;


import maze.MazeNode;

import java.awt.*;
import java.io.Serializable;

/**
 * Created by Jurian on 25-1-14.
 */
public class Tower implements Serializable {
    private static final int MAX_LEVEL = 3;
    private static final Color BASE_COLOR = Color.yellow;
    private static final Color[] LEVEL_COLORS = createLevelColors();
    public final MazeNode node;
    public final TowerType type;
    private int level = 1;

    public Tower(TowerType type, MazeNode node) {
        this(type, node, 1);
    }

    public Tower(TowerType type, MazeNode node, int level) {
        this.type = type;
        this.node = node;
        this.level = level;
    }

    public void addLevel(){
        if(type != TowerType.BASE && level < MAX_LEVEL)
            level++;
    }

    public void lowerLevel(){
        if(type != TowerType.BASE && level > 1) {
            level--;
        }
    }

    public int getLevel(){
        return level;
    }

    public Color getBaseColor(){
        if(type == TowerType.BASE)
            return Color.white;
        return LEVEL_COLORS[level-1];
    }

    private static Color[] createLevelColors(){
        Color[] colors = new Color[MAX_LEVEL];
        final float[] hsv = new float[3];
        Color.RGBtoHSB(BASE_COLOR.getRed(), BASE_COLOR.getGreen(), BASE_COLOR.getBlue(), hsv);
        for(int level = 0; level < MAX_LEVEL; level++){
            colors[level] = new Color(Color.HSBtoRGB(hsv[0] - level * .05f ,hsv[1],hsv[2]));
        }
        return colors;
    }
}
