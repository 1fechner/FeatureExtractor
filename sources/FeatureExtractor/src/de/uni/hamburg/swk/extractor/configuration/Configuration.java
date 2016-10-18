package de.uni.hamburg.swk.extractor.configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.uni.hamburg.swk.extractor.database.dao.impl.ProjectDAO;
import de.uni.hamburg.swk.extractor.database.entities.result.Project;
import de.uni.hamburg.swk.extractor.gui.callback.CallbackRegistry;
import de.uni.hamburg.swk.extractor.gui.callback.CallbackType;
import de.uni.hamburg.swk.extractor.utils.Constants;
import de.uni.hamburg.swk.extractor.utils.MessagesError;

public class Configuration
{
    private static boolean _verbose = false;
    private static String _projectName = "";

    private static Project _selectedProject;

    private static String _version = "1.0";

    /**
     * A list of file types to scan. Avoiding unnecessary
     * I/O-operations for files not containing any useful information
     */
    public static List<String> FILETYPES_VALID = new ArrayList<String>(
            Arrays.asList("java", "xml", "cfg", "properties"));
    /**
     * List of directories to ignore while scanning. These tend to contain
     * IDE-configuration an unwanted meta-data
     */
    public static List<String> DIRECTORIES_EXCLUDED = new ArrayList<String>(Arrays.asList(".settings", ".metadata",
            ".project", "bin", "resources"));

    /**
     * Initialize the static configuration<br>
     * Sets all values according to what was given by the CL arguments
     * 
     * @param args The command line args
     * @return
     */
    public static void initialize(String[] args)
    {
        if (args.length <= 0)
            return;

        for (int i = 0; i < args.length; ++i)
        {
            System.out.println(args[i]);
            switch (args[i])
            {
                case Constants.PARAM_VERBOSE:
                case Constants.PARAM_VERBOSE_L:
                    _verbose = true;
                    break;
                case Constants.PARAM_PROJECT:
                    if (args.length > i)
                        _projectName = args[i + 1];
                    break;
                case Constants.PARAM_EXTRACT_TO_CSV:
                    OptimizationRules.R_204_EXPORT_RESULTS_CSV = true;
                default:
                    break;
            }
        }
    }

    protected static void setProjectName(String projectName)
    {
        _projectName = projectName;
    }

    protected static String getProjectName()
    {
        return _projectName;
    }

    public static Project getSelectedProject()
    {
        return _selectedProject;
    }

    public static void setSelectedProject(Project _selectedProject)
    {
        Configuration._selectedProject = _selectedProject;
        // Invoke callback so change can be displayed
        CallbackRegistry.invoke(CallbackType.PROJECT_SELECTED, null, Configuration.class);
    }

    public static boolean isVerbose()
    {
        return _verbose;
    }

    public static String getVersion()
    {
        return _version;
    }

    public static void loadProjectFromArgs()
    {
        if (Configuration.getProjectName() != null && !Configuration.getProjectName().isEmpty())
        {
            System.out.println(Configuration.getProjectName());

            Project p = new ProjectDAO().getByName(Configuration.getProjectName());

            if (p != null)
            {
                Configuration.setSelectedProject(p);
                System.out.println(String.format(MessagesError.CONFIGURATION_PROJECT_LOADED_FROM_ARGS,
                        Configuration.getProjectName()));
            }
            else
            {
                System.err.println(String.format(MessagesError.CONFIGURATION_FAILED_TO_LOAD_PROJECT_FROM_ARGS,
                        Configuration.getProjectName()));
            }
        }
    }
}
