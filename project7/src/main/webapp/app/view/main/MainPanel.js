Ext.define('Project7.view.main.MainPanel', {
	extend : 'Ext.panel.Panel',
	xtype : 'mainPanel',

	requires : [ 'Project7.view.resource.ResourcePanel',
			'Project7.view.main.TopPanel.TopPanel',
			'Ext.layout.container.Accordion', 'Ext.Img', 'Ext.button.Split' ],

	layout : 'border',
	anchor : '100% 100%',

	items : [ {
		region : 'north',
		xtype : 'panel',
		items : [ {
			xtype : 'customHeader'
		},
		]
	}, {
		region : 'west',
		itemId : 'westPanel',
		collapsible : true,
		split : true,
		width : '18%',
		title : 'Resources',
		xtype : 'resourcePanel',
		listeners : {
			afterrender : function(panel) {
				panel.hide();
			}
		},
	}, {
		region : 'center',
		xtype : 'tabpanel',
		itemId : 'appMainTabPanel',
		id : 'appMainTabPanel',
	}, {
		region : 'east',
		title : 'Help',
		split : true,
		collapsible : true,
		width : '20%',
		collapsed : true,
		plugins : 'responsive',
		responsiveConfig : {
			tall : {
				region : 'south',
			},
			wide : {
				region : 'east',
			}
		},
		items : [ {

			html : 'The page is under construction.....'

		} ]
	} ]
});
