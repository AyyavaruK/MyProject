/*
 * SQLException.java
 *
 */

package com.telligent.util.exceptions;

/**
 * <P>
 * An exception that provides information on a database access error or other errors.
 * <P>
 * Each <code>SQLException</code> provides several kinds of information:
 * <ul>
 * <li>a string describing the error. This is used as the Java Exception message, available via the method <code>getMessage</code>. </li>
 * <li>a "SQLstate" string, which follows the XOPEN SQLstate conventions. The values of the SQLState string are described in the XOPEN SQL spec.</li>
 * <li>an integer error code that is specific to each vendor. Normally this will be the actual error code returned by the underlying database.</li>
 * <li>a chain to a next Exception. This can be used to provide additional error information.
 * </ul>
 * <P>
 * @author  spothu
 */
public class SQLException extends BaseException {
    
    /** Creates a new instance of SQLException */
    public SQLException() {
        super();
    }

    /**
	 * This method throws SQLException depending on message
     * @param message takes as message
     */    
    public SQLException( String message )
    {
        super( message );
    }
    
    /**
	 * This method throws SQLException depending on message and cause
     * @param message takes as message
     * @param cause takes as cause
     */    
    public SQLException( String message, Throwable cause )
    {
        super( message, cause );
    }
    
    /**
	 * This method throws SQLException depending on cause
     * @param cause takes as cause
     */    
    public  SQLException( Throwable cause )
    {
        super( cause );
    }
}
