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

@Injectable()
export class ErrorInterceptor implements HttpInterceptor {
    private httpClient: HttpClient;

    constructor(private httpBackend: HttpBackend, private notify: NotificationService) {
        this.httpClient = new HttpClient(httpBackend);
    }

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        return next.handle(req).pipe(
            catchError((error: HttpErrorResponse) => {
                let errorCode: string;
                console.error(error);
                if (error.error instanceof ErrorEvent) {
                    errorCode = 'Exception.INTERNAL_EXCEPTION'
                } else {
                    errorCode = error.error.message;
                }
                this.notify.error(errorCode);
                return throwError(errorCode);
            })
        );
    }
}
