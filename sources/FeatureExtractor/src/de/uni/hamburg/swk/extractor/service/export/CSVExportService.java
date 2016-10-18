package de.uni.hamburg.swk.extractor.service.export;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.uni.hamburg.swk.extractor.configuration.Configuration;
import de.uni.hamburg.swk.extractor.service.Service;

public class CSVExportService extends Service
{
    private static final String ENCODING = "UTF-8";
    private static final String OUT_FORMAT = "%s-%s-%s.csv";

    public static List<String> _buffer;

    public static void buffer(String str)
    {
        if (_buffer == null)
            _buffer = new ArrayList<>();

        _buffer.add(str);
    }

    public static void export()
    {
        PrintWriter writer = null;

        System.out.println("Exporting CSV...");

        try
        {
            writer = new PrintWriter(
                    String.format(OUT_FORMAT,
                            Configuration.getSelectedProject().getName().toLowerCase().replace(" ", "_"), new Date(),
                            String.format("%03d",
                                    Math.round(Configuration.getSelectedProject().getMinConfidence() * 100))),
                    ENCODING);

            for (String str : _buffer)
            {
                writer.write(str + "\n");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (writer != null)
                writer.close();
        }

        clear();

        System.out.println("Done!");
    }

    public static void clear()
    {
        if (_buffer != null)
            _buffer.clear();
    }
}
