package model;


import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestBenchPress {
    private Exercise benchpress;


    @BeforeEach
    public void runBefore() {
        benchpress = new BenchPress("Close grip bench press", 125);
    }

    @Test
    public void testConstructor() {
        assertEquals("Close grip bench press", benchpress.getDescription());
        assertEquals(125, benchpress.getWeight());
        assertEquals(0, benchpress.getSets().size());
    }

    @Test
    public void testAddSet() {
        benchpress.addSet(2);
        assertEquals(1, benchpress.getSets().size());
        assertEquals(2, benchpress.getSets().get(0).getNumber());
    }

    @Test
    public void testIncrementWeight() {
        benchpress.incrementWeight();
        assertEquals(130, benchpress.getWeight());
    }

    @Test
    public void testDeloadWeight() {
        benchpress.deloadWeight();
        assertEquals(110, benchpress.getWeight());
    }

    @Test
    public void testToJson() {
        benchpress.addSet(1);

        JSONObject jsonExercise = benchpress.toJson();
        assertEquals("Close grip bench press", jsonExercise.getString("description"));
        assertEquals(125, jsonExercise.getInt("weight"));
        assertEquals(1, jsonExercise.getJSONArray("sets").length());
        assertEquals("b", jsonExercise.getString("exerciseType"));
    }
}



