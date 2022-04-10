import { Injectable } from '@angular/core';
import { DatabaseService } from "./database.service";
import { ADD_SHELF, REMOVE_SHELF } from "../domain/actionTypes";
import { ShelfService } from "./shelf.service";
import { OnlineStatusService, OnlineStatusType } from "ngx-online-status";
import { AuthService } from "./auth.service";

@Injectable({
    providedIn: 'root'
})
export class SyncService {

    constructor(private onlineStatusService: OnlineStatusService,
                private dbService: DatabaseService,
                private shelfService: ShelfService) {
    }

    public registerSubscriber(authService: AuthService) {
        this.onlineStatusService.status.subscribe(
            (status: OnlineStatusType) => {
                if (status && authService.isLoggedIn()) {
                    this.synchronize(authService.getUsername());
                }
            }
        );
    }

    public async synchronize(username: string) {
        return this.dbService.getActionsForUsername(username).then(
            (actions: any[]) => {
                const grouped = this.groupByShelf(actions);
                console.log('SYNCING');
                console.log(grouped);

                for (let shelfId in grouped) {
                    // this.syncShelf(Number(shelfId), grouped[shelfId]);
                    // remove synced actions
                }
            }
        );
    }

    private syncShelf(shelfId: number, actions: any[]) {
        const grouped = this.groupByActionType(actions);
        if (grouped[REMOVE_SHELF]) {
            this.shelfService.deleteShelf(shelfId);
            // end sync
        }
        if (grouped[ADD_SHELF]) {
            // send add shelf req
            // get new id from backend
        }
        // rest of actions send to sync endpoint
    }

    private groupByShelf(actions: any[]) {
        return this.groupByKey(actions, 'shelfId');
    }

    private groupByActionType(actions: any[]) {
        return this.groupByKey(actions, 'shelfActionType');
    }

    private groupByKey(actions: any[], key: string) {
        return actions.reduce((result: any[], action: any) => {
            (result[action[key]] = result[action[key]] || []).push(action);
            return result;
        }, {});
    }
}
