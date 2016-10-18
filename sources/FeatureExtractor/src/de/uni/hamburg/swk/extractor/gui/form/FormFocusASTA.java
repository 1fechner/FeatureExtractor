package de.uni.hamburg.swk.extractor.gui.form;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import de.uni.hamburg.swk.extractor.database.dao.impl.ASTADAO;
import de.uni.hamburg.swk.extractor.database.entities.ak.ASTA;
import de.uni.hamburg.swk.extractor.database.entities.ak.Feature;
import de.uni.hamburg.swk.extractor.database.entities.ak.TechnologySolution;

public class FormFocusASTA extends Dialog
{

    private static final String WINDOW_TITLE = "ASTAs for %s (%s)";
    protected Object result;
    protected Shell shell;
    
    private ASTADAO _astaDAO;
    
    private TechnologySolution _technology;
    private Feature _feature;
    private Table _tableASTA;

    /**
     * Create the dialog.
     * 
     * @param parent
     * @param style
     */
    public FormFocusASTA(Shell parent, int style, TechnologySolution technology, Feature feature)
    {
        super(parent, style);
        setText(String.format(WINDOW_TITLE, technology.getName(), feature.getName()));
        
        _technology = technology;
        _feature = feature;
        
        _astaDAO = new ASTADAO();
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
        
        loadData();
        
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

    private void loadData()
    {
        List<ASTA> relevantASTA = _astaDAO.getByTechnologyAndFeature(_technology.getId(), _feature.getId());
        
        for (ASTA asta : relevantASTA)
        {
            TableItem i = new TableItem(_tableASTA, 0);

            String[] attributes = new String[3];
            attributes[0] = asta.getContext();
            attributes[1] = asta.getDescription();
            attributes[2] = asta.getType().name();

            i.setText(attributes);
            i.setData(asta);
        }
    }

    /**
     * Create contents of the dialog.
     */
    private void createContents()
    {
        shell = new Shell(getParent(), getStyle());
        shell.setSize(938, 627);
        shell.setText(getText());

        Button btnClose = new Button(shell, SWT.NONE);
        btnClose.addSelectionListener(new SelectionAdapter()
        {
            @Override
            public void widgetSelected(SelectionEvent arg0)
            {
                close();
            }
        });
        btnClose.setBounds(830, 579, 94, 36);
        btnClose.setText("Close");
        
        _tableASTA = new Table(shell, SWT.BORDER | SWT.FULL_SELECTION);
        _tableASTA.setBounds(10, 10, 914, 563);
        _tableASTA.setHeaderVisible(true);
        
        TableColumn tblclmnContext = new TableColumn(_tableASTA, SWT.NONE);
        tblclmnContext.setWidth(321);
        tblclmnContext.setText("Context");
        
        TableColumn tblclmnDescription = new TableColumn(_tableASTA, SWT.NONE);
        tblclmnDescription.setWidth(491);
        tblclmnDescription.setText("Description");
        
        TableColumn tblclmnType = new TableColumn(_tableASTA, SWT.NONE);
        tblclmnType.setResizable(false);
        tblclmnType.setToolTipText("Benefit or Drawback");
        tblclmnType.setWidth(100);
        tblclmnType.setText("Type");

    }

    protected void close()
    {
        shell.close();
    }
}
