package com.ucm;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import com.ucm.bo.IAgentBo;
import com.ucm.bo.ISendMail;
import com.ucm.model.TravelInformation;
import com.ucm.util.CustomUtil;

public class AgentController {
	
	private String from;
	private String destination;
	private Date doj;
	private int weight;
	private IAgentBo agentBo;
	private Date todayDate = new Date();
	private int accompanyCount;
	private ISendMail mailBean;

	public Date getTodayDate() {
	    return todayDate;
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
	 * @return the doj
	 */
	public Date getDoj() {
		return doj;
	}

	/**
	 * @param doj the doj to set
	 */
	public void setDoj(Date doj) {
		this.doj = doj;
	}

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
	 * @param agentBo the agentBo to set
	 */
	public void setAgentBo(IAgentBo agentBo) {
		this.agentBo = agentBo;
	}

	public void submitAgentRequest() throws ParseException{
		TravelInformation travelInfo = new TravelInformation();
		travelInfo.setTravelFrom(from);
		travelInfo.setTravelDestination(destination);
		travelInfo.setWeight(weight);
		travelInfo.setDateOfTravel(CustomUtil.convertDateToString(doj));
		travelInfo.setServiceType("AGENT");
		travelInfo.setAccompanyCount(accompanyCount);
		Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		String  emailId = (String)sessionMap.getOrDefault("loggedInEmailId", "Avinash@gmail.com");
		travelInfo.setEmailId(emailId);
		String status = agentBo.saveTravelInfo(travelInfo);
		if(status=="updated"){
			List<String> emailIdList = agentBo.retrieveMailIds(emailId);
			for (String email : emailIdList) {
				mailBean.sendMail(email, "Your trip is cancelled as service provider modified few details.");
			}
		}
		this.from="";
		this.destination="";
		this.weight=0;
		this.doj=null;
		this.accompanyCount=0;
		FacesMessage facesMessage = new FacesMessage("Data "+status+" successfully");
		FacesContext.getCurrentInstance().addMessage(null, facesMessage);
	}


	public int getAccompanyCount() {
		return accompanyCount;
	}


	public void setAccompanyCount(int accompanyCount) {
		this.accompanyCount = accompanyCount;
	}


	public ISendMail getMailBean() {
		return mailBean;
	}


	public void setMailBean(ISendMail mailBean) {
		this.mailBean = mailBean;
	}

}
