import { Injectable } from "@angular/core";
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot } from "@angular/router";
import { Observable } from "rxjs";
import { BookService } from "../service/book.service";
import { ConnectionService } from "../service/connection.service";
import { DatabaseService } from "../service/database.service";
import { fromPromise } from "rxjs/internal-compatibility";


@Injectable({
    providedIn: 'root'
})
export class BookResolver implements Resolve<any> {

    constructor(private bookService: BookService,
                private connectionService: ConnectionService,
                private databaseService: DatabaseService) { }

    resolve(route: ActivatedRouteSnapshot, rstate: RouterStateSnapshot): Observable<any> {
        if (this.connectionService.isOnline()) {
            return this.bookService.getBooks();
        }
        return fromPromise(this.databaseService.db.books.toArray())
    }
}
