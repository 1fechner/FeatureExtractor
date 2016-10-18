package de.uni.hamburg.swk.extractor.utils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 * Contains various helper functions that are used throughout the project
 * 
 * @author tobias
 */
public class HelperFunctions
{
    private static OutputStream _previousStream = null;

    private HelperFunctions()
    {
        // ...
    }

    /**
     * Returns an indention-string with the given depth. The String consists of
     * spaces
     * 
     * @param depth Depth of the indention
     * @return A String containing only space with the given depth
     */
    public static String getIndention(int depth)
    {
        return String.format("%0" + depth + "d", 0).replace("0", "  ");
    }

    /**
     * Sets the output to the given file. Stores the previously set output
     * stream
     * 
     * @param filename The file to write to
     */
    @Deprecated
    public static void setFileOutput(String filename)
    {
        _previousStream = System.out;

        try
        {
            System.setOut(new PrintStream(new FileOutputStream(filename)));
        }
        catch (FileNotFoundException e)
        {
            System.out.println(MessagesError.COULD_NO_SET_OUTPUT);
            e.printStackTrace();
        }
    }

    /**
     * Restores the output to the one set before calling the
     * {@code setFileOutput()} method.
     */
    @Deprecated
    public static void restoreOutput()
    {
        try
        {
            System.setOut(new PrintStream(_previousStream));
        }
        catch (Exception e)
        {
            System.out.println(MessagesError.COULD_NO_SET_OUTPUT);
            e.printStackTrace();
        }

        _previousStream = null;
    }
}