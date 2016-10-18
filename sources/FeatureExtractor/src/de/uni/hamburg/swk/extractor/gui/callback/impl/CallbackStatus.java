package de.uni.hamburg.swk.extractor.gui.callback.impl;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

import de.uni.hamburg.swk.extractor.gui.callback.Callback;

public class CallbackStatus extends Callback
{
    private Label _label;

    public CallbackStatus(Label label)
    {
        _label = label;
    }

    @Override
    public void exec(Object[] args)
    {
        String text = (String) args[0];

        Display.getDefault().asyncExec(new Runnable()
        {
            public void run()
            {
                _label.setText(text);
            }
        });
    }
}
