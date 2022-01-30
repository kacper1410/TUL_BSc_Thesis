import { Injectable } from '@angular/core';
import { OnlineStatusService, OnlineStatusType } from "ngx-online-status";

@Injectable({
    providedIn: 'root'
})
export class ConnectionService {

    private online = navigator.onLine;

    constructor(private onlineStatusService: OnlineStatusService) {
        this.onlineStatusService.status.subscribe(
            (status: OnlineStatusType) => this.online = !!status
        );
    }

    isOnline(): boolean {
        return this.online;
    }
}
