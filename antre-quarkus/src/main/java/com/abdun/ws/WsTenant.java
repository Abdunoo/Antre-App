package com.abdun.ws;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
// import org.apache.commons.io.FileUtils;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.resteasy.reactive.MultipartForm;

import com.abdun.dto.CurrentTenant;
import com.abdun.dto.DtoFile;
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

	@ConfigProperty(name = "abdun.upload-image")
	String uploadDirectory;

	@Path("list")
	@GET
	@NeedUser
	public List<RcdTenant> listTenant() {
		List<RcdTenant> lst = srvTenant.getTenants();
		System.out.println("get list of tenant");
		for (RcdTenant rcdTenant : lst) {
			String imageUrl = getImage(rcdTenant.getImageUrl());
			rcdTenant.setImageUrl(imageUrl);
		}
		return lst;
	}

	@Path("tenantName/{name}")
	@GET
	public RcdTenant getTenantByName(@PathParam("name") String name) {
		RcdTenant tenant = srvTenant.getTenantByName(name);
		if (tenant != null) {
			System.out.println("get tenant by name");
			tenant.setHistoryCollection(null);
			tenant.setPassword(null);
			tenant.setToken(null);
			String imageUrl = getImage(tenant.getImageUrl());
			tenant.setImageUrl(imageUrl);
			return tenant;
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
		String imageUrl = getImage(tenant.getImageUrl());
		tenant.setImageUrl(imageUrl);
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

		if (!BcryptUtil.matches(password, tenant.getPassword())) {
			// gagal login
			throw new WebApplicationException(Response.Status.UNAUTHORIZED);
		}
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

	// @Path("imageUrl/{url}")
	// @GET
	// @NeedUser
	// public RcdTenant updateImage(@PathParam("url") String imageUrl) {
	// RcdTenant ten = srvTenant.getTenantById(currentTenant.getTenId());
	// ten.setImageUrl(imageUrl);
	// srvTenant.update(ten);
	// return null;
	// }

	@Path("/upload")
	@POST
	@NeedUser
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public void uploadImage(@MultipartForm DtoFile dtoFile) throws IOException {
		File uploadDir = new File(uploadDirectory);
		if (!uploadDir.exists()) {
			uploadDir.mkdirs();
		}
		RcdTenant ten = srvTenant.getTenantById(currentTenant.getTenId());
		String fileName = ten.getNama() + "_" + dtoFile.file.fileName();
		ten.setImageUrl(fileName);
		srvTenant.update(ten);

		System.out.println(uploadDirectory);
		fileName = uploadDirectory + File.separator + fileName;
		Files.move(dtoFile.file.uploadedFile(), Paths.get(fileName));

		// HashMap<String, String> result = new HashMap<>();
		// result.put("imgUrl", dtoFile.file.fileName());
		// return result;
		// if (IOException e) {
		// Log.error("failed to upload file", e);
		// }base64
	}

	public String getImage(String imageUrl) {
		if (imageUrl != null) {
			try {
				String path = uploadDirectory + imageUrl;
				File file = new File(path);

				if (file.exists()) { // Check if the file exists before attempting to read it
					byte[] fileBytes = readBytesFromFile(file);
					String result = Base64.getEncoder().encodeToString(fileBytes);

					if (path.toLowerCase().endsWith("jpg") || path.toLowerCase().endsWith("jpeg")) {
						result = "data:image/jpeg;base64," + result;
					} else if (path.toLowerCase().endsWith("png")) {
						result = "data:image/png;base64," + result;
					} else if (path.toLowerCase().endsWith("gif")) {
						result = "data:image/gif;base64," + result;
					}

					imageUrl = result;
				} else {
					// Handle the case where the file doesn't exist
					throw new RuntimeException("File not found: " + path);
				}
			} catch (IOException ex) {
				throw new RuntimeException(ex);
			}
		}
		return imageUrl;
	}

	private byte[] readBytesFromFile(File file) throws IOException {
		return Files.readAllBytes(file.toPath());
	}
}