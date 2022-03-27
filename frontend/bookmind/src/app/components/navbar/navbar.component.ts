import { Component, OnInit } from '@angular/core';
import { AuthService } from "../../service/auth.service";
import { ConnectionService } from "../../service/connection.service";

@Component({
    selector: 'app-navbar',
    templateUrl: './navbar.component.html',
    styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit {

    constructor(public authService: AuthService,
                public connection: ConnectionService) {
    }

    ngOnInit(): void {
    }

    logout() {
        this.authService.logout();
    }
}
