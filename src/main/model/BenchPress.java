package model;

// Represents a Bench Press exercise

import org.json.JSONObject;

public class BenchPress extends Exercise {

    public BenchPress(String description, int weight) {
        super(description, weight);
    }


    @Override
    // EFFECTS: puts bench press exercise type field in Json exercise field
    public void putExerciseType(JSONObject jsonExercise) {
        jsonExercise.put("exerciseType", "b");
    }
}
