package com.telligent.model.db;

import com.telligent.util.exceptions.BaseException;

/**
 * All RDBMS errors will throw this exception. These errors will have sqlCode set to the actual RDBMS error. 
 * Additionally network errors and internal VORTEXjava errors will also throw this exception. These errors will all have an sqlCode of (-1). 
 * @author  spothu
 */
public class DBException extends BaseException {
    
    /** Creates a new instance of DBException */
    public DBException() {
        super();
    }

    /**
     * @param message
     */    
    public DBException( String message )
    {
        super( message );
    }
    
    /**
     * @param message
     * @param cause
     */    
    public DBException( String message, Throwable cause )
    {
        super( message, cause );
    }
    
    /**
     * @param cause
     */    
    public  DBException( Throwable cause )
    {
        super( cause );
    }
}
