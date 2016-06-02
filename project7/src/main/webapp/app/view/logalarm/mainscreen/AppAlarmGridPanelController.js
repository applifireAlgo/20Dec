/**
 * 
 */
Ext.define('Project7.view.logalarm.mainscreen.AppAlarmGridPanelController', {
	extend : 'Ext.app.ViewController',
	alias : 'controller.appAlarmGridPanelController',
	dataGrid : null,
	init : function() {
		this.dataGrid = this.view.getStore();
	},
	
	clearGrid : function() {
		this.view.store.removeAll();
	},
	
	onRowSelection : function(selModel, record, index, options){
    },

    addDataToGrid : function(formData){
    	var currentObject = this;
    	var isPresent = false;
    	var duplicateEntryFound = false;
    	currentObject.dataGrid.each(function(rec) {
    		if(rec.get('alarmIndex') != formData.alarmIndex && rec.get('alarmId') == formData.alarmId) {
    			Ext.Msg.alert('Error', "Duplicate entries found for alarm id. Alarm/s cannot be updated.");
    			duplicateEntryFound = true;
    		}
    	});
    	if(duplicateEntryFound) {
    		return;
    	}
    	if(!duplicateEntryFound) {
    		currentObject.dataGrid.each(function(rec) {
    			if(rec.get('alarmIndex') == formData.alarmIndex)
    			{
    				rec.set('alarmId', formData.alarmId);
    				rec.set('alarmContext', formData.contextId);
    				rec.set('contextType', formData.contextType!=undefined?formData.contextType:rec.get('contextType'));
    				rec.set('boundedContextId', currentObject.getView().up().down('#bcId').getValue());
    				rec.set('domainId', currentObject.getView().up().down('#domainId').getValue());
    				rec.set('severity', formData.severity!=undefined?formData.contextType:rec.get('severity'));
    				rec.set('connectorOrder', formData.connectorOrder);
    				rec.set('message', formData.message);
    				rec.set('diagnose', formData.diagnose);
    				rec.set('solution', formData.solution);
    				rec.set('architectureLayer', formData.architectureLayer!=undefined?formData.contextType:rec.get('architectureLayer'));
    				rec.set('alarmStatus', formData.alarmStatus!=undefined?formData.contextType:rec.get('alarmStatus'));
    				rec.set('exception', formData.exception!=undefined?formData.exception:"");
    				rec.set('rootException', formData.rootException!=undefined?formData.rootException:"");
    				rec.set('eventAction', formData.eventAction!=undefined?formData.contextType:rec.get('eventAction'));
    				isPresent = true;
    			}
    		});

    		if(!isPresent) {
    			this.dataGrid.add(formData);
    		}
    	}
    },
	
	toJson : function() {
		var appAlarmsJSON = '';
		var appAlarmsGridStore = this.view.getStore();
		
		appAlarmsGridStore.each(function(rec) {
			var alarmId = rec.get('alarmId');
			var alarmContext = rec.get('alarmContext');
			var contextType = rec.get('contextType');
			var connectorOrder = rec.get('connectorOrder');
			var boundedContextId = rec.get('boundedContextId');
			var domainId = rec.get('domainId');
			var severity = rec.get('severity');
			var appLayer = rec.get('architectureLayer');
			var message = rec.get('message').replace('\n\t\t\t\t','');
			var diagnose = rec.get('diagnose').replace('\n\t\t\t\t','');
			var solution = rec.get('solution').replace('\n\t\t\t\t','');
			var status = rec.get('alarmStatus');
			var eventAction = rec.get('eventAction');
			var exception = rec.get('exception');
			var rootException = rec.get('rootException');
			var alarmPlacement = rec.get('alarmPlacement');
			var alarmJSON = '{';
			if(alarmId.length  != 0){
				alarmJSON = alarmJSON.concat("'alarmId': '"+ alarmId + "',");
				alarmJSON = alarmJSON.concat("'alarmContext': "+ alarmContext + ",");
				alarmJSON = alarmJSON.concat("'contextType': "+ contextType + ",");
				alarmJSON = alarmJSON.concat("'connectorLogPriorities': '["+ connectorOrder + "]',");
				alarmJSON = alarmJSON.concat("'boundedContextId': '"+ boundedContextId + "',");
				alarmJSON = alarmJSON.concat("'domainId': '"+ domainId + "',");
				alarmJSON = alarmJSON.concat("'severity': "+ severity + ",");
				alarmJSON = alarmJSON.concat("'appLayer': '"+ appLayer + "',");
				alarmJSON = alarmJSON.concat("'message': '"+ message + "',");
				alarmJSON = alarmJSON.concat("'diagnosis': '"+ diagnose + "',");
				alarmJSON = alarmJSON.concat("'solution': '"+ solution + "',");
				alarmJSON = alarmJSON.concat("'status': '"+ status + "',");
				alarmJSON = alarmJSON.concat("'eventAction': '"+ eventAction + "',");
				alarmJSON = alarmJSON.concat("'exceptionClass': '"+ exception + "',");
				alarmJSON = alarmJSON.concat("'rootException': '"+ rootException + "',");
				alarmJSON = alarmJSON.concat("'alarmPlacement': '"+ alarmPlacement + "'");
				alarmJSON = alarmJSON.concat("},")
			}

			appAlarmsJSON = appAlarmsJSON.concat(alarmJSON);
		});
		appAlarmsJSON = appAlarmsJSON.substring(0,appAlarmsJSON.lastIndexOf(','));
		return appAlarmsJSON;
	}
});
