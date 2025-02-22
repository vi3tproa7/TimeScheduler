package src;

import java.util.Date;

public class Event {
    private String ID;
    private String userID;
    private String title;
    private String description;
    private Date date;
    private Date remind;
    private String location;
    private int duration;
    // private Date startTime;
    private int priority;

    public Event(String userID, String title, Date date, int duration)
    {
        this.userID = userID;
        this.title = title;
        this.date = date;
        // this.startTime = startTime;
        this.duration = duration;
    }

    public Event(String userID, String title, String description, Date date, Date remind, String location, int duration, int priority)
    {
        this.userID = userID;
        this.title = title;
        this.description = description;
        this.date = date;
        this.remind = remind;
        this.location = location;
        this.duration = duration;
        this.priority = priority;
        // this.startTime = startTime;
    }
    
    public Event(String id, String userID, String title, String description, Date date, Date remind, String location,
            int duration, int priority) {
        this.ID = id;
        this.userID = userID;
        this.title = title;
        this.description = description;
        this.date = date;
        this.remind = remind;
        this.location = location;
        this.duration = duration;
        this.priority = priority;
    }

    public String getID() {return ID;}
    public void setID(String ID) {this.ID = ID;}

    public String getUserID() {return userID;}
    public void setUserID(String userID) {this.userID = userID;}

    public String getTitle() {return title;}
    public void setTitle(String title) {this.title = title;}

    public String getDescription() {return description;}
    public void setDescription(String descripton) {this.description = descripton;}

    public Date getDate() {return date;}
    public void setDate(Date date) {this.date = date;}

    public String getLocation() {return location;}
    public void setLocaction(String location) {this.location = location;}

    public int getDuration() {return duration;}
    public void setDuration(int duration) {this.duration = duration;}

    public int getPriority() {return priority;}
    public void setPriority(int priority) {this.priority = priority;}

    public Date getRemind() {return remind;}
    public void setRemind(Date remind) {this.remind = remind;}
    
    /* public Date getStartTime() {return startTime;}
    public void setStartTime(Date startTime) {this.startTime = startTime;} */

}

