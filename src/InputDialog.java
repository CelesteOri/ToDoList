/* *****************************************************************************
* AUTHOR: Joanna Zabasajja
* FILE: InputDialog.java
* ASSIGNMENT: A4 - To-Do List
* COURSE: CSc 335; Fall 2022
* 
* PURPOSE: 
* 	Input dialog for entering a new tag. The code got too long, so I (Honor)
* 	moved it from MenuBar to its own class.
* 
* USAGE: 
* 	Allows the user to add tags
*/

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class InputDialog extends Dialog 
{
	private String message;
	private String input;
	
	/**
	 * A public constructor that makes the dialog box to add a new Tag.
	 * Not a lot to say about that.
	 * 
	 * @param parent, the Shell this is written on
	 * @param style, an int referring to the style of the Dialog; 
	 * 		inherited from Dialog
	 * 
	 * @return none; creates the InputDialog
	 */
	public InputDialog(Shell parent, int style) {
		super(parent, style);
		setText("Tag dialog");
		setMessage("Please enter a tag:");
	}
	
	/**
	 * A public constructor that makes the dialog box to add a new Tag.
	 * Has a built-in style.
	 * 
	 * @param parent, the Shell this is written on
	 * 
	 * @return none; creates the InputDialog
	 */
	public InputDialog(Shell parent) {
		this(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
	}
	
	/**
	 * A public method that returns the message.
	 * 
	 * @param none, just call.
	 * 
	 * @return message, a String
	 */
	public String getMessage() {
		return message;
	}
	
	/**
	 * A public method that sets the message to a given String.
	 * 
	 * @param message, the new String to use as a message
	 * 
	 * @return none; updates the value of (this.)message
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	
	/**
	 * A public method that returns the input.
	 * 
	 * @param none, just call.
	 * 
	 * @return input, a String
	 */
	public String getInput() {
		return input;
	}
	
	/**
	 * A public method that sets the input to a given String.
	 * 
	 * @param message, the new String to use as an input
	 * 
	 * @return none; updates the value of (this.)input
	 */
	public void setInput(String input) {
		this.input = input;
	}
	
	/**
	 * A public method that opens up the shell and creates its contents.
	 * Closes when disposed.
	 * 
	 * @param none; just call
	 * 
	 * @return input, the input String
	 */
	public String open() {
		Shell shell = new Shell(getParent(), getStyle());
		shell.setText(getText());
		createContents(shell);
		shell.pack();
		shell.open();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return input;
	}
	
	/**
	 * A private method that creates the contents of the shell,
	 * namely the field, ok, and cancel buttons
	 * 
	 * @param shell, the Shell this is added to
	 * 
	 * @return none; creates the field and buttons as well as their
	 * 		effects
	 */
	private void createContents(final Shell shell) {
		shell.setLayout(new GridLayout(2, true));

	    // Show the message
	    Label label = new Label(shell, SWT.NONE);
	    label.setText(message);
	    GridData data = new GridData();
	    data.horizontalSpan = 2;
	    label.setLayoutData(data);

	    // Display the input box
	    final Text text = new Text(shell, SWT.BORDER);
	    data = new GridData(GridData.FILL_HORIZONTAL);
	    data.horizontalSpan = 2;
	    text.setLayoutData(data);

	    // Create the OK button and add a handler
	    // so that pressing it will set input
	    // to the entered value
	    Button ok = new Button(shell, SWT.PUSH);
	    ok.setText("OK");
	    data = new GridData(GridData.FILL_HORIZONTAL);
	    ok.setLayoutData(data);
	    ok.addSelectionListener(new SelectionAdapter() {
	      public void widgetSelected(SelectionEvent event) {
	        input = text.getText();
	        shell.close();
	      }
	    });
	 // Create the cancel button and add a handler
	    // so that pressing it will set input to null
	    Button cancel = new Button(shell, SWT.PUSH);
	    cancel.setText("Cancel");
	    data = new GridData(GridData.FILL_HORIZONTAL);
	    cancel.setLayoutData(data);
	    cancel.addSelectionListener(new SelectionAdapter() {
	      public void widgetSelected(SelectionEvent event) {
	        input = null;
	        shell.close();
	      }
	    });

	    // Set the OK button as the default, so
	    // user can type input and press Enter
	    // to dismiss
	    shell.setDefaultButton(ok);
	  }
}
