package model;

import persistence.Writable;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

// Represents a Set

public class Set implements Writable {

    private int number;         // the number or "name" to refer to the set by
    private List<Rep> reps;     // list of prescribed reps in this set
    private String exerciseDescription;

    // EFFECTS: constructs a set with a number and empty list of reps
    // REQUIRES: number must be greater than zero
    public Set(String exerciseDescription, int number) {
        this.number = number;
        reps = new ArrayList<Rep>();

        this.exerciseDescription = exerciseDescription;
    }

    public int getNumber() {
        return number;
    }

    public List<Rep> getReps() {
        return reps;
    }

    public String getExerciseDescription() {
        return exerciseDescription;
    }

    // EFFECTS: makes a new rep with a rep number and adds it to the list of reps
    // MODIFIES: this
    public void addRep(int number) {
        Rep rep = new Rep(exerciseDescription, this.number, number);
        reps.add(rep);
        EventLog.getInstance().logEvent(
                new Event("Added rep " + number + " to " + exerciseDescription + " set "
                        + this.number));
    }

    // EFFECTS: adds a manually loaded rep to reps, intended for JsonReader
    // MODIFIES: this
    public void loadRep(Rep rep) {
        reps.add(rep);
        EventLog.getInstance().logEvent(
                new Event("Loaded rep " + rep.getNumber() + " into " + exerciseDescription
                        + " set " + number));
    }

    // This method references code from this CPSC210/JsonSerializationDemo
    // Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    @Override
    public JSONObject toJson() {
        JSONObject jsonSet = new JSONObject();
        jsonSet.put("number", number);
        jsonSet.put("reps", repsToJson());
        return jsonSet;
    }

    // This method references code from this CPSC210/JsonSerializationDemo
    // Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: returns reps in this workout as a JSON array
    private JSONArray repsToJson() {
        JSONArray jsonReps = new JSONArray();

        for (Rep rep : reps) {
            jsonReps.put(rep.toJson());
        }
        return jsonReps;
    }
}
