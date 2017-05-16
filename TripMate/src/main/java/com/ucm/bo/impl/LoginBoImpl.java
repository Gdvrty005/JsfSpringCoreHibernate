package com.ucm.bo.impl;

import java.util.List;

import com.ucm.bo.ILoginBo;
import com.ucm.dao.ILoginDao;
import com.ucm.model.TravelInformation;
import com.ucm.model.UserCredentials;

public class LoginBoImpl implements ILoginBo {

	private ILoginDao loginDao;

	@Override
	public UserCredentials validateUser(String email) {
		return loginDao.validateUser(email);
	}

	/**
	 * @return the loginDao
	 */
	public ILoginDao getLoginDao() {
		return loginDao;
	}

	/**
	 * @param loginDao
	 *            the loginDao to set
	 */
	public void setLoginDao(ILoginDao loginDao) {
		this.loginDao = loginDao;
	}

	@Override
	public List<TravelInformation> getUserTransactions(String emailId) {
		return loginDao.getUserTransactions(emailId);
	}

	@Override
	public List getUserActiveTransactions(String emailId) {
		return loginDao.getUserActiveTransactions(emailId);
	}


}
