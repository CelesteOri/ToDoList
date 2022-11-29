/**
 * PAST DUE DATE -> TURN TITLE / DATE RED
 * COMPLETED SECTION
 * TEMPLATES
 * 
 * FILTER / TAGGING SYSTEM
 * 
 * SAVE / LOAD SYSTEM AND RELATED PROMPTS
 * **/

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

/**
 * THINGS TO ADJUST IF CANVASPAINTLISTENER ITEM POSITIONS/SIZES ARE MOVED:
 * - index from MouseListener and MouseMoveListener
 * 		- related if statements
 * **/


public class UI {
	static final int COORDS[] = {20, 50};
	
	public static void main(String[] args) {
		
		Display screen = new Display();
		
		// Testing purpose
		ToDoList todo = new ToDoList();
		ToDoList done = new ToDoList();
		
		Task test = new Task("Title", "Description", new int[]{12, 30, 22}); 
		todo.add(test);
		Task test2 = new Task("Title2", "Description", new int[]{1, 6, 23}); 
		todo.add(test2);
		
		Shell shell = new Shell(screen); 
		shell.setSize(1000, 600); shell.setLayout( new GridLayout() );
		
		//---- our Widgets start here 
	    Composite upperComp = new Composite(shell, SWT.NO_FOCUS);
	    
	    //---- button for adding a task-------------------------------------------------------------------------------------------
	    Button addTask = new Button(shell, SWT.PUSH);
	    addTask.setText("Add Task"); 	    
	    addTask.addSelectionListener(new selectListen());
	    addTask.addMouseListener(new mouseListen());
		// -----------------------------------------------------------------------------------------------------------------------

	 	// Canvas setup
	 	Canvas canvas = new Canvas(upperComp, SWT.NONE);
	 	canvas.setSize(1000, 600); 
	 	//CanvasPaintListener pl = new CanvasPaintListener(canvas, todo, screen);
	 	int coord[] = {COORDS[0], COORDS[1]};
	 	canvas.addPaintListener(
	 			new CanvasPaintListener(canvas, todo, done, screen, coord));
	 	
	 	canvas.addMouseListener(new MouseListener() {
	 		public void mouseDoubleClick(MouseEvent event) {}
	 		public void mouseDown(MouseEvent event) {
	 			int x = event.x; int y = event.y;
	 			int index = (y - COORDS[1])/100; 
				
	 			if (x >= COORDS[0] + 10 && x <= COORDS[0] + 50 
	                     && y >= (100*index) + COORDS[1] + 20
	                     && y <= (100*index) + COORDS[1] + 60
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
	 		public void mouseUp(MouseEvent e) {}
	 	});
	 	canvas.addMouseMoveListener(new MouseMoveListener() {
			public void mouseMove(MouseEvent event) {
				int x = event.x; int y = event.y;
				int index = (y-COORDS[1])/100;
				boolean change = false;
				
				int temp = -1;
				
				for (int i = 0; i < todo.size(); i++) {
					if (todo.get(i).isHovered()) {
						todo.get(i).off();
						change = true;
						temp = i;
					}
				}
				
				if (x >= COORDS[0] && x <= COORDS[0] + 600
                        && y >= (100*index) + COORDS[1]
                        && y <= (100*index) + COORDS[1] + 80
                        && index < todo.size() && index >= 0) {
                   todo.get(index).hover();
                   if (temp == index) { change = false;}
                   else { change = true; }
               } 
				
				if (change == true) { canvas.redraw(); }
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

class CanvasPaintListener implements PaintListener {
	Canvas shell; ToDoList todo; ToDoList done; Display display;
    int x; int baseY;
    
    public CanvasPaintListener(Canvas sh, ToDoList todo, ToDoList done,
    		Display display, int coord[]) {
    	this.shell = sh; this.display = display;
    	this.todo = todo; this.done = done;
    	this.x = coord[0]; this.baseY = coord[1];
    }
    
    public void drawTask(PaintEvent e, int y, Task task) {
    	int baseColor = SWT.COLOR_DARK_GREEN;
    	int hoverColor = SWT.COLOR_GREEN;
    	
    	int color = baseColor;
    	if (task.isHovered()) { color = hoverColor; }
    	if (task.isCompleted()) { color = SWT.COLOR_DARK_GRAY;}
    	
		e.gc.setBackground(display.getSystemColor(color));
		e.gc.fillRoundRectangle(x, y, 600, 80, 10, 10);
		
		e.gc.setBackground(display.getSystemColor(SWT.COLOR_WHITE));
		e.gc.fillRoundRectangle(x + 10, y + 20, 40, 40, 25, 25);
		
		e.gc.setBackground(display.getSystemColor(color));
		e.gc.setForeground(display.getSystemColor(SWT.COLOR_WHITE));
		
		e.gc.setFont(new Font(display, "Comic Sans", 16, SWT.BOLD));
    	e.gc.drawString(task.getTitle(), x + 70, y+20);
    	
    	e.gc.setFont(new Font(display, "Comic Sans", 12, 0));
    	e.gc.drawString(task.getDate(), x + 480, y+25);
	}
    
	public void paintControl(PaintEvent e) {
		e.gc.setBackground(display.getSystemColor(SWT.COLOR_WHITE));
		e.gc.fillRoundRectangle(0, 0, 700, 100*todo.size() + 50, 25, 25);
		e.gc.setForeground(display.getSystemColor(SWT.COLOR_DARK_GREEN));
		if (todo.size() == 0) {
			e.gc.setFont(new Font(display, "Comic Sans", 12, SWT.BOLD));
	    	e.gc.drawString("All tasks complete!", 20, 10);
		} else {
			e.gc.setFont(new Font(display, "Comic Sans", 12, SWT.BOLD));
	    	e.gc.drawString("TO-DO: ", 20, 10);
		}
		
		for (int i = 0; i < todo.size(); i++) { 
			drawTask(e, i*100+baseY, todo.get(i));
		}
		
		
		int y =  100 * (todo.size() + 1) ;
		e.gc.setBackground(display.getSystemColor(SWT.COLOR_WHITE));
		e.gc.fillRoundRectangle(0, y, 700, 100*done.size() + 50, 25, 25);
		e.gc.setForeground(display.getSystemColor(SWT.COLOR_DARK_GREEN));
		e.gc.setFont(new Font(display, "Comic Sans", 12, SWT.BOLD));
		e.gc.drawString("COMPLETED TASKS: ", 20, y + 10);
		for (int i = 0; i < done.size(); i++) {
			drawTask(e, y + i*100+baseY, done.get(i));

		}
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

