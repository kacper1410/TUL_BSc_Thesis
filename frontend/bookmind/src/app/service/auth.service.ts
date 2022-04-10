import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { Credentials } from "../domain/Credentials";
import { environment } from "../../environments/environment";
import { AuthResponse } from "../domain/AuthResponse";
import { CookieService } from "ngx-cookie-service";
import jwtDecode from "jwt-decode";
import { JwtDecoded } from "../domain/JwtDecoded";
import { Router } from "@angular/router";
import { NotificationService } from "./notification.service";
import { DatabaseService } from "./database.service";
import { Authority } from "../domain/Authority";
import { Observable } from "rxjs";
import { tap } from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

    private readonly url: string;

    constructor(private http: HttpClient,
                private cookieService: CookieService,
                private router: Router,
                private notify: NotificationService,
                private dbService: DatabaseService) {
        this.url = environment.url + '/auth/';
    }

    login(credentials: Credentials): Observable<any> {
        return this.http.post<AuthResponse>(this.url, credentials).pipe(
            tap((response) => {
                this.decodeAuthResponse(response);
                this.dbService.saveUser(this.getLoggedInUser());
                this.notify.success('Success.login');
                this.router.navigateByUrl("/home");
            })
        );
    }

    private decodeAuthResponse(response: AuthResponse): void {
        this.cookieService.set('jwt', response.jwtToken);
        let decoded = jwtDecode<JwtDecoded>(response.jwtToken);
        this.cookieService.set('username', decoded.sub);
        this.cookieService.set('expires', String(decoded.exp));
        this.cookieService.set('authority', String(decoded.authorities.split(";")[0]));
        this.cookieService.set('authorities', String(decoded.authorities));
    }

    loginOffline(username: string) {
        this.cookieService.set('jwt', 'offline');
        this.dbService.getUser(username).subscribe(
            (user) => {
                this.cookieService.set('username', user.username);
                this.cookieService.set('expires', String((new Date().getTime() / 1000 + 900)) );
                this.cookieService.set('authority', user.authorities[0].authority);
                this.cookieService.set('authorities', user.authorities.map(a => a.authority).join(';'));
                this.router.navigateByUrl("/home");
            }
        );
    }

    isAuth(): boolean {
        return this.cookieService.get('jwt').length > 0 && !this.hasTokenExpired();
    }

    private hasTokenExpired() {
        return parseInt(this.cookieService.get('expires'), 10) * 1000 - new Date().getTime() < 0;
    }

    logout() {
        this.cookieService.deleteAll();
    }

    getJwt(): string {
        return this.cookieService.get('jwt');
    }

    isAdmin(): boolean {
        return this.isAuth() && this.cookieService.get('authority') === 'ROLE_ADMIN';
    }

    isModerator(): boolean {
        return this.isAuth() && this.cookieService.get('authority') === 'ROLE_MODERATOR';
    }

    isReader(): boolean {
        return this.isAuth() && this.cookieService.get('authority') === 'ROLE_READER';
    }

    getUsername(): string {
        return this.cookieService.get('username');
    }

    getCurrentAuth(): string {
        return this.cookieService.get('authority');
    }

    setCurrentAuth(authority: string) {
        this.cookieService.set('authority', authority);
    }

    private getLoggedInUser() {
        const auths: Authority[] = this.cookieService.get('authorities')
            .split(';')
            .map(auth => {
                return {
                    authority: auth
                }
            })
        return {
            username: this.getUsername(),
            authorities: auths
        }
    }

    public isLoggedIn(): boolean {
        return this.isAuth() && this.getJwt() !== 'offline';
    }
}
