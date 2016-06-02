Ext.define('Project7.view.appreportui.chart.GroupChartPanel', {
	extend : 'Ext.panel.Panel',
	requires:['Project7.view.appreportui.chart.GroupChartPanelController',
	          'Project7.view.appreportui.chart.ChartPanel'],
	controller:'groupChartPanelController',
	xtype : 'groupChartPanel',
	groupChartJson:null,
	//title:'GPanel',
	plugins: 'responsive',
	responsiveConfig: {
       portrait: {
    	   layout: {
    	        type: 'table',
    	        columns : 1,
    	        tableAttrs: {
    	        	 style: {
    	        		 width: "100%"
    	        	 }
    	        },
    	        tdAttrs : {
    	            align : 'center',
    	            valign : 'middle',
    	        }
    		}
        },
        landscape: {
        	layout: {
                type: 'table',
                //columns :2,
                tableAttrs: {
                	 style: {
                		 width: "100%"
                	 }
                },
                tdAttrs : {
                    align : 'center',
                    valign : 'middle',
                }
        	}
        }
    },
    margin:10,
	header:{
				hidden:true,
 	},
 	bodyStyle:{
 		background:"#f6f6f6"
 	},
	
	listeners:{
		afterrender:'grpChartPanelAfterRender'
	}
});