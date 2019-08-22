package ua.RetroCars.exeption;

import org.apache.log4j.Logger;

/**
 * An exception that provides information on a database access error.
 * 
 */
public class DBException extends RuntimeException{

	private static final long serialVersionUID = -3550446897536410392L;
	private static final Logger LOG = Logger.getLogger(DBException.class);


	public DBException() {
		super();
	}

	public DBException(String message, Throwable cause) {
		super(message, cause);
		LOG.trace(message + cause); 
	}

}
