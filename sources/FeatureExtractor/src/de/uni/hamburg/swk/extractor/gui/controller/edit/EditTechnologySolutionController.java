package de.uni.hamburg.swk.extractor.gui.controller.edit;

import java.util.Comparator;
import java.util.List;

import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import de.uni.hamburg.swk.extractor.database.SessionService;
import de.uni.hamburg.swk.extractor.database.dao.impl.TechnologySolutionDAO;
import de.uni.hamburg.swk.extractor.database.entities.ak.TechnologySolution;
import de.uni.hamburg.swk.extractor.gui.controller.AbstractKnowledgeBaseEditController;
import de.uni.hamburg.swk.extractor.utils.Constants;

public class EditTechnologySolutionController extends AbstractKnowledgeBaseEditController<TechnologySolution>
{
    private TechnologySolutionDAO _technologySolutionDAO;

    public EditTechnologySolutionController()
    {
        _technologySolutionDAO = new TechnologySolutionDAO();
    }

    public void load(Table table)
    {
        clearContentsTable(table);

        List<TechnologySolution> technologies = _technologySolutionDAO.getAll();
        technologies.sort(Comparator.comparing(e -> e.getName()));

        for (TechnologySolution t : technologies)
        {
            TableItem i = new TableItem(table, 0);

            String[] attributes = new String[4];
            attributes[0] = t.getName();
            attributes[1] = t.getDependsOn() != null ? t.getDependsOn().getName() : Constants.STRING_DIV;
            attributes[2] = t.getDescription();
            attributes[3] = t.isEnabled() + Constants.STRING_EMPTY;

            i.setText(attributes);
            i.setData(t);
        }
    }

    /**
     * Load additional infomation to the given combo
     * 
     * @param dependsOn
     */
    public void loadCombo(Combo dependsOn)
    {
        dependsOn.removeAll();
        
        List<TechnologySolution> technologies = _technologySolutionDAO.getAll();
        technologies.sort(Comparator.comparing(e -> e.getName()));
        
        // Empty entry for no dependency (null in db)
        dependsOn.add(Constants.STRING_DIV);

        for (TechnologySolution t : technologies)
        {
            dependsOn.add(t.getName());
        }
    }

    @Override
    public TechnologySolution instantiateNew()
    {
        return new TechnologySolution();
    }

    @Override
    public <P extends AbstractKnowledgeBaseEditController<TechnologySolution>.SaveSelectionParameter> void saveSelection(
            P param)
    {
        SaveTechnologySolutionParameter p = (SaveTechnologySolutionParameter) param;

        if (!checkTransaction())
            return;

        _currentSelection.setName(p.Name);
        _currentSelection.setDependsOn(p.DependsOn);
        _currentSelection.setEnabled(p.Enabled);
        _currentSelection.setDescription(p.Description);

        SessionService.getCurrentSession().save(_currentSelection);
    }

    @Override
    public <P extends AbstractKnowledgeBaseEditController<TechnologySolution>.ShowSelectionParameter> boolean showSelection(
            TableItem[] selection, P param)
    {
        ShowTechnologySolutionParameter p = (ShowTechnologySolutionParameter) param;

        if (selection.length > 0)
        {
            TechnologySolution t = (TechnologySolution) selection[0].getData();

            _currentSelection = t;

            p.Name.setText(t.getName() != null ? t.getName() : Constants.STRING_EMPTY);
            p.DependsOn.setText(t.getDependsOn() != null ? t.getDependsOn().getName() : Constants.STRING_DIV);
            p.Description.setText(t.getDescription() != null ? t.getDescription() : Constants.STRING_EMPTY);
            p.Enabled.setSelection(t.isEnabled());

            return true;
        }
        else
        {
            _currentSelection = null;
            p.Name.setText(Constants.STRING_EMPTY);
            p.DependsOn.setText(Constants.STRING_EMPTY);
            p.Description.setText(Constants.STRING_EMPTY);
            p.Enabled.setSelection(false);
            return false;
        }
    }

    public class SaveTechnologySolutionParameter extends SaveSelectionParameter
    {
        public String Name;
        public TechnologySolution DependsOn;
        public String Description;
        public boolean Enabled;

        public SaveTechnologySolutionParameter(String name, TechnologySolution dependsOn, String description,
                boolean enabled)
        {
            this.Name = name;
            this.DependsOn = dependsOn;
            this.Description = description;
            this.Enabled = enabled;
        }
    }

    public class ShowTechnologySolutionParameter extends ShowSelectionParameter
    {
        public Text Name;
        public Combo DependsOn;
        public StyledText Description;
        public Button Enabled;

        public ShowTechnologySolutionParameter(Text name, Combo dependsOn, StyledText description, Button enabled)
        {
            this.Name = name;
            this.DependsOn = dependsOn;
            this.Description = description;
            this.Enabled = enabled;
        }
    }
}
