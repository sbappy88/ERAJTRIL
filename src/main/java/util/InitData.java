package util;



//import sun.tools.jconsole.Utilities;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Properties;


/**
 * <b>InitData</b> is used for initializing all the automation related
 * configuration data like application path, log file locations, etc.
 * It has methods for setting the automation script name, initializing
 * the common variables, etc.
 *
 */
public class InitData {

    protected static InitData instance = null;
    protected static Properties initvalues = null;

    public static String app_path = "";
    public static String appName = "";
    public static String operSystem = "";
    public static String javaVersion = System.getProperty("java.version");
    public static boolean rftFlag = false;
    public static boolean paramIP = false;
    public static String OS_WIN = "Windows";
    public static String OS_LNX = "Linux";


    public static String pathSeparator = "";
    public static String user_id = "";
    public static ArrayList<String> build_id = new ArrayList<String>();
    public static String log_path = "";
    public static String projectPath = System.getProperty("user.dir") + pathSeparator;
    public static String scriptsPath = "scripts" + pathSeparator;
    public static String initPath = "Resources\\Data" ;
    public static String execHelperPath = "execHelpers" + pathSeparator;
    public static String initFileName = "";
    public static String comInitFileName = "";
    public static String send_mailTo = "";
    public static String send_mail = "";
    public static String jre_path = "";
    public static int startBlock = 0;
    public static int endBlock = 0;

    public static String psbudev_mount = "";
    public static String win_psbudev_mapped_drive = "";

    public static String web_server_ip = "";
    public static String autologs_loc_mount = "";
    public static String autologs_ip = "";
    public static String autologs_rmt_mount = "";
    public static String autologs_nat_ip = "";
    public static String autologs_win_map = "";
    public static String autologs_user = "";
    public static String autologs_pwd = "";
    public static String scriptName = "";

    public static String log_svr = "";
    public static String log_svr_ip = "";
    public static String log_user_name = "";
    public static String log_user_pwd = "";
    public static String link_dir = "";
    public static String vnc_pwd_common = "";
    public static String vnc_pwd_autodev = "";

    public static String app_host_name = "";
    public static String app_server_ip = "";
    public static String app_root_username = "";
    public static String app_root_password = "";
    public static String app_server_prompt = "";


    public static int installTimeout = 0;

    /**
     * Constructor which initializes basic variables needed to start execution.
     *
     * @throws Exception
     */
    protected InitData() throws Exception {

        String currentDirName = System.getProperty("user.dir");
        initPath();
        //String drive = currentDirName.split(":")[0];
        projectPath = currentDirName ;
        scriptsPath = projectPath + scriptsPath;
        //***initPath = projectPath + initPath + pathSeparator  ; **//orginal blcok by bappy
        execHelperPath = projectPath + app_path + pathSeparator + execHelperPath + pathSeparator  ;
        comInitFileName = projectPath + pathSeparator + initPath + pathSeparator + "init_common.cfg";

		/*
		if	(currentDirName.indexOf("ValenciaTests") > 0) {
			initFileName = Utilities.findFile(initPath, initFileName);
			comInitFileName = projectPath + pathSeparator + initPath + pathSeparator + "init_common.cfg";
		} else {
			initFileName = Utilities.convertPathToAbsolute(initFileName);
			comInitFileName = projectPath + pathSeparator + initPath + pathSeparator + "init_common.cfg";
		}
		*/
        System.out.println("initFileName## > " +initFileName );

        getAllValues();
        initCommonVariables();
        if (user_id.equals(""))
            user_id = System.getProperty("user.name");

        if (send_mailTo.equalsIgnoreCase(""))
            send_mailTo = getScriptValue("send_mailTo");

        web_server_ip = getScriptValue("web_server_ip");
        if (send_mail.equals(""))
            send_mail = getScriptValue("send_mail");
        jre_path = getScriptValue("jre_path");

        //get SSL flag only when aceFlag is true because SSL key are only used for ACE
        vnc_pwd_common = getScriptValue( "vnc_pwd_common");
        vnc_pwd_autodev = getScriptValue( "vnc_pwd_autodev");
    }
    private void initPath()
    {
        projectPath = System.getProperty("user.dir") + pathSeparator;
        scriptsPath = "scripts" + pathSeparator;
        //initPath = "config" + pathSeparator;
        execHelperPath = "execHelpers" + pathSeparator;

    }

    /**
     * Initializes the OS, init file, build versions to be used, application path and the log files path.
     * This method must be called before calling getInstance() in test classes
     *
     * @param os operating system
     * @param apppath application directory location
     * @param initfile name of the init file
     * @param blocks
     * @param buildID arraylist of build versions
     * @param logDir log files location
     * @param userID user Id
     */
    public static void setInitNames(String os, String apppath, String initfile, int[] blocks, ArrayList buildID, String logDir, String userID)
    {
        operSystem = os;
        pathSeparator = (operSystem == OS_WIN) ? "\\" : "/";
        app_path = apppath;
        initFileName = initfile;
        startBlock = blocks[0];
        endBlock = blocks[1];
        build_id = buildID;
        log_path = (logDir.equalsIgnoreCase("")) ? logDir : Utilities.convertPathToAbsolute(logDir);
        if (!userID.equalsIgnoreCase(""))
            send_mailTo = userID;
        send_mailTo = "abu.salahuddin@erainfotechbd.com";
    }

