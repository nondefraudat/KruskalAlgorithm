package com.github.nondefraudat;

import java.util.*;
import java.lang.Character;
import java.lang.Double;

public class ConnectedUndirectedWeightedGraph {
    private Map<Character, Map<Character, Double>> weights;

    public static ConnectedUndirectedWeightedGraph fromStringLines(List<String> lines) {
        ConnectedUndirectedWeightedGraph graph = new ConnectedUndirectedWeightedGraph();
        for (String line : lines) {
            String[] buffer = line.split(" ");
            graph.setEdgeWeight(buffer[0].charAt(0), buffer[0].charAt(1), Double.parseDouble(buffer[1]));
        }
        return graph;
    }

    public ConnectedUndirectedWeightedGraph() {
        weights = new HashMap<>();
    }

    public List<String> toStringLines() {
        List<String> lines = new ArrayList<>();
        Set<Character> vertices = weights.keySet();
        List<Character> usedVertices = new ArrayList<>();
        for (Character leadingVertex : vertices) {
            usedVertices.add(leadingVertex);
            for (Character terminatingVertex : vertices) {
                if (!usedVertices.contains(terminatingVertex)) {
                    Double buffer = weights.get(leadingVertex).get(terminatingVertex);
                    if (buffer != null) {
                        lines.add(leadingVertex.toString() + terminatingVertex.toString() + " " + buffer);
                    }
                }
            }
        }
        return lines;
    }

    public void setEdge(GraphEdge edge) {
        setEdgeWeight(edge.getLeadingVertex(), edge.getTerminatingVertex(), edge.getWeight());
    }

    public void setEdgeWeight(Character firstVertex, Character secondVertex, Double weight) {
        setEdgeWeightDirected(firstVertex, secondVertex, weight);
        setEdgeWeightDirected(secondVertex, firstVertex, weight);
    }

    private void setEdgeWeightDirected(Character firstVertex, Character secondVertex, Double weight) {
        Map<Character, Double> buffer;
        buffer = weights.get(firstVertex);
        if (buffer != null) {
            buffer.put(secondVertex, weight);
        }
        else {
            weights.put(firstVertex, new HashMap<>());
            weights.get(firstVertex).put(secondVertex, weight);
        }
    }

    public void deleteEdge(GraphEdge edge) {
        deleteEdge(edge.getLeadingVertex(), edge.getTerminatingVertex());
    }

    public void deleteEdge(Character leadingVertex, Character terminatingVertex) {
        setEdgeWeight(leadingVertex, terminatingVertex, null);
    }

    public List<Character> getVerticesNames() {
        List<Character> vertices = new ArrayList<>();
        for (Character key : weights.keySet()) {
            vertices.add(key);
        }
        return vertices;
    }

    public Double getEdgeWeight(Character leadingVertex, Character terminatingVertex) {
        return weights.get(leadingVertex).get(terminatingVertex);
    }

    public List<GraphEdge> getEdges() {
        List<GraphEdge> edges = new ArrayList<>();
        Set<Character> vertices = weights.keySet();
        List<Character> usedVertices = new ArrayList<>();
        for (Character leadingVertex : vertices) {
            usedVertices.add(leadingVertex);
            for (Character terminatingVertex : vertices) {
                if (!usedVertices.contains(terminatingVertex)) {
                    Double buffer = weights.get(leadingVertex).get(terminatingVertex);
                    if (buffer != null) {
                        edges.add(new GraphEdge(leadingVertex, terminatingVertex, buffer));
                    }
                }
            }
        }
        return edges;
    }

    public Boolean isLooped() {
        Character randomVertex = null;
        for (Character vertex : weights.keySet()) {
            randomVertex = vertex;
            break;
        }
        return isLooped(randomVertex, null, new ArrayList<>());
    }

    private Boolean isLooped(Character next, Character prev, List<Character> usedVertices) {
        for (Character target : weights.get(next).keySet()) {
            if (target != prev && getEdgeWeight(next, target) != null) {
                if (usedVertices.contains(target) || isLooped(target, next, usedVertices)) {
                    return true;
                }
                usedVertices.add(target);
            }
        }
        return false;
    }

    public ConnectedUndirectedWeightedGraph extractMinimumSpanningTree() {
        ConnectedUndirectedWeightedGraph tree = new ConnectedUndirectedWeightedGraph();
        List<GraphEdge> edges = getEdges();
        edges.sort(new Comparator<>() {
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

