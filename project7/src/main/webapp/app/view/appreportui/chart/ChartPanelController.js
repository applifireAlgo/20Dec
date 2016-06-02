Ext.define('Project7.view.appreportui.chart.ChartPanelController', {
	extend : 'Ext.app.ViewController',
	alias : 'controller.chartPanelController',
	
	chartPanelAfterRender:function(){
		var curView=this.getView();
		var chartJson=curView.chartJson;
		var chartPanel=new FusionCharts(chartJson);
		chartPanel.render(curView.body.id);
	},
	onChartFullScreenClick:function(chartFullScreenBtn){
		
		var chartView=this.getView();
		var chartJson=chartView.chartJson;
		var chartViewPanel = chartView.up("reportView");
		var upperSize = chartViewPanel.getSize();
               		
		var win = Ext.create('Ext.window.Window',{
               								height:upperSize.height,
               								width:upperSize.width,
               								constrain:true,
               								resizable :false,
               								closeAction:'destroy',
											animateTarget : chartView.el,
               								title:'',
               								draggable :false,
               								header:{
               									border:false,
               									style:{"background":"#ffffff","border-width":"0px !important"},
               									height:50
               								},
               								chartJson:chartJson,
               								chartViewPanel:chartViewPanel,
               								listeners:{
               									afterlayout:function(window){
               										var fusionchart=new FusionCharts(this.chartJson);
               										 fusionchart.width = '100%';
		 											 fusionchart.height ='100%';
		
													 fusionchart.render(window.body.id);
               									}
               								}
               							});
		chartViewPanel.add(win).show().setActive();

	}
});