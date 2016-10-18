package de.uni.hamburg.swk.extractor.gui.controller.service;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.wb.swt.SWTResourceManager;

import de.uni.hamburg.swk.extractor.configuration.Configuration;
import de.uni.hamburg.swk.extractor.configuration.OptimizationRules;
import de.uni.hamburg.swk.extractor.database.dao.impl.DecisionPointDAO;
import de.uni.hamburg.swk.extractor.database.entities.ak.TechnologySolution;
import de.uni.hamburg.swk.extractor.database.entities.alternatives.DecisionPoint;
import de.uni.hamburg.swk.extractor.gui.controller.AbstractTreeController;
import de.uni.hamburg.swk.extractor.service.decisionpoints.DecisionPointService;
import de.uni.hamburg.swk.extractor.utils.Constants;
import de.uni.hamburg.swk.extractor.utils.Messages;

public class DecisionPointServiceController extends AbstractTreeController
{
    public void startFindDecisionpoints()
    {
        DecisionPointService decisionPointService = new DecisionPointService();
        decisionPointService.findDecisionPoints();
    }

    public void fill(Tree _treeDecisionPoints)
    {
        if (Configuration.getSelectedProject() == null)
            return;

        _treeDecisionPoints.removeAll();

        TreeItem root = new TreeItem(_treeDecisionPoints, 0);
        root.setText(Configuration.getSelectedProject().getName());
        root.setImage(SWTResourceManager.getImage(Constants.ICO_PROJECT));

        List<DecisionPoint> desc = new DecisionPointDAO().getAllForProject(Configuration.getSelectedProject());

        for (DecisionPoint d : desc)
        {
            TreeItem item = new TreeItem(root, 0);
            item.setText(String.format(Messages.DECISIONPOINT_REPLACE, d.getAlternative().getIsAlternativeTo()
                    .getName(), d.getAlternative().getTechnology().getName()));
            item.setImage(SWTResourceManager.getImage(Constants.ICO_ALTERNATIVE));

            TreeItem dp = new TreeItem(item, 0);
            dp.setText(String.format(Messages.DECISIONPOINT_CONSIDER, d.getCommonRoot().getName(),
                    d.getDependencyChainLength()));
            dp.setImage(SWTResourceManager.getImage(Constants.ICO_WARNING));

            TreeItem path = new TreeItem(item, 0);
            path.setText(String.format(Messages.DECISIONPOINT_PATH));
            path.setImage(SWTResourceManager.getImage(Constants.ICO_WARNING));

            attachPath(path, d);

            if (OptimizationRules.R_401_WARN_MISSING_FEATURE && d.getAlternative().getCoverage() < 1.0f)
            {
                TreeItem missing = new TreeItem(item, 0);
                missing.setText(String.format(Messages.DECISIONPOINT_WARNING_MISSING_FEATURE, d.getAlternative()
                        .getTechnology().getName(), d.getAlternative().getCoverage()));
                missing.setImage(SWTResourceManager.getImage(Constants.ICO_WARNING));
            }
        }

        root.setExpanded(true);
    }

    private void attachPath(TreeItem path, DecisionPoint point)
    {
        List<TechnologySolution> alongPathA = new ArrayList<TechnologySolution>();
        List<TechnologySolution> alongPathB = new ArrayList<TechnologySolution>();
        List<TechnologySolution> both = new ArrayList<TechnologySolution>();

        // Gather both paths

        TechnologySolution techA = point.getAlternative().getTechnology();
        TechnologySolution techB = point.getAlternative().getIsAlternativeTo();

        both.add(techA);
        both.add(techB);

        TechnologySolution p;

        for (int i = 0; i < 2; ++i)
        {
            p = both.get(i);

            while (p.getDependsOn() != null && !p.getDependsOn().equals(point.getCommonRoot()))
            {
                p = p.getDependsOn();

                if (i == 0)
                    alongPathA.add(p);
                else
                    alongPathB.add(p);
            }
        }

        // Path A

        TreeItem itemA = new TreeItem(path, 0);
        itemA.setText(techA.getName());

        TreeItem rootA = new TreeItem(itemA, 0);
        rootA.setText(point.getCommonRoot().getName());

        for (TechnologySolution t : alongPathA)
        {
            TreeItem item = new TreeItem(itemA, 0);
            item.setText(t.getName());
        }

        TreeItem leafA = new TreeItem(itemA, 0);
        leafA.setText(techA.getName());

        // Path B

        TreeItem itemB = new TreeItem(path, 0);
        itemB.setText(techB.getName());

        TreeItem rootB = new TreeItem(itemB, 0);
        rootB.setText(point.getCommonRoot().getName());

        for (TechnologySolution t : alongPathB)
        {
            TreeItem item = new TreeItem(itemB, 0);
            item.setText(t.getName());
        }

        TreeItem leafB = new TreeItem(itemB, 0);
        leafB.setText(techB.getName());
    }
}