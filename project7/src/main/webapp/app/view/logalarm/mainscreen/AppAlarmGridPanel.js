/**
 * 
 */
Ext.define('Project7.view.logalarm.mainscreen.AppAlarmGridPanel', {
	extend : 'Ext.grid.Panel',
	xtype : 'appAlarmGridPanel',
	requires : [ 'Project7.view.logalarm.mainscreen.AppAlarmGridPanelController',
			'Ext.grid.column.Action' ],
	controller : 'appAlarmGridPanelController',
	stateful : true,
	collapsible : true,
	collapsed : false,
	multiSelect : true,
	autoScroll : false,
	title : 'App Alarams',
	columnLines : true,
	layout : 'fit',
	height : 500,
	viewConfig : {
		enableTextSelection : true
	},
	store : {
		fields : [ {
			name : 'alarmIndex',
			type : 'string'
		}, {
			name : 'alarmId',
			type : 'string'
		}, {
			name : 'connectorOrder',
			type : 'string'
		}, {
			name : 'message',
			type : 'string'
		}, {
			name : 'diagnose',
			type : 'string'
		}, {
			name : 'solution',
			type : 'string'
		}, ],
		data : [],
		sorters: [{
	         property: 'alarmId',
	         direction: 'ASC'
	     }],
	},
	initComponent : function() {
		
		var me = this;
		me.columns = [ {
			text : 'Alarm Index',
//			width : '1%',
			sortable : false,
			dataIndex : 'alarmIndex',
			hidden : true
		}, {
			text : 'Alarm ID',
			width : '08%',
			sortable : false,
			dataIndex : 'alarmId',
//			hidden : true
		}, {
			text : 'Alarm context',
			width : '06%',
			sortable : false,
			hidden : true,
			dataIndex : 'alarmContext'
		}, {
			text : 'Context type',
			width : '06%',
			sortable : false,
//			hidden : true,
			dataIndex : 'contextType'
		}, {
			text : 'Connector Order',
			width : '08%',
			sortable : false,
			dataIndex : 'connectorOrder'
		}, {
			text : 'Bounded context id',
			width : '06%',
			sortable : false,
			dataIndex : 'boundedContextId',
			hidden : true
		}, {
			text : 'Domain id',
			width : '08%',
			sortable : false,
			dataIndex : 'domainId',
			hidden : true
		}, {
			text : 'Severity',
			width : '07%',
			sortable : false,
			dataIndex : 'severity'
		}, {
			text : 'Layer',
			width : '08%',
			sortable : false,
			dataIndex : 'architectureLayer'
		}, {
			text : 'Message',
			width : '15%',
			sortable : false,
			dataIndex : 'message'
		}, {
			text : 'Diagnose',
			width : '14%',
			sortable : false,
			dataIndex : 'diagnose'
		}, {
			text : 'Solution',
			width : '14%',
			sortable : false,
			dataIndex : 'solution'
		}, {
			text : 'Status',
			width : '09%',
			sortable : false,
			dataIndex : 'alarmStatus'
		}, {
			text : 'Event action',
			width : '08%',
			sortable : false,
			dataIndex : 'eventAction'
		}, {
			text : 'Exception',
			width : '08%',
			sortable : false,
			dataIndex : 'exception'
		}, {
			text : 'Root exception',
			width : '08%',
			sortable : false,
			dataIndex : 'rootException',
			hidden : true
		}, {
			text : 'Alarm placement',
			width : '08%',
			sortable : false,
			dataIndex : 'alarmPlacement',
			hidden : true
		}];

		me.callParent();
	},
	listeners : {
		select : function(selModel, record, index, options) {
			var appAlarm = record.getData();
			appAlarm.id = appAlarm.alarmIndex;
			this.up().down('#appAlarmPanel').getController().renderFormData(appAlarm);
		}
	}
});
