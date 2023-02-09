package util;



import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
//import java.util.ListIterator;
import java.util.StringTokenizer;



/**
 * <b>Utilities</b> has the library for defining generic methods to be
 * used across applications.
 *
 */

public class Utilities {

    /**
     * <code>getInitValues</code> returns the array of values from init file
     * where a key matches a pattern
     *
     * @param String key_pattern
     * @return	String [] values
     */

    //static Logger logger = Logger.getInstance();

    /**
     * Returns a string IP Address of local host.
     *
     * @return ip address
     */
    public static String getLocalIPAddress () throws Exception {
        String ipAddress = null;
        InetAddress addr = InetAddress.getLocalHost();

        // Get IP Address
        //byte[] ipAddr = addr.getAddress();

        // Get hostname
        ipAddress = addr.getHostAddress();
        return ipAddress;
    }

    /**
     * Returns a string host name of local host.
     *
     * @return host name
     */
    public static String getLocalHostname () throws Exception {
        //String ipAddress = null;
        InetAddress addr = InetAddress.getLocalHost();
        String hostname = addr.getHostName();

        return hostname;
    }


    /**
     * Generates and returns an int random number between 0 and max limit provided.
     *
     * @param max max limit of random number
     * @return	random number within 0 to max interval
     */
    public static int getRandomInt (int max) throws Exception {
        // Value from 0.0 to 1.0
        double d = Math.random();
        int random = (int)(max * d);

        return random;
    }

    /**
     * Evaluates the drive name of the test project.
     *
     * @return drive name
     */
    public static String getDriveName() {

        String currentDirName = System.getProperty("user.dir");
        int drive_name_index = currentDirName.indexOf(":");
        String drive_name = currentDirName.substring(0,drive_name_index + 1);
        return drive_name;
    }

    /**
     * Extracts int value from a string and returns the int value.
     *
     * @param str string with int in it
     * @return int value from the string
     *
     */
    public static int getIntegerValue(String str) {

        int intVal = 0;
        StringTokenizer st = new StringTokenizer(str," ");

        while (st.hasMoreTokens()) {
            try {
                intVal = Integer.parseInt(st.nextToken()) ;

            } catch (NumberFormatException e) {
            }
        }
        return intVal;
    }

    /**
     * Maps drive to the 'mapping'.
     * <br><br><b>Usage example:</b> <br>
     * Utilities.mapDrives("f:",  " \\\\172.29.333.22\\C$ "  + win_mig_user_password + " /USER:workgroup\\" + win_mig_user_name +  " /persistent:yes" );
     *
     * @param driveName drive name
     * @param mapping mapping
     * @return <b>true</b> if mapping is successful
     */
    public static boolean mapDrives(String driveName, String mapping ) {

        String cmd1 = "cmd.exe /C net use " +  driveName + " /delete";
        String cmd2 = "cmd.exe /C net use " +  driveName + mapping;

        System.out.println(cmd1 + "\n" + cmd2);
        Runtime rt = Runtime.getRuntime();

        try{
            Process currentProcess = rt.exec(cmd1);
            currentProcess.waitFor();
        }
        catch (Exception excep)
        {
            System.out.println(excep.getMessage());
        }

        try{
            Process currentProcess = rt.exec(cmd2);
            currentProcess.waitFor();
        }
        catch (Exception excep)
        {
            System.out.println(excep.getMessage());
        }

        File toFile = new File(driveName);
        if (toFile.exists())
            return true;

        return false;
    }


    /**
     * Gets the stack trace as a string representation.
     *
     *   @param t throwable exception
     *   @return exception as a string
     */
    public static String getStackTraceFrom(final Throwable t)
    {
        ByteArrayOutputStream oss = new ByteArrayOutputStream();
        PrintStream print_stream = new PrintStream(oss);
        t.printStackTrace(print_stream);
        return oss.toString();
    }

    /**
     * Returns a string representation of an Object.
     * If the object is null, a "null" string is return else
     * the <code>toString()</code> method is invoked and the object
     * and the result is returned.
     *
     * @param object object which needs to be converted to string
     * @return string representation of an object
     */
    public static String toString(final Object object)
    {
        return
                (object != null ? object.toString() : "null");
    }

