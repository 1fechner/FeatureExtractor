package de.uni.hamburg.swk.extractor.gui.form;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import de.uni.hamburg.swk.extractor.database.dao.impl.FeatureDAO;
import de.uni.hamburg.swk.extractor.database.dao.impl.TechnologyFeatureDAO;
import de.uni.hamburg.swk.extractor.database.entities.ak.Feature;
import de.uni.hamburg.swk.extractor.database.entities.ak.TechnologyFeature;
import de.uni.hamburg.swk.extractor.database.entities.ak.TechnologySolution;
import de.uni.hamburg.swk.extractor.gui.controller.edit.EditTechnologyFeatureController;
import de.uni.hamburg.swk.extractor.gui.controller.edit.EditTechnologyFeatureController.SaveTechnologyFeatureParameter;
import de.uni.hamburg.swk.extractor.gui.controller.edit.EditTechnologyFeatureController.ShowTechnologyFeatureParameter;
import de.uni.hamburg.swk.extractor.gui.controller.utils.OpenController;
import de.uni.hamburg.swk.extractor.utils.Constants;

public class FormEditTechnologyFeatures extends Dialog
{
    private final static String TITLE = "Edit Technology Features for %s";

    private OpenController _openController;
    private EditTechnologyFeatureController _technologyFeatureEditController;

    protected Object result;
    protected Shell shell;
    private Table _table;

    private TechnologySolution _selection;

    private Text _textName;
    private Combo _comboFeature;
    private Combo _comboDependsOn;
    private StyledText _textDescription;

    private Button _btnDelete;

    private Button _btnConfirm;

