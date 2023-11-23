import { Component, OnDestroy, OnInit } from '@angular/core';
import * as ApexCharts from 'apexcharts';
import { Historys, PrvHome, Tenant } from '../home/PrvHome';
import { Subject, take, takeUntil } from 'rxjs';
import { AssetToUpload } from './AssetToUpload';

declare var Compress: any;

@Component({
	selector: 'cmp-pem',
	templateUrl: './CmpPem.html',
	styleUrls: ['./CmpPem.css']
})
export class CmpPem implements OnInit, OnDestroy {
	showPengaturan = false;
	showRiwayat = false;
	showQRcode = false;
	tokoBuka = false;
	maxNumber = false;
	editImage = false;
	editTime = false;
	editMaxAntre = false;
	editAlamat = false;

	unsubscriber = new Subject();
	selectedImage: string | ArrayBuffer | null = null;
	photo: any;
	tenant!: Tenant;
	history!: Historys[]
	antreNumber = 0
	antreanSelesai = 0
	dataQRcode = '';



	constructor(private prvHome: PrvHome,



	) { }

	ngOnInit(): void {
		this.getshowQRcode()
		this.getTenantData()
		this.getHistoryData()
		this.getStatusToko()
	}

	ngOnDestroy(): void {
		this.unsubscriber.next(null);
		this.unsubscriber.complete();
		this.unsubscriber.unsubscribe();
		// you need to unsubscribe to avoid memory leak
	}

	getTenantData() {
		const token = localStorage.getItem('token')
		this.prvHome.getTenantByToken(token).subscribe(
			(data: any) => {
				this.tenant = data
				this.setAntreNumber(this.tenant.numberNow)
			}
		)
	}

	getHistoryData() {
		this.prvHome.getHistoryAll().subscribe(
			(data: any) => {
				this.history = data
			}
		)
	}

	setAntreNumber(numberNow: any) {
		if (numberNow != null) {
			this.antreNumber = numberNow
			if (this.antreNumber != 0) {
				this.antreanSelesai = this.antreNumber - 1
			} else {
				this.antreNumber = 0
			}
		}
		this.prvHome.setAntreNumber(this.antreNumber);
	}

	nextNumber() {
		this.antreNumber = this.antreNumber + 1
		this.antreanSelesai = this.antreanSelesai + 1
		this.setAntreNumber(this.antreNumber)
		this.prvHome.nextAntreNumber(this.antreNumber).subscribe(
			(data: any) => {

			}
		)
	}

	prevNumber() {
		this.antreNumber = this.antreNumber - 1
		this.antreanSelesai = this.antreanSelesai - 1
		this.setAntreNumber(this.antreNumber)
		this.prvHome.prevAntreNumber(this.antreNumber).subscribe(
			(data: any) => {

			}
		)
	}

	getStatusToko() {
		this.prvHome.getStatusToko().pipe(takeUntil(this.unsubscriber))
			.subscribe(
				(data: any) => {
					this.tokoBuka = data
				}
			)
	}

	getshowQRcode() {
		this.prvHome.getShowBarcode().pipe(takeUntil(this.unsubscriber))
			.subscribe(
				(data: any) => {
					this.showQRcode = data
					console.log('show barcode ' + this.showQRcode)
				}
			)
	}

	onFileSelected(event: any) {
		this.editImage = !this.editImage
		const file = event.target.files[0];

		if (file) {
			const reader = new FileReader();
			reader.onload = (e: any) => {
				this.selectedImage = e.target.result;
			};
			reader.readAsDataURL(file);
		}

		const options = {
			targetSize: 0.5,
			quality: 0.75,
			maxWidth: 1024,
			maxHeight: 1024
		};

		const compress = new Compress(options);
		const files = [...event.target.files];
		compress.compress(files).then((results: any) => {
			this.photo = results[0].photo;
			let toUpload = new AssetToUpload;
			toUpload.file = new File([this.photo?.data], this.photo?.name);
			this.updateImage(toUpload)
		});
	}

	updateMaxAntre() {
		this.editMaxAntre = false
		this.prvHome.updateMaxAntre(this.tenant.maxAntre).subscribe(
			(data: any) => {

			}
		)
	}

	updateBukaDanTutup(buka: any, tutup: any) {
		this.editTime = false
		console.log('buka= ' + buka + ' tutup= ' + tutup)
		this.prvHome.updateJamBuka(this.tenant.buka, this.tenant.tutup).subscribe(
			(data: any) => {

			}
		)
	}

	updateAlamat(alamat: any) {
		this.editAlamat = false
		this.prvHome.updateAlamat(alamat).subscribe(
			(data: any) => {

			}
		)
	}

	getMyHistory() {
		this.showPengaturan = false
		this.prvHome.getHistoryAll().subscribe(
			(data: any) => {
				this.history = data
				console.log('get history succest')
			}
		)
	}

	downloadQRCode() {
		const canvas = document.querySelector('canvas') as HTMLCanvasElement;
		const image = canvas.toDataURL('image/png').replace('image/png', 'image/octet-stream');
		const link = document.createElement('a');
		link.href = image;
		link.download = 'qrcode.png';
		link.click();
	}

	updateImage(url: any) {
		this.prvHome.upload(url).subscribe(
			(data: any) => {

			}
		)
	}

	



}
