package de.uni.hamburg.swk.extractor.database.dao.impl;

import java.util.List;

import de.uni.hamburg.swk.extractor.database.dao.AbstractDAO;
import de.uni.hamburg.swk.extractor.database.entities.result.Element;
import de.uni.hamburg.swk.extractor.database.entities.result.Project;

public class ElementDAO extends AbstractDAO<Element>
{
    private final String QUERY_ALL = "FROM Element";
    private final String QUERY_ID = "FROM Element WHERE id = %s";
    private final String QUERY_PROJECT = "FROM Element WHERE project = %s AND version = %s";
    private final String QUERY_ROOT = "FROM Element WHERE parent = null AND project = %s AND version = %s";
    private final String QUERY_CHILDREN = "FROM Element WHERE parent = %s AND project = %s AND version = %s ORDER BY directory DESC, package, name ASC";
    private final String QUERY_ELEMENTS_TO_SCAN = "FROM Element WHERE project = %s AND version = %s AND directory = false";
    private final String QUERY_DELETE_FOR_PROJECT = "DELETE Element WHERE project = %s AND version < %s";

    @Override
    public List<Element> getAll()
    {
        return asList(QUERY_ALL);
    }

    @Override
    public Element getById(int id)
    {
        return getOne(String.format(QUERY_ID, id));
    }

    public List<Element> getByProject(Project project)
    {
        return asList(String.format(QUERY_PROJECT, project.getId(), project.getVersion()));
    }

    public List<Element> getRootForProject(Project project)
    {
        return asList(String.format(QUERY_ROOT, project.getId(), project.getVersion()));
    }

    public List<Element> getChildrenFor(int id, Project project)
    {
        return asList(String.format(QUERY_CHILDREN, id, project.getId(), project.getVersion()));
    }

    /**
     * Returns a list of all {@link Element} to be scanned for the given project
     * 
     * @param project The {@link Project} who's {@link Element} are to be
     *            scanned
     * @return The {@link Element} to be scanned for this {@link Project}. Is
     *         empty if there is nothing to be scanned or the project does not
     *         exist in the database
     */
    public List<Element> getElementsToScan(Project project)
    {
        return asList(String.format(QUERY_ELEMENTS_TO_SCAN, project.getId(), project.getVersion()));
    }

    /**
     * Deletes all {@link Element} for the given {@link Project}
     * 
     * @param project The {@link Project} to delete {@link Element} for
     * @return
     */
    public int deleteForProject(Project project)
    {
        return executeUpdate(String.format(QUERY_DELETE_FOR_PROJECT, project.getId(), project.getVersion()));
    }

}
