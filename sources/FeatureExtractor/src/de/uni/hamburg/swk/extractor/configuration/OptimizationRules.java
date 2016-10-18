package de.uni.hamburg.swk.extractor.configuration;

/**
 * A set of rules to configure and optimize the prescan-, extraction -as well as
 * alternative- and decisionPoint determination <br>
 * The Values are used during the execution of the algorithms to enable or
 * disable specific behavior
 * 
 * @author tobias
 *
 */
public class OptimizationRules
{
    public static boolean R_000_GUI = true;
    public static boolean R_001_INITIALIZE_DB_CONNECTION = true;
    public static boolean R_002_SPLASH = false;
    public static boolean R_003_LOAD_PROJECT_FROM_CLI = true;

    public static boolean R_100_LIMIT_RECURSION = true;
    public static boolean R_101_IGNORE_DIRECTORIES = true; 
    public static boolean R_102_IGNORE_FILE_TYPES = true; 

    public static boolean R_200_IGNORE_FEATURES_WITHOUT_INDICATORS = true;
    public static boolean R_201_FORMAT_INPUT = false;
    public static boolean R_202_SANITIZE_INPUT = true;
    public static boolean R_203_CONCURENT_PROCESSING = false;
    public static boolean R_204_EXPORT_RESULTS_CSV = false;

    public static boolean R_300_INHERITED_FEATURES = true;
    public static boolean R_301_DIRECT_RELATION_NO_ALTERNATIVE = true;
    
    public static boolean R_400_COMPLETE_PATH = false;
    public static boolean R_401_WARN_MISSING_FEATURE = true;

}
