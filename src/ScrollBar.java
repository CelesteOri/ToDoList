/* *****************************************************************************
* AUTHOR: Michelle Uddin and Honor Jang
* FILE: ScrollBar.java
* ASSIGNMENT: A4 - To-Do List
* COURSE: CSc 335; Fall 2022
* 
* PURPOSE: 
* 	An attempt to make a scrollbar for the program. It does not work very well.
* 
* USAGE: 
* 	Allows the user to scroll through the list to view more tasks,
* 	hypothetically.
*/
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Slider;

public class ScrollBar {
	private static Canvas canvas;
	private static int[] position;
	private static int[] end;
	
	private static int start;
	
	/**
	 * A public constructor that initializes and makes the ScrollBar.
	 * Not a lot to say about that.
	 * 
	 * @param canvas, the Canvas that is affected
	 * @param position, an int[] that contains UI placement information
	 * @param end, an int[] that contains info on the endpoint location
	 * @param start, an int referring to the default position
	 * 
	 * @return none; creates the ScollBar
	 */
	public ScrollBar(Canvas canvas, int[] position, int[] end, int start) {
		this.canvas = canvas;
		this.position = position;
		this.end = end;
		this.start = start;
		initSlider();
	}
	
	/**
	 * A private method that initializes the ScrollBar. Does not work too
	 * well, but I could not fix it. In theory, it'd allow scaled scrolling,
	 * but that did not happen in time.
	 * 
	 * @param none, just call.
	 * 
	 * @return none; initializes the ScollBar for usage
	 */
	private static void initSlider() {
		Slider slider = new Slider (canvas, SWT.VERTICAL);
		Rectangle clientArea = canvas.getClientArea();
		
		slider.setBounds (clientArea.width - 17, clientArea.y + 30, 15, clientArea.height-35);
		slider.setMinimum(0); slider.setMaximum(100); slider.setThumb(10);
        slider.setIncrement(5);
        
        // This is where I struggled
		slider.addListener (SWT.Selection, event -> {
			switch (event.detail) {
				case SWT.DRAG: 
					position[1] = -(slider.getSelection() - 50) * end[0] / 90;
					if (position[1] < -end[0]) { position[1] = -end[0]; }
					if (position[1] > start) { position[1] = start; }
					break;
				case SWT.ARROW_DOWN: 
					position[1] -= 20; 
					if (position[1] < -end[0]) { position[1] = -end[0]; }
					break;
				case SWT.ARROW_UP: 
					position[1] += 20; 
					if (position[1] > start) { position[1] = start; }
					break;
			}
			canvas.redraw();
		});
	}
}
