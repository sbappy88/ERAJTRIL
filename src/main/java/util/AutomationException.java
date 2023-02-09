package util;

//TODO:To change the template for this generated type comment go to
//Window - Preferences - Java - Code Style - Code Templates

/**
 * <b>AutomationException</b> is an exception class for throwing
 * automation related exceptions.
 *
 */
@SuppressWarnings("serial")
public class AutomationException extends Exception {

    /**
     * Constructor which takes the exception message.
     *
     * @param msg exception message
     */
    public AutomationException(String msg) {

        super(msg);
    }
}