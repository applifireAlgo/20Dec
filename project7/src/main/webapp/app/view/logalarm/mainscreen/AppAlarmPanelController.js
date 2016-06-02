/**
 * 
 */
Ext.define('Project7.view.logalarm.mainscreen.AppAlarmPanelController', {
	extend : 'Ext.app.ViewController',
	alias : 'controller.appAlarmPanelController',
	
	alarmIndex : null,
	alarmId : null,
	severity : null,
	connectorOrder : null,
	connectorOrderSeverity : null,
	connectorOrderGrid : null,
	
	idCounter : null,
	message : null,
	diagnose : null,
	solution : null,
	
	init : function(){
		var currentObject = this;
		currentObject.alarmIndex = currentObject.view.down('#alarmIndex');
		currentObject.alarmId = currentObject.view.down('#alarmId');
		currentObject.severity = currentObject.view.down('#severity');
		currentObject.connectorOrder = currentObject.view.down('#connectorOrder');
		currentObject.connectorOrderSeverity = currentObject.view.down('#connectorOrderSeverity');
		currentObject.connectorOrderGrid = currentObject.view.down('#connectorOrderGrid');
		
		currentObject.message = currentObject.view.down('#message');
		currentObject.diagnose = currentObject.view.down('#diagnose');
		currentObject.solution = currentObject.view.down('#solution');
		currentObject.idCounter = currentObject.view.down('#idCounter');
	},
	
	setFormData : function(alarmData) {
		var currentObject = this;
		var appAlarmGridPanelController = this.view.up().down('#appAlarmGridPanel').getController();
		appAlarmGridPanelController.clearGrid();
		currentObject.view.down('#addButton').setText('Update Alarm');
		if(alarmData != null && alarmData.length>0) {
			for(var i = 0; i<alarmData.length; i++) {
				var appAlarm = alarmData[i];
				var appAlarmGridPanelController = currentObject.view.up().down('#appAlarmGridPanel').getController();
				var severity = this.view.down('#severity').getStore().findRecord('severityId', appAlarm.severity).data.severity;
				var layer = this.view.down('#architectureLayer').getStore().findRecord('layerId', appAlarm.appLayer).data.layer;
				var status = this.view.down('#alarmStatus').getStore().findRecord('statusId', appAlarm.status).data.status;
				var eventAction = this.view.down('#eventAction').getStore().findRecord('eventActionId', appAlarm.eventAction).data.eventAction;
				appAlarmGridPanelController.addDataToGrid({
					"alarmIndex" : appAlarm.alarmId,
					"alarmId": appAlarm.alarmId,
					"alarmContext" : appAlarm.alarmContext,
					"contextType": appAlarm.contextType,
					"connectorOrder": appAlarm.connectorLogPriorities,
					"boundedContextId": appAlarm.boundedContextId,
					"domainId": appAlarm.domainId,
					"severity": severity,
					"architectureLayer": layer,
					"message": appAlarm.message,
					"diagnose": appAlarm.diagnosis,
					"solution": appAlarm.solution,
					"alarmStatus": status,
					"eventAction": eventAction,
					"exception": appAlarm.exceptionClass,
					"rootException": appAlarm.rootException,
					"alarmPlacement": appAlarm.alarmPlacement					
				});
			}
		}		
	},
	
	renderFormData : function(appAlarm) {
		var currentObject = this;
		currentObject.clearConnectorOrderGrid();
		currentObject.alarmIndex.setValue(appAlarm.id);
		currentObject.alarmId.setValue(appAlarm.alarmId);
		currentObject.severity.select(currentObject.severity.getStore().findRecord('severity', appAlarm.severity).data.severityId);
		var layerId = currentObject.view.down('#architectureLayer').getStore().findRecord('layer', appAlarm.architectureLayer).data.layerId;
		var statusId = currentObject.view.down('#alarmStatus').getStore().findRecord('status', appAlarm.alarmStatus).data.statusId;
		var eventActionId = currentObject.view.down('#eventAction').getStore().findRecord('eventAction', appAlarm.eventAction).data.eventActionId;
		currentObject.view.down('#architectureLayer').setValue(layerId);
		currentObject.view.down('#eventAction').setValue(eventActionId);
		currentObject.view.down('#alarmStatus').setValue(statusId);
//		currentObject.view.down('#rootExceptionLabel').setHidden(true);
		currentObject.view.down('#exceptionLabel').setValue(appAlarm.exception);
		if(appAlarm.rootException != "null" && appAlarm.rootException != "" && appAlarm.rootException != undefined) {
//			currentObject.view.down('#rootExceptionLabel').setHidden(false);
			currentObject.view.down('#rootExceptionLabel').setValue(appAlarm.rootException);
		}
		if(appAlarm.contextType == 0 || appAlarm.contextType == -1 || (appAlarm.contextType == -2 && appAlarm.exception!=undefined && appAlarm.exception != "")){
			currentObject.severity.disable();
			currentObject.view.down('#architectureLayer').disable();
			currentObject.view.down('#eventAction').disable();
			currentObject.view.down('#alarmStatus').disable();
			currentObject.message.setReadOnly(true);
			currentObject.diagnose.setReadOnly(true);
			currentObject.solution.setReadOnly(true);
		/*} else if(appAlarm.contextType == -2 && (appAlarm.exception==undefined || appAlarm.exception=="")) {
			currentObject.severity.disable();
			currentObject.view.down('#architectureLayer').disable();
			currentObject.view.down('#eventAction').disable();
			currentObject.view.down('#alarmStatus').disable();*/
		} else {
			currentObject.severity.enable();
			currentObject.view.down('#architectureLayer').enable();
			currentObject.view.down('#eventAction').enable();
			currentObject.view.down('#alarmStatus').enable();
			currentObject.message.setReadOnly(false);
			currentObject.diagnose.setReadOnly(false);
			currentObject.solution.setReadOnly(false);
		}
		var connector_orders = appAlarm.connectorOrder.split(',');
		var contextId = appAlarm.alarmId.substring((appAlarm.alarmId.length-6),(appAlarm.alarmId.length-3));
		currentObject.view.down('#contextId').setValue(contextId);
		currentObject.view.down('#contextId').setReadOnly(true);
		this.connectorOrderGrid.getStore();
		for(var j = 0;j<connector_orders.length;j++) {
			var connector_order = connector_orders[j].trim();
			if(connector_order != -1) {
				currentObject.connectorOrder.select(currentObject.connectorOrder.getStore().getAt(j));
				currentObject.connectorOrderSeverity.select(currentObject.connectorOrderSeverity.getStore().getAt(connector_order));
				this.onConnectorAddButton();
			}
		}
	
		currentObject.message.setValue(this.getFormattedMessage(appAlarm.message));
		currentObject.diagnose.setValue(appAlarm.diagnose);
		currentObject.solution.setValue(appAlarm.solution);
	},
	
	getFormattedMessage : function(message) {
		message = message.replace(/%s/ig, "<string>");
		message = message.replace(/%c/ig, "<character>");
		message = message.replace(/%d/ig, "<decimal>");
		message = message.replace(/%f/ig, "<float>");
		return message;
	},
	
	getFormatSpecifiedMessage : function(message) {
		message = message.replace(/<string>/ig, "%s");
		message = message.replace(/<character>/ig, "%c");
		message = message.replace(/<decimal>/ig,"%d");
		message = message.replace(/<float>/ig,"%f");
		return message;
	},
	
	clearConnectorOrderGrid : function() {
		this.connectorOrderGrid.store.removeAll();
	},
	
	onClearButton : function() {
		var appAlarmForm = this.view.getForm();
		appAlarmForm.reset();
		this.clearConnectorOrderGrid();
	},
	
	onResetButton : function(but,evt){
		this.onClearButton();
		var appAlarmGridPanelController = this.view.up().down('#appAlarmGridPanel').getController();
		this.view.down('#addButton').setText('Add Alarm');
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
					fields : ['severityId', 'severity'],
					data : severityData,
					sorters: [{
				         property: 'severityId',
				         direction: 'ASC'
				     }],
				});

		this.connectorOrderSeverity.setStore(severityStore);
		this.severity.setStore(severityStore);
	},
	setArchitectureLayers : function(architectureLayers) {
		var store = this.view.down('#architectureLayer');
		var layerStore = Ext.create('Ext.data.Store', {
					fields : ['layerId', 'layer'],
					data : architectureLayers,
					sorters: [{
				         property: 'layerId',
				         direction: 'ASC'
				     }],
				});

		store.setStore(layerStore);
	},
	setAlarmStatus: function(alarmStatus) {
		var store = this.view.down('#alarmStatus');
		var statusStore = Ext.create('Ext.data.Store', {
					fields : ['statusId', 'status'],
					data : alarmStatus,
					sorters: [{
				         property: 'status',
				         direction: 'ASC'
				     }],
				});

		store.setStore(statusStore);
	},
	setEventActions: function(eventActions) {
		var store = this.view.down('#eventAction');
		var actionStore = Ext.create('Ext.data.Store', {
					fields : ['eventActionId', 'eventAction'],
					data : eventActions,
					sorters: [{
				         property: 'eventActionId',
				         direction: 'ASC'
				     }],
				});

		store.setStore(actionStore);
	},
	
	onConnectorAddButton : function(but, evt) {
		var currentObject = this;
		var cid = this.connectorOrder.getValue();
		var name = this.connectorOrder.getRawValue();
		var severityId = this.connectorOrderSeverity.getValue();
		var severity = this.connectorOrderSeverity.getRawValue();
		var isPresent = false;
		if(cid==null || name == null || severityId==null || severity==null || cid=="" || name == "" || severityId=="" || severity=="") {
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
	
	onAddAppAlarm : function(but, evt){
		var form = but.up('form').getForm();
		var formData = form.getValues();
		this.generateAlarmId();
		formData.alarmId = this.alarmId.getValue();
		
		var connectorOrderStore = this.connectorOrder.getStore();
		var connector_order = new Array(connectorOrderStore.data.length);
		for(i = 0; i<4 ; i++) {
			connector_order[i] = -1;
		}
		/*for(i = 0; i<connector_order.length ; i++) {
			connector_order[i] = -1;
		}*/
		var connectorOrderGridStore = this.connectorOrderGrid.getStore();
		connectorOrderGridStore.each(function(rec) {
			var cid = rec.get('cid');
			var name = rec.get('name')
			var severityId = rec.get('severityId');
			var severity = rec.get('severity')
			
			connector_order[cid - 1] = parseInt(severityId);
		});
		
		formData.connectorOrder = connector_order
		formData.message = this.getFormatSpecifiedMessage(formData.message);
		formData.exception = but.up().up().up().down('#exceptionLabel').getValue();
		formData.rootException = but.up().up().up().down('#rootExceptionLabel').getValue();
		var appAlarmGridPanelController = this.view.up().down('#appAlarmGridPanel').getController();
		appAlarmGridPanelController.addDataToGrid(formData);
		but.setText('Update Alarm');
	},
	
	generateAlarmId : function() {
		alarmId = "";
		tree = this.view.up().up().up().down('#logModuleTree');
		alarmId = alarmId.concat(tree.getSelection()[0].parentNode.data.contextPrefix);
		alarmId = alarmId.concat(tree.getSelection()[0].data.domainPrefix);
		alarmId = alarmId.concat(this.view.down('#architectureLayer').getValue());
		alarmId = alarmId.concat(this.view.down('#severity').getValue());
		alarmId = alarmId.concat(this.view.down('#eventAction').getValue());
		alarmId = alarmId.concat(this.view.down('#contextId').getValue());
		alarmId = alarmId.concat(this.view.down('#alarmStatus').getValue());
		this.view.down('#alarmId').setValue(alarmId);
	}
});