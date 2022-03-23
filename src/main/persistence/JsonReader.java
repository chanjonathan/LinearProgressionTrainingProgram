package persistence;

import model.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// This class references code from this CPSC210/JsonSerializationDemo
// Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

// Represents a reader that reads workout from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads workout from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Workout read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseWorkout(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses workout from JSON object and returns it
    private Workout parseWorkout(JSONObject jsonWorkout) {
        String label = jsonWorkout.getString("label");
        Workout currentWorkout = new Workout(label);
        addExercises(currentWorkout, jsonWorkout);
        return currentWorkout;
    }

    // MODIFIES: workout
    // EFFECTS: parses exercises from JSON object and adds them to workroom
    private void addExercises(Workout currentWorkout, JSONObject jsonWorkout) {
        JSONArray jsonExercises = jsonWorkout.getJSONArray("exercises");
        for (Object json : jsonExercises) {
            JSONObject jsonExercise = (JSONObject) json;
            addExercise(currentWorkout, jsonExercise);
        }
    }

    // MODIFIES: workout
    // EFFECTS: parses exercise from JSON object and adds the correct subclass of exercise to workout
    private void addExercise(Workout currentWorkout, JSONObject jsonExercise) {
        String exerciseType = jsonExercise.getString("exerciseType");
        String description = jsonExercise.getString("description");
        int weight = jsonExercise.getInt("weight");
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
        }
        currentWorkout.loadExercise(exercise);
        addSets(exercise, jsonExercise);
    }

    // MODIFIES: exercise
    // EFFECTS: parses sets from JSON object and adds them to exercise
    private void addSets(Exercise currentExercise, JSONObject jsonExercise) {
        JSONArray jsonSets = jsonExercise.getJSONArray("sets");
        for (Object json : jsonSets) {
            JSONObject jsonSet = (JSONObject) json;
            addSet(currentExercise, jsonSet);
        }
    }

    // MODIFIES: exercise
    // EFFECTS: parses set from JSON object and adds it to workroom
    private void addSet(Exercise currentExercise, JSONObject jsonSet) {
        int number = jsonSet.getInt("number");
        Set set = new Set(currentExercise.getDescription(), number);
        currentExercise.loadSet(set);
        addReps(set, jsonSet);
    }

    // MODIFIES: set
    // EFFECTS: parses reps from JSON object and adds them to set
    private void addReps(Set currentSet, JSONObject jsonSet) {
        JSONArray jsonReps = jsonSet.getJSONArray("reps");
        for (Object json : jsonReps) {
            JSONObject jsonRep = (JSONObject) json;
            addRep(currentSet, jsonRep);
        }
    }

    // MODIFIES: set
    // EFFECTS: parses rep from JSON object and adds it to set
    private void addRep(Set currentSet, JSONObject jsonRep) {
        int number = jsonRep.getInt("number");
        Rep rep = new Rep(currentSet.getExerciseDescription(), currentSet.getNumber(), number);
        currentSet.loadRep(rep);
        int status = jsonRep.getInt("status");
        rep.loadStatus(status);
    }
}


