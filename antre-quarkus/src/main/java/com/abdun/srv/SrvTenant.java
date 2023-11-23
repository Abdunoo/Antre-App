package com.abdun.srv;

import java.util.Currency;
import java.util.List;

import com.abdun.dto.CurrentTenant;
import com.abdun.rcd.RcdTenant;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

/**
 *
 * @author trainee
 */
@ApplicationScoped
@Transactional
public class SrvTenant {

	@Inject
	private EntityManager em;

	@Inject
	private CurrentTenant currentTenant;

	public List<RcdTenant> getTenants() {
		TypedQuery<RcdTenant> tq = em.createQuery("SELECT t FROM RcdTenant t", RcdTenant.class);
		return tq.getResultList();
	}

	public RcdTenant getTenantByName(String nama) {
		try {
			TypedQuery<RcdTenant> tq = em.createQuery("SELECT t FROM RcdTenant t WHERE t.nama = :nama", RcdTenant.class);
			tq.setParameter("nama", nama);
			return tq.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public RcdTenant getTenantByToken(String token) {
		TypedQuery<RcdTenant> tq = em.createQuery("SELECT t FROM RcdTenant t WHERE t.token = :token", RcdTenant.class);
		tq.setParameter("token", token);
		return tq.getSingleResult();
	}

	public RcdTenant getTenantById(Integer id) {
		TypedQuery<RcdTenant> tq = em.createQuery("SELECT t FROM RcdTenant t WHERE t.id = :id", RcdTenant.class);
		tq.setParameter("id", id);
		return tq.getSingleResult();
	}

	public void updateStatusToko(String status) {
		RcdTenant ten = getTenantById(currentTenant.getTenId());
		ten.setStatusToko(status);
		em.merge(ten);
	}

	public void nextNumber(int number) {
		RcdTenant ten = getTenantById(currentTenant.getTenId());
		ten.setNumberNow(number);
		em.merge(ten);
	}

	public void prevNumber(int number) {
		RcdTenant ten = getTenantById(currentTenant.getTenId());
		ten.setNumberNow(number);
		em.merge(ten);
	}

	public void update(RcdTenant rcdTenant) {
		em.merge(rcdTenant);
	}

	public void detach(Object record) {
		em.detach(record);
	}

}
