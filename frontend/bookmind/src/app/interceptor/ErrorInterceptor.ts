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
import { NotifierService } from "angular-notifier";

@Injectable()
export class ErrorInterceptor implements HttpInterceptor {
    private httpClient: HttpClient;

    constructor(private httpBackend: HttpBackend, private notifierService: NotifierService) {
        this.httpClient = new HttpClient(httpBackend);
    }

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        return next.handle(req).pipe(
            catchError((error: HttpErrorResponse) => {
                let errorMessage: string;
                console.error(error);
                if (error.error instanceof ErrorEvent) {
                    errorMessage = 'Exception.INTERNAL_EXCEPTION'
                } else {
                    errorMessage = error.error.message;
                }
                this.notifierService.notify('error', errorMessage);
                return throwError(errorMessage);
            })
        );
    }
}
