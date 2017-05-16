package com.ucm.dao.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.ucm.dao.ILoginDao;
import com.ucm.model.TravelInformation;
import com.ucm.model.UserCredentials;
import com.ucm.model.UserDetails;
import com.ucm.util.CustomUtil;



public class LoginDaoImpl extends HibernateDaoSupport implements ILoginDao {


	@SuppressWarnings("unchecked")
	@Override
	public UserCredentials validateUser(String email) {
		List<UserCredentials> loggedInUser = (List<UserCredentials>) getHibernateTemplate().find("FROM UserCredentials WHERE emailId=?", email);
		System.out.println(loggedInUser);
		return loggedInUser.isEmpty()?new UserCredentials():loggedInUser.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TravelInformation> getUserTransactions(final String emailId) {
		List<TravelInformation> transactionsList = (List<TravelInformation>)getHibernateTemplate().find("FROM TravelInformation WHERE emailId=?", emailId);
		
		List<UserDetails> requestorEmailList = (List)getHibernateTemplate().execute(new HibernateCallback(){
			
			@Override
			public List doInHibernate(Session session) throws HibernateException {
				Query sqlQuery = session.createSQLQuery("SELECT EMAIL_ID,TRAVEL_FROM,TRAVEL_DESTINATION,DATE_OF_TRAVEL FROM t_travel_information WHERE SERVICE_PROVIDER_EMAIL='"+emailId+"'")
						.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List resultList = sqlQuery.list();
				List li = new ArrayList();
				
				@SuppressWarnings("rawtypes")
				Set s = new HashSet();
				for(Object object : resultList)
		         {
		            Map row = (Map)object;
		            UserDetails ud = new UserDetails();
		            ud.setEmailId((String)row.get("EMAIL_ID"));
		            ud.setFrom((String)row.get("TRAVEL_FROM"));
		            ud.setDestination((String)row.get("TRAVEL_DESTINATION"));
		            ud.setDoj((String)row.get("DATE_OF_TRAVEL"));
		            li.add(ud);
				}
				return li;
			}
		});
		for (TravelInformation tInfo : transactionsList) {
			List li = new ArrayList();
			for (UserDetails userDetails : requestorEmailList) {
				if(tInfo.getTravelFrom().equals(userDetails.getFrom()) && tInfo.getTravelDestination().equals(userDetails.getDestination())
						&& tInfo.getDateOfTravel().equals(userDetails.getDoj())){
					li.add(userDetails.getEmailId());
				}
			}
			tInfo.setRequestorIds(li);
		}
		return transactionsList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getUserActiveTransactions(final String emailId) {
		Date d = new Date();
		String convertDateToString ="";
		try {
			convertDateToString = CustomUtil.convertDateToString(d);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		List<TravelInformation> transactionsList = (List<TravelInformation>)getHibernateTemplate().find("FROM TravelInformation WHERE emailId=? and dateOfTravel > ?", emailId, convertDateToString);
		
List<UserDetails> requestorEmailList = (List)getHibernateTemplate().execute(new HibernateCallback(){
			
			@Override
			public List doInHibernate(Session session) throws HibernateException {
				Query sqlQuery = session.createSQLQuery("SELECT EMAIL_ID,TRAVEL_FROM,TRAVEL_DESTINATION,DATE_OF_TRAVEL FROM t_travel_information WHERE SERVICE_PROVIDER_EMAIL='"+emailId+"'")
						.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List resultList = sqlQuery.list();
				List li = new ArrayList();
				
				@SuppressWarnings("rawtypes")
				Set s = new HashSet();
				for(Object object : resultList)
		         {
		            Map row = (Map)object;
		            UserDetails ud = new UserDetails();
		            ud.setEmailId((String)row.get("EMAIL_ID"));
		            ud.setFrom((String)row.get("TRAVEL_FROM"));
		            ud.setDestination((String)row.get("TRAVEL_DESTINATION"));
		            ud.setDoj((String)row.get("DATE_OF_TRAVEL"));
		            li.add(ud);
				}
				return li;
			}
		});
		for (TravelInformation tInfo : transactionsList) {
			List li = new ArrayList();
			for (UserDetails userDetails : requestorEmailList) {
				if(tInfo.getTravelFrom().equals(userDetails.getFrom()) && tInfo.getTravelDestination().equals(userDetails.getDestination())
						&& tInfo.getDateOfTravel().equals(userDetails.getDoj())){
					li.add(userDetails.getEmailId());
				}
			}
			tInfo.setRequestorIds(li);
		}
		return transactionsList;
	}
}
