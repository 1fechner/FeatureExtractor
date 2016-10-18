package de.uni.hamburg.swk.extractor.gui.callback.impl;

import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;

public class CallbackEnableControl extends CallbackWorkFinished
{
    private Control _control;
    private boolean _enable;

    public CallbackEnableControl(Control c, boolean enable)
    {
        _control = c;
        _enable = enable;
    }

    @Override
    public void exec(Object[] args)
    {
        Display.getDefault().asyncExec(new Runnable()
        {
            public void run()
            {
                _control.setEnabled(_enable);
            }
        });
    }
}
