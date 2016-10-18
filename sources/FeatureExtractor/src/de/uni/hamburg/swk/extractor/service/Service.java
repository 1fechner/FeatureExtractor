package de.uni.hamburg.swk.extractor.service;

import de.uni.hamburg.swk.extractor.configuration.Configuration;
import de.uni.hamburg.swk.extractor.gui.callback.CallbackRegistry;
import de.uni.hamburg.swk.extractor.gui.callback.CallbackType;

public class Service
{
    protected void print(String str)
    {
        if (Configuration.isVerbose())
            System.out.println(str);
        
        CallbackRegistry.invoke(CallbackType.OUTPUT, new Object[] { str }, this.getClass());
    }
}