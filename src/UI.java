
import static org.eclipse.swt.events.SelectionListener.widgetSelectedAdapter;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

/**
 * PAST DUE DATE -> TURN TITLE / DATE RED
 * COMPLETED SECTION
 * TEMPLATES
 * 
 * FILTER / TAGGING SYSTEM
 * 
 * SAVE / LOAD SYSTEM AND RELATED PROMPTS
 * **/


/**
 * THINGS TO ADJUST IF CANVASPAINTLISTENER ITEM POSITIONS/SIZES ARE MOVED:
 * - index from MouseListener and MouseMoveListener
 * 		- related if statements
 * **/


public class UI {
	static final int COORDS[] = {20, 50};
	static int offset = 40;
	
	public static void main(String[] args) {
		Display screen = new Display();
		
		Tab tabs = new Tab();
		
		// Testing purpose
		ToDoList todo = new ToDoList();
		ToDoList done = new ToDoList();
		
		Shell shell = new Shell(screen); 
		shell.setSize(1000, 600); shell.setLayout( new GridLayout() );
		
		Composite upperComp = new Composite(shell, SWT.NO_FOCUS);
		Canvas canvas = new Canvas(upperComp, SWT.NONE);
	 	canvas.setSize(1000, 800); 
	 	int select[] = {0};
	 	MenuBar menu = new MenuBar(shell, canvas, screen, tabs, select);
	 	
	 	
		//---- our Widgets start here 
	    
	    
	    //---- button for adding a task-------------------------------------------------------------------------------------------
	    Button addTask = new Button(shell, SWT.PUSH);
	    addTask.setText("Add Task"); 	 
	    //addTask.addSelectionListener(new selectListen());
	    addTask.addMouseListener(new mouseListen());
	    addTask.addSelectionListener(widgetSelectedAdapter(e -> {
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
		    		for (int i = 0; i < todo.getTags().size(); i++) {
		    			TableItem item = new TableItem(table, SWT.NONE);
		    			item.setText(todo.getTags().get(i).getTitle());
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
				System.out.println ("Title: " + text.getText ());
				System.out.println ("Description: " + t.getText ());
				// add task to list
				int monthInt = Integer.parseInt(month.getText());
				int dayInt = Integer.parseInt(day.getText());
				int yearInt = Integer.parseInt(year.getText());
				Tag newTag = new Tag(tag.getText());
				Task newTask = new Task(text.getText(), t.getText(), new int[]{monthInt,dayInt,yearInt}, newTag);
				newTask.setTag(newTag);
				todo.add(newTask);
				dialog.close ();
			}));

			dialog.setDefaultButton (ok);
			dialog.pack ();
			dialog.open ();
	    }));
		// -----------------------------------------------------------------------------------------------------------------------
	    
	    //---- button for adding a tag-----------------------------------------------------------------------------------------
	    Button addTag = new Button(shell, SWT.PUSH);
	    addTag.setText("Add Tag"); 	    
	    addTag.addSelectionListener(new selectListen(){
	    	public void widgetSelected(SelectionEvent event) {
	    		System.out.println("Adding a tag");
	    		InputDialog tagDialog = new InputDialog(shell);
	    		tagDialog.setMessage("please enter a tag:");
	    		tagDialog.setInput("type");
	    		String tag = tagDialog.open();
	    		System.out.println("tag entered = " + tag);
	    		if (tag != null) {
	    			todo.addTag(tag);
	    		}
	    		
	    	}
	    	public void widgetDefaultSelected(SelectionEvent event) {
	    		
	    	}
	    });
	    addTag.addMouseListener(new mouseListen());
		// -----------------------------------------------------------------------------------------------------------------------
	    
	    //---- button for Delete tag----------------------------------------------------------------------------------------------
	    Button deleteFilter = new Button(shell, SWT.PUSH);
	    deleteFilter.setText("Delete Tag"); 	    
	    deleteFilter.addSelectionListener(new selectListen());
	    deleteFilter.addMouseListener(new mouseListen());
		// -----------------------------------------------------------------------------------------------------------------------
	    
	    //---- button for applying a filter---------------------------------------------------------------------------------------
	    Button applyFilter = new Button(shell, SWT.PUSH);
	    applyFilter.setText("Apply Filter"); 	  
	    //ArrayList<String> filtersToApply = new ArrayList<String>();
	    applyFilter.addSelectionListener(new selectListen() {
	    	public void widgetSelected(SelectionEvent event) {
	    		System.out.println("Applying a filter");
	    		Shell tableShell = new Shell (shell);
				tableShell.setSize(300, 300);
	    		Table table = new Table(tableShell, SWT.CHECK | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
	    		for (int i = 0; i < todo.getTags().size(); i++) {
	    			TableItem item = new TableItem(table, SWT.NONE);
	    			item.setText(todo.getTags().get(i).getTitle());
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
	    	public void widgetDefaultSelected(SelectionEvent event) {
	    		
	    	}
	    	
	    });
	   
	    applyFilter.addMouseListener(new mouseListen());
		// -----------------------------------------------------------------------------------------------------------------------


	    //---- button for clear filter----------------------------------------------------------------------------------------------
	    Button clearFilter = new Button(shell, SWT.PUSH);
	    clearFilter.setText("Clear Filter"); 	    
	    clearFilter.addSelectionListener(new selectListen() {
	    	public void widgetSelected(SelectionEvent event) {
	    		for (int i = 0; i < todo.size(); i++) {
	    			Task t = todo.get(i);
	    			t.setVisibility(true);
	    		}
	    		
	    	}
	    	public void widgetDefaultSelected(SelectionEvent event) {
	    		
	    	}
	    });
	    clearFilter.addMouseListener(new mouseListen());
		// -----------------------------------------------------------------------------------------------------------------------

	 	// Canvas setup
	 	
	 	//CanvasPaintListener pl = new CanvasPaintListener(canvas, todo, screen);
	 	int coord[] = {COORDS[0], COORDS[1]};
	 	
	 	
	 	canvas.addPaintListener(
	 			new CanvasPaintListener(canvas, tabs, screen, coord, select));
	 	
	 	canvas.addMouseListener(new MouseListener() {
	 		public void mouseDoubleClick(MouseEvent event) {}
	 		public void mouseDown(MouseEvent event) {
	 			int x = event.x; int y = event.y;
	 			int index = (y - COORDS[1] - offset)/100; 
	 			int tab = x/100;
	 			if (x >= 100*tab && x < 100*tab+100
	 					&& y >= 0 && y <= 30 && tab < tabs.size()) {
	 				select[0] = tab;
	 				canvas.redraw();
	 			}
	 			
				
	 			if (tabs.size() > 0) {
	 				ToDoList todo = tabs.get(select[0])[0];
					ToDoList done = tabs.get(select[0])[1];
		 			if (x >= COORDS[0] + 10 && x <= COORDS[0] + 50 
		                     && y >= (100*index) + COORDS[1] + 20 + offset
		                     && y <= (100*index) + COORDS[1] + 60 + offset
		                     && index < todo.size() && index >= 0) {
		                         todo.get(index).off(); 
		                         Task completed = todo.remove(todo.get(index));
		                         if (completed != null) { 
		                             completed.done();
		                             done.add(completed); 
		                         }
		                         canvas.redraw();
		            }   
	 			}
	 		}
	 		public void mouseUp(MouseEvent e) {}
	 	});
	 	canvas.addMouseMoveListener(new MouseMoveListener() {
			public void mouseMove(MouseEvent event) {
				int x = event.x; int y = event.y;
				int index = (y - COORDS[1] - offset)/100;
				boolean change = false;
				
				int temp = -1;
				
				if (tabs.size() > 0) {
					ToDoList todo = tabs.get(select[0])[0];
					for (int i = 0; i < todo.size(); i++) {
						if (todo.get(i).isHovered()) {
							todo.get(i).off();
							change = true;
							temp = i;
						}
					}
					
					if (x >= COORDS[0] && x <= COORDS[0] + 600
	                        && y >= (100*index) + COORDS[1] + offset
	                        && y <= (100*index) + COORDS[1] + 80 + offset
	                        && index < todo.size() && index >= 0) {
	                   todo.get(index).hover();
	                   if (temp == index) { change = false;}
	                   else { change = true; }
	                } 
					
					if (change == true) { canvas.redraw(); }
				}
			}
		});

	 	shell.pack(); 
	 	shell.open();
		while( !shell.isDisposed()) {
			if(!screen.readAndDispatch()) { screen.sleep(); }
		}
		screen.dispose();
	}
	
}

 

// selectListener class ----------------------------------------------------------------------------------------------------------------
class selectListen implements SelectionListener 
{

	public void widgetSelected(SelectionEvent event) 
	{
		//System.out.println("selection event");
		//Button.text.setText("Selection!");
	}
	public void widgetDefaultSelected(SelectionEvent event)
	{                    
	}  
}

//mouseListen class ----------------------------------------------------------------------------------------------------------------
class mouseListen implements MouseListener 
{

	public void mouseDoubleClick(MouseEvent e)
	{
		System.out.println("Double click.");
	}
	public void mouseDown(MouseEvent e)
	{
		System.out.println("Clicked on addTask");
	}
	public void mouseUp(MouseEvent e)
	{
		//System.out.println("Click - up.");
	}
}

