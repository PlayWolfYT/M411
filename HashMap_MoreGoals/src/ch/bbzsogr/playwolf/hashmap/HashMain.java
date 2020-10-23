package ch.bbzsogr.playwolf.hashmap;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import ch.bbzsogr.playwolf.hashmap.exceptions.MathException;
import ch.bbzsogr.playwolf.hashmap.utils.MathHelper;
import ch.bbzsogr.playwolf.hashmap.utils.MathHelper.BigDec;

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

            try {
				populationMap.put(year, MathHelper.averageOfArray(populationArray.toArray(), Integer.class));
			} catch (MathException e) {
				e.printStackTrace();
			}
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

            BigDec actualAmount = new BigDec(amount);
            actualAmount.mult(1000000);

            actualAmount.div(population, RoundingMode.HALF_EVEN);

            double amountPerPerson;
			try {
				amountPerPerson = actualAmount.getAs(Double.class);
			} catch (MathException e) {
                e.printStackTrace();
                System.exit(-1);
                return;
			}

            calculatedData.put(year, amountPerPerson);
        });

        System.out.format("| %-5s | %-15s |%n", "Jahr", "Kosten pro Kopf");
        calculatedData.forEach((k, v) -> System.out.format("| %-5d | %-15.0f |%n", k, v));

        try {
			System.out.format("%nDurchschnittliche Kosten pro Kopf: %.0f%n", MathHelper.averageOfArray(calculatedData.values().toArray(), Float.class));
		} catch (MathException e) {
			e.printStackTrace();
		}
    }
}
