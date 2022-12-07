/* *****************************************************************************
* AUTHOR: Michelle Uddin, Joanna Zabasajja, Honor Jang
* FILE: MenuBar.java
* ASSIGNMENT: A4 - To-Do List
* COURSE: CSc 335; Fall 2022
* 
* PURPOSE: 
* 	Creating a menu with dropdowns that allows the user to select certain
* 	actions, such as creating a new To-Do List, saving it, loading up a saved
* 	list, add tasks, and filter. Called by UI.java.
* 
* USAGE: 
* 	A menubar not unlike that used in Microsoft Word or something similar.
* 	Allows the user to interact with the list in a variety of ways.
*/
import static org.eclipse.swt.events.SelectionListener.widgetSelectedAdapter;

import java.time.LocalDate;
import java.io.*;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

public class MenuBar {

	private static Shell shell;
	private static Canvas canvas;
	private static Display display;
	private static Tab tabs;
	private static int select[];
	
	// menus
	private static Menu menuBar, fileMenu, editMenu, filterMenu;
	
	// menu icons
	private static MenuItem fileMenuHeader, editMenuHeader, filterMenuHeader;
	
	// file menu widgets
	private static MenuItem fileExit, fileClose, fileNew, fileSave, fileLoad;
	
	// task / tag widgets
	private static MenuItem editAdd, editNewTag;
	
	// filter widgets
	private static MenuItem filterApply, filterClear;
	
	/**
	 * A private method that opens a file and loads it into the program.
	 * The selected file must have the .todo extension or otherwise be
	 * formatted in the correct form, which is as so:
	 * 
	 * NAME OF LIST
	 * task name
	 * description
	 * MM DD YY
	 * tags (if any, a space if none)
	 * complete / incomplete
	 * task name...
	 * 
	 * @param selected, a String representing the path to the file
	 * 
	 * @return none; loads in the list from the given file into the
	 * 		program
	 */
	private static void openFile(String selected) {
		File file = new File(selected);
	    try {
	    	BufferedReader br = new BufferedReader(new FileReader(file));
	    	String line = br.readLine();
	    	int count = 0;
	    	
	    	// Holding information
	    	String reset[] = {"", "", "", "\n"};
	    	String data[] = {"", "", "", "\n"};
	    	
			while (line != null) {
				// First line is the name, rest is task data
				if (count == 0) { tabs.add(line); select[0] = tabs.size()-1; }
				else if (count <= 4) { data[count-1] = line; }
				
				// Filled the data array, add to the To-Do list
				if (count == 5) {
					// Get date
					int date[] = {0, 0, 0}; String temp[] = data[2].split(" ");
					for (int i = 0; i < 3; i++) {
						date[i] = Integer.parseInt(temp[i]);
					}
					
					// Get tag
					String tagName = data[3];
					Tag tag = new Tag(tagName); tabs.addTag(tag);
					if (tagName.length() == 0) {tag = null;}
					
					// Make task
					Task task = new Task(data[0], data[1], date, tag);
					
					// Status (and where to put it)
					int list = 0;
					if (line.equals("complete")) { 
						list = 1; task.done();
					}
					tabs.get(select[0])[list].add(task);
					
					data = reset; count = 0; 
				}
				line = br.readLine(); count += 1;
				
			}
			br.close();
		} catch (IOException e) {
			System.out.println("File read error");
		}
	}
	
	/**
	 * A private method that saves a To-Do List to the current working
	 * directory (should be in the project folder) as a .todo file,
	 * or a text file of a specific format. Allows loading, but does not
	 * prompt the user (unlike opening files)
	 * 
	 * @param none; just call
	 * 
	 * @return none; saves the To-Do List to the computer
	 */
	private static void saveFile() {
		ToDoList[] contents = tabs.get(select[0]);
		String name = tabs.getName(select[0]) + ".todo";
		FileWriter file;
		try {
			file = new FileWriter(name);
			file.write(tabs.getName(select[0]) + "\n");
			for (int list = 0; list < 2; list++) {
				// Write down all the tasks
				for (int i = 0; i < contents[list].size(); i++) {
					Task task = contents[list].get(i);
					file.write(task.getTitle() + "\n");
					file.write(task.getDesc() + "\n");
					file.write(task.getDate(2) + "\n");
					
					// tag line
					if (task.getTag() == null) { file.write(" \n"); }
					else { file.write(task.getTag().getTitle() + "\n"); }
					
					// completion
					if (task.isCompleted()) { file.write("complete\n"); }
					else { file.write("incomplete\n"); }
				}
			}
		    file.close();
		    System.out.println("Saved!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Error saving");
		}
	}
	
