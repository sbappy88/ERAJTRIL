package util;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

//import com.sun.xml.internal.ws.util.ByteArrayDataSource;
//import javax.mail.util.ByteArrayDataSource;

//import sun.tools.jconsole.Utilities;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import util.InitData;


/**
 * <b>Logger</b> class contains the methods for generating the logs.
 *
 */
public class Logger {

    private static FileWriter detailedwriter = null;
    private static FileWriter shortwriter = null;
    private static  Logger logger = null;
    private static String subjHead = "";
    private static String primDefltLogDirName = "Automation_Logs";
    private static String secDefltLogDirName = InitData.projectPath;

    private static String logRootDir = determineLogRoot();
    private static String dir_userid = logRootDir +  InitData.user_id;
    private static String dir_detailed = dir_userid + System.getProperty("file.separator") + "Detailed_Results" + System.getProperty("file.separator");
    private static String dir_short = dir_userid + System.getProperty("file.separator") + "Short_Results" + System.getProperty("file.separator");
    private static String detailedFilename = "";
    private static String shortFilename = "";
    private String archive_folder = dir_userid + System.getProperty("file.separator") + "Archived_Results";
    private String archive_folder_detailed = archive_folder + System.getProperty("file.separator") + "Detailed_Archive" + System.getProperty("file.separator");
    private String archive_folder_short = archive_folder + System.getProperty("file.separator") + "Short_Archive" + System.getProperty("file.separator");
    private String archive_detailed_filename = "";
    private String archive_screenshot_filename = "";
    private String archive_short_filename = "";
    ArrayList<String> DebugLogsFileList = new ArrayList<String>();
    public static final String debugLogTag = "_Debug_Output";
    public static final String debugLogDir = dir_detailed +  debugLogTag + System.getProperty("file.separator");
    public static final String debugLogDetailsDir = debugLogDir + System.getProperty("file.separator") + "Debug Details" +  System.getProperty("file.separator");
    private String archive_folder_debug =  "";
    private final static String detailedLogTag = "_detailedresults_";
    private final static String shortLogTag = "_shortresults_";
    public final static String screenshotLogTag = "_Screenshot_" ;
    private String archiveFileTag = "";
    private static String logsDate = "";
    private boolean passed = true;
    String status = "PASS";

    /**
     * Constructor to initialize the Logger instance.
     */
    protected Logger() {
        //public Logger() {
        logsDate = Utilities.getDateStr();

        //Set the detailed and short filename
        //**detailedFilename = dir_detailed + InitData.appName + detailedLogTag + logsDate + ".log";
        detailedFilename = dir_detailed + "Automation" + detailedLogTag + logsDate + ".log";
        //**shortFilename = dir_short + InitData.appName + shortLogTag + logsDate + ".log";
        shortFilename = dir_short + "Automation" + shortLogTag + logsDate + ".log";
        archive_logs(true);
    }

    /**
     * Returns a static instance of a Logger.
     *
     * @return instance of Logger
     */
    public static Logger getInstance()
    {
        if( logger == null )
            logger = new Logger();

        return logger;

    }

    /**
     * Gets the root directory to put the logs.
     *
     * @return log directory
     */
    public static String determineLogRoot() {
        String dir = "";

        if (!InitData.log_path.equals(""))
            dir = InitData.log_path + System.getProperty("file.separator");
        else if (InitData.operSystem.equalsIgnoreCase(InitData.OS_LNX))
            dir = secDefltLogDirName;
        else {
            //File dfltLogDir = new File( Utilities.getDriveName() + System.getProperty("file.separator") + primDefltLogDirName + System.getProperty("file.separator"));
            File dfltLogDir = new File( "C:" + System.getProperty("file.separator") + primDefltLogDirName + System.getProperty("file.separator"));
            //dir = Utilities.getDriveName() + System.getProperty("file.separator") + primDefltLogDirName + System.getProperty("file.separator");
            dir = "C:" + System.getProperty("file.separator") + primDefltLogDirName + System.getProperty("file.separator");

            //dir = (dfltLogDir.exists() && dfltLogDir.isDirectory()) ?
            //		 primDefltLogDirName : secDefltLogDirName;
            dfltLogDir.mkdir();
        }
        InitData.log_path = dir;
        return dir;
    }

