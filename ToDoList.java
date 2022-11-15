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
	
	public void switchPriority(int a, int b) {
		Task temp = todo.get(a);
		todo.set(a, todo.get(b));
		todo.set(b, temp);
	}
}
