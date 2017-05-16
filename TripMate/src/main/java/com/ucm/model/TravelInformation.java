package com.ucm.model;

import java.util.List;

public class TravelInformation {
	
	private long id;
	private String emailId;
	private String travelFrom;
	private String travelDestination;
	private String dateOfTravel;
	private int weight;
	private int accompanyCount;
	private String serviceType;
	private String serviceProvEmail;
	private int peopleAccompanied;
	private transient List requestorIds;
	
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * @return the emailId
	 */
	public String getEmailId() {
		return emailId;
	}
	/**
	 * @param emailId the emailId to set
	 */
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	/**
	 * @return the travelFrom
	 */
	public String getTravelFrom() {
		return travelFrom;
	}
	/**
	 * @param travelFrom the travelFrom to set
	 */
	public void setTravelFrom(String travelFrom) {
		this.travelFrom = travelFrom;
	}
	/**
	 * @return the travelDestination
	 */
	public String getTravelDestination() {
		return travelDestination;
	}
	/**
	 * @param travelDestination the travelDestination to set
	 */
	public void setTravelDestination(String travelDestination) {
		this.travelDestination = travelDestination;
	}
	/**
	 * @return the dateOfTravel
	 */
	public String getDateOfTravel() {
		return dateOfTravel;
	}
	/**
	 * @param dateOfTravel the dateOfTravel to set
	 */
	public void setDateOfTravel(String dateOfTravel) {
		this.dateOfTravel = dateOfTravel;
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
	 * @return the serviceType
	 */
	public String getServiceType() {
		return serviceType;
	}
	/**
	 * @param serviceType the serviceType to set
	 */
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	/**
	 * @return the serviceProvEmail
	 */
	public String getServiceProvEmail() {
		return serviceProvEmail;
	}
	/**
	 * @param serviceProvEmail the serviceProvEmail to set
	 */
	public void setServiceProvEmail(String serviceProvEmail) {
		this.serviceProvEmail = serviceProvEmail;
	}
	/**
	 * @return the accompanyCount
	 */
	public int getAccompanyCount() {
		return accompanyCount;
	}
	/**
	 * @param accompanyCount the accompanyCount to set
	 */
	public void setAccompanyCount(int accompanyCount) {
		this.accompanyCount = accompanyCount;
	}
	/**
	 * @return the peopleAccompanied
	 */
	public int getPeopleAccompanied() {
		return peopleAccompanied;
	}
	/**
	 * @param peopleAccompanied the peopleAccompanied to set
	 */
	public void setPeopleAccompanied(int peopleAccompanied) {
		this.peopleAccompanied = peopleAccompanied;
	}
	public List getRequestorIds() {
		return requestorIds;
	}
	public void setRequestorIds(List requestorIds) {
		this.requestorIds = requestorIds;
	}

}
