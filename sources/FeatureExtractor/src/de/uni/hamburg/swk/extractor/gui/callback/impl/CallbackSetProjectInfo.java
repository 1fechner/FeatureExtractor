package de.uni.hamburg.swk.extractor.gui.callback.impl;

import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MenuItem;

import de.uni.hamburg.swk.extractor.configuration.Configuration;
import de.uni.hamburg.swk.extractor.database.entities.result.Project;
import de.uni.hamburg.swk.extractor.gui.callback.Callback;

public class CallbackSetProjectInfo extends Callback
{
    private Label _name;
    private Label _path;
    private Label _confidence;
    private Label _coverage;
    private Label _dependencyChain;
    private MenuItem _edit;

    public CallbackSetProjectInfo(Label name, Label path, Label confidence, Label coverage, Label dependencyChain, MenuItem edit)
    {
        _name = name;
        _path = path;
        _confidence = confidence;
        _coverage = coverage;
        _dependencyChain = dependencyChain;
        _edit = edit;
    }

    @Override
    public void exec(Object[] args)
    {
        Project p = Configuration.getSelectedProject();

        _name.setText(p.getName());
        _name.setToolTipText(String.format("%s (ID: %s)", p.getName(), p.getId()));
        _path.setText(p.getSource());
        _path.setToolTipText(p.getSource());
        _confidence.setText(p.getMinConfidence() + "");
        _coverage.setText(p.getMinFeatureCoverage() + "");
        _dependencyChain.setText(p.getMaxDependencyChain() + "");
        _edit.setEnabled(true);
    }
}
