import { Component, OnDestroy, OnInit } from '@angular/core';
import { Historys, PrvHome, Tenant } from '../home/PrvHome';
import { Subject, takeUntil } from 'rxjs';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';

@Component({
	selector: 'cmp-pel',
	templateUrl: './CmpPel.html',
	styleUrls: ['./CmpPel.css']
})
export class CmpPel implements OnInit, OnDestroy {
	ambilNomor = false
	tokoBuka = false
	showProfil = false;
	unsubscriber = new Subject();
	nomorAnda: any
	namaAnda: any
	currentDate: any
	tenant!: Tenant
	history: Historys = {}



	constructor(
		private prvHome: PrvHome,
		private datePipe: DatePipe,
		private route: ActivatedRoute,
	) { }

	ngOnInit(): void {
		this.getTenantName()
	}

	getTenantName() {
		this.route.params.subscribe(params => {
			const tenantNama = params['tenant'];
			console.log(tenantNama)
			this.getTenantData(tenantNama)
			this.setNamaTenant(tenantNama)
		})
	}

	getTenantData(tenantName: any) {
		this.prvHome.getTenantByName(tenantName).subscribe(
			(data: any) => {
				this.tenant = data

				if (this.tenant.statusToko === 'buka') {
					this.tokoBuka = true
				}
			}
		)
	}

	// setAntreNumber(antreNumber: any) {
	// 	this.prvHome.setAntreNumber(antreNumber);
	// }

	// setStatusToko(status: any) {
	// 	this.prvHome.setStatusToko(status)
	// }

	// setNomorAnda(jumlahAntrean: any) {
	// 	this.nomorAnda = jumlahAntrean + 1
	// }

	setNamaTenant(nama: any) {
		this.prvHome.setNamaTenant(nama)
	}


	getAntreNumber() {
		this.getCurrentDate()
		this.nomorAnda = this.tenant.jumlahAntrean + 1
	}




	ngOnDestroy(): void {
		this.unsubscriber.next(null);
		this.unsubscriber.complete();
		this.unsubscriber.unsubscribe();
		// you need to unsubscribe to avoid memory leak
	}



	getCurrentDate() {
		const currentDate = new Date();
		this.currentDate = 'Tanggal ' + this.datePipe.transform(currentDate, 'd MMM y HH.mm', 'id')
	}

	insertHistory() {
		console.log('insert')
		this.ambilNomor = false
		this.history.nomor = this.nomorAnda
		this.history.waktuDaftar = new Date()
		this.history.tenantId = this.tenant
		this.prvHome.insertHistory(this.history).subscribe(
			(data: any) => {
				this.getTenantData(this.tenant.nama)

			}
		)
	}


}
