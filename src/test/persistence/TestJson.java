package persistence;

import model.Exercise;
import model.Rep;
import model.Set;
import model.Workout;

import static org.junit.jupiter.api.Assertions.assertEquals;

// This class references code from this CPSC210/JsonSerializationDemo
// Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

public class TestJson {

    protected void checkRep(int number, int status, Rep rep) {
        assertEquals(number, rep.getNumber());
        assertEquals(status, rep.getStatus());
    }

    protected void checkSet(int number, int sizeReps, Set set) {
        assertEquals(number, set.getNumber());
        assertEquals(sizeReps, set.getReps().size());
    }

    protected void checkExercise(String exerciseType, String description, int weight, int sizeSets, Exercise exercise) {
        assertEquals(exerciseType, exercise.getClass().toString());
        assertEquals(description, exercise.getDescription());
        assertEquals(weight, exercise.getWeight());
        assertEquals(sizeSets, exercise.getSets().size());
    }

    protected void checkWorkout(String label, int sizeExercise, Workout workout) {
        assertEquals(label, workout.getLabel());
        assertEquals(sizeExercise, workout.getExercises().size());
    }
}
