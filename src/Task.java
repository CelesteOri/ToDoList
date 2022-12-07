/**
/*
 * FILE: Task.java
 * AUTHOR(S): Honor Jang, Joanna Zabasajja, Michelle Uddin, Carlos Julian Garcia
 * DATE: Dec. 6 2022
 * PROJECT: ToDo List
 * DESCRIPTION:  This is the Task class, in here is the tasks descriptions and explaining what it is, how they are categorized,
 * scheduled by mm/dd/yyyy and organized by due date, the tags that one can choose for the task, and their status as completed
 * or done. 
 *     
 */


public class Task {
	private int date[] = {0, 0, 0}; // month / day / year
	private String title;
	private String description;
	private Tag tag;
	private boolean visible;
	
	private boolean valid = true;
	private boolean hover;
	private boolean completed;
	
	//Display of task's title, description, date, and possible tags
	public Task(String title, String description, int date[], Tag tag) {
		if (date.length != 3) { valid = false; }
		this.title = title;
		this.description = description;
		this.date = date;
		this.hover = false;
		this.completed = false;
		this.tag = tag;
		this.visible = true;
	}
	
	public boolean isValid() { return valid; }
	
	//Title set and get
	public void setTitle(String title) { this.title = title; }
	public String getTitle() { return title; }
	
	//Description set and get
	public void setDesc(String des) { this.description = des; }
	public String getDesc() { return description; }
	
	//Tag set and get
	public void setTag(Tag tag) { this.tag = tag; }
	public Tag getTag() { return tag; }
	
	//Visible set and get
	public void setVisibility(Boolean vis) { this.visible = vis; }
	public Boolean getVisibility() { return visible; }
	
	/*
     * Sorting tasks by date
     */
	public String getDate(int type) {
		String dateStr = "";
		int order[] = {0, 1, 2};
		if (type == 1) { int temp[] = {2, 0, 1}; order = temp;}
		for (int i = 0; i < 3; i++) {
			if (date[order[i]] < 10) { dateStr += "0"; }
			dateStr += "" + date[order[i]];
			if (i < 2 && type == 0) { dateStr += "/";}
			else if (type == 2) { dateStr += " "; }
		} 
		
		return dateStr;
	}
	
	public void hover() { hover = true; }
	public void off() { hover = false; }
	public boolean isHovered() { return hover; }
	public void done() { completed = true; }
	public boolean isCompleted() { return completed; }
}