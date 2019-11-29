package com.tansha.library.bookshelf.utils;

import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import com.tansha.library.bookshelf.model.User;

//import org.baeldung.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailSender {

	@Autowired
	private MessageSource messages;

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private Environment env;

	// API

	public void confirmRegistration(final HttpServletRequest request, User user) {
		// final User user = event.getUser();

		try {
			final SimpleMailMessage email = constructEmailMessage(request, user);
			System.out.println(email.toString() + "\n\n email SMTP >>> ");

			mailSender.send(email);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	//

	public final SimpleMailMessage constructEmailMessage(final HttpServletRequest request, final User user) {
		System.out.println("request.getLocale() >>>>>>> " + request.getLocale() + " FirstName >>> " + user.getName());
		final String recipientAddress = user.getEmailId();
		final String subject = "Registration Confirmation";
		System.out.println("constructEmailMessage 1 >>> " + recipientAddress);
		final String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort()
				+ request.getContextPath();
		System.out.println("constructEmailMessage 2 >>>" + appUrl);
		final String confirmationUrl = appUrl + "/emailtemplates/registrationConfirm.html?firstName=" + user.getName();
		System.out.println("constructEmailMessage 3 >>> " + confirmationUrl);
		// final String message = messages.getMessage("message.regSucc", null,
		// request.getLocale());
		String message = "Successfully Registered !!!";
		final SimpleMailMessage email = new SimpleMailMessage();
		System.out.println("constructEmailMessage 4");
		email.setTo(recipientAddress);
		System.out.println("constructEmailMessage 5");
		email.setReplyTo("habeep2k1@gmail.com");
		Date date = new Date();
		email.setSentDate(date);
		email.setSubject(subject);
		System.out.println("constructEmailMessage 6");
		email.setText(message);
		System.out.println("constructEmailMessage 7 >>>> ");
		email.setFrom("habeep2k1@gmail.com");
		System.out.println("constructEmailMessage 8");
		return email;
	}

}
