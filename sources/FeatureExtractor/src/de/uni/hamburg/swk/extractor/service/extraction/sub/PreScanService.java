package de.uni.hamburg.swk.extractor.service.extraction.sub;

import java.io.File;

import org.hibernate.Transaction;

import de.uni.hamburg.swk.extractor.configuration.Configuration;
import de.uni.hamburg.swk.extractor.configuration.OptimizationRules;
import de.uni.hamburg.swk.extractor.database.SessionService;
import de.uni.hamburg.swk.extractor.database.dao.impl.ElementDAO;
import de.uni.hamburg.swk.extractor.database.dao.impl.ResultSetDAO;
import de.uni.hamburg.swk.extractor.database.entities.result.Element;
import de.uni.hamburg.swk.extractor.database.entities.result.Project;
import de.uni.hamburg.swk.extractor.service.Service;
import de.uni.hamburg.swk.extractor.utils.Constants;
import de.uni.hamburg.swk.extractor.utils.Messages;
import de.uni.hamburg.swk.extractor.utils.MessagesError;

/**
 * Provides some pre-analysis on the files to be scanned <br>
 * This is, to reduce the amount of files to be scanned. Whitelists file-types
 * to be scanned (e.g. .java or .xml files) and therefore ignores things like
 * eclipse-project file or such
 * 
 * @author tobias
 *
 */
public class PreScanService extends Service
{
    /**
     * Performs a pre-scan on the given source directory, extracting components
     * and their elements to be scanned
     * 
     * @param source The source directory to be scanned
     * @param name The name of the project to be saved
     * @return A list of components to be scanned. When nothing eligible is
     *         found, the list is empty
     */
    public void run(Project project)
    {
        print(Messages.PRESCAN_START);

        // Begin transaction
        Transaction tx = SessionService.getCurrentSession().beginTransaction();

        if (project == null)
        {
            System.err.println(MessagesError.PROJECT_NOT_EXISTING);
        }

        project.setVersion(project.getVersion() + 1);
        // project.setThreshold(Configuration.getConfidenceThreshold());
        SessionService.getCurrentSession().save(project);

        ElementDAO elementDAO = new ElementDAO();
        elementDAO.deleteForProject(project);

        ResultSetDAO resultsetDAO = new ResultSetDAO();
        resultsetDAO.deleteAll(project);

        long startTime = System.currentTimeMillis();
        readDirectory(project.getSource(), Constants.PRESCAN_DEFAULT_DEPTH, project, null);
        long estimatedTime = System.currentTimeMillis() - startTime;

        print(String.format(Messages.PRESCAN_TIME, estimatedTime / 1000d, estimatedTime));

        tx.commit();
    }

    /**
     * Reads all directories in the given path recursively and extracts all
     * files to be scanned.
     * 
     * @param path The path to start from
     * @param depth The depth of recursion
     */
    private void readDirectory(String path, int depth, Project p, Element parent)
    {
        if (OptimizationRules.R_100_LIMIT_RECURSION && depth > Constants.PRESCAN_MAX_DEPTH)
        {
            System.err.println(String.format(MessagesError.SCAN_PRE_MAX_DEPTH_REACHED, Constants.PRESCAN_MAX_DEPTH));
            return;
        }

        File directory = new File(path);
        File[] files = directory.listFiles();

        for (int i = 0; i < files.length; i++)
        {
            if (files[i].isFile())
            {
                String extension = files[i].getName().replaceAll("^.*\\.(.*)$", "$1");

                // Add new element to this component if it is a file
                if (!OptimizationRules.R_102_IGNORE_FILE_TYPES
                        || /*
                            * files[i].getName().matches(Configuration.
                            * FILETYPES_TO_SCAN)
                            */Configuration.FILETYPES_VALID.contains(extension))
                {
                    // Insert all elements to scan into db
                    Element e = new Element();
                    e.setName(files[i].getName());
                    e.setProject(p);
                    e.setPath(files[i].getPath());
                    e.setPackage(e.getPath().replace(p.getSource() + "/", "").replace("/" + e.getName(), "")
                            .replace("/", "."));
                    e.setFileType(extension);
                    e.setVersion(p.getVersion());
                    e.setDirectory(false);
                    e.setScanned(false);
                    e.setParent(parent);
                    SessionService.getCurrentSession().save(e);
                }
            }
            else if (files[i].isDirectory())
            {
                // Add a new sub component if directory
                if (!OptimizationRules.R_101_IGNORE_DIRECTORIES
                        || !(Configuration.DIRECTORIES_EXCLUDED.contains(files[i].getName()) || files[i].getName()
                                .startsWith(".")))
                {
                    Element e = new Element();
                    e.setName(files[i].getName());
                    e.setProject(p);
                    e.setPath(files[i].getPath());
                    e.setPackage(e.getPath().replace(p.getSource() + "/", "").replace("/", "."));
                    e.setFileType("Directory");
                    e.setVersion(p.getVersion());
                    e.setScanned(false);
                    e.setDirectory(true);
                    e.setParent(parent);
                    SessionService.getCurrentSession().save(e);

                    readDirectory(files[i].getPath(), depth + 1, p, e);
                }
                else
                {
                    if (Configuration.isVerbose())
                        print("Ignoring directory " + files[i].getName());
                }
            }
        }
    }
}