
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
	 			int tab = x/175;
	 			if (x >= 175*tab && x < 175*tab+175
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

