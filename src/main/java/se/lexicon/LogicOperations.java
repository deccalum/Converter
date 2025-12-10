package se.lexicon;

public class LogicOperations {

    public static class FuelData {
        public Double distance;
        public Double fuelAmount;
        public Double fuelConsumption;
        public Double fuelEconomy;

        public boolean FuelDataCheck() {
            int count = 0;
            if (distance != null) count++;
            if (fuelAmount != null) count++;
            if (fuelConsumption != null) count++;
            if (fuelEconomy != null) count++;
            return count >= 2;
        }
    }

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

    public static FuelData fuelConsConvert(FuelData input) {
        double distance = (input.distance != null) ? input.distance : 0;
        double fuelLiters = (input.fuelAmount != null) ? input.fuelAmount : 0;
        double kmPerL = 0;
        if (input.fuelConsumption != null && input.fuelConsumption != 0) {
            kmPerL = 100.0 / input.fuelConsumption;
        } else if (input.fuelEconomy != null) {
            kmPerL = input.fuelEconomy;
        }

        if (distance == 0 && fuelLiters > 0 && kmPerL > 0)
            distance = fuelLiters * kmPerL;
        if (fuelLiters == 0 && distance > 0 && kmPerL > 0)
            fuelLiters = distance / kmPerL;
        if (kmPerL == 0 && distance > 0 && fuelLiters > 0)
            kmPerL = distance / fuelLiters;

        FuelData out = new FuelData();
        out.distance = distance;
        out.fuelAmount = fuelLiters;
        out.fuelEconomy = kmPerL;
        out.fuelConsumption = (kmPerL > 0) ? 100.0 / kmPerL : 0;
        return out;
    }

    public static boolean processFuelCalculation(FuelData data) {
        if (data.FuelDataCheck()) {
            System.out.println("\nSufficient data provided, Calculating...");
            FuelData res = fuelConsConvert(data);
            System.out.println("Distance: " + res.distance + " km");
            System.out.println("Fuel Amount: " + res.fuelAmount + " L");
            System.out.println("Consumption: " + res.fuelConsumption + " L/100km");
            System.out.println("Economy: " + res.fuelEconomy + " km/L");

            data.distance = res.distance;
            data.fuelAmount = res.fuelAmount;
            data.fuelConsumption = res.fuelConsumption;
            data.fuelEconomy = res.fuelEconomy;
            return true;
        }
        return false;
    }
}
