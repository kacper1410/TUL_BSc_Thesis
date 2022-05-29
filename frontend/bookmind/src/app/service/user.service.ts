import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { User } from "../domain/User";
import { environment } from "../../environments/environment";
import { Authority } from "../domain/Authority";
import { tap } from "rxjs/operators";
import { DatabaseService } from "./database.service";
import { ConnectionService } from "./connection.service";
import { AuthService } from "./auth.service";

@Injectable({
    providedIn: 'root'
})
export class UserService {

    private readonly url: string;

    constructor(private http: HttpClient,
                private dbService: DatabaseService,
                private connService: ConnectionService,
                private authService: AuthService) {
        this.url = environment.url + '/users/';
    }

    getProfile(): Observable<User> {
        return this.connService.getIfOnline(
            () => this.http.get<User>(this.url + 'profile', {
                observe: 'body',
                responseType: 'json'
            }),
            () => this.dbService.getUser(this.authService.getUsername())
        ).pipe(
            tap(
                (user) => this.dbService.saveUser(user)
            )
        );
    }

    getUsers(): Observable<User[]> {
        return this.http.get<User[]>(this.url, {
            observe: 'body',
            responseType: 'json'
        });
    }

    disable(id: number): Observable<any> {
        return this.http.put(this.url + `disable/${id}`, {});
    }

    enable(id: number): Observable<any> {
        return this.http.put(this.url + `enable/${id}`, {});
    }

    removeAuth(id: number, auth: Authority) {
        return this.http.delete(this.url + `accessLevel/${id}`, {
            body: auth
        });
    }

    addAuth(id: number, auth: Authority) {
        return this.http.put(this.url + `accessLevel/${id}`, auth);
    }

    getSavedUsers() {
        return this.dbService.getSavedUsers();
    }

    registerUser(user: User): Observable<any> {
        return this.http.post(this.url + 'register', user);
    }

    confirmUser(code: any): Observable<any> {
        return this.http.post(`${this.url}confirm/${code}`, {});
    }
}
