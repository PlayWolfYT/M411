package ch.bbzsogr.playwolf.hashmap;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class HashMain {
    
    /**
     * 
     * Main method to start the program.
     * Initializes a BDtoList instance, loads the data from it 
     * and then passes it to the calculation method.
     * 
     * @since 12.10.2020
     * @param args Program arguments
     */
    public static void main(String[] args) {
        DBtoList dbToList = new DBtoList();
        ArrayList<EnergieData> data = dbToList.loadData();
        energieKosten(data);
    }
    
    /**
     * 
     * Calculates the wanted data and outputs it to System.Out
     * 
     * @param data An ArrayList with the data to be calculated and outputted.
     */
    public static void energieKosten(ArrayList<EnergieData> data) {

        if (data == null) {
            // Error happened when reading the data.
            System.out.println("The provided data is null. Please verify the SQL-Statement!");
            return;
        }

        // For calculating the average population
        HashMap<Integer, ArrayList<Integer>> prePopulationMap = new HashMap<>();

        // For calculating the amount per person
        HashMap<Integer, Integer> populationMap = new HashMap<>();
        HashMap<Integer, Double> amountMap = new HashMap<>();

        // The actual data
        HashMap<Integer, Double> calculatedData = new HashMap<>();

        // Populate the amountMap and the prePopulationMap
        data.forEach(row -> {
            // If we already have data for that year
            if (amountMap.containsKey(row.getYear()) && prePopulationMap.containsKey(row.getYear())) {
                // Add to the total amount
                amountMap.put(row.getYear(), amountMap.get(row.getYear()) + row.getAmount());

                // Add an entry to the prePopulationMap entry (Used later for calculating the average)
                ArrayList<Integer> tmpPopulation = prePopulationMap.get(row.getYear());
                tmpPopulation.add(row.getPopulationCH() + row.getPopulationForeign());

                prePopulationMap.put(row.getYear(), tmpPopulation);
            } else {
                // Create new entry
                amountMap.put(row.getYear(), row.getAmount());
                prePopulationMap.put(row.getYear(), new ArrayList<>(Arrays.asList(row.getPopulationCH() + row.getPopulationForeign())));
            }
        });

        prePopulationMap.forEach((year, populationArray) -> {
            // If we only have one population amount.
            if (populationArray.size() == 1) {
                populationMap.put(year, populationArray.get(0));
                return;
            }

            AtomicInteger totalPopulation = new AtomicInteger();
            populationArray.forEach(p -> totalPopulation.addAndGet(p));

            populationMap.put(year, Math.round(totalPopulation.get() / populationArray.size()));
        });

        amountMap.forEach((year, amount) -> {
            if (calculatedData.containsKey(year)) {
                // Duplicate data. This should never happen!
                System.out.println("Something went horribly wrong. We have duplicate data.");
                System.exit(-1);
                return;
            }
            if (!populationMap.containsKey(year)) {
                // We dont have a population. This should also never happen.
                System.out.println("Something went horribly wrong. We dont have a population for the year " + year);
                System.exit(-1);
                return;
            }

            int population = populationMap.get(year);

            BigDecimal actualAmount = new BigDecimal("" + amount);
            actualAmount = actualAmount.multiply(new BigDecimal("1000000"));


            double amountPerPerson = actualAmount.divide(new BigDecimal("" + population), RoundingMode.HALF_EVEN).doubleValue();
            // Debug: System.out.format("Year: %d, Amount: %.2f, Population: %d, Per Person: %.2f%n", year, amount, population, amountPerPerson);

            calculatedData.put(year, amountPerPerson);
        });

        System.out.format("| %-5s | %-15s |%n", "Jahr", "Kosten pro Kopf");
        calculatedData.forEach((k, v) -> System.out.format("| %-5d | %-15.0f |%n", k, v));

        AtomicInteger totalAverage = new AtomicInteger();

        calculatedData.forEach((k, v) -> totalAverage.addAndGet(v.intValue()));

        System.out.format("%nDurchschnittliche Kosten pro Kopf: %.0f%n", (float) totalAverage.get() / calculatedData.size());
    }

}
