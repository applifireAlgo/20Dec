Ext.define('Project7.view.appreportui.chart.ChartPanel', {
	extend : 'Ext.panel.Panel',
	requires:['Project7.view.appreportui.chart.ChartPanelController'],
	controller:'chartPanelController',
	xtype : 'chartPanel',
	chartJson:null,
	title:'Panel',
	layout:{
		type:'fit',
		align:'center',
		pack:'center'
	},
	margin:10,
	header:{
				hidden:true,
 	},
	style:{
                    "box-shadow": "0 3px 6px rgba(0,0,0,0.16), 0 3px 6px rgba(0,0,0,0.23);"
    },
    bodyStyle:{
        background:"#ffffff"
    },
    tbar:["->",{
 		xtype:'button',
 		icon:"resources/appicons/ic_full_screen_arrows.png",
 		scale:'medium',
 		tooltip:"Full screen",
 		border:false,
 		style:{background:"#ffffff"},
 		listeners:{
 				click:'onChartFullScreenClick'
		}

 	}],
	listeners:{
		afterrender:'chartPanelAfterRender'
	}
});