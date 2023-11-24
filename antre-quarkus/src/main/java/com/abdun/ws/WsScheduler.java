package com.abdun.ws;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.abdun.dto.NeedUser;
import com.abdun.rcd.RcdTenant;
import com.abdun.srv.SrvTenant;

import io.quarkus.logging.Log;
import io.quarkus.scheduler.Scheduled;
import io.quarkus.scheduler.ScheduledExecution;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.control.ActivateRequestContext;
import jakarta.inject.Inject;

@ApplicationScoped
public class WsScheduler {

	@Inject
	private SrvTenant srvTenant;

	// private AtomicInteger counter = new AtomicInteger();

	// public int get() {
	// return counter.get();
	// }

	// @Scheduled(every="10s")
	// void increment() {
	// counter.incrementAndGet();
	// }

	// @Scheduled(cron="0 15 10 * * ?")
	// void cronJob(ScheduledExecution execution) {
	// counter.incrementAndGet();
	// System.out.println(execution.getScheduledFireTime());
	// }

	@ActivateRequestContext
	@Scheduled(cron = "{abdun.cron}")
	void resetJumlahAntrean() {
		Log.debug("test scheduler");
		try {
			List<RcdTenant> tenants = srvTenant.getTenants();
			for (RcdTenant rcdTenant : tenants) {
				if (rcdTenant.getJumlahAntrean() != null || rcdTenant.getJumlahAntrean() != 0
						|| rcdTenant.getNumberNow() != null || rcdTenant.getNumberNow() != 0) {
					rcdTenant.setJumlahAntrean(0);
					rcdTenant.setNumberNow(0);
					srvTenant.update(rcdTenant);
				}
			}
		} catch (Exception e) {
			throw e;
		}

	}
}