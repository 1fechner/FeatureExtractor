package de.uni.hamburg.swk.extractor.gui.dialog;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;
import org.hibernate.Transaction;

import de.uni.hamburg.swk.extractor.configuration.Configuration;
import de.uni.hamburg.swk.extractor.database.SessionService;
import de.uni.hamburg.swk.extractor.database.entities.result.Project;
import de.uni.hamburg.swk.extractor.gui.callback.CallbackRegistry;
import de.uni.hamburg.swk.extractor.gui.callback.CallbackType;
import de.uni.hamburg.swk.extractor.gui.callback.impl.CallbackStatus;
import de.uni.hamburg.swk.extractor.utils.Constants;
import de.uni.hamburg.swk.extractor.utils.Messages;
import de.uni.hamburg.swk.extractor.utils.MessagesError;

public class DialogProjectNewEdit extends Dialog
{
    private static final String WINDOW_TITLE_NEW = "New Project";
    private static final String WINDOW_TITLE_EDIT = "Edit Project %s";

    protected Object result;
    protected Shell shell;

    private Text textProjectName;
    private Text textProjectPath;
    private Spinner _spinnerConfidence;
    private Spinner _spinnerDependencies;
    private Spinner _spinnerCoverage;

    private Mode _mode;

    public enum Mode
    {
        CREATE, EDIT
    }

