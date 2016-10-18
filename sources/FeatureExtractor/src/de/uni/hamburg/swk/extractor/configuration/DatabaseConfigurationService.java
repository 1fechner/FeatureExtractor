package de.uni.hamburg.swk.extractor.configuration;

import org.hibernate.SessionFactory;

import de.uni.hamburg.swk.extractor.utils.Constants;

public class DatabaseConfigurationService
{
    public static SessionFactory configureDatabase()
    {
        PropertyService propertyService = new PropertyService();

        try
        {
            org.hibernate.cfg.Configuration cfg = new org.hibernate.cfg.Configuration();
            cfg.configure();

            cfg.setProperty(Constants.HIBERNATE_CONNECTION_URL,
                    propertyService.getPropValue(Constants.HIBERNATE_CONNECTION_URL));
            cfg.setProperty(Constants.HIBERNATE_CONNECTION_USERNAME,
                    propertyService.getPropValue(Constants.HIBERNATE_CONNECTION_USERNAME));
            cfg.setProperty(Constants.HIBERNATE_CONNECTION_PASSWORD,
                    propertyService.getPropValue(Constants.HIBERNATE_CONNECTION_PASSWORD));

            System.out.println(String.format("Connecting to <%s>",
                    propertyService.getPropValue(Constants.HIBERNATE_CONNECTION_URL)));

            SessionFactory sessionFactory = cfg.buildSessionFactory();

            return sessionFactory;
        }
        catch (Exception e)
        {
            return null;
        }
    }

}