    /**
     * Create the dialog.
     * 
     * @param parent
     * @param style
     */
    public FormEditTechnologyFeatures(Shell parent, int style, TechnologySolution solution)
    {
        super(parent, style);
        setText(String.format(TITLE, solution.getName()));
        _selection = solution;
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
        shell.setSize(767, 556);
        shell.setText(getText());

        Composite composite = new Composite(shell, SWT.BORDER);
        composite.setBounds(10, 318, 743, 184);

        Label lblName = new Label(composite, SWT.NONE);
        lblName.setBounds(10, 10, 69, 20);
        lblName.setText("Name");

        _textName = new Text(composite, SWT.BORDER);
        _textName.setBounds(107, 10, 317, 37);

        _textDescription = new StyledText(composite, SWT.BORDER);
        _textDescription.setBounds(430, 34, 303, 141);

        Label lblDescription = new Label(composite, SWT.NONE);
        lblDescription.setBounds(430, 10, 303, 20);
        lblDescription.setText("Description");

        _comboDependsOn = new Combo(composite, SWT.READ_ONLY);
        _comboDependsOn.setBounds(107, 53, 317, 37);

        _comboFeature = new Combo(composite, SWT.READ_ONLY);
        _comboFeature.setBounds(107, 96, 317, 37);

        Label lblBelongsTo = new Label(composite, SWT.NONE);
        lblBelongsTo.setBounds(10, 53, 91, 20);
        lblBelongsTo.setText("Depends On");

        Label lblDependsOn = new Label(composite, SWT.NONE);
        lblDependsOn.setBounds(10, 96, 91, 20);
        lblDependsOn.setText("Feature");

        _btnDelete = new Button(composite, SWT.NONE);
        _btnDelete.addSelectionListener(new SelectionAdapter()
        {
            @Override
            public void widgetSelected(SelectionEvent arg0)
            {
                delete();
            }
        });
        _btnDelete.setBounds(330, 139, 94, 36);
        _btnDelete.setText("Delete");
        _btnDelete.setImage(SWTResourceManager.getImage(Constants.ICO_DELETE));

        _btnConfirm = new Button(composite, SWT.NONE);
        _btnConfirm.addSelectionListener(new SelectionAdapter()
        {
            @Override
            public void widgetSelected(SelectionEvent arg0)
            {
                confirm();
            }
        });
        _btnConfirm.setBounds(230, 139, 94, 36);
        _btnConfirm.setText("Confirm");
        _btnConfirm.setImage(SWTResourceManager.getImage(Constants.ICO_CONFIRM));

        Button btnEditIndicators = new Button(composite, SWT.NONE);
        btnEditIndicators.addSelectionListener(new SelectionAdapter()
        {
            @Override
            public void widgetSelected(SelectionEvent arg0)
            {
                openEditIndicators();
            }
        });
        btnEditIndicators.setBounds(10, 139, 127, 36);
        btnEditIndicators.setText("Edit Indicators");
        btnEditIndicators.setImage(SWTResourceManager.getImage(Constants.ICO_INDICATOR));
        
        Button btnEditAsta = new Button(composite, SWT.NONE);
        btnEditAsta.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent arg0) {
                openEditASTAs();
            }
        });
        btnEditAsta.setBounds(143, 139, 81, 36);
        btnEditAsta.setText("Edit ASTA");

        Button btnCancel = new Button(shell, SWT.NONE);
        btnCancel.addSelectionListener(new SelectionAdapter()
        {
            @Override
            public void widgetSelected(SelectionEvent arg0)
            {
                close();
            }
        });
        btnCancel.setBounds(659, 508, 94, 36);
        btnCancel.setText("Cancel");

        Button btnSave = new Button(shell, SWT.NONE);
        btnSave.addSelectionListener(new SelectionAdapter()
        {
            @Override
            public void widgetSelected(SelectionEvent arg0)
            {
                save();
            }
        });
        btnSave.setBounds(559, 508, 94, 36);
        btnSave.setText("Save");
        btnSave.setImage(SWTResourceManager.getImage(Constants.ICO_AK_SAVE));

        _table = new Table(shell, SWT.BORDER | SWT.FULL_SELECTION);
        _table.setBounds(10, 52, 743, 257);
        _table.setHeaderVisible(true);
        _table.setLinesVisible(true);
        _table.addListener(SWT.Selection, new Listener()
        {
            public void handleEvent(Event e)
            {
                selectTableElement();
            }

        });

        TableColumn tblclmnName = new TableColumn(_table, SWT.NONE);
        tblclmnName.setWidth(100);
        tblclmnName.setText("Name");

        TableColumn tblclmnBelongsTo = new TableColumn(_table, SWT.NONE);
        tblclmnBelongsTo.setWidth(122);
        tblclmnBelongsTo.setText("Depends On");

        TableColumn tblclmnDependsOn = new TableColumn(_table, SWT.NONE);
        tblclmnDependsOn.setWidth(100);
        tblclmnDependsOn.setText("Feature");

        TableColumn tblclmnDescription = new TableColumn(_table, SWT.NONE);
        tblclmnDescription.setWidth(100);
        tblclmnDescription.setText("Description");

        Button btnNewFeature = new Button(shell, SWT.NONE);
        btnNewFeature.addSelectionListener(new SelectionAdapter()
        {
            @Override
            public void widgetSelected(SelectionEvent arg0)
            {
                createNewTechnologyFeature();
            }
        });
        btnNewFeature.setBounds(10, 10, 200, 36);
        btnNewFeature.setText("New Technology Feature");
        btnNewFeature.setImage(SWTResourceManager.getImage(Constants.ICO_ADD));

        setPanelEnabled(false);

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

    private void selectTableElement()
    {
        ShowTechnologyFeatureParameter p = _technologyFeatureEditController.new ShowTechnologyFeatureParameter(
                _textName, _comboDependsOn, _comboFeature, _textDescription);

        if (_technologyFeatureEditController.showSelection(_table.getSelection(), p))
            setPanelEnabled(true);
    }

    private void setPanelEnabled(boolean enabled)
    {
        _textName.setEnabled(enabled);
        _comboDependsOn.setEnabled(enabled);
        _comboFeature.setEnabled(enabled);
        _textDescription.setEnabled(enabled);

        _btnConfirm.setEnabled(enabled);
        _btnDelete.setEnabled(enabled);

        if (!enabled)
            clearSelection();
    }

    private void clearSelection()
    {
        _textName.setText("");
        _comboDependsOn.setText("");
        _comboFeature.setText("");
        _textDescription.setText("");
    }

    private void delete()
    {
        _technologyFeatureEditController.deleteCurrentSelection();
        setPanelEnabled(false);
        _technologyFeatureEditController.load(_table);
    }

    private void confirm()
    {
        TechnologyFeature dependsOn = null;
        String n = _comboDependsOn.getText();
        // If there is no entry if the given name, the dependency is null
        if (n != null && !n.isEmpty())
            dependsOn = new TechnologyFeatureDAO().getByName(n, _selection.getId());

        Feature feature = null;
        n = _comboFeature.getText();
        if (n != null && !n.isEmpty())
            feature = new FeatureDAO().getByName(n);

        SaveTechnologyFeatureParameter p = _technologyFeatureEditController.new SaveTechnologyFeatureParameter(
                _textName.getText(), _selection, dependsOn, feature, _textDescription.getText());

        _technologyFeatureEditController.saveSelection(p);
        setPanelEnabled(false);

        _technologyFeatureEditController.load(_table);
    }

    private void save()
    {
        _technologyFeatureEditController.apply();
        shell.close();
    }

    private void openEditIndicators()
    {
        if (_technologyFeatureEditController.hasCurrentSelection())
            _openController.openEditIndicators(shell, _technologyFeatureEditController.getCurrentSelection());
    }
    

    private void openEditASTAs()
    {
        if (_technologyFeatureEditController.hasCurrentSelection())
            _openController.openEditASTA(shell, _technologyFeatureEditController.getCurrentSelection());
    }

    private void close()
    {
        _technologyFeatureEditController.abort();
        shell.close();
    }

    private void createNewTechnologyFeature()
    {
        _technologyFeatureEditController.createNew();
        clearSelection();
        setPanelEnabled(true);
    }

    private void load()
    {
        _openController = new OpenController();

        _technologyFeatureEditController = new EditTechnologyFeatureController(_selection);
        _technologyFeatureEditController.load(_table);
        _technologyFeatureEditController.loadCombo(_comboDependsOn, _comboFeature);
    }
}