package se.lexicon;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        InnerMenu converters = new InnerMenu(scanner); // create instance of InnerMenu class

        List<MenuOption> mainMenuOptions = Arrays.asList(
                new MenuOption("1", "Length Converter", converters::lengthConverter),
                new MenuOption("2", "Speed Converter", converters::speedConverter),
                new MenuOption("3", "Fuel Consumption Converter", converters::fuelConsConverter),
                new MenuOption("X", "Exit Application", () -> System.out.println("Exiting application.")));

        MenuManager.menuLoop(scanner, "Main Menu", mainMenuOptions);
        scanner.close();
    }

    static class InnerMenu {
        private final Scanner scanner;
        private FuelData currentFuelData;

        public InnerMenu(Scanner scanner) {
            this.scanner = scanner; // "this" refers to the current object instance
        }

        public void lengthConverter() {

            List<MenuOption> lengthMenuOptions = Arrays.asList(
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

        public void speedConverter() {
            List<MenuOption> speedOptions = Arrays.asList(
                    new MenuOption("1", "km/h to m/s", () -> {
                        double kilometersPerHour = InputValidator.getValidDouble(scanner, "Enter speed in km/h: ");
                        double metersPerSecond = LogicOperations.speedConvert("1", kilometersPerHour);
                        System.out.println(kilometersPerHour + " km/h is " + metersPerSecond + " m/s.");
                    }),
                    new MenuOption("2", "m/s to km/h", () -> {
                        double metersPerSecond = InputValidator.getValidDouble(scanner, "Enter speed in m/s: ");
                        double kilometersPerHour = LogicOperations.speedConvert("2", metersPerSecond);
                        System.out.println(metersPerSecond + " m/s is " + kilometersPerHour + " km/h.");
                    }),
                    new MenuOption("B", "Back", () -> {
                    }));
            MenuManager.menuLoop(scanner, "Speed Converter Menu", speedOptions);
        }

        public void fuelConsConverter() {
            this.currentFuelData = new FuelData(); // resets fuel data for each menu entry

            // This option uses the external calculation method
            List<MenuOption> fuelConsOptions = Arrays.asList(
                    new MenuOption("1", "Distance", () ->
                            this.currentFuelData.distance = InputValidator.getValidDouble(scanner,
                            "Enter distance in kilometers: ")),
                    new MenuOption("2", "Fuel Amount", () ->
                        this.currentFuelData.fuelAmount = InputValidator.getValidDouble(scanner,
                                "Enter fuel amount in liters: ")),
                    new MenuOption("3", "Fuel Consumption", () ->
                            this.currentFuelData.fuelConsumption = InputValidator.getValidDouble(scanner,
                            "Enter fuel consumption in L/100km: ")),
                    new MenuOption("4", "Fuel Economy", () ->
                        this.currentFuelData.fuelEconomy = InputValidator.getValidDouble(scanner,
                                "Enter fuel economy in km/L: ")),
                    new MenuOption("C", "Calculate Results", this::handleCalculationRequest),
                    new MenuOption("B", "Back", () -> {}));

            MenuManager.menuLoop(scanner, "Fuel Consumption Converter Menu\n Input at least two values [1,2,3,4] and then select [C] to calculate the missing values.",
                    fuelConsOptions);
        }

        public static class FuelData { // stores input values for fuel consumption calculations
            public Double distance;
            public Double fuelAmount;
            public Double fuelConsumption;
            public Double fuelEconomy;

            public boolean FuelDataCheck() {
                int count = 0;
                if (distance != null)
                    count++;
                if (fuelAmount != null)
                    count++;
                if (fuelConsumption != null)
                    count++;
                if (fuelEconomy != null)
                    count++;
                return count >= 2;
            }
        }

        private void handleCalculationRequest() {
            if (currentFuelData.FuelDataCheck()) {
                System.out.println("\nSufficient data provided, Calculating");

                FuelData results = LogicOperations.fuelConsConvert(currentFuelData);

                System.out.println("\nCalculated Results:");
                System.out.println("Distance: " + results.distance + " km");
                System.out.println("Fuel Amount: " + results.fuelAmount + " liters");
                System.out.println("Fuel Consumption: " + results.fuelConsumption + " L/100km");
                System.out.println("Fuel Economy: " + results.fuelEconomy + " km/L");
            } else {
                System.out.println("\nInsufficient data provided. Please input at least two values.");
            }
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

            public static double speedConvert(String subChoice, double convertValue) {

                if (subChoice.equals("1")) {
                    System.out.println();
                    return convertValue / 3.6;
                } else {
                    System.out.println();
                    return convertValue * 3.6;
                }
            }
            /*
                   use of "?" same as? =     double distance;
                if (input.distance != null) distance = input.distance;
                else distance = 0;

                String label = (name != null) ? name : "(unknown)";

                Shorten if-else
                int sign = (n >= 0) ? 1 : -1;

                Nested choice
                int tier = (score >= 90) ? 1 : (score >= 75) ? 2 : 3;  // 1, 2, or 3

             */
            public static FuelData fuelConsConvert(FuelData input) {
                double distance = (input.distance != null) ? input.distance : 0;
                double fuelLiters = (input.fuelAmount != null) ? input.fuelAmount : 0;

                double kmPerL = 0;
                if (input.fuelConsumption != null && input.fuelConsumption != 0) {
                    kmPerL = 100.0 / input.fuelConsumption;
                } else if (input.fuelEconomy != null) {
                    kmPerL = input.fuelEconomy;
                }

                // Compute missing values using relationships
                if (distance == 0 && fuelLiters > 0 && kmPerL > 0) distance = fuelLiters * kmPerL;
                if (fuelLiters == 0 && distance > 0 && kmPerL > 0) fuelLiters = distance / kmPerL;
                if (kmPerL == 0 && distance > 0 && fuelLiters > 0) kmPerL = distance / fuelLiters;

                // Validate final state
                if (kmPerL == 0) {
                    throw new IllegalArgumentException("Insufficient or invalid data (km/L is zero)");
                }

                FuelData out = new FuelData();
                out.distance = distance;
                out.fuelAmount = fuelLiters;
                out.fuelEconomy = kmPerL;
                out.fuelConsumption = 100.0 / kmPerL;
                return out;
            }
        }
    }

    public record MenuOption(String key, String description, Runnable action) {

        public void execute() {
                action.run();
            }
        }

    public static class MenuManager {

        public static void menuLoop(Scanner scanner, String title, List<MenuOption> options) {
            while (true) {
                System.out.println("\n " + title);
                for (MenuOption option : options) {
                    System.out.println("[" + option.key() + "] " + option.description());
                }

                String choice = InputValidator.getValidString(scanner, "\n> ");
                boolean found = false; // to track if a valid option was found

                for (MenuOption option : options) {
                    if (option.key().equalsIgnoreCase(choice) || option.description().equalsIgnoreCase(choice)) {
                        option.execute();
                        found = true;

                        if (option.key().equalsIgnoreCase("X") || option.description().equalsIgnoreCase("Back")) {
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