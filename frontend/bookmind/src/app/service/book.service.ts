import { Injectable } from '@angular/core';
import { Observable } from "rxjs";
import { Book } from "../domain/Book";
import { HttpClient } from "@angular/common/http";

@Injectable({
    providedIn: 'root'
})
export class BookService {

    private readonly url: string;

    constructor(private http: HttpClient) {
        this.url = 'http://localhost:8080/books/';
    }

    getBooks(): Observable<Book[]> {
        return this.http.get<Book[]>(this.url, {
            observe: 'body',
            responseType: 'json'
        });
    }

    addBook(book: any): Observable<any> {
        return this.http.post(this.url, book);
    }

    remove(id: number):  Observable<any> {
        return this.http.delete(this.url + id);
    }
}
