import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Slider;

public class ScrollBar {
	private static Canvas canvas;
	private static int[] position;
	private static int[] end;
	
	private static int start;
	
	public ScrollBar(Canvas canvas, int[] position, int[] end, int start) {
		this.canvas = canvas;
		this.position = position;
		this.end = end;
		this.start = start;
		initSlider();
	}
	
	private static void initSlider() {
		Slider slider = new Slider (canvas, SWT.VERTICAL);
		Rectangle clientArea = canvas.getClientArea();
		
		slider.setBounds (clientArea.width - 17, clientArea.y + 30, 15, clientArea.height-35);
		slider.setMinimum(0); slider.setMaximum(100); slider.setThumb(10);
        slider.setIncrement(5);
        
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
