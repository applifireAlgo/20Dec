/** Rename this file as CustomDateField 
 * but a file with same name is already there in app 
 * so make sure that app is getting generated without any problem after removing that file n 
 * then do changes for this file as well */

Ext.define('Project7.view.fw.component.DateFields',{
	extend : 'Ext.form.Date',
	xtype : 'datefields',
	
	getValues : function() {
		
		if (this.getSubmitValue() != "" && this.getSubmitValue() != null) {
			/** Appending time for datefield *//*
			var value = this.getSubmitValue() + " 00:00:00";
			var timezone = Ext.util.Cookies.get('XA_TID');
			return value + " "+ timezone;*/
			
			return this.getSubmitValue();
			
		} else {
			return undefined;
		}
	},

	setValues : function(value) {
		
		/** Setting raw value by removing timezone *//*
		var res = value.replace(' '+Ext.util.Cookies.get('XA_TID'), '');
	    var dt = Ext.Date.parse(res, 'd-m-Y H:i:s');
 		if(!dt){
 			dt = Ext.Date.parse(res, 'd-n-Y H:i:s');							
 		}
 		this.setValue(dt);*/
		
		var date = new Date(value);
		this.setValue(date);
 	},
 	
 	/**Used for report custom ToDate daterange picker value*/
 	getCustomToDateValues:function(){
		if (this.getSubmitValue() != "" && this.getSubmitValue() != null) {
			/** Appending time for datefield */
			var value = this.getSubmitValue() + " 23:59:59";
			var timezone = Ext.util.Cookies.get('XA_TID');
			return value + " "+ timezone;
		} else {
			return undefined;
		}
	},
 	
 	/**Used for report custom FromDate daterange picker value*/
 	getCustomFromDateValues:function(){
		if (this.getSubmitValue() != "" && this.getSubmitValue() != null) {
			/** Appending time for datefield */
			var value = this.getSubmitValue() + " 00:00:00";
			var timezone = Ext.util.Cookies.get('XA_TID');
			return value + " "+ timezone;
		} else {
			return undefined;
		}
	}
});