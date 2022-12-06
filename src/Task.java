
public class Task {
	private int date[] = {0, 0, 0}; // month / day / year
	private String title;
	private String description;
	private Tag tag;
	private boolean visible;
	
	private boolean valid = true;
	private boolean hover;
	private boolean completed;
	
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
	
	public void setTitle(String title) { this.title = title; }
	public String getTitle() { return title; }
	
	public void setDesc(String des) { this.description = des; }
	public String getDesc() { return description; }
	
	public void setTag(Tag tag) { this.tag = tag; }
	public Tag getTag() { return tag; }
	
	public void setVisibility(Boolean vis) { this.visible = vis; }
	public Boolean getVisibility() { return visible; }
	
	
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