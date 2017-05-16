package com.ucm;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import com.ucm.bo.ILoginBo;
import com.ucm.bo.IRegisterBo;
import com.ucm.bo.ISendMail;
import com.ucm.model.Register;
import com.ucm.model.UserCredentials;
import com.ucm.util.CryptUtil;

public class RegisterController {

	private String firstName;

	private String lastName;

	private String dob;

	private String emailId;

	private String degree;

	private String password;

	private IRegisterBo registerBo;

	private ISendMail mailBean;

	private ILoginBo loginBo;

	/**
	 * @param loginBo
	 *            the loginBo to set
	 */
	public void setLoginBo(ILoginBo loginBo) {
		this.loginBo = loginBo;
	}

	/**
	 * @param registerBo
	 */
	public void setRegisterBo(IRegisterBo registerBo) {
		this.registerBo = registerBo;
	}

	/**
	 * @param mailBean
	 *            the mailBean to set
	 */
	public void setMailBean(ISendMail mailBean) {
		this.mailBean = mailBean;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName
	 *            the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName
	 *            the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the dob
	 */
	public String getDob() {
		return dob;
	}

	/**
	 * @param dob
	 *            the dob to set
	 */
	public void setDob(String dob) {
		this.dob = dob;
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
	 * @return the degree
	 */
	public String getDegree() {
		return degree;
	}

	/**
	 * @param degree
	 *            the degree to set
	 */
	public void setDegree(String degree) {
		this.degree = degree;
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

	public String registerUser() {
		String viewName="Main?faces-redirect=true";
		try {
			UserCredentials user = loginBo.validateUser(emailId);
			if (null == user.getEmailId()) {
				Register register = new Register();
				register.setEmailId(this.emailId);
				register.setFirstName(this.firstName);
				register.setLastName(this.lastName);
				register.setPassword(CryptUtil.encrypt(password));
				registerBo.saveUser(register);
				String mailText = "Thanks for registering with us.";
				mailBean.sendMail(this.emailId, mailText);
			} else {
				FacesMessage facesMessage = new FacesMessage("EmailId already registered.");
				FacesContext.getCurrentInstance().addMessage(null,facesMessage);
				viewName="Register";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return viewName;
	}

}