    /**
     * Returns the property value for the given key.
     * <code>getScriptValue</code> returns the value of property for the script.
     *
     * @param key the property key whose value is been searched for
     * @return string value
     */
    public String getScriptValue(String key)
    {
        try
        {
            return(initvalues.getProperty(key));
        }
        catch (Exception e)
        {
            System.out.println("Properties load exception: " + e.getMessage());
            return null;
        }
    }

    /**
     * Reads values from config file init.cfg
     *
     * @return properties object read from the given file
     */
    public Properties getAllValues()
    {
        initvalues = new Properties();
        //Properties comProp = new Properties();
        Properties indProp = new Properties();
        File comFile = new File(comInitFileName);
        File initFile = null;
        initFile = new File(initFileName);

        try
        {
            if (!comFile.canRead())
                System.out.println("Cannot read init filename " + comInitFileName);
            initvalues.load(new FileInputStream(comInitFileName));


            if (initFile == null || !initFile.canRead())
                System.out.println("Cannot read comInit filename " + initFileName);

            indProp.load(new FileInputStream(initFileName));

            // add common properties
            Enumeration en = indProp.propertyNames();
            String key = "";
            String val = "";

            // Read all the elements from common property file
            while ( en.hasMoreElements() ) {
                key = (String)en.nextElement();
                val = indProp.getProperty(key);
                initvalues.setProperty(key, val);
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }

        return initvalues;
    }

    /**
     * Reads values from config file based on a pattern init.cfg
     *
     * @param keyPattern pattern to use for looking up values
     * @return array of String
     */
    public String[] getInitValues (String keyPattern){
        String [] temp = new String [150];
        //Properties prop = getAllValues();
        int i = 0;
        int intValue = 0;
        String[] spl;
        String key = null;
        String value = null;
        int index = 0;
        int countEntry = 0;

        // If keyPattern contains '_' at the end, remove it so the matching condition would work
        if(keyPattern.charAt(keyPattern.length() - 1) == '_')
            keyPattern = keyPattern.substring(0, keyPattern.length() - 1);

        for ( Enumeration en = initvalues.propertyNames(); en.hasMoreElements(); ) {
            key = (String)en.nextElement();
            value = initvalues.getProperty( key );
            if (key.equals(keyPattern) || (key.startsWith(keyPattern) && key.matches(keyPattern + "_[0-9]+(_\\w*)?"))) {
                //Calculate index of this element in the array
                spl = key.split("_");
                index = spl.length - 1;
                //If last unit is not numeric, take one before last
                while (true) {
                    if (!spl[index].matches("[0-9]+?"))
                        index -= 1;
                    else {
                        intValue = (new Integer(spl[index])).intValue();
                        break;
                    }
                    //If there is no numeric component, put it in first element
                    if (index<0) {
                        intValue = 1;
                        break;
                    }
                }

                temp[intValue-1]= value;
                //temp[i] = value;
                System.out.println( key + "="+ value);
                if (i < intValue)
                    i = intValue; // assign the max value to i that is seen
                countEntry++;
            }
        }

        System.out.println( "Found " + countEntry + " entries");

        //Prepare array of exact length
        String [] values = new String [countEntry];
        int counter = 0;
        for (int iter= 0; iter < i; iter++)
        {if (temp[iter] != null)
            values[counter++] = temp[iter];

        }

        return values;
    }

    /**
     * Reads values from config file based on a pattern init.cfg
     * and returns as boolean array.
     *
     * @param keyPattern string pattern to look for
     * @return boolean array
     */
    public boolean[] getBooleanInitValues(String keyPattern) {

        String[] stringValues = getInitValues(keyPattern);
        boolean[] booleanValues = new boolean[stringValues.length];

        for(int index=0; index < booleanValues.length; index++) {

            booleanValues[index] =
                    stringValues[index].equals("true") ? true : false;
        }

        return booleanValues;
    }

    /**
     * Reads values from config file init_common.cfg
     *
     * @throws Exception
     */
    public void initCommonVariables () throws Exception {
        autologs_loc_mount = getScriptValue("autologs_loc_mount");
        autologs_ip = getScriptValue("autologs_ip");
        autologs_user = getScriptValue("autologs_user");
        autologs_pwd = getScriptValue("autologs_pwd");

        log_svr = getScriptValue( "log_svr");
        log_svr_ip = getScriptValue( "log_svr_ip");
        log_user_name = getScriptValue("log_user_name");
        log_user_pwd = getScriptValue("log_user_pwd");

        user_id = getScriptValue("user_id");

        installTimeout = (new Integer(getScriptValue("installTimeout"))).intValue();

        psbudev_mount = getScriptValue("psbudev_mount");
        win_psbudev_mapped_drive = getScriptValue("win_psbudev_mapped_drive");
    }

    /**
     * Sets the script name.
     *
     * @param name script name to be set
     *
     */
    public static void setScriptName(String name)
    {
        scriptName = name;
    }

    /**
     * Returns the debug log filename.
     *
     * @param component
     */
    public static String getDebugLogFileName(String component)
    {
        return (Logger.debugLogDir + ScriptsMgmt.getScript_filename(InitData.scriptName) + Logger.debugLogTag + "_" + component + ".log");
    }

    public static String getDebugLogFileName(String component, String fileExtension)
    {
        return (Logger.debugLogDir + ScriptsMgmt.getScript_filename(InitData.scriptName) + Logger.debugLogTag + "_" + component + fileExtension);
    }
}
