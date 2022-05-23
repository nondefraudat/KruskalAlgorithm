package com.github.nondefraudat;

import java.util.HashMap;
import java.lang.Character;
import java.lang.Double;
import java.util.Set;
import java.util.Vector;

public class ConnectedUndirectedWeightedGraph {
    private HashMap<Character, HashMap<Character, Double>> weights;

    public static ConnectedUndirectedWeightedGraph fromStringLines(Vector<String> lines) {
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

    public Vector<String> toStringLines() {
        Vector<String> lines = new Vector<>();
        Set<Character> vertices = weights.keySet();
        Vector<Character> usedVertices = new Vector<>();
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
        HashMap<Character, Double> buffer;
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

    public Vector<Character> getVerticesNames() {
        Vector<Character> vertices = new Vector<>();
        for (Character key : weights.keySet()) {
            vertices.add(key);
        }
        return vertices;
    }

    public Double getEdgeWeight(Character leadingVertex, Character terminatingVertex) {
        return weights.get(leadingVertex).get(terminatingVertex);
    }

    public Vector<GraphEdge> getEdges() {
        Vector<GraphEdge> edges = new Vector<>();
        Set<Character> vertices = weights.keySet();
        Vector<Character> usedVertices = new Vector<>();
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
        for (Character vertex : weights.keySet()) {
            if(isLooped(vertex, null, vertex)) {
                return true;
            }
        }
        return false;
    }

    private Boolean isLooped(Character current, Character prev, Character target) {
        Set<Character> buffer = weights.get(current).keySet();
        for (Character vertex : buffer) {
            if (vertex != prev && getEdgeWeight(current, vertex) != null) {
                if (vertex == target) {
                    return true;
                }
                return isLooped(vertex, current, target);
            }
        }
        return false;
    }
}

