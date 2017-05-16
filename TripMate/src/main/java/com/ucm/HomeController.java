package com.ucm;

import java.util.Map;

import javax.faces.context.FacesContext;

public class HomeController {
	
	private String pageName;
	
	public String getPage(){
		return pageName;
	}

	/**
	 * @return the pageName
	 */
	public String getPageName() {
		return pageName;
	}

	/**
	 * @param pageName the pageName to set
	 */
	public void setPageName(String pageName) {
		Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		if(!sessionMap.containsKey("isUserLoggedIn")){
			sessionMap.put("isUserLoggedIn", "false");
		}
		this.pageName = pageName;
	}
	
}
