import { Injectable } from "@angular/core";
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot } from "@angular/router";
import { Observable } from "rxjs";
import { BookService } from "../service/book.service";


@Injectable({
    providedIn: 'root'
})
export class BookDetailsResolver implements Resolve<any> {

    constructor(private bookService: BookService) {
    }

    resolve(route: ActivatedRouteSnapshot, rstate: RouterStateSnapshot): Observable<any> {
        const pathId = parseInt(route.params.id, 10);
        return this.bookService.getBookForEdit(pathId);
    }
}