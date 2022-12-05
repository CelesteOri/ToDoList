import java.util.ArrayList;

public class Tab {
	private ArrayList<ToDoList[]> tabs;
	private ArrayList<String> names;
	
	public Tab() {
		tabs = new ArrayList<ToDoList[]>();
		names = new ArrayList<String>();
	}
	
	public void add() {
		ToDoList[] temp = {new ToDoList(), new ToDoList()};
		tabs.add(temp);
		names.add("test");
	}
	
	public ToDoList[] get(int index) {
		return tabs.get(index);
	}
	
	public void close(int index) {
		tabs.remove(index);
	}
	
	public int size() {
		return tabs.size();
	}
	
	public String getName(int index) {
		if (index < names.size()) { return names.get(index); }
		return "";
	}
}
