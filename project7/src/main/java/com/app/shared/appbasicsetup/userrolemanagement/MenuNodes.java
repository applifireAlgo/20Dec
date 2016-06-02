package com.app.shared.appbasicsetup.userrolemanagement;import java.util.ArrayList;
import java.util.List;

public class MenuNodes
{
	private Menu menu;
	
	private List<MenuNodes> nodes = new ArrayList<MenuNodes>();
	
	public MenuNodes(Menu _menu){
		this.menu = _menu;
	}
	
	public void addMenu(MenuNodes menu){
		nodes.add(menu);
	}
	

	public Menu getMenu(){
		return menu;
	}
	
	public List getList(){
		return nodes;
	}
	@Override
	public String toString() {
		return menu.toString();
	}

}
