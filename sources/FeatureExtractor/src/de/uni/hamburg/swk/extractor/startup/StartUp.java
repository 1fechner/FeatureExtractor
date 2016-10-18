package de.uni.hamburg.swk.extractor.startup;

import de.uni.hamburg.swk.extractor.configuration.Configuration;
import de.uni.hamburg.swk.extractor.configuration.OptimizationRules;
import de.uni.hamburg.swk.extractor.gui.form.FormMain;
import de.uni.hamburg.swk.extractor.startup.mode.ModeHelp;
import de.uni.hamburg.swk.extractor.utils.Messages;

/**
 * Entry point for Feature Extractor. Configures and starts the application
 * according to the mode given
 * 
 * @author tobias
 *
 */
public class StartUp
{
    /**
     * Main Method for Feature Extractor. <br>
     * Sets up the application, initializes the configuration. <br>
     * The application has different modes it can run in, though these are
     * different from what they do, they use the same data structures and suche,
     * hence being one application.
     * <p>
     * The different modes are
     * <li>Management
     * <li>Extraction
     * <li>Alternatives
     * <li>Help
     * </p>
     * For further information, see mode 'Help'
     * 
     * @param args Command line arguments
     */
    public static void main(String[] args)
    {
        Configuration.initialize(args);

        if (OptimizationRules.R_000_GUI)
            startGUI(args);
        else
            startConsole(args);
    }

    private static void startGUI(String[] args)
    {
        FormMain.entry(args);
    }

    private static void startConsole(String[] args)
    {
        System.out.println(String.format(Messages.VERSION, Configuration.getVersion()));

        new ModeHelp().execute(args);

        // // Check if mode is given
        // if (args.length > 0)
        // {
        // try
        // {
        // System.out.println(Configuration.getMode());
        // // Initialize mode view reflection
        // Mode m = (Mode) Class.forName(Mode.class.getName()
        // + Configuration.getMode().substring(0, 1).toUpperCase() +
        // Configuration.getMode().substring(1))
        // .newInstance();
        // m.execute(args);
        // }
        // catch (Exception e)
        // {
        // System.out.println(MessagesError.MODE_INVALID);
        // e.printStackTrace();
        // }
        // }
        // else
        // {
        // // There were no args specified, print help
        // System.out.println(MessagesError.ARGS_NOT_SPECIDIED);
        // new ModeHelp().execute(args);
        // }
        //
        // SessionService.kill();
    }
}
