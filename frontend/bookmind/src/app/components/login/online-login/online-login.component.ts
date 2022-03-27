import { Component, OnInit } from '@angular/core';
import { defaultCredentials } from "../../../domain/default/defaultCredentials";
import { AuthService } from "../../../service/auth.service";

@Component({
    selector: 'app-online-login',
    templateUrl: './online-login.component.html',
    styleUrls: ['./online-login.component.scss']
})
export class OnlineLoginComponent implements OnInit {


    credentials = defaultCredentials();

    constructor(private authService: AuthService) {
    }

    ngOnInit(): void {
    }

    login(): void {
        this.authService.login(this.credentials);
    }
}
