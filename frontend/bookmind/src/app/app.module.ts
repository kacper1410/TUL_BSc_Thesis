import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { OnlineStatusModule } from 'ngx-online-status';

import { AppComponent } from './app.component';
import { BookListComponent } from './components/book/book-list/book-list.component';
import { HTTP_INTERCEPTORS, HttpClient, HttpClientModule } from "@angular/common/http";
import { BookAddComponent } from './components/book/book-add/book-add.component';
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
import { BookEditComponent } from './components/book/book-edit/book-edit.component';
import { ProfileComponent } from './components/user/profile/profile.component';
import { UserListComponent } from './components/user/user-list/user-list.component';
import { ShelfListComponent } from './components/shelf/shelf-list/shelf-list.component';
import { ShelfAddComponent } from './components/shelf/shelf-add/shelf-add.component';
import { ShelfDetailsComponent } from './components/shelf/shelf-details/shelf-details.component';
import { ShelfAddBookComponent } from './components/shelf/shelf-add-book/shelf-add-book.component';
import { OfflineLoginComponent } from './components/login/offline-login/offline-login.component';
import { OnlineLoginComponent } from './components/login/online-login/online-login.component';
import { ShelfEditComponent } from './components/shelf/shelf-edit/shelf-edit.component';
import { RegisterFormComponent } from './components/register/register-form/register-form.component';
import { ConfirmAccountComponent } from './components/register/confirm-account/confirm-account.component';
import { SyncErrorModalComponent } from './components/sync-error-modal/sync-error-modal.component';

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
        LoginComponent,
        BookEditComponent,
        ProfileComponent,
        UserListComponent,
        ShelfListComponent,
        ShelfAddComponent,
        ShelfDetailsComponent,
        ShelfAddBookComponent,
        OfflineLoginComponent,
        OnlineLoginComponent,
        ShelfEditComponent,
        RegisterFormComponent,
        ConfirmAccountComponent,
        SyncErrorModalComponent
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
