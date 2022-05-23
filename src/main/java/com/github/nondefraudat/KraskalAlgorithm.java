package com.github.nondefraudat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.Vector;

public class KraskalAlgorithm {
    static Boolean isApplicationLooped = true;
    static Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        showInfo();
        while (isApplicationLooped) {
            handleLine();
        }
    }

    private static void showInfo() {
        System.out.println("");
        System.out.println("= KRASKAL TREE EXTRACTOR");
        System.out.println("- Type 'typehere' to start graph input");
        System.out.println("- Type 'readfile <file path>' to read graph from file");
        System.out.println("- Type 'exit' to terminate program");
    }

    private static void handleLine() {
        System.out.print("> ");
        String line = in.nextLine();
        if (line.equals("exit")) {
            unloopApplication();
        }
        else if (line.equals("info")) {
            showInfo();
        }
        else if (line.contains("readfile ")) {
            ConnectedUndirectedWeightedGraph graph = ConnectedUndirectedWeightedGraph.fromStringLines(readFile(line.replace("readfile ", "")));
            System.out.println("Source graph:");
            writeConsole(graph.toStringLines());
            System.out.println("\nTarget tree:");
            writeConsole(MinimumSpanningTree.fromConnectedUndirectedWeightedGraph(graph).toStringLines());
        }
        else if (line.equals("typehere")) {
            ConnectedUndirectedWeightedGraph graph = ConnectedUndirectedWeightedGraph.fromStringLines(readConsole());
            System.out.println("Source graph:");
            writeConsole(graph.toStringLines());
            System.out.println("\nTarget tree:");
            writeConsole(MinimumSpanningTree.fromConnectedUndirectedWeightedGraph(graph).toStringLines());
        }
        else {
            System.out.println("E: Unknown command, try 'info' command");
        }
    }

    private static void unloopApplication() {
        isApplicationLooped = false;
    }

    private static Vector<String> readConsole() {
        System.out.println("! type '$' from new string to end input");
        Vector<String> lines = new Vector<>();
        do {
            lines.add(in.nextLine());
        // while last elem != '$'
        } while(!lines.get(lines.size() - 1).equals("$"));
        lines.remove(lines.size() - 1);
        return lines;
    }

    private static void writeConsole(Vector<String> lines) {
        for (String line : lines) {
            System.out.println(line);
        }
    }

    private static Vector<String> readFile(String filePath) {
        Vector<String> lines = new Vector<>();
        try {
            Path path = Path.of(filePath);
            String content = Files.readString(path);
            String buffer[] = content.split("\n");
            for (String unit : buffer) {
                lines.add(unit);
            }
        } catch (IOException e) {
            lines.add("file reading error");
        }
        return lines;
    }
}
