package se.lexicon;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        boolean run = true;
        Scanner scanner = new Scanner(System.in);

        while (run) {
            System.out.println();
            System.out.println("Main Menu");
            System.out.println("[1] Length Converter");
            System.out.println("[2] Speed Converter");
            System.out.println("[3] Fuel Consumption Converter");
            System.out.println("[X] Exit");
            System.out.println();

            String choice = InputValidator.getValidString(scanner, "> ");

            switch (choice) {
                case "1":
                    Inner lengthConverter = new Inner(scanner);
                    lengthConverter.LengthConverter();
                    System.out.println();
                    break;
                case "2":
                    Inner speedConverter = new Inner(scanner);
                    speedConverter.SpeedConverter();
                    System.out.println();
                    break;
                case "3":
                    Inner fuelConverter = new Inner(scanner);
                    fuelConverter.FuelConsConverter();
                    System.out.println();
                    break;
                case "X":
                    run = false;
                    break;
            }
        }
        scanner.close();
    }

    // runs when called from main
    static class Inner {
        private Scanner scanner;

        public Inner(Scanner scanner) {
            this.scanner = scanner; // "this" refers to the current object instance
        }

        public void LengthConverter() {

            System.out.println();
            System.out.println("Length Converter");
            System.out.println("[1] Meters to Kilometers:");
            System.out.println("[2] Kilometers to Meters:");

            System.out.println();
            System.out.print("> ");
            String subChoice = scanner.next();

            if (subChoice.equals("1")) {
                System.out.println();
                double meters = InputValidator.getValidDouble(scanner, "Enter meters: ");
                System.out.println();
                double kilometers = Inner.LogicOperations.metricConvert(subChoice, meters);
                System.out.println(meters + " meters is " + kilometers + " kilometers.");
            } else {
                System.out.println();
                double kilometers = InputValidator.getValidDouble(scanner, "Enter kilometers: ");
                System.out.println();
                double meters = Inner.LogicOperations.metricConvert(subChoice, kilometers);
                System.out.println(kilometers + " kilometers is " + meters + " meters.");
            }
        }

        public void SpeedConverter() {
            System.out.println();
            System.out.println("Speed Converter");
        }

        public void FuelConsConverter() {
            System.out.println("Fuel Consumption Converter");
        }

        public void MainMenu() {
            System.out.println("Returning to Main Menu");
        }

        public void Exit() {
            System.out.println("Exiting application");
        }

        // for shared operations inside inner class
        static class LogicOperations {
            public static double metricConvert(String subChoice, double convertValue) {

                if (subChoice.equals("1")) {
                    System.out.println();
                    return convertValue / 1000;
                } else {
                    System.out.println();
                    return convertValue * 1000;
                }
            }
        }
    }

    public static class InputValidator {

        public static double getValidDouble(Scanner scanner, String prompt) {
            while (true) {
                System.out.print(prompt);
                if (scanner.hasNextDouble()) {
                    double value = scanner.nextDouble();
                    scanner.nextLine(); // clear buffer
                    return value;
                } else {
                    System.out.println("Invalid number.");
                    System.out.println();
                    scanner.next(); // clear invalid input
                }
            }
        }

        public static String getValidString(Scanner scanner, String prompt) {
            String input;
            do {
                System.out.print(prompt);
                input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    System.out.println("Input invalid.");
                    System.out.println();
                }
            } while (input.isEmpty());
            return input;
        }
    }
}