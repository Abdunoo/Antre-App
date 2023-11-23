package com.abdun.rcd;

import java.io.Serializable;
import java.sql.Time;
import java.util.Collection;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 *
 * @author trainee
 */
@Entity
@Table(name = "tenant")
public class RcdTenant implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	public Integer id;
	@Column(name = "nama")
	private String nama;
	@Column(name = "alamat")
	private String alamat;
	@Column(name = "description")
	private String description;
	@Column(name = "email")
	private String email;
	@Column(name = "password")
	private String password;
	@Column(name = "max_antre")
	private Integer maxAntre;
	@Column(name = "buka")
	private String buka;
	@Column(name = "tutup")
	private String tutup;
	@Column(name = "link_tenant")
	private String linkTenant;
	@Column(name = "status_toko")
	private String statusToko;
	@Column(name = "token")
	private String token;
	@Column(name = "image_url")
	private String imageUrl;
	@Column(name = "number_now")
	private Integer numberNow;
	@Column(name = "jumlah_antrean")
	private Integer jumlahAntrean;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "tenantId", fetch = FetchType.EAGER)
	private Collection<RcdAntreHistory> historyCollection;

	public RcdTenant() {

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

	public String getAlamat() {
		return alamat;
	}

	public void setAlamat(String alamat) {
		this.alamat = alamat;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getMaxAntre() {
		return maxAntre;
	}

	public void setMaxAntre(Integer maxAntre) {
		this.maxAntre = maxAntre;
	}

	public String getLinkTenant() {
		return linkTenant;
	}

	public void setLinkTenant(String linkTenant) {
		this.linkTenant = linkTenant;
	}

	public String getStatusToko() {
		return statusToko;
	}

	public void setStatusToko(String statusToko) {
		this.statusToko = statusToko;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Integer getNumberNow() {
		return numberNow;
	}

	public void setNumberNow(Integer numberNow) {
		this.numberNow = numberNow;
	}

	public String getBuka() {
		return buka;
	}

	public void setBuka(String buka) {
		this.buka = buka;
	}

	public String getTutup() {
		return tutup;
	}

	public void setTutup(String tutup) {
		this.tutup = tutup;
	}

	public Integer getJumlahAntrean() {
		return jumlahAntrean;
	}

	public void setJumlahAntrean(Integer jumlahAntrean) {
		this.jumlahAntrean = jumlahAntrean;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Collection<RcdAntreHistory> getHistoryCollection() {
		return historyCollection;
	}

	public void setHistoryCollection(Collection<RcdAntreHistory> historyCollection) {
		this.historyCollection = historyCollection;
	}

}
