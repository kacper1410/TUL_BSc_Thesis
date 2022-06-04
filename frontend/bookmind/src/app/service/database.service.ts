import { Injectable } from '@angular/core';
import Dexie from "dexie";
import { Book } from "../domain/Book";
import { Observable } from "rxjs";
import { fromPromise } from "rxjs/internal-compatibility";
import { Shelf } from "../domain/Shelf";
import { User } from "../domain/User";
import { ADD_BOOK, ADD_SHELF, REMOVE_BOOK, REMOVE_SHELF, UPDATE } from "../domain/actionTypes";

@Injectable({
    providedIn: 'root'
})
export class DatabaseService {

    db: any;

    constructor() {
        this.db = new Dexie("BookmindDatabase");
        this.db.version(1).stores({
            books: "++id",
            shelves: "++id,username",
            users: "username",
            actions: "++id, shelfActionType, username"
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
        this.db.shelves
            .where('id').noneOf(shelves.map(s => s.id))
            .and((shelf: Shelf) => shelf.username === username)
            .delete()
            .then(
                () => shelves.forEach(
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
                )
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

    saveNewShelf(newShelf: Shelf, username: string): Observable<any> {
        newShelf.username = username;
        newShelf.new = true;
        const shelfCopy: any = newShelf;
        delete shelfCopy.id;
        const promise = this.db.shelves.add(shelfCopy)
            .then(
                (id: number) => {
                    shelfCopy.id = id;
                    return this.db.actions.put({
                        shelfActionType: ADD_SHELF,
                        actionDate: new Date(),
                        username: username,
                        shelf: shelfCopy,
                        shelfId: shelfCopy.id
                    });
                }
            );

        return fromPromise(promise);
    }

    deleteShelf(id: number, username: string): Observable<any> {
        const promise = this.db.shelves
            .where('id').equals(id)
            .and((shelf: Shelf) => shelf.username === username)
            .first()
            .then(
                (shelf: Shelf) => {
                    let promiseSync;
                    if (shelf.new) {
                        promiseSync = this.db.actions
                            .where('shelfActionType').equals(ADD_SHELF)
                            .and((action: any) => action.shelf.id === id)
                            .delete()
                    } else {
                        promiseSync = this.db.actions.put({
                            shelfActionType: REMOVE_SHELF,
                            actionDate: new Date(),
                            username: username,
                            shelf: shelf,
                            shelfId: shelf.id
                        });
                    }

                    const promiseDelete = this.db.shelves
                        .where('id').equals(id)
                        .delete();
                    return fromPromise(Promise.all([promiseSync, promiseDelete]));
                }
            )
        return fromPromise(promise);
    }

    getBookWithShelves(id: number, username: string): Observable<Book> {
        const promiseBook = this.db.books
            .where('id').equals(id)
            .first();
        const promiseShelves = this.db.shelves
            .where('username').equals(username)
            .and((shelf: Shelf) => shelf.books.some(
                (book: Book) => book.id === id)
            )
            .toArray();
        const promise = Promise.all([promiseBook, promiseShelves])
            .then((values: any) => {
                values[0].shelves = values[1];
                return values[0];
            })
        return fromPromise(promise);
    }

    removeBookFromShelfOffline(shelfId: number, bookId: number, username: string): Observable<any> {
        const promiseDelete = this.db.actions
            .where('shelfActionType').equals(ADD_BOOK)
            .and((action: any) => action.shelfId === shelfId && action.bookId === bookId)
            .delete();
        const promisePut = this.db.actions.put({
            shelfActionType: REMOVE_BOOK,
            actionDate: new Date(),
            username: username,
            shelfId: shelfId,
            bookId: bookId
        });
        return fromPromise(Promise.all([promisePut, promiseDelete]));
    }

    removeBookFromShelf(shelfId: number, bookId: number, username: string): Observable<any> {
        return this.db.shelves
            .where('id').equals(shelfId)
            .and((shelf: Shelf) => shelf.username === username)
            .modify(
                (shelf: Shelf) => {
                    if (!shelf.books) shelf.books = []
                    const bookIndex = shelf.books.findIndex((book: Book) => book.id === bookId);
                    if (bookIndex >= 0) {
                        shelf.books.splice(bookIndex, 1);
                    }
                }
            )
    }

    addBookToShelfOffline(shelfId: number, bookId: number, username: string) {
        const promiseDelete = this.db.actions
            .where('shelfActionType').equals(REMOVE_BOOK)
            .and((action: any) => action.shelfId === shelfId && action.bookId === bookId)
            .delete();
        const promisePut = this.db.actions.put({
            shelfActionType: ADD_BOOK,
            actionDate: new Date(),
            username: username,
            shelfId: shelfId,
            bookId: bookId
        });
        return fromPromise(Promise.all([promisePut, promiseDelete]));
    }

    async addBookToShelf(shelfId: number, bookId: number, username: string) {
        const book = await this.db.books
            .where('id').equals(bookId)
            .first();
        return this.db.shelves
            .where('id').equals(shelfId)
            .and((shelf: Shelf) => shelf.username === username)
            .modify((shelf: Shelf) => {
                if (!shelf.books) shelf.books = []
                shelf.books.push(book)
            });
    }

    modifyShelf(id: any, newShelf: Shelf, username: string) {
        const modifyPromise = this.db.shelves
            .where('id').equals(id)
            .and((shelf: Shelf) => shelf.username === username)
            .modify(
                (shelf: Shelf) => {
                    shelf.name = newShelf.name
                }
            )

        const actionPromise = this.db.actions
            .where('shelfActionType').equals(UPDATE)
            .and((action: any) => action.shelf.id === id)
            .modify((action: any) => {
                action.actionDate = new Date();
                action.shelf = newShelf;
            })
            .then(
                (modified: number) => {
                    if (modified > 0)
                        return;

                    return this.db.actions.put({
                        shelfActionType: UPDATE,
                        actionDate: new Date(),
                        username: username,
                        shelf: newShelf,
                        shelfId: newShelf.id
                    });
                }
            )

        return fromPromise(Promise.all([modifyPromise, actionPromise]));
    }

    getActionsForUsername(username: string) {
        return this.db.actions
            .where('username').equals(username)
            .toArray();
    }

    removeActions(actions: any[]) {
        for (const action of actions) {
            this.db.actions.delete(action.id);
        }
    }
}
