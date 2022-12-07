
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
	static int OFFSET = 40; static int START = 50;
	static int OGCOORD[] = {20, START};
	
	public static void main(String[] args) {
		int coords[] = {OGCOORD[0], OGCOORD[1]}; int endpoint[] = {0};
		
		Display screen = new Display();
		
		Tab tabs = new Tab();
		
		Shell shell = new Shell(screen); 
		shell.setSize(1000, 800); shell.setLayout( new GridLayout() );
		shell.setText("To-Do List");
		
		Composite upperComp = new Composite(shell, SWT.NO_FOCUS);
		Canvas canvas = new Canvas(upperComp, SWT.NONE);
	 	canvas.setSize(1000, 800); 
	 	int select[] = {0};
	 	
	 	MenuBar menu = new MenuBar(shell, canvas, screen, tabs, select);
	 	ScrollBar scroll[] = {new ScrollBar(canvas, coords, endpoint, START)};

	 	int data[][] = {coords, select, endpoint};
	 	canvas.addPaintListener(
	 			new CanvasPaintListener(canvas, tabs, screen, data));
	 	
	 	canvas.addMouseListener(new MouseListener() {
	 		public void mouseDoubleClick(MouseEvent event) {}
	 		public void mouseDown(MouseEvent event) {
	 			int x = event.x; int y = event.y;
	 			int index = (y - coords[1] - OFFSET)/100; 
	 			int tab = x/175;
	 			if (x >= 175*tab && x < 175*tab+175
	 					&& y >= 0 && y <= 30 && tab < tabs.size()
	 					&& select[0] != tab) {
	 				select[0] = tab;
	 				ScrollBar temp = new ScrollBar(canvas, coords, endpoint, START);
	 				scroll[0] = temp;
	 				coords[1] = OGCOORD[1];
	 				canvas.redraw();
	 			} else if (x >= 175*tab + 150 && x < 175*tab+170
	 					&& y >= 5 && y <= 25 && tab < tabs.size()) {
	 				tabs.close(select[0]);
	 				select[0] = tab - 1;
	 				if (select[0] < 0) { select[0] = 0; }
	 				coords[1] = OGCOORD[1];
	 				ScrollBar temp = new ScrollBar(canvas, coords, endpoint, START);
	 				scroll[0] = temp;
	 				canvas.redraw();
	 			} 
	 			
				
	 			if (tabs.size() > 0) {
	 				ToDoList todo = tabs.get(select[0])[0];
					ToDoList done = tabs.get(select[0])[1];
					if (todo.get(index) != null) {
						if (todo.get(index).getVisibility()) {
							if (x >= coords[0] + 10 && x <= coords[0] + 50 
				                     && y >= (100*index) + coords[1] + 20 + OFFSET
				                     && y <= (100*index) + coords[1] + 60 + OFFSET
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
		 			
	 			}
	 		}
	 		public void mouseUp(MouseEvent e) {}
	 	});
	 	canvas.addMouseMoveListener(new MouseMoveListener() {
			public void mouseMove(MouseEvent event) {
				int x = event.x; int y = event.y;
				int index = (y - coords[1] - OFFSET)/100;
				boolean change = false;
				
				int tempTab = -1;
				int tempTodo = -1;
				
				int tab = x/175;
				for (int i = 0; i < tabs.size(); i++) {
					if (tabs.get(i)[0].isHovered()) {
						tabs.get(i)[0].hover(false);
						change = true; tempTab = i;
					}
				}
				if (x >= 175*tab + 150 && x < 175*tab+170
	 					&& y >= 5 && y <= 25 && tab < tabs.size()) {
					tabs.get(tab)[0].hover(true);
	 				if (tempTab == tab) { change = false;}
	                   else { change = true; }
	 			} 
				
				if (tabs.size() > 0) {
					ToDoList todo = tabs.get(select[0])[0];
					for (int i = 0; i < todo.size(); i++) {
						if (todo.get(i).isHovered()) {
							todo.get(i).off();
							change = true; tempTodo = i;
						}
					}
					
					if (x >= coords[0] && x <= coords[0] + 600
	                        && y >= (100*index) + coords[1] + OFFSET
	                        && y <= (100*index) + coords[1] + 80 + OFFSET
	                        && index < todo.size() && index >= 0) {
	                   todo.get(index).hover();
	                   if (tempTodo == index) { change = false;}
	                   else { change = true; }
	                } 
					
					
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

