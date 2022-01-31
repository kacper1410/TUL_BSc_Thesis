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
export class BookDetailsResolver implements Resolve<any> {

    constructor(private bookService: BookService,
                private connectionService: ConnectionService,
                private databaseService: DatabaseService) { }

    resolve(route: ActivatedRouteSnapshot, rstate: RouterStateSnapshot): Observable<any> {
        const pathId = parseInt(route.params.id, 10);
        if (this.connectionService.isOnline()) {
            return this.bookService.getBook(pathId);
        }
        return fromPromise(this.databaseService.db.books.where('id').equals(pathId).first());
    }
}
