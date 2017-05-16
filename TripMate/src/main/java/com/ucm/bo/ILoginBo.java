package com.ucm.bo;

import java.util.List;

import com.ucm.model.TravelInformation;
import com.ucm.model.UserCredentials;

public interface ILoginBo {
	public UserCredentials validateUser(String email);

	public List<TravelInformation> getUserTransactions(String emailId);
	

	public List getUserActiveTransactions(String emailId);
}
