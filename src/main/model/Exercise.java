package model;

import persistence.Writable;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

// Represents general exercise

public class Exercise implements Writable {

    protected String description;           // detailed description of the exercise
    protected int weight;                   // the prescribed weight of the exercise
    protected List<Set> sets;               // list of prescribed sets in this exercise

    // EFFECTS: constructs an Exercise with description, weight, and empty list of sets
    // REQUIRES: description cannot be zero length
    public Exercise(String description, int weight) {
        this.description = description;
        this.weight = weight;
        this.sets = new ArrayList<Set>();
    }

    public String getDescription() {
        return description;
    }

    public int getWeight() {
        return weight;
    }

    public List<Set> getSets() {
        return sets;
    }

    // EFFECTS: makes a new set with a set number and adds it to the list of sets
    // MODIFIES: this
    public void addSet(int number) {
        Set set = new Set(description, number);
        sets.add(set);
        EventLog.getInstance().logEvent(
                new Event("Added set " + set.getNumber() + " to " + description));
    }

    // EFFECTS: adds a manually loaded set to sets, intended for JsonReader
    // MODIFIES: this
    public void loadSet(Set set) {
        sets.add(set);
        EventLog.getInstance().logEvent(
                new Event("Loaded set " + set.getNumber() + " to " + description));
    }

    //EFFECTS: increases the prescribed weight by 10
    //MODIFIES: this
    public void incrementWeight() {
        weight += 5;
        EventLog.getInstance().logEvent(
                new Event("Increased " + description + " weight by 5 lbs"));
    }

    //EFFECT: decreases the weight by 10% and rounds down to a multiple of 5
    //MODIFIES: this
    public void deloadWeight() {
        weight = (int) (5 * (Math.round(weight * 0.9) / 5));
        EventLog.getInstance().logEvent(
                new Event("Decreased " + description + " weight by 10%"));
    }

    // This method references code from this CPSC210/JsonSerializationDemo
    // Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    @Override
    public JSONObject toJson() {
        JSONObject jsonExercise = new JSONObject();
        jsonExercise.put("description", description);
        jsonExercise.put("weight", weight);
        putExerciseType(jsonExercise);
        jsonExercise.put("sets", setsToJson());
        return jsonExercise;
    }

    // EFFECTS: puts plain exercise type field in Json exercise field
    public void putExerciseType(JSONObject jsonExercise) {
        jsonExercise.put("exerciseType", "");
    }

    // This method references code from this CPSC210/JsonSerializationDemo
    // Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: returns sets in this workout as a JSON array
    private JSONArray setsToJson() {
        JSONArray jsonSets = new JSONArray();

        for (Set set : sets) {
            jsonSets.put(set.toJson());
        }
        return jsonSets;
    }
}
