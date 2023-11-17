package com.abdun.ws;

import java.util.List;

import com.abdun.dto.CurrentTenant;
import com.abdun.dto.NeedUser;
import com.abdun.rcd.RcdAntreHistory;
import com.abdun.rcd.RcdTenant;
import com.abdun.srv.SrvAntreHistory;
import com.abdun.srv.SrvTenant;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

/**
 *
 * @author
 */
@Path("history")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class WsAntreHistory {
	@Inject
	private SrvAntreHistory srvAntreHistory;
	@Inject
	private SrvTenant srvTenant;

	@Inject
	private CurrentTenant currentTenant;

	@Path("list")
	@GET
	// @NeedUser
	public List<RcdAntreHistory> listHistory() {
		List<RcdAntreHistory> lst = srvAntreHistory.getAllHistory();
		System.out.println("get list of all historys");
		for (RcdAntreHistory rcdAntreHistory : lst) {
			srvAntreHistory.detach(rcdAntreHistory.getTenantId());
			rcdAntreHistory.getTenantId().setHistoryCollection(null);
		}
		return lst;
	}

	@Path("myHistorys")
	@GET
	@NeedUser
	public List<RcdAntreHistory> myHistorys() {
		int tenantId = currentTenant.getTenId();
		List<RcdAntreHistory> lst = srvAntreHistory.getMyHistorys(tenantId);
		System.out.println("get list of my history");
		for (RcdAntreHistory rcdAntreHistory : lst) {
			srvAntreHistory.detach(rcdAntreHistory.getTenantId());
			rcdAntreHistory.getTenantId().setHistoryCollection(null);
		}
		return lst;
	}

	@Path("insert")
	@POST
	public RcdAntreHistory insert(RcdAntreHistory rcdAntreHistory) {
		srvAntreHistory.update(rcdAntreHistory);
		RcdTenant ten = srvTenant.getTenantById(rcdAntreHistory.getTenantId().getId()); // set jumlah antrean in tenant table
		if (ten.getJumlahAntrean() == null) {
			ten.setJumlahAntrean(1);
		} else {
			ten.setJumlahAntrean(ten.getJumlahAntrean() + 1);
		}
		srvTenant.update(ten);
		System.out.println("add history");
		return null;
	}

}
