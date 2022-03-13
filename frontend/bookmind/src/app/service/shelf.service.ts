import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { environment } from "../../environments/environment";
import { Observable } from "rxjs";
import { Shelf } from "../domain/Shelf";

@Injectable({
    providedIn: 'root'
})
export class ShelfService {

    private readonly url: string;

    constructor(private http: HttpClient) {
        this.url = environment.url + '/shelves/';
    }

    getMyShelves(): Observable<Shelf[]> {
        return this.http.get<Shelf[]>(this.url + 'me', {
            observe: 'body',
            responseType: 'json'
        });
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
