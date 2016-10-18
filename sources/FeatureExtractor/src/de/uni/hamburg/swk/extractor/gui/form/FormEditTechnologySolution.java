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

import de.uni.hamburg.swk.extractor.database.dao.impl.TechnologySolutionDAO;
import de.uni.hamburg.swk.extractor.database.entities.ak.TechnologySolution;
import de.uni.hamburg.swk.extractor.gui.controller.edit.EditTechnologySolutionController;
import de.uni.hamburg.swk.extractor.gui.controller.utils.MessageController;
import de.uni.hamburg.swk.extractor.gui.controller.utils.OpenController;
import de.uni.hamburg.swk.extractor.utils.Constants;
import de.uni.hamburg.swk.extractor.utils.Messages;

public class FormEditTechnologySolution extends Dialog
{
    private static final String WINDOW_TITLE = "Edit Architectural Knowledge (Technologies)";

    private OpenController _openController;
    private EditTechnologySolutionController _technologySolutionEditController;

    protected Object result;
    protected Shell shell;

    private Table _table;
    private Text _lblName;
    private Combo _comboDependsOn;
    private Button _checkEnabled;
    private StyledText _txtDescription;

    private Button _btnSaveTech;

    private Button _btnDelete;

    private Button _btnEditFeatures;

    /**
     * Create the dialog.
     * 
     * @param parent
     * @param style
     */
    public FormEditTechnologySolution(Shell parent, int style)
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
        shell.setSize(939, 652);
        shell.setText(getText());

        Button btnSave = new Button(shell, SWT.NONE);
        btnSave.addSelectionListener(new SelectionAdapter()
        {
            @Override
            public void widgetSelected(SelectionEvent arg0)
            {
                save();
            }
        });
        btnSave.setImage(SWTResourceManager.getImage(Constants.ICO_AK_SAVE));
        btnSave.setBounds(731, 604, 94, 36);
        btnSave.setText("Save");

        Button btnCancel = new Button(shell, SWT.NONE);
        btnCancel.addSelectionListener(new SelectionAdapter()
        {
            @Override
            public void widgetSelected(SelectionEvent arg0)
            {
                cancel();
            }
        });
        btnCancel.setBounds(831, 604, 94, 36);
        btnCancel.setText("Cancel");

        Label lblStatus = new Label(shell, SWT.NONE);
        lblStatus.setBounds(10, 604, 615, 36);
        lblStatus.setText("");

        Composite composite = new Composite(shell, SWT.BORDER);
        composite.setBounds(10, 406, 915, 192);

        _lblName = new Text(composite, SWT.BORDER);
        _lblName.setBounds(103, 10, 379, 37);

        _checkEnabled = new Button(composite, SWT.CHECK);
        _checkEnabled.setBounds(488, 53, 46, 24);

        _comboDependsOn = new Combo(composite, SWT.READ_ONLY);
        _comboDependsOn.setBounds(103, 53, 271, 37);

        Label lblName = new Label(composite, SWT.NONE);
        lblName.setBounds(10, 10, 69, 20);
        lblName.setText("Name");

        Label lblDescription = new Label(composite, SWT.NONE);
        lblDescription.setBounds(488, 10, 79, 20);
        lblDescription.setText("Description");

        Label lblDependson = new Label(composite, SWT.NONE);
        lblDependson.setBounds(10, 57, 87, 20);
        lblDependson.setText("DependsOn");

        _txtDescription = new StyledText(composite, SWT.BORDER | SWT.WRAP);
        _txtDescription.setBounds(573, 10, 332, 172);

        Label lblEnabled = new Label(composite, SWT.NONE);
        lblEnabled.setBounds(431, 53, 51, 20);
        lblEnabled.setText("Enabled");

