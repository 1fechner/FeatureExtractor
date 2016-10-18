package de.uni.hamburg.swk.extractor.service.decisionpoints;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Transaction;

import de.uni.hamburg.swk.extractor.configuration.Configuration;
import de.uni.hamburg.swk.extractor.configuration.OptimizationRules;
import de.uni.hamburg.swk.extractor.database.SessionService;
import de.uni.hamburg.swk.extractor.database.dao.impl.AlternativeDAO;
import de.uni.hamburg.swk.extractor.database.dao.impl.DecisionPointDAO;
import de.uni.hamburg.swk.extractor.database.entities.ak.TechnologySolution;
import de.uni.hamburg.swk.extractor.database.entities.alternatives.Alternative;
import de.uni.hamburg.swk.extractor.database.entities.alternatives.DecisionPoint;
import de.uni.hamburg.swk.extractor.service.Service;
import de.uni.hamburg.swk.extractor.utils.Messages;
import de.uni.hamburg.swk.extractor.utils.MessagesError;

public class DecisionPointService extends Service
{
    private AlternativeDAO _alternativeDAO;
    private DecisionPointDAO _decisionPointDAO;

    public List<DecisionPoint> findDecisionPoints()
    {
        _alternativeDAO = new AlternativeDAO();
        _decisionPointDAO = new DecisionPointDAO();
        
        if (Configuration.getSelectedProject() == null)
        {
            print(MessagesError.PLEASE_SELECT_A_PROJECT_FIRST);
            return new ArrayList<>();
        }

        List<Alternative> alternatives = _alternativeDAO.getForProject(Configuration.getSelectedProject());

        List<DecisionPoint> decisionPoints = new ArrayList<DecisionPoint>();

        Transaction tx = SessionService.getCurrentSession().beginTransaction();

        _decisionPointDAO.deleteForProject(Configuration.getSelectedProject());

        for (Alternative alt : alternatives)
        {
            findDecisionPoint(alt);
        }

        tx.commit();

        return decisionPoints;
    }

    private void findDecisionPoint(Alternative alternative)
    {
        print(String.format(Messages.DECISIONPOINT_SEARCHING, alternative.getTechnology().getName(),
                alternative.getIsAlternativeTo().getName()));

        if (alternative.getTechnology().equals(alternative.getIsAlternativeTo()))
            print(Messages.DECISIONPOINT_TECHNOLOGIES_EQUAL);

        TechnologySolution it1 = alternative.getTechnology();
        TechnologySolution it2 = alternative.getIsAlternativeTo();

        int depthL = 1;
        int depthR = 1;
        int depth = 0;

        while (it1.getDependsOn() != null)
        {
            // Reset to only count path once
            depthL = 1;
            
            while (it2.getDependsOn() != null)
            {
                depth = depthL + depthR;

                if (depth <= Configuration.getSelectedProject().getMaxDependencyChain()
                        && it1.getDependsOn().equals(it2.getDependsOn()))
                {
                    print(String.format(Messages.DECISIONPOINT_MAKE_DECISION_AT, it1.getDependsOn().getName(), depth));
                    print(String.format(Messages.DECISIONPOINT_CONSIDER_CHANGING, it1.getName(), it2.getName()));

                    DecisionPoint d = new DecisionPoint();

                    d.setCommonRoot(it1.getDependsOn());
                    d.setAlternative(alternative);
                    d.setDependencyChainLength(depth);
                    d.setProject(Configuration.getSelectedProject());
                    d.setVersion(Configuration.getSelectedProject().getVersion());
                    SessionService.getCurrentSession().save(d);

                    if (OptimizationRules.R_401_WARN_MISSING_FEATURE && alternative.getCoverage() < 1.0)
                        print(String.format(Messages.DECISIONPOINT_WARNING_MISSING_FEATURE,
                                alternative.getTechnology().getName(), alternative.getCoverage()));

                    if (!OptimizationRules.R_400_COMPLETE_PATH)
                    {
                        return;
                    }

                }
                else if (depth > Configuration.getSelectedProject().getMaxDependencyChain())
                {
                    print("\tWARNING: Too many dependencies. Aborting search.");
                }
                depthL += 1;

                it2 = it2.getDependsOn();
            }

            depthR += 1;

            // Reset it2 to check the complete path again
            it2 = alternative.getIsAlternativeTo();

            it1 = it1.getDependsOn();
        }

        print("");
    }
}
