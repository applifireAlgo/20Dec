package com.app.config;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.athena.server.pluggable.utils.AppLoggerConstant;
import com.spartan.pluggable.logger.api.LogManagerFactory;
import com.spartan.pluggable.logger.api.SpartanLoggerException;

public class SpartanLogManagerLoader implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		System.out.println("Call to Context destroy");
		shutdown();
		System.out.println("Context destroyed");
	}

	@Override
	public void contextInitialized(ServletContextEvent context) {
		System.out.println("Context initialized");
		try {
			// String relativePath =
			// context.getServletContext().getResource("/WEB-INF/conf/").toString();
			String relativePath = context.getServletContext().getRealPath("/WEB-INF/conf/");
			// relativePath = relativePath.replace("file:", "");
			System.out.println(relativePath + "/");
			relativePath = relativePath + "/";
			initLogger(relativePath);
		} catch (SpartanLoggerException e) {
			e.printStackTrace();
		}
	}

	/**
	 * initialize the spartan logger with given configuration path
	 * 
	 * @throws SpartanLoggerException
	 */
	private void initLogger(String _path) throws SpartanLoggerException {
		System.out.println("Initializing logger");

		// String logManagerId = LogManagerFactory.createMultiLogManager(_path);
		// LogManager Log = LogManagerFactory.getInstance(logManagerId);
		// System.setProperty("LOGGER_ID", logManagerId);
		// System.out.println("Logger initialized");
		// System.out.println("Logger ID:==="+AppLoggerConstant.LOGGER_ID);

		LogManagerFactory.createLogManager(_path, AppLoggerConstant.LOGGER_ID);
		System.out.println("Logger initialized with unique logger id " + AppLoggerConstant.LOGGER_ID);
	}

	/** graceful shutdown */
	public void shutdown() {
		System.out.println("Call to graceful shutdown of spartan logger started.");
		LogManagerFactory.getInstance(AppLoggerConstant.LOGGER_ID).out.shutdown();
		System.out.println("Call to graceful shutdown of spartan logger completed.");
	}

}
