package com.ucm.bo.impl;

import java.util.Map;

import com.ucm.bo.IRegisterBo;
import com.ucm.dao.IRegisterDao;
import com.ucm.model.Register;
import com.ucm.model.UserCredentials;

public class RegisterBoImpl implements IRegisterBo {

	IRegisterDao registerDao;

	@Override
	public void saveUser(Register register) {
		registerDao.save(register);
	}
	
	/**
	 * @param registerDao
	 *            the registerDao to set
	 */
	public void setRegisterDao(IRegisterDao registerDao) {
		this.registerDao = registerDao;
	}



	@Override
	public void updatePassword(UserCredentials userCredentials) {
		registerDao.updatePassword(userCredentials);
	}

	@Override
	public Map getUsers() {
		return registerDao.getUsers();
	}

}
