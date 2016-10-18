package de.uni.hamburg.swk.extractor.gui.dialog;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import de.uni.hamburg.swk.extractor.configuration.Configuration;
import de.uni.hamburg.swk.extractor.configuration.OptimizationRules;
import de.uni.hamburg.swk.extractor.utils.Constants;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Composite;

public class DialogPreferences extends Dialog
{

    private static final String WINDOW_TITLE = "Preferences";

    protected Object result;
    protected Shell shell;

    private Button btn100;
    private Button btn101;
    private Button btn102;
    private Button btn200;
    private Button btn201;
    private Button btn202;
    private Button btn203;
    private Button btn204;
    private Button btn300;
    private Button btn301;
    private Button btn400;
    private Button btn401;

    private Text textIgnoreDirectories;
    private Text textVaildFiles;
    private List listValidFiles;
    private List listIgnoreDirectories;
    private Spinner spinnerLimitRecursion;
    private TabItem tbtmDecisionpoints;
    private Composite composite_3;

    /**
     * Create the dialog.
     * 
     * @param parent
     * @param style
     */
    public DialogPreferences(Shell parent, int style)
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
        shell.setSize(725, 587);
        shell.setText(getText());

        Button btnOk = new Button(shell, SWT.NONE);
        btnOk.addSelectionListener(new SelectionAdapter()
        {
            @Override
            public void widgetSelected(SelectionEvent arg0)
            {
                close();
            }
        });
        btnOk.setBounds(616, 539, 94, 36);
        btnOk.setText("Ok");

        Label lblSettings = new Label(shell, SWT.NONE);
        lblSettings.setBounds(10, 10, 80, 20);
        lblSettings.setText("Preferences");

        TabFolder tabFolder = new TabFolder(shell, SWT.NONE);
        tabFolder.setBounds(10, 36, 700, 490);

        TabItem tbtmAlternatives = new TabItem(tabFolder, SWT.NONE);
        tbtmAlternatives.setText("Prescan");

        Composite composite_1 = new Composite(tabFolder, SWT.NONE);
        tbtmAlternatives.setControl(composite_1);

        btn100 = new Button(composite_1, SWT.CHECK);
        btn100.setBounds(10, 10, 251, 24);
        btn100.addSelectionListener(new SelectionAdapter()
        {
            @Override
            public void widgetSelected(SelectionEvent arg0)
            {
                OptimizationRules.R_100_LIMIT_RECURSION = btn100.getSelection();
            }
        });
        btn100.setToolTipText("If activated, the search for features is stopped if there are too many levels of folders containing the target sources. \nThis option is used to prevent an endless recursion while scanning files.\nDisable at your own risk.");
        btn100.setText("Limit Recursion");

        spinnerLimitRecursion = new Spinner(composite_1, SWT.BORDER);
        spinnerLimitRecursion.setEnabled(false);
        spinnerLimitRecursion.setBounds(267, 10, 203, 37);

        btn101 = new Button(composite_1, SWT.CHECK);
        btn101.setToolTipText("Ignores all directories in the list while scanning.\nThis prevents the extractor from scanning unnecessary information, for example IDE-configuration, log-files, binaries etc.\nYou can add and remove new directories as your wish, results might be unpredictable.\nPlease note that when log-directories included and their file-type is valid, the runtime could increase drastically.");
        btn101.setBounds(10, 52, 251, 24);
        btn101.addSelectionListener(new SelectionAdapter()
        {
            @Override
            public void widgetSelected(SelectionEvent arg0)
            {
                OptimizationRules.R_101_IGNORE_DIRECTORIES = btn101.getSelection();
            }
        });
        btn101.setText("Ignore Directories");

        listIgnoreDirectories = new List(composite_1, SWT.BORDER | SWT.V_SCROLL);
        listIgnoreDirectories.setBounds(267, 53, 203, 76);

        textIgnoreDirectories = new Text(composite_1, SWT.BORDER);
        textIgnoreDirectories.setBounds(476, 51, 204, 37);

