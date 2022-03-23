package model;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestWorkout {
    private Workout workout;

    @BeforeEach
    public void runBefore() {
        workout = new Workout("Workout A");
    }

    @Test
    public void testConstructor() {
        assertEquals("Workout A", workout.getLabel());
        assertEquals(0, workout.getExercises().size());
    }

    @Test
    public void testAddExercise() {
        workout.addExercise("s", "Front squat", 195);
        workout.addExercise("b", "Straight leg deadlift", 200);
        workout.addExercise("d", "2 count paused deadlift", 225);
        workout.addExercise("", "Cable row", 45);
        assertEquals(4, workout.getExercises().size());
        assertEquals("Front squat", workout.getExercises().get(0).getDescription());
        assertEquals(200, workout.getExercises().get(1).getWeight());
        assertEquals("2 count paused deadlift", workout.getExercises().get(2).getDescription());
        assertEquals("Cable row", workout.getExercises().get(3).getDescription());
    }

    @Test
    public void testRegenerate() {
        workout.addExercise("s", "Front squat", 195);
        workout.addExercise("b", "Straight leg deadlift", 200);
        workout.getExercises().get(0).addSet(1);
        workout.getExercises().get(0).addSet(2);
        workout.getExercises().get(1).addSet(1);
        workout.getExercises().get(0).getSets().get(0).addRep(1);
        workout.getExercises().get(0).getSets().get(0).addRep(2);
        workout.getExercises().get(0).getSets().get(1).addRep(1);
        workout.getExercises().get(0).getSets().get(1).addRep(2);
        workout.getExercises().get(1).getSets().get(0).addRep(1);
        workout.getExercises().get(0).getSets().get(0).getReps().get(0).complete();
        workout.getExercises().get(0).getSets().get(1).getReps().get(0).complete();
        workout.getExercises().get(0).getSets().get(0).getReps().get(1).miss();
        workout.getExercises().get(0).getSets().get(1).getReps().get(1).miss();
        workout.getExercises().get(1).getSets().get(0).getReps().get(0).complete();
        workout.regenerate();
        assertEquals(0, workout.getExercises().get(0).getSets().get(0).getReps().get(0).getStatus());
        assertEquals(0, workout.getExercises().get(0).getSets().get(0).getReps().get(1).getStatus());
        assertEquals(0, workout.getExercises().get(0).getSets().get(1).getReps().get(0).getStatus());
        assertEquals(0, workout.getExercises().get(0).getSets().get(1).getReps().get(1).getStatus());
        assertEquals(0, workout.getExercises().get(1).getSets().get(0).getReps().get(0).getStatus());
    }

    @Test
    public void testToJson() {
        workout.addExercise("s", "Front squat", 195);

        JSONObject jsonWorkout = workout.toJson();
        assertEquals("Workout A", jsonWorkout.getString("label"));
        assertEquals(1, jsonWorkout.getJSONArray("exercises").length());
    }
}


