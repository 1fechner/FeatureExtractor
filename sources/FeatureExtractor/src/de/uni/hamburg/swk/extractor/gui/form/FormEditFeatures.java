package de.uni.hamburg.swk.extractor.gui.form;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
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

import de.uni.hamburg.swk.extractor.gui.controller.edit.EditFeatureController;
import de.uni.hamburg.swk.extractor.gui.controller.edit.EditFeatureController.SaveFeatureParameter;
import de.uni.hamburg.swk.extractor.gui.controller.edit.EditFeatureController.ShowFeatureParameter;
import de.uni.hamburg.swk.extractor.utils.Constants;

public class FormEditFeatures extends Dialog
{
    private EditFeatureController _editFeatureController;

    private static final String WINDOW_TITLE = "Edit Features";

    protected Object result;
    protected Shell shell;

    private Table _tableFeatures;
    private Text _textName;
    private Text _textAssociation;
    private StyledText _textDescription;

    private Button _btnDelete;

    private Button _btnConfirm;

    /**
     * Create the dialog.
     * 
     * @param parent
     * @param style
     */
    public FormEditFeatures(Shell parent, int style)
    {
        super(parent, style);
        setText(WINDOW_TITLE);

        _editFeatureController = new EditFeatureController();
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

        _editFeatureController.load(_tableFeatures);

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

        Button btnNewFeature = new Button(shell, SWT.NONE);
        btnNewFeature.addSelectionListener(new SelectionAdapter()
        {
            @Override
            public void widgetSelected(SelectionEvent arg0)
            {
                createNewFeature();
            }
        });
        btnNewFeature.setBounds(10, 10, 139, 36);
        btnNewFeature.setText("New Feature");
        btnNewFeature.setImage(SWTResourceManager.getImage(Constants.ICO_ADD));

        Button btnCancel = new Button(shell, SWT.NONE);
        btnCancel.setBounds(659, 508, 94, 36);
        btnCancel.setText("Cancel");
        btnCancel.addSelectionListener(new SelectionAdapter()
        {
            @Override
            public void widgetSelected(SelectionEvent arg0)
            {
                close();
            }
        });

        Button btnSave = new Button(shell, SWT.NONE);
        btnSave.addSelectionListener(new SelectionAdapter()
        {
            @Override
            public void widgetSelected(SelectionEvent arg0)
            {
                apply();
            }
        });
        btnSave.setBounds(559, 508, 94, 36);
        btnSave.setText("Save");
        btnSave.setImage(SWTResourceManager.getImage(Constants.ICO_AK_SAVE));

        Composite composite = new Composite(shell, SWT.BORDER);
        composite.setBounds(10, 359, 743, 143);

        Label lblName = new Label(composite, SWT.NONE);
        lblName.setBounds(10, 10, 79, 20);
        lblName.setText("Name");

        _textName = new Text(composite, SWT.BORDER);
        _textName.setBounds(94, 10, 233, 37);

        Label lblDescription = new Label(composite, SWT.NONE);
        lblDescription.setBounds(333, 10, 79, 20);
        lblDescription.setText("Description");

        _textDescription = new StyledText(composite, SWT.BORDER | SWT.WRAP);
        _textDescription.setBounds(418, 10, 315, 123);

        _btnDelete = new Button(composite, SWT.NONE);
        _btnDelete.addSelectionListener(new SelectionAdapter()
        {
            @Override
            public void widgetSelected(SelectionEvent arg0)
            {
                delete();
            }
        });
        _btnDelete.setBounds(318, 97, 94, 36);
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
        _btnConfirm.setBounds(218, 97, 94, 36);
        _btnConfirm.setText("Confirm");
        _btnConfirm.setImage(SWTResourceManager.getImage(Constants.ICO_CONFIRM));

        _textAssociation = new Text(composite, SWT.BORDER);
        _textAssociation.setBounds(94, 53, 233, 37);

        Label lblAssociation = new Label(composite, SWT.NONE);
        lblAssociation.setBounds(10, 53, 78, 20);
        lblAssociation.setText("Association");

        _tableFeatures = new Table(shell, SWT.BORDER | SWT.FULL_SELECTION);
        _tableFeatures.setBounds(10, 52, 743, 301);
        _tableFeatures.setHeaderVisible(true);
        _tableFeatures.setLinesVisible(true);
        _tableFeatures.addListener(SWT.Selection, new Listener()
        {
            public void handleEvent(Event e)
            {
                selectTableElement();
            }

        });

        TableColumn tblclmnName = new TableColumn(_tableFeatures, SWT.NONE);
        tblclmnName.setWidth(342);
        tblclmnName.setText("Name");

        TableColumn tblclmnAssociation = new TableColumn(_tableFeatures, SWT.NONE);
        tblclmnAssociation.setWidth(150);
        tblclmnAssociation.setText("Association");

        TableColumn tblclmnDescription = new TableColumn(_tableFeatures, SWT.NONE);
        tblclmnDescription.setWidth(100);
        tblclmnDescription.setText("Description");

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
        
        setPanelEnabled(false);
    }

    private void selectTableElement()
    {
        ShowFeatureParameter p = _editFeatureController.new ShowFeatureParameter(_textName, _textAssociation,
                _textDescription);

        if (_editFeatureController.showSelection(_tableFeatures.getSelection(), p))
            setPanelEnabled(true);
    }

    private void setPanelEnabled(boolean enabled)
    {
        _textName.setEnabled(enabled);
        _textAssociation.setEnabled(enabled);
        _textDescription.setEnabled(enabled);

        _btnConfirm.setEnabled(enabled);
        _btnDelete.setEnabled(enabled);

        if (!enabled)
            clearSelection();
    }

    private void clearSelection()
    {
        _textName.setText(Constants.STRING_EMPTY);
        _textAssociation.setText(Constants.STRING_EMPTY);
        _textDescription.setText(Constants.STRING_EMPTY);
    }

    private void createNewFeature()
    {
        _editFeatureController.createNew();
        clearSelection();
        setPanelEnabled(true);
    }

    private void apply()
    {
        _editFeatureController.apply();
        shell.close();
    }

    private void delete()
    {
        _editFeatureController.deleteCurrentSelection();
        _editFeatureController.load(_tableFeatures);
        setPanelEnabled(false);
    }

    private void confirm()
    {
        SaveFeatureParameter p = _editFeatureController.new SaveFeatureParameter(_textName.getText(),
                _textAssociation.getText(), _textDescription.getText());

        _editFeatureController.saveSelection(p);
        _editFeatureController.load(_tableFeatures);
        setPanelEnabled(false);
    }

    private void close()
    {
        _editFeatureController.abort();
        shell.close();
    }
}
