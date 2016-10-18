package de.uni.hamburg.swk.extractor.gui.controller.utils;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

public class MessageController
{
    public static int show(String title, String message, int options)
    {
        MessageBox m = new MessageBox(new Shell(Display.getCurrent()), options);
        m.setMessage(message);
        m.setText(title);
        return m.open();
    }
}