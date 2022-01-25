import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { OnlineStatusModule } from 'ngx-online-status';

import { AppComponent } from './app.component';
import { BookListComponent } from './book-list/book-list.component';
import { HTTP_INTERCEPTORS, HttpClientModule } from "@angular/common/http";
import { BookAddComponent } from './book-add/book-add.component';
import { MainComponent } from './main/main.component';
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { ServiceWorkerModule } from '@angular/service-worker';
import { environment } from '../environments/environment';
import { NavbarComponent } from './navbar/navbar.component';
import { LoginComponent } from './login/login.component';
import { AuthInterceptor } from "./interceptor/AuthInterceptor";

@NgModule({
    declarations: [
        AppComponent,
        BookListComponent,
        BookAddComponent,
        MainComponent,
        NavbarComponent,
        LoginComponent
    ],
    imports: [
        BrowserModule,
        HttpClientModule,
        AppRoutingModule,
        ReactiveFormsModule,
        OnlineStatusModule,
        ServiceWorkerModule.register('ngsw-worker.js', {
            enabled: environment.production,
            // Register the ServiceWorker as soon as the app is stable
            // or after 30 seconds (whichever comes first).
            registrationStrategy: 'registerWhenStable:30000'
        }),
        FormsModule,
    ],
    providers: [
        { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true }
    ],
    bootstrap: [ AppComponent ]
})
export class AppModule {
}
