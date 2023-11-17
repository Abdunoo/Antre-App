import { Injectable, OnInit } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { PrvHome } from './PrvHome';

@Injectable({
	providedIn: 'root',
})
export class AuthGuard implements CanActivate, OnInit {

	constructor(
		private router: Router,
		private prvHome: PrvHome
	) { }


	canActivate(): boolean {
		const token = localStorage.getItem('token')
		if (token != null && token != '') {
			return true;
		} else {
			this.router.navigate(['/dashboard/login'])
			console.log('no permission')
			return false;
		}
	}

	ngOnInit() {

	}

}
