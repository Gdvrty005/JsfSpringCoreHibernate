package com.ucm;

import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import com.ucm.bo.IAgentBo;
import com.ucm.bo.ILoginBo;
import com.ucm.bo.IRegisterBo;
import com.ucm.bo.ISendMail;
import com.ucm.model.TravelInformation;
import com.ucm.model.UserCredentials;
import com.ucm.util.CryptUtil;
import com.ucm.util.CustomUtil;

public class LoginController {

	private String emailId;

	private String confirmEmailId;

	private String password;
	
	private String confirmPassword;

	private List transactionsList;
	
	private List usersList;

	private ILoginBo loginBo;

	private ISendMail mailBean;
	
	private IAgentBo agentBo;
	
	private IRegisterBo registerBo;

	private List agentsList;

	private List servicesList;

	private List activeTransactionsList;
	

	/**
	 * @param agentBo the agentBo to set
	 */
	public void setAgentBo(IAgentBo agentBo) {
		this.agentBo = agentBo;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the emailId
	 */
	public String getEmailId() {
		return emailId;
	}

	/**
	 * @param emailId
	 *            the emailId to set
	 */
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	
	/**
	 * @return the activeTransactionsList
	 */
	public List getActiveTransactionsList() {
		return activeTransactionsList;
	}

	/**
	 * @param activeTransactionsList
	 *            the activeTransactionsList to set
	 */
	public void setActiveTransactionsList(List activeTransactionsList) {
		this.activeTransactionsList = activeTransactionsList;
	}

	/**
	 * @return the loginBo
	 */
	public ILoginBo getLoginBo() {
		return loginBo;
	}

	/**
	 * @param loginBo
	 *            the loginBo to set
	 */
	public void setLoginBo(ILoginBo loginBo) {
		this.loginBo = loginBo;
	}

	/**
	 * @param mailBean
	 *            the mailBean to set
	 */
	public void setMailBean(ISendMail mailBean) {
		this.mailBean = mailBean;
	}

	/**
	 * @return the transactionsList
	 */
	public List getTransactionsList() {
		return transactionsList;
	}

	/**
	 * @param transactionsList
	 *            the transactionsList to set
	 */
	public void setTransactionsList(List transactionsList) {
		this.transactionsList = transactionsList;
	}

	public String validateUser() {
		String view = "Login";
		UserCredentials loggedInUser = loginBo.validateUser(emailId);
		
		try {
			if (loggedInUser.getPassword() != null) {
				if (password.equals(
						CryptUtil.decrypt(loggedInUser.getPassword() == null ? "" : loggedInUser.getPassword()))) {
					Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext()
							.getSessionMap();
					sessionMap.put("isUserLoggedIn", "true");
					sessionMap.put("loggedInEmailId", loggedInUser.getEmailId());
					if("teamtripmate@gmail.com".equalsIgnoreCase(loggedInUser.getEmailId())){
						view="Admin";
						Map adminMap = registerBo.getUsers();
						usersList=(List)adminMap.get("users");
						setAgentsList((List)adminMap.get("agents"));
						setServicesList((List)adminMap.get("services"));
					}else{
						view = "UserHome";
					}
				}else {
					FacesMessage facesMessage = new FacesMessage("Please provide valid credentials");
					FacesContext.getCurrentInstance().addMessage(null, facesMessage);
				}
			} else {
				FacesMessage facesMessage = new FacesMessage("Please provide valid credentials");
				FacesContext.getCurrentInstance().addMessage(null, facesMessage);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return view;
	}

	public String logout() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "Main?faces-redirect=true";
	}

	public String recoverPassword() {
		try {
			FacesMessage facesMessage = new FacesMessage();
			if (emailId.equals(confirmEmailId)) {
				UserCredentials loggedInUser = loginBo.validateUser(emailId);
				if (null != loggedInUser.getEmailId()) {
					String pwd = CryptUtil.decrypt(loggedInUser.getPassword());
					mailBean.sendMail(emailId,
							"Please use '" + pwd + "' as password to login to your TripMate Account.");
					facesMessage.setSummary("Password sent to registered MailId.");
				}
			}else {
				facesMessage.setSummary("Please register before requesting for password.");
			}
			FacesContext.getCurrentInstance().addMessage(null, facesMessage);
			this.emailId = "";
			this.confirmEmailId="";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "PWDRecovery";
	}

	public String userTrasactions() {
		Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		String emailId = (String) sessionMap.getOrDefault("loggedInEmailId", "Admin@gmail.com");
		this.setTransactionsList(loginBo.getUserTransactions(emailId));
		this.setActiveTransactionsList(loginBo.getUserActiveTransactions(emailId));
		return "UserProfile";
	}
	
	public void updatePassword(){
		try{
			Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
			String  emailId = (String)sessionMap.getOrDefault("loggedInEmailId", "Admin@gmail.com");
			UserCredentials loggedInUser = loginBo.validateUser(emailId);
			if (loggedInUser.getPassword() != null) {
				if (password.equals(CryptUtil.decrypt(loggedInUser.getPassword() == null ? "" : loggedInUser.getPassword()))) {
					loggedInUser.setPassword(CryptUtil.encrypt(confirmPassword));
					registerBo.updatePassword(loggedInUser);
					FacesMessage facesMessage = new FacesMessage("Password updated successfully");
					FacesContext.getCurrentInstance().addMessage(null, facesMessage);
					mailBean.sendMail(emailId, "Your password has changed successfully.");
				}
			} else {
				FacesMessage facesMessage = new FacesMessage("Please provide valid credentials");
				FacesContext.getCurrentInstance().addMessage(null, facesMessage);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("in update password.");
	}
	
	public void addService() {
		try {
			Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
			String loggedInEmailId = (String) sessionMap.getOrDefault("loggedInEmailId", "Admin@gmail.com");
			Map<String, String> requestParameterMap = FacesContext.getCurrentInstance().getExternalContext()
					.getRequestParameterMap();
			String emailId = (String) requestParameterMap.get("paramEmailId");
			String from = (String) requestParameterMap.get("paramFrom");
			String dest = (String) requestParameterMap.get("paramDest");
			String weight = (String) requestParameterMap.get("paramWeight");
			String doj = requestParameterMap.get("paramDOJ");
			TravelInformation travelInfo = new TravelInformation();
			travelInfo.setDateOfTravel(doj);
			travelInfo.setServiceType("SERVICE");
			travelInfo.setTravelFrom(from);
			travelInfo.setTravelDestination(dest);
			travelInfo.setWeight(Integer.parseInt(weight));
			travelInfo.setEmailId(loggedInEmailId);
			travelInfo.setServiceProvEmail(emailId);
			agentBo.addService(travelInfo);
			sessionMap.put("serviceAdded", "true");
			FacesMessage facesMessage = new FacesMessage("Data added successfully");
			FacesContext.getCurrentInstance().addMessage(null, facesMessage);
			mailBean.sendMail(loggedInEmailId, "You have choosen " + emailId + " as your co-traveller.");
			mailBean.sendMail(emailId, "You are added as co-traveller by " + loggedInEmailId + ".");
		} catch (Exception e) {
			e.printStackTrace();
			FacesMessage facesMessage = new FacesMessage("Error occured.");
			FacesContext.getCurrentInstance().addMessage(null, facesMessage);
		}

	}

	public void cancelService() {
		try {
			Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
			String loggedInEmailId = (String) sessionMap.getOrDefault("loggedInEmailId", "Admin@gmail.com");
			Map<String, String> requestParameterMap = FacesContext.getCurrentInstance().getExternalContext()
					.getRequestParameterMap();
			String emailId = (String) requestParameterMap.get("paramEmailId");
			String from = (String) requestParameterMap.get("paramFrom");
			String dest = (String) requestParameterMap.get("paramDest");
			String weight = (String) requestParameterMap.get("paramWeight");
			String doj = requestParameterMap.get("paramDOJ");
			TravelInformation travelInfo = new TravelInformation();
			travelInfo.setDateOfTravel(doj);
			travelInfo.setServiceType("SERVICE");
			travelInfo.setTravelFrom(from);
			travelInfo.setTravelDestination(dest);
			travelInfo.setWeight(Integer.parseInt(weight));
			travelInfo.setEmailId(loggedInEmailId);
			travelInfo.setServiceProvEmail(emailId);
			agentBo.cancelService(travelInfo);
			FacesMessage facesMessage = new FacesMessage("Service cancelled successfully");
			FacesContext.getCurrentInstance().addMessage(null, facesMessage);
			mailBean.sendMail(emailId, "Your service has been cancelled by " + loggedInEmailId + ".");
		} catch (Exception e) {
			e.printStackTrace();
			FacesMessage facesMessage = new FacesMessage("Error occured.");
			FacesContext.getCurrentInstance().addMessage(null, facesMessage);
		}
	}
	
	public String modifyDetails(){
		try {
			AgentController agentBean = (AgentController)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("agentBean");
			Map<String, String> requestParameterMap = FacesContext.getCurrentInstance().getExternalContext()
					.getRequestParameterMap();
			String from = requestParameterMap.get("paramFrom");
			String dest = requestParameterMap.get("paramDest");
			String weight = requestParameterMap.get("paramWeight");
			String doj = requestParameterMap.get("paramDOJ");
			String peopleAccom = requestParameterMap.get("paramPeopleAccom");
			agentBean.setFrom(from);
			agentBean.setDestination(dest);
			agentBean.setDoj(CustomUtil.convertStringToDate(doj));
			agentBean.setWeight(Integer.parseInt(weight));
			agentBean.setAccompanyCount(Integer.parseInt(peopleAccom));
		} catch (Exception e) {
			e.printStackTrace();
			FacesMessage facesMessage = new FacesMessage("Error occured.");
			FacesContext.getCurrentInstance().addMessage(null, facesMessage);
		}
		return "UserHome";
	}
	
	public void cancelAgent(){
		try {
			Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
			String loggedInEmailId = (String) sessionMap.getOrDefault("loggedInEmailId", "Admin@gmail.com");
			Map<String, String> requestParameterMap = FacesContext.getCurrentInstance().getExternalContext()
					.getRequestParameterMap();
			String emailId = (String) requestParameterMap.get("paramEmailId");
			String from = (String) requestParameterMap.get("paramFrom");
			String dest = (String) requestParameterMap.get("paramDest");
			String weight = (String) requestParameterMap.get("paramWeight");
			String doj = requestParameterMap.get("paramDOJ");
			TravelInformation travelInfo = new TravelInformation();
			travelInfo.setDateOfTravel(doj);
			travelInfo.setServiceType("AGENT");
			travelInfo.setTravelFrom(from);
			travelInfo.setTravelDestination(dest);
			travelInfo.setWeight(Integer.parseInt(weight));
			travelInfo.setEmailId(loggedInEmailId);
			travelInfo.setServiceProvEmail(emailId);
			agentBo.cancelAgent(travelInfo);
			FacesMessage facesMessage = new FacesMessage("Agent cancelled successfully");
			FacesContext.getCurrentInstance().addMessage(null, facesMessage);
			List<String> emailIdList = agentBo.retrieveMailIds(emailId);
			for (String email : emailIdList) {
				mailBean.sendMail(email, "Your trip is cancelled as service provider modified few details.");
			}
			mailBean.sendMail(emailId, "Your service has been cancelled by " + loggedInEmailId + ".");
		} catch (Exception e) {
			e.printStackTrace();
			FacesMessage facesMessage = new FacesMessage("Error occured.");
			FacesContext.getCurrentInstance().addMessage(null, facesMessage);
		}
	}

	/**
	 * @return the confirmEmailId
	 */
	public String getConfirmEmailId() {
		return confirmEmailId;
	}

	/**
	 * @param confirmEmailId
	 *            the confirmEmailId to set
	 */
	public void setConfirmEmailId(String confirmEmailId) {
		this.confirmEmailId = confirmEmailId;
	}

	/**
	 * @return the confirmPassword
	 */
	public String getConfirmPassword() {
		return confirmPassword;
	}

	/**
	 * @param confirmPassword the confirmPassword to set
	 */
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	/**
	 * @return the registerBo
	 */
	public IRegisterBo getRegisterBo() {
		return registerBo;
	}

	/**
	 * @param registerBo the registerBo to set
	 */
	public void setRegisterBo(IRegisterBo registerBo) {
		this.registerBo = registerBo;
	}

	/**
	 * @return the usersList
	 */
	public List getUsersList() {
		return usersList;
	}

	/**
	 * @param usersList the usersList to set
	 */
	public void setUsersList(List usersList) {
		this.usersList = usersList;
	}

	/**
	 * @return the agentsList
	 */
	public List getAgentsList() {
		return agentsList;
	}

	/**
	 * @param agentsList the agentsList to set
	 */
	public void setAgentsList(List agentsList) {
		this.agentsList = agentsList;
	}

	/**
	 * @return the servicesList
	 */
	public List getServicesList() {
		return servicesList;
	}

	/**
	 * @param servicesList the servicesList to set
	 */
	public void setServicesList(List servicesList) {
		this.servicesList = servicesList;
	}

}
