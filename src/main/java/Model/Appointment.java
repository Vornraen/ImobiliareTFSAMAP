package Model;

import java.util.Date;

public class Appointment {
    private int id;
    private int propertyId;
    private int userId;
    private int agentId;
    private String date;
    private boolean done;

    public Appointment() {
    }

    public Appointment(int propertyId, int userId, int agentId, String date,boolean done) {
        this.propertyId = propertyId;
        this.userId = userId;
        this.agentId = agentId;
        this.date = date;
        this.done=done;
    }

    public Appointment(int id,int propertyId, int userId, int agentId, String date,boolean done) {
        this.id=id;
        this.propertyId = propertyId;
        this.userId = userId;
        this.agentId = agentId;
        this.date = date;
        this.done=done;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(int propertyId) {
        this.propertyId = propertyId;
    }

    public int getAgentId() {
        return agentId;
    }

    public void setAgentId(int agentId) {
        this.agentId = agentId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public String showDone(){
        if(done)
            return "Done";
        else return "Pending";
    }

}