import { Injectable } from "@angular/core";
import { HttpBackend, HttpClient, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Observable } from "rxjs";
import { AuthService } from "../service/auth.service";


@Injectable()
export class AuthInterceptor implements HttpInterceptor {
    private httpClient: HttpClient;

    constructor(private httpBackend: HttpBackend, private authService: AuthService) {
        this.httpClient = new HttpClient(httpBackend);
    }

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        if (this.authService.isAuth()) {
            const jwt = this.authService.getJwt();
            const authRequest = req.clone({setHeaders: {Authorization: 'Bearer ' + jwt}})
            return next.handle(authRequest);
        }

        return next.handle(req);
    }

}
