package de.uni.hamburg.swk.extractor.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import de.uni.hamburg.swk.extractor.utils.Constants;

public class PropertyService
{

    public String getPropValue(String property) throws IOException
    {
        InputStream in = null;

        try
        {
            Properties prop = new Properties();

            File cfgFile = new File(Constants.CONFIG_PROPERTIES_FILE);
            in = new FileInputStream(cfgFile);

            prop.load(in);

            return prop.getProperty(property);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (in != null)
                in.close();
        }

        return "";
    }

    public void setProperty(String key, String value) throws IOException
    {
        // try
        // {
        // Properties prop = new Properties();
        //
        // File cfgFile = new File(Constants.CONFIG_PROPERTIES_FILE);
        // _inputStream = new FileInputStream(cfgFile);
        //
        // if (_inputStream != null)
        // {
        // prop.load(_inputStream);
        // }
        // else
        // {
        // System.err.println(
        // String.format(MessagesError.PROPERTY_FILE_S_NOT_FOUND,
        // Constants.CONFIG_PROPERTIES_FILE));
        // }
        //
        // prop.setProperty(key, value);
        // }
        // catch (Exception e)
        // {
        // e.printStackTrace();
        // }
        // finally
        // {
        // if (_inputStream != null)
        // _inputStream.close();
        // }
    }
}
