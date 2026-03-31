package meditrack;

import java.time.LocalDate;

public class Medicine {
    private String name;
    private String dosage;
    private int frequencyPerDay;   // 1, 2, or 3
    private LocalDate startDate;
    private int durationDays;
    private int totalTablets;
    private int tabletsRemaining;

    public Medicine(String name, String dosage, int frequencyPerDay,
                    LocalDate startDate, int durationDays) {
        this.name = name;
        this.dosage = dosage;
        this.frequencyPerDay = frequencyPerDay;
        this.startDate = startDate;
        this.durationDays = durationDays;
        this.totalTablets = frequencyPerDay * durationDays;
        this.tabletsRemaining = totalTablets;
    }

    // ---- Getters ----
    public String getName()            { return name; }
    public String getDosage()          { return dosage; }
    public int getFrequencyPerDay()    { return frequencyPerDay; }
    public LocalDate getStartDate()    { return startDate; }
    public int getDurationDays()       { return durationDays; }
    public int getTotalTablets()       { return totalTablets; }
    public int getTabletsRemaining()   { return tabletsRemaining; }

    public LocalDate getEndDate() {
        return startDate.plusDays(durationDays - 1);
    }

    public boolean isActive() {
        LocalDate today = LocalDate.now();
        return !today.isBefore(startDate) && !today.isAfter(getEndDate());
    }

    public boolean isCompleted() {
        return LocalDate.now().isAfter(getEndDate());
    }

    public void takeDose() {
        if (tabletsRemaining > 0) tabletsRemaining--;
    }

    public boolean isLowStock() {
        int daysLeft = frequencyPerDay > 0 ? tabletsRemaining / frequencyPerDay : 0;
        return daysLeft <= 3 && tabletsRemaining > 0;
    }

    /** CSV line format: name,dosage,freq,startDate,duration,remaining */
    public String toCsv() {
        return String.join(",", name, dosage,
                String.valueOf(frequencyPerDay),
                startDate.toString(),
                String.valueOf(durationDays),
                String.valueOf(tabletsRemaining));
    }

    public static Medicine fromCsv(String line) {
        String[] p = line.split(",", -1);
        Medicine m = new Medicine(p[0], p[1],
                Integer.parseInt(p[2]),
                LocalDate.parse(p[3]),
                Integer.parseInt(p[4]));
        m.tabletsRemaining = Integer.parseInt(p[5]);
        return m;
    }

    @Override
    public String toString() {
        String status = isCompleted() ? "Completed" : isActive() ? "Active" : "Upcoming";
        return String.format("%-20s %-8s %dx/day  Started: %s  Ends: %s  Tablets left: %d  [%s]",
                name, dosage, frequencyPerDay, startDate, getEndDate(),
                tabletsRemaining, status);
    }
}
