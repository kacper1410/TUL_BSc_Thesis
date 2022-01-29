import { Injectable } from "@angular/core";
import {
    HttpBackend,
    HttpClient,
    HttpErrorResponse,
    HttpEvent,
    HttpHandler,
    HttpInterceptor,
    HttpRequest
} from "@angular/common/http";
import { Observable, throwError } from "rxjs";
import { catchError } from "rxjs/operators";
import { NotificationService } from "../service/notification.service";
import { Router } from "@angular/router";

@Injectable()
export class ErrorInterceptor implements HttpInterceptor {
    private httpClient: HttpClient;

    constructor(private httpBackend: HttpBackend,
                private notify: NotificationService,
                private router: Router) {
        this.httpClient = new HttpClient(httpBackend);
    }

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        return next.handle(req).pipe(
            catchError((error: HttpErrorResponse) => {
                let errorCode = this.processError(error);
                if (error.status === 401) this.router.navigateByUrl('/login');
                return throwError(errorCode);
            })
        );
    }

    private processError(error: HttpErrorResponse) {
        let errorCode = '';
        console.error(error);
        if (!(error.error instanceof ErrorEvent)) {
            errorCode = error.error.message;
        }
        errorCode = errorCode? errorCode : 'Exception.INTERNAL_EXCEPTION'
            this.notify.error(errorCode);
        return errorCode;
    }
}
