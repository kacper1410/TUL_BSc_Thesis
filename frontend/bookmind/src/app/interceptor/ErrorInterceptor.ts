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
                this.processError(error);
                return throwError(error);
            })
        );
    }

    private processError(error: HttpErrorResponse): void {
        console.error(error);

        if (error.status < 400 || error.status > 500)
            return;

        if (error.status === 409 && error.url?.match('.*\/sync\/.*'))
            return;

        const errorCode = error.error?.message ? error.error?.message : 'Exception.INTERNAL_EXCEPTION';
        this.notify.error(errorCode);

        if (error.status === 401)
            this.router.navigateByUrl('/login');
    }
}
