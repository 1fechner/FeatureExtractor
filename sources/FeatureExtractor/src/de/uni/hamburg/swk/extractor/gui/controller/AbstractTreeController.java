package de.uni.hamburg.swk.extractor.gui.controller;

import org.eclipse.swt.graphics.Image;
import org.eclipse.wb.swt.SWTResourceManager;

import de.uni.hamburg.swk.extractor.utils.Constants;

public abstract class AbstractTreeController
{
    /**
     * Returns an icon according to the given filetype
     * 
     * @param fileType The file-type of the icon
     * @return The icon
     */
    public static Image getFileImage(String fileType)
    {
        switch (fileType)
        {
            case Constants.ICO_TYPE_JAVA:
                return SWTResourceManager.getImage(Constants.ICO_JAVA);
            case Constants.ICO_TYPE_TXT:
                return SWTResourceManager.getImage(Constants.ICO_TXT);
            case Constants.ICO_TYPE_PROPERTIES:
                return SWTResourceManager.getImage(Constants.ICO_PROPERTIES);
            case Constants.ICO_TYPE_DIRECTORY:
                return SWTResourceManager.getImage(Constants.ICO_FOLDER);
            default:
                return SWTResourceManager.getImage(Constants.ICO_FILE);
        }
    }

    /**
     * Returns an icon according to the given type
     * 
     * @param type The image-type of the icon
     * @return The icon
     */
    public static Image getTypeImage(String type)
    {
        switch (type)
        {
            case Constants.ICO_TYPE_TECHNOLOGY:
                return SWTResourceManager.getImage(Constants.ICO_TECHNOLOGY);
            case Constants.ICO_TYPE_FEATURE:
                return SWTResourceManager.getImage(Constants.ICO_FEATURE);
            case Constants.ICO_TYPE_TECHNOLOGY_FEATURE:
                return SWTResourceManager.getImage(Constants.ICO_TECHNOLOGY_FEATURE);
            case Constants.ICO_TYPE_INDICATOR:
                return SWTResourceManager.getImage(Constants.ICO_INDICATOR);
            default:
                return null;
        }
    }
}