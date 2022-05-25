package com.github.nondefraudat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConsoleUI {
    private Scanner scanner;
    private Boolean isLooped;

    public ConsoleUI(Scanner scanner) {
        this.scanner = scanner;
    }

    public void startHandlingLoop() {
        isLooped = true;
        showInfo();
        while (isLooped) {
            System.out.print("> ");
            handleUserInput(scanner.nextLine());
        }
    }

    private static void showInfo() {
        System.out.println("= KRASKAL TREE EXTRACTOR");
        System.out.println("- Type 'typehere' to start graph input");
        System.out.println("- Type 'readfile <file path>' to read graph from file");
        System.out.println("- Type 'exit' to terminate program");
    }

    private void handleUserInput(String userInput) {
        switch (userInput) {
            case "exit": {
                isLooped = false;
                return;
            }
            case "info": {
                showInfo();
                break;
            }
            case "typehere": {
                showGraphHandling(extractStringLinesFromConsole());
                break;
            }
            default: {
                if (userInput.contains("readfile ")) {
                    try {
                        showGraphHandling(extractStringLinesFromFile(userInput.replace("readfile ", "")));
                    } catch(IOException e) {
                        System.out.println("E: Cannot open file '" + userInput.replace("readfile ", "") + "'");
                        System.out.println("   file not found");
                    }
                }
                else {
                    System.out.println("E: Unknown expression '" + userInput + "'");
                    System.out.println("   type 'info' to show command list");
                }
                break;
            }
        }
        return;
    }

    private List<String> extractStringLinesFromConsole() {
        System.out.println("! type '$' from new string to end input");
        List<String> lines = new ArrayList<>();
        do {
            lines.add(scanner.nextLine());
        } while(!lines.get(lines.size() - 1).equals("$"));
        lines.remove(lines.size() - 1);
        return lines;
    }

    private List<String> extractStringLinesFromFile(String filePath) throws IOException {
        List<String> lines = new ArrayList<>();
        for (String line : Files.readString(Path.of(filePath)).split("\n")) {
            lines.add(line);
        }
        return lines;
    }

    private void showGraphHandling(List<String> lines) {
        ConnectedUndirectedWeightedGraph graph = ConnectedUndirectedWeightedGraph.fromStringLines(lines);
        showGraph(graph, "Source graph:");
        showGraph(graph.extractMinimumSpanningTree(), "Target tree:");
    }

    private void showGraph(ConnectedUndirectedWeightedGraph graph, String head) {
        System.out.println(head);
        for (String line : graph.toStringLines()) {
           System.out.println(line);
        }
        System.out.println();
    }
}
