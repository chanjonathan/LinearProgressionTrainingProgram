package model;

import persistence.Writable;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

// Represents a Workout

public class Workout implements Writable {
    private String label;               // the label of the workout
    private List<Exercise> exercises;

    // Construct a Workout.
    // effects: Creates an empty workout with label
    public Workout(String label) {
        this.label = label;
        exercises = new ArrayList<Exercise>();
    }

    public String getLabel() {
        return label;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    // EFFECTS: makes a new exercise, either squat, bench press, or deadlift based on the exerciseType parameter,
    //          with description and weight, and adds it to the list of exercises
    // REQUIRES: weight must be a multiple of 5
    // MODIFIES: this
    public void addExercise(String exerciseType, String description, int weight) {
        Exercise exercise;
        switch (exerciseType) {
            case "s":
                exercise = new Squat(description, weight);
                break;
            case "b":
                exercise = new BenchPress(description, weight);
                break;
            case "d":
                exercise = new Deadlift(description, weight);
                break;
            default:
                exercise = new Exercise(description, weight);
                break;
        }
        EventLog.getInstance().logEvent(
                new Event("Added " + description + " to " + label));
        exercises.add(exercise);
    }

    // EFFECTS: adds a manually loaded exercise to exercises, intended for JsonReader
    // MODIFIES: this
    public void loadExercise(Exercise exercise) {
        exercises.add(exercise);
        EventLog.getInstance().logEvent(
                new Event("Loaded " + exercise.getDescription() + " to " + label));
    }

    // EFFECTS: resets every rep in all of this workout's exercises and sets and deloads any exercise containing a set
    //          with a missed rep, otherwise increments exercise
    // MODIFIES: this
    public void regenerate() {
        for (Exercise exercise : exercises) {
            boolean missed = false;
            for (Set set : exercise.getSets()) {
                for (Rep rep : set.getReps()) {
                    if (rep.getStatus() == -1) {
                        missed = true;
                    }
                    rep.reset();
                }
            }
            if (missed) {
                exercise.deloadWeight();
            } else {
                exercise.incrementWeight();
            }
        }
    }

    // This method references code from this CPSC210/JsonSerializationDemo
    // Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    @Override
    public JSONObject toJson() {
        JSONObject jsonWorkout = new JSONObject();
        jsonWorkout.put("label", label);
        jsonWorkout.put("exercises", exercisesToJson());
        return jsonWorkout;
    }

    // This method references code from this CPSC210/JsonSerializationDemo
    // Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: returns exercises in this workout as a JSON array
    private JSONArray exercisesToJson() {
        JSONArray jsonExercises = new JSONArray();

        for (Exercise exercise : exercises) {
            jsonExercises.put(exercise.toJson());
        }
        return jsonExercises;
    }
}