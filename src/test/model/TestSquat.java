package model;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestSquat {
    private Exercise squat;

    @BeforeEach
    public void runBefore() {
        squat = new Squat("2 count paused squat", 175);
    }

    @Test
    public void testConstructor() {
        assertEquals("2 count paused squat", squat.getDescription());
        assertEquals(175, squat.getWeight());
        assertEquals(0, squat.getSets().size());
    }

    @Test
    public void testAddSet() {
        squat.addSet(5);
        assertEquals(1, squat.getSets().size());
        assertEquals(5, squat.getSets().get(0).getNumber());
    }

    @Test
    public void testIncrementWeight() {
        squat.incrementWeight();
        assertEquals(185, squat.getWeight());
    }

    @Test
    public void testDeloadWeight() {
        squat.deloadWeight();
        assertEquals(155, squat.getWeight());
    }

    @Test
    public void testToJson() {
        squat.addSet(1);

        JSONObject jsonExercise = squat.toJson();
        assertEquals("2 count paused squat", jsonExercise.getString("description"));
        assertEquals(175, jsonExercise.getInt("weight"));
        assertEquals(1, jsonExercise.getJSONArray("sets").length());
        assertEquals("s", jsonExercise.getString("exerciseType"));
    }
}



