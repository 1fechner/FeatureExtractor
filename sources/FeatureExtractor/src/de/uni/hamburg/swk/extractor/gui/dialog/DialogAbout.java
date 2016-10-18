package de.uni.hamburg.swk.extractor.gui.dialog;

import java.awt.Desktop;
import java.net.URL;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;

import de.uni.hamburg.swk.extractor.configuration.Configuration;
import de.uni.hamburg.swk.extractor.utils.Constants;
import de.uni.hamburg.swk.extractor.utils.Messages;

public class DialogAbout extends Dialog
{
    private static final String WINDOW_TITLE = "About";
    
    protected Object result;
    protected Shell shell;

    /**
     * Create the dialog.
     * 
     * @param parent
     * @param style
     */
    public DialogAbout(Shell parent, int style)
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
        shell.setSize(450, 359);
        shell.setText(getText());

        Button btnClose = new Button(shell, SWT.NONE);
        btnClose.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseDown(MouseEvent arg0)
            {
                closeWindow();
            }
        });
        btnClose.setBounds(342, 311, 94, 36);
        btnClose.setText("Close");

        Label lblName = new Label(shell, SWT.NONE);
        lblName.setFont(SWTResourceManager.getFont("Cantarell", 13, SWT.NORMAL));
        lblName.setBounds(10, 10, 127, 23);
        lblName.setText("FeatureExtractor");

        Label lblVersion = new Label(shell, SWT.NONE);
        lblVersion.setFont(SWTResourceManager.getFont("Cantarell", 8, SWT.NORMAL));
        lblVersion.setBounds(384, 10, 52, 15);
        lblVersion.setText(String.format("Version %s", Configuration.getVersion()));

        Label lblIconsByHttpwwwfatcowcom = new Label(shell, SWT.WRAP);
        lblIconsByHttpwwwfatcowcom.setBounds(10, 260, 54, 20);
        lblIconsByHttpwwwfatcowcom.setText("Icons by");

        Link linkCow = new Link(shell, SWT.NONE);
        linkCow.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseDoubleClick(MouseEvent arg0)
            {
                openURLinBrowser(Constants.FATCOW_URL);
            }
        });
        linkCow.setBounds(70, 260, 145, 20);
        linkCow.setText("<a href=\"http://www.fatcow.com/free-icons\" >FatCow Web Hosting</a>");

        Link linkLicense = new Link(shell, 0);
        linkLicense.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseDoubleClick(MouseEvent arg0)
            {
                openURLinBrowser(Constants.URL_CREATIVE_COMMONS_3);
            }
        });
        linkLicense.setText(
                "<a href=\"https://creativecommons.org/licenses/by/3.0/us/\" >Creative Commons (Attribution 3.0 United States)</a>");
        linkLicense.setBounds(10, 286, 327, 20);

        Label lblUnder = new Label(shell, SWT.NONE);
        lblUnder.setBounds(214, 260, 106, 20);
        lblUnder.setText("under licence by");

        Label lblNewLabel = new Label(shell, SWT.BORDER | SWT.WRAP);
        lblNewLabel.setBounds(10, 65, 426, 189);
        lblNewLabel.setText(
                Messages.ABOUT_TEXT);

        Label lblTobiasFechneruni = new Label(shell, SWT.NONE);
        lblTobiasFechneruni.setText(Messages.ABOUT_NAME);
        lblTobiasFechneruni.setBounds(10, 39, 426, 20);
    }

    private void closeWindow()
    {
        shell.close();
    }

    private static void openURLinBrowser(String url)
    {
        try
        {
            Desktop.getDesktop().browse(new URL(url).toURI());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
