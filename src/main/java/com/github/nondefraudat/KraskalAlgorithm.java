package com.github.nondefraudat;

import java.util.Scanner;

// Главный класс программы и точка входа
public class KraskalAlgorithm {
    public static void main(String[] args) {
        ConsoleUI userInterface = new ConsoleUI(new Scanner(System.in));
        userInterface.startHandlingLoop();
    }
}
