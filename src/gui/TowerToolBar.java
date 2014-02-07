package gui;

import tower.TowerType;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jurian on 25-1-14.
 */
class TowerToolBar extends JPanel {
    private final Map<TowerType, TowerButton> towerButtons = new HashMap<>();
    private static TowerType selectedTower = TowerType.BASE;

    public TowerToolBar() {
        setBackground(Color.black);
        GridLayout gridLayout = new GridLayout(0, 2, 4, 4);
        setLayout(gridLayout);
        for(TowerType type : TowerType.values())
            addTowerButton(type);
        towerButtons.get(TowerType.BASE).select();
    }

    private void addTowerButton(TowerType type) {
        final TowerButton tb = new TowerButton(type, this);
        tb.setAlignmentX(LEFT_ALIGNMENT);
        towerButtons.put(type, tb);
        add(tb);
    }

    void deselectCurrent(TowerType newSelection) {
        towerButtons.get(selectedTower).deSelect();
        selectedTower = newSelection;
    }

    public static TowerType getSelectedTower(){
        return selectedTower;
    }
}
