package model;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestRep {
    private Rep rep;


    @BeforeEach
    public void runBefore() {
        rep = new Rep("Exercise", 1, 1);
    }

    @Test
    public void testConstructor() {
        assertEquals(1, rep.getNumber());
        assertEquals(0, rep.getStatus());
    }

    @Test
    public void testComplete() {
        rep.complete();
        assertEquals(1, rep.getStatus());
    }

    @Test
    public void testMiss() {
        rep.miss();
        assertEquals(-1, rep.getStatus());
    }

    @Test
    public void testReset() {
        rep.complete();
        rep.reset();
        assertEquals(0, rep.getStatus());
        rep.miss();
        rep.reset();
        assertEquals(0, rep.getStatus());
    }

    @Test
    public void testToJson() {
        JSONObject jsonRep = rep.toJson();
        assertEquals(1, jsonRep.getInt("number"));
        assertEquals(0, jsonRep.getInt("status"));

        rep.complete();
        JSONObject jsonRepComplete = rep.toJson();
        assertEquals(1, jsonRepComplete.getInt("status"));

        rep.miss();
        JSONObject jsonRepMissed = rep.toJson();
        assertEquals(-1, jsonRepMissed.getInt("status"));


    }
}
