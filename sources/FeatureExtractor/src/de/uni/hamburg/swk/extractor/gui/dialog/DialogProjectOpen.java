package de.uni.hamburg.swk.extractor.gui.dialog;

import java.util.List;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

import de.uni.hamburg.swk.extractor.configuration.Configuration;
import de.uni.hamburg.swk.extractor.database.SessionService;
import de.uni.hamburg.swk.extractor.database.dao.impl.ProjectDAO;
import de.uni.hamburg.swk.extractor.database.entities.result.Project;

public class DialogProjectOpen extends Dialog
{
    private final String TITLE = "Open Project...";

    protected Object result;
    protected Shell shell;
    private Combo combo;

    /**
     * Create the dialog.
     * 
     * @param parent
     * @param style
     */
    public DialogProjectOpen(Shell parent, int style)
    {
        super(parent, SWT.BORDER | SWT.CLOSE);
        setText(TITLE);
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

        load();

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
        shell.setSize(445, 126);
        shell.setText(getText());

        Label lblSelectProjectFrom = new Label(shell, SWT.NONE);
        lblSelectProjectFrom.setBounds(10, 10, 342, 20);
        lblSelectProjectFrom.setText("Select Project from connected database");

        Button btnOk = new Button(shell, SWT.NONE);
        btnOk.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseDown(MouseEvent arg0)
            {
                confirm();
            }
        });
        btnOk.setBounds(242, 79, 94, 36);
        btnOk.setText("Ok");

        Button btnCancel = new Button(shell, SWT.NONE);
        btnCancel.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseDown(MouseEvent arg0)
            {
                closeWindow();
            }
        });

        btnCancel.setBounds(342, 79, 94, 36);
        btnCancel.setText("Cancel");

        combo = new Combo(shell, SWT.NONE);
        combo.setBounds(10, 36, 426, 37);
    }

    private void load()
    {
        List<Project> projects = new ProjectDAO().getAll();

        for (Project p : projects)
        {
            combo.add(p.getName());
        }

        SessionService.closeCurrentSession();
    }

    private void confirm()
    {
        String selectedProject = combo.getItem(combo.getSelectionIndex());

        if (!selectedProject.isEmpty())
        {
            Project p = new ProjectDAO().getByName(selectedProject);
            Configuration.setSelectedProject(p);
            closeWindow();
        }
    }

    private void closeWindow()
    {
        shell.close();
    }
}
