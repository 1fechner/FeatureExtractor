package de.uni.hamburg.swk.extractor.gui.form;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;

import de.uni.hamburg.swk.extractor.gui.controller.tree.ViewKnowledgeBaseTreeController;

public class FormViewKnowledgeBase extends Dialog
{
    private static final String WINDOW_TITLE = "View Knowledge Base";

    private ViewKnowledgeBaseTreeController _controller;

    protected Object result;
    protected Shell shell;

    private Tree _tree;

    /**
     * Create the dialog.
     * 
     * @param parent
     * @param style
     */
    public FormViewKnowledgeBase(Shell parent, int style)
    {
        super(parent, SWT.DIALOG_TRIM);
        setText(WINDOW_TITLE);
        
        _controller = new ViewKnowledgeBaseTreeController();
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

        _controller.load(_tree);

        Display display = getParent().getDisplay();
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
        shell.setSize(775, 575);
        shell.setText(getText());

        Button btnClose = new Button(shell, SWT.NONE);
        btnClose.addSelectionListener(new SelectionAdapter()
        {
            @Override
            public void widgetSelected(SelectionEvent arg0)
            {
                closeWindow();
            }
        });
        btnClose.setBounds(667, 527, 94, 36);
        btnClose.setText("Close");

        _tree = new Tree(shell, SWT.BORDER);
        _tree.setBounds(10, 10, 751, 511);

    }

    private void closeWindow()
    {
        shell.close();
    }
}
