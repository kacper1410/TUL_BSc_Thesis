import { Component, OnInit } from '@angular/core';
import { User } from "../../../domain/User";
import { UserService } from "../../../service/user.service";
import { AuthService } from "../../../service/auth.service";

@Component({
    selector: 'app-offline-login',
    templateUrl: './offline-login.component.html',
    styleUrls: ['./offline-login.component.scss']
})
export class OfflineLoginComponent implements OnInit {
    users: User[] = [];

    constructor(private userService: UserService,
                private authService: AuthService) {
        this.userService.getSavedUsers().subscribe(
            (users) => this.users = users
        );
    }

    ngOnInit(): void {
    }

    login(username: string) {
        this.authService.loginOffline(username);
    }

    removeUser(id: number) {

    }
}
