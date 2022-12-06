import java.util.ArrayList;

public class Tab {
	private ArrayList<ToDoList[]> tabs;
	private ArrayList<String> names;
	private ArrayList<Tag> tags;
	
	public Tab() {
		tabs = new ArrayList<ToDoList[]>();
		names = new ArrayList<String>();
		tags = new ArrayList<Tag>();
		
		tags.add(new Tag("School"));
		tags.add(new Tag("Work"));
		tags.add(new Tag("Home"));
	}
	
	public void add(String name) {
		ToDoList[] temp = {new ToDoList(), new ToDoList()};
		tabs.add(temp);
		names.add(name);
	}
	
	public ToDoList[] get(int index) {
		return tabs.get(index);
	}
	
	public void close(int index) {
		tabs.remove(index);
		names.remove(index);
	}
	
	public int size() {
		return tabs.size();
	}
	
	public String getName(int index) {
		if (index < names.size()) { return names.get(index); }
		return "";
	}
	
	public String displayName(int index) {
		String name = "";
		if (index < names.size()) {
			for (int i = 0; i < names.get(index).length() && i < 7; i++) { 
				name += "" + names.get(index).charAt(i);
			} 
			if (name.length() != names.get(index).length()) {
				name += "...";
			}
		}
		return name;
	}
	
	
	public void addTag(String tag) {
		if (!hasTag(tag) && tag.length() > 0) {
			Tag newTag = new Tag(tag);
			tags.add(newTag);
		}
	}
	
	public void addTag(Tag tag) {
		if (!hasTag(tag) && tag != null) {
			tags.add(tag);
		}
	}
	
	public ArrayList<Tag> getTags(){
		return this.tags;
	}
	public boolean hasTag(String tag) {
		for (int i = 0; i < tags.size(); i++) {
			if (tags.get(i).getTitle().equals(tag)) {
				return true;
			}
		}
		return false;
	}
	public boolean hasTag(Tag tag) {
		for (int i = 0; i < tags.size(); i++) {
			if (tags.get(i).getTitle().equals(tag.getTitle())) {
				return true;
			}
		}
		return false;
	}
		
	public boolean contains(String name) {
		return names.contains(name);
	}
}
