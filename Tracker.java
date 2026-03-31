package meditrack;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tracker {

    private List<Medicine>   medicines = new ArrayList<>();
    private List<DoseRecord> doseLogs  = new ArrayList<>();

    public Tracker() {
        medicines = FileManager.loadMedicines();
        doseLogs  = FileManager.loadDoseLogs();
    }

    // ---- Add / Remove ----

    public void addMedicine(Medicine m) {
        medicines.add(m);
        save();
        System.out.println("[OK] Added: " + m.getName());
    }

    public boolean removeMedicine(String name) {
        boolean removed = medicines.removeIf(m -> m.getName().equalsIgnoreCase(name));
        if (removed) { save(); System.out.println("[OK] Removed: " + name); }
        else           System.out.println("[!] Medicine not found: " + name);
        return removed;
    }

    // ---- Today's Schedule ----

    public void showTodaySchedule() {
        LocalDate today = LocalDate.now();
        System.out.println("\n===== Today's Schedule (" + today + ") =====");
        boolean any = false;
        for (Medicine m : medicines) {
            if (!m.isActive()) continue;
            any = true;
            int dosesTaken = countDosesToday(m.getName(), today);
            int dosesNeeded = m.getFrequencyPerDay();
            int remaining   = dosesNeeded - dosesTaken;
            System.out.printf("  %-20s  %dx/day | Taken: %d | Still needed: %d%n",
                    m.getName(), dosesNeeded, dosesTaken, Math.max(0, remaining));
            if (m.isLowStock())
                System.out.println("    ⚠  LOW STOCK — only " + m.getTabletsRemaining() + " tablet(s) left!");
        }
        if (!any) System.out.println("  No active medicines today.");
        System.out.println("==========================================\n");
    }

    // ---- Mark Dose Taken ----

    public void markDoseTaken(String name) {
        Medicine med = findByName(name);
        if (med == null) { System.out.println("[!] Medicine not found: " + name); return; }
        if (!med.isActive()) { System.out.println("[!] " + name + " is not active today."); return; }

        LocalDate today = LocalDate.now();
        int dosesTaken  = countDosesToday(name, today);
        if (dosesTaken >= med.getFrequencyPerDay()) {
            System.out.println("[!] All doses for " + name + " already taken today.");
            return;
        }
        med.takeDose();
        doseLogs.add(new DoseRecord(name, today, true));
        save();
        System.out.println("[OK] Dose marked as taken for: " + name
                + " (" + (dosesTaken + 1) + "/" + med.getFrequencyPerDay() + ")");
    }

    // ---- List All Medicines ----

    public void listAll() {
        System.out.println("\n===== All Medicines =====");
        if (medicines.isEmpty()) { System.out.println("  (none)"); return; }
        for (Medicine m : medicines) System.out.println("  " + m);
        System.out.println("=========================\n");
    }

    // ---- Stock Summary ----

    public void stockSummary() {
        System.out.println("\n===== Stock Summary =====");
        for (Medicine m : medicines) {
            if (m.isCompleted()) continue;
            String warn = m.isLowStock() ? "  *** LOW STOCK ***" : "";
            System.out.printf("  %-20s  %d tablet(s) left%s%n",
                    m.getName(), m.getTabletsRemaining(), warn);
        }
        System.out.println("=========================\n");
    }

    // ---- Dose History ----

    public void showHistory(String name) {
        System.out.println("\n===== Dose History: " + name + " =====");
        boolean found = false;
        for (DoseRecord dr : doseLogs) {
            if (dr.getMedicineName().equalsIgnoreCase(name)) {
                System.out.println("  " + dr);
                found = true;
            }
        }
        if (!found) System.out.println("  No records found.");
        System.out.println("=================================\n");
    }

    // ---- Helpers ----

    private int countDosesToday(String name, LocalDate date) {
        int count = 0;
        for (DoseRecord dr : doseLogs)
            if (dr.getMedicineName().equalsIgnoreCase(name)
                    && dr.getDate().equals(date) && dr.isTaken()) count++;
        return count;
    }

    private Medicine findByName(String name) {
        for (Medicine m : medicines)
            if (m.getName().equalsIgnoreCase(name)) return m;
        return null;
    }

    private void save() {
        FileManager.saveMedicines(medicines);
        FileManager.saveDoseLogs(doseLogs);
    }
}
