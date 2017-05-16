package com.ucm.bo.impl;

import java.util.List;

import com.ucm.bo.IAgentBo;
import com.ucm.dao.IAgentDao;
import com.ucm.model.TravelInformation;

public class AgentBoImpl implements IAgentBo {
	
	private IAgentDao agentDao;

	@Override
	public String saveTravelInfo(TravelInformation travelInfo) {
		return agentDao.saveTravelInfo(travelInfo);
	}
	
	public List<TravelInformation> getTravelInfo(String from,String destination, String emailId){
		return agentDao.getTravelInfo(from, destination,emailId);
	}
	
	public void cancelService(TravelInformation travelInfo){
		agentDao.cancelService(travelInfo);
	}
	
	@Override
	public void addService(TravelInformation travelInfo){
		agentDao.addService(travelInfo);
	}

	/**
	 * @return the agentDao
	 */
	public IAgentDao getAgentDao() {
		return agentDao;
	}

	/**
	 * @param agentDao the agentDao to set
	 */
	public void setAgentDao(IAgentDao agentDao) {
		this.agentDao = agentDao;
	}

	@Override
	public List<String> retrieveMailIds(String emailId) {
		return agentDao.retrieveMailIds(emailId);
	}

	@Override
	public void cancelAgent(TravelInformation travelInfo) {
		agentDao.cancelAgent(travelInfo);
	}

}
