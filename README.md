MediTrack — Personal Medicine Tracker
Student Name: Aditi Sinha
Reg. No.: 24BHI10024
Course: Programming in Java
Environment: Windows / Linux / macOS (any JDK 11+)

What is MediTrack?
MediTrack is a command-line Java application that helps individuals track their daily medicine schedules. It solves a real, everyday problem — people forget doses, don't notice low stock, or lose track of which medicines are still active. MediTrack keeps all of this organised in a persistent, file-based system so your data survives every session.

Features

Add a medicine — name, dosage, frequency (1×/2×/3× per day), start date, duration
View today's schedule — see what's due, how many doses have been taken, and what's still needed
Mark a dose as taken — logs the time and updates tablet count
List all medicines — shows status (Active / Upcoming / Completed) for every entry
Stock summary — flags medicines with 3 days or fewer of supply remaining
Dose history — full log of every dose taken for a specific medicine
Remove a medicine — delete an entry from the tracker
Persistent storage — all data saved to CSV files; nothing lost on exit


Java Concepts Used
ConceptWhere AppliedOOP — Classes & EncapsulationMedicine, DoseRecord, Tracker, FileManagerArrayListStoring medicine list and dose logsHashMap (ready for extension)Tracker internal lookupsFile I/O — BufferedReader / BufferedWriterFileManager.java — save & load CSVLocalDate / LocalTimeScheduling, duration maths, dose timestampsScannerCLI menu and all user inputException handlingInvalid input, missing files, date parse errorsString manipulationCSV serialisation / deserialisation

Repository Structure
meditrack-24BHI10024/
├── README.md
├── src/
│   └── meditrack/
│       ├── Main.java          ← Entry point, CLI menu
│       ├── Medicine.java      ← Medicine model + CSV serialisation
│       ├── DoseRecord.java    ← Dose log entry model
│       ├── Tracker.java       ← Core business logic
│       └── FileManager.java   ← File I/O (save / load)
└── data/                      ← Auto-created on first run
    ├── medicines.csv
    └── dose_log.csv

How to Run
Requirements

Java JDK 11 or higher
Any terminal (Command Prompt, PowerShell, Bash)

Steps
bash# 1. Clone the repository
git clone https://github.com/aditisinha20/meditrack-24BHI10024.git
cd meditrack-24BHI10024

# 2. Compile all source files
javac -d out src/meditrack/*.java

# 3. Run the application
java -cp out meditrack.Main
Example Session
===========================================
   MediTrack — Personal Medicine Tracker
   Aditi Sinha · 24BHI10024
===========================================

--- MENU ---
 1. Add a medicine
 2. View today's schedule
 3. Mark a dose as taken
 4. List all medicines
 5. Stock summary
 6. Dose history for a medicine
 7. Remove a medicine
 0. Exit
Enter choice: 1

--- Add Medicine ---
Name           : Paracetamol
Dosage (e.g. 500mg): 500mg
Frequency per day (1/2/3): 3
Start date (YYYY-MM-DD, Enter = today):
Duration in days: 5
[OK] Added: Paracetamol

Data Storage
MediTrack stores all data in a data/ folder that is created automatically on first run.

data/medicines.csv — one medicine per line
data/dose_log.csv — one dose record per line

CSV format (medicines):
name,dosage,frequencyPerDay,startDate,durationDays,tabletsRemaining
Paracetamol,500mg,3,2026-03-31,5,15

Low Stock Warning
MediTrack automatically warns you when a medicine has 3 days or fewer of tablets remaining:
⚠  LOW STOCK — only 2 tablet(s) left!

Author
Aditi Sinha · 24BHI10024 · Programming in Java
