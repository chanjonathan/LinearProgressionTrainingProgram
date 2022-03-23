package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;


// This class references code from this CPSC210/AlarmSystem
// Link: https://github.students.cs.ubc.ca/CPSC210/AlarmSystem
public class TestEventLog {
    private List<Event> logList;

    private Event event1;
    private Event event2;
    private Event event3;

    @BeforeEach
    public void runBefore() {
        logList = new ArrayList<>();

        event1 = new Event("Event 1");
        event2 = new Event("Event 2");
        event3 = new Event("Event 3");
    }

    @Test
    public void testLogEvent() {
        EventLog eventLog = EventLog.getInstance();
        eventLog.logEvent(event1);
        eventLog.logEvent(event2);
        eventLog.logEvent(event3);

        for (Event event : eventLog) {
            logList.add(event);
        }
        assertTrue(logList.contains(event1));
        assertTrue(logList.contains(event2));
        assertTrue(logList.contains(event3));
    }
}
