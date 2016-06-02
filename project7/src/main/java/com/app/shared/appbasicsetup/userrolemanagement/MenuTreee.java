package com.app.shared.appbasicsetup.userrolemanagement;import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import atg.taglib.json.util.JSONArray;
import atg.taglib.json.util.JSONException;
import atg.taglib.json.util.JSONObject;

public class MenuTreee {
	private List menus = new ArrayList<>();
	private MenuNodes node = null;

	public MenuTreee(List list) {
		this.menus = list;
	}

	private void createMenuPanel() {
		HashMap map = new HashMap();
		for (Iterator iterator = menus.iterator(); iterator.hasNext();) {
			Menu menu = (Menu) iterator.next();
			if (node == null) {
				node = new MenuNodes(menu);
				map.put(menu.getMenuTreeId(), node);
			} else {
				String split[] = menu.getMenuTreeId().split("\\.");
				String check = "";
				for (int i = 0; i < split.length - 1; i++) {
					check = check + split[i] + ".";
				}
				if (map.get(check) != null) {
					MenuNodes root = (MenuNodes) map.get(check);
					MenuNodes inner = new MenuNodes(menu);
					root.addMenu(inner);
					map.put(menu.getMenuTreeId(), inner);
				}
			}
		}
	}

	public JSONObject createMenuTree() throws JSONException{
		createMenuPanel();
		JSONObject json=new JSONObject();
		if(node!=null)
		{
			json =  node.getMenu().toJSON();
			JSONArray array = createJson(new JSONArray(), node);
			
			if(array!=null && !array.isEmpty())
			{
				json.put("children", array);
				json.put("icon","images/folder-database-icon.png");
				json.put("cls", "folderTitle");
				json.put("leaf", false);
			}
			else 
				json = new JSONObject();
		}
		
		return json;
	}
 
	
	private JSONArray createJson(JSONArray json, MenuNodes node) throws JSONException{
		if(node.getMenu()!=null){
			if(node.getList().size()>0){
				for (Iterator iterator = node.getList().iterator(); iterator.hasNext();) {
					MenuNodes type = (MenuNodes) iterator.next();
					
					if(type.getMenu()!=null){
						JSONObject inner = type.getMenu().toJSON();
						
						if(type.getList().size()>0){
							JSONArray innerArray = createChilds(new JSONArray(), type);
							inner.put("children", innerArray);
							inner.put("leaf", false);
						}else{
							inner.put("leaf", true);
						}
						json.add(inner);
					}
				}
			}
		}
		return json;
	}
	
	
	private JSONArray createChilds(JSONArray json, MenuNodes node) throws JSONException{
		JSONObject inner = null;
		if(node.getList().size()>0){
			for (Iterator iterator = node.getList().iterator(); iterator.hasNext();) {
				MenuNodes type = (MenuNodes) iterator.next();
				if(type.getMenu()!=null){
					inner = type.getMenu().toJSON();
					if(type.getList().size()>0){
						JSONArray innerArray = createJson(new JSONArray(), type);
						inner.put("children", innerArray);
						inner.put("leaf", false);
					}else{
						inner.put("leaf", true);
					}
					
				json.add(inner);
				}
			}
		}
		return json;
	}
	
	
}
