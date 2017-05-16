package com.ucm.dao.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import com.ucm.dao.IAgentDao;
import com.ucm.model.TravelInformation;
import com.ucm.util.CustomUtil;


@Transactional(readOnly = false)
public class AgentDaoImpl extends HibernateDaoSupport implements IAgentDao {

	@Override
	public String saveTravelInfo(TravelInformation travelInfo) {
		String status="";
		@SuppressWarnings("unused")
		List<TravelInformation> ti = (List<TravelInformation>)getHibernateTemplate().find("from TravelInformation where "
				+ "travelFrom=? and travelDestination=? and emailId = ? and serviceType=?", travelInfo.getTravelFrom(),travelInfo.getTravelDestination(),travelInfo.getEmailId(),"AGENT");
		if(!ti.isEmpty()){
			ti.get(0).setAccompanyCount(travelInfo.getAccompanyCount());
			ti.get(0).setDateOfTravel(travelInfo.getDateOfTravel());
			ti.get(0).setEmailId(travelInfo.getEmailId());
			ti.get(0).setTravelDestination(travelInfo.getTravelDestination());
			ti.get(0).setTravelFrom(travelInfo.getTravelFrom());
			ti.get(0).setWeight(travelInfo.getWeight());
			ti.get(0).setPeopleAccompanied(0);
			getHibernateTemplate().update(ti.get(0));
			
			status="updated";
		}else{
		getHibernateTemplate().save(travelInfo);
		status="saved";
		}
		return status;
	}
	
	@SuppressWarnings("unchecked")
	public List<TravelInformation> getTravelInfo(String from,String destination, String emailId){
		Date d = new Date();
		String convertDateToString ="";
		try {
			convertDateToString = CustomUtil.convertDateToString(d);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		List<TravelInformation> travelSearch = (List<TravelInformation>)getHibernateTemplate().find("from TravelInformation where "
				+ "travelFrom=? and travelDestination=? and emailId <> ? and dateOfTravel >? and serviceType=? and peopleAccompanied<accompanyCount", from,destination,emailId,convertDateToString,"AGENT");
		return travelSearch;
	}
	
	public void cancelService(TravelInformation travelInfo){
		@SuppressWarnings("unused")
		List<TravelInformation> ti = (List<TravelInformation>)getHibernateTemplate().find("from TravelInformation where "
				+ "travelFrom=? and travelDestination=? and emailId = ? and dateOfTravel =? and serviceType=?", travelInfo.getTravelFrom(),travelInfo.getTravelDestination(),travelInfo.getServiceProvEmail(),travelInfo.getDateOfTravel(),"AGENT");
		if(!ti.isEmpty()){
		int peopleAccompanied = ti.get(0).getPeopleAccompanied();
		ti.get(0).setPeopleAccompanied(peopleAccompanied-1);
		getHibernateTemplate().update(ti.get(0));
		}
		List travelInfoObject = getHibernateTemplate().find("from TravelInformation where "
				+ "travelFrom=? and travelDestination=? and emailId = ? and dateOfTravel =? and serviceType=? and serviceProvEmail=? and weight=? and emailId=?", travelInfo.getTravelFrom(),
				travelInfo.getTravelDestination(),travelInfo.getEmailId(),travelInfo.getDateOfTravel(),"SERVICE",
				travelInfo.getServiceProvEmail(),travelInfo.getWeight(),travelInfo.getEmailId());
		getHibernateTemplate().delete(travelInfoObject.get(0));
	}
	
	@Override
	public void cancelAgent(TravelInformation travelInfo){
		List travelInfoObject = getHibernateTemplate().find("from TravelInformation where "
				+ "travelFrom=? and travelDestination=? and emailId = ? and dateOfTravel =? and serviceType=? and weight=?", travelInfo.getTravelFrom(),
				travelInfo.getTravelDestination(),travelInfo.getEmailId(),travelInfo.getDateOfTravel(),"AGENT",travelInfo.getWeight());
		getHibernateTemplate().delete(travelInfoObject.get(0));
	}
	
	@Override
	public void addService(TravelInformation travelInfo) {
		@SuppressWarnings("unused")
		List<TravelInformation> ti = (List<TravelInformation>)getHibernateTemplate().find("from TravelInformation where "
				+ "travelFrom=? and travelDestination=? and emailId = ? and dateOfTravel =? and serviceType=?", travelInfo.getTravelFrom(),travelInfo.getTravelDestination(),travelInfo.getServiceProvEmail(),travelInfo.getDateOfTravel(),"AGENT");
		if(!ti.isEmpty()){
			int peopleAccompanied = ti.get(0).getPeopleAccompanied();
			ti.get(0).setPeopleAccompanied(peopleAccompanied+1);
			getHibernateTemplate().update(ti.get(0));
		}
		getHibernateTemplate().saveOrUpdate(travelInfo);

	}

	@Override
	public List<String> retrieveMailIds(String emailId) {
		List<TravelInformation> travelInfoList = (List<TravelInformation>)getHibernateTemplate().find("from TravelInformation where "
				+"serviceType=? and serviceProvEmail=?","SERVICE",emailId);
		List<String> emailIdList = new ArrayList<String>();
		for (TravelInformation travelInformation : travelInfoList) {
			emailIdList.add(travelInformation.getEmailId());
		}
		getHibernateTemplate().deleteAll(travelInfoList);
		return emailIdList;
	}

}