        Button btnRemoveIgnoreDirectories = new Button(composite_1, SWT.NONE);
        btnRemoveIgnoreDirectories.addSelectionListener(new SelectionAdapter()
        {
            @Override
            public void widgetSelected(SelectionEvent arg0)
            {
                removeDirectory();
            }
        });
        btnRemoveIgnoreDirectories.setBounds(476, 94, 130, 36);
        btnRemoveIgnoreDirectories.setText("Remove Selected");

        Button btnAddIgnoreDirectories = new Button(composite_1, SWT.NONE);
        btnAddIgnoreDirectories.addSelectionListener(new SelectionAdapter()
        {
            @Override
            public void widgetSelected(SelectionEvent arg0)
            {
                addDirectory();
            }
        });
        btnAddIgnoreDirectories.setBounds(612, 94, 68, 36);
        btnAddIgnoreDirectories.setText("Add");

        btn102 = new Button(composite_1, SWT.CHECK);
        btn102.setToolTipText("Only scans the file-types in the list.\nThis avoids scanning files that contain no useful information, e.g. log-files.\nYou could exclude types that have no rules associated with them whatoever.\nYou can add and remove new types as your wish, results might be unpredictable.");
        btn102.setBounds(10, 135, 251, 24);
        btn102.addSelectionListener(new SelectionAdapter()
        {
            @Override
            public void widgetSelected(SelectionEvent arg0)
            {
                OptimizationRules.R_102_IGNORE_FILE_TYPES = btn102.getSelection();
            }
        });
        btn102.setText("Valid File-Types");

        listValidFiles = new List(composite_1, SWT.BORDER | SWT.V_SCROLL);
        listValidFiles.setBounds(267, 135, 203, 76);

        textVaildFiles = new Text(composite_1, SWT.BORDER);
        textVaildFiles.setBounds(476, 135, 204, 37);

        Button btnRemoveIgnoreFiles = new Button(composite_1, SWT.NONE);
        btnRemoveIgnoreFiles.addSelectionListener(new SelectionAdapter()
        {
            @Override
            public void widgetSelected(SelectionEvent arg0)
            {
                removeFile();
            }
        });
        btnRemoveIgnoreFiles.setBounds(476, 175, 130, 36);
        btnRemoveIgnoreFiles.setText("Remove Selected");

        Button btnAddIgnoreFiles = new Button(composite_1, SWT.NONE);
        btnAddIgnoreFiles.addSelectionListener(new SelectionAdapter()
        {
            @Override
            public void widgetSelected(SelectionEvent arg0)
            {
                addFile();
            }
        });
        btnAddIgnoreFiles.setBounds(612, 175, 68, 36);
        btnAddIgnoreFiles.setText("Add");

        TabItem tbtmExtraction = new TabItem(tabFolder, SWT.NONE);
        tbtmExtraction.setText("Extraction");

        Composite composite = new Composite(tabFolder, SWT.NONE);
        tbtmExtraction.setControl(composite);

        btn200 = new Button(composite, SWT.CHECK);
        btn200.setToolTipText("Ignores features that have no indicatores assiciated with them.\nThough this might not be useful for the given architectural knowledge, the complexity of the algorithm could drecrease if this is checked.");
        btn200.addSelectionListener(new SelectionAdapter()
        {
            @Override
            public void widgetSelected(SelectionEvent arg0)
            {
                OptimizationRules.R_200_IGNORE_FEATURES_WITHOUT_INDICATORS = btn200.getSelection();
            }
        });
        btn200.setBounds(10, 10, 670, 24);
        btn200.setText("Ignore Features without Indicators");

        btn201 = new Button(composite, SWT.CHECK);
        btn201.setToolTipText("Format the input of all files before processing.\nThis ensures that there are no false negatives due to bad formatting and statements over multiple lines.\nThe input files are not changed on disk. The formatting is done in-place.");
        btn201.addSelectionListener(new SelectionAdapter()
        {
            @Override
            public void widgetSelected(SelectionEvent arg0)
            {
                OptimizationRules.R_201_FORMAT_INPUT = btn201.getSelection();
            }
        });
        btn201.setEnabled(false);
        btn201.setBounds(10, 40, 670, 24);
        btn201.setText("Format Input");

