package de.uni.hamburg.swk.extractor.gui.controller.tree;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.wb.swt.SWTResourceManager;

import de.uni.hamburg.swk.extractor.configuration.Configuration;
import de.uni.hamburg.swk.extractor.database.dao.impl.ElementDAO;
import de.uni.hamburg.swk.extractor.database.dao.impl.ResultSetDAO;
import de.uni.hamburg.swk.extractor.database.dao.impl.TechnologyFeatureDAO;
import de.uni.hamburg.swk.extractor.database.entities.ak.TechnologyFeature;
import de.uni.hamburg.swk.extractor.database.entities.result.Element;
import de.uni.hamburg.swk.extractor.database.entities.result.ResultSet;
import de.uni.hamburg.swk.extractor.gui.callback.CallbackRegistry;
import de.uni.hamburg.swk.extractor.gui.callback.CallbackType;
import de.uni.hamburg.swk.extractor.gui.controller.AbstractTreeController;
import de.uni.hamburg.swk.extractor.utils.Constants;

public class FeatureTreeController
{

    private static final String FORMAT_ITEM = "%s (%s) - %s";
    private static final String FORMAT_ELEMENT_NAME_NO = "%s (%s)";
    private static final String FEATURES_LOADED = "Features loaded";
    private static final String PROJECT_S_HAS_NO_RESULTS_YET_PLEASE_EXECUTE_AN_EXTRACTION_FIRST = "Project %s has no results yet. Please execute an  extraction first";
    private static final String NO_PROJECT_SELECTED_OPEN_A_PROJECT_FIRST = "No Project selected. Open a project first.";
    private static final String LOADING = "Loading - This could take a while, get a coffee...";

    public void loadTree(Tree treeFeatures)
    {
        CallbackRegistry.invoke(CallbackType.STATUS, new Object[] { LOADING }, FeatureTreeController.class);

        treeFeatures.removeAll();

        if (Configuration.getSelectedProject() == null)
        {
            CallbackRegistry.invoke(CallbackType.STATUS, new Object[] { NO_PROJECT_SELECTED_OPEN_A_PROJECT_FIRST },
                    FeatureTreeController.class);
            return;
        }

        ElementDAO dao = new ElementDAO();

        java.util.List<Element> elements = dao.getRootForProject(Configuration.getSelectedProject());

        if (elements.size() <= 0)
        {
            CallbackRegistry.invoke(CallbackType.STATUS,
                    new Object[] { String.format(PROJECT_S_HAS_NO_RESULTS_YET_PLEASE_EXECUTE_AN_EXTRACTION_FIRST,
                            Configuration.getSelectedProject().getName()) },
                    FeatureTreeController.class);
            return;
        }

        TreeItem root = new TreeItem(treeFeatures, 0);
        root.setImage(SWTResourceManager.getImage(Constants.ICO_PROJECT));
        root.setText(Configuration.getSelectedProject().getName());

        Display.getDefault().asyncExec(new Runnable()
        {
            public void run()
            {
                int totalCnt = 0;
                
                for (Element e : elements)
                {
                    TreeItem i = new TreeItem(root, 0);

                    totalCnt += e.getNoOfFeatures();
                    i.setText(String.format(FORMAT_ELEMENT_NAME_NO, e.getName(), e.getNoOfFeatures()));
                    i.setData(e);
                    i.setImage(AbstractTreeController.getFileImage(e.getFileType()));

                    loadChildren(i);
                }

                root.setText(String.format(FORMAT_ELEMENT_NAME_NO, Configuration.getSelectedProject().getName(), totalCnt));
                root.setExpanded(true);

                CallbackRegistry.invoke(CallbackType.STATUS, new Object[] { FEATURES_LOADED },
                        FeatureTreeController.class);
                CallbackRegistry.invoke(CallbackType.WORK_FINISHED, new Object[] {}, FeatureTreeController.class);
            }
        });
    }

    public void loadChildren(TreeItem parent)
    {
        Element e = (Element) parent.getData();

        java.util.List<Element> children = new ElementDAO().getChildrenFor(e.getId(),
                Configuration.getSelectedProject());

        for (Element el : children)
        {
            TreeItem i = new TreeItem(parent, 0);

            i.setText(String.format(FORMAT_ELEMENT_NAME_NO, el.getName(), el.getNoOfFeatures()));
            i.setData(el);
            i.setImage(AbstractTreeController.getFileImage(el.getFileType()));

            loadChildren(i);
        }
    }

    public java.util.List<TechnologyFeature> getForElement(Element e)
    {
        TechnologyFeatureDAO dao = new TechnologyFeatureDAO();

        return e.isDirectory() ? dao.getForPackage(e.getPackage()) : dao.getByBelongsTo(e.getId());
    }

    public void selectItem(TreeItem[] selection, Label lblElementName, List listElementFeatures)
    {
        if (selection.length <= 0 || selection[0].getData() == null)
        {
            lblElementName.setText("");
            listElementFeatures.removeAll();
            return;
        }

        Element e = (Element) selection[0].getData();

        listElementFeatures.removeAll();

        java.util.List<ResultSet> res = new ResultSetDAO().getByBelongsTo(e.getId());

        lblElementName.setText(String.format(FORMAT_ELEMENT_NAME_NO, e.getName(), e.getNoOfFeatures()));

        for (ResultSet r : res)
        {
            listElementFeatures.add(String.format(FORMAT_ITEM, r.getTechnologyFeature().getName(),
                    r.getTechnologyFeature().getBelongsTo().getName(), r.getConfidence()));
        }
    }
}
