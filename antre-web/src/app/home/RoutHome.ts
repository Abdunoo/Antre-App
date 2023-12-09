import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CmpPem } from '../home-pemilik/CmpPem';
import { CmpPel } from '../home-pelanggan/CmpPel';
import { CmpLogin } from '../home-pemilik/login/CmpLogin';
import { CmpDaftar } from '../home-pemilik/login/daftar/CmpDaftar';
import { CmpDashboard } from '../dashboard/CmpDashboard';
import { CmpCreatePwd } from '../home-pemilik/login/daftar/pass/CmpCreatePwd';
import { AuthGuard } from './AuthGuard';



@NgModule({
	imports: [
		RouterModule.forChild([
			{ path: 'pemilik', component: CmpPem },
			{ path: 'antreTo/:tenant', component: CmpPel },
			{ path: 'dashboard/login', component: CmpLogin },
			{ path: 'dashboard/daftar', component: CmpDaftar },
			{ path: 'dashboard/pwd', component: CmpCreatePwd },
			{ path: 'dashboard', component: CmpDashboard }
		])
	],
	exports: [RouterModule],

})
export class RoutHome { }

