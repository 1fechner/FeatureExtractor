package de.uni.hamburg.swk.extractor.gui.controller;

import org.eclipse.swt.widgets.Table;

public abstract class AbstractTableController
{
    /**
     * Clear the given table. <br>
     * Removes all data
     * 
     * @param table The table to be cleared
     */
    protected void clearAllTable(Table table)
    {
        table.setRedraw(false);

        while (table.getColumnCount() > 0)
        {
            table.getColumns()[0].dispose();
        }

        table.clearAll();
        table.removeAll();

        table.setRedraw(true);
    }

    /**
     * Clears the given table's contents. Removes all cells and rows, but leaves
     * the headers and columns
     * 
     * @param table The table to be cleared
     */
    protected void clearContentsTable(Table table)
    {
        table.clearAll();
        table.removeAll();
    }

}