        _btnSaveTech = new Button(composite, SWT.NONE);
        _btnSaveTech.addSelectionListener(new SelectionAdapter()
        {
            @Override
            public void widgetSelected(SelectionEvent arg0)
            {
                confirm();
            }
        });
        _btnSaveTech.setBounds(373, 146, 94, 36);
        _btnSaveTech.setText("Confirm");

        _btnDelete = new Button(composite, SWT.NONE);
        _btnDelete.addSelectionListener(new SelectionAdapter()
        {
            @Override
            public void widgetSelected(SelectionEvent arg0)
            {
                delete();
            }
        });
        _btnDelete.setImage(SWTResourceManager.getImage(Constants.ICO_DELETE));
        _btnDelete.setBounds(473, 146, 94, 36);
        _btnDelete.setText("Delete");

        _btnEditFeatures = new Button(composite, SWT.NONE);
        _btnEditFeatures.addSelectionListener(new SelectionAdapter()
        {
            @Override
            public void widgetSelected(SelectionEvent arg0)
            {
                editFeatures();
            }
        });
        _btnEditFeatures.setImage(SWTResourceManager.getImage(Constants.ICO_TECHNOLOGY_FEATURE));
        _btnEditFeatures.setBounds(10, 146, 143, 36);
        _btnEditFeatures.setText("Edit Features");

        // Link linkCreateDepended = new Link(composite, SWT.NONE);
        // linkCreateDepended.addSelectionListener(new SelectionAdapter()
        // {
        // @Override
        // public void widgetSelected(SelectionEvent arg0)
        // {
        // createChild();
        // }
        // });
        // linkCreateDepended.setBounds(10, 94, 87, 20);
        // linkCreateDepended.setText("<a>Create Child</a>");

        // Link link = new Link(composite, 0);
        // link.addSelectionListener(new SelectionAdapter()
        // {
        // @Override
        // public void widgetSelected(SelectionEvent arg0)
        // {
        // createTechnologyFeature();
        // }
        //
        // private void createTechnologyFeature()
        // {
        // System.out.println("Click2");
        // }
        // });
        // link.setText("<a>Create Technology Feature</a>");
        // link.setBounds(10, 120, 187, 20);

        Button btnNewTechnology = new Button(shell, SWT.NONE);
        btnNewTechnology.addSelectionListener(new SelectionAdapter()
        {
            @Override
            public void widgetSelected(SelectionEvent arg0)
            {
                newTechnology();
            }
        });
        btnNewTechnology.setImage(SWTResourceManager.getImage(Constants.ICO_ADD));
        btnNewTechnology.setBounds(10, 6, 155, 36);
        btnNewTechnology.setText("New Technology");

        _table = new Table(shell, SWT.BORDER | SWT.FULL_SELECTION);
        _table.setBounds(10, 48, 915, 352);
        _table.setHeaderVisible(true);
        _table.setLinesVisible(true);
        _table.addListener(SWT.Selection, new Listener()
        {
            public void handleEvent(Event e)
            {
                selectTableElement();
            }
        });

        TableColumn tblclmnName = new TableColumn(_table, SWT.LEAD);
        tblclmnName.setWidth(212);
        tblclmnName.setText("Name");

        TableColumn tblclmnDependsOn = new TableColumn(_table, SWT.NONE);
        tblclmnDependsOn.setWidth(205);
        tblclmnDependsOn.setText("DependsOn");

        TableColumn tblclmnName_1 = new TableColumn(_table, SWT.NONE);
        tblclmnName_1.setWidth(419);
        tblclmnName_1.setText("Description");

        TableColumn tblclmnEnabled = new TableColumn(_table, SWT.NONE);
        tblclmnEnabled.setWidth(25);
        tblclmnEnabled.setResizable(false);
        tblclmnEnabled.setText("Enabled");

        Button btnApply = new Button(shell, SWT.NONE);
        btnApply.addSelectionListener(new SelectionAdapter()
        {
            @Override
            public void widgetSelected(SelectionEvent arg0)
            {
                applyChanges();
            }
        });
        btnApply.setText("Apply");
        btnApply.setBounds(631, 604, 94, 36);

