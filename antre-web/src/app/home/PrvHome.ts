import { Injectable } from "@angular/core"
import { BehaviorSubject, Subject, Timestamp } from "rxjs"
import { HttpClient } from '@angular/common/http';

export interface Tenant {
	id?: number
	nama?: string
	alamat?: string
	description?: string
	email?: string
	password?: string
	maxAntre?: number
	buka?: string
	tutup?: string
	linkTenant: string
	statusToko?: string
	token?: string
	numberNow?: number
	jumlahAntrean: number
}

export interface Historys {
	id?: number
	nama?: string
	nomor?: number
	waktuDaftar?: any
	tenantId?: Tenant
}


@Injectable({ providedIn: 'root' })
export class PrvHome {
	// private postObservable = new BehaviorSubject<Posting[]>([])
	// private storyObservable = new BehaviorSubject<Story[]>([])
	// private productObservable = new BehaviorSubject<Product[]>([])

	private antreNumber = new BehaviorSubject<number>(0)
	private showBarcode = new Subject<boolean>
	private statusTokoBuka = new Subject<boolean>
	private namatenant = new BehaviorSubject<string>('')

	API = 'http://localhost:8080/api/'

	constructor(private http: HttpClient,
	) { }

	public setAntreNumber(number: number) {
		this.antreNumber.next((number))
	}

	public getAntreNumber() {
		return this.antreNumber;
	}

	public setShowBarcode(val: boolean) {
		this.showBarcode.next((val))
	}

	public getShowBarcode() {
		return this.showBarcode
	}

	public setStatusToko(val: boolean) {
		this.statusTokoBuka.next((val))
	}

	public getStatusToko() {
		return this.statusTokoBuka
	}

	public setNamaTenant(val: string) {
		this.namatenant.next((val))
	}

	public getNamaTenant() {
		return this.namatenant
	}


	// =======================api Register======================= //

	daftar(nameTenant: string, email: string) {
		let payload = { nameTenant, email }
		return this.http.post(this.API + `reg/new`, payload)
	}

	save(nameTenant: string, password: string) {
		let payload = { nameTenant, password }
		return this.http.post(this.API + `reg/save`, payload)
	}

	login(nama: string, password: string) {
		let payload = { nama, password }
		return this.http.post<Tenant>(this.API + `tenant/login`, payload) // mengakses API
	}


	// =======================api Tenant======================= //

	getTenants() {
		return this.http.get<Tenant>(this.API + `tenant/list`) // mengakses API
	}

	getTenantByName(name: string) {
		return this.http.get<Tenant>(this.API + `tenant/tenantName/` + name) // mengakses API
	}

	getTenantByToken(token: any) {
		return this.http.get<Tenant>(this.API + `tenant/getTenant/` + token) // mengakses API
	}

	updateStatusTenant(status: string) {
		return this.http.get<Tenant>(this.API + 'tenant/status/' + status) // mengakses API
	}



	nextAntreNumber(number: number) {
		return this.http.get<Tenant>(this.API + 'tenant/next/' + number) // mengakses API
	}

	prevAntreNumber(number: number) {
		return this.http.get<Tenant>(this.API + 'tenant/prev/' + number) // mengakses API
	}

	updateMaxAntre(maxAntre: any) {
		return this.http.get<Tenant>(this.API + 'tenant/maxAntre/' + maxAntre) // mengakses API
	}

	updateJamBuka(buka: any, tutup: any) {
		return this.http.get<Tenant>(this.API + 'tenant/update/jam?buka=' + buka + '&tutup=' + tutup) // mengakses API
	}

	updateAlamat(alamat: string) {
		return this.http.get<Tenant>(this.API + 'tenant/alamat/' + alamat) // mengakses API
	}

	// =======================api History======================= //

	getHistoryAll() {
		return this.http.get<Historys>(this.API + `history/myHistorys`) // mengakses API
	}

	insertHistory(history: Historys) {
		return this.http.post<History>(this.API + `history/insert`, history) // mengakses API
	}
}