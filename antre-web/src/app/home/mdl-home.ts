import { NgModule } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { RoutHome } from './RoutHome';
import { NgApexchartsModule } from 'ng-apexcharts';
import { CmpChart } from '../home-pemilik/chart/chart.component';
import { CmpPem } from '../home-pemilik/CmpPem';
import { CmpPemCarousel } from '../home-pemilik/carousel/CmpPemCarousel';
import { CmpPel } from '../home-pelanggan/CmpPel';
import { CmpPelCarousel } from '../home-pelanggan/carousel/CmpPelCarousel';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CmpLogin } from '../home-pemilik/login/CmpLogin';
import { CmpDaftar } from '../home-pemilik/login/daftar/CmpDaftar';
import { CmpDashboard } from '../dashboard/CmpDashboard';
import { CmpCreatePwd } from '../home-pemilik/login/daftar/pass/CmpCreatePwd';
import { AuthGuard } from './AuthGuard';
import { QRCodeModule } from 'angularx-qrcode';



@NgModule({
	declarations: [
		CmpPem,
		CmpPemCarousel,
		CmpChart,
		CmpPel,
		CmpPelCarousel,
		CmpLogin,
		CmpDaftar,
		CmpDashboard,
		CmpCreatePwd

	],
	imports: [
		CommonModule,
		RoutHome,
		NgApexchartsModule,
		FormsModule,
		ReactiveFormsModule,
		QRCodeModule
	],
	providers: [DatePipe, AuthGuard]

})
export class MdlHome { }
