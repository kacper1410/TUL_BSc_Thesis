import { Component, OnInit } from '@angular/core';
import { OnlineStatusService, OnlineStatusType } from "ngx-online-status";
import { ConnectionService } from "../../service/connection.service";

@Component({
    selector: 'app-main',
    templateUrl: './main.component.html',
    styleUrls: ['./main.component.scss']
})
export class MainComponent implements OnInit {

    ngOnInit(): void {
    }

    constructor(public connection: ConnectionService){
    }
}
