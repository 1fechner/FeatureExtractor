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
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.wb.swt.SWTResourceManager;

import de.uni.hamburg.swk.extractor.database.entities.ASTAType;
import de.uni.hamburg.swk.extractor.database.entities.ak.TechnologyFeature;
import de.uni.hamburg.swk.extractor.gui.controller.edit.EditASTAController;
import de.uni.hamburg.swk.extractor.gui.controller.edit.EditASTAController.SaveASTAParameter;
import de.uni.hamburg.swk.extractor.gui.controller.edit.EditASTAController.ShowASTAParameter;
import de.uni.hamburg.swk.extractor.utils.Constants;

public class FormEditASTAs extends Dialog
{
    private Object result;
    private Shell shell;

    private Table _table;
    private Combo _comboType;
    private StyledText _textContext;
    private StyledText _textDescription;

    private EditASTAController _editASTAController;
    private Button _btnSave;
    private Button _btnCancel;
    private Button _btnConfirm;
    private Button _btnDelete;

    /**
     * Create the dialog.
     * 
     * @param parent
     * @param style
     */
    public FormEditASTAs(Shell parent, int style, TechnologyFeature feature)
    {
        super(parent, style);
        setText("SWT Dialog");

        _editASTAController = new EditASTAController(feature);
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

        _editASTAController.loadCombo(_comboType);
        load();
        setPanelEnabled(false);

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
        shell.setSize(767, 612);
        shell.setText(getText());

        _table = new Table(shell, SWT.BORDER | SWT.FULL_SELECTION);
        _table.addSelectionListener(new SelectionAdapter()
        {
            @Override
            public void widgetSelected(SelectionEvent arg0)
            {
                showSelection();
            }
        });
        _table.setBounds(10, 52, 743, 280);
        _table.setHeaderVisible(true);
        _table.setLinesVisible(true);

        TableColumn tblclmnContext = new TableColumn(_table, SWT.NONE);
        tblclmnContext.setWidth(274);
        tblclmnContext.setText("Context");

        TableColumn tblclmnDescription = new TableColumn(_table, SWT.NONE);
        tblclmnDescription.setWidth(318);
        tblclmnDescription.setText("Description");

        TableColumn tblclmnType = new TableColumn(_table, SWT.NONE);
        tblclmnType.setWidth(100);
        tblclmnType.setText("Type");

        Button btnNewAsta = new Button(shell, SWT.NONE);
        btnNewAsta.addSelectionListener(new SelectionAdapter()
        {
            @Override
            public void widgetSelected(SelectionEvent arg0)
            {
                newASTA();
            }
        });
        btnNewAsta.setBounds(10, 10, 123, 36);
        btnNewAsta.setText("New ASTA");
        btnNewAsta.setImage(SWTResourceManager.getImage(Constants.ICO_ADD));

        _btnCancel = new Button(shell, SWT.NONE);
        _btnCancel.addSelectionListener(new SelectionAdapter()
        {
            @Override
            public void widgetSelected(SelectionEvent arg0)
            {
                cancel();
            }
        });
        _btnCancel.setBounds(660, 564, 93, 36);
        _btnCancel.setText("Cancel");

        _btnSave = new Button(shell, SWT.NONE);
        _btnSave.addSelectionListener(new SelectionAdapter()
        {
            @Override
            public void widgetSelected(SelectionEvent arg0)
            {
                save();
            }
        });
        _btnSave.setBounds(561, 564, 93, 36);
        _btnSave.setText("Save");
        _btnSave.setImage(SWTResourceManager.getImage(Constants.ICO_AK_SAVE));

        Composite composite = new Composite(shell, SWT.BORDER);
        composite.setBounds(10, 338, 743, 213);

        Label lblContext = new Label(composite, SWT.NONE);
        lblContext.setBounds(10, 44, 68, 20);
        lblContext.setText("Context");

        Label lblDescription = new Label(composite, SWT.NONE);
        lblDescription.setBounds(394, 10, 94, 20);
        lblDescription.setText("Description");

        _comboType = new Combo(composite, SWT.READ_ONLY);
        _comboType.setBounds(84, 10, 304, 37);

        Label lblType = new Label(composite, SWT.NONE);
        lblType.setBounds(10, 10, 68, 20);
        lblType.setText("Type");

        _textContext = new StyledText(composite, SWT.BORDER);
        _textContext.setBounds(84, 53, 304, 110);

        _textDescription = new StyledText(composite, SWT.BORDER);
        _textDescription.setBounds(494, 10, 237, 153);

        _btnConfirm = new Button(composite, SWT.NONE);
        _btnConfirm.addSelectionListener(new SelectionAdapter()
        {
            @Override
            public void widgetSelected(SelectionEvent arg0)
            {
                confirm();
            }
        });
        _btnConfirm.setBounds(10, 169, 93, 36);
        _btnConfirm.setText("Confirm");
        _btnConfirm.setImage(SWTResourceManager.getImage(Constants.ICO_CONFIRM));

        _btnDelete = new Button(composite, SWT.NONE);
        _btnDelete.addSelectionListener(new SelectionAdapter()
        {
            @Override
            public void widgetSelected(SelectionEvent arg0)
            {
                delete();
            }
        });
        _btnDelete.setBounds(109, 169, 93, 36);
        _btnDelete.setText("Delete");
        _btnDelete.setImage(SWTResourceManager.getImage(Constants.ICO_DELETE));
    }

    protected void newASTA()
    {
        _editASTAController.createNew();

        showSelection();
    }

    private void showSelection()
    {
        ShowASTAParameter p = _editASTAController.new ShowASTAParameter(_comboType, _textContext, _textDescription);

        _editASTAController.showSelection(_table.getSelection(), p);

//        load();
        setPanelEnabled(true);
    }

    protected void confirm()
    {
        ASTAType t = ASTAType.valueOf(_comboType.getText());
        SaveASTAParameter p = _editASTAController.new SaveASTAParameter(t, _textContext.getText(),
                _textDescription.getText());

        _editASTAController.saveSelection(p);

        load();
        setPanelEnabled(false);
    }

    protected void delete()
    {
        _editASTAController.deleteCurrentSelection();

        load();
        setPanelEnabled(false);        
    }

    protected void save()
    {
        _editASTAController.apply();

        shell.close();
    }

    protected void cancel()
    {
        _editASTAController.abort();
        shell.close();
    }

    private void load()
    {
        _editASTAController.load(_table);
    }

    private void setPanelEnabled(boolean enabled)
    {
        _comboType.setEnabled(enabled);
        _textContext.setEnabled(enabled);
        _textDescription.setEnabled(enabled);

        _btnConfirm.setEnabled(enabled);
        _btnDelete.setEnabled(enabled);

        if (!enabled)
            clearSelection();
    }

    private void clearSelection()
    {
        _comboType.setText(ASTAType.Undefined.name());
        _textContext.setText(Constants.STRING_EMPTY);
        _textDescription.setText(Constants.STRING_EMPTY);
    }

}
