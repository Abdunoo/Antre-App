import { NgModule, isDevMode } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MdlHome } from './home/mdl-home';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { DefaultHttpInterceptor } from './shared/DefaultHttpInterceptor';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ServiceWorkerModule } from '@angular/service-worker';


@NgModule({
	declarations: [
		AppComponent,
	],
	imports: [
		BrowserModule,
		AppRoutingModule,
		MdlHome,
		HttpClientModule,
		BrowserAnimationsModule,
  ServiceWorkerModule.register('ngsw-worker.js', {
    enabled: !isDevMode(),
    // Register the ServiceWorker as soon as the application is stable
    // or after 30 seconds (whichever comes first).
    registrationStrategy: 'registerWhenStable:30000'
  })

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
