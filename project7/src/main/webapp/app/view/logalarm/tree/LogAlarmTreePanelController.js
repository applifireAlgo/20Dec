/**
 * 
 */
Ext.define('Project7.view.logalarm.tree.LogAlarmTreePanelController', {
	extend : 'Ext.app.ViewController',
	alias : 'controller.logAlarmTreePanelController',
	
	logModuleTree : null,
	
	init : function(){
		this.logModuleTree = this.view.down('#logModuleTree');
	},
	
	clearTreePanel : function() {
		this.logModuleTree.store.sync();
		this.setTreeStore();
	},
	
	setTreeStore : function(){
		 var node = this.logModuleTree.getRootNode();
		 node.removeAll();
		 this.logModuleTree.getSelectionModel().deselectAll();
	},
		
	setFormData : function(logModuleData){
		this.clearTreePanel();
		var data = Ext.JSON.decode(logModuleData);
		var me = this;
		var logModuleTree = this.logModuleTree;
		var logModuleTreeRootNode = logModuleTree.getRootNode();
		logModuleTreeRootNode.removeAll();
		Ext.Array.each(data, function(record) {
			me.addChild(logModuleTree, record);
		});
	},
	
	addChild : function(parentNode, record) {
		parentNode.getRootNode().appendChild({"bId": record.bId, "text" :record.contextName + " [" + record.contextPrefix + "]", "contextName": record.contextName, "systemContext": record.systemContext,"contextPrefix": record.contextPrefix, "icon": 'images/folder-database-icon.png', "expanded": true});
		if(record.domains != null || record.domains != undefined) {
			var child = record.domains;
			for(var i = 0 ; i < parentNode.getRootNode().childNodes.length; i++) {
				var node = parentNode.getRootNode().childNodes[i];
				if(node.data.bId == record.bId) {
					for (var x = 0; x < child.length; x++) {
						child[x].text = child[x].domain + " [" + child[x].domainPrefix + "]";
						child[x].leaf = true;
						child[x].icon = "images/table_edit_icon.png";
						node.appendChild(child[x]);
					}
				}
			}
		}
	},
	
	onItemClick : function(view, record, item, index, e) {
		var tabPanel = this.view.up().down('#logAlarmMainViewTabPanel');
		if(record.data.leaf) {
			var domainData = record.data;
			var bcData = record.parentNode.data;
			tabPanel.getActiveTab().setTitle("Domain name : " + domainData.domain);
			tabPanel.getActiveTab().getController().setFormData(bcData, domainData);	
			this.view.up().getController().setButtonText("Update");
			if(!bcData.systemContext) {
				this.view.up().down('#appAlarmPanel').down('#addButton').setDisabled(true);
				this.view.up().down('#appAlarmPanel').down('#resetButton').setDisabled(true);
				this.view.up().down('#saveButton').setDisabled(true);
			} else if(bcData.systemContext) {
				this.view.up().down('#appAlarmPanel').down('#addButton').setDisabled(false);
				this.view.up().down('#appAlarmPanel').down('#resetButton').setDisabled(false);
				this.view.up().down('#saveButton').setDisabled(false);
			}
		}
	},
	
	onAddModuleClick : function() {
		 this.view.up().getController().onClearButton();
	}
	
});