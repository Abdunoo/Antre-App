package com.abdun.ws;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.abdun.dto.CurrentTenant;
import com.abdun.dto.NeedUser;
import com.abdun.rcd.RcdTenant;
import com.abdun.srv.SrvTenant;

import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 *
 * @author
 */
@Path("tenant")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class WsTenant {

	@Inject
	private SrvTenant srvTenant;

	@Inject
	private CurrentTenant currentTenant;

	@Path("list")
	@GET
	@NeedUser
	public List<RcdTenant> listTenant() {
		List<RcdTenant> lst = srvTenant.getTenants();
		System.out.println("get list of tenant");
		return lst;
	}

	@Path("tenantName/{name}")
	@GET
	public RcdTenant getTenantByName(@PathParam("name") String name) {
		RcdTenant lst = srvTenant.getTenantByName(name);
		if (lst != null) {
			System.out.println("get tenant by name");
			lst.setHistoryCollection(null);
			lst.setPassword(null);
			lst.setToken(null);
			return lst;
		} else {
			return null;
		}
	}

	@Path("getTenant/{token}")
	@GET
	// @NeedUser
	public RcdTenant getTenantByToken(@PathParam("token") String token) {
		RcdTenant tenant = srvTenant.getTenantByToken(token);
		System.out.println("get tenant by token");
		tenant.setHistoryCollection(null);
		tenant.setPassword(null);
		tenant.setToken(null);
		return tenant;
	}

	@Path("login")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public HashMap<String, String> check(Map<String, String> param) {
		String name = param.get("nama");
		RcdTenant tenant = srvTenant.getTenantByName(name);
		String password = param.get("password");

		// if (!BcryptUtil.matches(password, member.getPassword())) {
		// // gagal login
		// throw new WebApplicationException(Response.Status.UNAUTHORIZED);
		// }
		HashMap<String, String> result = new HashMap<>();
		result.put("token", tenant.getToken());
		return result;
	}

	@Path("status/{status}")
	@GET
	@NeedUser
	public RcdTenant updateStatusToko(@PathParam("status") String status) {
		srvTenant.updateStatusToko(status);
		System.out.println("update");
		return null;
	}

	@Path("next/{number}")
	@GET
	@NeedUser
	public RcdTenant nextAntreNumber(@PathParam("number") int number) {
		srvTenant.nextNumber(number);
		System.out.println("next");
		return null;
	}

	@Path("prev/{number}")
	@GET
	@NeedUser
	public RcdTenant prevAntreNumber(@PathParam("number") int number) {
		srvTenant.prevNumber(number);
		System.out.println("prev");
		return null;
	}

	@Path("update/jam")
	@GET
	@NeedUser
	public RcdTenant updateBukaTutupTenant(@QueryParam("buka") String buka, @QueryParam("tutup") String tutup) {
		RcdTenant ten = srvTenant.getTenantById(currentTenant.getTenId());
		ten.setBuka(buka);
		ten.setTutup(tutup);
		srvTenant.update(ten);
		return null;
	}

	@Path("maxAntre/{maxAntre}")
	@GET
	@NeedUser
	public RcdTenant updateMaxAntre(@PathParam("maxAntre") int max) {
		RcdTenant ten = srvTenant.getTenantById(currentTenant.getTenId());
		ten.setMaxAntre(max);
		srvTenant.update(ten);
		return null;
	}

	@Path("alamat/{alamat}")
	@GET
	@NeedUser
	public RcdTenant updateAlamat(@PathParam("alamat") String alamat) {
		RcdTenant ten = srvTenant.getTenantById(currentTenant.getTenId());
		ten.setAlamat(alamat);
		srvTenant.update(ten);
		return null;
	}

}