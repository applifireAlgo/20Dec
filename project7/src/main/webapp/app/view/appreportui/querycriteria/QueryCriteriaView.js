Ext.define('Project7.view.appreportui.querycriteria.QueryCriteriaView',{
	extend : 'Ext.panel.Panel',
	requires : [ 'Project7.view.appreportui.querycriteria.QueryCriteriaViewController' ],
	xtype : 'queryCriteriaPanel',
	controller : 'queryCriteriaController',
	bodyPadding : '10',
	bodyStyle: 'background:#f6f6f6;',
	itemId : 'querycriteria',
	reportDataJson:null,
	reportView:null,
	items:[{
		xtype:'panel',
		bodyPadding:30,
		shrinkWrap:3,
		margin:10,
		style:{
                    "box-shadow": "0 3px 6px rgba(0,0,0,0.16), 0 3px 6px rgba(0,0,0,0.23);"
    	},
    	layout:{type:'vbox'},
		items:[{
	        xtype:'fieldset',
	        title: 'Auto Refresh',
	        checkboxName:'auto_refersh',
	        itemId:'isAutoRef',
	        checkboxToggle: true,
	        collapsed: true,
	        layout:'anchor',
	        bodyPadding:0,
	        border:0,
	        listeners:{
	        	collapse:'onUncheckAutoRefresh'
	        },
	        items :[{
	    		xtype : 'combo',
	    		itemId:'cmbrefInterval',
	    		fieldLabel : 'Refresh Interval',
	    		margin:'10 0 0 0',
	    		//labelWidth:110,
	    		store : Ext.create('Ext.data.Store', {
	    			fields : [ 'displayValue', 'value' ],
	    			data : [ {
	    				"displayValue" : "5 Seconds",
	    				"value" : 5000
	    			}, {
	    				"displayValue" : "10 Seconds",
	    				"value" : 10000
	    			}, {
	    				"displayValue" : "20 Seconds",
	    				"value" : 20000
	    			}, {
	    				"displayValue" : "30 Seconds",
	    				"value" : 30000
	    			}, {
	    				"displayValue" : "1 Minute",
	    				"value" : 60000
	    			}, {
	    				"displayValue" : "3 Minute",
	    				"value" : 180000
	    			}, {
	    				"displayValue" : "5 Minute",
	    				"value" : 300000
	    			},
	    			//...
	    			]
	    		}),
	    		name : "interval",
	    		queryMode : 'local',
	    		displayField : 'displayValue',
	    		valueField : 'value',
	    		listeners:{
	    			select:'changeTimers'
	    		}
	    	}]
	    
		},{
			xtype:'panel',
			border:false,
			bodyPadding:0,
			itemId:'qcInnerPanelId',
			margin:'0 0 0 5',
			items:[]
			
		}],
		bbar:{
				bodyPadding:0,
				border:0,
				items:[{xtype:'tbfill'},{
				text : 'Clear',
				itemId:"btnClear",
				scale:'medium',
				//icon : 'images/closable.png',
				listeners:{
					click:'clearData'
				}
			}, {
				text : 'Search',
				itemId:"btnSearch",
				scale:'medium',
				//icon : 'resources/css/images/search.png',
				listeners:{
					click:'filterData'
				}
			},{xtype:'tbfill'}]
		  },
		plugins:'responsive',
		responsiveConfig:{
			portrait: {
                width:'100%'
            },
            landscape: {
               width:'35%'
            }
		}
	}],
	listeners:{
		scope:'controller',
		added:'afterQCPanelAdded'
	}
});