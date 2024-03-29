import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { environment } from "../../environments/environment";
import { Observable } from "rxjs";
import { Shelf } from "../domain/Shelf";
import { ConnectionService } from "./connection.service";
import { DatabaseService } from "./database.service";
import { mergeMap, tap } from "rxjs/operators";
import { AuthService } from "./auth.service";
import { Book } from "../domain/Book";

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
        return this.connService.getIfOnline(
            () => this.http.post(this.url + 'me', shelf, {observe: 'response'}),
            () => this.dbService.saveNewShelf(shelf, this.authService.getUsername())
        )
    }

    deleteShelf(id: number): Observable<any> {
        return this.connService.getIfOnline(
            () => this.http.delete(this.url + `me/${id}`, {observe: 'response'}),
            () => this.dbService.deleteShelf(id, this.authService.getUsername())
        );
    }

    getShelf(id: number): Observable<Shelf> {
        const username = this.authService.getUsername();
        return this.connService.getIfOnline(
            () => this.http.get<Shelf>(this.url + `me/${id}`, {
                observe: 'body',
                responseType: 'json'
            }),
            () => this.dbService.getShelfByUsername(id, username)
        ).pipe(
            tap(
                (shelf) => this.dbService.saveShelfForUsername(shelf, username)
            )
        );
    }

    removeBookFromShelf(book: Book, shelf: Shelf) {
        const username = this.authService.getUsername();
        return this.connService.getIfOnline(
            () => this.http.delete(this.url + `me/${shelf.id}/book/${book.id}`),
            () => this.dbService.removeBookFromShelfOffline(shelf, book, username)
        ).pipe(
            mergeMap(
                () => this.dbService.removeBookFromShelf(shelf.id, book.id, username)
            )
        );
    }

    addBookToShelf(book: Book, shelf: Shelf) {
        const username = this.authService.getUsername();
        return this.connService.getIfOnline(
            () => this.http.put(this.url + `me/${shelf.id}/book/${book.id}`, {}),
            () => this.dbService.addBookToShelfOffline(shelf, book, username)
        ).pipe(
            mergeMap(
                () => this.dbService.addBookToShelf(shelf.id, book.id, username)
            )
        );
    }

    updateShelf(shelf: Shelf, id: number) {
        return this.connService.getIfOnline(
            () => this.http.put(this.url + 'me/' + id, shelf),
            () => this.dbService.modifyShelf(id, shelf, this.authService.getUsername())
        );
    }
}
