package model;


import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestExercise {
    private Exercise exercise;

    @BeforeEach
    public void runBefore() {
        exercise = new Exercise("Lat pull down", 50);
    }

    @Test
    public void testConstructor() {
        assertEquals("Lat pull down", exercise.getDescription());
        assertEquals(50, exercise.getWeight());
        assertEquals(0, exercise.getSets().size());
    }

    @Test
    public void testAddSet() {
        exercise.addSet(2);
        assertEquals(1, exercise.getSets().size());
        assertEquals(2, exercise.getSets().get(0).getNumber());
    }

    @Test
    public void testIncrementWeight() {
        exercise.incrementWeight();
        assertEquals(55, exercise.getWeight());
    }

    @Test
    public void testDeloadWeight() {
        exercise.deloadWeight();
        assertEquals(45, exercise.getWeight());
    }

    @Test
    public void testToJson() {
        exercise.addSet(1);

        JSONObject jsonExercise = exercise.toJson();
        assertEquals("Lat pull down", jsonExercise.getString("description"));
        assertEquals(50, jsonExercise.getInt("weight"));
        assertEquals(1, jsonExercise.getJSONArray("sets").length());
        assertEquals("", jsonExercise.getString("exerciseType"));
    }
}



