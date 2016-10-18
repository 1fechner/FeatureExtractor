package de.uni.hamburg.swk.extractor.gui.controller.edit;

import java.util.Comparator;
import java.util.List;

import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import de.uni.hamburg.swk.extractor.database.SessionService;
import de.uni.hamburg.swk.extractor.database.dao.impl.FeatureDAO;
import de.uni.hamburg.swk.extractor.database.dao.impl.TechnologyFeatureDAO;
import de.uni.hamburg.swk.extractor.database.entities.CapabilityType;
import de.uni.hamburg.swk.extractor.database.entities.ak.Feature;
import de.uni.hamburg.swk.extractor.database.entities.ak.TechnologyFeature;
import de.uni.hamburg.swk.extractor.database.entities.ak.TechnologySolution;
import de.uni.hamburg.swk.extractor.gui.controller.AbstractKnowledgeBaseEditController;
import de.uni.hamburg.swk.extractor.utils.Constants;

public class EditTechnologyFeatureController extends AbstractKnowledgeBaseEditController<TechnologyFeature>
{
    private TechnologySolution _parent;

    private TechnologyFeatureDAO _technologyFeatureDAO;
    private FeatureDAO _featureDAO;

    public EditTechnologyFeatureController(TechnologySolution parent)
    {
        _parent = parent;
        _technologyFeatureDAO = new TechnologyFeatureDAO();
        _featureDAO = new FeatureDAO();
    }

    @Override
    public void load(Table table)
    {
        clearContentsTable(table);

        List<TechnologyFeature> technologyFeatures = _technologyFeatureDAO.getByBelongsTo(_parent.getId());

        for (TechnologyFeature t : technologyFeatures)
        {
            TableItem i = new TableItem(table, 0);

            String[] attributes = new String[4];
            attributes[0] = t.getName();
            attributes[1] = t.getDependsOn() != null ? t.getDependsOn().getName() : Constants.STRING_DIV;
            attributes[2] = t.getFeatureImplemented() != null ? t.getFeatureImplemented().getName()
                    : Constants.STRING_DIV;
            attributes[3] = t.getDescription();

            i.setText(attributes);
            i.setData(t);
        }
    }

    public void loadCombo(Combo dependsOn, Combo feature)
    {
        dependsOn.removeAll();
        feature.removeAll();
        
        // Load all possible dependencies. Only TechnologyFeatures that belong
        // to the parent are possoble (Creating integrity)
        List<TechnologyFeature> techFeat = _technologyFeatureDAO.getByBelongsTo(_parent.getId());
        techFeat.sort(Comparator.comparing(e -> e.getName()));

        dependsOn.add(Constants.STRING_DIV);
        for (TechnologyFeature t : techFeat)
        {
            dependsOn.add(t.getName());
        }

        // Load features to implement
        List<Feature> features = _featureDAO.getAll();
        features.sort(Comparator.comparing(e -> e.getName()));

        feature.add(Constants.STRING_DIV);
        for (Feature f : features)
        {
            feature.add(f.getName());
        }
    }

    @Override
    public TechnologyFeature instantiateNew()
    {
        TechnologyFeature ret = new TechnologyFeature();
        ret.setCapabilityType(CapabilityType.Undefined);
        return ret;
    }

    @Override
    public <P extends AbstractKnowledgeBaseEditController<TechnologyFeature>.SaveSelectionParameter> void saveSelection(
            P param)
    {
        SaveTechnologyFeatureParameter p = (SaveTechnologyFeatureParameter) param;

        if (!checkTransaction())
            return;

        _currentSelection.setName(p.Name);
        _currentSelection.setDependsOn(p.DependsOn);
        _currentSelection.setDescription(p.Description);
        _currentSelection.setFeatureImplemented(p.FeatureImplemented);
        _currentSelection.setBelongsTo(_parent);

        SessionService.getCurrentSession().save(_currentSelection);
    }

    @Override
    public <P extends AbstractKnowledgeBaseEditController<TechnologyFeature>.ShowSelectionParameter> boolean showSelection(
            TableItem[] selection, P param)
    {
        ShowTechnologyFeatureParameter p = (ShowTechnologyFeatureParameter) param;

        if (selection.length > 0)
        {
            TechnologyFeature t = (TechnologyFeature) selection[0].getData();

            _currentSelection = t;

            p.Name.setText(t.getName() != null ? t.getName() : Constants.STRING_EMPTY);
            p.DependsOn.setText(t.getDependsOn() != null ? t.getDependsOn().getName() : Constants.STRING_DIV);
            p.Description.setText(t.getDescription() != null ? t.getDescription() : Constants.STRING_EMPTY);
            p.FeatureImplemented.setText(
                    t.getFeatureImplemented() != null ? t.getFeatureImplemented().getName() : Constants.STRING_DIV);
            ;

            return true;
        }
        else
        {
            _currentSelection = null;
            p.Name.setText(Constants.STRING_EMPTY);
            p.DependsOn.setText(Constants.STRING_DIV);
            p.Description.setText(Constants.STRING_EMPTY);
            p.FeatureImplemented.setText(Constants.STRING_DIV);
            return false;
        }
    }

    public class SaveTechnologyFeatureParameter extends SaveSelectionParameter
    {
        public String Name;
        public TechnologyFeature DependsOn;
        public String Description;
        public Feature FeatureImplemented;

        public SaveTechnologyFeatureParameter(String name, TechnologySolution belongsTo, TechnologyFeature dependsOn,
                Feature featuresImplemented, String description)
        {
            this.Name = name;
            this.DependsOn = dependsOn;
            this.FeatureImplemented = featuresImplemented;
            this.Description = description;
        }
    }

    public class ShowTechnologyFeatureParameter extends ShowSelectionParameter
    {
        public Text Name;
        public Combo DependsOn;
        public Combo FeatureImplemented;
        public StyledText Description;

        public ShowTechnologyFeatureParameter(Text name, Combo dependsOn, Combo featureImplemented,
                StyledText description)

        {
            this.Name = name;
            this.DependsOn = dependsOn;
            this.FeatureImplemented = featureImplemented;
            this.Description = description;
        }
    }
}
