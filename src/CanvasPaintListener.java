import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;

public class CanvasPaintListener implements PaintListener {
	Canvas shell; Tab tabs; Display display;
    int x; int baseY; int select[];
    
    public CanvasPaintListener(Canvas sh, Tab tabs, Display display, int coord[], int select[]) {
    	this.shell = sh; this.display = display;
    	this.tabs = tabs;
    	this.x = coord[0]; this.baseY = coord[1];
    	this.select = select;
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
    	
      	e.gc.setFont(new Font(display, "Comic Sans", 12, 0));
      	if (task.getTag() != null) {
      		System.out.println("here");
      		if (task.getTag().getTitle() != null) {
          		e.gc.drawString(task.getTag().getTitle(), x + 480, y+50);

      		}
      	}
      	
      	
    	
    	
	}
    
	public void paintControl(PaintEvent e) {
		int y = 40;
		
		for (int i = 0; i < tabs.size(); i++) {
			if (i == select[0]) {
				e.gc.setBackground(display.getSystemColor(SWT.COLOR_WHITE));
			} else {
				e.gc.setBackground(display.getSystemColor(SWT.COLOR_GRAY));
			}
			e.gc.fillRoundRectangle(i*100, 0, 100, 30, 25, 25);
			e.gc.fillRectangle(i*100, 15, 100, 60);
			e.gc.setForeground(display.getSystemColor(SWT.COLOR_DARK_GREEN));
			e.gc.setFont(new Font(display, "Comic Sans", 10, SWT.BOLD));
			e.gc.drawString(tabs.getName(i), i*100+10, 10);
		}
		
		if (tabs.size() > select[0]) {
			
			ToDoList todo = tabs.get(select[0])[0];
			ToDoList done = tabs.get(select[0])[1];
		
			e.gc.setBackground(display.getSystemColor(SWT.COLOR_WHITE));
			e.gc.fillRoundRectangle(0, y, 700, 100*todo.size() + 50, 25, 25);
			e.gc.setForeground(display.getSystemColor(SWT.COLOR_DARK_GREEN));
			e.gc.setFont(new Font(display, "Comic Sans", 12, SWT.BOLD));
			if (todo.size() == 0 && done.size() == 0) {
				e.gc.drawString("Nothing to show! Why not add one?", 20, y + 10);
			} else if (todo.size() == 0) {
				e.gc.drawString("All tasks complete!", 20, y + 10);
			} else {
				e.gc.drawString("TO-DO: ", 20, y + 10);
			}
		
			for (int i = 0; i < todo.size(); i++) { 
				Task task = todo.get(i);
				if (task.getVisibility()) {
					drawTask(e, i*100+baseY + y, todo.get(i));
					if (task.isHovered()) {
						e.gc.setBackground(display.getSystemColor(SWT.COLOR_WHITE));
						e.gc.fillRoundRectangle(725, y, 250, 700, 25, 25);
						e.gc.setForeground(display.getSystemColor(SWT.COLOR_DARK_GREEN));
						
						e.gc.setFont(new Font(display, "Comic Sans", 16, SWT.BOLD));
				    	e.gc.drawString(task.getTitle(), 740, y + 15);
						
						e.gc.setFont(new Font(display, "Comic Sans", 10, SWT.BOLD));
						e.gc.drawString(task.getDesc(), 745, y + 60);
					}
				}
			}
		
		
			int y2 =  100 * (todo.size() + 1) + y;
			e.gc.setBackground(display.getSystemColor(SWT.COLOR_WHITE));
			e.gc.fillRoundRectangle(0, y2, 700, 100*done.size() + 50, 25, 25);
			e.gc.setForeground(display.getSystemColor(SWT.COLOR_DARK_GREEN));
			e.gc.setFont(new Font(display, "Comic Sans", 12, SWT.BOLD));
			e.gc.drawString("COMPLETED TASKS: ", 20, y2 + 10);
			for (int i = 0; i < done.size(); i++) {
				drawTask(e, y2 + i*100+baseY, done.get(i));
	
			}
		}
	}
} 