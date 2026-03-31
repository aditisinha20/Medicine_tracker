package meditrack;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileManager {

    private static final String MEDICINES_FILE  = "data/medicines.csv";
    private static final String DOSE_LOG_FILE   = "data/dose_log.csv";

    public static void saveMedicines(List<Medicine> medicines) {
        ensureDir();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(MEDICINES_FILE))) {
            for (Medicine m : medicines) {
                bw.write(m.toCsv());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("[ERROR] Could not save medicines: " + e.getMessage());
        }
    }

    public static List<Medicine> loadMedicines() {
        ensureDir();
        List<Medicine> list = new ArrayList<>();
        File f = new File(MEDICINES_FILE);
        if (!f.exists()) return list;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.isBlank()) list.add(Medicine.fromCsv(line.trim()));
            }
        } catch (IOException e) {
            System.out.println("[ERROR] Could not load medicines: " + e.getMessage());
        }
        return list;
    }

    public static void saveDoseLogs(List<DoseRecord> logs) {
        ensureDir();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(DOSE_LOG_FILE))) {
            for (DoseRecord dr : logs) {
                bw.write(dr.toCsv());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("[ERROR] Could not save dose log: " + e.getMessage());
        }
    }

    public static List<DoseRecord> loadDoseLogs() {
        ensureDir();
        List<DoseRecord> list = new ArrayList<>();
        File f = new File(DOSE_LOG_FILE);
        if (!f.exists()) return list;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.isBlank()) list.add(DoseRecord.fromCsv(line.trim()));
            }
        } catch (IOException e) {
            System.out.println("[ERROR] Could not load dose log: " + e.getMessage());
        }
        return list;
    }

    private static void ensureDir() {
        new File("data").mkdirs();
    }
}