	/**
	 * A public constructor that initializes and makes the MenuBar.
	 * Not a lot to say about that.
	 * 
	 * @param shell, the Shell the program is on
	 * @param canvas, the Canvas that is affected
	 * @param display, the Display of the program
	 * @param tabs, a Tab representing all the tabs of the program
	 * @param select, a 1-element array representing the selected list
	 * 
	 * @return none; creates the MenuBar
	 */
	public MenuBar(Shell shell, Canvas canvas, Display display,
			Tab tabs, int select[]) {
		this.shell = shell;
		this.display = display;
		this.canvas = canvas;
		this.tabs = tabs;
		this.select = select;
		menuBar = new Menu(shell, SWT.BAR);
		initializeMenu();
		shell.setMenuBar(menuBar);
	}
	
	/**
	 * A public method that gets the MenuBar's menu.
	 * 
	 * @param none; just call
	 * 
	 * @return menuBar, the Menu of the MenuBar
	 */
	public Menu getMenuBar() {
		return menuBar;
	}
	
	/**
	 * A private method that initializes the Menu; calls other initializers.
	 * 
	 * @param none; just call
	 * 
	 * @return none; initializes the menu bar
	 */
	private static void initializeMenu() {
		// initializes the file portion of the menu
		initFileSubmenu();
		// edit portion
		initEditSubmenu();		
		// filter
		initFilterSubmenu();	
	}
	
	/**
	 * A private method that shuts down the program.
	 * 
	 * @param none; just call
	 * 
	 * @return none; ends the program
	 */
	private static void shutDown() {
		shell.close();
		display.dispose();
	}
	
	/**
	 * A private method that handles adding fields to dialog boxes.
	 * 
	 * @param name, the String used to label the field
	 * @param dialog, the Shell this is drawn on
	 * 
	 * @return text, the Text from the field
	 */
	private static Text addField(String name, Shell dialog) {
		Label label = new Label (dialog, SWT.NONE);
		Text text = new Text (dialog, SWT.SINGLE | SWT.BORDER);
		GridData gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		gridData.horizontalSpan = 2;
		gridData.grabExcessHorizontalSpace = true;
		text.setLayoutData(gridData);
		label.setText(name);
		return text;
	}
	