    /**
     * Create the dialog.
     * 
     * @param parent
     * @param style
     */
    public DialogProjectNewEdit(Shell parent, int style, Mode mode)
    {
        super(parent, SWT.DIALOG_TRIM);

        _mode = mode;

        switch (_mode)
        {
            case CREATE:
                setText(WINDOW_TITLE_NEW);
                break;
            case EDIT:
                setText(String.format(WINDOW_TITLE_EDIT, Configuration.getSelectedProject().getName()));
                break;
            default:
        }
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

        if (_mode == Mode.EDIT)
            loadSelectedProject();

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
        shell.setSize(454, 266);
        shell.setText(getText());

        Button btnCancel = new Button(shell, SWT.NONE);
        btnCancel.setImage(SWTResourceManager.getImage(Constants.ICO_CANCEL));
        btnCancel.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseDown(MouseEvent arg0)
            {
                shell.close();
            }
        });
        btnCancel.setBounds(342, 218, 94, 36);
        btnCancel.setText("Cancel");

        Button btnSave = new Button(shell, SWT.NONE);
        btnSave.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseDown(MouseEvent arg0)
            {
                if (doIt())
                    shell.close();
            }
        });
        btnSave.setImage(SWTResourceManager.getImage(Constants.ICO_CONFIRM));
        btnSave.setBounds(242, 218, 94, 36);
        btnSave.setText("Save");

        textProjectName = new Text(shell, SWT.BORDER);
        textProjectName.setBounds(100, 10, 336, 32);

        Label lblName = new Label(shell, SWT.NONE);
        lblName.setFont(SWTResourceManager.getFont("Cantarell", 11, SWT.NORMAL));
        lblName.setBounds(10, 10, 84, 24);
        lblName.setText("Name");

        Label lblPath = new Label(shell, SWT.NONE);
        lblPath.setBounds(10, 52, 69, 20);
        lblPath.setText("Path");

        textProjectPath = new Text(shell, SWT.BORDER);
        textProjectPath.setBounds(100, 48, 282, 36);

        Label lblConfidenceThreshold = new Label(shell, SWT.NONE);
        lblConfidenceThreshold.setBounds(10, 100, 100, 20);
        lblConfidenceThreshold.setText("Min Confidence");

        _spinnerConfidence = new Spinner(shell, SWT.BORDER);
        _spinnerConfidence.setSelection(75);
        _spinnerConfidence.setPageIncrement(5);
        _spinnerConfidence.setBounds(10, 126, 126, 37);

        Button btnOpenPath = new Button(shell, SWT.NONE);
        btnOpenPath.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseDown(MouseEvent arg0)
            {
                openFileDialog();
            }

        });
        btnOpenPath.setBounds(388, 48, 48, 36);
        btnOpenPath.setImage(SWTResourceManager.getImage(Constants.ICO_FOLDER));

        Label lblStatus = new Label(shell, SWT.NONE);
        lblStatus.setText("Enter details of project...");
        lblStatus.setBounds(10, 169, 426, 43);

        CallbackRegistry.register(new CallbackStatus(lblStatus), DialogProjectNewEdit.class);

        Label lblHelpConfidenceThreshold = new Label(shell, SWT.NONE);
        lblHelpConfidenceThreshold.setImage(SWTResourceManager.getImage(Constants.ICO_HELP));
        lblHelpConfidenceThreshold.setToolTipText(Messages.DESC_CONFIDENCE);
        lblHelpConfidenceThreshold.setBounds(116, 100, 20, 20);

        _spinnerCoverage = new Spinner(shell, SWT.BORDER);
        _spinnerCoverage.setSelection(90);
        _spinnerCoverage.setPageIncrement(5);
        _spinnerCoverage.setBounds(142, 126, 126, 37);

        _spinnerDependencies = new Spinner(shell, SWT.BORDER);
        _spinnerDependencies.setSelection(5);
        _spinnerDependencies.setPageIncrement(5);
        _spinnerDependencies.setMinimum(0);
        _spinnerDependencies.setMaximum(Integer.MAX_VALUE);
        _spinnerDependencies.setBounds(274, 126, 162, 37);

        Label lblMinCoverage = new Label(shell, SWT.NONE);
        lblMinCoverage.setText("Min Coverage");
        lblMinCoverage.setBounds(142, 100, 100, 20);

        Label lblMaxDependencyChain = new Label(shell, SWT.NONE);
        lblMaxDependencyChain.setText("Max Dependencies");
        lblMaxDependencyChain.setBounds(274, 100, 136, 20);

        Label label = new Label(shell, SWT.NONE);
        label.setToolTipText(Messages.DESC_COVERAGE);
        label.setImage(SWTResourceManager.getImage(Constants.ICO_HELP));
        label.setBounds(248, 100, 20, 20);

        Label label_1 = new Label(shell, SWT.NONE);
        label_1.setToolTipText(Messages.DESC_DECISION_POINTS);
        label_1.setImage(SWTResourceManager.getImage(Constants.ICO_HELP));
        label_1.setBounds(416, 100, 20, 20);
    }

    private boolean doIt()
    {
        if (textProjectName.getText().isEmpty())
        {
            CallbackRegistry.invoke(CallbackType.STATUS, new Object[] { MessagesError.PROJECT_NEW_NO_NAME },
                    DialogProjectNewEdit.class);
        }
        else if (textProjectPath.getText().isEmpty())
        {
            CallbackRegistry.invoke(CallbackType.STATUS, new Object[] { MessagesError.PROJECT_NEW_NO_DIRECTORY },
                    DialogProjectNewEdit.class);
        }
        else if (!new File(textProjectPath.getText()).exists())
        {

            CallbackRegistry.invoke(CallbackType.STATUS, new Object[] { MessagesError.PROJECT_NEW_INVALID_DIRECTORY },
                    DialogProjectNewEdit.class);
        }
        else if (!_spinnerConfidence.getText().matches("\\d{1,3}"))
        {
            CallbackRegistry.invoke(CallbackType.STATUS, new Object[] { MessagesError.PROJECT_NEW_CONFIDENCE_INVALID },
                    DialogProjectNewEdit.class);
        }
        else if (!_spinnerCoverage.getText().matches("\\d{1,3}"))
        {

            CallbackRegistry.invoke(CallbackType.STATUS, new Object[] { MessagesError.PROJECT_NEW_COVERAGE_INVALID },
                    DialogProjectNewEdit.class);
        }
        else
        {
            Project p = null;

            if (_mode == Mode.CREATE)
                p = new Project();
            else if (_mode == Mode.EDIT)
                p = Configuration.getSelectedProject();

            if (p != null)
            {
                populateAndSave(p);
                return true;
            }
        }

        return false;
    }

    private void loadSelectedProject()
    {
        Project p = Configuration.getSelectedProject();

        textProjectName.setText(p.getName());
        textProjectPath.setText(p.getSource());
        _spinnerConfidence.setSelection(Math.round(p.getMinConfidence() * 100));
        _spinnerCoverage.setSelection(Math.round(p.getMinFeatureCoverage() * 100));
        _spinnerDependencies.setSelection(p.getMaxDependencyChain());
    }

    private void populateAndSave(Project project)
    {
        float confidence = (float) Integer.parseInt(_spinnerConfidence.getText()) / 100f;
        float coverage = (float) Integer.parseInt(_spinnerCoverage.getText()) / 100f;
        int dependencyChain = Integer.parseInt(_spinnerDependencies.getText());

        project.setName(textProjectName.getText());
        project.setSource(textProjectPath.getText());
        project.setMinConfidence(confidence);
        project.setMinFeatureCoverage(coverage);
        project.setMaxDependencyChain(dependencyChain);
        project.setVersion(_mode == Mode.CREATE ? 0 : project.getVersion());

        Transaction tx = SessionService.getCurrentSession().beginTransaction();
        SessionService.getCurrentSession().save(project);
        tx.commit();

        Configuration.setSelectedProject(project);
    }

    private void openFileDialog()
    {
        DirectoryDialog dlg = new DirectoryDialog(shell, 0);
        String dir = dlg.open();

        if (dir != null && !dir.isEmpty())
            textProjectPath.setText(dir);
    }
}
