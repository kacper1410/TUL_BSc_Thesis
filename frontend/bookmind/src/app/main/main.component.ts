import { Component, OnInit } from '@angular/core';
import { OnlineStatusService, OnlineStatusType } from "ngx-online-status";

@Component({
    selector: 'app-main',
    templateUrl: './main.component.html',
    styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit {

    ngOnInit(): void {
    }

    online = navigator.onLine;

    constructor(private onlineStatusService: OnlineStatusService) {
        this.onlineStatusService.status.subscribe(
            (status: OnlineStatusType) => this.online = !!status
        );
    }
}
