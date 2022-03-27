import { Component, OnInit } from '@angular/core';
import { ConnectionService } from "../../service/connection.service";

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

    constructor(public connService: ConnectionService) {
    }

    ngOnInit(): void {
    }
}
