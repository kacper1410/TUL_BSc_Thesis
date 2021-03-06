import { Injectable } from '@angular/core';
import { DatabaseService } from "./database.service";
import { ADD_BOOK, ADD_SHELF, REMOVE_BOOK, REMOVE_SHELF, UPDATE } from "../domain/actionTypes";
import { ShelfService } from "./shelf.service";
import { OnlineStatusService, OnlineStatusType } from "ngx-online-status";
import { AuthService } from "./auth.service";
import { Observable, of } from "rxjs";
import { HttpClient } from "@angular/common/http";
import { environment } from "../../environments/environment";
import { SyncErrorModalComponent } from "../components/sync-error-modal/sync-error-modal.component";
import { NgbModal } from "@ng-bootstrap/ng-bootstrap";

@Injectable({
    providedIn: 'root'
})
export class SyncService {

    private readonly url: string;

    constructor(private onlineStatusService: OnlineStatusService,
                private dbService: DatabaseService,
                private shelfService: ShelfService,
                private http: HttpClient,
                private modalService: NgbModal) {
        this.url = environment.url + '/sync/';
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
                const errors: any = {};
                const syncPromises = [];

                for (let shelfId in grouped) {
                    const syncPromise = this.syncShelf(Number(shelfId), grouped[shelfId])
                        .then(
                            () => {
                                console.log(`Successful synchronization of shelf with id: ${shelfId}`);
                                this.dbService.removeActions(grouped[shelfId]);
                            },
                            (response) => {
                                if (response.status === 409) {
                                    console.warn(`Some actions could not be synchronized for shelf with id ${shelfId}`);
                                    errors[Number(shelfId)] = (response.error);
                                    this.dbService.removeActions(grouped[shelfId]);
                                }
                            }
                        );
                    syncPromises.push(syncPromise);
                }
                Promise.all(syncPromises)
                    .then(() => {
                        if (Object.keys(errors).length !== 0) {
                            const ngbModalRef = this.modalService.open(SyncErrorModalComponent, {backdrop: 'static', keyboard: false});
                            ngbModalRef.componentInstance.initialize(errors);
                        }
                    })
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
        const syncActions: any[] = [];
        syncActions.push(...(groupedByType[UPDATE] ? groupedByType[UPDATE] : []));
        syncActions.push(...(groupedByType[ADD_BOOK] ? groupedByType[ADD_BOOK] : []));
        syncActions.push(...(groupedByType[REMOVE_BOOK] ? groupedByType[REMOVE_BOOK] : []));
        return this.sendSyncShelfRequest(newId ? newId : shelfId, syncActions).toPromise();
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

    private sendSyncShelfRequest(shelfId: string, actions: any[]): Observable<any> {
        if (actions)
            return this.http.post(this.url + shelfId, actions, {observe: 'response'});
        return of();
    }
}
