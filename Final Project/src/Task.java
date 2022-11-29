public class Task {
	private int date[] = {0, 0, 0}; // month / day / year
	private String title;
	private String description;
	private Tag tag;
	
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
		this.tag = null;
	}
	
	public boolean isValid() { return valid; }
	
	public void setTitle(String title) { this.title = title; }
	public String getTitle() { return title; }
	
	public void setDesc(String des) { this.description = des; }
	public String getDesc() { return description; }
	
	public void setTag(Tag tag) { this.tag = tag; }
	public Tag getTag() { return tag; }
	
	public String getDate() {
		String dateStr = "";
		for (int i = 0; i < 3; i++) {
			if (date[i] < 10) { dateStr += "0"; }
			dateStr += "" + date[i];
			if (i < 2) { dateStr += "/";}
		}
		
		return dateStr;
	}
	
	public int[] getRawDate() { return date; }
	
	public void hover() { hover = true; }
	public void off() { hover = false; }
	public boolean isHovered() { return hover; }
	public void done() { completed = true; }
	public boolean isCompleted() { return completed; }
}