        setPanelEnabled(false);

        // Prevent closing
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

    private void applyChanges()
    {
        _technologySolutionEditController.apply();
        _technologySolutionEditController.load(_table);
    }

    private void selectTableElement()
    {
        EditTechnologySolutionController.ShowTechnologySolutionParameter p = _technologySolutionEditController.new ShowTechnologySolutionParameter(
                _lblName, _comboDependsOn, _txtDescription, _checkEnabled);

        if (_technologySolutionEditController.showSelection(_table.getSelection(), p))
            setPanelEnabled(true);
    }

    private void setPanelEnabled(boolean enabled)
    {
        _lblName.setEnabled(enabled);
        _txtDescription.setEnabled(enabled);
        _comboDependsOn.setEnabled(enabled);
        _checkEnabled.setEnabled(enabled);

        _btnSaveTech.setEnabled(enabled);
        _btnDelete.setEnabled(enabled);
        _btnEditFeatures.setEnabled(enabled);

        if (!enabled)
            clearSelection();
    }

    private void clearSelection()
    {
        _lblName.setText("");
        _comboDependsOn.setText("");
        _checkEnabled.setSelection(true);
        _txtDescription.setText("");
    }

    private void saveChanges()
    {
        TechnologySolution dependency = null;
        String n = _comboDependsOn.getText();
        if (n != null && !n.isEmpty())
            dependency = new TechnologySolutionDAO().getByName(n);

        EditTechnologySolutionController.SaveTechnologySolutionParameter p = _technologySolutionEditController.new SaveTechnologySolutionParameter(
                _lblName.getText(), dependency, _txtDescription.getText(), _checkEnabled.getSelection());
        _technologySolutionEditController.saveSelection(p);

        _technologySolutionEditController.load(_table);
    }

    private void load()
    {
        _openController = new OpenController();

        _technologySolutionEditController = new EditTechnologySolutionController();
        _technologySolutionEditController.load(_table);
        _technologySolutionEditController.loadCombo(_comboDependsOn);
    }

    private void save()
    {
        applyChanges();
        shell.close();
    }

    private void cancel()
    {
        if (!_technologySolutionEditController.hasChanges())
            shell.close();
        else
        {
            int res = MessageController.show(Messages.TITLE_SAVE_CHANGES, Messages.MSG_SAVE_CHANGES,
                    SWT.ICON_WARNING | SWT.CANCEL | SWT.NO | SWT.YES);

            if (res == SWT.YES)
            {
                _technologySolutionEditController.apply();
                shell.close();
            }
            else if (res == SWT.NO)
            {
                _technologySolutionEditController.abort();
                shell.close();
            }
        }
    }

    private void confirm()
    {
        saveChanges();
        setPanelEnabled(false);
    }

    private void delete()
    {
        if (_technologySolutionEditController.getCurrentSelection() != null)
        {
            String msg = String.format("Do you really want to delete the Technology '%s'?",
                    _technologySolutionEditController.getCurrentSelection().getName());
            int res = MessageController.show("Confirm Delete", msg, SWT.ICON_QUESTION | SWT.YES | SWT.NO);

            if (res == SWT.YES)
                _technologySolutionEditController.deleteCurrentSelection();

            _technologySolutionEditController.load(_table);

            setPanelEnabled(false);
        }
    }

    private void editFeatures()
    {
        if (_technologySolutionEditController.getCurrentSelection() != null)
        {
            _openController.openEditTechnologyFeatures(shell, _technologySolutionEditController.getCurrentSelection());
            _technologySolutionEditController.load(_table);
        }
    }

    // private void createChild()
    // {
    // System.out.println("Implement!");
    // }

    private void newTechnology()
    {
        _technologySolutionEditController.createNew();
        clearSelection();
        setPanelEnabled(true);
    }
}
