import { Component, OnInit } from '@angular/core';
import { AuthService } from "../../service/auth.service";
import { defaultCredentials } from "../../domain/default/defaultCredentials";

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

    credentials = defaultCredentials();

    constructor(private authService: AuthService) {
    }

    ngOnInit(): void {
    }

    login(): void {
        this.authService.login(this.credentials);
    }
}
