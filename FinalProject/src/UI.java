import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.GridLayout;

/**
 * THINGS TO ADJUST IF CANVASPAINTLISTENER ITEM POSITIONS/SIZES ARE MOVED:
 * - index from MouseListener and MouseMoveListener
 * 		- related if statements
 * **/


public class UI {
	public static void main(String[] args) {
		
		Display screen = new Display();
		
		// Testing purpose
		ToDoList todo = new ToDoList();
		Task test = new Task("Title", "Description", new int[]{12, 30, 22}); 
		todo.add(test);
		Task test2 = new Task("Title2", "Description", new int[]{1, 6, 23}); 
		todo.add(test2);
		
		Shell shell = new Shell(screen); 
		shell.setSize(1000, 600); shell.setLayout( new GridLayout() );
		
		//---- our Widgets start here 
	    Composite upperComp = new Composite(shell, SWT.NO_FOCUS);

	 	// Canvas setup
	 	Canvas canvas = new Canvas(upperComp, SWT.NONE);
	 	canvas.setSize(1000, 600); 
	 		
	 	canvas.addPaintListener(new CanvasPaintListener(canvas, todo, screen));
	 	canvas.addMouseListener(new MouseListener() {
	 		public void mouseDoubleClick(MouseEvent event) {}
	 		public void mouseDown(MouseEvent event) {
	 			int x = event.x; int y = event.y;
	 			int index = (event.y-50)/100; 
				
	 			if (x >= 10 && x <= 50 && index < todo.size()
	 				&& y >= (100*index) + 70 && y <= (100*index) + 110) {
	 				System.out.println("Clicked the box for " + index + "!");
				}  
	 		}
	 		public void mouseUp(MouseEvent e) {}
	 	});
	 	canvas.addMouseMoveListener(new MouseMoveListener() {
			public void mouseMove(MouseEvent event) {
				int x = event.x;
				int index = (event.y-50)/100;
				boolean change = false;
				
				int temp = -1;
				
				for (int i = 0; i < todo.size(); i++) {
					if (todo.get(i).isHovered()) {
						todo.get(i).off();
						change = true;
						temp = i;
					}
				}
				
				if (x >= 0 && x <= 600 && index < todo.size()) {
					todo.get(index).hover();
					if (temp == index) { change = false;}
					else { change = true; }
				} 
				
				if (change == true) { canvas.redraw(); }
			}
		});
	 	
	 	shell.open();
		while( !shell.isDisposed()) {
			if(!screen.readAndDispatch()) { screen.sleep(); }
		}
		screen.dispose();
	}
	
}

class CanvasPaintListener implements PaintListener {
    Canvas shell; ToDoList todo; Display display;
    
    public CanvasPaintListener(Canvas sh, ToDoList todo, Display display) {
    	this.shell = sh; this.todo = todo; this.display = display;
    }
    
    public void drawTask(PaintEvent e, int y, int i) {
    	Task task = todo.get(i);
    	
    	int baseColor = SWT.COLOR_DARK_GREEN;
    	int hoverColor = SWT.COLOR_GREEN;
    	
    	int color = baseColor;
    	if (task.isHovered()) { color = hoverColor; }
    	
		e.gc.setBackground(display.getSystemColor(color));
		e.gc.fillRoundRectangle(0, y, 600, 80, 10, 10);
		
		e.gc.setBackground(display.getSystemColor(SWT.COLOR_WHITE));
		e.gc.fillRoundRectangle(10, y + 20, 40, 40, 25, 25);
		
		e.gc.setBackground(display.getSystemColor(color));
		e.gc.setForeground(display.getSystemColor(SWT.COLOR_WHITE));
		
		e.gc.setFont(new Font(display, "Comic Sans", 16, SWT.BOLD));
    	e.gc.drawString(task.getTitle(), 70, y+20);
    	
    	e.gc.setFont(new Font(display, "Comic Sans", 12, 0));
    	e.gc.drawString(task.getDate(), 480, y+25);
	}
    
	public void paintControl(PaintEvent e) {
		for (int i = 0; i < todo.size(); i++) {
			drawTask(e, i*100+50, i);
		}
	}
}  
