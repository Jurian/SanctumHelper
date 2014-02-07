package maze;

import pathfinder.Graph;
import pathfinder.GraphNode;
import tower.Tower;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Jurian on 26-1-14.
 */
public class MazeNode implements Serializable {
    enum Neighbour{
        TOP,RIGHT,BOTTOM,LEFT;
    }

    public final int x ,y , z;
    public final GraphNode graphNode;
    public final boolean buildable;

    private Tower tower;
    private final GraphNode[] neighbours = new GraphNode[4];


    public MazeNode(int x, int y, int z, boolean buildable, GraphNode graphNode) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.graphNode = graphNode;
        this.buildable = buildable;

    }

    public void addNeighbour(GraphNode node, Neighbour location){
        neighbours[location.ordinal()] = node;
    }

    public boolean isFree(){
        return buildable && tower == null;
    }

    public void removeTower() {
        tower = null;
    }

    public Tower getTower() {
        return tower;
    }

    public void setTower(Tower tower) {
        this.tower = tower;
    }

    public void removeEdges(Graph graph){
        for(GraphNode node : neighbours)
            if(node != null)
                graph.removeEdge(graphNode.id(), node.id());
    }

    public void addEdges(Graph graph) {
        for(GraphNode node : neighbours)
            if(node != null)
                graph.addEdge(graphNode.id(), node.id(), 0);
    }

    public Color getColor() {
        return buildable ? Color.white : Color.lightGray;
    }
}
