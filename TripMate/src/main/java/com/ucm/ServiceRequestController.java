package com.ucm;

import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import com.ucm.bo.IAgentBo;
import com.ucm.model.TravelInformation;
import com.ucm.util.CustomUtil;

public class ServiceRequestController {
	
	private String from;
	private String destination;
	private int weight;
	private IAgentBo agentBo;
	private List travelSearchList;
	
	/**
	 * @return the weight
	 */
	public int getWeight() {
		return weight;
	}

	/**
	 * @param weight the weight to set
	 */
	public void setWeight(int weight) {
		this.weight = weight;
	}

	/**
	 * @return the from
	 */
	public String getFrom() {
		return from;
	}



	/**
	 * @param from the from to set
	 */
	public void setFrom(String from) {
		this.from = from;
	}



	/**
	 * @return the destination
	 */
	public String getDestination() {
		return destination;
	}



	/**
	 * @param destination the destination to set
	 */
	public void setDestination(String destination) {
		this.destination = destination;
	}
	
	/**
	 * @param agentBo the agentBo to set
	 */
	public void setAgentBo(IAgentBo agentBo) {
		this.agentBo = agentBo;
	}




	public String submitServiceRequest(){
		TravelInformation travelInfo = new TravelInformation();
		travelInfo.setTravelFrom(from);
		travelInfo.setTravelDestination(destination);
		travelInfo.setWeight(weight);
		travelInfo.setServiceType("SERVICE");
		Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		String  emailId = (String)sessionMap.getOrDefault("loggedInEmailId", "Admin@gmail.com");
		sessionMap.remove("serviceAdded");
		travelInfo.setEmailId(emailId);
		travelSearchList = agentBo.getTravelInfo(from, destination, emailId);
		this.from="";
		this.destination="";
		this.weight=0;
		if(travelSearchList.isEmpty()){
			FacesMessage facesMessage = new FacesMessage("No Agent found in this route");
			FacesContext.getCurrentInstance().addMessage(null, facesMessage);
		}
		return "RequestService";
	}
	
	/**
	 * @return the travelSearchList
	 */
	public List getTravelSearchList() {
		return travelSearchList;
	}

	/**
	 * @param travelSearchList the travelSearchList to set
	 */
	public void setTravelSearchList(List travelSearchList) {
		this.travelSearchList = travelSearchList;
	}

}
