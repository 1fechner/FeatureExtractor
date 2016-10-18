package de.uni.hamburg.swk.extractor.service.extraction.sub;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import de.uni.hamburg.swk.extractor.configuration.OptimizationRules;
import de.uni.hamburg.swk.extractor.service.Service;

public class FilePreparationService extends Service
{
    /**
     * Read a file into the buffer. Creates a list of the files lines
     * 
     * @param file The file to be read
     * @return The files contents line by line as a list
     */
    public static List<String> readFile(String file)
    {
        List<String> lines = new ArrayList<String>();

        File f = new File(file);

        try
        {
            BufferedReader br = new BufferedReader(new FileReader(f));
            String line;

            // Read file into buffer
            while ((line = br.readLine()) != null)
            {
                lines.add(line);
            }
            br.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        if (OptimizationRules.R_201_FORMAT_INPUT)
            lines = format(lines);

        return OptimizationRules.R_202_SANITIZE_INPUT ? sanitize(lines) : lines;
    }

    private static List<String> sanitize(List<String> lines)
    {
        List<String> nLines = new ArrayList<String>();

        for (String s : lines)
        {
            if (!s.matches("[ {};(),.&|]*") || !s.startsWith("//"))
                nLines.add(s);
        }

        return nLines;
    }

    private static List<String> format(List<String> lines)
    {
        System.err.println("format not implemented!");
        return lines;
    }
}
