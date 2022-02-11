import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { User } from "../domain/User";

@Injectable({
    providedIn: 'root'
})
export class UserService {

    private readonly url: string;

    constructor(private http: HttpClient) {
        this.url = 'http://localhost:8080/users/';
    }

    getProfile(): Observable<User> {
        return this.http.get<User>(this.url + 'profile', {
            observe: 'body',
            responseType: 'json'
        });
    }
}
