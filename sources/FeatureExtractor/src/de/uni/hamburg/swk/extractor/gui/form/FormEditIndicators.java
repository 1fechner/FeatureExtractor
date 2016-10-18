package de.uni.hamburg.swk.extractor.gui.form;

import org.eclipse.swt.SWT;
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
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import de.uni.hamburg.swk.extractor.database.entities.Confidence;
import de.uni.hamburg.swk.extractor.database.entities.IndicatorLanguage;
import de.uni.hamburg.swk.extractor.database.entities.IndicatorType;
import de.uni.hamburg.swk.extractor.database.entities.ak.TechnologyFeature;
import de.uni.hamburg.swk.extractor.gui.controller.edit.EditIndicatorController;
import de.uni.hamburg.swk.extractor.gui.controller.edit.EditIndicatorController.SaveIndicatorParameter;
import de.uni.hamburg.swk.extractor.gui.controller.edit.EditIndicatorController.ShowIndicatorParameter;
import de.uni.hamburg.swk.extractor.utils.Constants;

public class FormEditIndicators extends Dialog
{
    private static final String TITLE = "Edit Indicators for %s";

    private TechnologyFeature _parent;

    private EditIndicatorController _editIndicatorController;

    protected Object result;
    protected Shell shell;
    private Table _tableIndicators;
    private Text textValue;
    private Text textParameter;
    private Text textScope;
    private Button _btnConfirm;
    private Button _btnDelete;
    private Combo comboType;
    private Combo comboConfidence;

    private Combo comboLanguage;

    /**
     * Create the dialog.
     * 
     * @param parent
     * @param style
     */
    public FormEditIndicators(Shell parent, int style, TechnologyFeature feat)
    {
        super(parent, style);
        setText(String.format(TITLE, feat.getName()));

        _parent = feat;
        _editIndicatorController = new EditIndicatorController(_parent);
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

        _editIndicatorController.load(_tableIndicators);
        _editIndicatorController.loadCombo(comboType, comboConfidence, comboLanguage);

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

        Button btnCancel = new Button(shell, SWT.NONE);
        btnCancel.addSelectionListener(new SelectionAdapter()
        {
            @Override
            public void widgetSelected(SelectionEvent arg0)
            {
                cancel();
            }
        });
        btnCancel.setBounds(659, 564, 94, 36);
        btnCancel.setText("Cancel");

        Button btnSave = new Button(shell, SWT.NONE);
        btnSave.addSelectionListener(new SelectionAdapter()
        {
            @Override
            public void widgetSelected(SelectionEvent arg0)
            {
                apply();
            }
        });
        btnSave.setBounds(559, 564, 94, 36);
        btnSave.setText("Save");
        btnSave.setImage(SWTResourceManager.getImage(Constants.ICO_AK_SAVE));

        Button btnNewIndicator = new Button(shell, SWT.NONE);
        btnNewIndicator.addSelectionListener(new SelectionAdapter()
        {
            @Override
            public void widgetSelected(SelectionEvent arg0)
            {
                createNewIndicator();
            }
        });
        btnNewIndicator.setBounds(10, 10, 129, 36);
        btnNewIndicator.setText("New Indicator");
        btnNewIndicator.setImage(SWTResourceManager.getImage(Constants.ICO_ADD));

        _tableIndicators = new Table(shell, SWT.BORDER | SWT.FULL_SELECTION);
        _tableIndicators.addSelectionListener(new SelectionAdapter()
        {
            @Override
            public void widgetSelected(SelectionEvent arg0)
            {
                selectTableElement();
            }
        });
        _tableIndicators.setBounds(10, 51, 743, 303);
        _tableIndicators.setHeaderVisible(true);
        _tableIndicators.setLinesVisible(true);

        TableColumn tblclmnType = new TableColumn(_tableIndicators, SWT.NONE);
        tblclmnType.setWidth(100);
        tblclmnType.setText("Type");

        TableColumn tblclmnLanguage = new TableColumn(_tableIndicators, SWT.NONE);
        tblclmnLanguage.setWidth(100);
        tblclmnLanguage.setText("Language");

        TableColumn tblclmnValue = new TableColumn(_tableIndicators, SWT.NONE);
        tblclmnValue.setWidth(163);
        tblclmnValue.setText("Value");

        TableColumn tblclmnParameter = new TableColumn(_tableIndicators, SWT.NONE);
        tblclmnParameter.setWidth(143);
        tblclmnParameter.setText("Parameter");

        TableColumn tblclmnScope = new TableColumn(_tableIndicators, SWT.NONE);
        tblclmnScope.setWidth(139);
        tblclmnScope.setText("Scope");

        TableColumn tblclmnConfidence = new TableColumn(_tableIndicators, SWT.NONE);
        tblclmnConfidence.setWidth(101);
        tblclmnConfidence.setText("Confidence");

        Composite composite = new Composite(shell, SWT.BORDER);
        composite.setBounds(10, 360, 743, 198);

        _btnConfirm = new Button(composite, SWT.NONE);
        _btnConfirm.addSelectionListener(new SelectionAdapter()
        {
            @Override
            public void widgetSelected(SelectionEvent arg0)
            {
                confirm();
            }
        });
        _btnConfirm.setBounds(10, 150, 94, 36);
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
        _btnDelete.setBounds(110, 150, 94, 36);
        _btnDelete.setText("Delete");
        _btnDelete.setImage(SWTResourceManager.getImage(Constants.ICO_DELETE));

        comboType = new Combo(composite, SWT.READ_ONLY);
        comboType.setBounds(95, 10, 197, 37);

        Label lblType = new Label(composite, SWT.NONE);
        lblType.setBounds(10, 10, 42, 20);
        lblType.setText("Type");

        Label lblValue = new Label(composite, SWT.NONE);
        lblValue.setBounds(298, 10, 69, 20);
        lblValue.setText("Value");

        textValue = new Text(composite, SWT.BORDER);
        textValue.setBounds(373, 10, 360, 37);

        textParameter = new Text(composite, SWT.BORDER);
        textParameter.setBounds(373, 53, 360, 37);

        textScope = new Text(composite, SWT.BORDER);
        textScope.setBounds(373, 96, 360, 37);

        Label lblScope = new Label(composite, SWT.NONE);
        lblScope.setBounds(298, 91, 69, 20);
        lblScope.setText("Scope");

        Label lblParameter = new Label(composite, SWT.NONE);
        lblParameter.setBounds(298, 54, 69, 20);
        lblParameter.setText("Parameter");

        comboConfidence = new Combo(composite, SWT.READ_ONLY);
        comboConfidence.setBounds(95, 53, 197, 37);

        Label lblConfidence = new Label(composite, SWT.NONE);
        lblConfidence.setBounds(10, 53, 79, 20);
        lblConfidence.setText("Confidence");

        comboLanguage = new Combo(composite, SWT.READ_ONLY);
        comboLanguage.setEnabled(false);
        comboLanguage.setBounds(95, 96, 197, 37);

        Label lblLanguage = new Label(composite, SWT.NONE);
        lblLanguage.setText("Language");
        lblLanguage.setBounds(10, 96, 79, 20);

        setPanelEnabled(false);
    }