	/**
	 * A private method that initializes the File submenu, which
	 * contains buttons to make a new To-Do List, save the current
	 * To-Do List, load a To-Do List from a file, close the current
	 * To-Do list, and close the program.
	 * 
	 * @param none; just call
	 * 
	 * @return none; initializes the file submenu
	 */
	private static void initFileSubmenu() {
		fileMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
		fileMenuHeader.setText("File");
		fileMenu = new Menu(shell, SWT.DROP_DOWN);
		fileMenuHeader.setMenu(fileMenu);

		// Make new file
	    fileNew = new MenuItem(fileMenu, SWT.PUSH);
	    fileNew.setText("Create new to-do list");
	    fileNew.addSelectionListener(widgetSelectedAdapter(e -> {
	    	if (tabs.size() <= 4) {
		    	Shell dialog = new Shell (shell, SWT.DIALOG_TRIM 
		    			| SWT.APPLICATION_MODAL);
				dialog.setMinimumSize(400, 100);
				dialog.setText("New To-Do List");
				GridLayout gridLayout = new GridLayout();
				gridLayout.numColumns = 3;
				dialog.setSize(400, 100);
				dialog.setLayout (gridLayout);
				
				Text text = addField("Title:", dialog);
				
				Button cancel = new Button (dialog, SWT.PUSH);
				cancel.setText ("Cancel");
				cancel.addSelectionListener (widgetSelectedAdapter(event -> {
					System.out.println("User cancelled dialog");
					dialog.close ();
				}));
				
				
				Button ok = new Button (dialog, SWT.PUSH);
				ok.setText ("OK");
				ok.addSelectionListener (widgetSelectedAdapter(event -> {
					try {
						// Adds a task so it's not empty
						if (!tabs.contains(text.getText())) {
							String todaysDate = LocalDate.now().toString();
							int date[] = {
									Integer.parseInt(todaysDate.substring(5,7)),
									Integer.parseInt(todaysDate.substring(8,10)),
									Integer.parseInt(todaysDate.substring(2,4))
							};
							
							Task task = new Task("Add tasks",
									"Add tasks to " + text.getText() +"!", date, null);
							
							tabs.add(text.getText());
							select[0] = tabs.size() - 1;
							
							tabs.get(select[0])[0].add(task);
							dialog.close();
							canvas.redraw();
						} else {
							System.out.println ("That list already exists!");
						}
					} catch (Exception exception) {
						System.out.println ("Invalid entry");
					}
				}));
	
				dialog.setDefaultButton(ok);
				dialog.pack();
				dialog.open();
	    	} else {
	    		System.out.println("Too many tabs for the display.");
	    	}
	    }));
	    
	    // Save file
	    fileSave = new MenuItem(fileMenu, SWT.PUSH);
	    fileSave.setText("Save current to-do list");
	    fileSave.addSelectionListener(new SelectionListener() {
	        public void widgetSelected(SelectionEvent event) {
	        	if (tabs.size() > 0) {
	        		saveFile();
	        	}
	        }

	        public void widgetDefaultSelected(SelectionEvent event) {
	        }
	    });
	    
	    // Load file
	    fileLoad = new MenuItem(fileMenu, SWT.PUSH);
	    fileLoad.setText("Load a saved to-do list");
	    fileLoad.addSelectionListener(new SelectionListener() {
	        public void widgetSelected(SelectionEvent event) {
	        	FileDialog fd = new FileDialog(shell, SWT.OPEN);
	        	fd.setText("Open");
	        	String[] filterExt = { "*.todo" };
	        	fd.setFilterExtensions(filterExt);
	        	String selected = fd.open();
	        	try {
	        		openFile(selected);
	        	} catch (Exception e) {
	        		System.out.println("File does not exist!");
	        	}
	        	canvas.redraw();
	        }

	        public void widgetDefaultSelected(SelectionEvent event) {
	        }
	    });
	    
	    // Close the current list
	    fileClose = new MenuItem(fileMenu, SWT.PUSH);
	    fileClose.setText("Close current to-do list");
	    fileClose.addSelectionListener(new SelectionListener() {
	        public void widgetSelected(SelectionEvent event) {
	        	if (tabs.size() > 0) {
	        		tabs.close(select[0]);
	    			select[0] = tabs.size() - 1;
	    			if (select[0] < 0) { select[0] = 0; }
	    			canvas.redraw();
	        	}
	        }

	        public void widgetDefaultSelected(SelectionEvent event) {
	        }
	    });
	    
	    // Exit the program
	    fileExit = new MenuItem(fileMenu, SWT.PUSH);
	    fileExit.setText("Exit");
	    fileExit.addSelectionListener(new SelectionListener() {
	    	public void widgetSelected(SelectionEvent event) {
	    		shutDown();
	    	}
	    	public void widgetDefaultSelected(SelectionEvent event) {
	    		shutDown();
	    	}
	    });
	}
	
