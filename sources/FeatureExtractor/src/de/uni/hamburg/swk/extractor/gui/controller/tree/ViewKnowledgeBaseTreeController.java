package de.uni.hamburg.swk.extractor.gui.controller.tree;

import java.util.List;

import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.wb.swt.SWTResourceManager;

import de.uni.hamburg.swk.extractor.database.dao.impl.IndicatorDAO;
import de.uni.hamburg.swk.extractor.database.dao.impl.TechnologyFeatureDAO;
import de.uni.hamburg.swk.extractor.database.dao.impl.TechnologySolutionDAO;
import de.uni.hamburg.swk.extractor.database.entities.ak.Indicator;
import de.uni.hamburg.swk.extractor.database.entities.ak.TechnologyFeature;
import de.uni.hamburg.swk.extractor.database.entities.ak.TechnologySolution;
import de.uni.hamburg.swk.extractor.gui.controller.AbstractTreeController;
import de.uni.hamburg.swk.extractor.utils.Constants;
import de.uni.hamburg.swk.extractor.utils.Messages;

public class ViewKnowledgeBaseTreeController extends AbstractTreeController
{
    public void load(Tree tree)
    {
        TreeItem root = new TreeItem(tree, 0);
        root.setImage(SWTResourceManager.getImage(Constants.ICO_AK));
        root.setText(Messages.FORM_VIEW_AK_ROOT_ELEMENT_NAME);

        root.setExpanded(true);

        List<TechnologySolution> technologies = new TechnologySolutionDAO().getTechnologySolutionsRoot();

        for (TechnologySolution t : technologies)
        {
            TreeItem c = new TreeItem(root, 0);

            c.setText(t.getName());
            c.setImage(AbstractTreeController.getTypeImage(Constants.ICO_TYPE_TECHNOLOGY));
            c.setData(t);

            loadChildrenTechnology(c);
        }
    }

    private void loadChildrenTechnology(TreeItem i)
    {
        TechnologySolution t = (TechnologySolution) i.getData();
        List<TechnologySolution> children = new TechnologySolutionDAO().getTechnologySolutionsChildren(t.getId());

        for (TechnologySolution c : children)
        {
            TreeItem ic = new TreeItem(i, 0);

            ic.setText(c.getName());
            ic.setImage(AbstractTreeController.getTypeImage(Constants.ICO_TYPE_TECHNOLOGY));
            ic.setData(c);

            loadChildrenTechnology(ic);
            loadTechnologyFeatures(ic);
        }
    }

    private void loadTechnologyFeatures(TreeItem i)
    {
        TechnologySolution t = (TechnologySolution) i.getData();

        List<TechnologyFeature> features = new TechnologyFeatureDAO().getRootFor(t.getId());

        for (TechnologyFeature f : features)
        {
            TreeItem c = new TreeItem(i, 0);
            c.setText(f.getName());
            c.setImage(AbstractTreeController.getTypeImage(Constants.ICO_TYPE_TECHNOLOGY_FEATURE));
            c.setData(f);

            loadChildrenTechnologyFeatures(c);
            loadIndicators(c);
        }
    }

    private void loadChildrenTechnologyFeatures(TreeItem i)
    {
        TechnologyFeature t = (TechnologyFeature) i.getData();

        List<TechnologyFeature> features = new TechnologyFeatureDAO().getChidrenFor(t.getId());

        for (TechnologyFeature f : features)
        {
            TreeItem c = new TreeItem(i, 0);
            c.setText(f.getName());
            c.setImage(AbstractTreeController.getTypeImage(Constants.ICO_TYPE_TECHNOLOGY_FEATURE));
            c.setData(f);

            loadChildrenTechnologyFeatures(c);
            loadIndicators(c);
        }
    }

    private void loadIndicators(TreeItem i)
    {
        TechnologyFeature t = (TechnologyFeature) i.getData();

        List<Indicator> indicators = new IndicatorDAO().getIndicatorsForFeature(t.getId());

        for (Indicator indic : indicators)
        {
            TreeItem c = new TreeItem(i, 0);
            c.setImage(AbstractTreeController.getTypeImage(Constants.ICO_TYPE_INDICATOR));
            c.setText(String.format(Messages.FORM_VIEW_AK_INDICATOR_TEXT, indic.getType().toString(),
                    indic.getIndicatorLanguage().name(), indic.getValue(), indic.getParameter(), indic.getScope()));
        }
    }
}
