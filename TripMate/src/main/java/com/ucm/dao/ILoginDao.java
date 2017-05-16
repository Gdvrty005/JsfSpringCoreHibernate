package com.ucm.dao;

import java.util.List;

import com.ucm.model.TravelInformation;
import com.ucm.model.UserCredentials;

public interface ILoginDao {
	public UserCredentials validateUser(String email);

	public List<TravelInformation> getUserTransactions(String emailId);
	
	public List getUserActiveTransactions(String emailId);
}
