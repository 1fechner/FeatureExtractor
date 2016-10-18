package de.uni.hamburg.swk.extractor.gui.splash;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

public class SplashScreen extends Dialog
{
    private static final String WINDOW_TITLE = "Loading...";

    protected Object result;
    protected Shell shell;

    /**
     * Create the dialog.
     * 
     * @param parent
     * @param style
     */
    public SplashScreen(Shell parent, int style)
    {
        super(parent, style);
        setText(WINDOW_TITLE);
    }

    /**
     * Open the dialog.
     * 
     * @return the result
     */
    public Object open()
    {
        createContents();
        shell.open();
        shell.layout();
        Display display = getParent().getDisplay();
        System.out.println("Splash?");
        while (!shell.isDisposed())
        {
            if (!display.readAndDispatch())
            {
                display.sleep();
            }
        }
        return result;
    }

    /**
     * Create contents of the dialog.
     */
    private void createContents()
    {
        shell = new Shell(getParent(), getStyle());
        shell.setSize(450, 300);
        shell.setText(getText());

        shell.addListener(SWT.Traverse, new Listener()
        {
            public void handleEvent(Event e)
            {
                if (e.detail == SWT.TRAVERSE_ESCAPE)
                {
                    e.doit = false;
                }
            }
        });
    }

    public void close()
    {
        shell.close();
    }

}