    /**
     * Removes the extra spaces from a string. If there are two spaces
     * back to back, one is removed.
     *
     * @param line line with extra spaces
     * @return string with just one space instead of two
     */
    public static String concatSpacesToOne(String line) {

        while(line.indexOf("  ") > 0 ) {

            line = line.replaceAll("  ", " ");
        }
        return line;
    }

    /**
     * Returns the current date in string format.
     *
     * @return date string
     */
    public static String getDateStr()
    {
        Date date = new Date();
        String dstr = date.toString();

        String[] tok = dstr.split(" ");

        String mon = tok[1].trim();
        String day = tok[2].trim();
        String year = tok[5].trim();

        String datestr = mon + day + year;
        return(datestr);
    }

    /**
     * Matches pattern in the Expect output
     *
     * @param pattern pattern to be matched
     * @param exp expect
     */
	/*public static boolean expMatchPattern(String pattern, Expect exp )
	{
		ListIterator li = null;
		String reply = null;
		boolean rc = false;

		try {
			li = exp.expectAnything();
			while (li.hasNext())
			{
				reply = li.next().toString();
				if (reply.indexOf(pattern) >= 0) {
					rc = true;
					break;
				}
			}
  		}
   		catch (Exception excep)
		{
   		  System.out.println(excep.getMessage());
		}
		return rc;
	}
	 */
    /**
     * Cleans the console.
     *
     * @param args arguments containing the ip, port, password
     * @throws Exception if it fails to clear the console
     */

	/*
	public static void cleanConsole(String [] args) throws Exception
    {
		Logger logger = Logger.getInstance();

		logger.logDetailAndConsole("");
		logger.logDetailAndConsole("Cleaning Console");
		logger.logDetailAndConsole("----------------");

		String term_port = args[0];
		String term_ip = args[1];
		String term_pwd = args[2];
		String term_en_pwd = args[3];
		String telnet_port = "23";

	    //Default values
		int max_attempts = 4;

		int attempts = 0;
		int ret_code = -1; //Failure
		Expect exp = new Expect();
		while (attempts < max_attempts) {
			try {
				attempts++;
				exp.setVerboseOff();
				exp.setDelay(6000); // 1min
				System.out.println("Connection string: "
						+ term_ip + " " + telnet_port);
				exp.open(term_ip.trim() + " " + telnet_port);
				Thread.sleep(1);
				exp.send(Expect.CRLF);
				Thread.sleep(1);
				exp.expectToEnd("assword"); // Stupid way to support {Pp}assword
				exp.send(term_pwd.trim() + Expect.CRLF);
				exp.send("enable" + Expect.CRLF);
				//sleep(3);
				exp.expectToEnd("assword"); // Stupid way to support {Pp}assword
				exp.send(term_en_pwd.trim() + Expect.CRLF);
				System.out.println("Connected");
				System.out.println("Clear string: " + "clear line " + term_port);
				for (int i=0; i<5; i++) {
					exp.send("clear line " + term_port);
					exp.send(Expect.CRLF);
					exp.expectToEnd("[confirm]");
					//sleep(5);
					exp.send(Expect.CRLF);
				}
				ret_code = 0;
				System.out.println("Cleared");
				exp.close();
				break;
			}
			catch (Exception e)
			{
				try
				{
					exp.close();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
			}
		}
		if (ret_code < 0)
			logger.logDetailAndConsole("Serial console NOT cleared for " + term_ip + " " + term_port);
		else
			logger.logDetailAndConsole("Serial console cleared for " + term_ip + " " + term_port);

		logger.logDetailAndConsole("End Cleaning Console");
		logger.logDetailAndConsole("--------------------");

    }

	public static void stopVnc()
	{
		VCCGetInitData initdata = VCCGetInitData.getInstance();
		Logger logger = Logger.getInstance();

		String vnc_pwd_autodev = initdata.getScriptValue( "vnc_pwd_autodev");
		//int indexOf_test = (VCCGetInitData.project_path).indexOf("test");
		vnc_pwd_autodev = (VCCGetInitData.project_path) + "\\" + vnc_pwd_autodev;

		String cmd = "winvnc4.exe -disconnect";
		Runtime rt = Runtime.getRuntime();

		try {
			Process currentProcess = rt.exec(cmd);
			currentProcess.waitFor();
			logger.logDetailAndConsole("Disconnected VNC clients");
			cmd = "cmd.exe /C \"regedit /s " + vnc_pwd_autodev + "\"";
			currentProcess = rt.exec(cmd);
			currentProcess.waitFor();
			logger.logDetailAndConsole("Changed VNC password to autodev");
		}
   		catch (Exception ex)
		{
   			System.out.println(ex.getMessage());
		}
	}

   		public static void startVnc()
   		{
   			VCCGetInitData initdata = VCCGetInitData.getInstance();
   			Logger logger = Logger.getInstance();

   			String vnc_pwd_common = initdata.getScriptValue( "vnc_pwd_common");
   			//int indexOf_test = (VCCGetInitData.project_path).indexOf("test");
   			vnc_pwd_common = (VCCGetInitData.project_path) + "\\" + vnc_pwd_common;

   			String cmd = "winvnc4.exe -disconnect";
   			Runtime rt = Runtime.getRuntime();

   			try {
   				Process currentProcess = rt.exec(cmd);
   				currentProcess.waitFor();
   				logger.logDetailAndConsole("Disconnected VNC clients");
   				cmd = "cmd.exe /C \"regedit /s " + vnc_pwd_common + "\"";
   				currentProcess = rt.exec(cmd);
   				currentProcess.waitFor();
   				logger.logDetailAndConsole("Changed VNC password to common");
   			}
   	   		catch (Exception ex)
   			{
   	   		  System.out.println(ex.getMessage());
   			}
   		}
*/