    /**
     * Archives the old results log for current day.
     *
     * @param delete flag to indicate whether to delete the files after archiving logs
     */
    public void archive_logs(boolean delete) {

        File moveFile;
        try {
            //Get the current time
            Calendar cal = new GregorianCalendar();
            int hour24 = cal.get(Calendar.HOUR_OF_DAY);
            int min = cal.get(Calendar.MINUTE);
            int sec = cal.get(Calendar.SECOND);
            archiveFileTag = "_" + hour24 + "_" + min + "_" + sec ;
            archive_folder_debug = archive_folder + System.getProperty("file.separator") + "Detailed_Archive" + System.getProperty("file.separator") + debugLogTag + archiveFileTag + System.getProperty("file.separator");

            // Create user's archive folders
            File archive_folder_file = new File(archive_folder);
            archive_folder_file.mkdir();
            File archive_folder_detailed_file = new File(archive_folder_detailed);
            archive_folder_detailed_file.mkdir();
            File archive_folder_short_file = new File(archive_folder_short);
            archive_folder_short_file.mkdir();
            File archive_folder_debug_file = new File(archive_folder_debug );
            archive_folder_debug_file.mkdir();


            // Move the detailed log file to archive folder
            moveFile = new File(detailedFilename);
            if (moveFile.exists())
            {
                archive_detailed_filename = moveFile.getName() + archiveFileTag + ".log";
                Utilities.copyFile(detailedFilename,archive_folder_detailed + archive_detailed_filename);
                // Delete the file only if parameter "delete" is true
                if (delete)
                    moveFile.delete();
            }

            //get the list of all other lingering detailed  files. archive and delete them
            if (delete)
                archiveDeleteOldFiles(dir_detailed,archive_folder_detailed,detailedLogTag);

            // Move the snapshot file to arhive folder
            String screenshot_file_str = getDetailedResults_folder() + InitData.appName + screenshotLogTag + Utilities.getDateStr()+".jpg";
            moveFile = new File (screenshot_file_str);
            if (moveFile.exists())
            {
                archive_screenshot_filename = moveFile.getName() + archiveFileTag + ".jpg";
                Utilities.copyFile(screenshot_file_str,archive_folder_detailed + archive_screenshot_filename);
                if (delete)
                    moveFile.delete();

            }

            //get the list of all other lingering screenshot files. archive and delete them
            if (delete)
                archiveDeleteOldFiles(dir_detailed,archive_folder_detailed,screenshotLogTag);

            // Move the short log file to archive folder
            moveFile = new File(shortFilename);
            if (moveFile.exists())
            {
                //toDir = new File(archive_folder_short);
                archive_short_filename = moveFile.getName() + archiveFileTag + ".log";
                Utilities.copyFile(shortFilename , archive_folder_short + archive_short_filename + System.getProperty("file.separator"));
                if (delete)
                    moveFile.delete();

            }
            //move the files in archive folder and delete
            if (delete)
                archiveDeleteOldFiles(dir_short,archive_folder_short,shortLogTag);

            // Get the list of files for client logs and then archive them
            DebugLogsFileList = getFileList(debugLogDir,debugLogTag);
            // move the files in archive folder
            if ((DebugLogsFileList.size()) != 0)
            {archiveFolder(debugLogDir,archive_folder_debug ,true);
                // delete old files
                if (delete)
                { moveFile = new File(debugLogDir);
                    moveFile.delete();
                }
            }
        } // end try
        catch (Exception e)
        {
            System.out.println("Exception is " + e.getMessage());
        }
    }

