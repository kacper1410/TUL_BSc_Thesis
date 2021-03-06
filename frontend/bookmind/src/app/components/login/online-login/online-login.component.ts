import { Component, OnInit } from '@angular/core';
import { defaultCredentials } from "../../../domain/default/defaultCredentials";
import { AuthService } from "../../../service/auth.service";
import { SyncService } from "../../../service/sync.service";

@Component({
    selector: 'app-online-login',
    templateUrl: './online-login.component.html',
    styleUrls: ['./online-login.component.scss']
})
export class OnlineLoginComponent implements OnInit {


    credentials = defaultCredentials();

    constructor(private authService: AuthService,
                private syncService: SyncService) {
    }

    ngOnInit(): void {
    }

    login(): void {
        this.authService.login(this.credentials).subscribe(
            () => this.syncService.synchronize(this.authService.getUsername())
        );
    }
}
