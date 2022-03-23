package ui;

import model.Exercise;
import model.Rep;
import model.Set;
import model.Workout;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

// Linear Progression Training Program application

public class LinearProgressionTrainingProgram {
    private static final String FILE = "./data/LinearProgressionTrainingProgram.json";
    private int setsPerExercise;
    private int repsPerSet;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: Runs Linear Progression Training Program application, initializing variables
    public LinearProgressionTrainingProgram() {
        input = new Scanner(System.in);
        jsonWriter = new JsonWriter(FILE);
        jsonReader = new JsonReader(FILE);
        printWelcome();
        loadOrNew();
    }

    // EFFECTS: Presents user with options: load progress or begin new program
    private void loadOrNew() {
        System.out.println("n - > start new program");
        System.out.println("l - > load saved program");
        String command = filterForCommand("l", "n", "");
        switch (command) {
            case "l":
                loadProgress();
                break;
            case "n":
                collectUserPreferences();
                break;
        }
    }

    // EFFECTS: prints welcome message
    private void printWelcome() {
        System.out.println("                                                  \n"
                + "                         `....``                  \n"
                + "                 `/:-.-:/+ss+-.``.`               \n"
                + "                :ddy+:/+sdmNds-..```              \n"
                + "               `hho+:.``.:odmmd+.````             \n"
                + "              `/-`/yh- `.:+hmNNd:yhy-             \n"
                + "               /y+sddy.`./yydddo-mmo:             \n"
                + "               /d/s/---.``o-.:/+:omo              \n"
                + "               `o-sy++/:` .` `./o/ms              \n"
                + "                `/+o```  `.  `.:/sNm`             \n"
                + "                `h:.   `..`.:oydmNNNo`            \n"
                + "                 ::`  --.`/hdddmmNNNNy/--.``      \n"
                + "                      mNh:+hmyhmNNNNNNdy+++/:::-.`\n"
                + "                   `.-dMMh:+s+ymNmNNNNms-....-:://\n"
                + "                ./shmmNMMm+-:/ydsossooo/:--::/:...\n"
                + "           `.-:sNNmdhhmmNN+.sdsy+/++///::/+oo/-.:+\n"
                + "    `-:+osydhs++mNNNmdyoooyyNdys+://+++++///:::+oy\n"
                + " -sdNMMNNmhyyhdmdhyyhho/--/mNy+++oyhhyo++//:::://+\n"
                + "mMMMMNdhsymNmmmddddhys+:.`+mhsooshhyso+/::::::::::\n"
                + "MMNmyo/-sNMNNmdhyso+/-..``+mdhhhhys+/::------:/oo/");
        System.out.println("\nWelcome to Linear Progression Training Program!");
    }

    // EFFECTS: prompts user to input initial training max numbers and desired type of "a x b" program
    private void collectUserPreferences() {

        System.out.println();
        int maxSquat = filterForInteger("Input your squat training max: ");
        int maxBenchPress = filterForInteger("Input your bench press training max: ");
        int maxDeadlift = filterForInteger("Input your deadlift training max: ");

        System.out.println();
        System.out.println("Input the type of program desired.");
        setsPerExercise = filterForInteger("Sets: ");
        repsPerSet = filterForInteger("Reps: ");

        startTrainingProgram(maxSquat, maxBenchPress, maxDeadlift);
    }

    // EFFECTS: Creates a new workout with three exercises: squat, bench press, and deadlift, with  50% of training max
    //          weight, and adds the user specified number of sets to each exercise, and adds the user specified
    //          number of reps to each set.
    private void startTrainingProgram(int maxSquat, int maxBenchPress, int maxDeadlift) {
        int initialSquat = (int) (5 * (Math.round(maxSquat * 0.5) / 5));
        int initialBenchPress = (int) (5 * (Math.round(maxBenchPress * 0.5) / 5));
        int initialDeadlift = (int) (5 * (Math.round(maxDeadlift * 0.5) / 5));

        Workout currentWorkout = new Workout("Workout A");

        currentWorkout.addExercise("s", "Competition squat", initialSquat);
        currentWorkout.addExercise("b", "Competition bench press", initialBenchPress);
        currentWorkout.addExercise("d", "Competition deadlift", initialDeadlift);

        for (Exercise exercise : currentWorkout.getExercises()) {
            for (int i = 0; i < setsPerExercise; i++) {
                exercise.addSet(i + 1);
            }
            for (Set set : exercise.getSets()) {
                for (int i = 0; i < repsPerSet; i++) {
                    set.addRep(i + 1);
                }
            }
        }
        logTrainingProgram(currentWorkout);
    }

    // EFFECTS: cycles through each exercise, set, and rep, calling print functions and user input functions while
    //          tracking where the current rep is
    // MODIFIES: this
    public void logTrainingProgram(Workout currentWorkout) {

        int currentExercise = 0;
        int currentSet = 0;
        int currentRep = 0;

        for (Exercise exercise : currentWorkout.getExercises()) {
            for (Set set : exercise.getSets()) {
                for (Rep rep : set.getReps()) {
                    printWorkout(currentWorkout, currentExercise, currentSet, currentRep);
                    collectMarkings(rep);
                    currentRep++;
                }
                currentSet++;
            }
            currentExercise++;
        }
        printWorkout(currentWorkout, currentExercise, currentSet, currentRep);
        continueTrainingProgram(currentWorkout, "----------Workout complete!----------");
    }

