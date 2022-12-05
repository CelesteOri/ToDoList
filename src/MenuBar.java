import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

public class MenuBar {

	private static Shell shell;
	private static Canvas canvas;
	private static Display display;
	private static Tab tabs;
	private static int select[];
	
	// menus
	private static Menu menuBar, fileMenu, editMenu;
	
	// menu icons
	private static MenuItem fileMenuHeader, editMenuHeader;
	
	// file menu widgets
	private static MenuItem fileExitItem, fileNewItem;
	
	// add task widgets
	private static MenuItem editAddItem, editTagItem;
	
	
	public MenuBar(Shell shell, Canvas canvas, Display display, Tab tabs, int select[]) {
		this.shell = shell;
		this.display = display;
		this.canvas = canvas;
		this.tabs = tabs;
		this.select = select;
		menuBar = new Menu(shell, SWT.BAR);
		initializeMenu();
		shell.setMenuBar(menuBar);
	}
	
	public Menu getMenuBar() {
		return menuBar;
	}
	
	private static void initializeMenu() {
		// initializes the file portion of the menu
		initFileSubmenu();
		
		initEditSubmenu();		
	}
	
	private static void shutDown() {
		shell.close();
		display.dispose();
	}
	
	private static void initFileSubmenu() {
		fileMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
		fileMenuHeader.setText("File");
		fileMenu = new Menu(shell, SWT.DROP_DOWN);
		fileMenuHeader.setMenu(fileMenu);

	    fileNewItem = new MenuItem(fileMenu, SWT.PUSH);
	    fileNewItem.setText("Create New To-Do List");
	    fileNewItem.addSelectionListener(new SelectionListener() {
	    	public void widgetSelected(SelectionEvent event) {
	    		tabs.add();
	    		System.out.println(tabs.size());
	    		canvas.redraw();
	    	}
	    	public void widgetDefaultSelected(SelectionEvent event) {
	    	}	    		
	    });
	    
	    fileExitItem = new MenuItem(fileMenu, SWT.PUSH);
	    fileExitItem.setText("Exit");
	    fileExitItem.addSelectionListener(new SelectionListener() {
	    	public void widgetSelected(SelectionEvent event) {
	    		shutDown();
	    	}
	    	public void widgetDefaultSelected(SelectionEvent event) {
	    		shutDown();
	    	}
	    });
	}
	
	private static void initEditSubmenu() {
		editMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
		editMenuHeader.setText("Edit");
		editMenu = new Menu(shell, SWT.DROP_DOWN);
		editMenuHeader.setMenu(editMenu);

	    editAddItem = new MenuItem(editMenu, SWT.PUSH);
	    editAddItem.setText("Add Task");
	    editAddItem.addSelectionListener(new SelectionListener() {
	    	public void widgetSelected(SelectionEvent event) {
	    		if (tabs.size() > 0) {
	    			int date[] = {0, 0, 22};
	    			tabs.get(select[0])[0].add(new Task("Test", "x", date, null));
	    		}
	    		canvas.redraw();
	    	}
	    	public void widgetDefaultSelected(SelectionEvent event) {
	    	}	    		
	    });
	    
	}
}