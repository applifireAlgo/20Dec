Ext.define('Project7.view.appreportui.ReportView', {
	extend : 'Ext.form.Panel',
	requires : ['Project7.view.appreportui.ReportViewController',
	            'Project7.view.appreportui.datagrid.DataGridPanel',
	            'Project7.view.appreportui.datagrid.DataGridView',
	            'Project7.view.appreportui.querycriteria.QueryCriteriaView',
	            'Project7.view.appreportui.chart.ChartView',
	            'Project7.view.appreportui.datapoint.DataPointView',
	            'Project7.view.googlemaps.map.MapPanel',
	            'Project7.view.appreportui.chartpoint.ChartPointView'
	            ],
	xtype : 'reportView',
	controller : 'reportViewController',
	layout : 'border',
	reportJSON:null,
	bodyStyle:{
        background:'#f6f6f6'
    },
	listeners : {
		scope : 'controller',
		afterrender : 'afterRenderReport',
		boxready : 'fetchReportData',
		added:'onReportAdded'
	}
});
