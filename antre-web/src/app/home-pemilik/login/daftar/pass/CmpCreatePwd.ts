import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PrvHome } from 'src/app/home/PrvHome';

@Component({
	selector: 'app-create-pwd',
	templateUrl: './CmpCreatePwd.html',
	styleUrls: ['./CmpCreatePwd.css']
})
export class CmpCreatePwd implements OnInit {

	errorMsg = '';
	nameTenant = ''

	constructor(
		private prvHome: PrvHome,
		private router: Router,
		private route: ActivatedRoute

	) { }

	ngOnInit(): void {
		this.getNameTenant()
	}

	getNameTenant() {
		this.route.queryParams.subscribe(params => {
			this.nameTenant = params['name'];
		});
	}

	save(pwd: string, conPwd: string) {
		const trimmedPwd = pwd.trim();
		const trimmedConPwd = conPwd.trim();

		if (trimmedPwd === trimmedConPwd && trimmedPwd.length >= 8) {
			this.prvHome.save(this.nameTenant, trimmedPwd).subscribe(
				(data: any) => {
					localStorage.setItem('token', data.token)
					this.router.navigate(['/pemilik'])
					console.log('Registration successful');
				}
			)

		} else {
			this.errorMsg = ('Invalid password. Passwords must match and have a minimum length of 8 characters.');
		}
	}
}
