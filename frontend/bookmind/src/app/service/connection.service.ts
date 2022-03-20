import { Injectable } from '@angular/core';
import { OnlineStatusService, OnlineStatusType } from "ngx-online-status";
import { Observable } from "rxjs";
import { catchError, mergeMap } from "rxjs/operators";
import { HttpClient } from "@angular/common/http";
import { environment } from "../../environments/environment";

@Injectable({
    providedIn: 'root'
})
export class ConnectionService {

    private online = navigator.onLine;
    private readonly url: string;

    constructor(private onlineStatusService: OnlineStatusService,
                private http: HttpClient) {
        this.url = environment.url;
        this.onlineStatusService.status.subscribe(
            (status: OnlineStatusType) => this.online = !!status
        );
    }

    isOnline(): boolean {
        return this.online;
    }

    public getIfOnline<T>(onlineFun: () => Observable<T>, offlineFun: () => Observable<T>): Observable<T> {
        if (this.online) {
            return this.checkConnection().pipe(
                mergeMap(
                    () => onlineFun()
                ),
                catchError(
                    () => offlineFun()
                )
            )
        }
        return offlineFun();
    }

    private checkConnection(): Observable<any> {
        return this.http.get(this.url + '/connection');
    }
}
