package se.lexicon;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        InnerMenu converters = new InnerMenu(scanner); // create instance of InnerMenu class

        List<MenuOption> mainMenuOptions = Arrays.<MenuOption>asList(
                new MenuOption("1", "Length Converter", converters::LengthConverter),
                new MenuOption("2", "Speed Converter", converters::SpeedConverter),
                new MenuOption("3", "Fuel Consumption Converter", converters::FuelConsConverter),
                new MenuOption("X", "Exit Application", () -> System.out.println("Exiting application.")));

        MenuManager.menuLoop(scanner, "Main Menu", mainMenuOptions);
        scanner.close();
    }

    // runs when called from main
    static class InnerMenu {
        private Scanner scanner;

        public InnerMenu(Scanner scanner) {
            this.scanner = scanner; // "this" refers to the current object instance
        }

        public void LengthConverter() {

            List<MenuOption> lengthMenuOptions = Arrays.<MenuOption>asList(
                    new MenuOption("1", "Meters to Kilometers", () -> {
                        double meters = InputValidator.getValidDouble(scanner, "Enter meters: ");
                        double kilometers = LogicOperations.metricConvert("1", meters);
                        System.out.println(meters + " meters is " + kilometers + " kilometers.");
                    }),
                    new MenuOption("2", "Kilometers to Meters", () -> {
                        double kilometers = InputValidator.getValidDouble(scanner, "Enter kilometers: ");
                        double meters = LogicOperations.metricConvert("2", kilometers);
                        System.out.println(kilometers + " kilometers is " + meters + " meters.");
                    }),
                    new MenuOption("B", "Back", () -> {
                    }));

            MenuManager.menuLoop(scanner, "Length Converter Menu", lengthMenuOptions);
        }

        public void SpeedConverter() {
            List<MenuOption> speedOptions = Arrays.asList(
                    new MenuOption("B", "Back", () -> {
                    }));
            MenuManager.menuLoop(scanner, "Speed Converter Menu", speedOptions);
        }

        public void FuelConsConverter() {
            List<MenuOption> fuelConsOptions = Arrays.asList(
                    new MenuOption("B", "Back", () -> {
                    }));
            MenuManager.menuLoop(scanner, "Fuel Consumption Converter Menu", fuelConsOptions);
        }

        // for shared operations inside InnerMenu class
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

    public static class MenuOption {
        private final String key;
        private final String description;
        private final Runnable action; // The code to run when selected

        public MenuOption(String key, String description, Runnable action) {
            this.key = key;
            this.description = description;
            this.action = action;
        }

        public String getKey() {
            return key;
        }

        public String getDescription() {
            return description;
        }

        public void execute() {
            action.run();
        }
    }

    public class MenuManager {

        public static void menuLoop(Scanner scanner, String title, List<MenuOption> options) {
            while (true) {
                System.out.println("\n " + title);
                for (MenuOption option : options) {
                    System.out.println("[" + option.getKey() + "] " + option.getDescription());
                }

                String choice = InputValidator.getValidString(scanner, "\n> ");
                boolean found = false; // to track if a valid option was found

                for (MenuOption option : options) {
                    if (option.getKey().equalsIgnoreCase(choice) || option.getDescription().equalsIgnoreCase(choice)) {
                        option.execute();
                        found = true;

                        if (option.getKey().equalsIgnoreCase("X") || option.getDescription().equalsIgnoreCase("Back")) {
                            return; // Exit the menu loop
                        }
                        break;
                    }
                }
                if (!found) {
                    InputValidator.displayError("\nInvalid option: " + choice);
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
                    System.out.println("\nInvalid number.");
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
                    System.out.println("\nInput invalid.");
                }
            } while (input.isEmpty());
            return input;
        }

        public static void displayError(String message) {
            System.out.println("Error: " + message);
        }
    }

}