package gui;

import maze.Maze;
import maze.MazeNode;
import pathfinder.GraphNode;
import pathfinder.GraphSearch_Astar;
import tower.Tower;
import tower.TowerType;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Jurian on 26-1-14.
 */
class MazePanel extends JPanel implements MouseMotionListener, MouseInputListener, MouseWheelListener {
    private static final int PADDING = 6, TILE_SIZE = 64;
    private final Maze maze;
    private static final Map<TowerType, ImageIcon> imageIconRegistry = createIconRegistry();
    private List<GraphNode> path;
    private int towerCreateLevel = 1;
    private boolean replaceTower = false;

    private static Map<TowerType, ImageIcon> createIconRegistry() {
        Map<TowerType, ImageIcon> map = new HashMap<>();
        for(TowerType type : TowerType.values())
            map.put(type, new ImageIcon(type.location, type.description));
        return map;
    }

    public MazePanel(){
        addMouseMotionListener(this);
        addMouseListener(this);
        addMouseWheelListener(this);
        setBackground(Color.white);
        setupActionMaps();

        maze = new Maze();
        calculatePath();
    }

    private void setupActionMaps(){
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_SHIFT, InputEvent.SHIFT_MASK), "replaceTower");
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_SHIFT, 0, true), "replaceTowerNot");
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_1, 0), "setTowerLevel");
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_2, 0), "setTowerLevel");
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_3, 0), "setTowerLevel");
        getActionMap().put("setTowerLevel", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                towerCreateLevel = Integer.parseInt(e.getActionCommand());
            }
        });
        getActionMap().put("replaceTower", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                replaceTower = true;
            }
        });
        getActionMap().put("replaceTowerNot", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                replaceTower = false;
            }
        });
    }

    void calculatePath(){
        GraphSearch_Astar pathfinder = new GraphSearch_Astar(maze.getGraph());
        path = pathfinder.search(0, 99);
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        for(MazeNode node : maze.getNodes()){
            final int x = node.x * TILE_SIZE;
            final int y = node.y * TILE_SIZE;
            graphics.setColor(node.getColor());
            graphics.fillRoundRect(
                    PADDING + x,
                    PADDING + y,
                    TILE_SIZE,
                    TILE_SIZE,
                    8,8);

            final Tower tower = node.getTower();
            if(tower != null) {
                graphics.setColor(tower.getBaseColor());
                graphics.fillRoundRect(
                        PADDING + x,
                        PADDING + y,
                        TILE_SIZE,
                        TILE_SIZE,
                        8,8);

                imageIconRegistry.get(tower.type).paintIcon(this, graphics, PADDING + x, PADDING + y);
            }

            graphics.setColor(Color.black);
            graphics.drawRoundRect(
                    PADDING + x,
                    PADDING + y,
                    TILE_SIZE,
                    TILE_SIZE,
                    8,8);
        }

        if(path != null) {
            graphics.setColor(Color.red);
            for(GraphNode node : path) {
                final int x = (int) node.x() * TILE_SIZE;
                final int y = (int) node.y() * TILE_SIZE;

                graphics.fillOval(
                        PADDING + x + TILE_SIZE / 2 - 5,
                        PADDING + y + TILE_SIZE / 2 - 5,
                        10,
                        10
                );
            }
        }
    }

    private void addTower(int x, int y){
        MazeNode node = maze.getMazeNode(x, y);

        if(replaceTower){
            node.removeTower();
        }

        if(maze.isFree(x,y)) {
            final Tower tower = new Tower(TowerToolBar.getSelectedTower(), maze.getMazeNode(x,y), towerCreateLevel);
            node.setTower(tower);
            node.removeEdges(maze.getGraph());

            calculatePath();

            repaint();
        }
    }

    private void removeTower(int x, int y){
        MazeNode node = maze.getMazeNode(x, y);
        if(node != null && !node.isFree()) {
            node.removeTower();
            node.addEdges(maze.getGraph());

            calculatePath();

            repaint();
        }
    }

    private void upgradeTower(int x, int y){
        MazeNode node = maze.getMazeNode(x, y);
        if(node != null && !node.isFree()) {
            final Tower tower = node.getTower();
            tower.addLevel();
            repaint();
        }
    }

    private void downgradeTower(int x, int y) {
        MazeNode node = maze.getMazeNode(x, y);
        if(node != null && !node.isFree()) {
            final Tower tower = node.getTower();
            tower.lowerLevel();
            repaint();
        }
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        final int x = (mouseEvent.getX()- PADDING ) / TILE_SIZE;
        final int y = (mouseEvent.getY() - PADDING ) / TILE_SIZE;

        if(SwingUtilities.isLeftMouseButton(mouseEvent)){
            addTower(x, y);
        }else if(SwingUtilities.isRightMouseButton(mouseEvent)) {
            removeTower(x, y);
        }
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        final int x = (mouseEvent.getX()- PADDING ) / TILE_SIZE;
        final int y = (mouseEvent.getY() - PADDING ) / TILE_SIZE;

        if(SwingUtilities.isLeftMouseButton(mouseEvent)){
            addTower(x, y);
        }else if(SwingUtilities.isRightMouseButton(mouseEvent)) {
            removeTower(x,y);
        }else if(mouseEvent.getButton() == 5) {
            upgradeTower(x, y);
        }else if(mouseEvent.getButton() == 4) {
            downgradeTower(x, y);
        }
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent mouseWheelEvent) {
        final int x = (mouseWheelEvent.getX()- PADDING ) / TILE_SIZE;
        final int y = (mouseWheelEvent.getY() - PADDING ) / TILE_SIZE;

        if(mouseWheelEvent.getWheelRotation() > 0) {
            downgradeTower(x, y);
        }else if(mouseWheelEvent.getWheelRotation() < 0) {
            upgradeTower(x, y);
        }
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {}

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {}

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {}

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {}

    @Override
    public void mouseExited(MouseEvent mouseEvent) {}
}
