/**
 * AUTOSORT
 * SAVE STATES
 * CONVERT TO TXT / TXT TO TODOLIST
 * **/

import java.util.ArrayList;

public class ToDoList {
	private ArrayList<Task> todo;
	private boolean hovered = false;
	
	public ToDoList() {
		todo = new ArrayList<Task>();
	}
	
	public void add(Task newTask) {
		int date = Integer.parseInt(newTask.getDate(1)); 
		int i = 0;
		if (todo.size() > 0) {
			while (i < todo.size()) {
				Task t = todo.get(i);
				if (date > Integer.parseInt(t.getDate(1))
						|| (date == Integer.parseInt(t.getDate(1))
						&& newTask.getTitle().compareTo(t.getTitle()) == -1)) {
					i += 1;
				} else { break; }
			}
			
		}
		if (i == todo.size() || newTask.isCompleted()) { todo.add(newTask); } 
		else { todo.add(i, newTask); }
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
	
	public Task get(int index) {
		if (index < todo.size()) { return todo.get(index); }
		return null;
	}
	
	public int size() { return todo.size(); }	
	
	public void hover(boolean toggle) { hovered = toggle; }
	public boolean isHovered() { return hovered; }
}