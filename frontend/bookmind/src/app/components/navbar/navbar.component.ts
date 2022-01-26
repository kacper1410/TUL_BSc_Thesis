import { Component, OnInit } from '@angular/core';
import { AuthService } from "../../service/auth.service";
import { NotifierService } from "angular-notifier";

@Component({
    selector: 'app-navbar',
    templateUrl: './navbar.component.html',
    styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit {

    constructor(public authService: AuthService, private notifierService: NotifierService) {
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
