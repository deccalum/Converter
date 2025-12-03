package se.lexicon;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        boolean run = true;
        Scanner scanner = new Scanner(System.in);

        while (run) {
            System.out.println("Main Menu");
            System.out.println("[1] Option 1");
            System.out.println("[2] Option 2");
            System.out.println("[3] Option 3");
            System.out.println("[X] Exit");
            String choice = scanner.next();

            if (choice.equalsIgnoreCase("X")) {
                run = false;
            }
        }
        scanner.close();
    }

    // runs when called from main
    static class Inner {
        private Scanner scanner;

        public Inner(Scanner scanner) {
            this.scanner = scanner;
        }

        public void Option1() {
            System.out.println("Option 1 selected");
        }

        public void Option2() {
            System.out.println("Option 2 selected");
        }

        public void Option3() {
            System.out.println("Option 3 selected");
        }

        public void MainMenu() {
            System.out.println("Returning to Main Menu");
        }

        public void Exit() {
            System.out.println("Exiting application");
        }

        static class LogicOperations {
            public static void performAction(String action) {
                System.out.println("Performing action: " + action);
            }
        }
    }
}
