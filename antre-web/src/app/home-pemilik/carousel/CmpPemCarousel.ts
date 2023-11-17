import { Component, OnDestroy, OnInit } from '@angular/core';
import { PrvHome, Tenant } from '../../home/PrvHome';
import { Subject, takeUntil } from 'rxjs';

@Component({
	selector: 'cmp-pem-carousel',
	templateUrl: './CmpPemCarousel.html',
	styleUrls: ['./CmpPemCarousel.css']
})
export class CmpPemCarousel implements OnInit, OnDestroy {

	tenant!: Tenant
	unsubscriber = new Subject();
	tokoBuka = false;
	antreNumber: any;

	constructor(private prvHome: PrvHome

	) { }

	ngOnInit(): void {
		this.getAntreNumber()
		this.getTenantData()
	}

	getTenantData() {
		const token = localStorage.getItem('token')
		this.prvHome.getTenantByToken(token).subscribe(
			(data: any) => {
				this.tenant = data
				if (this.tenant.statusToko === 'buka') {
					this.tokoBuka = true
				} else {
					this.tokoBuka = false
				}
			}
		)
	}

	getAntreNumber() {
		this.prvHome.getAntreNumber().pipe(takeUntil(this.unsubscriber))
			.subscribe(
				(data: any) => {
					this.antreNumber = data
					console.log('antre number is ' + this.antreNumber)
				}
			)
	}

	ngOnDestroy(): void {
		this.unsubscriber.next(null);
		this.unsubscriber.complete();
		this.unsubscriber.unsubscribe();
		// you need to unsubscribe to avoid memory leak
	}

	showBarcode() {
		this.prvHome.setShowBarcode(true)
	}

	// setStatusToko(tokoBuka: boolean) {
	// 	if (tokoBuka) {
	// 		this.tenant.statusToko = 'tutup'
	// 	} else {
	// 		this.tenant.statusToko = 'buka'
	// 	}
	// 	this.prvHome.updateStatusTenant(this.tenant.statusToko).subscribe(
	// 		(data: any) => {

	// 		}
	// 	)
	// 	this.prvHome.setStatusToko(tokoBuka)
	// }

	openToko() {
		this.tokoBuka = true
		this.prvHome.setStatusToko(true)
		this.prvHome.updateStatusTenant('buka').subscribe(
			(data: any) => {

			}
		)
	}

	closeToko() {
		this.tokoBuka = false
		this.prvHome.setStatusToko(false)
		this.prvHome.updateStatusTenant('tutup').subscribe(
			(data: any) => {

			}
		)
	}

	setBarcode() {

	}


}
