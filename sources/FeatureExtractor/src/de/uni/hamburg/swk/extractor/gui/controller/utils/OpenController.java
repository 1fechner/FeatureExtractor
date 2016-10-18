package de.uni.hamburg.swk.extractor.gui.controller.utils;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;

import de.uni.hamburg.swk.extractor.database.entities.ak.Feature;
import de.uni.hamburg.swk.extractor.database.entities.ak.TechnologyFeature;
import de.uni.hamburg.swk.extractor.database.entities.ak.TechnologySolution;
import de.uni.hamburg.swk.extractor.gui.dialog.DialogAbout;
import de.uni.hamburg.swk.extractor.gui.dialog.DialogPreferences;
import de.uni.hamburg.swk.extractor.gui.dialog.DialogProjectNewEdit;
import de.uni.hamburg.swk.extractor.gui.dialog.DialogProjectNewEdit.Mode;
import de.uni.hamburg.swk.extractor.gui.dialog.DialogProjectOpen;
import de.uni.hamburg.swk.extractor.gui.form.FormEditASTAs;
import de.uni.hamburg.swk.extractor.gui.form.FormEditFeatures;
import de.uni.hamburg.swk.extractor.gui.form.FormEditIndicators;
import de.uni.hamburg.swk.extractor.gui.form.FormEditTechnologyFeatures;
import de.uni.hamburg.swk.extractor.gui.form.FormEditTechnologySolution;
import de.uni.hamburg.swk.extractor.gui.form.FormFocusASTA;
import de.uni.hamburg.swk.extractor.gui.form.FormViewKnowledgeBase;

public class OpenController
{
    public static final int PARAMS = SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL;
    public static final int PARAMS_2 = SWT.APPLICATION_MODAL;
    public static final int PARAMS_3 = SWT.DIALOG_TRIM;

    public void openAbout(Shell parent)
    {
        DialogAbout dlg = new DialogAbout(parent, PARAMS_2);
        dlg.open();
    }

    public void openEditTechnologySolutions(Shell parent)
    {
        FormEditTechnologySolution form = new FormEditTechnologySolution(parent, PARAMS);
        form.open();
    }

    public void openEditTechnologyFeatures(Shell parent, TechnologySolution technologySolution)
    {
        FormEditTechnologyFeatures frm = new FormEditTechnologyFeatures(parent, PARAMS_2, technologySolution);
        frm.open();
    }

    public void openEditIndicators(Shell parent, TechnologyFeature technologyFeature)
    {
        FormEditIndicators form = new FormEditIndicators(parent, PARAMS_2, technologyFeature);
        form.open();
    }

    public void openEditASTA(Shell parent, TechnologyFeature technologyFeature)
    {
        FormEditASTAs form = new FormEditASTAs(parent, PARAMS_2, technologyFeature);
        form.open();
    }

    public void openEditFeatures(Shell parent)
    {
        FormEditFeatures form = new FormEditFeatures(parent, PARAMS_2);
        form.open();
    }

    // public void openAKConfigure(Shell parent)
    // {
    // }

    public void openView(Shell parent)
    {
        FormViewKnowledgeBase frm = new FormViewKnowledgeBase(parent, PARAMS);
        frm.open();
    }

    public void openPreferences(Shell parent)
    {
        DialogPreferences dlg = new DialogPreferences(parent, PARAMS);
        dlg.open();
    }

    public void openProjectSelect(Shell parent)
    {
        DialogProjectOpen dlg = new DialogProjectOpen(parent, PARAMS);
        dlg.open();
    }

    public void openProjectNew(Shell parent)
    {
        DialogProjectNewEdit dlg = new DialogProjectNewEdit(parent, PARAMS, Mode.CREATE);
        dlg.open();
    }

    public void openProjectEdit(Shell parent)
    {
        DialogProjectNewEdit dlg = new DialogProjectNewEdit(parent, PARAMS, Mode.EDIT);
        dlg.open();
    }

    public void openFocusASTA(Shell parent, TechnologySolution t, Feature f)
    {
        FormFocusASTA frm = new FormFocusASTA(parent, PARAMS_3, t, f);
        frm.open();
    }
}
