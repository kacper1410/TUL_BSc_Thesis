import { Injectable } from "@angular/core";
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot } from "@angular/router";
import { Observable } from "rxjs";
import { ShelfService } from "../service/shelf.service";

@Injectable({
    providedIn: 'root'
})
export class ShelfResolver implements Resolve<any> {

    constructor(private shelfService: ShelfService) {
    }

    resolve(route: ActivatedRouteSnapshot, rstate: RouterStateSnapshot): Observable<any> {
        return this.shelfService.getMyShelves();
    }
}
