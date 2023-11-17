package com.abdun.srv;

import java.util.List;

import com.abdun.dto.CurrentTenant;
import com.abdun.rcd.RcdAntreHistory;
import com.abdun.rcd.RcdTenant;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

/**
 *
 * @author trainee
 */
@ApplicationScoped
@Transactional
public class SrvAntreHistory {

	@Inject
	private EntityManager em;

	@Inject
	private CurrentTenant currentTenant;

	public List<RcdAntreHistory> getAllHistory() {
		TypedQuery<RcdAntreHistory> tq = em.createQuery("SELECT h FROM RcdAntreHistory h LEFT JOIN FETCH h.tenantId", RcdAntreHistory.class);
		return tq.getResultList();
	}
	
	public List<RcdAntreHistory> getMyHistorys(int id) {
		TypedQuery<RcdAntreHistory> tq = em.createQuery("SELECT h FROM RcdAntreHistory h LEFT JOIN FETCH h.tenantId WHERE h.tenantId.id = :id", RcdAntreHistory.class);
		tq.setParameter("id", id);
		return tq.getResultList();
	}

	public void update(RcdAntreHistory rcdAntreHistory) {
		em.merge(rcdAntreHistory);
	}

	public void detach(Object record) {
		em.detach(record);
	}

}
