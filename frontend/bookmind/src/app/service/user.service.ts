import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { User } from "../domain/User";
import { environment } from "../../environments/environment";

@Injectable({
    providedIn: 'root'
})
export class UserService {

    private readonly url: string;

    constructor(private http: HttpClient) {
        this.url = environment.url + '/users/';
    }

    getProfile(): Observable<User> {
        return this.http.get<User>(this.url + 'profile', {
            observe: 'body',
            responseType: 'json'
        });
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
}
