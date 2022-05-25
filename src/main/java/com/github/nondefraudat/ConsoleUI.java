package com.github.nondefraudat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Пользовательский интерфейс
public class ConsoleUI {
    // Стандартный сканер, вынесен чтобы не плодились лишние объекты
    private Scanner scanner;
    // Флаг цикла, проограмма работает, пока он true
    private Boolean isLooped;

    public ConsoleUI(Scanner scanner) {
        this.scanner = scanner;
    }

    // Цикл обработки команд
    // предполагается что программа завершается тоько при вводе
    // комманды 'exit' пользователем
    public void startHandlingLoop() {
        isLooped = true;
        showInfo();
        while (isLooped) {
            System.out.print("> ");
            handleUserInput(scanner.nextLine());
        }
    }

    // Вывод основной информации о работе программы
    private static void showInfo() {
        System.out.println("= KRASKAL TREE EXTRACTOR");
        System.out.println("- Type 'typehere' to start graph input");
        System.out.println("- Type 'readfile <file path>' to read graph from file");
        System.out.println("- Type 'exit' to terminate program");
    }

    // Функия обработки команды
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

    // Функция для чтения графов из пользовательского ввода,
    // записывает в список строки(lines) пока последняя строка не '$'
    // полученный список возвращается без '$'
    private List<String> extractStringLinesFromConsole() {
        System.out.println("! type '$' from new string to end input");
        List<String> lines = new ArrayList<>();
        do {
            lines.add(scanner.nextLine());
        } while(!lines.get(lines.size() - 1).equals("$"));
        lines.remove(lines.size() - 1);
        return lines;
    }

    // Функция для чтения графа из файла
    // считывает весь текст из файла
    // и разбивает на отдельные строки в список
    private List<String> extractStringLinesFromFile(String filePath) throws IOException {
        List<String> lines = new ArrayList<>();
        for (String line : Files.readString(Path.of(filePath)).split("\n")) {
            lines.add(line);
        }
        return lines;
    }

    // Функция обработки графа
    // граф из списка строк формируется в объект
    // ConnectedUndirectedWeightedGraph
    // (связный ненаправленный взвешенный граф)
    // полученный граф выводится(было сделано для отладки)
    // затем преобразуется в минимальное остное дерево, на основе тогоже объекта графа
    // и выводится, что и должно являться результатом работы программы, по задаче
    private void showGraphHandling(List<String> lines) {
        ConnectedUndirectedWeightedGraph graph = ConnectedUndirectedWeightedGraph.fromStringLines(lines);
        showGraph(graph, "Source graph:");
        showGraph(graph.extractMinimumSpanningTree(), "Target tree:");
    }

    // Функция вывода графа в консоль
    private void showGraph(ConnectedUndirectedWeightedGraph graph, String head) {
        System.out.println(head);
        for (String line : graph.toStringLines()) {
           System.out.println(line);
        }
        System.out.println();
    }
}
