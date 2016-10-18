package de.uni.hamburg.swk.extractor.startup.mode;

import de.uni.hamburg.swk.extractor.configuration.Configuration;
import de.uni.hamburg.swk.extractor.database.entities.result.Project;
import de.uni.hamburg.swk.extractor.service.extraction.ExtractionService;
import de.uni.hamburg.swk.extractor.utils.MessagesError;

public class ModeExtract extends Mode
{
    @Override
    public void execute(String[] args)
    {
        Project p = null;

        if (Configuration.getSelectedProject() != null)
        {
            p = Configuration.getSelectedProject();
        }
        else
        {
            System.out.println(MessagesError.NO_PROJECT_OR_SOURCE_TO_WORK_WITH);
            return;
        }

        if (p != null)
        {
            ExtractionService extr = new ExtractionService();
            extr.extract(p);
        }
        else
        {
            System.out.println(MessagesError.PROJECT_COULD_NOT_BE_INITIALIZED);
        }

    }
}