    // EFFECTS: prompts user to input a command to either completes a rep or marks a rep as missed
    // MODIFIES: this
    private void collectMarkings(Rep rep) {
        System.out.println("-------------------------------------");
        System.out.println("c - > mark complete");
        System.out.println("m - > mark missed");

        String command = filterForCommand("c", "m", "");
        switch (command) {
            case "c":
                rep.complete();
                break;
            case "m":
                rep.miss();
                break;
        }
    }

    // EFFECTS: prompts the user to input a command to either regenerate the workout and log the program again or exit
    // MODIFIES: this
    private void continueTrainingProgram(Workout currentWorkout, String message) {
        System.out.println(message);
        System.out.println("n - > next workout");
        System.out.println("s - > save progress");
        System.out.println("x - > exit");

        String command = filterForCommand("n", "s", "x");
        switch (command) {
            case "n":
                currentWorkout.regenerate();
                logTrainingProgram(currentWorkout);
                break;
            case "s":
                saveProgress(currentWorkout);
                continueTrainingProgram(currentWorkout, "-----------Progress saved!-----------");
                break;
            case "x":
                endProgram();
                break;
        }
    }

    // EFFECTS: filters user input until they input one of a, b, or c, otherwise tells user that their input is invalid
    private String filterForCommand(String a, String b, String c) {

        boolean inputValid = false;

        String stringInput = "";

        while (!inputValid) {
            stringInput = input.next();
            if (stringInput.equals(a) || stringInput.equals(b) || stringInput.equals(c)) {
                inputValid = true;
            } else {
                System.out.println("Invalid input: " + stringInput);
            }
        }
        return stringInput;
    }

    // EFFECTS: prompts user with field to input and filters user input until they input a valid java integer,
    //          otherwise tells user that their input is invalid
    // REQUIRES: fieldPrompt must not be zero length
    private int filterForInteger(String fieldPrompt) {

        boolean inputValid = false;

        String stringInput;
        int intInput = 0;

        while (!inputValid) {
            System.out.print(fieldPrompt);
            stringInput = input.next();
            try {
                intInput = Integer.parseInt(stringInput);
                inputValid = true;
            } catch (Exception e) {
                System.out.println("Invalid input: " + stringInput);
            }
        }
        return intInput;
    }

    // EFFECTS: prints the whole program for user reference, including every rep belonging to every set, belonging to
    // every exercise, to the workout along with the status of the rep and a current rep indicator
    private void printWorkout(Workout currentWorkout, int currentExercise, int currentSet, int currentRep) {
        int exerciseIndex = 0;
        int setIndex = 0;
        int repIndex = 0;

        System.out.println("\n\n--------------" + currentWorkout.getLabel() + "--------------");

        for (Exercise exercise : currentWorkout.getExercises()) {
            System.out.println(exercise.getDescription() + ": " + exercise.getWeight() + " lbs");
            for (Set set : exercise.getSets()) {
                System.out.println("      " + "Set " + set.getNumber());
                for (Rep rep : set.getReps()) {
                    System.out.print("            " + "Rep " + rep.getNumber());
                    boolean repIsEditing = currentExercise == exerciseIndex && currentSet == setIndex
                            && currentRep == repIndex;
                    printRepStatus(rep.getStatus(), repIsEditing);
                    repIndex++;
                }
                setIndex++;
            }
            exerciseIndex++;
        }
    }

    // EFFECTS: prints the complete, missed, or a blank if a rep is incomplete. also prints an indicator if the rep
    //          the incomplete rep is also the one being edited by logTrainingProgram
    private void printRepStatus(int status, boolean repIsEditing) {
        switch (status) {
            case 0:
                System.out.print(": ");
                if (repIsEditing) {
                    System.out.println("        <<<<<<<<<<");
                } else {
                    System.out.println(" ");
                }
                break;
            case 1:
                System.out.println(": Complete");
                break;
            case -1:
                System.out.println(": Missed");
                break;
        }
    }

    private void endProgram() {
        System.out.println("\nGoodbye!");
    }

    // This method references code from this CPSC210/JsonSerializationDemo
    // Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // tries to write currentWorkout into json file, otherwise prints error message and calls for continue prompt
    private void saveProgress(Workout currentWorkout) {
        try {
            jsonWriter.open();
            jsonWriter.write(currentWorkout);
            jsonWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error writing save file " + FILE);
            continueTrainingProgram(currentWorkout, "---------Progress not saved!---------");
        }
    }

    // This method references code from this CPSC210/JsonSerializationDemo
    // Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: tries to read json file and to currentWorkout and calls for workout to be regenerated and logged
    // if successful, otherwise prints error message calls load or new prompt again
    // MODIFIES: currentWorkout
    private void loadProgress() {
        try {
            Workout currentWorkout = jsonReader.read();
            currentWorkout.regenerate();
            logTrainingProgram(currentWorkout);
        } catch (IOException e) {
            System.out.println("Error reading save file " + FILE);
            loadOrNew();
        }
    }
}