package com.app.config.appSetup.xmlParser;
import com.app.config.appSetup.model.AppConfiguration;

import com.app.config.appSetup.model.AuthPlugin;

import com.app.config.appSetup.model.AuthenticationConfig;

import com.app.config.appSetup.model.ConnectionConfig;

import com.app.config.appSetup.model.ConnectionPooling;

import com.app.config.appSetup.model.ConnectorConfig;

import com.app.config.appSetup.model.DatabaseConfig;

import com.app.config.appSetup.model.DriveProperties;

import com.app.config.appSetup.model.MailConfig;

import com.app.config.appSetup.model.PathConfig;

import com.app.config.appSetup.model.SchedulerConfig;

import com.app.config.appSetup.model.SearchEngineConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import com.athena.config.appsetUp.interfaces.AppConfigurationInterface;
import com.athena.config.appsetUp.interfaces.AuthenticationConfigInterface;
import com.athena.config.appsetUp.interfaces.ConnectionConfigInterface;
import com.athena.config.appsetUp.interfaces.ConnectionPoolingInterface;
import com.athena.config.appsetUp.interfaces.ConnectorConfigInterface;
import com.athena.config.appsetUp.interfaces.DatabaseConfigInterface;
import com.athena.config.appsetUp.interfaces.DrivePropertiesInterface;
import com.athena.config.appsetUp.interfaces.MailConfigInterface;
import com.athena.config.appsetUp.interfaces.SchedulerConfigInteface;
import com.athena.config.appsetUp.interfaces.SearchEngineConfigInterface;



public class AppSetupSAXParser extends AbstractSAXParser {
	// APP SETUP PROPERTIES
	private AppConfigurationInterface appConfiguration;
	private PathConfig pathConfig;

	private String basePath;
	private String webAppPath;
	private String os;

	// AUTHENTICATION
	private AuthenticationConfigInterface authenticationConfig;
	private AuthPlugin authPlugin;
	private String authClassName;
	private int authType;
	private int type;
	private String key;
	private HashMap<String, String> authProperties = new HashMap<String, String>();
	private boolean recaptcha;

	// DATABASE CONFIG PROPERTIES
	private DatabaseConfigInterface databaseConfig;
	// CONNECTION CONFIG PROPERTIES
	private ConnectionConfigInterface connectionConfig;
	private String driverName;
	private String protocol;
	private String host;
	private int port;
	private String database;
	private String optionalParams;
	private String userName;
	private String password;
	// CONNECTION POOLING PROPERTIES
	private ConnectionPoolingInterface connectionPooling;
	private int initialSize;
	private int maxActive;
	private int maxIdle;
	private int minIdle;
	private boolean testOnBorrow;
	private boolean testOnReturn;
	private boolean testWhileIdle;
	private String validationQuery;
	private int timeBetweenEvictionRunsMillis;
	private int numTestPerEvictionRun;

	private String solrHome;

	// DRIVE PROPERTIES
	private DrivePropertiesInterface driveProperties;
	private String drive;
	private String publicDrive;
	private String privateDrive;

	// APP PROPERTIES
	private SearchEngineConfigInterface searchEngineConfig;
	private String docResultFields;
	private String language;
	private String searchAppURL;
	private String searchbasePath;

	// MAIL CONFIGURATION PROPERTIES
	private MailConfigInterface mailConfig;
	private int smtpPort;
	private int smtpsPort;
	private boolean smtpAuth;
	private boolean smtpTls;
	private boolean smtpSsl;

	// SCHEDULER CONFIGURATION
	private String scheudlerurl;
	private int schedulerRefreshTime;
	private SchedulerConfigInteface schedulerConfig;

	// Connector Config Property

	private String serverId;
	private String serverName;
	private String ipAddress;
	private String portNo;
	private String databaseName;
	// private String userName;
	// private String password;
	private Integer connectorType;
	private String filePath;
	// private String protocol;
	//database type and database Driver name added for external integration 
	private String databaseType;
	private String databaseDriverName;
	private String databaseUrl;

	private String webServerBasePath;

	List<ConnectorConfigInterface> connectionConfigList = new ArrayList<ConnectorConfigInterface>();

