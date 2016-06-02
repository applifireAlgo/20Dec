/**
 * 
 */
Ext.define('Project7.view.logalarm.mainscreen.AddLogAlarm', {
	extend : 'Ext.form.Panel',
	xtype : 'addLogAlarm',
	requires : [ 'Project7.view.logalarm.mainscreen.AddLogAlarmController',
			'Project7.view.logalarm.mainscreen.AppAlarmGridPanel',
			'Project7.view.logalarm.mainscreen.AppAlarmPanel',
			'Ext.form.FieldSet', 'Ext.form.field.ComboBox',
			'Ext.form.FieldContainer' ],
	controller : 'addLogAlarmController',
	title : 'Add Alarm',
	layout : 'anchor',
	autoScroll : false,
	defaults : {
		anchor : '100% 100%',
		margin : 10,
		labelWidth : 120,
		allowBlank : false,
	},
	
	items : [ {
		xtype : 'fieldset',
		title : 'Domain configuration',
		collapsible : true,
		defaults : {
			anchor : '100% 100%',
			margin : 10,
			labelWidth : 120,
			allowBlank : false,
		},
		items : [ {
			xtype : 'hidden',
			name : 'bcId',
			itemId : 'bcId',
			value : ''
		},{
			xtype : 'hidden',
			name : 'domainId',
			itemId : 'domainId',
			value : ''
		},{
			xtype : 'textfield',
			name : 'domainName',
			itemId : 'domainName',
			readOnly : true,
			hidden : true,
			fieldLabel : 'Domain name',
			emptyText : 'Domain name',
			blankText : 'Domain name should not be blank!'
		}, {
			xtype: 'fieldcontainer',
			layout: 'column',
			items : [{
				xtype : 'checkbox',
				name : 'trace',
				columnWidth : .30,
				itemId : 'trace',
				fieldLabel : 'Trace',
			}, {
				xtype : 'combobox',
				name : 'criticality',
				itemId : 'criticality',
				fieldLabel : 'Criticality',
				margin : '0 10 0 0',
				columnWidth : .30,
				store : {
					fields : ['criticality','label'],
					sorters: 'criticality',
					data : [{ label : 'LOW', criticality : '1' },
					        { label : 'MEDIUM', criticality : '2' },
					        { label : 'HIGH', criticality : '3' }]
				},
				displayField : 'label',
				valueField : 'criticality',
				emptyText : 'Criticality',
				blankText : 'Criticality should not be blank!'
			}]
		}, {
			xtype : 'fieldcontainer',
			fieldLabel : 'Default Connector Order',
			defaults : {
				labelWidth : 120,
				allowBlank : false,
			},
			items : [{
				xtype : 'grid',
				name : 'connectorOrderGrid',
				itemId : 'connectorOrderGrid',
				stateful : true,
				collapsed : false,
				multiSelect : true,
				autoScroll : true,
				fieldLabel : 'Connector Order',
				columnLines : true,
				viewConfig : {
					enableTextSelection : true
				},
				store : {
					fields : [],
					data : []
				},
				columns : [ {
					text : 'Connector ID',
//					width : '25%',
					sortable : true,
					hidden : true,
					dataIndex : 'cid'
				}, {
					text : 'Connector Name',
					width : '50%',
					dataIndex : 'name'
				}, {
					text : 'Severity ID',
//					width : '25%',
					hidden : true,
					dataIndex : 'severityId'
				}, {
					text : 'Severity',
					width : '50%',
					dataIndex : 'severity'
				}]
			} ]
		}]

	}, {
		xtype : 'appAlarmPanel',
		itemId : 'appAlarmPanel'
	}, {
		xtype : 'appAlarmGridPanel',
		itemId : 'appAlarmGridPanel'
	} ]
});