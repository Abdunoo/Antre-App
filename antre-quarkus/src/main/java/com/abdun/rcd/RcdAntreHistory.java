package com.abdun.rcd;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

import org.apache.james.mime4j.dom.datetime.DateTime;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 *
 * @author trainee
 */
@Entity
@Table(name = "antre_his")
public class RcdAntreHistory implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	public Integer id;
	@Column(name = "nama")
	private String nama;
	@Column(name = "nomor")
	private Integer nomor;
	@Column(name = "waktu_daftar")
	private Timestamp waktuDaftar;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "tenant_id", referencedColumnName = "id")
	private RcdTenant tenantId;

	public RcdAntreHistory() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	public Integer getNomor() {
		return nomor;
	}

	public void setNomor(Integer nomor) {
		this.nomor = nomor;
	}

	public Timestamp getWaktuDaftar() {
		return waktuDaftar;
	}

	public void setWaktuDaftar(Timestamp waktuDaftar) {
		this.waktuDaftar = waktuDaftar;
	}

	public RcdTenant getTenantId() {
		return tenantId;
	}

	public void setTenantId(RcdTenant tenantId) {
		this.tenantId = tenantId;
	}

}
