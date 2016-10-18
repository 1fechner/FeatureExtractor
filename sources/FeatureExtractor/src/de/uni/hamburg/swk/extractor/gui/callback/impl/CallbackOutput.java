package de.uni.hamburg.swk.extractor.gui.callback.impl;

import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Display;

import de.uni.hamburg.swk.extractor.gui.callback.Callback;

public class CallbackOutput extends Callback
{
    private StyledText _target;

    public CallbackOutput(StyledText target)
    {
        _target = target;
    }

    @Override
    public void exec(Object[] args)
    {
        String text = (String) args[0];

        Display.getDefault().asyncExec(new Runnable()
        {
            public void run()
            {
                _target.append(text + "\n");
                scrollToEnd();
            }
        });
    }

    private void scrollToEnd()
    {
        _target.setTopIndex(_target.getLineCount() - 1);
    }
}
