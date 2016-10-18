package de.uni.hamburg.swk.extractor.utils;

/**
 * This class contains error messages
 * 
 * @author tobias
 *
 */
public class MessagesError
{
    public static final String PROPERTY_FILE_S_NOT_FOUND = "property file '%s' not found in the classpath";
    public static final String PLEASE_SELECT_A_PROJECT_FIRST = "Please select a project first";

    private MessagesError()
    {// ...

    }

    public static final String ARGS_NOT_SPECIDIED = "No arguments specified";
    public static final String MODE_INVALID = "Invalid mode given";
    public static final String COULD_NO_SET_OUTPUT = "Could not set output! Printing to console...";

    public static final String MGMT_CMD_NOT_AVAILABLE = "Command '%s' not available";

    public static final String SCAN_PRE_MAX_DEPTH_REACHED = "Aborting recursive search for files: Depth reached %s";

    public static final String PROJECT_NOT_EXISTING = "Project does not exist!";

    public static final String CONFIGURATION_FAILED_TO_LOAD_PROJECT_FROM_ARGS = "Failed to load project from args: %s";
    public static final String CONFIGURATION_PROJECT_LOADED_FROM_ARGS = "Project loaded from args: %s";

    public static final String PROJECT_COULD_NOT_BE_INITIALIZED = "Project could not be initialized!";
    public static final String NO_PROJECT_OR_SOURCE_TO_WORK_WITH = "No project or source to work with!";

    public static final String PROJECT_NEW_CONFIDENCE_INVALID = "Confidence Threshold is not valid";
    public static final String PROJECT_NEW_COVERAGE_INVALID = "Feature Coverage is not valid";
    public static final String PROJECT_NEW_INVALID_DIRECTORY = "Please choose a valid directory";
    public static final String PROJECT_NEW_NO_DIRECTORY = "Please choose a directory";
    public static final String PROJECT_NEW_NO_NAME = "Please enter a name";

}