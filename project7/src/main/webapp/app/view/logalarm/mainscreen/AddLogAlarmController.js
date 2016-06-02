/**
 * 
 */
Ext.define('Project7.view.logalarm.mainscreen.AddLogAlarmController', {
	extend : 'Ext.app.ViewController',
	alias : 'controller.addLogAlarmController',
	
	trace : null,
	connectorOrder : null,
	connectorOrderSeverity : null,
	connectorOrderGrid : null,
	criticality : null,
	idRangeStartsWith : null,
	appAlarmPanel : null,
	appAlarmPanelController : null,
	appAlarmGridPanel : null,
	appAlarmGridPanelController : null,
	
	init : function() {
		var currentObject = this;
		currentObject.trace = currentObject.view.down('#trace');
		currentObject.connectorOrder = currentObject.view.down('#connectorOrder');
		currentObject.connectorOrderSeverity = currentObject.view.down('#connectorOrderSeverity');
		
		currentObject.connectorOrderGrid = currentObject.view.down('#connectorOrderGrid');
		
		currentObject.criticality = currentObject.view.down('#criticality');
		currentObject.idRangeStartsWith = currentObject.view.down('#idRangeStartsWith');
		
		currentObject.appAlarmPanel = currentObject.view.down('#appAlarmPanel');
		currentObject.appAlarmPanelController = currentObject.appAlarmPanel.getController();
		
		currentObject.appAlarmGridPanel = currentObject.view.down('#appAlarmGridPanel');
		currentObject.appAlarmGridPanelController = currentObject.appAlarmGridPanel.getController();
	},
	
	loadLoggerData : function(logConfigData) {
		var currentObject = this;
		currentObject.setConnectorOrder(logConfigData.connectorArray);
		currentObject.setSeverities(logConfigData.alarmSeverity);
		
		currentObject.appAlarmPanelController.setConnectorOrder(logConfigData.connectorArray);
		currentObject.appAlarmPanelController.setSeverities(logConfigData.alarmSeverity);
		currentObject.appAlarmPanelController.setAlarmStatus(logConfigData.alarmStatus);
		currentObject.appAlarmPanelController.setEventActions(logConfigData.eventActions);
		currentObject.appAlarmPanelController.setArchitectureLayers(logConfigData.architectureLayers);
	},
	
	setFormData : function(bcData, domainData) {
		var currentObject = this;
		this.clearFormData();
		currentObject.view.down('#bcId').setValue(bcData.contextPrefix);
		currentObject.view.down('#domainId').setValue(domainData.domainPrefix);
		currentObject.view.down('#domainName').setValue(domainData.domain);
		var jsonData = {};
		jsonData.bcPrefix = bcData.contextPrefix;
		jsonData.domainPrefix = domainData.domainPrefix;
		Ext.Ajax.request({
			timeout: 6000000,
			url : 'secure/logAlarmService/getGridData',
			method : 'POST',
			jsonData : jsonData,
			currentObject : currentObject,
			success : function(response, opts){
				var responseJson = Ext.JSON.decode(response.responseText);
				if (responseJson.response.success == true) {
					var logAlarmData = responseJson.response.data;
					currentObject.view.down('#addButton').setText('Update Alarm');
					if(logAlarmData != null) {
						var appDomain = Ext.JSON.decode(logAlarmData);
						currentObject.trace.setValue(appDomain.isTraceEnabled);
						currentObject.criticality.select(currentObject.criticality.getStore().getAt(appDomain.domainCriticality-1));
						var connector_orders = appDomain.connectorLogPriorities.split(',');
						for(i = 0;i<connector_orders.length;i++) {
							var connector_order = connector_orders[i].trim();
							if(connector_order != -1) {
								currentObject.connectorOrder.select(currentObject.connectorOrder.getStore().getAt(i));
								currentObject.connectorOrderSeverity.select(currentObject.connectorOrderSeverity.getStore().getAt(connector_order));
								currentObject.onConnectorAddButton();
							}
						}
						currentObject.appAlarmPanelController.setFormData(appDomain.appAlarms);
					}
				} else {
					Ext.Msg.alert("Error.", responseJson.response.message);
				}
			},
			failure : function(response, opts){
				var responseJson = Ext.JSON.decode(response.responseText);
				Ext.Msg.alert("Error...", responseJson.response.message);
			}
		});
	},
	
	clearConnectorOrderGrid : function() {
		this.connectorOrderGrid.store.removeAll();
	},
	
	clearFormData : function() {
		var logModuleForm = this.view.getForm();
		logModuleForm.reset();
		this.clearConnectorOrderGrid();
		this.appAlarmGridPanelController.clearGrid();
		this.appAlarmPanelController.onClearButton();
	},
	
	setConnectorOrder : function(connectorsData) {
		var store = this.connectorOrder.getStore();
		var connectorOrderStore = Ext.create('Ext.data.Store', {
					fields : ['cid', 'name'],
					data : connectorsData,
					
					sorters: [{
				         property: 'cid',
				         direction: 'ASC'
				     }],
				});

		this.connectorOrder.setStore(connectorOrderStore);
	},
	
	setSeverities : function(severityData) {
		var store = this.connectorOrder.getStore();
		var severityStore = Ext.create('Ext.data.Store', {
			fields : ['severity', 'label'],
			data : severityData,
			sorters: [{
				property: 'severity',
				direction: 'ASC'
			}],
		});

		this.connectorOrderSeverity.setStore(severityStore);
//		this.criticality.setStore(severityStore);
	},
	
	onConnectorAddButton : function(but, evt) {
		var currentObject = this;
		
		var cid = this.connectorOrder.getValue();
		var name = this.connectorOrder.getRawValue();
		var severityId = this.connectorOrderSeverity.getValue();
		var severity = this.connectorOrderSeverity.getRawValue();
		var isPresent = false;
		if(cid==null || name == null || severityId == null || severity == null || cid =="" || name == "" || severityId == "" || severity == "") {
			Ext.Msg.alert("Error", "Connector Order is Not Selected");
		}
		else {
			var connectorObject = [{ 'cid' : cid, 'name': name, 'severityId' : severityId, 'severity' : severity }];

			var connectorOrderGridStore = this.connectorOrderGrid.store;
			connectorOrderGridStore.each(function(rec) {
				var cid = rec.get('cid');
				var name = rec.get('name')
				var severityId = rec.get('severityId');
				var severity = rec.get('severity')

				if(cid == currentObject.connectorOrder.getValue()){
					rec.set('name', currentObject.connectorOrder.getRawValue());
					rec.set('severityId', currentObject.connectorOrderSeverity.getValue());
					rec.set('severity', currentObject.connectorOrderSeverity.getRawValue());
					isPresent = true;
				}
			});

			if(!isPresent) {
				this.connectorOrderGrid.getStore().add(connectorObject);
			}
		}
	},
	
	onClearButton : function(but, evt) {
		this.clearFormData();
	},
	
	updateLogModules : function(logModuleJSON){
		var currentObject = this;
		Ext.Ajax.request({
			timeout: 6000000,
			url : 'secure/logAlarmService/updateLogAlarm',
			jsonData : Ext.JSON.decode(logModuleJSON),
			method : 'PUT',
			success : function(response, opts) {
				var responseJson = Ext.JSON.decode(response.responseText);
				if (responseJson.response.success == true) {
					var message = responseJson.response.message;
					//get newly updated log modules after update
					currentObject.clearFormData();
					currentObject.view.up().up().controller.getLogModules();
					Ext.Msg.alert("Log Alarm Update",message);
				} 
				else {
					Ext.Msg.alert("Error...",responseJson.response.message);
				}
			},
			failure : function(response,opts) {
				var responseJson = Ext.JSON.decode(response.responseText);	
				Ext.Msg.alert("Error...",responseJson.response.message); 
			},
		}, this);
	},
	
	onSaveButton : function(but, evt) {
		var currentObject = this;
		var form = but.up('form').getForm();
		var formJSON = Ext.JSON.encode(form.getValues());
				
		var logModuleJSON = this.createLogModuleJSON(formJSON);
		
		if (form.isValid()) {
//			this.waitWindow = Ext.MessageBox.wait('Please wait, request in process');
			currentObject.updateLogModules(logModuleJSON);
		} else {
			Ext.Msg.alert('Form Validation', "Form is not valid");
		}
	},
	
	getTranslatedConnectorOrder : function() {
		var connectorOrderStore = this.connectorOrder.getStore();
		var connector_order = new Array(connectorOrderStore.data.length);
		/*for(i = 0; i<connector_order.length ; i++) {
			connector_order[i] = -1;
		}*/
		for(i = 0; i<4 ; i++) {
			connector_order[i] = -1;
		}
		var connectorOrderGridStore = this.connectorOrderGrid.getStore();
		connectorOrderGridStore.each(function(rec) {
			var cid = rec.get('cid');
			var name = rec.get('name')
			var severity = rec.get('severityId');
			var label = rec.get('severity')
			
			connector_order[cid - 1] = parseInt(severity);
		});
		return connector_order;
	},
	
	createLogModuleJSON : function(formJSON) {
		var currentObject = this;
		var connector_order = currentObject.getTranslatedConnectorOrder();
		var alarmsJSON = this.appAlarmGridPanelController.toJson();
		if(currentObject.view.down('#trace').getValue()) {
			trace = "yes";
		} else {
			trace = "no";
		}
		var logModuleJSON = '{';
		var appAlarmJSON = '';
		appAlarmJSON = appAlarmJSON.concat("'appAlarms': [ ");
		appAlarmJSON = appAlarmJSON + alarmsJSON + "]";
		var logModuleJSON = '{';
		formJson = Ext.JSON.decode(formJSON);
		logModuleJSON = logModuleJSON.concat("'boundedContextId': '"+ formJson.bcId + "',");
		logModuleJSON = logModuleJSON.concat("'domainId': '"+ formJson.domainId + "',");
		logModuleJSON = logModuleJSON.concat("'domainName': '"+ formJson.domainName + "',");
		logModuleJSON = logModuleJSON.concat("'enableTrace': '"+ trace + "',");
		logModuleJSON = logModuleJSON.concat("'domainCriticality': "+ currentObject.criticality.getValue() + ",");
		logModuleJSON = logModuleJSON.concat("'connectorLogPriorities': '["+ connector_order + "]',");
		logModuleJSON = logModuleJSON.concat(appAlarmJSON);
		
		logModuleJSON = logModuleJSON.concat("}")
		
		return logModuleJSON;
	}
});