package com.cfm.sacc.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class LogHandler{
	
	private LogHandler() {
	}
	
	private static final String CONFIGLOG_INFO = "{}[{}] {}";
	private static final String CONFIGLOG_ERROR = "{}[{}] {} :: {}";
	private static Logger logError;
	private static Logger logMonitor;
	
	static {
		logError 	= LoggerFactory.getLogger( "errores" );
		logMonitor	= LoggerFactory.getLogger( "monitor" );
	}
	
	public static void info(String uid,  Class<?> c,String msg)	{
		logMonitor.info(CONFIGLOG_INFO,uid, c.getSimpleName(), msg);
	}
 
	public static void error(String uid, Class<?> c, String msg, Exception e ) {
		logError.error(CONFIGLOG_ERROR,uid, c.getSimpleName(), msg, e.getMessage());
	}
	
}
