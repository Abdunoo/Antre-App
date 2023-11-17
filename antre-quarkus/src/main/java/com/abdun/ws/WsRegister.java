package com.abdun.ws;

import java.util.HashMap;
import java.util.UUID;

import com.abdun.common.EmailSender;
import com.abdun.rcd.RcdTenant;
import com.abdun.srv.SrvRegister;
import com.abdun.srv.SrvTenant;

import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;
import io.smallrye.common.annotation.Blocking;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import net.bytebuddy.utility.RandomString;
import io.quarkus.elytron.security.common.BcryptUtil;

@Path("reg")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class WsRegister {

	@Inject
	Mailer mailer;

	@Inject
	EmailSender emailSender;

	@Inject
	SrvRegister srvRegister;

	@Inject
	SrvTenant srvTenant;

	@Path("new")
	@POST
	@Blocking
	public void registerNewUser(HashMap<String, String> payload) {
		String email = payload.get("email").toLowerCase().trim();
		String nameTenant = payload.get("nameTenant").toLowerCase().trim();
		String baseUrl = "http://localhost:4200/";
		String invitationUrl = srvRegister.createRegistrationEntry(nameTenant, baseUrl);
		emailSender.sendRegistrationEmail(invitationUrl, email);

		RcdTenant ten = new RcdTenant();
		ten.setNama(nameTenant);
		ten.setEmail(email);
		srvTenant.update(ten);
	}

	@Path("save")
	@POST
	public HashMap<String, String> savePassword(HashMap<String, String> payload) {
		String nameTenant = payload.get("nameTenant");
		RcdTenant ten = srvTenant.getTenantByName(nameTenant);

		if (ten != null) {
			String password = BcryptUtil.bcryptHash(payload.get("password"));
			String rememberMeToken = UUID.randomUUID().toString().replace("-", "");
			ten.setPassword(password);
			ten.setToken(rememberMeToken);
			ten.setLinkTenant("http://localhost:4200/antreTo/" + nameTenant);
			srvTenant.update(ten);
		}
		HashMap<String, String> result = new HashMap<>();
		result.put("token", ten.getToken());
		return result;
	}
}
