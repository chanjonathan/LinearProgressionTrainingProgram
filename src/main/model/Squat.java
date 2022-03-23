package model;

// Represents a Squat exercise

import org.json.JSONObject;

public class Squat extends Exercise {

    public Squat(String description, int weight) {
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
    // EFFECTS: puts squat exercise type field in Json exercise field
    public void putExerciseType(JSONObject jsonExercise) {
        jsonExercise.put("exerciseType", "s");
    }
}
