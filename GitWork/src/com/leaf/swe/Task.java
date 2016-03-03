package com.leaf.swe;

import java.util.Date;

/**
 * Rappresenta un singolo Task di Teamwork.
 */
public class Task {
    String tasklistID; //Tasklist della quale il task fa parte
    String id;
    String title;
    //Date dueDate;

    public Task(String tasklistID, String id, String title) {
        this.tasklistID = tasklistID;
        this.id = id;
        this.title = title;
    }

    public String getTasklistID() { return tasklistID;}

    public String getId() { return id;}

    public String getTitle() { return title;}

    public void setTasklistID(String tasklistID) { this.tasklistID = tasklistID;}

    public void setId(String id) { this.id = id;}

    public void setTitle(String title) { this.title = title;}
}
