import java.time.LocalDate;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;

public class CanvasPaintListener implements PaintListener {
	Canvas shell; Tab tabs; Display display;
	int pos[]; int select[]; int end[];
    
    public CanvasPaintListener(Canvas sh, Tab tabs, Display display, int[][] data) {
    	this.shell = sh; this.display = display;
    	this.tabs = tabs;
    	this.pos = data[0];
    	this.select = data[1];
    	this.end = data[2];
    }
    
    private int getNow() {
    	String todaysDate = LocalDate.now().toString();
    	String formatted = "";
    	for (int i = 2; i+1 < todaysDate.length(); i+=3) {
    		formatted += "" + todaysDate.charAt(i) + todaysDate.charAt(i+1);
    	}
    	return Integer.parseInt(formatted);
    }
    
    public void drawTask(PaintEvent e, int y, Task task) {
    	int x = pos[0]; 
    	
    	int baseColor = SWT.COLOR_DARK_GREEN;
    	int hoverColor = SWT.COLOR_GREEN;
    	
    	int color = baseColor; 
    	
    	if (getNow() > Integer.parseInt(task.getDate(1))) {
    		color = SWT.COLOR_DARK_RED; hoverColor = SWT.COLOR_RED;
    	}
    	if (task.isHovered()) { color = hoverColor; }
    	if (task.isCompleted()) { color = SWT.COLOR_DARK_GRAY;}
    	
		e.gc.setBackground(display.getSystemColor(color));
		e.gc.fillRoundRectangle(x, y, 600, 80, 10, 10);
		
		e.gc.setBackground(display.getSystemColor(SWT.COLOR_WHITE));
		e.gc.fillRoundRectangle(x + 10, y + 20, 40, 40, 25, 25);
		
		e.gc.setBackground(display.getSystemColor(color));
		e.gc.setForeground(display.getSystemColor(SWT.COLOR_WHITE));
		
		e.gc.setFont(new Font(display, "Courier", 16, SWT.BOLD));
    	e.gc.drawString(task.getTitle(), x + 70, y+25);
    	
    	if (getNow() <= Integer.parseInt(task.getDate(1)) ||
    			task.isCompleted()) {
    		e.gc.setFont(new Font(display, "Courier", 12, 0));
    		
    	} else {
    		e.gc.setFont(new Font(display, "Courier", 12, SWT.BOLD));
    	}
    	e.gc.setForeground(display.getSystemColor(SWT.COLOR_WHITE));
    	e.gc.drawString(task.getDate(0), x + 480, y+30);
    	
      	e.gc.setFont(new Font(display, "Courier", 12, 0));
      	if (task.getTag() != null) {
      		System.out.println("here");
      		if (task.getTag().getTitle() != null) {
          		e.gc.drawString(task.getTag().getTitle(), x + 480, y+50);

      		}
      	}
	}
    
