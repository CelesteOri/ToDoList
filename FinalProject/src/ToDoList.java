import java.util.ArrayList;

public class ToDoList {
	private ArrayList<Task> todo;
	
	public ToDoList() {
		todo = new ArrayList<Task>();
	}
	
	public void add(Task newTask) {
		todo.add(newTask);
	}
	
	public Task remove(Task done) {
		boolean del = todo.remove(done);
		
		if (del) { return done; }
		return null;
	}
	
	public void sort() { // Radix sort?
		Task temp[] = new Task[todo.size()];
		
		for (int i = 0; i < todo.size(); i++) {
			
		}
		
		// sort year last
		// sort month second
		// sort day first
	}
	
	public int size() { return todo.size(); }
	
	public Task get(int i) { return todo.get(i); }
}
