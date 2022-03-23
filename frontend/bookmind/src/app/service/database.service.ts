import { Injectable } from '@angular/core';
import Dexie from "dexie";
import { Book } from "../domain/Book";
import { Observable } from "rxjs";
import { fromPromise } from "rxjs/internal-compatibility";
import { Shelf } from "../domain/Shelf";

@Injectable({
    providedIn: 'root'
})
export class DatabaseService {

    db: any;

    constructor() {
        this.db = new Dexie("BookmindDatabase");
        this.db.version(1).stores({
            books: "++id,title,author",
            shelves: "++id,name,username"
        });
    }

    saveBooks(books: Book[]) {
        books.forEach(
            (book) => this.db.books.put(book)
        );
    }

    getBooks(): Observable<Book[]> {
        return fromPromise(this.db.books.toArray());
    }

    getShelvesByUsername(username: string): Observable<Shelf[]> {
        return fromPromise(
            this.db.shelves
                .where('username').equals(username)
                .toArray()
        );
    }

    saveShelvesForUsername(shelves: Shelf[], username: string) {
        shelves.forEach(
            (shelf) => {
                shelf.username = username;
                this.db.shelves.put(shelf);
            }
        );
    }
}
