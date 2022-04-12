import { Injectable } from '@angular/core';
import { DatabaseService } from "./database.service";
import { ADD_SHELF, REMOVE_SHELF } from "../domain/actionTypes";
import { ShelfService } from "./shelf.service";
import { OnlineStatusService, OnlineStatusType } from "ngx-online-status";
import { AuthService } from "./auth.service";
import { of } from "rxjs";

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
        console.log('Synchronizing...');
        return this.dbService.getActionsForUsername(username).then(
            (actions: any[]) => {
                const grouped = this.groupByShelf(actions);

                for (let shelfId in grouped) {
                    this.syncShelf(Number(shelfId), grouped[shelfId]).then(
                        () => {
                            console.log(`Successful synchronization of shelf with id: ${shelfId}`)
                        }
                    )
                    this.dbService.removeActions(grouped[shelfId]);
                }
            }
        );
    }

    private async syncShelf(shelfId: number, actions: any[]): Promise<any> {
        const groupedByType = this.groupByActionType(actions);
        if (groupedByType[REMOVE_SHELF]) {
            return this.shelfService.deleteShelf(shelfId).toPromise();
        }
        let newId;
        if (groupedByType[ADD_SHELF]) {
            newId = (await this.shelfService.addShelf(groupedByType[ADD_SHELF][0].shelf).toPromise())
                .headers.get('Location')
                .replace('/shelves/me/', '');
        }
        console.log(newId);
        // rest of actions send to sync endpoint
        return of();
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
