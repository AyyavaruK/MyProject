package com.telligent.model.db;
/**
 * <p> Description here TODOJAVADOC
 *
 * <p> Known Issues here TODOJAVADOC
 *
 *
 * Objects instantiated from this class are not to be shared across
 * threads, or held onto for long periods of time ( in member_variables, caches etc)
 * These are supposed to be short-lifetime objects with thread-local scope
 *
 * @author spothu
 */
public interface ErrorMessages {
    public String DB_CONNECTED = "Connected to Database";
    public String DB_INTERNAL_ERROR = "Internal Database Error";
    public String INVALID_DB_DRIVER = "Invalid Database Driver";
    public String CONNECTION_ERROR = "Error while connecting to Database";
}
