package util;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Properties;
import util.AutomationException;
import util.InitData;
import util.Utilities;

public class MainInitData extends InitData
{

    protected static MainInitData ipicsinit = null;
    public static final String APP_DIR = "";
    public static String browser_path = "";

    public static String app_admin_username = "";
    public static String app_admin_password = "";
    public static String media_path = "";
    // Common variables
    public static String admin_role = "";
    public static String operator_role = "";
    public static int browserLaunch_Timeout=0;

    public static String[] server_ip = {};
    public static String[] server_name = {};
    public static String[] server_type = {};

    public static String server_version = "";
    public static String client_version = "";

    /**
     * Constructor to initialize valencia app name, variables and
     * common variables.
     *
     * @throws Exception
     */
    protected MainInitData() throws Exception {
        super();
        appName = "Valencia";
        initIPICSVariables();
        initIPICSCommonVariables();
    }

    public static MainInitData getInstance() throws Exception
    {
        if( ipicsinit == null )
            ipicsinit = new MainInitData();
        return ipicsinit;
    }

    public static void setInitNames(String ip, String userid)
    {
        user_id = userid;
        app_path = APP_DIR;
    }

    public void initIPICSVariables () throws Exception {
        browser_path = getScriptValue("browser_path");
        app_admin_username = getScriptValue("app_admin_username");
        app_admin_password = getScriptValue("app_admin_password");

        server_ip = getInitValues("server_ip");
        server_name = getInitValues("server_name");
        server_type = getInitValues("server_type");
        app_host_name = getScriptValue("app_host_name");
        app_root_username = getScriptValue("app_root_username");
        app_root_password = getScriptValue("app_root_password");
        app_server_prompt = getScriptValue("app_server_prompt");
        app_server_ip = getScriptValue("app_server_ip");

    }

    public void initIPICSCommonVariables () throws Exception
    {
        browserLaunch_Timeout = (new Integer(getScriptValue("browserLaunch_Timeout"))).intValue();
        admin_role = getScriptValue("admin_role");
        operator_role = getScriptValue("operator_role");
        autologs_loc_mount = getScriptValue("autologs_loc_mount");
    }

    public static void preCheckExec() throws Exception
    {
        String operSystem = Utilities.findOS();
        pathSeparator = System.getProperty("file.separator");
        setInitNames(operSystem, "valencia", readInitFilename(), readBuildID());
    }

    //This method must be called before calling getInstance() in test classes
    public static void setInitNames(String os, String apppath, String initfile, ArrayList buildID)
    {
        operSystem = os;
        app_path = apppath;
        initFileName = projectPath + pathSeparator + initPath + pathSeparator + initfile;
        build_id = buildID;
    }

    public static String readInitFilename() throws Exception {
        String name = null;
        Properties m_prop = new Properties();
        String fileStr = projectPath + pathSeparator + "Resources\\Data" + pathSeparator + "init_filename.cfg";

        // Read Init_Filename.cfg
        File varFile = new File(fileStr);
        if (!varFile.canRead())
        {
            throw new AutomationException("Cannot read filename " + fileStr);
        }

        try
        {
            m_prop.load(new FileInputStream(fileStr));
            name = m_prop.getProperty("filename");
            send_mailTo = m_prop.getProperty("send_mailTo");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new AutomationException("Cannot read init filename from " + fileStr);
        }

        return name;
    }

    /*
     * Gets build Id.
     */
    public static ArrayList<String> readBuildID() throws Exception
    {
        ArrayList<String> buildInfo = new ArrayList<String>();
        Properties m_prop = new Properties();
        String fileStr = projectPath + pathSeparator + "Resources\\Data" + pathSeparator + "init_filename.cfg";

        // Read Init_Filename.cfg
        File varFile = new File(fileStr);
        if (!varFile.canRead())
        {
            throw new AutomationException("Cannot read filename " + fileStr);
        }

        try {
            m_prop.load(new FileInputStream(fileStr));
            // name = m_prop.getProperty("build_id");
            server_version = m_prop.getProperty("server_version");
            client_version = m_prop.getProperty("client_version");

            buildInfo.add(server_version);
            buildInfo.add(client_version);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new AutomationException("Cannot read init filename from " + fileStr);
        }
        return buildInfo;
    }

}