    /**
     * Archives and deletes old list of files where filename has a specific file tag.
     *
     * @param folderName location where old files are located
     * @param archiveFolderName location where the old files need to be archived
     * @param file_tag file tag to look for to archive
     */
    public void archiveDeleteOldFiles(String folderName, String archiveFolderName , String fileTag)
    {
        File moveFile ;
        File dir = new File(folderName);
        String[] children = dir.list();
        if (children != null)
        {
            for (int i=0; i<children.length; i++)
            {
                // Get filename of file or directory
                String filename = children[i];
                if (( filename.indexOf(fileTag) >= 0 ) )
                {
                    moveFile = new File (folderName + filename);
                    try {
                        Utilities.copyFile(folderName + filename , archiveFolderName + filename + fileTag + ".log" );
                        moveFile.delete();
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * This method returns the detailed results folder.
     *
     * @return location of detailed results
     */
    public String getDetailedResults_folder()
    {
        return dir_detailed;
    }

    /**
     * Archives the list of files.
     *
     * @param folderName source folder
     * @param archiveFolderName destination folder to copy to
     * @param fileList list of files that need to be copied
     * @param delete flag to indicate whether to delete the original files in source folder
     */
    public void archiveFiles (String folderName, String archiveFolderName , ArrayList fileList, boolean delete)
    {
        File moveFile ;

        // move the files in archive folder
        for (int count = 0 ; count <fileList.size() ; count++)
        {
            moveFile = new File (folderName + fileList.get(count));

            try {
                Utilities.copyFile(folderName + fileList.get(count) , archiveFolderName + fileList.get(count) + archiveFileTag + ".log" );
                if (delete)
                    moveFile.delete();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }


    /**
     * Copies the folderName to archiveFolderName along with all sub-directories.
     *
     * @param sourceFolder source folder
     * @param archiveFolderName destination folder to copy to
     * @param delete whether to delete the source folder or not
     */
    public void archiveFolder (String sourceFolder, String archiveFolderName, boolean delete) {

        File moveFile ;
        boolean test = false;

        // move the files in archive folder
        moveFile = new File (sourceFolder);

        try {
            Utilities.copyFiles(moveFile  , new File (archiveFolderName) );

            if (delete)
                test = Utilities.deleteDir(moveFile);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }



    /**
     * Gets list of all files which have a specific tag in filename.
     *
     * @param folderName folder to look in
     * @param file_tag tag to match in filename
     * @return list of filenames which matched the tag
     */
    public ArrayList<String> getFileList(String folderName, String file_tag)
    {
        ArrayList<String> fileList = new ArrayList<String>();
        File dir = new File(folderName);
        String[] children = dir.list();
        String filename = "";
        if (children != null)
        {
            for (int i=0; i<children.length; i++)
            {
                // Get filename of file or directory
                filename = children[i];
                if (( filename.indexOf(file_tag) >= 0 ))
                {
                    fileList.add(filename);
                }
            }
        }
        return fileList;
    }


    /**
     * Gets list of all files in a folder.
     *
     * @param folderName folder to look in
     * @return list of filenames in the folder
     */
    public ArrayList<String> getAllFiles(String folderName) {

        ArrayList<String> fileList = new ArrayList<String>();
        File dir = new File(folderName);
        String[] children = dir.list();
        String filename = "";
        if (children != null)
        {
            for (int i=0; i<children.length; i++)
            {
                // Get filename of file or directory
                filename = children[i];
                fileList.add(filename);

            }
        }
        return fileList;
    }
    /**
     * Logs the message in the detailed log file with date and time prefixed to the message.
     *
     * @param msg the message to be logged
     */
    public void logDetail(String msg) {
        logDetailNoTime(getDateTime() + " " +  msg );
    }

    /**
     * Logs the message in the detailed log file and also prints the message to console.
     *
     * @param msg the message to be logged
     */
    public void logDetailAndConsole(String msg)
    {
        logDetail(msg);
        System.out.println(msg);
        //RationalTestScript.logInfo(msg);
    }

    /**
     * Logs the message as an error message.
     *
     * @param msg the message to be logged
     */
    public void logError(String msg)
    {
        logDetail("Error: " + msg);
        System.out.println(msg);
        //RationalTestScript.logInfo(msg);
    }

    /**
     * Gets the date and time as a string.
     *
     * @return string containing date and time
     */
    public static String getDateTime()
    {
        String datestr = null;
        DateFormat dateFormat = DateFormat.getDateTimeInstance( DateFormat.MEDIUM, DateFormat.LONG);
        datestr = dateFormat.format( new Date());
        return datestr;
    }

    /**
     * Logs the message to the detailed log file.
     * The detailed log file is available under C:\Automation\Logs.
     * The naming convention for the detailed log file is "detailedresults_mmmddyyyy.log".
     * This method opens the log file, writes into it and closes it.
     *
     * @param msg the message to be logged
     */
    public void logDetailNoTime(String msg)
    {
        String msg1 = msg + System.getProperty("line.separator");

        char msgbuf[] = new char[msg1.length()];
        msg1.getChars(0,msg1.length(), msgbuf, 0);

        try
        {
            open(true);
            detailedwriter.write(msgbuf);
            detailedwriter.flush();
            close();
        }
        catch( IOException ioe)
        {
            System.out.println("Error Writing detailed log " + msg1);
            ioe.printStackTrace();
        }
    }

    /*
     * Opens the log file for adding log messages.
     * append flag indicates whether to append data to existing
     * data in the file
     */
    private void open(boolean append) {

        try {
            // Creating the folders for detailed and short results
            File base_logs_dir = new File(logRootDir);
            base_logs_dir.mkdirs();

            File new_detailed_dir_userid = new File(dir_userid);
            new_detailed_dir_userid.mkdirs();
            File new_detailed_dir = new File(dir_detailed);
            new_detailed_dir.mkdirs();
            File new_short_dir = new File(dir_short);
            new_short_dir.mkdirs();
            File new_debug_dir = new File(debugLogDir);
            new_debug_dir.mkdirs();
            File new_deug_details_dir = new File (debugLogDetailsDir);
            new_deug_details_dir.mkdirs();
            detailedwriter = new FileWriter( detailedFilename, append);
            shortwriter = new FileWriter( shortFilename, append);

        } catch( Exception e) {
            System.out.println("Error Creating Logger : " + e);
            e.printStackTrace();
        }
    }

    /*
     * Closes the log file.
     */
    private void close()
    {
        try
        {
            detailedwriter.close();
            shortwriter.close();
        }
        catch( IOException ioe )
        {
            System.out.println("Error closing logs " + ioe);
            ioe.printStackTrace();
        }
    }

    /**
     * Sends an email with archive set to false.
     *
     */
    public void sendmail()
    {
        sendmail(false, true);
    }

    /**
     * Sends an email with spool flag specified.
     *
     * @param spool flag to indicate whether to copy application logs or not
     */
    public void sendmail(boolean spool)
    {
        sendmail(true, spool);
    }

    /**
     * Reads the short result file and sends email to the relevant user with the short results.
     *
     * @param archive flag to indicate whether to archive files or not
     * @param spool flag to indicate whether to copy application logs or not
     */

    public void sendmail(boolean archive, boolean spool)
    {
        logDetailAndConsole ("");
        logDetailAndConsole ("Start sendmail");
        logDetailAndConsole ("--------------");

        String folder_name = "";
        String autodev = "psbu-auto-dev";
        //String autodev = "abu.salahuddin";
        //String autodev = "deeptis";
        String buffer = null ;
        String subject = "";
        String folder = "";
        String sendmailTo = "";
        String[] temp = null;
        String getVersion = null;
        if ( (InitData.user_id.equalsIgnoreCase("root")||InitData.user_id.equalsIgnoreCase("") || InitData.user_id.equalsIgnoreCase("labuser")) )
            sendmailTo = InitData.send_mailTo;
        else{
            if (!InitData.user_id.equalsIgnoreCase(InitData.send_mailTo))
                sendmailTo = InitData.user_id  + " " + InitData.send_mailTo ;
            else
                sendmailTo = InitData.send_mailTo;

        }


        String body = "";
        String tempStr = null;
        if (sendmailTo.indexOf(autodev) < 0)
            sendmailTo += " " + autodev;

        String send_mailTo_arr[] = sendmailTo.split(" ");
        //only send mail if send_mail option is set to yes in init file
        if (InitData.send_mail.equals("yes"))
        {
            try {
                String result = "";
                int iteration = 0;
                int tempIter = 0;
                //String ip = Utilities.getLocalHostname().trim().toLowerCase();
                String ip = Utilities.getLocalIPAddress().trim().toLowerCase();

                if (spool) {
                    try {
                        if (InitData.appName.equalsIgnoreCase("vsm"))
                        {
                            folder = logsDate + archiveFileTag;
                            //*****VSMLogger.copyVSMServerLogs(logsDate + archiveFileTag);
                        }

                    }
                    catch (Exception e)
                    {
                        folder_name = "Unable to get application logs. Logs source is not accessible";
                    }
                }
                BufferedReader inputData = new BufferedReader(new FileReader(shortFilename));
                logDetailAndConsole("Started parsing thru the short logs");

                while (true)
                {
                    buffer = inputData.readLine();
                    if(buffer == null) break;

                    // If the short results has Build Date & Build ID then make it as 'subject' for email
                    if (buffer.startsWith("Cisco_VSMS-") ||buffer.startsWith("Cisco_VSOM-") )
                    {
                        getVersion = "";
                        temp = buffer.split("_");
                        for (tempIter = 1; tempIter < temp.length ; tempIter++)
                            getVersion += temp[tempIter];
                        if (iteration > 0)
                            subject = subject + ", " + getVersion;
                        else
                        {
                            subject = getVersion;
                            iteration++;
                        }
                        continue; // If subject is found then go to next line
                    }

                    result = result + "<br>" + buffer ;
                    if ((buffer.indexOf("FAILED") >= 0) || (buffer.indexOf("Failed") >= 0))
                    {
                        status = buffer;
                        passed = false;
                    }

                    if ((buffer.indexOf("Warning") >= 0) || (buffer.indexOf("WARNING") >= 0))
                    {
                        if (!status.contains("Warning"))  // if warning is already in status , do not add one more
                            status = status + " with Warnings";
                        System.out.println("");
                    }

                    //do the error count check
                    if (passed &&  (buffer.indexOf("Passed") < 0) && (buffer.indexOf("Failed") < 0) && (buffer.indexOf("Warning") < 0) && !(buffer.equals("")))
                    {
			 		   /*tempStr = VSMLogger.validateErrorCounts(buffer);
			 		   if ( tempStr != null)
		 	       			status = tempStr;*/
                    }

                }

                archive_logs(archive);
                String logsDir = new File(primDefltLogDirName).getName();
                // This is the link to the documentation
                String logs_header = "<br><br>For more details please refer to logs below:";
                //String logs_message_link = "file://" + "10.21.86.95" + "//" +  primDefltLogDirName +  InitData.user_id  + "\\Archived_Results\\Detailed_Archive\\" + archive_detailed_filename;
                String logs_message_link = "file://" + ip + "//" +  logsDir + "//" + InitData.user_id  + "//Archived_Results//Detailed_Archive//" + archive_detailed_filename;
                String logs_message_str = archive_folder_detailed + archive_detailed_filename;
                String logs_message = (logRootDir.contains( primDefltLogDirName)) ?
                        "<html xmlns:o=\"urn:schemas-microsoft-com:office:office\"" +
                                "xmlns:w=\"urn:schemas-microsoft-com:office:word\"" + "xmlns=\"http://www.w3.org/TR/REC-html40\">" +
                                "<body lang=EN-US link=blue vlink=purple style=\'tab-interval:.5in\'>" + "<div class=Section1>" +
                                "<p class=MsoNormal><a href=\"" + logs_message_link + "\">" +
                                "<span class=SpellE><spanclass=GramE>Detailed Automation Logs</span></span></a></p>" + "</div>" +
                                "</body></html>"
                        :
                        "<br>Detailed Automation Logs are located on your execution machine: " + logs_message_str;

                if (spool) {

                    // These are the logs links provided to the user. This will need addition of testbed name once we have more testbeds.
                    String screenshot_msg_link = "file://" + ip + "//" +  logsDir + "//" + InitData.user_id  + "//Archived_Results//Detailed_Archive//"  + archive_screenshot_filename ;
                    String screenshot_msg = "<html xmlns:o=\"urn:schemas-microsoft-com:office:office\"" +
                            "xmlns:w=\"urn:schemas-microsoft-com:office:word\"" + "xmlns=\"http://www.w3.org/TR/REC-html40\">" +
                            "<body lang=EN-US link=blue vlink=purple style=\'tab-interval:.5in\'>" + "<div class=Section1>" +
                            "<p class=MsoNormal><a href=\"" + screenshot_msg_link + "\">" + "<span class=SpellE><spanclass=GramE>Client Screenshot</span></span></a></p>" + "</div>" + "</body></html>";

                    String app_logs_message_link = "";
                    String app_logs_message = "";

                    // Create link for Application logs - this is specific to VSM for now
                    if (InitData.appName.equalsIgnoreCase("vsm"))
                    {
                        app_logs_message_link = "file:" + InitData.psbudev_mount + "//automation//AutomationLogs//" + folder ;
                        app_logs_message = "<html xmlns:o=\"urn:schemas-microsoft-com:office:office\"" +
                                "xmlns:w=\"urn:schemas-microsoft-com:office:word\"" + "xmlns=\"http://www.w3.org/TR/REC-html40\">" +
                                "<body lang=EN-US link=blue vlink=purple style=\'tab-interval:.5in\'>" + "<div class=Section1>" +
                                "<p class=MsoNormal><a href=\"" + app_logs_message_link + "\">" + "<span class=SpellE><spanclass=GramE>" + InitData.appName.toUpperCase() + "	Logs</span></span></a></p>" + "</div>" + "</body> </html>";
                    }

                    // Send screenshot link if a screenshot file exists.
                    if (!passed && (!archive_screenshot_filename.equals("")) )
                        logs_message = logs_message + screenshot_msg;

                    File moveFile;
                    String debug_logs_link = null;
                    String debug_logs = null;

                    // Create links for all the Debug log outputs
                    if (DebugLogsFileList.size() > 0)
                    {
                        // if file size < 0 then don't send the link to logs.
                        moveFile = new File (archive_folder_debug  );
                        debug_logs_link =  "file://" + ip + "//" +  logsDir + "//" + InitData.user_id  + "//Archived_Results//Detailed_Archive//"  +  debugLogTag + archiveFileTag;

                        debug_logs = "<html xmlns:o=\"urn:schemas-microsoft-com:office:office\"" +
                                "xmlns:w=\"urn:schemas-microsoft-com:office:word\"" + "xmlns=\"http://www.w3.org/TR/REC-html40\">" +
                                "<body lang=EN-US link=blue vlink=purple style=\'tab-interval:.5in\'>" + "<div class=Section1>" +
                                "<p class=MsoNormal><a href=\"" + debug_logs_link + "\">" + "<span class=SpellE><spanclass=GramE>Client Debug Logs " +"</span></span></a></p>" + "</div>" + "</body></html>";



                        // Deploy logs to be sent only if the size of deploy logs file > 0
                        if (passed)
                            //body =  result + logs_header + logs_message + app_logs_message + deploy_logs;
                            body =  result + logs_header + logs_message + debug_logs + app_logs_message;
                        else
                            //body =  result + "<br>" + referDocMsg + logs_header + logs_message +  app_logs_message + deploy_logs;
                            body =  result + "<br>" + logs_header + logs_message + debug_logs + app_logs_message;

                    }else
                        body = result + logs_header + logs_message +  app_logs_message ;

                }//if (spool)

                try {
                    System.out.println("Sending email");
                    if (spool)
                        //postMail(send_mailTo_arr,InitData.appName.toUpperCase() + " Automation on " + InitData.app_host_name + " : " + status + " : " + getSubjHead() ,body,autodev + "@jaxara.com");
                        postMail(send_mailTo_arr,"Test eami subject","Test email body","sbappy88@yahoo.com");
                    else
                        //postMail(send_mailTo_arr,InitData.appName.toUpperCase() + " Automation on " + InitData.app_server_ip + " : " + status + " : " + getSubjHead() ,body,"sbappy88@yahoo.com");
                        postMail(send_mailTo_arr,"Test eami subject","Test email body","sbappy88@yahoo.com");

                    logDetailAndConsole(result);

                }
                catch (Exception e)
                {
                    System.out.println(e.getMessage());
                }
                finally
                {
                    logger.logDetailAndConsole ("End sendmail");
                    logger.logDetailAndConsole ("------------");
                    inputData.close();
                }
            }
            catch ( Exception e)
            {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Sends an email with the given subject and message to multiple recipients.
     *
     * @param recipients users who will recieve the email
     * @param subject subject header of the email
     * @param message message body of the email
     * @param from user who is sending the email
     * @throws Exception if sending of email fails
     */

    public static void postMail( String recipients[ ], String subject, String message , String from) throws Exception
    {
        boolean debug = false;

        try {
            //Set the host smtp address
            Properties props = new Properties();
            props.put("mail.smtp.host", "192.168.4.98");

            // create some properties and get the default Session
            Session session = Session.getDefaultInstance(props, null);
            session.setDebug(debug);

            // create a message
            MimeMessage msg = new MimeMessage(session);

            // set the from and to address
            InternetAddress addressFrom = new InternetAddress(from);
            msg.setFrom(addressFrom);

            InternetAddress[] addressTo = new InternetAddress[recipients.length];
            for (int i = 0; i < recipients.length; i++)
            {
                addressTo[i] = new InternetAddress(recipients[i]);
            }
            msg.setRecipients(MimeMessage.RecipientType.TO, addressTo);

            // Optional : You can also set your custom headers in the Email if you Want
            // Setting the Subject and Content Type
            msg.setSubject(subject);
            //***msg.setDataHandler(new DataHandler(new ByteArrayDataSource(message.toString(), "text/html")));

            Transport.send(msg);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Logs the message to the short log file.
     * The short log file is available under C:\VCC_Automation\VCC_Logs\Short_Results.
     * The naming convention for the short log file is "vccshortresults_mmmddyyyy.log".
     * This method opens the log file, writes into it and closes it.
     *
     * @param msg the message to be logged
     */
    public void logShort(String msg) {

        String msg1 = msg;
        // remove the path scripts. if it exists in the script name
        if (msg1.startsWith("vframe.scripts."))
        {
            int index_dot = msg1.indexOf(".");
            int msg_length = msg1.length();
            msg1 = msg1.substring(index_dot + 1 ,msg_length);
        }

        msg1 = msg1 + System.getProperty("line.separator");

        char msgbuf[] = new char[msg1.length()];
        msg1.getChars(0,msg1.length(), msgbuf, 0);

        try
        {
            open(true);
            shortwriter.write(msgbuf);
            shortwriter.flush();
            close();
        }
        catch( IOException ioe)
        {
            System.out.println("Error Writing short log "  + ioe + ". Log Message "+ msg1);
            ioe.printStackTrace();
        }
    }

    /**
     * Sets the subject header in the email.
     *
     * @param head email subject header string
     */
    public static void setSubjHead (String head) {
        if (head != null)
            subjHead += head;
    }

    /**
     * Returns the subject header.
     *
     * @return email subject header
     */
    public static String getSubjHead () {
        return subjHead;
    }

    public static void testResultbyEmail() {

        String mailSmtpHost = "mail.erainfotechbd.com";

        String mailTo = "abu.salahuddin@erainfotechbd.com";
        String send_mailTo_arr[] = InitData.send_mailTo.split(" ");
        String mailCc = "sbappy88@gmail.com";
        String mailFrom = "abu.salahuddin@erainfotechbd.com";
        String mailSubject = "Automation Test Result - " + logsDate;
        String mailText = "This is an email regarding Automation Test Result of JTRL";
        String[] myarray = null;
        String string = null;
        byte[] serObj = null;

        try {

            File fl = new File(shortFilename);
            serObj = getBytesFromFile(fl);
            string = new String(serObj);
        } catch( IOException ex ) {
            ex.printStackTrace();
        }

        //send email
        sendEmail(send_mailTo_arr, mailCc, mailFrom, mailSubject, string, mailSmtpHost);

    }//testResultbyEmail

    public static void sendEmail(String to [], String cc, final String from, String subject, String text, String smtpHost) {
        try {
            Properties properties = new Properties();
            //properties.put("mail.smtp.host", smtpHost);
            properties.put("mail.smtp.host", "mail.erainfotechbd.com"); //SMTP Host
            properties.put("mail.smtp.port", "587"); //TLS Port 587 or 465
            properties.put("mail.smtp.auth", "true"); //enable authentication
            //properties.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS

            Authenticator auth = new Authenticator() {
                //override the getPasswordAuthentication method
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(from, "029115571");
                }
            };

            Session emailSession = Session.getDefaultInstance(properties, auth);
            System.out.println("Session created");



            InternetAddress[] addressTo = new InternetAddress[to.length];
            for (int i = 0; i < to.length; i++)
            {
                addressTo[i] = new InternetAddress(to[i]);
            }

            Message emailMessage = new MimeMessage(emailSession);
            emailMessage.addHeader("Content-type", "text/HTML; charset=UTF-8");
            emailMessage.addHeader("format", "flowed");
            emailMessage.addHeader("Content-Transfer-Encoding", "8bit");
            emailMessage.setFrom(InternetAddress("abu.salahuddin@erainfotechbd.com", "Admin SQA"));
            emailMessage.setReplyTo(InternetAddress.parse("abu.salahuddin@erainfotechbd.com", false));
            emailMessage.setSubject(subject);
            //emailMessage.setText(text);
            emailMessage.setContent(text,"text/html" );
            emailMessage.setSentDate(new Date());
            //**emailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(addressTo));
            emailMessage.setRecipients(MimeMessage.RecipientType.TO, addressTo);
            //**emailMessage.addRecipient(Message.RecipientType.CC, new InternetAddress(cc));
            emailMessage.setFrom(new InternetAddress(from));
            //emailMessage.setSubject(subject);
            //emailMessage.setText(text);
            //emailMessage.setDataHandler(new DataHandler(new ByteArrayDataSource(text, "text/html")));

            emailSession.setDebug(true);

            Transport.send(emailMessage);
        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }//end sendEmail

	/*
	public static void sendEmail(String to [], String cc, String from, String subject, byte[] text, String smtpHost) {
		try {
			Properties properties = new Properties();
			properties.put("mail.smtp.host", smtpHost);
			Session emailSession = Session.getDefaultInstance(properties);

			InternetAddress[] addressTo = new InternetAddress[to.length];
		    for (int i = 0; i < to.length; i++)
		    {
		    	addressTo[i] = new InternetAddress(to[i]);
		    }

			Message emailMessage = new MimeMessage(emailSession);
			//**emailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(addressTo));
			emailMessage.setRecipients(MimeMessage.RecipientType.TO, addressTo);
			//**emailMessage.addRecipient(Message.RecipientType.CC, new InternetAddress(cc));
			emailMessage.setFrom(new InternetAddress(from));
			emailMessage.setSubject(subject);
			//emailMessage.setText(text);
			//emailMessage.setDataHandler(new DataHandler(new ByteArrayDataSource(text, "text/html")));

			emailSession.setDebug(true);

			Transport.send(emailMessage);
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}*/

    private static Address InternetAddress(String string, String string2) {
        // TODO Auto-generated method stub
        return null;
    }

    public static byte[] getBytesFromFile(File file)throws IOException
    {
        InputStream is = null;
        is = new FileInputStream(file);
        // Get the size of the file
        long length = file.length();
        // You cannot create an array using a long type.
        // It needs to be an int type.
        // Before converting to an int type, check
        // to ensure that file is not larger than Integer.MAX_VALUE.
        if (length > Integer.MAX_VALUE) {
            // File is too large
        }
        // Create the byte array to hold the data
        byte[] bytes = new byte[(int) length];
        // Read in the bytes
        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length
                && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
            offset += numRead;
        }
        // Ensure all the bytes have been read in
        if (offset < bytes.length) {
            throw new IOException("Could not completely read file " + file.getName());
        }
        // Close the input stream and return bytes
        is.close();
        return bytes;
    }//getBytesFromFile


}