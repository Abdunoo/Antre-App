import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MdlHome } from './home/mdl-home';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { DefaultHttpInterceptor } from './shared/DefaultHttpInterceptor';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';


@NgModule({
	declarations: [
		AppComponent,
	],
	imports: [
		BrowserModule,
		AppRoutingModule,
		MdlHome,
		HttpClientModule,
		BrowserAnimationsModule

	],
	providers: [
		{
			provide: HTTP_INTERCEPTORS,
			useClass: DefaultHttpInterceptor,
			multi: true,
		}
	], bootstrap: [AppComponent]
})
export class AppModule { }
