package de.uni.hamburg.swk.extractor.gui.callback.impl;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ProgressBar;

import de.uni.hamburg.swk.extractor.gui.callback.Callback;

public class CallbackProgress extends Callback
{
    ProgressBar _bar;

    public CallbackProgress(ProgressBar bar)
    {
        _bar = bar;
    }

    @Override
    public void exec(Object[] args)
    {
        if (args.length < 2)
            throw new IllegalArgumentException(String.format("Callback for %s is missing arguments (%s, should be 2)",
                    CallbackProgress.class.getName(), args.length));

        int progress = (int) args[0];
        int max = (int) args[1];

        Display.getDefault().asyncExec(new Runnable()
        {
            public void run()
            {
                _bar.setMaximum(max);
                _bar.setSelection(progress);
                _bar.setToolTipText(String.format("%s/%s", progress, max));
            }
        });
    }
}
