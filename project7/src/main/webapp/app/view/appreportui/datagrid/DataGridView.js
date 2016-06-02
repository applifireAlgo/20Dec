Ext.define('Project7.view.appreportui.datagrid.DataGridView', {
	extend : 'Ext.grid.Panel',
	requires : ['Project7.view.appreportui.datagrid.DataGridViewController'],
	xtype:'dataGridView',
	controller:'dataGridController',
	itemId : 'dataGridViewId',
	columnLines:true,
	viewConfig:{
		stripeRows:true,
	},
	reserveScrollbar:true,
	reportDataJSON:null,
	reportView:null,
	bodyStyle:{
        background:"#f6f6f6"
	},
	listeners : {
		scope:'controller',
		cellclick : 'dataGridCellClick',
		itemcontextmenu : 'dataGridRightClick',
	}
});