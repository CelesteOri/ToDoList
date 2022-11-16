import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.GridLayout;

public class UI {
	public static void main(String[] args) {
		
		Display screen = new Display();
		
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
    
	public void paintControl(PaintEvent e) {
		for (int i = 0; i < todo.size(); i++) {
			e.gc.setForeground(display.getSystemColor(SWT.COLOR_DARK_GREEN));
			e.gc.setBackground(display.getSystemColor(SWT.COLOR_DARK_GREEN));
			e.gc.fillRoundRectangle(0, i*100 + 50, 600, 80, 10, 10);
			e.gc.setBackground(display.getSystemColor(SWT.COLOR_WHITE));
			e.gc.fillRoundRectangle(10, i*100 + 70, 40, 40, 25, 25);
			e.gc.setBackground(display.getSystemColor(SWT.COLOR_DARK_GREEN));
			e.gc.setForeground(display.getSystemColor(SWT.COLOR_WHITE));
			e.gc.setFont(new Font(display, "Comic Sans", 16, SWT.BOLD));
        	e.gc.drawString(todo.get(i).getTitle(), 70, i*100+70);
        	e.gc.setFont(new Font(display, "Comic Sans", 12, 0));
        	e.gc.drawString(todo.get(i).getDate(), 480, i*100+75);
		}
	}
}  
