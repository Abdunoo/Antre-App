package com.abdun.dto;

import jakarta.enterprise.context.RequestScoped;

/**
 *
 * @author abdun
 */
@RequestScoped
public class CurrentTenant {
	private int tenId;

	public int getTenId() {
		return tenId;
	}

	public void setTenId(int tenId) {
		this.tenId = tenId;
	}

}