        btn202 = new Button(composite, SWT.CHECK);
        btn202.setToolTipText("Sanitizes the input before scanning.\nThis removes uncesessary lines from the source files, e.g. empty lines, comments, lines containing only braces etc.\nThis prevents scanning lines without any information for every indicator, thereby dramatically reducing complexity.");
        btn202.addSelectionListener(new SelectionAdapter()
        {
            @Override
            public void widgetSelected(SelectionEvent arg0)
            {
                OptimizationRules.R_202_SANITIZE_INPUT = btn202.getSelection();
            }
        });
        btn202.setBounds(10, 70, 670, 24);
        btn202.setText("Sanitize Input");

        btn203 = new Button(composite, SWT.CHECK);
        btn203.setToolTipText("Enables multi threading.\nFiles are independet and can be scanned concurrently.");
        btn203.addSelectionListener(new SelectionAdapter()
        {
            @Override
            public void widgetSelected(SelectionEvent arg0)
            {
                OptimizationRules.R_203_CONCURENT_PROCESSING = btn203.getSelection();
            }
        });
        btn203.setEnabled(false);
        btn203.setBounds(10, 100, 670, 24);
        btn203.setText("Multi Threading");

        btn204 = new Button(composite, SWT.CHECK);
        btn204.setToolTipText("Exports the extraction results to a csv file.\nThis can be used for further analysis");
        btn204.setText("Export Extraction Results to CSV");
        btn204.setSelection(false);
        btn204.setBounds(10, 130, 670, 24);
        btn204.addSelectionListener(new SelectionAdapter()
        {
            @Override
            public void widgetSelected(SelectionEvent arg0)
            {
                OptimizationRules.R_204_EXPORT_RESULTS_CSV = btn204.getSelection();
            }
        });

        TabItem tbtmDecisionPoints = new TabItem(tabFolder, SWT.NONE);
        tbtmDecisionPoints.setText("Alternatives");

        Composite composite_2 = new Composite(tabFolder, SWT.NONE);
        tbtmDecisionPoints.setControl(composite_2);

        btn300 = new Button(composite_2, SWT.CHECK);
        btn300.setToolTipText("Enables technologies to 'inherit' features from their dependencies.");
        btn300.addSelectionListener(new SelectionAdapter()
        {
            @Override
            public void widgetSelected(SelectionEvent arg0)
            {
                OptimizationRules.R_300_INHERITED_FEATURES = btn300.getSelection();
            }
        });
        btn300.setBounds(10, 10, 670, 24);
        btn300.setText("Inherit Features from Dependencies");

        btn301 = new Button(composite_2, SWT.CHECK);
        btn301.setToolTipText("If two technologies are on the same chain of dependencies (One technology is a direct of transitive dependency of the other), they are not considered alternatives.\nThis is useful, if you consider specification such as JPA and their implementations such as Hibernate as seperate technologies with respective depenendies.");
        btn301.addSelectionListener(new SelectionAdapter()
        {
            @Override
            public void widgetSelected(SelectionEvent arg0)
            {
                OptimizationRules.R_301_DIRECT_RELATION_NO_ALTERNATIVE = btn301.getSelection();
            }
        });
        btn301.setBounds(10, 40, 670, 24);
        btn301.setText("Don't consider Features on the same dependency path as alternatives");

        tbtmDecisionpoints = new TabItem(tabFolder, SWT.NONE);
        tbtmDecisionpoints.setText("Decisionpoints");

        composite_3 = new Composite(tabFolder, SWT.NONE);
        tbtmDecisionpoints.setControl(composite_3);

