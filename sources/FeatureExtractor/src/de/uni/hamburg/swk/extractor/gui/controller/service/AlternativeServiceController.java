package de.uni.hamburg.swk.extractor.gui.controller.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import de.uni.hamburg.swk.extractor.configuration.Configuration;
import de.uni.hamburg.swk.extractor.database.dao.impl.ResultSetDAO;
import de.uni.hamburg.swk.extractor.database.dao.impl.TechnologySolutionDAO;
import de.uni.hamburg.swk.extractor.database.entities.ak.Feature;
import de.uni.hamburg.swk.extractor.database.entities.ak.TechnologySolution;
import de.uni.hamburg.swk.extractor.database.entities.alternatives.Alternative;
import de.uni.hamburg.swk.extractor.database.entities.result.ResultSet;
import de.uni.hamburg.swk.extractor.gui.controller.AbstractTableController;
import de.uni.hamburg.swk.extractor.service.alternatives.AlternativeService;
import de.uni.hamburg.swk.extractor.utils.Constants;

public class AlternativeServiceController extends AbstractTableController
{
    private static final String S_S_FEATURES_USED = "%s (%s Features used)";
    private static final int COL_WIDTH = 650;
    
    private Map<TechnologySolution, Set<Feature>> _technologiesUsed;
    private java.util.List<Alternative> _alternatives;

    /**
     * 
     */
    public void startFindAlternatives()
    {
        AlternativeService alternativeService = new AlternativeService();

        _alternatives = alternativeService.findAlternatives(Configuration.getSelectedProject());
    }

    /**
     * 
     * @param listAlternatives
     */
    public void displayTechnologiesUsed(List listAlternatives)
    {
        listAlternatives.removeAll();

        if (Configuration.getSelectedProject() == null)
            return;

        java.util.List<ResultSet> res = new ResultSetDAO().getByProject(Configuration.getSelectedProject());

        _technologiesUsed = initializeTechnologiesUsed(res);

        for (TechnologySolution t : _technologiesUsed.keySet())
        {
            listAlternatives.add(
                    String.format(Constants.FORMAT_DISPLAY_TECHNOLOGY, t.getName(), _technologiesUsed.get(t).size()));
        }
    }

    public void displayAlternativesForTechnology(Table table, String[] selection, Label label)
    {
        clearAllTable(table);

        if (selection.length < 1)
            return; // No selection

        // Yeah, bad bad stuff calling it by its name I know...
        String selectedTechnology = selection[0];

        TechnologySolution t = new TechnologySolutionDAO().getByName(selectedTechnology);

        // System.out.println(_alternatives.size());
        java.util.List<Alternative> alternatives = _alternatives.stream().filter(a -> a.getIsAlternativeTo().equals(t))
                .collect(Collectors.toList());

        label.setText(String.format(S_S_FEATURES_USED, t.getName(), _technologiesUsed.get(t).size()));

        int colWidth = COL_WIDTH;

        if (alternatives.size() > 0)
            colWidth /= alternatives.size();

        for (int i = 0; i < alternatives.size() + 1; i++)
        {
            TableColumn col = new TableColumn(table, i == 0 ? SWT.LEAD : SWT.NONE);
            col.setWidth(i == 0 ? 260 : colWidth);
            col.setAlignment(i == 0 ? SWT.LEFT : SWT.CENTER);
            col.setText(i != 0 ? String.format(Constants.FORMAT_COVERAGE,
                    alternatives.get(i - 1).getTechnology().getName(), alternatives.get(i - 1).getCoverage()) : "");
            col.setData(i != 0 ? alternatives.get(i - 1).getTechnology() : t);
            col.setToolTipText(i != 0 ? alternatives.get(i - 1).getTechnology().getName() : "");
            col.setResizable(i != 0 ? false : true);
        }

        for (Feature f : _technologiesUsed.get(t))
        {
            if (f == null)
                continue;

            String[] values = new String[alternatives.size() + 1];

            values[0] = f.getName();

            for (int i = 1; i < alternatives.size() + 1; ++i)
            {
                values[i] = alternatives.get(i - 1).getFeaturesImplemeted().contains(f) ? Constants.FEATURE_IMPLEMENTED
                        : Constants.FEATURE_NOT_IMPLEMENTED;
            }

            TableItem tableItem = new TableItem(table, SWT.NONE);
            tableItem.setData(f);
            tableItem.setText(values);
        }
    }

    private Map<TechnologySolution, Set<Feature>> initializeTechnologiesUsed(java.util.List<ResultSet> res)
    {
        Map<TechnologySolution, Set<Feature>> ret = new HashMap<>();

        for (ResultSet r : res)
        {
            if (ret.containsKey(r.getTechnologyFeature().getBelongsTo()))
            {
                Set<Feature> feat = ret.get(r.getTechnologyFeature().getBelongsTo());
                feat.add(r.getTechnologyFeature().getFeatureImplemented());

                ret.put(r.getTechnologyFeature().getBelongsTo(), feat);
            }
            else
            {
                Set<Feature> feat = new HashSet<>();
                feat.add(r.getTechnologyFeature().getFeatureImplemented());

                ret.put(r.getTechnologyFeature().getBelongsTo(), feat);
            }
        }

        return ret;
    }
}
