package de.uni.hamburg.swk.extractor.startup.mode;

import de.uni.hamburg.swk.extractor.configuration.Configuration;
import de.uni.hamburg.swk.extractor.service.alternatives.AlternativeService;
import de.uni.hamburg.swk.extractor.service.decisionpoints.DecisionPointService;

public class ModeAlternatives extends Mode
{
    @Override
    public void execute(String[] args)
    {
        AlternativeService alternativeService = new AlternativeService();
        DecisionPointService decisionPointService = new DecisionPointService();

        alternativeService.findAlternatives(Configuration.getSelectedProject());

        decisionPointService.findDecisionPoints();
    }
}