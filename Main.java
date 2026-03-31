package meditrack;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 * MediTrack — Personal Medicine Tracker
 * CLI entry point.
 *
 * Author : Aditi Sinha
 * Reg No : 24BHI10024
 * Course : Programming in Java
 */
public class Main {

    private static final Scanner sc = new Scanner(System.in);
    private static final Tracker tracker = new Tracker();

    public static void main(String[] args) {
        banner();
        boolean running = true;
        while (running) {
            printMenu();
            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1" -> addMedicine();
                case "2" -> tracker.showTodaySchedule();
                case "3" -> markDose();
                case "4" -> tracker.listAll();
                case "5" -> tracker.stockSummary();
                case "6" -> showHistory();
                case "7" -> removeMedicine();
                case "0" -> { System.out.println("\nGoodbye! Stay healthy.\n"); running = false; }
                default  ->  System.out.println("[!] Invalid option. Try again.\n");
            }
        }
        sc.close();
    }

    // ---- Menu ----

    private static void banner() {
        System.out.println("===========================================");
        System.out.println("   MediTrack — Personal Medicine Tracker  ");
        System.out.println("   Aditi Sinha · 24BHI10024               ");
        System.out.println("===========================================\n");
    }

    private static void printMenu() {
        System.out.println("--- MENU ---");
        System.out.println(" 1. Add a medicine");
        System.out.println(" 2. View today's schedule");
        System.out.println(" 3. Mark a dose as taken");
        System.out.println(" 4. List all medicines");
        System.out.println(" 5. Stock summary");
        System.out.println(" 6. Dose history for a medicine");
        System.out.println(" 7. Remove a medicine");
        System.out.println(" 0. Exit");
        System.out.print("Enter choice: ");
    }

    // ---- Add Medicine ----

    private static void addMedicine() {
        System.out.println("\n--- Add Medicine ---");
        System.out.print("Name           : "); String name = sc.nextLine().trim();
        System.out.print("Dosage (e.g. 500mg): "); String dosage = sc.nextLine().trim();

        int freq = readInt("Frequency per day (1/2/3): ", 1, 3);
        LocalDate start = readDate("Start date (YYYY-MM-DD, Enter = today): ");
        int duration = readInt("Duration in days: ", 1, 365);

        tracker.addMedicine(new Medicine(name, dosage, freq, start, duration));
        System.out.println();
    }

    // ---- Mark Dose ----

    private static void markDose() {
        System.out.print("Medicine name: ");
        String name = sc.nextLine().trim();
        tracker.markDoseTaken(name);
        System.out.println();
    }

    // ---- Remove Medicine ----

    private static void removeMedicine() {
        System.out.print("Medicine name to remove: ");
        String name = sc.nextLine().trim();
        tracker.removeMedicine(name);
        System.out.println();
    }

    // ---- Dose History ----

    private static void showHistory() {
        System.out.print("Medicine name: ");
        String name = sc.nextLine().trim();
        tracker.showHistory(name);
    }

    // ---- Input Helpers ----

    private static int readInt(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            try {
                int v = Integer.parseInt(sc.nextLine().trim());
                if (v >= min && v <= max) return v;
                System.out.println("[!] Please enter a value between " + min + " and " + max);
            } catch (NumberFormatException e) {
                System.out.println("[!] Invalid number. Try again.");
            }
        }
    }

    private static LocalDate readDate(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();
            if (input.isEmpty()) return LocalDate.now();
            try {
                return LocalDate.parse(input);
            } catch (DateTimeParseException e) {
                System.out.println("[!] Invalid date format. Use YYYY-MM-DD.");
            }
        }
    }
}
