package de.uni.hamburg.swk.extractor.gui.controller.edit;

import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import de.uni.hamburg.swk.extractor.database.SessionService;
import de.uni.hamburg.swk.extractor.database.dao.impl.ASTADAO;
import de.uni.hamburg.swk.extractor.database.entities.ASTAAttribute;
import de.uni.hamburg.swk.extractor.database.entities.ASTAType;
import de.uni.hamburg.swk.extractor.database.entities.CapabilityType;
import de.uni.hamburg.swk.extractor.database.entities.ak.ASTA;
import de.uni.hamburg.swk.extractor.database.entities.ak.TechnologyFeature;
import de.uni.hamburg.swk.extractor.gui.controller.AbstractKnowledgeBaseEditController;
import de.uni.hamburg.swk.extractor.utils.Constants;

public class EditASTAController extends AbstractKnowledgeBaseEditController<ASTA>
{
    private ASTADAO _astaDAO;
    private TechnologyFeature _feature;

    public EditASTAController(TechnologyFeature feature)
    {
        super();

        _astaDAO = new ASTADAO();
        _feature = feature;
    }

    @Override
    public void load(Table table)
    {
        clearContentsTable(table);

        for (ASTA asta : _astaDAO.getByTechnologyFeature(_feature.getId()))
        {
            TableItem i = new TableItem(table, 0);

            String[] attributes = new String[3];
            attributes[0] = asta.getContext();
            attributes[1] = asta.getDescription();
            attributes[2] = asta.getType().name();

            i.setText(attributes);
            i.setData(asta);
        }
    }

    public void loadCombo(Combo combo)
    {
        combo.removeAll();

        for (ASTAType t : ASTAType.values())
            combo.add(t.name());
    }

    @Override
    protected ASTA instantiateNew()
    {
        ASTA newASTA = new ASTA();
        newASTA.setAttribute(ASTAAttribute.Undefined);
        newASTA.setCapabilityType(CapabilityType.Undefined);
        newASTA.setBelongsTo(_feature);
        newASTA.setSource(Constants.STRING_EMPTY);

        return newASTA;
    }

    @Override
    public <P extends AbstractKnowledgeBaseEditController<ASTA>.SaveSelectionParameter> void saveSelection(P param)
    {
        SaveASTAParameter p = (SaveASTAParameter) param;

        if (!checkTransaction())
            return;

        _currentSelection.setType(p.Type);
        _currentSelection.setContext(p.Context);
        _currentSelection.setDescription(p.Description);

        SessionService.getCurrentSession().save(_currentSelection);

    }

    @Override
    public <P extends AbstractKnowledgeBaseEditController<ASTA>.ShowSelectionParameter> boolean showSelection(
            TableItem[] selection, P param)
    {
        ShowASTAParameter p = (ShowASTAParameter) param;

        if (selection.length > 0)
        {
            ASTA a = (ASTA) selection[0].getData();

            _currentSelection = a;

            p.Type.setText(a.getType().name());
            p.Context.setText(a.getContext() != null ? a.getContext() : Constants.STRING_EMPTY);
            p.Description.setText(a.getDescription() != null ? a.getDescription() : Constants.STRING_EMPTY);

            return true;

        }
        else
        {
            p.Type.setText(ASTAType.Undefined.name());
            p.Context.setText(Constants.STRING_EMPTY);
            p.Description.setText(Constants.STRING_EMPTY);

            return false;
        }
    }

    public class SaveASTAParameter extends SaveSelectionParameter
    {
        public ASTAType Type;
        public String Context;
        public String Description;

        public SaveASTAParameter(ASTAType type, String context, String description)
        {
            this.Type = type;
            this.Context = context;
            this.Description = description;
        }
    }

    public class ShowASTAParameter extends ShowSelectionParameter
    {
        public Combo Type;
        public StyledText Context;
        public StyledText Description;

        public ShowASTAParameter(Combo type, StyledText context, StyledText description)
        {
            this.Type = type;
            this.Context = context;
            this.Description = description;
        }
    }
}
