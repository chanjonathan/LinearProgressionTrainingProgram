package model;

// Represents a Deadlift exercise

import org.json.JSONObject;

public class Deadlift extends Exercise {

    public Deadlift(String description, int weight) {
        super(description, weight);
    }

    //EFFECTS: increases the prescribed weight by 5
    //MODIFIES: this
    @Override
    public void incrementWeight() {
        weight += 10;
        EventLog.getInstance().logEvent(
                new Event("Increased " + description + " weight by 10 lbs"));
    }


    @Override
    // EFFECTS: puts deadlift exercise type field in Json exercise field
    public void putExerciseType(JSONObject jsonExercise) {
        jsonExercise.put("exerciseType", "d");
    }
}
