package de.uni.hamburg.swk.extractor.startup.mode;

/**
 * This mode prints the possible inputs for this program
 * 
 * @author tobias
 *
 */
public class ModeHelp extends Mode
{

    private static final String HELP_NOT_IMPLEMENTED = "Help not implemented";

    @Override
    public void execute(String[] args)
    {
        System.out.println(HELP_NOT_IMPLEMENTED);
    }
}