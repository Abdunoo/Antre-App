package com.abdun.srv;

import java.math.BigInteger;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.abdun.dto.CurrentTenant;
import com.abdun.dto.DtoCount;
import com.abdun.rcd.RcdAntreHistory;
import com.abdun.rcd.RcdTenant;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
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

	public void update(RcdAntreHistory rcdAntreHistory) {
		em.merge(rcdAntreHistory);
	}

	public void detach(Object record) {
		em.detach(record);
	}

	public List<RcdAntreHistory> getAllHistory() {
		try {
			TypedQuery<RcdAntreHistory> tq = em.createQuery("SELECT h FROM RcdAntreHistory h LEFT JOIN FETCH h.tenantId",
					RcdAntreHistory.class);
			return tq.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<RcdAntreHistory> getMyHistorys(int id) {
		try {
			TypedQuery<RcdAntreHistory> tq = em.createQuery("SELECT h\n" + //
					"FROM RcdAntreHistory h\n" + //
					"LEFT JOIN FETCH h.tenantId\n" + //
					"WHERE h.tenantId.id = :id\n" + //
					"  AND DATE(h.waktuDaftar) = CURDATE()\n" + //
					"ORDER BY h.id DESC\n" + //
					"", RcdAntreHistory.class);
			tq.setParameter("id", id);
			return tq.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<DtoCount> countHistory(int tenantId) {
		try {
			String nativeQuery = "SELECT " +
					"    DATE(waktu_daftar) AS date, " +
					"    COUNT(*) AS count " +
					"FROM " +
					"    antre_his " +
					"WHERE tenant_id = :id " +
					" AND waktu_daftar >= CURDATE() - INTERVAL 7 DAY " +
					"GROUP BY " +
					"    date " +
					"ORDER BY " +
					"    date";

			Query query = em.createNativeQuery(nativeQuery);
			query.setParameter("id", tenantId);
			List<Object[]> results = query.getResultList();
			List<DtoCount> dtoCounts = new ArrayList<>();

			for (Object[] result : results) {
				DtoCount dtoCount = new DtoCount();
				dtoCount.setDate((Date) result[0]); // Assuming the date is a string
				dtoCount.setCount(((Long) result[1]).intValue()); // Assuming the count is an integer
				dtoCounts.add(dtoCount);
			}

			if (dtoCounts.isEmpty()) {
				DtoCount dtoCount = new DtoCount();
				Date p = new Date(2023, 12, 31);
				dtoCount.setCount(1);
				dtoCount.setDate(p);
				dtoCounts.add(dtoCount);
				return dtoCounts;

			} else {
				return dtoCounts;
			}

		} catch (NoResultException e) {
			return null;
		}
	}

}
