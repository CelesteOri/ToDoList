import static org.eclipse.swt.events.SelectionListener.widgetSelectedAdapter;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
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
	private static MenuItem fileExit, fileClose, fileNew;
	
	// task / tag widgets
	private static MenuItem editAdd, editNewTag, editDelTag;
	
	// filter widgets
	private static MenuItem filterApply, filterClear;
	
	
	public MenuBar(Shell shell, Canvas canvas, Display display, Tab tabs, int select[]) {
		this.shell = shell;
		this.display = display;
		this.canvas = canvas;
		this.tabs = tabs;
		this.select = select;
		menuBar = new Menu(shell, SWT.BAR);
		initializeMenu();
		shell.setMenuBar(menuBar);
	}
	
	public Menu getMenuBar() {
		return menuBar;
	}
	
	private static void initializeMenu() {
		// initializes the file portion of the menu
		initFileSubmenu();
		
		initEditSubmenu();		
		
		initFilterSubmenu();	
	}
	
	private static void shutDown() {
		shell.close();
		display.dispose();
	}
	
	private static void initFileSubmenu() {
		fileMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
		fileMenuHeader.setText("File");
		fileMenu = new Menu(shell, SWT.DROP_DOWN);
		fileMenuHeader.setMenu(fileMenu);

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
				
				
				Label label = new Label (dialog, SWT.NONE);
				Text text = new Text (dialog, SWT.SINGLE | SWT.BORDER);
				GridData gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
				gridData.horizontalSpan = 2;
				gridData.grabExcessHorizontalSpace = true;
				text.setLayoutData(gridData);
				label.setText ("Title:");
				
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
						if (!tabs.contains(text.getText())) {
							tabs.add(text.getText());
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
	    
	    fileClose = new MenuItem(fileMenu, SWT.PUSH);
	    fileClose.setText("Close current to-do list");
	    fileClose.addSelectionListener(new SelectionListener() {
	    	public void widgetSelected(SelectionEvent event) {
	    		if (tabs.size() > 0) {
		    		tabs.close(select[0]);
		    		if (select[0] >= tabs.size()) { select[0] = tabs.size() - 1; }
	
		    		canvas.redraw();
	    		} else { System.out.println("There's nothing to close!");}
	    	}
	    	public void widgetDefaultSelected(SelectionEvent event) {
	    	}	    		
	    });
	    
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
	
	private static void initEditSubmenu() {
		editMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
		editMenuHeader.setText("Edit");
		editMenu = new Menu(shell, SWT.DROP_DOWN);
		editMenuHeader.setMenu(editMenu);

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
				
				
				Label label = new Label (dialog, SWT.NONE);
				Text text = new Text (dialog, SWT.SINGLE | SWT.BORDER);
				GridData gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
				gridData.horizontalSpan = 2;
				gridData.grabExcessHorizontalSpace = true;
				text.setLayoutData(gridData);
				label.setText ("Title:");
				
				Label l = new Label (dialog, SWT.NONE);
				final Text t = new Text (dialog, SWT.SINGLE | SWT.BORDER);
				GridData gridData2 = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
				gridData2.horizontalAlignment = GridData.FILL;
				gridData2.horizontalSpan = 2;
				t.setLayoutData(gridData2);
				l.setText ("Description:");
				
				Label l3 = new Label (dialog, SWT.NONE);
				final Text month = new Text (dialog, SWT.SINGLE | SWT.BORDER);
				GridData gridData3 = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
				gridData3.horizontalAlignment = GridData.FILL;
				gridData3.horizontalSpan = 2;
				month.setLayoutData(gridData3);
				l3.setText ("Month:");
				
				Label l4 = new Label (dialog, SWT.NONE);
				final Text day = new Text (dialog, SWT.SINGLE | SWT.BORDER);
				GridData gridData4 = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
				gridData4.horizontalAlignment = GridData.FILL;
				gridData4.horizontalSpan = 2;
				day.setLayoutData(gridData4);
				l4.setText ("Day:");
				
				Label l5 = new Label (dialog, SWT.NONE);
				final Text year = new Text (dialog, SWT.SINGLE | SWT.BORDER);
				GridData gridData5 = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
				gridData5.horizontalAlignment = GridData.FILL;
				gridData5.horizontalSpan = 2;
				year.setLayoutData(gridData5);
				l5.setText ("Year:");
				
				Label l6 = new Label (dialog, SWT.NONE);
				final Text tag = new Text (dialog, SWT.SINGLE | SWT.BORDER);
				GridData gridData6 = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
				gridData6.horizontalAlignment = GridData.FILL;
				gridData6.horizontalSpan = 2;
				tag.setLayoutData(gridData5);
				l6.setText ("Tag:");
				
				
				GridData gridData7 = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
				gridData7.horizontalSpan = 4;
				Button chooseTag = new Button(dialog, SWT.PUSH);
				chooseTag.setText("Choose tag"); 	  
			    //String tag = new String();
			    chooseTag.addSelectionListener(new selectListen() {
			    	public void widgetSelected(SelectionEvent event) {
			    		System.out.println("Choosing a tag");
			    		Shell tableShell = new Shell (shell, SWT.TITLE | SWT.APPLICATION_MODAL
			    				| SWT.CLOSE | SWT.MAX);
						tableShell.setSize(300, 300);
			    		Table table = new Table(tableShell, SWT.CHECK | SWT.BORDER
			    				| SWT.V_SCROLL | SWT.H_SCROLL);
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
						Tag newTag = new Tag(tag.getText());
						Task newTask = new Task(text.getText(), t.getText(),
								new int[]{monthInt,dayInt,yearInt}, newTag);
						newTask.setTag(newTag);
						todo.add(newTask);
						dialog.close ();
					} catch (Exception exception) {
						System.out.println ("Invalid entry");
					}
				}));
	
				dialog.setDefaultButton (ok);
				dialog.pack ();
				dialog.open ();
	    }}));
	    
	    editNewTag = new MenuItem(editMenu, SWT.PUSH);
	    editNewTag.setText("Add Tag");
	    editNewTag.addSelectionListener(widgetSelectedAdapter(e -> {
    		System.out.println("Adding a tag");
    		InputDialog tagDialog = new InputDialog(shell);
    		tagDialog.setMessage("please enter a tag:");
    		tagDialog.setInput("type");
    		String tag = tagDialog.open();
    		System.out.println("tag entered = " + tag);
    		if (tag != null) {
    			tabs.addTag(tag);
    		}
	    }));
	    
	    editDelTag = new MenuItem(editMenu, SWT.PUSH);
	    editDelTag.setText("Delete Tag");
	    editDelTag.addSelectionListener(widgetSelectedAdapter(e -> {
    		// idk
	    }));
	    
	    
	}

	private static void initFilterSubmenu() {
		filterMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
		filterMenuHeader.setText("Filter");
		filterMenu = new Menu(shell, SWT.DROP_DOWN);
		filterMenuHeader.setMenu(filterMenu);
		
		filterApply = new MenuItem(filterMenu, SWT.PUSH);
		filterApply.setText("Apply Filter");
		filterApply.addSelectionListener(new selectListen() {
	    	public void widgetSelected(SelectionEvent event) {
	    		if (tabs.size() > 0 && tabs.getTags().size() > 0) {
		    		ToDoList todo = tabs.get(select[0])[0];
		    		System.out.println("Applying a filter");
		    		Shell tableShell = new Shell (shell);
					tableShell.setSize(300, 300);
		    		Table table = new Table(tableShell, SWT.CHECK | SWT.BORDER 
		    				| SWT.V_SCROLL | SWT.H_SCROLL);
		    		for (int i = 0; i < tabs.getTags().size(); i++) {
		    			TableItem item = new TableItem(table, SWT.NONE);
		    			item.setText(tabs.getTags().get(i).getTitle());
		    		}
		    		table.setSize(300, 300);
		    		table.addSelectionListener(new selectListen() {
		    			public void widgetSelected(SelectionEvent event) {
		    				String string = event.detail == SWT.CHECK ? "Checked" : "Selected";
		    				System.out.println(event.item + " " + string);
		    				String selected = event.item.toString();
		    				selected = selected.replace("TableItem {", "");
		    				selected = selected.replace("}", "");
		    				System.out.println(selected);
		    				//filtersToApply.add(selected);
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