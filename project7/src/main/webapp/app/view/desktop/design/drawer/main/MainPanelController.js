Ext.define('Project7.view.desktop.design.drawer.main.MainPanelController', {
    extend: 'Ext.app.ViewController',
 
    alias: 'controller.mainPanel',
    /**Search**/
    recognizing : false,
    recognition:null,
    searchCommand : [],

    onMainPanelAfterRender : function(mainPanel){
        
    	this.loadLangCombo();
        
        this.configureTabContextMenu();
        
        this.configureSearchEngine();
        
        this.initializeGoogleMap();
        
        mainPanel.getEl().on('contextmenu', function(view) {
                       view.stopEvent();
        });
        
    },
    initializeGoogleMap : function(){
        var googleScript = document.createElement('script');
        googleScript.setAttribute("type","text/javascript");
        googleScript.setAttribute("src", "https://maps.googleapis.com/maps/api/js?v=3.18");
        document.body.appendChild(googleScript);
    },
    displayUserDetail:function()
    {
        //Get user cookie value set from angular js loginController
         var userNameDField =  Ext.getCmp('userNameDField');
        
        var cookieUserInfo = Ext.util.Cookies.get('userInfo');
        if(cookieUserInfo==null){
            var pathName=location.pathname;
            var initialPath=pathName.slice(0,pathName.lastIndexOf("/"));
            location.href=location.origin+initialPath+"/";
        }
        
        userNameDField.setValue(decodeURI(cookieUserInfo));
         
    
    },
    configureTabContextMenu : function(){
    	
        var appMainTabPanel = Ext.getCmp('appMainTabPanel');
        var scope = this;
        var contextMenu = new Ext.menu.Menu({
              targetTab :"",
              items: [{
                text: 'Close Other',
                scope:scope,
                listeners:{
                    click: function(tab){
                        
                        tab.scope.onCloseTabOptionClick(1);
                    }
                }
              },{
                text: 'Close All',
                scope:scope,
                listeners:{
                    click: function(tab){
                        
                        tab.scope.onCloseTabOptionClick(2);
                    }
                }
              }]
            });

        appMainTabPanel.tabBar.getEl().on('contextmenu', function(e, t, eOpts) {
            
                 e.stopEvent();
                 var appMainTabPanel =  Ext.getCmp('appMainTabPanel');
                 if(appMainTabPanel.items.items.length>0){
                  contextMenu.showAt(e.getXY());
                 }else{
                    return false;
                 }
            });
    },
     onCloseTabOptionClick : function(type){
    	 
         var appMainTabPanel = Ext.getCmp('appMainTabPanel');
         var activeItem = appMainTabPanel.getActiveTab();
         var centerTabs = appMainTabPanel.items.items;
         if(type == 1){
             
               
               for(var i = centerTabs.length -1; i >= 0 ; i--){
               
                  
                   if(centerTabs[i] !== activeItem){
           
                        centerTabs[i].close();
                   }
               }

         }else{
           while(centerTabs.length>0){
             var tab = centerTabs[0];
             tab.close();
          }

         }
         },
    onDrawerBtnClick : function(drawerBtn){
        var westPanel = Ext.getCmp('westPanel');
        westPanel.setHidden(!westPanel.isHidden());
    },
    
    onMyProfileClick : function()
    {
        
         this.pushViewInMainTab( "Project7.view.usermanagement.enduser.UserProfile","My Profile","menuAction","Project7.view.usermanagement.enduser.UserProfile");
    },
    
    onChangePwdClick:function()
    {
      
         this.pushViewInMainTab( "Project7.view.usermanagement.enduser.ChangePwd","Change Password","menuAction","Project7.view.usermanagement.enduser.ChangePwd");
    },
     onCloudClick : function()
    {
        
        this.pushViewInMainTab("Project7.view.clouddrive.CloudDrive","Cloud Drive","menuAction","Project7.view.clouddrive.CloudDrive");
    },
    
    pushViewInMainTab : function(className,title,cmpKey,cmpValue){
        var appMainTabPanel = Ext.getCmp('appMainTabPanel');
        if(className){
        var ident= {};
          ident[cmpKey]=cmpValue;

         var appMainTabPanel =  Ext.getCmp('appMainTabPanel');
         var clickedAction = appMainTabPanel.down("["+cmpKey+"='"+cmpValue+"']");
         
         if(clickedAction){
            appMainTabPanel.setActiveItem(clickedAction);
            return clickedAction;
         }else{
        
         var addedForm = appMainTabPanel.add(Ext.create(className,{
             closable:true,
             title:title,
             menuAction:className,
             cmpKey:cmpValue
            
         }));
         Ext.merge(addedForm,ident);
         appMainTabPanel.setActiveItem(addedForm);
         return addedForm;
         }
     }
    },

    onLogoutClick : function(logoutBtn)
    {
        Ext.Ajax.request({
            url : "secure/Logout",
            method : 'POST',
            jsonData : {},
            success : function(response, scope) {
                
                var jsonRespone = Ext.JSON.decode(response.responseText);
                if (jsonRespone.response.success == "true") {
                    //this.location.reload();
                    var pathName=this.location.pathname;
                    var initialPath=pathName.slice(0,pathName.lastIndexOf("/"));
                    
                    Ext.util.Cookies.clear('changePwd',initialPath);
                    
                    this.location.href=this.location.origin+initialPath+"/";
                } else {
                    Ext.Msg.alert('Logout failed',jsonRespone.response.message);
                }
            },
            failure : function() {
                Ext.Msg.alert('Error', 'Cannot connect to server');
            }
        });
    },
    loadLangCombo : function(){

    
        Ext.Ajax.request({
            url : 'secure/SearchEngineService/getAvailableLanguages',
            method : 'GET',
            view : this.view,
            success : function(response, scope) {
                var responseData = Ext.decode(response.responseText);
                var data = Ext.decode(responseData.response.data.message);
                var combo = scope.view.down('#Lang');
                var store  = {
                    fields: [ {
                        name : 'name',
                        type : 'string'
                    }, {
                        name : 'value ',
                        type : 'string',
                    }],
                    data : data                 
                }
                combo.setStore(store);  
                combo.select(combo.getStore().getAt(0));
            },
            failure : function() {
                //Ext.Msg.alert('Error', 'Cannot connect to server');
            }
        });
     },
     onSearchClick : function(searchBtn){

        var searchView = this.pushViewInMainTab("Project7.view.searchengine.search.SearchEngineMainPanel","Search Result","isSearchCmp","true");

        if(searchView){
        var mainview = this.getView();
        
        mainview.down("#voice").setIcon(
                'resources/appicons/ic_mic_grey.png');

        var searchTxt = Ext.getCmp('searchField');
        
        var langCmb = mainview.down('#Lang'); 
        var langValue = langCmb.getValue();
        var operationType = (langValue)?langValue:"en";
        searchView.controller.doSearch(searchView,searchTxt.getValue(),operationType);

        }
    },
    
    onVoiceSearchClick : function(searchBtn){
      var currentScope = this;
        recognizing=this.recognizing;
        recognition=this.recognition;
        if (recognizing) {
            recognition.stop();
            return;
        }
        
        recognition.start();
        ignore_onend = false;

        this.getView().down("#voice").setIcon(
                'resources/appicons/ic_mic_slash_grey.png');

       // currentScope.showButtons('none');
        start_timestamp = event.timeStamp;
        
        function showInfo(s) {
            if (s) {
                for (var child = info.firstChild; child; child = child.nextSibling) {
                    if (child.style) {
                        child = child.id == s ? 'inline'
                                : 'none';
                    }
                }
                info = 'visible';
            } else {
                info = 'hidden';
            }
        }

         //current_style;
    },
    configureSearchEngine : function(){
        //Search Config
        var _currentScope=this;
        this.searchCommand.push("search");
        this.searchCommand.push("find");
        this.searchCommand.push("go");
        searchCommand=this.searchCommand;
        currentTab = this.getView();
        // txtField=this.getView().down("#searchs");
        var final_span = "";
        var interim_span = "";
        var final_transcript = '';

        var ignore_onend;
        var start_timestamp;
        if (!('webkitSpeechRecognition' in window)) {
            this.getView().down('#voice').setHidden(true);
            //upgrade();
        } else {
            start_button = 'inline-block';
            var recognition = new webkitSpeechRecognition();
            this.recognition=recognition;
            recognition.continuous = true;
            recognition.interimResults = true;

            recognition.onstart = function() {
                // currentTab = Ext.MainAppMagr.getActiveTab();
                recognizing = true;
                _currentScope.getView().down("#voice").setIcon(
                        'images/mic-animate.gif');

            };

            recognition.onerror = function(event) {
                // currentTab = Ext.MainAppMagr.getActiveTab();
                if (event.error == 'no-speech') {

                    _currentScope.getView().down("#voice").setIcon(
                            'resources/appicons/ic_mic_grey.png');
                    alert("No speech was detected. You may need to adjust your microphone");
                    ignore_onend = true;
                }
                if (event.error == 'audio-capture') {

                    currentTab.down("#voice").setIcon(
                            'resources/appicons/ic_mic_grey.png');
                    alert("No microphone was found. Ensure that a microphone is installed");
                    ignore_onend = true;
                }
                if (event.error == 'not-allowed') {
                    currentTab.down("#voice").setIcon(
                            'resources/appicons/ic_mic_slash_grey.png');
                    if (event.timeStamp - start_timestamp < 100) {
                        alert("Permission to use microphone is blocked.");
                    } else {
                        alert("Permission to use microphone was denied.");
                    }
                    ignore_onend = true;
                }
            };

            recognition.onend = function() {
                // currentTab = Ext.MainAppMagr.getActiveTab();
                recognizing = false;
                if (ignore_onend) {

                    return;
                }
                
                _currentScope.getView().down("#voice").setIcon(
                        'resources/appicons/ic_mic_grey.png');

                if (!final_transcript) {

                    return;
                }
                //showInfo('');
                if (window.getSelection) {
                    window.getSelection().removeAllRanges();
                    var range = document.createRange();
                    range.selectNode(document
                            .getElementById('final_span'));
                    window.getSelection().addRange(range);
                }

            };

            recognition.onresult = function(event) {
                // currentTab = Ext.MainAppMagr.getActiveTab();
                var interim_transcript = '';
                for (var i = event.resultIndex; i < event.results.length; ++i) {
                    if (event.results[i].isFinal) {
                        final_transcript = event.results[i][0].transcript;
                    } else {
                        interim_transcript += event.results[i][0].transcript;
                    }
                }
                var isSearchCommand = false;
                var inputWords = final_transcript.split(' ');

                for (var k = 0; k < inputWords.length; k++) {

                    if (searchCommand.indexOf(inputWords[k]) != -1) {

                        isSearchCommand = true;
                        final_transcript = final_transcript
                                .replace(inputWords[k], '');
                    }
                }
                _currentScope.getView().down('#searchField').setValue(
                        final_transcript);
                if (isSearchCommand) {
                    _currentScope.onSearchClick(currentTab.down('#searchField'));
                }
                if (recognizing) {

                    recognition.stop();
                    return;
                }

                if (final_transcript || interim_transcript) {
                    _currentScope.showButtons('inline-block');
                }
            };
        }
    }
   
});
