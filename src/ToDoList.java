/**
/*
 * FILE: ToDoList.java
 * AUTHOR(S): Honor Jang, Joanna Zabasajja, Michelle Uddin, Carlos Julian Garcia
 * DATE: Dec. 6 2022
 * PROJECT: ToDo List
 * DESCRIPTION:  This is the ToDoList class, this one is used to display the lists themselves and how they work. 
 * Multiple lists can be added through the UI and named accordingly to what the user wants. 
 * Lists can be saved, loaded and closed through the file option.
 * Tasks inside the lists can be classified as currently on going or completed, in the former case they will
 * appear green with their name and their due date, while the ladder will be displayed separately grayed out.
 *     
 */

import java.util.ArrayList;

public class ToDoList {
	private ArrayList<Task> todo;
	private boolean hovered = false;
	
	public ToDoList() {
		todo = new ArrayList<Task>();
	}
	
	/*
     * Adding tasks section
     */
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
	
	/*
     * Removing tasks from lists
     */
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
	//hover display over tasks
	public void hover(boolean toggle) { hovered = toggle; }
	public boolean isHovered() { return hovered; }
}