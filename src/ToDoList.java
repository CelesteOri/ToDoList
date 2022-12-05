/**
 * AUTOSORT
 * SAVE STATES
 * CONVERT TO TXT / TXT TO TODOLIST
 * **/

import java.util.ArrayList;

public class ToDoList {
	private ArrayList<Task> todo;
	
	private String name = "test";
	
	public ToDoList() {
		todo = new ArrayList<Task>();
	}
	
	public void add(Task newTask) {
		int date = Integer.parseInt(newTask.getDate(true)); 
		int i = 0;
		if (todo.size() > 0) {
			while (i < todo.size()) {
				if (date > Integer.parseInt(todo.get(i).getDate(true))) {
					i += 1;
				} else { break; }
			}
			
		}
		if (i == todo.size()) { todo.add(newTask); } 
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
	
	public void sort() { // Radix sort? Updates after each addition / removal
				
		// sort year last
		// sort month second
		// sort day first
	}
	
	public int size() { return todo.size(); }
	
	
}