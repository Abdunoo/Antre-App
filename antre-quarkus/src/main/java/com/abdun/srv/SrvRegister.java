package com.abdun.srv;

import java.util.UUID;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

/**
 *
 * @author trainee
 */
@ApplicationScoped
@Transactional
public class SrvRegister {
	/**
	 *
	 * @param email
	 * @param baseUrl
	 * @return invitationUrl
	 */
	public String createRegistrationEntry(String nameTenant, String baseUrl) {
		String token = UUID.randomUUID().toString();

		if (nameTenant.contains(" ")) {
			nameTenant = nameTenant.replaceAll(" ", "%20");
		}

		if (!baseUrl.endsWith("/")) {
			baseUrl = baseUrl + "/";
		}
		baseUrl = baseUrl + "dashboard/pwd?name=" + nameTenant + "&token=" + token;
		return baseUrl;
	}
}
