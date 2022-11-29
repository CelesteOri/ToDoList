/**
 * AUTOSORT
 * SAVE STATES
 * CONVERT TO TXT / TXT TO TODOLIST
 * **/

import java.util.ArrayList;

public class ToDoList {
	private ArrayList<Task> todo;
	private ArrayList<Tag> tags;
	
	public ToDoList() {
		todo = new ArrayList<Task>();
		tags = new ArrayList<Tag>();
	}
	
	public void add(Task newTask) {
		todo.add(newTask);
	}
	
	public void addTag(String tag) {
		Tag newTag = new Tag(tag);
		tags.add(newTag);
	}
		
	public Task remove(Task done) {
		boolean del = todo.remove(done);
		
		if (del) { return done; }
		return null;
	}
	
	public Task remove(int index) {
		Task done = null;
	
		if (index < todo.size()) {
			done = todo.get(index);
			todo.remove(done);
		}
		
		return done;
	}
	
	public void sort() { // Radix sort? Updates after each addition / removal
				
		// sort year last
		// sort month second
		// sort day first
	}
	
	public int size() { return todo.size(); }
	
	public Task get(int i) { return todo.get(i); }
	
	public ArrayList<Tag> getTags(){
		return this.tags;
	}
}
