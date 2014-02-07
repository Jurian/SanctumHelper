package gui;

import tower.TowerType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Jurian on 25-1-14.
 */
class TowerButton extends JButton implements ActionListener {
    private final TowerToolBar parentPanel;
    private final TowerType type;
    private boolean selected = false;

    public TowerButton(TowerType type, TowerToolBar parentPanel) {
        super();

        this.parentPanel = parentPanel;
        ImageIcon icon = new ImageIcon(type.location, type.description);
        this.type = type;

        setToolTipText(type.description);
        setBorder(BorderFactory.createEmptyBorder());
        setContentAreaFilled(false);
        addActionListener(this);
        setIcon(icon);
    }

    void deSelect(){
        if(selected) {
            selected = false;
            repaint();
        }
    }

    void select() {
        parentPanel.deselectCurrent(type);
        selected = true;
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        select();
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        if(selected) {
            Graphics2D g2d = (Graphics2D) graphics;
            g2d.setColor(new Color(1f, 1f, 1f, .9f));
            g2d.drawRoundRect(
                    0,0,
                    getWidth()-1,
                    getHeight()-1,
                    8,8);
            g2d.setColor(new Color(1f, 1f, 1f, .2f));
            g2d.fillRoundRect(
                    0,0,
                    getWidth()-1,
                    getHeight()-1,
                    8,8);
        }
    }
}
