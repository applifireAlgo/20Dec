package com.app.server.businessservice.appinsight.alarms;
import com.app.server.repository.appinsight.alarms.ArtLogAlarmRepository;

import com.app.server.repository.appinsight.alarms.ArtLogBoundedContextRepository;


import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import com.spartan.pluggable.logger.api.AlarmManager;

public class AlarmFileScheduler extends TimerTask {

	Timer timer = new Timer();
	
	@Autowired
	private ArtLogBoundedContextRepository artLogBoundedContextRepository;

	@Autowired
	private AppLogAlarmGenerationBizService appLogAlarmGenerationBizService;

	@Autowired
	ResourceLoader resourceLoader;

	private AlarmManager alarmManager;
	
	@Autowired
	private ArtLogAlarmRepository artLogAlarmRepository;

	@Override
	public void run() {
		//System.out.println("\n\n\n************************ In run() method of ReminderTask***********************");
		System.out.println("AlarmFileScheduler started");
		checkVersionNumber();
	}

	@PostConstruct
	public void init() {
	schedule();
	}

	private void schedule() {
		timer.scheduleAtFixedRate(this,60000,120000);
		//timer.scheduleAtFixedRate(this,10000,120000);

	}

	private void initializeAlarmManager() {
		try {
			File appLogConfigFilesPath = resourceLoader.getResource("WEB-INF/conf/appConfiguration.xml").getFile();
			//System.out.println("**************** appLogConfigFiles is : " + appLogConfigFilesPath);
			String filePath = appLogConfigFilesPath.getAbsoluteFile().getParentFile().getAbsolutePath();
			//System.out.println("********** File path is : " + filePath);
			alarmManager = new AlarmManager(filePath);
			alarmManager.loadAlarmFiles();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void destroy() {
		timer.cancel();
	}

	private void checkVersionNumber() {
		try {
			initializeAlarmManager();
			//System.out.println("##### In checkVerSionNumber() ************");
				int dbVersionNumber = artLogAlarmRepository.getVersionNumber();
				//System.out.println("Version Number from database : " + dbVersionNumber);
				int currentFileVersionNumber = alarmManager.getAlarmContext().getAppAlarmVersion();
				//System.out.println("current VersionNumber from file: " + currentFileVersionNumber);

				if ((dbVersionNumber != 0) && (dbVersionNumber > currentFileVersionNumber)) {
					//System.out.println("Rewriting xml.....");
						//System.out.println("########## In IF condition ");
						try
						{
						String alarmData = artLogAlarmRepository.getAlarmDataByType(2);
						appLogAlarmGenerationBizService.reGenerateAppAlarmXml(alarmData);

						}
						catch(Exception e)
						{
							e.printStackTrace();
						}

					}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