    /**
     * Copies one file to another file.
     *
     * @param fromFileName file to be copied from
     * @param toFileName file to be copied to
     */
    public static void copyFile(String fromFileName, String toFileName)
            throws IOException {

        File fromFile = new File(fromFileName);
        File toFile = new File(toFileName);

        if (!fromFile.exists())
            throw new IOException("FileCopy: " + "no such source file: "
                    + fromFileName);
        if (!fromFile.isFile())
            throw new IOException("FileCopy: " + "can't copy directory: "
                    + fromFileName);
        if (!fromFile.canRead())
            throw new IOException("FileCopy: " + "source file is unreadable: "
                    + fromFileName);

        if (toFile.isDirectory())
            toFile = new File(toFile, fromFile.getName());

        if (toFile.exists()) {
            if (!toFile.canWrite())
                throw new IOException("FileCopy: "
                        + "destination file is unwriteable: " + toFileName);
        } else {
            String parent = toFile.getParent();
            if (parent == null)
                parent = System.getProperty("user.dir");
            File dir = new File(parent);
            if (!dir.exists()) {
                boolean success = (new File(parent)).mkdir();
                if (!success)
                    throw new IOException("FileCopy: "
                            + "cannot create destination directory: " + parent);
            }

            if (dir.isFile())
                throw new IOException("FileCopy: "
                        + "destination is not a directory: " + parent);
            if (!dir.canWrite())
                throw new IOException("FileCopy: "
                        + "destination directory is unwriteable: " + parent);
        }

        FileInputStream from = null;
        FileOutputStream to = null;
        try {
            from = new FileInputStream(fromFile);
            to = new FileOutputStream(toFile);
            byte[] buffer = new byte[4096];
            int bytesRead;

            while ((bytesRead = from.read(buffer)) != -1)
                to.write(buffer, 0, bytesRead); // write
        } finally {
            if (from != null)
                try {
                    from.close();
                } catch (IOException e) {}
            if (to != null)
                try {
                    to.close();
                } catch (IOException e) {}
        }
    }