	/**
	 * A private method that initializes the Edit submenu, which
	 * contains buttons to add new Tasks and Tags.
	 * 
	 * @param none; just call
	 * 
	 * @return none; initializes the edit submenu
	 */
	private static void initEditSubmenu() {
		editMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
		editMenuHeader.setText("Edit");
		editMenu = new Menu(shell, SWT.DROP_DOWN);
		editMenuHeader.setMenu(editMenu);

		// Add tasks to the current list
	    editAdd = new MenuItem(editMenu, SWT.PUSH);
	    editAdd.setText("Add Task");
	    editAdd.addSelectionListener(widgetSelectedAdapter(e -> {
	    	if (tabs.size() > 0) {
				ToDoList todo = tabs.get(select[0])[0];
		    	Shell dialog = new Shell (shell, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
				dialog.setMinimumSize(500, 300);
				dialog.setText("Add Task");
				GridLayout gridLayout = new GridLayout();
				gridLayout.numColumns = 3;
				dialog.setSize(500, 300);
				dialog.setLayout (gridLayout);
				
				Text text = addField("Title:", dialog);
				Text t = addField("Description:", dialog);
				Text month = addField("Month:", dialog);
				Text day = addField("Day:", dialog);
				Text year = addField("Year:", dialog);
				Text tag = addField("Tag:", dialog);
				
				GridData gridData7 = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
				gridData7.horizontalSpan = 4;
				Button chooseTag = new Button(dialog, SWT.PUSH);
				chooseTag.setText("Choose tag"); 	  
			    //String tag = new String();
			    chooseTag.addSelectionListener(new selectListen() {
			    	public void widgetSelected(SelectionEvent event) {
			    		System.out.println("Choosing a tag");
			    		Shell tableShell = new Shell (shell, SWT.TITLE | 
			    				SWT.APPLICATION_MODAL | SWT.CLOSE | SWT.MAX);
						tableShell.setSize(300, 300);
			    		Table table = new Table(tableShell, SWT.CHECK | 
			    				SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
			    		for (int i = 0; i < tabs.getTags().size(); i++) {
			    			TableItem item = new TableItem(table, SWT.NONE);
			    			item.setText(tabs.getTags().get(i).getTitle());
			    		}
			    		table.setSize(300, 300);
			    		table.addSelectionListener(new selectListen() {
			    			public void widgetSelected(SelectionEvent event) {
			    				String string = event.detail == SWT.CHECK ? "Checked" : "Selected";
			    				System.out.println(event.item + " " + string);
			    				System.out.println(event.item);
			    				String selected = event.item.toString();
			    				selected = selected.replace("TableItem {", "");
			    				selected = selected.replace("}", "");
			    				System.out.println(selected);
			    				tag.setText(selected);
			    				tableShell.close();
			    			}
			    			public void widgetDefaultSelected(SelectionEvent event) {
			    	    		
			    	    	}
			    	    });
			    		tableShell.open();
			    		
			    	}
			    	public void widgetDefaultSelected(SelectionEvent event) {
			    		
			    	}
			    });			
	
				
				Button cancel = new Button (dialog, SWT.PUSH);
				cancel.setText ("Cancel");
				cancel.addSelectionListener (widgetSelectedAdapter(event -> {
					System.out.println("User cancelled dialog");
					dialog.close ();
				}));
				
				
				Button ok = new Button (dialog, SWT.PUSH);
				ok.setText ("OK");
				ok.addSelectionListener (widgetSelectedAdapter(event -> {
					// add task to list
					try {
						int monthInt = Integer.parseInt(month.getText());
						int dayInt = Integer.parseInt(day.getText());
						int yearInt = Integer.parseInt(year.getText());
						Tag newTag = null;
						if (tag.getText().length() != 0) { 
							newTag = new Tag(tag.getText());
							tabs.addTag(newTag);
						}
						
						Task newTask = new Task(text.getText(), t.getText(),
								new int[]{monthInt,dayInt,yearInt}, newTag);
						newTask.setTag(newTag);
						todo.add(newTask);
						dialog.close ();
						canvas.redraw();
					} catch (Exception exception) {
						System.out.println ("Invalid entry due to " + exception);
					}
				}));
	
				dialog.setDefaultButton (ok);
				dialog.pack ();
				dialog.open ();
	    }}));
	    
	    // Add a tag
	    editNewTag = new MenuItem(editMenu, SWT.PUSH);
	    editNewTag.setText("Add Tag");
	    editNewTag.addSelectionListener(widgetSelectedAdapter(e -> {
    		System.out.println("Adding a tag");
    		InputDialog tagDialog = new InputDialog(shell);
    		tagDialog.setMessage("please enter a tag:");
    		tagDialog.setInput("type");
    		String tag = tagDialog.open();
    		if (tag != null) {
    			tabs.addTag(tag);
    		}
	    }));
	}

	/**
	 * A private method that initializes the Filter submenu, which
	 * contains buttons to trigger a filter (so that only the Tasks
	 * with the Tag are shown) or clear the filter.
	 * 
	 * @param none; just call
	 * 
	 * @return none; initializes the filter submenu
	 */
	private static void initFilterSubmenu() {
		filterMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
		filterMenuHeader.setText("Filter");
		filterMenu = new Menu(shell, SWT.DROP_DOWN);
		filterMenuHeader.setMenu(filterMenu);
		
		// Apply filter
		filterApply = new MenuItem(filterMenu, SWT.PUSH);
		filterApply.setText("Apply Filter");
		filterApply.addSelectionListener(new selectListen() {
	    	public void widgetSelected(SelectionEvent event) {
	    		if (tabs.size() > 0 && tabs.getTags().size() > 0) {
		    		ToDoList todo = tabs.get(select[0])[0];
		    		System.out.println("Applying a filter");
		    		Shell tableShell = new Shell (shell);
					tableShell.setSize(300, 300);
		    		Table table = new Table(tableShell, SWT.CHECK |
		    				SWT.BORDER  | SWT.V_SCROLL | SWT.H_SCROLL);
		    		for (int i = 0; i < tabs.getTags().size(); i++) {
		    			TableItem item = new TableItem(table, SWT.NONE);
		    			item.setText(tabs.getTags().get(i).getTitle());
		    		}
		    		// Make a table of the current tags, as to pick one
		    		table.setSize(300, 300);
		    		table.addSelectionListener(new selectListen() {
		    			public void widgetSelected(SelectionEvent event) {
		    				String string = event.detail == SWT.CHECK ? "Checked" : "Selected";
		    				System.out.println(event.item + " " + string);
		    				String selected = event.item.toString();
		    				selected = selected.replace("TableItem {", "");
		    				selected = selected.replace("}", "");
		    				System.out.println(selected);
		    				for (int i = 0; i < todo.size(); i++) {
		    					Task t = todo.get(i);
		    					if (t.getTag() != null) {
		    						System.out.println("title = " + t.getTag().getTitle());
		    						if (t.getTag().getTitle().equals(selected)) {
		    							System.out.print("equal");
			    						t.setVisibility(true);
			    					}
		    						else {
		    							t.setVisibility(false);
		    						}
		    					}
		    					else {
		    						t.setVisibility(false);
		    					}
		    				}
		    				tableShell.close();
		    				canvas.redraw();
		    			}
		    			public void widgetDefaultSelected(SelectionEvent event) {
		    	    		
		    	    	}
		    	    });
		    		tableShell.open();
	    		}
	    	}
	    	public void widgetDefaultSelected(SelectionEvent event) {
	    		
	    	}
	    	
	    });
		
		// Clear the filter
		filterClear = new MenuItem(filterMenu, SWT.PUSH);
		filterClear.setText("Clear Filter");
		filterClear.addSelectionListener(new selectListen() {
		    	public void widgetSelected(SelectionEvent event) {
		    		if (tabs.size() > 0) {
			    		ToDoList todo = tabs.get(select[0])[0];
			    		for (int i = 0; i < todo.size(); i++) {
			    			Task t = todo.get(i);
			    			t.setVisibility(true);
			    		}
		    		}
		    	}
		    	public void widgetDefaultSelected(SelectionEvent event) {
		    		
		    	}
		    });
	}
}