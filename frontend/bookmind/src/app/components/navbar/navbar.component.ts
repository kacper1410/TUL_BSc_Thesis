import { Component, OnInit } from '@angular/core';
import { AuthService } from "../../service/auth.service";
import { NotifierService } from "angular-notifier";
import { ConnectionService } from "../../service/connection.service";

@Component({
    selector: 'app-navbar',
    templateUrl: './navbar.component.html',
    styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit {

    constructor(public authService: AuthService,
                private notifierService: NotifierService,
                public connection: ConnectionService) {
    }

    ngOnInit(): void {
    }

    logout() {
        this.authService.logout();
    }

    test() {
        this.notifierService.notify('default', 'test message');
    }
}