    private void selectTableElement()
    {
        ShowIndicatorParameter p = _editIndicatorController.new ShowIndicatorParameter(comboType, textValue,
                textParameter, textScope, comboConfidence, comboLanguage);

        if (_editIndicatorController.showSelection(_tableIndicators.getSelection(), p))
            setPanelEnabled(true);
    }

    private void setPanelEnabled(boolean enabled)
    {
        comboType.setEnabled(enabled);
        textValue.setEnabled(enabled);
        textParameter.setEnabled(enabled);
        textScope.setEnabled(enabled);
        comboConfidence.setEnabled(enabled);
        comboLanguage.setEnabled(enabled);

        _btnConfirm.setEnabled(enabled);
        _btnDelete.setEnabled(enabled);

        if (!enabled)
            clearSelection();
    }

    private void clearSelection()
    {
        comboType.setText("undefined");
        textValue.setText(Constants.STRING_EMPTY);
        textParameter.setText(Constants.STRING_EMPTY);
        textScope.setText(Constants.STRING_EMPTY);
        comboConfidence.setText("VeryHigh");
        comboLanguage.setText("ANY");
    }

    private void cancel()
    {
        _editIndicatorController.abort();
        shell.close();
    }

    private void apply()
    {
        _editIndicatorController.apply();
        shell.close();
    }

    private void createNewIndicator()
    {
        _editIndicatorController.createNew();
        clearSelection();
        setPanelEnabled(true);
    }

    private void confirm()
    {
        IndicatorType t = IndicatorType.valueOf(comboType.getText());
        Confidence c = Confidence.valueOf(comboConfidence.getText());
        IndicatorLanguage l = IndicatorLanguage.valueOf(comboLanguage.getText());

        SaveIndicatorParameter p = _editIndicatorController.new SaveIndicatorParameter(t, textValue.getText(),
                textParameter.getText(), textScope.getText(), c, l);

        _editIndicatorController.saveSelection(p);
        _editIndicatorController.load(_tableIndicators);

        setPanelEnabled(false);
    }

    private void delete()
    {
        _editIndicatorController.deleteCurrentSelection();
        setPanelEnabled(false);
        _editIndicatorController.load(_tableIndicators);
    }
}