    /**
     * Copies recursively directories or files from source to destination.
     *
     * @param src source file
     * @param dest destination file
     *
     * @throws IOException
     */
    public static void copyFiles(File src, File dest) throws IOException {

        //Check to ensure that the source is valid...
        if (!src.exists()) {
            throw new IOException("copyFiles: Can not find source: " + src.getAbsolutePath()+".");

        } else if (!src.canRead()) { //check to ensure we have rights to the source...
            throw new IOException("copyFiles: No right to source: " + src.getAbsolutePath()+".");
        }

        //is this a directory copy?
        if (src.isDirectory())         {
            if (!dest.exists()) { //does the destination already exist?
                //if not we need to make it exist if possible (note this is mkdirs not mkdir)
                if (!dest.mkdirs()) {
                    throw new IOException("copyFiles: Could not create direcotry: " + dest.getAbsolutePath() + ".");

                }

            }

            //get a listing of files...
            String list[] = src.list();
            //copy all the files in the list.
            for (int i = 0; i < list.length; i++)
            {
                File dest1 = new File(dest, list[i]);
                File src1 = new File(src, list[i]);
                copyFiles(src1 , dest1);
            }

        } else {

            //This was not a directory, so lets just copy the file

            FileInputStream fin = null;
            FileOutputStream fout = null;

            byte[] buffer = new byte[4096]; //Buffer 4K at a time (you can change this).
            int bytesRead;
            try {

                //open the files for input and output
                fin =  new FileInputStream(src);
                fout = new FileOutputStream (dest);

                //while bytesRead indicates a successful read, lets write...
                while ((bytesRead = fin.read(buffer)) >= 0) {
                    fout.write(buffer,0,bytesRead);

                }

            } catch (IOException e) { //Error copying file...

                IOException wrapper = new IOException("copyFiles: Unable to copy file: " +
                        src.getAbsolutePath() + "to" + dest.getAbsolutePath()+".");
                wrapper.initCause(e);
                wrapper.setStackTrace(e.getStackTrace());
                throw wrapper;

            } finally { //Ensure that the files are closed (if they were open).

                if (fin != null)
                    fin.close();

                if (fout != null)
                    fout.close();

            }

        }

    }

    /**
     * Copies directory from on elocation to another.
     *
     * @param sourcePath location of source directory
     * @param destinationPath location of destination directory
     * @throws IOException
     */
    public static void copyDirectory(String sourcePath, String destinationPath)
            throws IOException {

        File srcPath = new File(sourcePath);
        File destPath = new File(destinationPath);

        if(!srcPath.exists())
            throw new IOException("DirectoryCopy: no such directory found: " + srcPath);

        if(!srcPath.isDirectory())
            throw new IOException("DirectoryCopy: " + srcPath + " is not a directory");

        if(!destPath.exists())
            destPath.mkdir();

        String files[] = srcPath.list();

        for(int i = 0; i < files.length; i++)
        {
            //System.out.println("Call CopyFile(" + sourcePath + files[i].toString() + ", " +
            //		            destinationPath + files[i].toString() + ")" );
            copyFile(sourcePath + files[i].toString(), destinationPath + files[i].toString());
        }
    }


    /**
     * Deletes the directory after deleting the contents of the directory.
     *
     * @param dir directory
     * @return <b>true</b> if the delete is successful
     */
    public static boolean deleteDir(File dir) {

        if (dir.isDirectory()) {

            String[] children = dir.list();
            boolean success;

            for (int i=0; i<children.length; i++) {
                success = deleteDir(new File(dir, children[i]));
                if (!success)
                    return false;
            }
        }
        // The directory is now empty so delete it
        return dir.delete();
    }

    /**
     * Returns the number of occurrences of the file name in the given pattern.
     *
     * @param pattern string pattern
     * @param fileName name of the file to be matched in the pattern
     * @return number of occurrences
     */
    public static int getNumberOfOccurrences(String pattern, String fileName)
            throws Exception {

        int num = 0;
        String line = null;

        if ((fileName == null) || (fileName == ""))
            throw new IllegalArgumentException("Not valid file name: " + fileName);

        BufferedReader in = new BufferedReader(new FileReader(fileName));
        if (!in.ready())
            throw new IOException();

        while ((line = in.readLine()) != null) {
            if (line.indexOf(pattern) >= 0 )
                num++;
        }

        in.close();
        return num;
    }

