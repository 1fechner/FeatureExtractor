package de.uni.hamburg.swk.extractor.gui.controller;

import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.hibernate.Transaction;

import de.uni.hamburg.swk.extractor.database.SessionService;

public abstract class AbstractKnowledgeBaseEditController<T> extends AbstractTableController
{
    private static final String ABORTING_TRANSACTION = "Aborting %s";
    protected boolean _hasChanges = false;
    protected Transaction _transaction;

    protected T _currentSelection;

    /**
     * Abstract super class for save parameter given when an element should be
     * saved
     */
    public class SaveSelectionParameter
    {
    }

    /**
     * Abstract super class for selection parameter given when an element should
     * be shown
     */
    public class ShowSelectionParameter
    {
    }

    /**
     * Returns the currently selected element
     * 
     * @return The element selected
     */
    public T getCurrentSelection()
    {
        return _currentSelection;
    }

    /**
     * Returns if there have been changes since the last save/load
     * 
     * @return Whether the controller has changes or not
     */
    public boolean hasChanges()
    {
        return _hasChanges;
    }

    /**
     * Returns whether there currently is an element selected
     * 
     * @return Whether an element is selected or not
     */
    public boolean hasCurrentSelection()
    {
        return _currentSelection != null;
    }

    /**
     * Saves all changes since the last commit
     */
    public void apply()
    {
        if (_hasChanges && _transaction != null)
        {
            _transaction.commit();
            _hasChanges = false;
        }
    }

    /**
     * Aborts all changes since the last commit.
     */
    public void abort()
    {
        System.out.println(String.format(ABORTING_TRANSACTION, _transaction));

        if (_transaction != null)
        {
            _transaction.rollback();
            SessionService.getCurrentSession().clear();
            _hasChanges = false;
        }
    }

    /**
     * Deletes the currently selected element
     */
    public void deleteCurrentSelection()
    {
        if (!checkTransaction())
            return;

        SessionService.getCurrentSession().delete(_currentSelection);
        _currentSelection = null;
    }

    public void createNew()
    {
        // Get a new object, instantiated an pre-configured from the specific
        // implementation
        _currentSelection = instantiateNew();
    }

    protected boolean checkTransaction()
    {
        if (!hasCurrentSelection())
            return false;

        if (!_hasChanges)
        {
            _transaction = SessionService.getCurrentSession().beginTransaction();
            _hasChanges = true;
        }

        return true;
    }

    public abstract void load(Table table);

    protected abstract T instantiateNew();

    public abstract <P extends SaveSelectionParameter> void saveSelection(P param);

    public abstract <P extends ShowSelectionParameter> boolean showSelection(TableItem[] selection, P param);

}
