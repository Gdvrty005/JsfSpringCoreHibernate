package com.ucm.dao;

import java.util.Map;

import com.ucm.model.Register;
import com.ucm.model.UserCredentials;

public interface IRegisterDao {

	void save(Register register);
	public void updatePassword(UserCredentials userCredentials);
	Map getUsers();
}
