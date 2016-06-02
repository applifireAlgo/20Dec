Ext.define('Project7.view.maintopmenu.MenuPanel', {
	extend : 'Ext.toolbar.Toolbar',
	
	xtype : 'menuPanel',
	itemId : 'menuPanel', 
	
	requires : [ 'Project7.view.maintopmenu.MenuPanelController' //,'Ext.button.Split' 
	             ],
	controller : 'menuPanelController',
	
	baseCls : 'menuPanelBody',
	
	listeners : {
		scope : 'controller',
		afterrender : 'loadMenus'
	}	
});