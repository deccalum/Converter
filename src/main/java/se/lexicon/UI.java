package se.lexicon;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.function.Supplier;
import se.lexicon.LogicOperations.FuelData;

public class UI {

    public static void startApp() {
        Scanner scanner = new Scanner(System.in);
        Main.InnerMenu converters = new Main.InnerMenu();
        List<MenuOption> mainMenuOptions = Arrays.asList(
                new MenuOption("1", () -> "Length Converter", () -> converters.lengthConverter(scanner)),
                new MenuOption("2", () -> "Speed Converter", () -> converters.speedConverter(scanner)),
                new MenuOption("3", () -> "Fuel Consumption Converter", () -> converters.fuelConsConverter(scanner)),
                new MenuOption("X", () -> "Exit Application", () -> System.out.println("Exiting application.")));
        MenuManager.menuLoop(scanner, "Main Menu", mainMenuOptions);
        scanner.close();
    }

    public static void lengthMenu(Scanner scanner) {
        List<MenuOption> options = Arrays.asList(
                new MenuOption("1", () -> "Meters to Kilometers", () -> {
                    double val = InputValidator.getValidDouble(scanner, "Enter meters: ");
                    double res = LogicOperations.metricConvert("1", val);
                    System.out.println(val + " meters is " + res + " kilometers.");
                }),
                new MenuOption("2", () -> "Kilometers to Meters", () -> {
                    double val = InputValidator.getValidDouble(scanner, "Enter kilometers: ");
                    double res = LogicOperations.metricConvert("2", val);
                    System.out.println(val + " kilometers is " + res + " meters.");
                }),
                new MenuOption("B", () -> "Back", () -> {
                }));
        MenuManager.menuLoop(scanner, "Length Converter Menu", options);
    }

    public static void speedMenu(Scanner scanner) {
        List<MenuOption> options = Arrays.asList(
                new MenuOption("1", () -> "km/h to m/s", () -> {
                    double val = InputValidator.getValidDouble(scanner, "Enter speed in km/h: ");
                    double res = LogicOperations.speedConvert("1", val);
                    System.out.println(val + " km/h is " + res + " m/s.");
                }),
                new MenuOption("2", () -> "m/s to km/h", () -> {
                    double val = InputValidator.getValidDouble(scanner, "Enter speed in m/s: ");
                    double res = LogicOperations.speedConvert("2", val);
                    System.out.println(val + " m/s is " + res + " km/h.");
                }),
                new MenuOption("B", () -> "Back", () -> {
                }));
        MenuManager.menuLoop(scanner, "Speed Converter Menu", options);
    }

    public static void fuelMenu(Scanner scanner) {
        FuelData data = new FuelData();

        List<MenuOption> options = Arrays.asList(
                new MenuOption("1", () -> "Distance " + fmt(data.distance, "km"), () -> {
                    data.distance = InputValidator.getValidDouble(scanner, "Enter distance (km): ");
                    autoCalc(scanner, data);
                }),
                new MenuOption("2", () -> "Fuel Amount " + fmt(data.fuelAmount, "L"), () -> {
                    data.fuelAmount = InputValidator.getValidDouble(scanner, "Enter fuel (L): ");
                    autoCalc(scanner, data);
                }),
                new MenuOption("3", () -> "Fuel Consumption " + fmt(data.fuelConsumption, "L/100km"), () -> {
                    data.fuelConsumption = InputValidator.getValidDouble(scanner, "Enter consumption (L/100km): ");
                    autoCalc(scanner, data);
                }),
                new MenuOption("4", () -> "Fuel Economy " + fmt(data.fuelEconomy, "km/L"), () -> {
                    data.fuelEconomy = InputValidator.getValidDouble(scanner, "Enter economy (km/L): ");
                    autoCalc(scanner, data);
                }),
                new MenuOption("B", () -> "Back", () -> {
                }));
        MenuManager.menuLoop(scanner, "Fuel Consumption Menu", options);
    }

    private static void autoCalc(Scanner scanner, FuelData data) {
        if (LogicOperations.processFuelCalculation(data)) {
            System.out.println("\nPress Enter to return...");
            scanner.nextLine();
        }
    }

    private static String fmt(Double value, String unit) {
        return (value == null) ? "" : value + " " + unit;
    }

    public record MenuOption(String key, Supplier<String> label, Runnable action) {
        public void execute() {
            action.run();
        }
    }

    public static class MenuManager {
        public static void menuLoop(Scanner scanner, String title, List<MenuOption> options) {
            while (true) {
                System.out.println("\n " + title);
                for (MenuOption opt : options)
                    System.out.println("[" + opt.key() + "] " + opt.label().get());

                String choice = InputValidator.getValidString(scanner, "\n> ");
                boolean found = false;
                for (MenuOption opt : options) {
                    if (opt.key().equalsIgnoreCase(choice) || opt.label().get().equalsIgnoreCase(choice)) {
                        opt.execute();
                        found = true;
                        if (choice.equalsIgnoreCase("X") || choice.equalsIgnoreCase("B")
                                || opt.label().get().equalsIgnoreCase("Back"))
                            return;
                        break;
                    }
                }
                if (!found)
                    System.out.println("Invalid option.");
            }
        }
    }

    public static class InputValidator {
        public static double getValidDouble(Scanner scanner, String prompt) {
            while (true) {
                System.out.print(prompt);
                if (scanner.hasNextDouble()) {
                    double v = scanner.nextDouble();
                    scanner.nextLine();
                    return v;
                }
                System.out.println("Invalid number.");
                scanner.next();
            }
        }

        public static String getValidString(Scanner scanner, String prompt) {
            String s;
            do {
                System.out.print(prompt);
                s = scanner.nextLine().trim();
            } while (s.isEmpty());
            return s;
        }

        public static void displayError(String msg) {
            System.out.println("Error: " + msg);
        }
    }

}