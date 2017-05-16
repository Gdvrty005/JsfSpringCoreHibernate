package com.ucm.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import com.ucm.dao.IRegisterDao;
import com.ucm.model.Register;
import com.ucm.model.UserCredentials;
import com.ucm.model.UserDetails;

@Transactional(readOnly = false)
public class RegisterDaoImpl extends HibernateDaoSupport implements IRegisterDao{

	@Override
	public void save(Register register) {
		UserCredentials userCredentials = new UserCredentials();
		userCredentials.setEmailId(register.getEmailId());
		userCredentials.setPassword(register.getPassword());
		getHibernateTemplate().save(register);
		getHibernateTemplate().save(userCredentials);
	}
	@Override
	public void updatePassword(UserCredentials userCredentials){
		getHibernateTemplate().update(userCredentials);
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Map getUsers() {
		
		Map<String, List> map = new HashMap<String, List>();
		
		List usersList = (List)getHibernateTemplate().execute(new HibernateCallback(){

			@Override
			public List doInHibernate(Session session) throws HibernateException {
				Query sqlQuery = session.createSQLQuery("SELECT TUC.EMAIL_ID,TR.FIRST_NAME, TR.LAST_NAME FROM T_USER_CREDENTIALS TUC "
						+ "JOIN T_REGISTER TR ON TUC.EMAIL_ID = TR.EMAIL_ID "+"WHERE TUC.EMAIL_ID NOT IN ('teamtripmate@gmail.com')")
						.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List resultList = sqlQuery.list();
				List li = new ArrayList();
				for(Object object : resultList)
		         {
					UserDetails ud= new UserDetails();
		            Map row = (Map)object;
		            ud.setEmailId((String)row.get("EMAIL_ID"));
		            ud.setFirstName((String)row.get("FIRST_NAME"));
		            ud.setLastName((String)row.get("LAST_NAME"));
		            li.add(ud);
				}
				return li;
			}
			
		});
		map.put("users", usersList);
		
		List agentsList = (List)getHibernateTemplate().find("from TravelInformation where serviceType =?", "AGENT");
		
		List serviceList = (List)getHibernateTemplate().find("from TravelInformation where serviceType =?", "SERVICE");
		
		map.put("agents", agentsList);
		map.put("services", serviceList);
		return map;
//		return (List<UserCredentials>) getHibernateTemplate().find("from UserCredentials where emailId <> ?","teamtripmate@gmail.com");
	}

}
