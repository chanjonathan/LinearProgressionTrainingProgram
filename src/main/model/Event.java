package model;

import java.util.Calendar;
import java.util.Date;

// This class references code from this CPSC210/AlarmSystem
// Link: https://github.students.cs.ubc.ca/CPSC210/AlarmSystem
public class Event {
    private Date date;
    private String description;

    // EFFECTS: Constructs a new Event with description and date of construction
    public Event(String description) {
        date = Calendar.getInstance().getTime();
        this.description = description;
    }


    public Date getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    @Override
    // EFFECTS: returns the description of the event with the date as one string
    public String toString() {
        return date.toString() + ": " + description;
    }
}
