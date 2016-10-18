package de.uni.hamburg.swk.extractor.gui.controller.edit;

import java.util.List;

import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import de.uni.hamburg.swk.extractor.database.SessionService;
import de.uni.hamburg.swk.extractor.database.dao.impl.FeatureDAO;
import de.uni.hamburg.swk.extractor.database.entities.ak.Feature;
import de.uni.hamburg.swk.extractor.gui.controller.AbstractKnowledgeBaseEditController;
import de.uni.hamburg.swk.extractor.utils.Constants;

public class EditFeatureController extends AbstractKnowledgeBaseEditController<Feature>
{
    private FeatureDAO _featureDAO;

    public EditFeatureController()
    {
        super();

        _featureDAO = new FeatureDAO();
    }

    @Override
    public void load(Table table)
    {
        clearContentsTable(table);

        List<Feature> features = _featureDAO.getAll();

        for (Feature t : features)
        {
            TableItem i = new TableItem(table, 0);

            String[] attributes = new String[3];
            attributes[0] = t.getName();
            attributes[1] = t.getAssociation();
            attributes[2] = t.getDescription();

            i.setText(attributes);
            i.setData(t);
        }
    }

    @Override
    protected Feature instantiateNew()
    {
        Feature ret = new Feature();
        return ret;
    }

    @Override
    public <P extends AbstractKnowledgeBaseEditController<Feature>.SaveSelectionParameter> void saveSelection(P param)
    {
        SaveFeatureParameter p = (SaveFeatureParameter) param;

        if (!checkTransaction())
            return;

        _currentSelection.setName(p.Name);
        _currentSelection.setAssociation(p.Association);
        _currentSelection.setDescription(p.Description);

        SessionService.getCurrentSession().save(_currentSelection);
    }

    @Override
    public <P extends AbstractKnowledgeBaseEditController<Feature>.ShowSelectionParameter> boolean showSelection(
            TableItem[] selection, P param)
    {
        ShowFeatureParameter p = (ShowFeatureParameter) param;

        if (selection.length > 0)
        {
            Feature t = (Feature) selection[0].getData();

            _currentSelection = t;

            p.Name.setText(t.getName() != null ? t.getName() : Constants.STRING_EMPTY);
            p.Association.setText(t.getAssociation() != null ? t.getAssociation() : Constants.STRING_EMPTY);
            p.Description.setText(t.getDescription() != null ? t.getDescription() : Constants.STRING_EMPTY);

            return true;
        }
        else
        {
            _currentSelection = null;
            p.Name.setText(Constants.STRING_EMPTY);
            p.Association.setText(Constants.STRING_EMPTY);
            p.Description.setText(Constants.STRING_EMPTY);

            return false;
        }
    }

    public class SaveFeatureParameter extends SaveSelectionParameter
    {
        public String Name;
        public String Association;
        public String Description;

        public SaveFeatureParameter(String name, String association, String description)
        {
            this.Name = name;
            this.Association = association;
            this.Description = description;
        }
    }

    public class ShowFeatureParameter extends ShowSelectionParameter
    {
        public Text Name;
        public Text Association;
        public StyledText Description;

        public ShowFeatureParameter(Text name, Text association, StyledText description)

        {
            this.Name = name;
            this.Association = association;
            this.Description = description;
        }
    }
}
