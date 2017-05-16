package com.ucm.bo.impl;

import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import com.ucm.bo.ISendMail;

public class SendMailImpl implements ISendMail {

	private MailSender mailSender;
    private SimpleMailMessage templateMessage;
    

	/**
	 * @param mailSender the mailSender to set
	 */
	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}


	/**
	 * @param templateMessage the templateMessage to set
	 */
	public void setTemplateMessage(SimpleMailMessage templateMessage) {
		this.templateMessage = templateMessage;
	}



	@Override
	public void sendMail(String to,String text) {
		SimpleMailMessage msg = new SimpleMailMessage(this.templateMessage);
        msg.setTo(to);
        msg.setText(text);
        try{
            this.mailSender.send(msg);
            System.out.println("mail sent successfully");
        }
        catch (MailException ex) {
            System.err.println(ex.getMessage());
        }
	}

	
}
