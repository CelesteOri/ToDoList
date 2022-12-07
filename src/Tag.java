/**
/*
 * FILE: Tag.java
 * AUTHOR(S): Joanna Zabasajja
 * DATE: Dec. 6 2022
 * PROJECT: ToDo List
 * DESCRIPTION: This is the tag class. Here the tags are set up for each task depending on for what the task is for.
 * For example: Work, school or home
 */

public class Tag {
    private String info;

    //Tag display information
    public Tag(String info) {
        this.info = info;
    }

    public void setInfo(String info) { this.info = info; }
    public String getTitle() { return this.info; }

}