import { Component, OnInit } from '@angular/core';
import { PrvHome, Tenant } from 'src/app/home/PrvHome';

@Component({
	selector: 'cmp-dafter',
	templateUrl: './CmpDaftar.html',
	styleUrls: ['./CmpDaftar.css']
})
export class CmpDaftar implements OnInit {

	errorMsg = ''
	tenant?: Tenant[]
	daftarSuccest = false;

	constructor(
		private prvHome: PrvHome
	) { }

	ngOnInit(): void {
	}

	daftar(email: string, nama: string) {
		console.log(email, nama)

		if (!email.includes('@' && 'com')) {
			this.errorMsg = 'need "@" and "com"'
		} else {
			this.prvHome.getTenantByName(nama).subscribe(
				(data: any) => {
					if (data === null) {
						this.prvHome.daftar(nama, email).subscribe(
							(data: any) => {
								this.daftarSuccest = true
							}
						)
					} else {
						this.errorMsg = 'Ada nama bisnis yang sama. Ganti Nama dengan yang lain!'
					}
				}
			)
		}
	}

}
