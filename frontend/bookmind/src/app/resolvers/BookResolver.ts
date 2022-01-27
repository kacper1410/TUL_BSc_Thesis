import { Injectable } from "@angular/core";
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot } from "@angular/router";
import { Observable } from "rxjs";
import { BookService } from "../service/book.service";


@Injectable({
    providedIn: 'root'
})
export class BookResolver implements Resolve<any> {

    constructor(private bookService: BookService) { }

    resolve(route: ActivatedRouteSnapshot, rstate: RouterStateSnapshot): Observable<any> {
        return this.bookService.getBooks();
    }
}
