Ext.define('Project7.project7.web.com.controller.organization.contactmanagement.CoreContactsMainController', {
     extend: 'Project7.view.fw.frameworkController.AggregateViewController',
     alias: 'controller.CoreContactsMainController',
     init: function() {
          var form = this.view.down('#CoreContactsForm');
          form.reset();
          this.addressTypeIdLoad();
          this.timezoneLoad();
          this.titleIdLoad();
          this.nativeLanguageCodeLoad();
          this.genderIdLoad();
          this.commGroupIdLoad();
          this.countryIdLoad();
          if (this.view.disableDB != null && this.view.disableDB) {
               this.updateFormUI(form, 'Update', true);
               this.hideDataBrowser();
          } else {
               this.updateFormUI(form, 'Save', true);
               this.renderTreeGridData();
          }
     },
     addressTypeIdLoad: function() {
          var scope = this.getView();
          Ext.Ajax.request({
               url: 'secure/AddressType/findAll',
               method: 'GET',
               async: false,
               sender: scope,
               jsonData: {},
               success: function(response, scope) {
                    var compRef = scope.sender.down('#addressTypeId');
                    scope.sender.controller.assignAllComboData(scope.sender, 'addressTypeId', response.responseText);
                    scope.sender.controller.addCommunicationLog(response, true, compRef);
               },
               failure: function(response, scope) {
                    var compRef = scope.sender.down('#addressTypeId');
                    scope.sender.controller.addCommunicationLog(response, false, compRef);
               }
          }, scope);
     },
     timezoneLoad: function() {
          var scope = this.getView();
          Ext.Ajax.request({
               url: 'secure/Timezone/findAll',
               method: 'GET',
               async: false,
               sender: scope,
               jsonData: {},
               success: function(response, scope) {
                    var compRef = scope.sender.down('#timezone');
                    scope.sender.controller.assignAllComboData(scope.sender, 'timezone', response.responseText);
                    scope.sender.controller.addCommunicationLog(response, true, compRef);
               },
               failure: function(response, scope) {
                    var compRef = scope.sender.down('#timezone');
                    scope.sender.controller.addCommunicationLog(response, false, compRef);
               }
          }, scope);
     },
     titleIdLoad: function() {
          var scope = this.getView();
          Ext.Ajax.request({
               url: 'secure/Title/findAll',
               method: 'GET',
               async: false,
               sender: scope,
               jsonData: {},
               success: function(response, scope) {
                    var compRef = scope.sender.down('#titleId');
                    scope.sender.controller.assignAllComboData(scope.sender, 'titleId', response.responseText);
                    scope.sender.controller.addCommunicationLog(response, true, compRef);
               },
               failure: function(response, scope) {
                    var compRef = scope.sender.down('#titleId');
                    scope.sender.controller.addCommunicationLog(response, false, compRef);
               }
          }, scope);
     },
     nativeLanguageCodeLoad: function() {
          var scope = this.getView();
          Ext.Ajax.request({
               url: 'secure/Language/findAll',
               method: 'GET',
               async: false,
               sender: scope,
               jsonData: {},
               success: function(response, scope) {
                    var compRef = scope.sender.down('#nativeLanguageCode');
                    scope.sender.controller.assignAllComboData(scope.sender, 'nativeLanguageCode', response.responseText);
                    scope.sender.controller.addCommunicationLog(response, true, compRef);
               },
               failure: function(response, scope) {
                    var compRef = scope.sender.down('#nativeLanguageCode');
                    scope.sender.controller.addCommunicationLog(response, false, compRef);
               }
          }, scope);
     },
     genderIdLoad: function() {
          var scope = this.getView();
          Ext.Ajax.request({
               url: 'secure/Gender/findAll',
               method: 'GET',
               async: false,
               sender: scope,
               jsonData: {},
               success: function(response, scope) {
                    var compRef = scope.sender.down('#genderId');
                    scope.sender.controller.assignAllComboData(scope.sender, 'genderId', response.responseText);
                    scope.sender.controller.addCommunicationLog(response, true, compRef);
               },
               failure: function(response, scope) {
                    var compRef = scope.sender.down('#genderId');
                    scope.sender.controller.addCommunicationLog(response, false, compRef);
               }
          }, scope);
     },
     commGroupIdLoad: function() {
          var scope = this.getView();
          Ext.Ajax.request({
               url: 'secure/CommunicationGroup/findAll',
               method: 'GET',
               async: false,
               sender: scope,
               jsonData: {},
               success: function(response, scope) {
                    var compRef = scope.sender.down('#commGroupId');
                    scope.sender.controller.assignAllComboData(scope.sender, 'commGroupId', response.responseText);
                    scope.sender.controller.addCommunicationLog(response, true, compRef);
               },
               failure: function(response, scope) {
                    var compRef = scope.sender.down('#commGroupId');
                    scope.sender.controller.addCommunicationLog(response, false, compRef);
               }
          }, scope);
     },
     countryIdLoad: function() {
          var scope = this.getView();
          Ext.Ajax.request({
               url: 'secure/Country/findAll',
               method: 'GET',
               async: false,
               sender: scope,
               jsonData: {},
               success: function(response, scope) {
                    var compRef = scope.sender.down('#countryId');
                    scope.sender.controller.assignAllComboData(scope.sender, 'countryId', response.responseText);
                    scope.sender.controller.addCommunicationLog(response, true, compRef);
               },
               failure: function(response, scope) {
                    var compRef = scope.sender.down('#countryId');
                    scope.sender.controller.addCommunicationLog(response, false, compRef);
               }
          }, scope);
     },
     onCountryIdChange: function(me, newValue, oldValue, eOpts) {
          this.onCountryIdChangeStateId(me, newValue, oldValue, eOpts);
          this.onCountryIdChangeCityId(me, newValue, oldValue, eOpts);
     },
     onStateIdChange: function(me, newValue, oldValue, eOpts) {
          this.onStateIdChangeCityId(me, newValue, oldValue, eOpts);
     },
     onCommGroupIdChange: function(me, newValue, oldValue, eOpts) {
          this.onCommGroupIdChangeCommType(me, newValue, oldValue, eOpts);
     },
     onCountryIdChangeStateId: function(me, newValue, oldValue, eOpts) {
          var scope = this.getView();
          Ext.Ajax.request({
               async: false,
               url: 'secure/State/findByCountryId',
               method: 'POST',
               jsonData: {
                    findKey: newValue
               },
               sender: scope,
               element: me,
               success: function(response, scope) {
                    var stateIdCombo = scope.element.up('form').down('#stateId');
                    scope.sender.controller.assignComboData(stateIdCombo, response.responseText);
                    scope.sender.controller.addCommunicationLog(response, true, stateIdCombo);
               },
               failure: function(response, scope) {
                    scope.sender.controller.addCommunicationLog(response, false);
               }
          }, scope);
     },
     onCountryIdChangeCityId: function(me, newValue, oldValue, eOpts) {
          var scope = this.getView();
          Ext.Ajax.request({
               async: false,
               url: 'secure/City/findByCountryId',
               method: 'POST',
               jsonData: {
                    findKey: newValue
               },
               sender: scope,
               element: me,
               success: function(response, scope) {
                    var cityIdCombo = scope.element.up('form').down('#cityId');
                    scope.sender.controller.assignComboData(cityIdCombo, response.responseText);
                    scope.sender.controller.addCommunicationLog(response, true, cityIdCombo);
               },
               failure: function(response, scope) {
                    scope.sender.controller.addCommunicationLog(response, false);
               }
          }, scope);
     },
     onStateIdChangeCityId: function(me, newValue, oldValue, eOpts) {
          var scope = this.getView();
          Ext.Ajax.request({
               async: false,
               url: 'secure/City/findByStateId',
               method: 'POST',
               jsonData: {
                    findKey: newValue
               },
               sender: scope,
               element: me,
               success: function(response, scope) {
                    var cityIdCombo = scope.element.up('form').down('#cityId');
                    scope.sender.controller.assignComboData(cityIdCombo, response.responseText);
                    scope.sender.controller.addCommunicationLog(response, true, cityIdCombo);
               },
               failure: function(response, scope) {
                    scope.sender.controller.addCommunicationLog(response, false);
               }
          }, scope);
     },
     onCommGroupIdChangeCommType: function(me, newValue, oldValue, eOpts) {
          var scope = this.getView();
          Ext.Ajax.request({
               async: false,
               url: 'secure/CommunicationType/findByCommGroupId',
               method: 'POST',
               jsonData: {
                    findKey: newValue
               },
               sender: scope,
               element: me,
               success: function(response, scope) {
                    var commTypeCombo = scope.element.up('form').down('#commType');
                    scope.sender.controller.assignComboData(commTypeCombo, response.responseText);
                    scope.sender.controller.addCommunicationLog(response, true, commTypeCombo);
               },
               failure: function(response, scope) {
                    scope.sender.controller.addCommunicationLog(response, false);
               }
          }, scope);
     },
     updateFormUI: function(form, status, butText) {
          for (var index = 0; index < form.items.items.length; index++) {
               var item = form.items.items[index];
               if (item.xtype == 'form' || item.xtype == 'panel') {
                    this.updateFormUI(item, status);
               }
               if (item.isCompositeKey) {
                    if (status == 'Save') {
                         item.setDisabled(false);
                    } else if (status == 'Update') {
                         item.setDisabled(true);
                    }
               }
          }
          if (butText) {
               if (status == 'Save') {
                    form.down('#saveFormButton').setText('Save');
               } else if (status == 'Update') {
                    form.down('#saveFormButton').setText('Update');
               }
          }
     },
     renderTreeGridData: function(responseData) {
          if (responseData == null) {
               responseData = this.fetchDataAjaxCall();
          }
          if (responseData.response.success) {
               var data = responseData.response.data;
               var tree = this.view.down('#CoreContactsTree');
               var rootNode = tree.getRootNode();
               rootNode.removeAll();
               for (var index = 0; index < data.length; index++) {
                    var childNode = {
                         text: data[index].primaryDisplay,
                         bConfig: data[index],
                         leaf: true,
                         icon: 'images/table_icon.png'
                    };
                    rootNode.appendChild(childNode);
               }
               tree.getStore().sort('text', 'ASC');
               this.setGridData(data);
               var compRef = this.view.down('#CoreContactsTree');
          } else if (!sessionTimeOutFlag) {
               this.responseHandler(responseData);
          }
     },
     /*********************Main Controller Functions*********************************/
     onDeleteActionColumnClickMainGrid: function(grid, rowIndex) {
          var recordId = grid.store.data.items[rowIndex].data.primaryKey;
          me = this;
          Ext.MessageBox.confirm({
               title: 'Confirm',
               msg: 'Delete Record',
               buttons: Ext.MessageBox.YESNO,
               fn: function(clickedButtonId) {
                    if (clickedButtonId == 'yes') {
                         me.deleteRecord(recordId);
                    }
               },
               animateTarget: this,
               icon: Ext.MessageBox.QUESTION
          })
     },
     deleteRecord: function(recordId) {
          var me = this;
          var restURL = this.view.restURL;
          var url = 'secure' + restURL + '/' + recordId;
          var jsonData = {};
          Ext.Ajax.request({
               url: url,
               method: 'DELETE',
               jsonData: jsonData,
               success: function(response, opts) {
                    var responseText = Ext.JSON.decode(response.responseText);
                    if (responseText.response.success) {
                         Ext.Msg.alert('Server Response', responseText.response.message);
                         me.refreshMainForm(null, null, opts.method);
                    } else if (!sessionTimeOutFlag) {
                         me.responseHandler(responseText);
                    }
               },
               failure: function(response, scope) {
                    me.addCommunicationLog(response, false);
                    if (!sessionTimeOutFlag) {
                         var responseText = Ext.JSON.decode(response.responseText);
                         me.responseHandler(responseText);
                    }
               }
          });
     },
     resetForm: function(but) {
          var form = but.up('form');
          form.down('#saveFormButton').setText('Save');
          form.reset();
          var grid = this.view.down('#CoreContactsGrid');
          var tree = this.view.down('#CoreContactsTree');
          tree.setSelection();
          grid.setSelection();
          var gridAddress = but.up('form').down('#AddressGrid');
          gridAddress.getStore().removeAll();
          gridAddress.reconfigure();
          var gridCommunicationData = but.up('form').down('#CommunicationDataGrid');
          gridCommunicationData.getStore().removeAll();
          gridCommunicationData.reconfigure();
     },
     hideDataBrowser: function() {
          var form = this.view.down('#CoreContacts');
          var grid = this.view.down('#CoreContactsGrid');
          var tree = this.view.down('#CoreContactsTreeContainer');
          this.view.down('#addNewForm').destroy();
          grid.setHidden(true);
          tree.setHidden(true);
          if (this.view.primaryKey != null) {
               this.findById(this.view.primaryKey);
          }
     },
     fetchDataAjaxCall: function() {
          var url = this.getView().restURL;
          var me = this;
          var data = null;
          Ext.Ajax.request({
               url: 'secure' + url + '/findAll',
               method: 'GET',
               maskEnable: true,
               async: false,
               jsonData: {},
               success: function(response, scope) {
                    var responseData = Ext.JSON.decode(response.responseText);
                    data = responseData;
                    me.addCommunicationLog(response, true);
               },
               failure: function(response, scope) {
                    me.addCommunicationLog(response, false);
                    if (!sessionTimeOutFlag) {
                         var responseText = Ext.JSON.decode(response.responseText);
                         me.responseHandler(responseText);
                    }
               }
          }, this);
          return data;
     },
     onTreeRefreshClick: function(event, toolEl, panelHeader) {
          var responseData = this.fetchDataAjaxCall();
          var data = responseData.response.data;
          var tree = this.view.down('#CoreContactsTree');
          var rootNode = tree.getRootNode();
          rootNode.removeAll();
          for (var index = 0; index < data.length; index++) {
               var childNode = {
                    text: data[index].primaryDisplay,
                    bConfig: data[index],
                    leaf: true,
                    icon: 'images/table_icon.png'
               };
               rootNode.appendChild(childNode);
          }
          tree.getStore().sort('text', 'ASC');
     },
     onGridRefreshClick: function(event, toolEl, panelHeader) {
          var responseData = this.fetchDataAjaxCall();
          var data = responseData.response.data;
          this.setGridData(data);
     },
     refreshMainForm: function(but, data, method) {
          var formPanel = this.view.down('#CoreContactsForm');
          this.updateFormUI(formPanel, 'Save', true); /** Adding data to tree and grid */
          if (data != null) {
               var grid = this.view.down('#CoreContactsGrid');
               var tree = this.view.down('#CoreContactsTree');
               if (method == 'PUT') {
                    if (!(data instanceof Object)) {
                         var data = {
                              'findKey': data
                         };
                    }
                    data = this.findRecordById(this.view.restURL, data);
                    grid.getSelectionModel().selected.items[0].data = data;
                    grid.reconfigure();
                    var node = this.findChild(tree.getRootNode(), 'primaryKey', data.primaryKey);
                    if (node) {
                         node.set('text', data.primaryDisplay);
                         node.bConfig = data;
                         tree.reconfigure();
                    }
               } else {
                    grid.store.add(data);
                    var rootNode = tree.getRootNode();
                    var childNode = {
                         text: data.primaryDisplay,
                         bConfig: data,
                         leaf: true,
                         icon: 'images/table_icon.png'
                    };
                    rootNode.appendChild(childNode);
               }
          } else {
               if (method == 'DELETE') {
                    this.onGridRefreshClick();
                    this.onTreeRefreshClick();
               }
          }
          if (but != null) {
               this.resetForm(but);
          } else {
               var but = this.view.down('#resetFormButton');
               this.resetForm(but);
          }
     },
     /********************************Tree Controller Functions**********************************/
     onFilterClick: function(but, evt) {
          var me = this;
          var currentObject = this.getView();
          var form = but.up('form');
          if (!form.isValid()) {
               return;
          }
          var searchData = this.getData(form);
          Ext.Ajax.request({
               url: 'secure' + currentObject.restURL + '/search',
               method: 'POST',
               maskEnable: true,
               maskEle: currentObject,
               view: currentObject,
               jsonData: Ext.JSON.encode(searchData),
               success: function(response, opts) {
                    var responseText = Ext.JSON.decode(response.responseText);
                    if (responseText.response.success) {
                         me.renderTreeGridData(responseText);
                    } else if (!sessionTimeOutFlag) {
                         me.responseHandler(responseText);
                    }
               },
               failure: function(response, opts) {
                    var currentView = opts.view;
                    var compRef = currentView.down('#CoreContactsTree');
                    me.addCommunicationLog(response, false, compRef);
                    if (!sessionTimeOutFlag) {
                         var responseText = Ext.JSON.decode(response.responseText);
                         me.responseHandler(responseText);
                    }
               }
          });
     },
     treeClick: function(me, record, item, index, e, eOpts) {
          var primaryKey = record.data.bConfig.primaryKey;
          if (record.data.leaf) {
               var CoreContacts = this.view.down('#CoreContactsForm');
               CoreContacts.reset();
               var gridPanel = this.view.down('#CoreContactsGrid');
               var foundRecord = gridPanel.store.findRecord('primaryKey', primaryKey);
               if (gridPanel && foundRecord) {
                    gridPanel.setSelection(foundRecord);
               }
          }
          var formPanel = this.getView().up().down('#CoreContactsForm');
          formPanel.down('#saveFormButton').setText('Update'); /** Finding record by id */
          jsonData = {
               'findKey': primaryKey
          }
          var data = this.findRecordById(this.view.restURL, jsonData);
          this.modifyComponents(data, formPanel);
          this.updateFormUI(formPanel, 'Update', true);
          var AddressGrid = formPanel.down('#AddressGrid');
          AddressGrid.store.loadData(data.address);
          AddressGrid.setSelection(0);
          var CommunicationDataGrid = formPanel.down('#CommunicationDataGrid');
          CommunicationDataGrid.store.loadData(data.communicationData);
          CommunicationDataGrid.setSelection(0);
          this.showFirstCard(formPanel);
          tabPanel = formPanel.up('tabpanel');
          tabPanel.setActiveTab(0);
     },
     /********************************Grid Controller Functions***********************************/
     onGridItemClick: function(me, record, item, index, e, eOpts) {
          var primaryKey = record.data.primaryKey;
          jsonData = {
               'findKey': primaryKey
          } /** Finding record by id */
          var data = this.findRecordById(this.view.restURL, jsonData);
          var treePanel = this.view.up().up().down('#CoreContactsTree');
          var foundNode = this.findChild(treePanel.getRootNode(), 'primaryKey', primaryKey);
          if (treePanel && foundNode) {
               treePanel.setSelection(foundNode);
          }
          var formPanel = this.getView().up().down('#CoreContactsForm');
          formPanel.down('#saveFormButton').setText('Update');
          this.modifyComponents(data, formPanel);
          this.updateFormUI(formPanel, 'Update', true);
          var AddressGrid = formPanel.down('#AddressGrid');
          AddressGrid.store.loadData(data.address);
          AddressGrid.setSelection(0);
          var CommunicationDataGrid = formPanel.down('#CommunicationDataGrid');
          CommunicationDataGrid.store.loadData(data.communicationData);
          CommunicationDataGrid.setSelection(0);
          this.showFirstCard(formPanel);
          tabPanel = formPanel.up('tabpanel');
          tabPanel.setActiveTab(0);
     },
     renderFormValue: function(val, me) {
          store = this.view.up().down('#CoreContacts').down('#' + me.column.dataIndex + '').store;
          if (store.data.length == 0) {
               return '';
          }
          var displayValue = store.findRecord('primaryKey', val).data.primaryDisplay;
          return displayValue != null ? displayValue : '';
     },
     setGridData: function(data) {
          var gridStore = this.view.down('#CoreContactsGrid').store;
          gridStore.removeAll();
          gridStore.add(data);
          gridStore.sort('primaryDisplay', 'ASC');
     },
     /********************************Form Controller Functions***********************************/
     findById: function(primaryKey) {
          var me = this;
          var restURL = this.view.restURL;
          Ext.Ajax.request({
               url: 'secure' + restURL + '/findById',
               method: 'POST',
               controller: me,
               jsonData: {
                    'findKey': primaryKey
               },
               success: function(response, request) {
                    var responseText = Ext.JSON.decode(response.responseText);
                    if (responseText.response.success) {
                         request.controller.loadData(responseText);
                    } else if (!sessionTimeOutFlag) {
                         me.responseHandler(responseText);
                    }
               },
               failure: function(response, scope) {
                    if (!sessionTimeOutFlag) {
                         var responseText = Ext.JSON.decode(response.responseText);
                         me.responseHandler(responseText);
                    }
               }
          });
     },
     loadData: function(responseText) {
          var formPanel = this.getView().up().down('#CoreContactsForm');
          this.modifyComponents(responseText.response.data, formPanel);
     },
     fetchDataFromStore: function(store) {
          storeItems = store.data.items;
          obj = {};
          arr = [];
          for (counter in storeItems) {
               arr.push(storeItems[counter].data);
          }
          return obj['objArr'] = arr;
     },
     addAddress: function(but) {
          var formAddress = but.up().down('form');
          var gridAddress = but.up().up().down('#AddressGrid');
          if (formAddress.isValid()) {
               var values = this.getData(formAddress);
               values = values[gridAddress.bindable];
               if (gridAddress.selection) {
                    gridAddress.selection.data = values;
               } else {
                    gridAddress.getStore().add(values);
               }
               gridAddress.reconfigure();
               formAddress.reset();
               gridAddress.setSelection();
          }
     },
     onAddressGridItemClick: function(cellModel, record, rowIndex, columnIndex, eOpts, comp) {
          var form;
          if (comp != null) {
               form = comp.up();
          } else {
               form = this.view.down('#AddressForm');
          }
          form.loadRecord(record)
     },
     addCommunicationData: function(but) {
          var formCommunicationData = but.up().down('form');
          var gridCommunicationData = but.up().up().down('#CommunicationDataGrid');
          if (formCommunicationData.isValid()) {
               var values = this.getData(formCommunicationData);
               values = values[gridCommunicationData.bindable];
               if (gridCommunicationData.selection) {
                    gridCommunicationData.selection.data = values;
               } else {
                    gridCommunicationData.getStore().add(values);
               }
               gridCommunicationData.reconfigure();
               formCommunicationData.reset();
               gridCommunicationData.setSelection();
          }
     },
     onCommunicationDataGridItemClick: function(cellModel, record, rowIndex, columnIndex, eOpts, comp) {
          var form;
          if (comp != null) {
               form = comp.up();
          } else {
               form = this.view.down('#CommunicationDataForm');
          }
          form.loadRecord(record)
     },
     renderFormValue: function(value, metaData, record, row, col, store, gridView) {
          try {
               var comboStore = this.getView().down('#' + metaData.column.dataIndex).getStore();
               var index = comboStore.findExact('primaryKey', value);
               return comboStore.getAt(index).data.primaryDisplay;
          } catch (e) {
               return value;
          }
     },
     saveForm: function(but, evt) {
          var form = but.up('form');
          var firstCard = form.down('#form0');
          if (!firstCard.getForm().isValid()) {
               this.showCard(form, firstCard);
               return;
          }
          var AddressForm = form.down('#AddressForm');
          var Address = form.down('#AddressGrid');
          if (!(AddressForm.isValid() || Address.store.getCount() != 0)) {
               this.showCard(form, AddressForm);
               return;
          }
          this.addAddress(AddressForm.down('button'));
          var CommunicationDataForm = form.down('#CommunicationDataForm');
          var CommunicationData = form.down('#CommunicationDataGrid');
          if (!(CommunicationDataForm.isValid() || CommunicationData.store.getCount() != 0)) {
               this.showCard(form, CommunicationDataForm);
               return;
          }
          this.addCommunicationData(CommunicationDataForm.down('button'));
          var jsonData = this.getData(form);
          var method;
          if (but.text == 'Save') {
               method = 'POST'
          } else if (but.text == 'Update') {
               method = 'PUT';
               jsonData.systemInfo = {
                    'activeStatus': 1
               }
          }
          restURL = this.view.restURL;
          var me = this;
          Ext.Ajax.request({
               url: 'secure' + restURL + '/',
               but: but,
               method: method,
               maskEnable: true,
               maskEle: form,
               jsonData: jsonData,
               success: function(response, opts) {
                    var responseText = Ext.JSON.decode(response.responseText);
                    if (responseText.response.success) {
                         Ext.Msg.alert('Server Response', responseText.response.message);
                         me.refreshMainForm(but, responseText.response.data, opts.method);
                    } else if (!sessionTimeOutFlag) {
                         me.responseHandler(responseText);
                    }
               },
               failure: function(response, scope) {
                    me.addCommunicationLog(response, false);
                    if (!sessionTimeOutFlag) {
                         var responseText = Ext.JSON.decode(response.responseText);
                         me.responseHandler(responseText);
                    }
               }
          }, this);
     },
});