package model;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestSet {
    private Set set;

    @BeforeEach
    public void runBefore() {
        set = new Set("Exercise", 1);
    }

    @Test
    public void testConstructor() {
        assertEquals(1, set.getNumber());
        assertEquals(0, set.getReps().size());
        assertEquals("Exercise", set.getExerciseDescription());
    }

    @Test
    public void testAddRep() {
        set.addRep(1);
        assertEquals(1, set.getReps().size());
    }

    @Test
    public void testToJson() {
        set.addRep(1);

        JSONObject jsonSet = set.toJson();
        assertEquals(1, jsonSet.getInt("number"));
        assertEquals(1, jsonSet.getJSONArray("reps").length());
    }
}


