package se.lexicon;

import java.util.Scanner;

public class Main {
    static void main() {
        boolean run = true;
        Scanner scanner = new Scanner(System.in);

        while (run) {
            System.out.println("Main Menu");
            System.out.println("1. Option 1");
            System.out.println("2. Option 2");
            System.out.println("[X]. Exit");
            String choice = scanner.next();

            if (choice.equalsIgnoreCase("X")) {
                run = false;
            }
        }
        scanner.close();
    }
} 
