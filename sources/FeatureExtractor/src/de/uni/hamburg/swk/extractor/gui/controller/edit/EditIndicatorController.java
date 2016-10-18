package de.uni.hamburg.swk.extractor.gui.controller.edit;

import java.util.List;

import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import de.uni.hamburg.swk.extractor.database.SessionService;
import de.uni.hamburg.swk.extractor.database.dao.impl.IndicatorDAO;
import de.uni.hamburg.swk.extractor.database.entities.Confidence;
import de.uni.hamburg.swk.extractor.database.entities.IndicatorLanguage;
import de.uni.hamburg.swk.extractor.database.entities.IndicatorType;
import de.uni.hamburg.swk.extractor.database.entities.ak.Indicator;
import de.uni.hamburg.swk.extractor.database.entities.ak.TechnologyFeature;
import de.uni.hamburg.swk.extractor.gui.controller.AbstractKnowledgeBaseEditController;
import de.uni.hamburg.swk.extractor.utils.Constants;

public class EditIndicatorController extends AbstractKnowledgeBaseEditController<Indicator>
{
    private TechnologyFeature _parent;

    private IndicatorDAO _indicatorDAO;

    public EditIndicatorController(TechnologyFeature parent)
    {
        _parent = parent;
        _indicatorDAO = new IndicatorDAO();
    }

    @Override
    public void load(Table table)
    {
        clearContentsTable(table);

        List<Indicator> indicators = _indicatorDAO.getIndicatorsForFeature(_parent.getId());

        for (Indicator t : indicators)
        {
            TableItem i = new TableItem(table, 0);

            String[] attributes = new String[6];
            attributes[0] = t.getType().name();
            attributes[1] = t.getIndicatorLanguage().name();
            attributes[2] = t.getValue();
            attributes[3] = t.getParameter();
            attributes[4] = t.getScope();
            attributes[5] = t.getConfidence().name();

            i.setText(attributes);
            i.setData(t);
        }
    }

    public void loadCombo(Combo comboType, Combo comboConfidence, Combo comboLanguage)
    {
        comboType.removeAll();
        comboConfidence.removeAll();
        comboLanguage.removeAll();
        
        for (IndicatorType i : IndicatorType.values())
            comboType.add(i.name());
        for (Confidence c : Confidence.values())
            comboConfidence.add(c.name());
        for (IndicatorLanguage l : IndicatorLanguage.values())
            comboLanguage.add(l.name());
    }

    @Override
    protected Indicator instantiateNew()
    {
        Indicator i = new Indicator();
        i.setType(IndicatorType.Undefined);
        i.setConfidence(Confidence.VeryHigh);
        i.setIndicatorLanguage(IndicatorLanguage.Any);
        i.setBelongsTo(_parent);

        return i;
    }

    @Override
    public <P extends AbstractKnowledgeBaseEditController<Indicator>.SaveSelectionParameter> void saveSelection(P param)
    {
        SaveIndicatorParameter p = (SaveIndicatorParameter) param;

        if (!checkTransaction())
            return;

        _currentSelection.setType(p.Type);
        _currentSelection.setValue(p.Value);
        _currentSelection.setParameter(p.Parameter);
        _currentSelection.setScope(p.Scope);
        _currentSelection.setConfidence(p.Confidence);
        _currentSelection.setIndicatorLanguage(p.Language);
        _currentSelection.setBelongsTo(_parent);

        SessionService.getCurrentSession().save(_currentSelection);
    }

    @Override
    public <P extends AbstractKnowledgeBaseEditController<Indicator>.ShowSelectionParameter> boolean showSelection(
            TableItem[] selection, P param)
    {
        ShowIndicatorParameter p = (ShowIndicatorParameter) param;

        if (selection.length > 0)
        {
            Indicator t = (Indicator) selection[0].getData();

            _currentSelection = t;

            p.Type.setText(t.getType().name());
            p.Value.setText(t.getValue() != null ? t.getValue() : Constants.STRING_EMPTY);
            p.Parameter.setText(t.getParameter() != null ? t.getParameter() : Constants.STRING_EMPTY);
            p.Scope.setText(t.getScope() != null ? t.getScope() : Constants.STRING_EMPTY);
            p.Confidence.setText(t.getConfidence().name());
            p.Language.setText(t.getIndicatorLanguage().name());

            return true;
        }
        else
        {
            _currentSelection = null;

            p.Type.setText(Constants.STRING_DIV);
            p.Value.setText(Constants.STRING_EMPTY);
            p.Parameter.setText(Constants.STRING_EMPTY);
            p.Scope.setText(Constants.STRING_EMPTY);
            p.Confidence.setText(Confidence.VeryHigh.name());
            p.Language.setText(IndicatorLanguage.Any.name());
            return false;
        }
    }

    public class SaveIndicatorParameter extends SaveSelectionParameter
    {
        public IndicatorType Type;
        public String Value;
        public String Parameter;
        public String Scope;
        public Confidence Confidence;
        public IndicatorLanguage Language;

        public SaveIndicatorParameter(IndicatorType type, String value, String param, String scope,
                Confidence confidence, IndicatorLanguage language)
        {
            this.Type = type;
            this.Value = value;
            this.Parameter = param;
            this.Scope = scope;
            this.Confidence = confidence;
            this.Language = language;
        }
    }

    public class ShowIndicatorParameter extends ShowSelectionParameter
    {
        public Combo Type;
        public Text Value;
        public Text Parameter;
        public Text Scope;
        public Combo Confidence;
        public Combo Language;

        public ShowIndicatorParameter(Combo type, Text value, Text parameter, Text scope, Combo confidence,
                Combo language)
        {
            this.Type = type;
            this.Value = value;
            this.Parameter = parameter;
            this.Scope = scope;
            this.Confidence = confidence;
            this.Language = language;
        }
    }
}
