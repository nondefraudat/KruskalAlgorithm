package com.github.nondefraudat;

import java.util.*;
import java.lang.Character;
import java.lang.Double;

// Связный ненаправленный взвешенный граф
// основной класс работы с графами
public class ConnectedUndirectedWeightedGraph {
    // Фактически список списка связей каждого узла и вес этих связей
    // записи вида AB 10 BA 10 присутствуют
    // записи вида AA 1 допускаются
    private Map<Character, Map<Character, Double>> weights;

    // Статический метод преобразования пользовательского ввода в граф
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

    // Преобразование графа в читаемый вывод
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

    // Назначение ребра
    public void setEdge(GraphEdge edge) {
        setEdgeWeight(edge.getLeadingVertex(), edge.getTerminatingVertex(), edge.getWeight());
    }

    // Назначение длины ребра
    public void setEdgeWeight(Character firstVertex, Character secondVertex, Double weight) {
        setEdgeWeightDirected(firstVertex, secondVertex, weight);
        setEdgeWeightDirected(secondVertex, firstVertex, weight);
    }

    // Назначение длины ребра в одном направлении, функция создана для исключения повторяющегося кода
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

    // Назначение пустого значения ребру
    public void deleteEdge(GraphEdge edge) {
        deleteEdge(edge.getLeadingVertex(), edge.getTerminatingVertex());
    }

    // Назначение пустого значения ребру
    public void deleteEdge(Character leadingVertex, Character terminatingVertex) {
        setEdgeWeight(leadingVertex, terminatingVertex, null);
    }

    // Получение списка наименований узлов
    public List<Character> getVerticesNames() {
        List<Character> vertices = new ArrayList<>();
        for (Character key : weights.keySet()) {
            vertices.add(key);
        }
        return vertices;
    }

    // Получение значения ребра
    public Double getEdgeWeight(Character leadingVertex, Character terminatingVertex) {
        return weights.get(leadingVertex).get(terminatingVertex);
    }

    // Получение списка ребер
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

    // Возврощает true, если граф зациклен
    public Boolean isLooped() {
        for (Character vertex : weights.keySet()) {
            return isLooped(vertex, null, new ArrayList<>());
        }
        return false;
    }

    // Рекурсия для определения зацикленности графа
    private Boolean isLooped(Character next, Character prev, List<Character> usedVertices) {
        usedVertices.add(next);
        for (Character target : weights.get(next).keySet()) {
            if (target != prev && getEdgeWeight(next, target) != null) {
                if (usedVertices.contains(target) || isLooped(target, next, usedVertices)) {
                    return true;
                }
            }
        }
        return false;
    }

    // Извлечение из графа, графа вида минимального остного дерева
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

