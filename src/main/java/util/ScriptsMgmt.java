package util;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.UnsupportedEncodingException;

import javax.imageio.ImageIO;



/**
 * <b>ScriptMgmt</b> is a functional script management class used for a variety tasks related to scripts.
 * It contains methods for stopping script execution, sending email with results, logging the
 * PASS/FAIL/WARNING messages using a static logger instance.
 */

public class ScriptsMgmt {

    Logger logger = Logger.getInstance();
    private static  ScriptsMgmt instance = null;

    /**
     * Returns a static instance of {@link ScriptsMgmt}. If instance is null, a new ScriptMgmt object is created and returned.
     * However, there is only a singleton instance of this class.
     *
     * @return instance of {@link ScriptsMgmt}
     */
    public static ScriptsMgmt getInstance()
    {
        if( instance == null )
            instance = new ScriptsMgmt();

        return instance;
    }

    /**
     * Stops the script execution and sends out the mail with short results.
     *
     */
    public void stop_sendmail()
    {
        logger.logDetailAndConsole("Terminating script execution due to error");
        logger.sendmail(true);
        //***RationalTestScript.stop();
        //	System.exit(1);
    }

    /**
     * Creates a screenshot in detailed results folder and sends email with short results and relevant links.
     * This method should be called when exception is thrown while running the scripts.
     *
     * @param full_script_name the full script name
     * @param e exception
     */
    public void logFail_stop_sendmail(String full_script_name, Exception e)
    {
        String script_name = getScript_filename(full_script_name);
        logger.logDetailAndConsole("Exception occurred in the script " + script_name + "  " + e.getMessage());
        e.printStackTrace();
        logger.logDetailAndConsole(script_name + " - FAILED");
        logger.logDetailAndConsole("");
        logger.logShort(script_name + " - FAILED");
        getScreenshot(logger.getDetailedResults_folder()  + InitData.appName + "_Screenshot_" + Utilities.getDateStr()+ ".jpg");
        stop_sendmail();
        //   	System.exit(1);
    }

    /**
     * Creates a screenshot in detailed results folder and sends email with short results and relevant links.
     * This method should be called when script failure needs to be reported and execution has to be stopped.
     *
     * @param full_script_name the full script name
     */
    public void logFail_stop_sendmail(String full_script_name)
    {
        String script_name = getScript_filename(full_script_name);
        logger.logDetailAndConsole(script_name + " - FAILED");
        logger.logDetailAndConsole("");
        logger.logShort(script_name + "<h3 style=\"color:red;\"><b> - FAILED </b></h3>");
        logFooter(InitData.scriptName,false);
        getScreenshot(logger.getDetailedResults_folder()  + InitData.appName + "_Screenshot_" + Utilities.getDateStr()+ ".jpg");
        stop_sendmail();
        //  	System.exit(1);
    }

    /**
     * Reports "Failed" in the logs with the exception.
     *
     * @param full_script_name the full script name
     * @param e exception logged
     */
    public void logFail(String full_script_name, Exception e) {
        String script_name = getScript_filename(full_script_name);
        logger.logDetailAndConsole("Exception occurred in the script " + script_name + "  " + e.getMessage());
        logger.logDetailAndConsole(script_name + " - FAILED ");
        logger.logDetailAndConsole("");
        logger.logShort(script_name + "<font color=\"red\"><b> - FAILED </b></font>");

    }

    /**
     * Reports "Failed" in the logs.
     *
     * @param full_script_name the full script name
     */
    public void logFail(String full_script_name)
    {
        String script_name = getScript_filename(full_script_name);
        logger.logDetailAndConsole(script_name + " - FAILED<BR>");
        logger.logDetailAndConsole("");
        logger.logShort(script_name + "<font color=\"red\"><b> - FAILED </b></font><BR>");
        getScreenshot(logger.getDetailedResults_folder()  +  InitData.appName + Logger.screenshotLogTag + Utilities.getDateStr()+ ".jpg");
    }

    /**
     * Reports "Warning" in the logs.
     *
     * @param full_script_name the full script name
     */
    public void logWarning(String full_script_name)
    {
        String script_name = getScript_filename(full_script_name);
        logger.logDetailAndConsole(script_name + " - WARNING");
        logger.logDetailAndConsole("");
        logger.logShort(script_name + " - Warning");
    }

    /**
     * Reports "Passed" in log file
     *
     * @param full_script_name the full script name
     */
    public void logPass(String full_script_name)
    {
        String script_name = getScript_filename(full_script_name);
        logger.logDetailAndConsole("");
        logger.logDetailAndConsole(script_name + " - Passed<BR>");
        logger.logDetailAndConsole("");
        logger.logShort(script_name + "<font color=\"green\"><b> - Passed </b></font><BR>");
    }

    /**
     * Clears the Java Web Start Cache.
     *
     */
    public void clear_cache()
    {
        //String jre_path = initdata.getScriptValue("jre_path");
        String cmd = InitData.jre_path + "javaws.exe -uninstall";
        Runtime rt = Runtime.getRuntime();
        try
        {
            Process currentProcess = rt.exec(cmd);
            currentProcess.waitFor();
            logger.logDetailAndConsole("Clean web start cache - done");
        }
        catch (Exception excep)
        {
            System.out.println(excep.getMessage());
        }
    }

    /**
     * Starts the Internet Explorer(IE) Browser.
     *
     * @param browser browser name
     * @param url to be launched
     */
    public void startBrowser(String browser, String url)
    {
        String cmd = browser + " " + url;
        Runtime rt = Runtime.getRuntime();
        try
        {
            Process currentProcess = rt.exec(cmd);
            //currentProcess.waitFor();
            logger.logDetailAndConsole("Start browser - done");
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Get the script file name based on the full script name.
     *
     * @param script_name full script name
     * @return filename of the script
     */
    public static String getScript_filename(String script_name)
    {
        int string_len = script_name.length();
        String filename = "";

        int dot_pos = script_name.lastIndexOf(".");
        filename = script_name.substring(dot_pos +1, string_len);
        return filename;
    }

    /**
     * Adds header message to the logs.
     *
     * @param scriptName name of the script
     */
    public void logHeader(String scriptName)
    {
        logger.logDetailAndConsole("");
        logger.logDetailAndConsole("");
        logger.logDetailAndConsole("Beginning of " + getScript_filename(scriptName));
        logger.logDetailAndConsole("==========================================");
    }

    /**
     * Adds footer message at the end of script in logs.
     *
     * @param scriptName name of the script
     * @param sendMail flag to indicate whether to send email
     * @throws UnsupportedEncodingException
     */
    public void logFooter(String scriptName, boolean sendMail)
    {
        logger.logDetailAndConsole("End of " + getScript_filename(scriptName));
        logger.logDetailAndConsole("-------------------------------------------");

        if (sendMail)
            //**logger.sendmail();
            logger.testResultbyEmail();
    }

    /**
     * Creates a screenshot.
     *
     * @param filename name and path of the screenshot file
     */
    public void getScreenshot(String filename) {
        try {
            Robot robot = new Robot();
            BufferedImage screenShot = robot.createScreenCapture (new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
            ImageIO.write(screenShot, "JPG", new File(filename));

        } catch (Exception e) {
            System.err.println("Unhandled Exception :  " + e);
            e.printStackTrace();
        }
    }
}