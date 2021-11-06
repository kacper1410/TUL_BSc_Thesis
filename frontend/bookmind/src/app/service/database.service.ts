import { Injectable } from '@angular/core';
import Dexie from "dexie";

@Injectable({
    providedIn: 'root'
})
export class DatabaseService {

    db: any;

    constructor() {
        this.db = new Dexie("BookmindDatabase");
        this.db.version(1).stores({
            books: "++id,title"
        })
    }

}
