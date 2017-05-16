package com.ucm.dao;

import java.util.List;

import com.ucm.model.TravelInformation;

public interface IAgentDao {

	public String saveTravelInfo(TravelInformation travelInfo);

	public List<TravelInformation> getTravelInfo(String from, String destination, String emailId);

	public void cancelService(TravelInformation travelInfo);

	void addService(TravelInformation travelInfo);

	List<String> retrieveMailIds(String emailId);

	void cancelAgent(TravelInformation travelInfo);
}
