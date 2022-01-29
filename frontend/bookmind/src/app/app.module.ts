import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { OnlineStatusModule } from 'ngx-online-status';

import { AppComponent } from './app.component';
import { BookListComponent } from './components/book-list/book-list.component';
import { HTTP_INTERCEPTORS, HttpClient, HttpClientModule } from "@angular/common/http";
import { BookAddComponent } from './components/book-add/book-add.component';
import { MainComponent } from './components/main/main.component';
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { ServiceWorkerModule } from '@angular/service-worker';
import { environment } from '../environments/environment';
import { NavbarComponent } from './components/navbar/navbar.component';
import { LoginComponent } from './components/login/login.component';
import { AuthInterceptor } from "./interceptor/AuthInterceptor";
import { NotifierModule } from "angular-notifier";
import { notifierOptions } from "./config/BookmindNotifierOptions";
import { ErrorInterceptor } from "./interceptor/ErrorInterceptor";
import { TranslateHttpLoader } from "@ngx-translate/http-loader";
import { TranslateLoader, TranslateModule, TranslateService } from "@ngx-translate/core";
import { NgxBootstrapConfirmModule } from "ngx-bootstrap-confirm";

export function rootLoaderFactory(http: HttpClient) {
    return new TranslateHttpLoader(http)
}
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
        NotifierModule.withConfig(notifierOptions),
        TranslateModule.forRoot({
            loader: {
                provide: TranslateLoader,
                useFactory: rootLoaderFactory,
                deps: [HttpClient]
            }
        }),
        NgxBootstrapConfirmModule
    ],
    providers: [
        { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true },
        { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true },
        TranslateService
    ],
    bootstrap: [ AppComponent ]
})
export class AppModule {
}
