package model;


import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestDeadlift {
    private Exercise deadlift;

    @BeforeEach
    public void runBefore() {
        deadlift = new Deadlift("Romanian deadlift", 200);
    }

    @Test
    public void testConstructor() {
        assertEquals("Romanian deadlift", deadlift.getDescription());
        assertEquals(200, deadlift.getWeight());
        assertEquals(0, deadlift.getSets().size());
    }

    @Test
    public void testAddSet() {
        deadlift.addSet(3);
        assertEquals(1, deadlift.getSets().size());
        assertEquals(3, deadlift.getSets().get(0).getNumber());
    }

    @Test
    public void testIncrementWeight() {
        deadlift.incrementWeight();
        assertEquals(210, deadlift.getWeight());
    }

    @Test
    public void testDeloadWeight() {
        deadlift.deloadWeight();
        assertEquals(180, deadlift.getWeight());
    }

    @Test
    public void testToJson() {
        deadlift.addSet(1);

        JSONObject jsonExercise = deadlift.toJson();
        assertEquals("Romanian deadlift", jsonExercise.getString("description"));
        assertEquals(200, jsonExercise.getInt("weight"));
        assertEquals(1, jsonExercise.getJSONArray("sets").length());
        assertEquals("d", jsonExercise.getString("exerciseType"));
    }
}