	public void paintControl(PaintEvent e) {
		int y = pos[1]; int baseY = 40;
		
		e.gc.setBackground(display.getSystemColor(SWT.COLOR_DARK_BLUE));
		e.gc.fillRectangle(0, 0, 1000, 800);
		
		if (tabs.size() > select[0]) {
			e.gc.setBackground(display.getSystemColor(SWT.COLOR_WHITE));
			e.gc.fillRectangle(0, 30, 1000, 800);
			
			ToDoList todo = tabs.get(select[0])[0];
			ToDoList done = tabs.get(select[0])[1];
		
			e.gc.setBackground(display.getSystemColor(SWT.COLOR_GRAY));
			e.gc.fillRoundRectangle(0, y, 700, 100*todo.size() + 50, 25, 25);
			e.gc.setForeground(display.getSystemColor(SWT.COLOR_DARK_GREEN));
			e.gc.setFont(new Font(display, "Courier", 12, SWT.BOLD));
			if (todo.size() == 0 && done.size() == 0) {
				e.gc.drawString("Nothing to show! Why not add one?", 20, y + 15);
			} else if (todo.size() == 0) {
				e.gc.drawString("All tasks complete!", 20, y + 15);
			} else {
				e.gc.drawString("TO-DO: ", 20, y + 15);
			}
		
			for (int i = 0; i < todo.size(); i++) { 
                Task task = todo.get(i);
                if (task.getVisibility()) {
                    drawTask(e, i*100+baseY + y, todo.get(i));
                    if (task.isHovered()) {
                        e.gc.setBackground(display.getSystemColor(SWT.COLOR_WHITE));
                        e.gc.fillRoundRectangle(725, y, 250, 700, 25, 25);
                        e.gc.setForeground(display.getSystemColor(SWT.COLOR_DARK_GREEN));

                        e.gc.setFont(new Font(display, "Courier", 16, SWT.BOLD));
                        e.gc.drawString(task.getTitle(), 740, y + 15);

                        e.gc.setFont(new Font(display, "Courier", 10, SWT.BOLD));

                        String description = task.getDesc();
                        
                        int line = y; int j = 0;
                        int length = description.length();
                        
                        while (j < length) {
                            if (j+16 <= length) {
                            	if (description.charAt(j+16) == ' ') {
                            		String subStr = description.substring(j, j+16);
                            		e.gc.drawString(subStr, 745, line + 60);
                            		j += 16;
                            	} else {
                            		String subStr = description.substring(j, j+15);
                            		e.gc.drawString(subStr + "-", 745, line + 60);
                            		j += 15;
                            	}
                                line+=20; 
                            } else {
                                String subStr = description.substring(j, length);
                                e.gc.drawString(subStr, 745, line + 60);
                                j += length;
                            }
                        }
                    }
                }
                e.gc.setBackground(display.getSystemColor(SWT.COLOR_WHITE));
        		e.gc.fillRectangle(0, 30, 1000, 10);
			}
		
		
			int y2 =  100 * (todo.size() + 1) + y;
			e.gc.setBackground(display.getSystemColor(SWT.COLOR_GRAY));
			e.gc.fillRoundRectangle(0, y2, 700, 100*done.size() + 50, 25, 25);
			e.gc.setForeground(display.getSystemColor(SWT.COLOR_DARK_GREEN));
			e.gc.setFont(new Font(display, "Courier", 12, SWT.BOLD));
			e.gc.drawString("COMPLETED TASKS: ", 20, y2 + 15);
			for (int i = 0; i < done.size(); i++) {
				drawTask(e, y2 + (i*100)+baseY, done.get(i));
			}
			end[0] = y2;
		} else {
			e.gc.setForeground(display.getSystemColor(SWT.COLOR_DARK_GREEN));
			e.gc.setFont(new Font(display, "Courier", 12, SWT.BOLD));
			e.gc.drawString("A vast void greets you...", 20, 50);
			e.gc.drawString("How about making a new To-Do List?", 20, 90);
		}
		
		e.gc.setBackground(display.getSystemColor(SWT.COLOR_DARK_BLUE));
		e.gc.fillRectangle(0, 0, 1000, 30);
		for (int i = 0; i < tabs.size(); i++) {
			if (i == select[0]) {
				e.gc.setBackground(display.getSystemColor(SWT.COLOR_WHITE));
			} else {
				e.gc.setBackground(display.getSystemColor(SWT.COLOR_GRAY));
			}
			e.gc.fillRoundRectangle(i*175, 0, 175, 30, 25, 25);
			e.gc.fillRectangle(i*175, 15, 175, 15);
			e.gc.setForeground(display.getSystemColor(SWT.COLOR_DARK_GREEN));
			e.gc.setFont(new Font(display, "Courier", 10, SWT.BOLD));
			e.gc.drawString(tabs.displayName(i), i*175+10, 8);
			
			e.gc.setFont(new Font(display, "Courier", 10, SWT.BOLD));
			if (select[0] == i) {
				if (!tabs.get(i)[0].isHovered()) {
					e.gc.setForeground(display.getSystemColor(SWT.COLOR_GRAY));
				} else {
					e.gc.setForeground(display.getSystemColor(SWT.COLOR_DARK_GRAY));
				}
				e.gc.drawString("X", i*175+150, 10);
			}
		}
	}
} 