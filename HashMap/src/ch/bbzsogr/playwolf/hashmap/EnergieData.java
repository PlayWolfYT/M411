package ch.bbzsogr.playwolf.hashmap;

public class EnergieData {
    
    private final int year;
    private final int energy_type;
    private final double amount;
    private final int pop_ch;
    private final int pop_foreign;


    /**
     * A object for referencing the data better.
     * @param year Year of the entry
     * @param energy_type Energy-Type of the entry
     * @param amount Amount of the entry
     * @param pop_ch Population of Switzerland of the entry
     * @param pop_foreign Foreign population of the entry
     */
    public EnergieData(int year, int energy_type, double amount, int pop_ch, int pop_foreign) {
        this.year = year;
        this.energy_type = energy_type;
        this.amount = amount;
        this.pop_ch = pop_ch;
        this.pop_foreign = pop_foreign;
    }

    /**
     * 
     * @return Year of the entry
     */
    public int getYear() {
        return year;
    }

    /**
     * 
     * @return Energy-Type of the entry
     */
    public int getEnergyType() {
        return energy_type;
    }

    /**
     * 
     * @return Amount of the entry
     */
    public double getAmount() {
        return amount;
    }

    /**
     * 
     * @return Swiss population of the entry
     */
    public int getPopulationCH() {
        return pop_ch;
    }
    
    /**
     * 
     * @return Foreign population of the entry
     */
    public int getPopulationForeign() {
        return pop_foreign;
    }

}
