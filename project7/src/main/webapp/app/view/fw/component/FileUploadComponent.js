Ext.define('Project7.view.fw.component.FileUploadComponent', {
	xtype : "fileupload",
	extend : 'Ext.form.FieldSet',	
	itemId : 'dataUploadFieldSet',
	fileUploadPath:"",
	border:true,
	items : [{
		xtype : 'form',
		layout : 'hbox',
		defaults :{
			margin: "5 5 5 5",
		},
		items : [{
			xtype : 'filefield',
			name : 'file',
			flex : 3,
			listeners : {
				change : function(button, e, value) {
					/** Set file limit up to 5mb */
					if(event.target.files[0].size > 5000000){
						Ext.Msg.alert('Error', 'Please upload file under 5mb');	
						this.setRawValue("");					
						return ;					
					}
					var newValue = e.replace(/^c:\\fakepath\\/i, ''); 
					this.setRawValue(newValue);
					this.up().down('#uploadButton').setDisabled(false);
				},
				render : function() {
					var view = this.up();
					this.setFieldLabel(view.fileUploadCaption);
				}
			}
		 },{
			 xtype : 'button',
			action:'dataUpload',
			text : 'Upload',
			itemId : 'uploadButton',
			disabled : true,
			width:150,
			handler : function(btn, e, eOpts) {
				
				var fileUploadView = btn.up('fileupload');
				var uploadForm = btn.up('form');
				
				uploadForm.submit({
			url : 'secure/UploadService/upload',
			method:'POST',
			maskEnable: true,
            maskEle: uploadForm,
            maskMsg: 'File uploading...',
            fileUploadView:fileUploadView,
			scope:this,
			success : function(response,opts) {
				var responseData = Ext.JSON.decode(opts.response.responseText);
				
				var fileUploadView = opts.fileUploadView;
				fileUploadView["fileUploadPath"] =  responseData.response.data;
				fileUploadView.down('#uploadButton').setDisabled(true);
					
				var fileField = fileUploadView.down('filefield');
				fileField.setRawValue(responseData.response.data);
			},
			failure : function(response, opts) {
				var responseData = Ext.JSON.decode(opts.response.responseText);
				
				var fileUploadView = opts.fileUploadView;
				fileUploadView["fileUploadPath"] =  responseData.response.data;
				fileUploadView.down('#uploadButton').setDisabled(true);
					
				var fileField = fileUploadView.down('filefield');
				fileField.setRawValue(responseData.response.data);
				
			}
		});
	}
		 }
		         ]
		
	}],
	getValue : function() {
		
		return this.fileUploadPath;
	},

	setText :function(value) {
		
		this.down('filefield').setRawValue(value);
	}
});