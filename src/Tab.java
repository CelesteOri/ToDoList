/* *****************************************************************************
* AUTHOR: Honor Jang
* FILE: Tab.java
* ASSIGNMENT: A4 - To-Do List
* COURSE: CSc 335; Fall 2022
* 
* PURPOSE: 
* 	A object containing To-Do Lists, allowing multitabbing. Saves tags to use
* 	throughout the program. Assigns names to the lists.
* 
* USAGE: 
* 	Meant to handle multiple to-do lists. 
*/
import java.util.ArrayList;

public class Tab {
	private ArrayList<ToDoList[]> tabs;
	private ArrayList<String> names;
	private ArrayList<Tag> tags;
	
	/**
	 * A public constructor that initializes a Tab object for
	 * use in the program (notably UI).
	 * 
	 * @param none, just call
	 * 
	 * @return none; creates Tab object with three default tags
	 */
	public Tab() {
		tabs = new ArrayList<ToDoList[]>();
		names = new ArrayList<String>();
		tags = new ArrayList<Tag>();
		
		tags.add(new Tag("School"));
		tags.add(new Tag("Work"));
		tags.add(new Tag("Home"));
	}
	
	/**
	 * A public method that adds a new To-Do list (and corresponding
	 * completed list) and a name to the Tab.
	 * 
	 * @param name, a String to be the name of the To-Do List
	 * 
	 * @return none; adds to both tabs and names (To-Do List and String)
	 */
	public void add(String name) {
		ToDoList[] temp = {new ToDoList(), new ToDoList()};
		tabs.add(temp);
		names.add(name);
	}
	
	/**
	 * A public method that gets the pair of To-Do lists (the active
	 * and completed ones) at a given index.
	 * 
	 * @param index, the int representing the index of the lists
	 * 
	 * @return the active / completed pair at that index
	 */
	public ToDoList[] get(int index) {
		return tabs.get(index);
	}
	
	/**
	 * A public method that closes / deletes the To-Do list at a given index.
	 * 
	 * @param index, the int representing the index of the lists
	 * 
	 * @return none; closes the list at the given index by removing it from
	 * 		the tabs
	 */
	public void close(int index) {
		tabs.remove(index);
		names.remove(index);
	}
	
	/**
	 * A public method that gets the size of the tabs.
	 * 
	 * @param none; just call
	 * 
	 * @return the number of lists
	 */
	public int size() {
		return tabs.size();
	}
	
	/**
	 * A public method that gets the name of the list at a given
	 * index.
	 * 
	 * @param index, the int representing the index of the lists
	 * 
	 * @return the name (String) of the chosen To-Do list
	 */
	public String getName(int index) {
		if (index < names.size()) { return names.get(index); }
		return "";
	}
	
	/**
	 * A public method that does a similar thing to getName(),
	 * but formats it for viewing without going out of bounds.
	 * 
	 * @param index, the int representing the index of the lists
	 * 
	 * @return the formatted name (String) for that given pair
	 */
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
	
	/**
	 * A public method that adds a Tag to the Tag list from
	 * a String.
	 * 
	 * @param tag, the String to convert to a Tag
	 * 
	 * @return none; adds a Tag
	 */
	public void addTag(String tag) {
		if (!hasTag(tag) && tag.length() > 0) {
			Tag newTag = new Tag(tag);
			tags.add(newTag);
		}
	}
	
	/**
	 * A public method that adds a Tag to the Tag list from
	 * a Tag.
	 * 
	 * @param tag, a Tag
	 * 
	 * @return none; adds a Tag
	 */
	public void addTag(Tag tag) {
		if (!hasTag(tag) && tag != null) {
			tags.add(tag);
		}
	}
	
	/**
	 * A public method that gets all Tags registered in the
	 * program.
	 * 
	 * @param none; just call
	 * 
	 * @return tags, an ArrayList of Tags
	 */
	public ArrayList<Tag> getTags(){
		return this.tags;
	}
	
	/**
	 * A public method that checks if the tag already is registered.
	 * 
	 * @param tag, the String to search for (Tag name)
	 * 
	 * @return true, if the String is in a Tag; false otherwise
	 */
	public boolean hasTag(String tag) {
		for (int i = 0; i < tags.size(); i++) {
			if (tags.get(i).getTitle().equals(tag)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * A public method that checks if the tag already is registered.
	 * 
	 * @param tag, the Tag to search for
	 * 
	 * @return true, if present; false otherwise
	 */
	public boolean hasTag(Tag tag) {
		for (int i = 0; i < tags.size(); i++) {
			if (tags.get(i).getTitle().equals(tag.getTitle())) {
				return true;
			}
		}
		return false;
	}
		
	/**
	 * A public method that checks if there is a list with a given
	 * name already.
	 * 
	 * @param name, a String to check for
	 * 
	 * @return true, if the name is already in the list; false otherwise
	 */
	public boolean contains(String name) {
		return names.contains(name);
	}
}
