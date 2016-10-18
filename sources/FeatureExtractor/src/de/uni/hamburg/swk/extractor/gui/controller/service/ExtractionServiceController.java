package de.uni.hamburg.swk.extractor.gui.controller.service;

import java.util.Observable;

import de.uni.hamburg.swk.extractor.configuration.Configuration;
import de.uni.hamburg.swk.extractor.gui.callback.CallbackRegistry;
import de.uni.hamburg.swk.extractor.gui.callback.CallbackType;
import de.uni.hamburg.swk.extractor.service.extraction.ExtractionService;

public class ExtractionServiceController extends Observable
{
    private Thread _treadExtract;

    public void startExtract()
    {
        _treadExtract = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                ExtractionService extr = new ExtractionService();
                extr.extract(Configuration.getSelectedProject());

                CallbackRegistry.invoke(CallbackType.WORK_FINISHED, new Object[] {}, ExtractionServiceController.class);
            }
        });

        _treadExtract.start();
    }

    @SuppressWarnings("deprecation")
    public void stopExtract()
    {
        if (_treadExtract != null && _treadExtract.isAlive())
            _treadExtract.stop();

        CallbackRegistry.invoke(CallbackType.WORK_FINISHED, new Object[] {}, ExtractionServiceController.class);
    }
}
