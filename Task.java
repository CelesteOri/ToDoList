public class Task {
	private boolean valid = true;
	private int date[] = {0, 0, 0}; // like binary sort?
	private String title;
	private String description;
	
	public Task(String title, String description, int date[]) {
		if (date.length != 3) { valid = false; return; }
		this.title = title;
		this.description = description;
		this.date = date;
	}
	
	public boolean isValid() { return valid; }
	
	public void setTitle(String title) { this.title = title; }
	public String getTitle() { return title; }
	
	public void setDesc(String des) { this.description = des; }
	public String getDesc() { return description; }
	
	public String getDate() {
		return date[0] + "/" + date[1] + "/" + date[2];
	}
}
