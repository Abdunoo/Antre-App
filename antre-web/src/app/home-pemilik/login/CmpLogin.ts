import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { PrvHome } from 'src/app/home/PrvHome';

@Component({
	selector: 'cmp-login',
	templateUrl: './CmpLogin.html',
	styleUrls: ['./CmpLogin.css']
})
export class CmpLogin implements OnInit {

	token = ''
	errorMsg = ''

	constructor(
		private prvHome: PrvHome,
		private router: Router,

	) { }

	ngOnInit() {

	}

	login(nama: any, pwd: any) {
		this.prvHome.login(nama, pwd).subscribe(
			(data: any) => {
				if (data.token != null && data.token != '') {
					localStorage.setItem('token', data.token)
					console.log('login succest')
					this.router.navigate(['/pemilik'])
				} else {
					this.errorMsg = 'wrong nama or password'
				}
			}
		)
	}


}
