package se.lexicon;

import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        UI.startApp();
    }
    static class InnerMenu {
        public void lengthConverter(Scanner scanner) {
            UI.lengthMenu(scanner);
        }
        public void speedConverter(Scanner scanner) {
            UI.speedMenu(scanner);
        }
        public void fuelConsConverter(Scanner scanner) {
            UI.fuelMenu(scanner);
        }
    }
}
