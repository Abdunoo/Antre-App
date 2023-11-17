package com.abdun.common;

import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;
import org.jboss.logging.Logger;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

/**
 *
 * @author mubin
 */
@ApplicationScoped
public class EmailSender {

	private Logger log = Logger.getLogger(EmailSender.class);
	@Inject
	Mailer mailer;

	public EmailSender() {
	}

	public void send(String recipients, String content, String title) {

		mailer.send(Mail.withText(recipients, title, content));

	}

	public void sendRegistrationEmail(String linkUrl, String recipient) {

		String content = "Terima kasih telah mendaftar di AntreKuyy.\n"
				+ "\n"
				+ "Klik link berikut ini untuk aktivasi email Anda dan membuat password:\n"
				+ "\n"
				+ linkUrl + "\n"
				+ "\n"
				+ "Link tersebut akan kadaluarsa dalam 24 jam.\n\n"
				+ "Terima kasih,\n"
				+ "--AntreKuyy--";
		send(recipient, content, "Ayo antre dengan lebih mudah");
	}
}
