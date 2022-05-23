package com.github.nondefraudat;

import java.util.Comparator;
import java.util.Vector;

public class MinimumSpanningTree extends ConnectedUndirectedWeightedGraph {
    private MinimumSpanningTree() {
        super();
    }

    private static class Node {
        Character leadingVertex;
        Character terminatingVertex;
        Double weight;

        public Node() {
            leadingVertex = null;
            terminatingVertex = null;
            weight = null;
        }
    }

    public static MinimumSpanningTree fromConnectedUndirectedWeightedGraph(ConnectedUndirectedWeightedGraph graph) {
        MinimumSpanningTree tree = new MinimumSpanningTree();
        Vector<GraphEdge> edges = graph.getEdges();
        edges.sort(new Comparator<GraphEdge>() {
            @Override
            public int compare(GraphEdge left, GraphEdge right) {
                return left.getWeight().compareTo(right.getWeight());
            }
        });
        for (GraphEdge edge : edges) {
            tree.setEdge(edge);
            if (tree.isLooped()) {
                tree.deleteEdge(edge);
            }
        }
        return tree;
    }
}