    /**
     * Clears any previously generated client log files.
     *
     * @param pattern file name pattern
     * @return <b>true</b> if deletion is successful
     */
    public static boolean clearPreviousClientLog(String pattern)
    {
        //Find current user home directory where client log is located
        String fullHome = System.getProperty("user.home");
        String shortHome = (new File(fullHome)).getName();
        String client_path="C:\\Documents and Settings\\" + shortHome + "\\VFrame\\";
        String client_file=pattern;

        // Delete old client log
        String cmd = "cmd.exe /C del \"" + client_path + client_file + "\"";
        Runtime rt = Runtime.getRuntime();
        try{
            System.out.println("clear client logs cmd: " + cmd);
            Process currentProcess = rt.exec(cmd);
            currentProcess.waitFor();
            return true;
        }
        catch (Exception excep)
        {
            System.out.println(excep.getMessage());
        }
        return false;
    }

    /**
     * Deletes files which have pattern in the filename.
     * Note that regular expressions are not supported in this method.
     * Only * is supported which will delete all files.
     *
     * @param folder the folder from which files are to be deleted
     * @param pattern file name pattern
     */
    public static void delete_files(String folder , String pattern)
    {
        try {
            File dir = new File(folder);
            String[] children = dir.list();
            String filename="";
            File deleteFile;
            if (children != null)
            {
                for (int i=0; i<children.length; i++) {
                    // Get filename of file or directory
                    filename = children[i];
                    if (( filename.indexOf(pattern) >= 0 ) || pattern.equals("*") )
                    {
                        deleteFile = new File(folder + filename);
                        deleteFile.delete();
                        System.out.println(filename + " deleted");
                    }
                }
            }
        } // end try
        catch (Exception e)
        {
            System.out.println("Exception is " + e.getMessage());
        }
    }

    /**
     * Searches for a filename in sub-directories
     * along with current directory.
     *
     * @param path location where file is to be searched
     * @param findFilename name of the file to be searched
     * @return full path of the file
     */
    public static String  findFile (String path , String findFilename)
    {
        File dirPath = new  File(path);
        String[] children = dirPath.list();
        String filename = "";
        String dirFilename = "";

        String path1 = "";
        if (children == null) {
            // Either dir does not exist or is not a directory
        }
        else {

            for (int i=0; i<children.length; i++)
            {
                //dirFilename = Path + File.separatorChar + children[i];
                dirFilename = path + InitData.pathSeparator + children[i];
                // Get filename of file or directory
                if (new File(dirFilename).isDirectory())
                {
                    path1 = findFile(path + children[i],findFilename);
                    if (path1 != "")
                        return path1;
                }
                else
                {
                    filename = children[i];
                    // if filename is a simple file then check if this is the one we are looking for.
                    if (( filename.equals(findFilename) ))
                    {
                        return path + File.separatorChar + children[i];
                    }
                }
            }
        }
        return path1;
    }

    /**
     * copied client log files to the specified "user_dir_name"
     *
     */
	/*public static void copyClientLogs(String user_dir_name, String client_path)
	{
		try {
			File dir = new File(client_path);
			String filename = "";
			String[] children = dir.list();

			if (children == null) {
				// Either dir does not exist or is not a directory
			}
			else {
		        for (int i=0; i<children.length; i++) {
		            // Get filename of file or directory
		            filename = children[i];
		            if ( filename.indexOf("client_") >= 0 )
		            {
		            	Utilities.copyFile (client_path + filename,
		                    		InitData.logs_mapped_drive + InitData.pathSeparator + "vcc_logs_" +
		                    		Utilities.getLocalHostname() + InitData.pathSeparator +  user_dir_name +
		                    		InitData.pathSeparator +filename);

		                System.out.println(filename);
		            }
		        }
	        }


		} // end try
		catch (Exception e)
		{
			System.out.println("Exception is " + e.getMessage());
		}
	}*/

