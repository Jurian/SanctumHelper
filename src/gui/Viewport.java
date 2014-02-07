package gui;


import javax.swing.*;
import java.awt.*;

/**
 * Created by Jurian on 25-1-14.
 */
public class Viewport extends JFrame{
    private static final Viewport ourInstance = new Viewport();

    private static Viewport getInstance() {
        return ourInstance;
    }

    private Viewport() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Sanctum Maze Designer");
        setSize(1200, 800);

        initComponents();
    }

    private void initComponents() {
        TowerToolBar towerToolBar = new TowerToolBar();
        JPanel sidePanel = new JPanel();
        sidePanel.setBackground(Color.black);
        sidePanel.add(towerToolBar);

        MazePanel mazePanel = new MazePanel();
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, sidePanel, mazePanel);
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(146);
        splitPane.setDividerSize(3);

        getContentPane().add(splitPane);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Viewport.getInstance().setVisible(true);
        });
    }
}
