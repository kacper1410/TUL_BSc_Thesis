import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { environment } from "../../environments/environment";
import { Observable } from "rxjs";
import { Shelf } from "../domain/Shelf";
import { ConnectionService } from "./connection.service";
import { DatabaseService } from "./database.service";
import { tap } from "rxjs/operators";
import { AuthService } from "./auth.service";

@Injectable({
    providedIn: 'root'
})
export class ShelfService {

    private readonly url: string;

    constructor(private http: HttpClient,
                private connService: ConnectionService,
                private dbService: DatabaseService,
                private authService: AuthService) {
        this.url = environment.url + '/shelves/';
    }

    getMyShelves(): Observable<Shelf[]> {
        const username = this.authService.getUsername();
        return this.connService.getIfOnline(
            () => this.http.get<Shelf[]>(this.url + 'me', {
                observe: 'body',
                responseType: 'json'
            }),
            () => this.dbService.getShelvesByUsername(username)
        ).pipe(
            tap(
                (shelves) => this.dbService.saveShelvesForUsername(shelves, username)
            )
        );
    }

    addShelf(shelf: Shelf): Observable<any> {
        return this.http.post(this.url + 'me', shelf);
    }

    deleteShelf(id: number): Observable<any> {
        return this.http.delete(this.url + `me/${id}`);
    }

    getShelf(id: number): Observable<Shelf> {
        return this.http.get<Shelf>(this.url + `me/${id}`, {
            observe: 'body',
            responseType: 'json'
        });
    }

    removeBookFromShelf(bookId: number, shelfId: number) {
        return this.http.delete(this.url + `me/${shelfId}/book/${bookId}`);
    }

    addBookToShelf(bookId: number, shelfId: number) {
        return this.http.put(this.url + `me/${shelfId}/book/${bookId}`, {});
    }
}