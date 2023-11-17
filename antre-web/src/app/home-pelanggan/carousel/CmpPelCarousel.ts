import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subject, take, takeUntil } from 'rxjs';
import { PrvHome, Tenant } from 'src/app/home/PrvHome';

@Component({
	selector: 'cmp-pel-carousel',
	templateUrl: './CmpPelCarousel.html',
	styleUrls: ['./CmpPelCarousel.css']
})
export class CmpPelCarousel implements OnInit, OnDestroy {

	unsubscriber = new Subject();
	tokoBuka = false;
	antreNumber: any;
	tenant!: Tenant;

	constructor(private prvHome: PrvHome

	) { }

	ngOnInit(): void {
		this.getNamaTenant()
	}

	getNamaTenant() {
		this.prvHome.getNamaTenant().pipe(takeUntil(this.unsubscriber))
			.subscribe(
				(data: any) => {
					this.getTenantData(data)
				}
			)
	}

	getTenantData(tenantName: string) {
		this.prvHome.getTenantByName(tenantName).subscribe(
			(data: any) => {
				this.tenant = data
				this.antreNumber = this.tenant.numberNow

				if (this.antreNumber === null) {
					this.antreNumber = 0
				}

				if (this.tenant.statusToko === 'buka') {
					this.tokoBuka = true
				} else {
					this.tokoBuka = false
				}
			}
		)
	}

	// getAntreNumber() {
	// 	this.prvHome.getAntreNumber().pipe(takeUntil(this.unsubscriber))
	// 		.subscribe(
	// 			(data: any) => {
	// 				this.antreNumber = data
	// 				console.log('antre number is ' + this.antreNumber)
	// 			}
	// 		)
	// }

	// getStatusToko() {
	// 	this.prvHome.getStatusToko().pipe(takeUntil(this.unsubscriber))
	// 		.subscribe(
	// 			(data: any) => {
	// 				this.tokoBuka = data
	// 			}
	// 		)
	// }

	ngOnDestroy(): void {
		this.unsubscriber.next(null);
		this.unsubscriber.complete();
		this.unsubscriber.unsubscribe();
		// you need to unsubscribe to avoid memory leak
	}

	// showBarcode() {
	// 	this.prvHome.setShowBarcode(true)
	// }


}
