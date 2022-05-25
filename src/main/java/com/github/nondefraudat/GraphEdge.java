package com.github.nondefraudat;

// Ребро графа, структура данных, в форме класса
// хранит 2 узла, с которыми связано ребро и значение ребра
public class GraphEdge {
    private Character leadingVertex;
    private Character terminatingVertex;
    private Double weight;

    public GraphEdge(Character leadingVertex, Character terminatingVertex, Double weight) {
        this.leadingVertex = leadingVertex;
        this.terminatingVertex = terminatingVertex;
        this.weight = weight;
    }

    public Character getLeadingVertex() {
        return leadingVertex;
    }

    public Character getTerminatingVertex() {
        return terminatingVertex;
    }

    public Double getWeight() {
        return weight;
    }
}
