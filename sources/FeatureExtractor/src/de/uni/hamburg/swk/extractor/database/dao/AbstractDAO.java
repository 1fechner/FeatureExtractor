package de.uni.hamburg.swk.extractor.database.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import de.uni.hamburg.swk.extractor.database.SessionService;

/**
 * Abstract Base Class for typed DAOs
 * 
 * @author tobias
 *
 * @param <T> The type of the object to be considered
 */
@SuppressWarnings("unchecked")
public abstract class AbstractDAO<T> implements DAO<T>
{
    /**
     * Returns results for the given query
     * 
     * @param query The query to be executed
     * @return A list of results
     */
    protected List<T> asList(String query)
    {
        Session s = SessionService.getCurrentSession();

        if (s == null)
            return new ArrayList<>();

        return (List<T>) s.createQuery(query).list();
    }

    /**
     * Returns one element from the given query. Returns null if there is none
     * 
     * @param query The query to be executed
     * @return The first element of the result list, null if none
     */
    protected T getOne(String query)
    {
        Session s = SessionService.getCurrentSession();

        if (s == null)
            return null;

        List<T> results = (List<T>) s.createQuery(query).list();

        return results.size() > 0 ? results.get(0) : null;
    }

    /**
     * Executes the given query. Used to execute updates
     * 
     * @param query The query to be executed
     * @return
     */
    protected int executeUpdate(String query)
    {
        return SessionService.getCurrentSession().createQuery(query).executeUpdate();
    }

    @Override
    public abstract List<T> getAll();
}