        btn400 = new Button(composite_3, SWT.CHECK);
        btn400.setToolTipText("Consider the full dependeny path when using the threshold.\nThis includes all dependencies above the designated decision point, thereby measuring the absolute amount of dependencies.\nTechnologies with less dependencies overall are easier to exchange.");
        btn400.addSelectionListener(new SelectionAdapter()
        {
            @Override
            public void widgetSelected(SelectionEvent arg0)
            {
                OptimizationRules.R_400_COMPLETE_PATH = btn400.getSelection();
            }
        });
        btn400.setBounds(10, 10, 670, 24);
        btn400.setText("Use complete dependency path");

        btn401 = new Button(composite_3, SWT.CHECK);
        btn401.setToolTipText("Add a warning to the determined decision points, if the possible alternative does not cover all currenlty used features.");
        btn401.addSelectionListener(new SelectionAdapter()
        {
            @Override
            public void widgetSelected(SelectionEvent arg0)
            {
                OptimizationRules.R_401_WARN_MISSING_FEATURE = btn401.getSelection();
            }
        });
        btn401.setBounds(10, 40, 670, 24);
        btn401.setText("Warn if alternative is missing features");

        Label lblPleaseNotePreferences = new Label(shell, SWT.NONE);
        lblPleaseNotePreferences.setBounds(10, 532, 600, 43);
        lblPleaseNotePreferences
                .setText("Please note: Preferences are only saved at runtime.\nThey will no be available after restarting the application");

    }

    private void load()
    {
        btn100.setSelection(OptimizationRules.R_100_LIMIT_RECURSION);
        btn101.setSelection(OptimizationRules.R_101_IGNORE_DIRECTORIES);
        btn102.setSelection(OptimizationRules.R_102_IGNORE_FILE_TYPES);

        btn200.setSelection(OptimizationRules.R_200_IGNORE_FEATURES_WITHOUT_INDICATORS);
        btn201.setSelection(OptimizationRules.R_201_FORMAT_INPUT);
        btn202.setSelection(OptimizationRules.R_202_SANITIZE_INPUT);
        btn203.setSelection(OptimizationRules.R_203_CONCURENT_PROCESSING);
        btn203.setSelection(OptimizationRules.R_204_EXPORT_RESULTS_CSV);

        btn300.setSelection(OptimizationRules.R_300_INHERITED_FEATURES);
        btn301.setSelection(OptimizationRules.R_301_DIRECT_RELATION_NO_ALTERNATIVE);

        btn400.setSelection(OptimizationRules.R_400_COMPLETE_PATH);
        btn401.setSelection(OptimizationRules.R_401_WARN_MISSING_FEATURE);

        spinnerLimitRecursion.setSelection(Constants.PRESCAN_MAX_DEPTH);

        listIgnoreDirectories.removeAll();
        for (String s : Configuration.DIRECTORIES_EXCLUDED)
        {
            listIgnoreDirectories.add(s);
        }

        listValidFiles.removeAll();
        for (String s : Configuration.FILETYPES_VALID)
        {
            listValidFiles.add(s);
        }
    }

    private void addDirectory()
    {
        String txt = textIgnoreDirectories.getText();

        if (txt != null && !txt.equals("") && !Configuration.DIRECTORIES_EXCLUDED.contains(txt))
        {
            Configuration.DIRECTORIES_EXCLUDED.add(txt);
            textIgnoreDirectories.setText("");
            load();
        }
    }

    private void removeDirectory()
    {
        String[] selection = listIgnoreDirectories.getSelection();
        if (selection != null && selection.length > 0)
        {
            String s = selection[0];
            Configuration.DIRECTORIES_EXCLUDED.remove(s);
            load();
        }
    }

    private void addFile()
    {
        String txt = textVaildFiles.getText();

        if (txt != null && !txt.equals("") && !Configuration.FILETYPES_VALID.contains(txt))
        {
            Configuration.FILETYPES_VALID.add(txt);
            textVaildFiles.setText("");
            load();
        }
    }

    private void removeFile()
    {
        String[] selection = listValidFiles.getSelection();
        if (selection != null && selection.length > 0)
        {
            String s = selection[0];
            Configuration.FILETYPES_VALID.remove(s);
            load();
        }
    }

    private void close()
    {
        shell.close();
    }
}
