package de.uni.hamburg.swk.extractor.management;

import de.uni.hamburg.swk.extractor.database.SessionService;

public abstract class ManagementCommand
{
    public ManagementCommand()
    {
        SessionService.openSession();
    }

    public abstract void execute(String[] args);
}