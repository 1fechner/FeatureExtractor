package de.uni.hamburg.swk.extractor.database;

import org.eclipse.swt.SWT;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import de.uni.hamburg.swk.extractor.configuration.DatabaseConfigurationService;
import de.uni.hamburg.swk.extractor.configuration.OptimizationRules;
import de.uni.hamburg.swk.extractor.gui.controller.utils.MessageController;
import de.uni.hamburg.swk.extractor.utils.Messages;

public class SessionService
{
    private static SessionFactory _sessionFactory;
    private static Session _currentSession;

    private SessionService()
    {
    }

    /**
     * Opens a session to the database
     * 
     * @return Returns a new open session or the currently open session, if a
     *         session was opened before
     */
    public static synchronized Session openSession()
    {
        if (_sessionFactory == null)
            setUpSessionFactory();

        try
        {
            if (_currentSession == null || !_currentSession.isOpen())
                _currentSession = _sessionFactory.openSession();
        }
        catch (Exception e)
        {
            if (OptimizationRules.R_000_GUI)
            {
                MessageController.show("Error connecting to database",
                        "Could not connect to database\n" + e.getMessage(), SWT.ICON_ERROR | SWT.OK);
                e.printStackTrace();
            }
            else
            {
                e.printStackTrace();
            }
        }

        return _currentSession;
    }

    /**
     * Closes the session currently open. Does nothing if no session is open at
     * the moment
     */
    public static synchronized void closeCurrentSession()
    {
        if (_currentSession != null && _currentSession.isOpen())
            _currentSession.close();
    }

    /**
     * Returns the currently open session. If no session is open, a new session
     * is opened
     * 
     * @return
     */
    public static synchronized Session getCurrentSession()
    {
        if (_currentSession == null || !_currentSession.isOpen())
            openSession();
        return _currentSession;
    }

    /**
     * Closes the sessionFactory, killing all open sessions. Should only called
     * after all the work is done
     */
    public static synchronized void kill()
    {
        closeCurrentSession();

        if (_sessionFactory != null)
            _sessionFactory.close();
    }

    /**
     * Sets up the {@link SessionFactory}. Uses what is configured in the
     * properties file
     */
    private static synchronized void setUpSessionFactory()
    {
        System.out.println(Messages.SETTING_UP_SESSION_FACTORY);
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        try
        {
            _sessionFactory = DatabaseConfigurationService.configureDatabase();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