	public AppSetupSAXParser() {
	}

	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		resetTagData();
		if (qName.equalsIgnoreCase("pathConfig")) {
			this.os = attributes.getValue("os");
		}
		if (qName.equalsIgnoreCase("authPlugin")) {
			this.type = Integer.valueOf(attributes.getValue("type"));
		}
	}

	public void endElement(String uri, String localName, String qName) throws SAXException {
		String dataValue = getTagData();

		switch (qName) {
		case "baseFilePath": // database config properties
			this.basePath = dataValue.replaceAll("(\r\n|\n|\t)", "");
			break;
		case "webAppPath":
			this.webAppPath = dataValue.replaceAll("(\r\n|\n|\t)", "");
			break;
		case "pathConfig":
			this.pathConfig = new PathConfig(basePath, webAppPath);
			break;
		case "authType":
			this.authType = Integer.valueOf(dataValue);
			break;
		case "authClassName":
			this.authClassName = dataValue;
			break;
		case "key":
			this.key = dataValue;
			break;
		case "value":
			this.authProperties.put(key, dataValue);
			break;
		case "recaptcha":
			this.recaptcha = Boolean.valueOf(dataValue);
			break;
		case "authPlugin":
			this.authPlugin = new AuthPlugin(authClassName, authType, type, authProperties);
			break;
		case "authenticationConfig":
			this.authenticationConfig = new AuthenticationConfig(authPlugin, recaptcha);
			break;
		case "driverName":
			this.driverName = dataValue;
			break;
		case "protocol":
			this.protocol = dataValue.replaceAll("(\r\n|\n|\t)", "");
			break;
		case "host":
			this.host = dataValue;
			break;
		case "port":
			this.port = Integer.parseInt(dataValue);
			break;
		case "database":
			this.database = dataValue;
			break;
		case "optionalParams":
			this.optionalParams = dataValue.replaceAll("(\r\n|\n|\t)", "");
			break;
		case "userName":
			this.userName = dataValue;
			break;
		case "password":
			this.password = dataValue;
			break;
		case "initialSize": // database pooling properties
			this.initialSize = Integer.parseInt(dataValue);
			break;
		case "maxActive":
			this.maxActive = Integer.parseInt(dataValue);
			break;
		case "maxIdle":
			this.maxIdle = Integer.parseInt(dataValue);
			break;
		case "minIdle":
			this.minIdle = Integer.parseInt(dataValue);
			break;
		case "testOnBorrow":
			this.testOnBorrow = Boolean.parseBoolean(dataValue);
			break;
		case "testOnReturn":
			this.testOnReturn = Boolean.parseBoolean(dataValue);
			break;
		case "testWhileIdle":
			this.testWhileIdle = Boolean.parseBoolean(dataValue);
			break;
		case "validationQuery":
			this.validationQuery = dataValue;
			break;
		case "timeBetweenEvictionRunsMillis":
			this.timeBetweenEvictionRunsMillis = Integer.parseInt(dataValue);
			break;
		case "numTestPerEvictionRun":
			this.numTestPerEvictionRun = Integer.parseInt(dataValue);
			break;
		case "drive": // reading drive properties
			this.drive = dataValue;
			break;
		case "drivePublic":
			this.publicDrive = dataValue;
			break;
		case "driveShared":
			this.privateDrive = dataValue;
			break;
		case "solrHome":
			this.solrHome = dataValue;
			break;
		case "docResultFields":
			this.docResultFields = dataValue;
			break;
		case "language":
			this.language = dataValue;
			break;
		case "searchAppURL":
			this.searchAppURL = dataValue;
			break;
		case "basePath":
			this.searchbasePath = dataValue;
			break;
		// MAIL CONFIGURATION PROPERTIES
		case "smtpHost":
			this.host = dataValue;
			break;
		case "smtpPort":
			this.smtpPort = Integer.parseInt(dataValue);
			break;
		case "smtpsPort":
			this.smtpsPort = Integer.parseInt(dataValue);
			break;
		case "smtpAuth":
			this.smtpAuth = Boolean.parseBoolean(dataValue);
			break;
		case "smtpStarttlsEnable":
			this.smtpTls = Boolean.parseBoolean(dataValue);
			break;
		case "smtpSslEnable":
			this.smtpSsl = Boolean.parseBoolean(dataValue);
			break;
		case "schedulerurl":
			this.scheudlerurl = dataValue;
			break;
		case "schedulerRefreshTime":
			this.schedulerRefreshTime = Integer.parseInt(dataValue);
			break;
		case "serverId":
			this.serverId = dataValue;
			break;

		case "serverName":
			this.serverName = dataValue;
			break;
		case "ipAddress":
			this.ipAddress = dataValue;
			break;
		case "portNo":
			this.portNo = dataValue;
			break;
		case "databaseName":
			this.databaseName = dataValue;
			break;
		case "filePath":
			this.filePath = dataValue;
			break;
		case "databaseType":
			this.databaseType=dataValue;
			break;
		case "databaseDriverName":
			this.databaseDriverName=dataValue;
			break;
		case "databaseUrl":
			this.databaseUrl=dataValue;
			break;
		case "webServer.BasePath":
			this.webServerBasePath = dataValue;
			break;
		case "connectorType":
			this.connectorType = Integer.parseInt(dataValue);
			break;

		case "connectionConfig": // creation of objects
			this.connectionConfig = new ConnectionConfig(driverName, protocol, host, port, database, optionalParams, userName, password);
			break;
		case "connectionPooling":
			this.connectionPooling = new ConnectionPooling(initialSize, maxActive, maxIdle, minIdle, testOnBorrow, testOnReturn, testWhileIdle, validationQuery,
					timeBetweenEvictionRunsMillis, numTestPerEvictionRun);
			break;
		case "databaseConfig":
			this.databaseConfig = new DatabaseConfig(this.connectionConfig, this.connectionPooling);
			break;
		case "cloudDriveConfig":
			this.driveProperties = new DriveProperties(drive, publicDrive, privateDrive);
			break;
		case "searchEngineConfig":
			this.searchEngineConfig = new SearchEngineConfig(docResultFields, language, searchAppURL, searchbasePath);
			break;
		case "mailConfig":
			this.mailConfig = new MailConfig(userName, password, smtpAuth, host, smtpPort, smtpTls, smtpSsl, smtpsPort);
			break;
		case "schedulerConfig":
			this.schedulerConfig = new SchedulerConfig(scheudlerurl, schedulerRefreshTime);
			break;
		case "connectorConfig":
			ConnectorConfig connectorConfig = new ConnectorConfig(serverId, serverName,ipAddress, portNo, databaseName, userName, password, connectorType, protocol,filePath,databaseType,databaseDriverName,databaseUrl);
			this.connectionConfigList.add(connectorConfig);
			break;
		case "AppConfig":
			this.appConfiguration = new AppConfiguration(pathConfig, authenticationConfig, databaseConfig, searchEngineConfig, driveProperties, mailConfig,
					schedulerConfig, connectionConfigList);
			break;
		default:
			break;
		}
	}

	@Override
	public Object getObject() {
		return appConfiguration;
	}
}
