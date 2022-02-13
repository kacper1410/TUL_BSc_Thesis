import { Injectable } from "@angular/core";
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot } from "@angular/router";
import { Observable } from "rxjs";
import { fromPromise } from "rxjs/internal-compatibility";
import { UserService } from "../service/user.service";


@Injectable({
    providedIn: 'root'
})
export class UserResolver implements Resolve<any> {

    constructor(private userService: UserService) {
    }

    resolve(route: ActivatedRouteSnapshot, rstate: RouterStateSnapshot): Observable<any> {
        return this.userService.getUsers();
    }
}
