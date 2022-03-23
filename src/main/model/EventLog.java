package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

// This class references code from this CPSC210/AlarmSystem
// Link: https://github.students.cs.ubc.ca/CPSC210/AlarmSystem

// Represents a log with a list of events
public class EventLog implements Iterable<Event> {
    private static EventLog eventLog;
    private List<Event> events;

    // EFFECTS: Constructs an EventLog with empty list of events
    private EventLog() {
        events = new ArrayList<Event>();
    }

    // EFFECTS: returns event log, creates one if null
    public static EventLog getInstance() {
        if (eventLog == null) {
            eventLog = new EventLog();
        }
        return eventLog;
    }

    // EFFECTS: add event to list of events
    // MODIFIES: this
    public void logEvent(Event event) {
        events.add(event);
    }

    @Override
    // EFFECTS: allows EventLog to be iterated over, iterating over the list of events
    public Iterator<Event> iterator() {
        return events.iterator();
    }
}