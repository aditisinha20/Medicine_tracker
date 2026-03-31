package meditrack;

import java.time.LocalDate;
import java.time.LocalTime;

public class DoseRecord {
    private String medicineName;
    private LocalDate date;
    private LocalTime time;
    private boolean taken;

    public DoseRecord(String medicineName, LocalDate date, boolean taken) {
        this.medicineName = medicineName;
        this.date          = date;
        this.time          = LocalTime.now();
        this.taken         = taken;
    }

    public String getMedicineName() { return medicineName; }
    public LocalDate getDate()      { return date; }
    public boolean isTaken()        { return taken; }
    public void markTaken()         { this.taken = true; this.time = LocalTime.now(); }

    /** CSV: medicineName,date,HH:mm,taken */
    public String toCsv() {
        return String.join(",", medicineName, date.toString(),
                time.getHour() + ":" + time.getMinute(),
                String.valueOf(taken));
    }

    public static DoseRecord fromCsv(String line) {
        String[] p = line.split(",", -1);
        DoseRecord dr = new DoseRecord(p[0], LocalDate.parse(p[1]), Boolean.parseBoolean(p[3]));
        return dr;
    }

    @Override
    public String toString() {
        return String.format("%-20s  Date: %s  Time: %02d:%02d  Status: %s",
                medicineName, date,
                time.getHour(), time.getMinute(),
                taken ? "TAKEN" : "MISSED");
    }
}
