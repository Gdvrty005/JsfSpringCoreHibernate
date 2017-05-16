package com.ucm.bo;

import java.util.Map;

import com.ucm.model.Register;
import com.ucm.model.UserCredentials;

public interface IRegisterBo {

	public void saveUser(Register register) ;
	public void updatePassword(UserCredentials userCredentials);
	public Map getUsers();
}
