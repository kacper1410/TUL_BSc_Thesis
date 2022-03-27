import { Injectable } from '@angular/core';
import Dexie from "dexie";
import { Book } from "../domain/Book";
import { Observable } from "rxjs";
import { fromPromise } from "rxjs/internal-compatibility";
import { Shelf } from "../domain/Shelf";
import { User } from "../domain/User";

@Injectable({
    providedIn: 'root'
})
export class DatabaseService {

    db: any;

    constructor() {
        this.db = new Dexie("BookmindDatabase");
        this.db.version(1).stores({
            books: "++id",
            shelves: "++id,username,*books",
            users: "username"
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

    saveShelvesForUsername(shelves: Shelf[], username: string) {
        shelves.forEach(
            (newShelf) => {
                this.getShelfByUsername(newShelf.id, username).subscribe(
                    (shelf) => {
                        if (shelf) {
                            this.db.shelves.update(newShelf.id, newShelf);
                        } else {
                            newShelf.username = username;
                            this.db.shelves.put(newShelf);
                        }
                    }
                )
            }
        );
    }

    getShelvesByUsername(username: string): Observable<Shelf[]> {
        return fromPromise(
            this.db.shelves
                .where('username').equals(username)
                .toArray()
        );
    }

    saveShelfForUsername(shelf: Shelf, username: string) {
        shelf.username = username;
        this.db.shelves.put(shelf);
    }

    getShelfByUsername(id: number, username: string): Observable<Shelf> {
        return fromPromise(
            this.db.shelves
                .where('id').equals(id)
                .and((shelf: Shelf) => shelf.username === username)
                .first()
        );
    }

    saveUser(newUser: any) {
        this.getUser(newUser.username).subscribe(
            (user) => {
                if (user) {
                    this.db.users.update(newUser.username, newUser);
                } else {
                    this.db.users.put(newUser);
                }
            }
        )
    }

    getUser(username: string): Observable<User> {
        return fromPromise(
            this.db.users
                .where('username').equals(username)
                .first()
        );
    }

    getSavedUsers(): Observable<User[]> {
        return fromPromise(
            this.db.users.toArray()
        );
    }
}