    /**
     * Formats the MAC/WAN Address.
     *
     * @param address mac/wwn address
     * @return formatted MAC/WWN address
     */
    public static String formatMACorWWN(String address) {
        if (address == null) {
            return "";
        }
        if (address.indexOf(":") != -1)
            return address;
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < address.length() - 1; i += 2) {
            if (sb.length() > 0)
                sb.append(":");
            sb.append(address.substring(i, i + 2));
        }
        return sb.toString();
    }

    /**
     * Changes the given IP Address by 1.
     *
     * @param address ip address
     * @return
     */
    public static String changeIPAddressBy1(String address)
    {
        if (address == null)
            return "";
        if (address.indexOf(".") != -1)
            return address;

        char[] newIP = address.toCharArray();
        int last = newIP.length - 1;
        if (newIP[last] > 0)
            newIP[last] -= 1;
        else
            newIP[last] += 1;

        return newIP.toString();
    }

    /**
     * Returns the java method name.
     *
     * @return method name
     * @throws Exception
     */
    public static String getJavaMethodName() throws Exception {

        int ln = (InitData.rftFlag) ? 3 : 2;
        String name = (Thread.currentThread().getStackTrace())[ln].getMethodName();

        return name;
    }

    /**
     * Stops the VNC client.
     *
     */
    public static void stopVnc() {
        Logger logger = Logger.getInstance();
        //String vnc_pwd_autodev = (InitData.project_path) + "\\" + InitData.vnc_pwd_autodev;
        String cmd = "winvnc4.exe -disconnect";
        Runtime rt = Runtime.getRuntime();

        try {
            Process currentProcess = rt.exec(cmd);
            currentProcess.waitFor();
            logger.logDetailAndConsole("Disconnected VNC clients");
            cmd = "cmd.exe /C \"regedit /s " + InitData.vnc_pwd_autodev + "\"";
            currentProcess = rt.exec(cmd);
            currentProcess.waitFor();
            logger.logDetailAndConsole("Changed VNC password to autodev");
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Starts the VNC client.
     *
     */
    public static void startVnc() {

        Logger logger = Logger.getInstance();
        String vnc_pwd_common = (InitData.projectPath) + InitData.pathSeparator + InitData.vnc_pwd_common;
        String cmd = "winvnc4.exe -disconnect";
        Runtime rt = Runtime.getRuntime();

        try {
            Process currentProcess = rt.exec(cmd);
            currentProcess.waitFor();
            logger.logDetailAndConsole("Disconnected VNC clients");
            cmd = "cmd.exe /C \"regedit /s " + vnc_pwd_common + "\"";
            currentProcess = rt.exec(cmd);
            currentProcess.waitFor();
            logger.logDetailAndConsole("Changed VNC password to common");
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Reduces the speed of entry and mouse using the
     * <code>setOption</code> method in
     * {@link RationalTestScript}.
     *
     */

/*****************************************************
 public static void slowMouse() {
 // reducing the speed of entry and mouse
 RationalTestScript.setOption(IOptionName.DELAY_BEFORE_MOUSE_UP, 0.05);
 RationalTestScript.setOption(IOptionName.DELAY_BEFORE_KEY_DOWN,.09);
 RationalTestScript.setOption(IOptionName.DELAY_BEFORE_KEY_UP,.05);
 RationalTestScript.setOption(IOptionName.DELAY_BEFORE_MOUSE_DOWN, 0.3);
 RationalTestScript.setOption(IOptionName.DELAY_BEFORE_MOUSE_MOVE, 0.05);
 }
 ***/
    /**
     * Resets the mouse speed back to its original state by using the
     * <code>resetOption</code> method in {@link RationalTestScript}.
     *
     */
	/*public static void resetMouse() {
		//Resetting the mouse speed back to original state.
		RationalTestScript.resetOption(IOptionName.DELAY_BEFORE_MOUSE_UP);
		RationalTestScript.resetOption(IOptionName.DELAY_BEFORE_MOUSE_DOWN);
		RationalTestScript.resetOption(IOptionName.DELAY_BEFORE_MOUSE_MOVE);
		RationalTestScript.resetOption(IOptionName.DELAY_BEFORE_KEY_UP);
		RationalTestScript.resetOption(IOptionName.DELAY_BEFORE_KEY_DOWN);
	}

	/**
	 * Finds the operating system on which the script is running.
	 *
	 * @return operating system name
	 * @throws AutomationException if unknown OS is found
	 */
    public static String findOS() throws AutomationException {
        String osStr = System.getProperty("os.name");
        System.out.println("OS Name: " + System.getProperty("os.name"));
        //System.out.println("OS Architecture: " + System.getProperty("os.arch"));
        //System.out.println("OS Version: " + System.getProperty("os.version"));

        if (osStr.indexOf(InitData.OS_WIN) >= 0) {
            osStr = InitData.OS_WIN;
        } else if (osStr.indexOf(InitData.OS_LNX) >= 0) {
            osStr = InitData.OS_LNX;
        } else
            throw new AutomationException ("Unknown OS");

        System.out.println("Determined OS as: " + osStr);

        return osStr;
    }

    /**
     * Converts the given path to an absolute path.
     *
     * @param path to be converted
     * @return absolute path
     */
    public static String convertPathToAbsolute(String path) {
        File file = new File(path);
        String absolute = "";
        try {
            absolute = file.getCanonicalPath();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return absolute;
    }

    /**
     * Closes all windows of a given className.
     *
     * @param className name of the class that refers to window
     */
	/*public static void closeAllWindows(String className)
	{
	 RootTestObject root = RootTestObject.getRootTestObject();
	 IWindow[] windows = root.getTopWindows();
	 String windowClassName;
	 for (int i = 0; i < windows.length; i++)
	 { windowClassName = windows[i].getWindowClassName();
	   if(windowClassName.equals(className))
	      windows[i].close();
	 }
	}
*********************************************************/
    /**
     * Kills the specified process.
     *
     * @param processName name of the process to be killed
     */
    public static void killProcess(String processName)
    {
        Runtime rt = Runtime.getRuntime();
        String[] callArgs = { "Taskkill.exe", "/F", "/IM", processName };
        try {
            // trigger the kill process command
            Process proc = rt.exec(callArgs);
            proc.waitFor();
            Logger logger = Logger.getInstance();
            logger.logDetailAndConsole("Killed " + processName);
            //System.out.println("Exit code is: " + proc.exitValue());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Check for a process if it is running.
     *
     * @param processName name of the process
     * @return <b>true</b> if the specified process is running<br>
     * 		   <b>false</b> if the specified process is not running
     */
    public static boolean checkProcess(String processName)
    {
        Logger logger = Logger.getInstance();
        Runtime rt = Runtime.getRuntime();
        String[] callArgs = { "tasklist.exe", "/fi", "ImageName eq " + processName };
        try {
            // trigger the list process command
            Process proc = rt.exec(callArgs);
            //proc.waitFor();
            BufferedReader stdInput = new BufferedReader(new
                    InputStreamReader(proc.getInputStream()));
            String result = "";
            String s = "";
            while (!procDone(proc)) {
                // Get the output from spawned process
                while((s=stdInput.readLine()) !=null){
                    result = result + s;
                }
                logger.logDetailAndConsole("result:" + result);
            }

            // Check if process exists in the return output
            if (result.indexOf(processName) >= 0)
            {logger.logDetailAndConsole("Process " + processName + " exists");
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /*
     * Returns true is process is done.
     */
    private static boolean procDone(Process p) {
        try {
            int v = p.exitValue();
            return true;
        }
        catch(IllegalThreadStateException e) {
            return false;
        }
    }

    /**
     * Calculates system time upto millisecond accuracy.
     *
     * @return time in h:mm:ss.SSS a format.
     * Example: 1:19:44.999 PM
     */
    public static String getTimeInMilliSec()
    {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("h:mm:ss.SSS a");
        return sdf.format(cal.getTime());

    }

    /**
     * Updates registry with the values specified in regFile
     *
     * @param regFile registry values to be updated
     */
    public static void updateRegistry(String regFile)
    {
        String cmd = "";
        Runtime rt = Runtime.getRuntime();
        Process currentProcess;

        try {
            cmd = "cmd.exe /C \"regedit /s " + regFile + "\"";
            currentProcess = rt.exec(cmd);
            currentProcess.waitFor();
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }
    }
}