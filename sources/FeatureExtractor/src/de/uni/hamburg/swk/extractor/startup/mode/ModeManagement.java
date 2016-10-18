package de.uni.hamburg.swk.extractor.startup.mode;

import de.uni.hamburg.swk.extractor.management.ManagementCommandTikZ;
import de.uni.hamburg.swk.extractor.management.ManagementCommandViewFlat;

public class ModeManagement extends Mode
{
    @Override
    public void execute(String[] args)
    {
        new ManagementCommandViewFlat().execute(args);
        new ManagementCommandTikZ().execute(args);
    }
}