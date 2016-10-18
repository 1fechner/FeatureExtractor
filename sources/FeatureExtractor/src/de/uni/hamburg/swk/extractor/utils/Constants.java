package de.uni.hamburg.swk.extractor.utils;

/**
 * This class contains constants and fixed values
 * 
 * @author tobias
 *
 */
public class Constants
{
    private Constants()
    {
        // ...
    }

    /**
     * Maximum depth of recursion to avoid endless loops, or too long scans on
     * deep directory structures
     */
    public final static int PRESCAN_MAX_DEPTH = 25;
    public final static int PRESCAN_DEFAULT_DEPTH = 1;

    public final static int EXTRACTION_PRINT_CAP = 500;

    public final static int NO_THREADS = 8;

    public final static String STRING_EMPTY = "";
    public final static String STRING_DIV = "-";

    // Files
    public static final String LOG_FILE = "output.log";
    public static final String CONFIG_PROPERTIES_FILE = "config/database.properties";

    // Hibernate
    public static final String HIBERNATE_CONNECTION_PASSWORD = "hibernate.connection.password";
    public static final String HIBERNATE_CONNECTION_USERNAME = "hibernate.connection.username";
    public static final String HIBERNATE_CONNECTION_URL = "hibernate.connection.url";

    // Parameters
    public static final String PARAM_VERBOSE = "-v";
    public static final String PARAM_VERBOSE_L = "--verbose";
    public static final String PARAM_PROJECT = "--project";
    public static final String PARAM_EXTRACT_TO_CSV = "--extract-csv";

    // Formats
    public static final String MATCH_SPACES = "\\s*";
    public static final String MATCH_ANY_WORD = "\\w*";
    public static final String LABEL_NONE = "N/A";
    public static final String FEATURE_NOT_IMPLEMENTED = "O";
    public static final String FEATURE_IMPLEMENTED = "X";
    public static final String FORMAT_COVERAGE = "%s (%.3f)";
    public static final String FORMAT_DISPLAY_TECHNOLOGY = "%s";

    // Resources
    public static final String ICO_INDICATOR = "resources/indicator.ico";
    public static final String ICO_TECHNOLOGY_FEATURE = "resources/tech_feature.ico";
    public static final String ICO_FEATURE = "resources/feature.ico";
    public static final String ICO_TECHNOLOGY = "resources/technology.ico";
    public static final String ICO_FILE = "resources/file.ico";
    public static final String ICO_FOLDER = "resources/folder.ico";
    public static final String ICO_PROPERTIES = "resources/properties.ico";
    public static final String ICO_TXT = "resources/txt.ico";
    public static final String ICO_JAVA = "resources/java.ico";
    public static final String ICO_STOP = "resources/stop.ico";
    public static final String ICO_CONFIRM = "resources/confirm.ico";
    public static final String ICO_CANCEL = "resources/cancel.ico";
    public static final String ICO_SEARCH = "resources/search.ico";
    public static final String ICO_RELOAD = "resources/reload.ico";
    public static final String ICO_FIND = "resources/find.ico";
    public static final String ICO_HELP = "resources/help.ico";
    public static final String ICO_EXIT = "resources/exit.ico";
    public static final String ICO_WARNING = "resources/warning.ico";
    public static final String ICO_DELETE = "resources/delete.ico";
    public static final String ICO_ADD = "resources/add.ico";
    public static final String ICO_PROJECT = "resources/project.ico";
    public static final String ICO_PROJECT_ADD = "resources/project_add.ico";
    public static final String ICO_PROJECT_OPEN = "resources/project_open.ico";
    public static final String ICO_PROJECT_EDIT = "resources/project_edit.ico";
    public static final String ICO_PREFERENCES = "resources/preferences.ico";
    public static final String ICO_AK = "resources/ak.ico";
    public static final String ICO_AK_SAVE = "resources/ak_save.ico";
    public static final String ICO_AK_CONFIGURE = "resources/ak_configure.ico";
    public static final String ICO_TECHNOLOGY_ADD = "resources/technology_add.ico";
    public static final String ICO_ALTERNATIVE = "resources/alternative.ico";

    // Icon Type
    public static final String ICO_TYPE_INDICATOR = "indicator";
    public static final String ICO_TYPE_TECHNOLOGY_FEATURE = "technologyFeature";
    public static final String ICO_TYPE_FEATURE = "feature";
    public static final String ICO_TYPE_TECHNOLOGY = "technology";
    public static final String ICO_TYPE_DIRECTORY = "Directory";
    public static final String ICO_TYPE_PROPERTIES = "properties";
    public static final String ICO_TYPE_TXT = "txt";
    public static final String ICO_TYPE_JAVA = "java";

    public static final String FATCOW_URL = "http://www.fatcow.com/free-icons";
    public static final String URL_CREATIVE_COMMONS_3 = "https://creativecommons.org/licenses/by/3.0/us/";
};