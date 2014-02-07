package maze;

import pathfinder.Graph;
import pathfinder.GraphNode;


import java.awt.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by Jurian on 26-1-14.
 */
public class Maze implements Serializable {
    public final Random random = new Random();
    private final Map<Dimension, MazeNode> mazeNodes;
    private final transient Graph graph;

    public Maze() {
        graph = new Graph();
        mazeNodes = new HashMap<>();

        int id = 0;
        for(int y = 0; y < 10; y++) {
            for(int x = 0; x < 10; x++) {
                final GraphNode gNode = new GraphNode(id, x, y, 0);
                graph.addNode(gNode);
                mazeNodes.put(new Dimension(x, y),
                        new MazeNode(x, y, 0, random.nextBoolean(),gNode));
                id++;
            }
        }

        for(MazeNode node : mazeNodes.values()) {
            final MazeNode top = getMazeNode(node.x, node.y - 1);
            final MazeNode right = getMazeNode(node.x + 1, node.y);
            final MazeNode bottom = getMazeNode(node.x, node.y + 1);
            final MazeNode left = getMazeNode(node.x - 1, node.y);

            if(top != null){
                node.addNeighbour(top.graphNode, MazeNode.Neighbour.TOP);
                graph.addEdge(node.graphNode.id(), top.graphNode.id(), 0);
            }
            if(right != null){
                node.addNeighbour(right.graphNode, MazeNode.Neighbour.RIGHT);
                graph.addEdge(node.graphNode.id(), right.graphNode.id(), 0);
            }
            if(bottom != null){
                node.addNeighbour(bottom.graphNode, MazeNode.Neighbour.BOTTOM);
                graph.addEdge(node.graphNode.id(), bottom.graphNode.id(), 0);
            }
            if(left != null){
                node.addNeighbour(left.graphNode, MazeNode.Neighbour.LEFT);
                graph.addEdge(node.graphNode.id(), left.graphNode.id(), 0);
            }
        }

        graph.compact();
    }

    public Collection<MazeNode> getNodes() {
        return mazeNodes.values();
    }

    public int getSize() {
        return graph.getNbrNodes();
    }

    public Graph getGraph() {
        return graph;
    }

    public MazeNode getMazeNode(int x, int y) {
        return mazeNodes.get(new Dimension(x, y));
    }

    public boolean isFree(int x, int y) {
        MazeNode node = getMazeNode(x,y);
        return node != null && node.isFree();
    }
}