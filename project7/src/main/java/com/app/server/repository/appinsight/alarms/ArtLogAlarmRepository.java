package com.app.server.repository.appinsight.alarms;
import com.app.shared.appinsight.alarms.ArtLogAlarm;


import org.springframework.context.annotation.Scope;


@Scope(value = "request")
public interface ArtLogAlarmRepository {
	    public void save(ArtLogAlarm artLogAlarm);

		public ArtLogAlarm findByAlarmType(int alarmType);

		public void update(ArtLogAlarm artLogAlarm);
		
		public String getAlarmDataByType(int alarmType);
		
		public int getVersionNumber();

}
