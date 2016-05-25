/*
 * BaseException.java
 *
 */

package com.telligent.util.exceptions;

import java.io.PrintStream;
import java.io.PrintWriter;
/**
 * Base exception class for all application exceptions.
 * @author  spothu
 */
public class BaseException extends java.lang.Exception
{
    private Throwable _cause;
    
    /** Creates a new instance of BaseException */
    public BaseException() {
        super();
    }
    
    /**
     * @param message takes as param
     */    
    public BaseException( String message )
    {
        super( message );
    }
    
    /**
     * @param message takes as param
     * @param cause takes as param
     */    
    public BaseException( String message, Throwable cause )
    {
        super( message, cause );
    }
    
    /**
     * @param cause takes as param
     */    
    public  BaseException( Throwable cause )
    {
        super( cause );
    }
    
    /**
	 * Returns the cause of this throwable or null if the cause is nonexistent or unknown.
     * @return the cause
     */    
    public Throwable getCause()
    {
        return this._cause;
    }
    /**
	  * A Throwable class contains a snapshot of the execution stack of its thread at the time it was created. 
	 * It can also contain a message string that gives more information about the error.
	*/
    public void printStackTrace()
    {
        
        if( this._cause == null )
        {
            super.printStackTrace();
        }
        else
        {
            synchronized( System.err )
            {
                super.printStackTrace();
                System.err.println( "Cause of the above exception is :\r\n" );
                this._cause.printStackTrace();
            }
        }
    }

    /**
	 * A Throwable class contains a snapshot of the execution stack of its thread at the time it was created. 
	 * It can also contain a message string that gives more information about the error.
     * @param stream takes as param
     */    
    public void printStackTrace( PrintStream stream )
    {
        
        if( this._cause == null )
        {
            super.printStackTrace( stream );
        }
        else
        {
            synchronized( System.err )
            {
                super.printStackTrace( stream );
                System.err.println( "Cause of the above exception is :\r\n" );
                this._cause.printStackTrace( stream );
            }
        }
    }

    /**
	 * A Throwable class contains a snapshot of the execution stack of its thread at the time it was created. 
	 * It can also contain a message string that gives more information about the error.
     * @param writer takes as param
     */    
    public void printStackTrace( PrintWriter writer )
    {
        
        if( this._cause == null )
        {
            super.printStackTrace( writer );
        }
        else
        {
            synchronized( System.err )
            {
                super.printStackTrace( writer );
                System.err.println( "Cause of the above exception is :\r\n" );
                this._cause.printStackTrace( writer );
            }
        }
    }
}