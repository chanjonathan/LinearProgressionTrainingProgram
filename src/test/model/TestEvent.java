package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

// This class references code from this CPSC210/AlarmSystem
// Link: https://github.students.cs.ubc.ca/CPSC210/AlarmSystem
public class TestEvent {
    private Event event;
    private Date date;

    @BeforeEach
    public void runBefore() {
        event = new Event("Event happened");
        date = Calendar.getInstance().getTime();
    }

    @Test
    public void testEvent() {
        assertEquals("Event happened", event.getDescription());
        assertEquals(date.toString(), event.getDate().toString());
    }

    @Test
    public void testToString() {
        assertEquals(date.toString() + ": " + "Event happened", event.toString());
    }
}
