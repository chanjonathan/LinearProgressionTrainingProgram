package model;

// Represents a Repetition

import persistence.Writable;
import org.json.JSONObject;

public class Rep implements Writable {

    private int number;         // the number or "name" to refer to the rep by
    private int status;         // -1,0,1 corresponding to missed,incomplete,complete

    private int setNumber;
    private String exerciseDescription;

    // EFFECTS: constructs a rep with a number and a status of zero
    // REQUIRES: number must be greater than zero
    public Rep(String exerciseDescription, int setNumber, int number) {
        this.number = number;
        this.status = 0;

        this.exerciseDescription = exerciseDescription;
        this.setNumber = setNumber;
    }

    public int getNumber() {
        return number;
    }

    public int getStatus() {
        return status;
    }

    // EFFECTS: changes the status of a rep
    // MODIFIES: this
    public void loadStatus(int status) {
        this.status = status;
        String statusDescription;
        switch (status) {
            case 1:
                statusDescription = "complete";
                break;
            case -1:
                statusDescription = "missed";
                break;
            default:
                statusDescription = "unmarked";
                break;
        }
        EventLog.getInstance().logEvent(
                new Event("Set " + exerciseDescription + " set " + setNumber + " rep " + this.getNumber()
                        + " to " + statusDescription));
    }


    public int getSetNumber() {
        return setNumber;
    }

    public String getExerciseDescription() {
        return exerciseDescription;
    }

    //EFFECT: changes status to 1
    //MODIFIES: this
    public void complete() {
        status = 1;
        EventLog.getInstance().logEvent(
                new Event("Marked " + exerciseDescription + " set " + setNumber + " rep " + this.getNumber()
                        + " as complete"));
    }

    //EFFECT: changes status to -1
    //MODIFIES: this
    public void miss() {
        status = -1;
        EventLog.getInstance().logEvent(
                new Event("Marked " + exerciseDescription + " set " + setNumber + " rep " + this.getNumber()
                        + " as missed"));
    }

    //EFFECT: changes status to back to 0
    //MODIFIES: this
    public void reset() {
        status = 0;
        EventLog.getInstance().logEvent(
                new Event("Reset " + exerciseDescription + " set " + setNumber + " rep " + this.getNumber()));
    }

    // This method references code from this CPSC210/JsonSerializationDemo
    // Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    @Override
    public JSONObject toJson() {
        JSONObject jsonRep = new JSONObject();
        jsonRep.put("number", number);
        jsonRep.put("status", status);
        return jsonRep;
    }
}