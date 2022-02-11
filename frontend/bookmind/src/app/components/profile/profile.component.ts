import { Component, OnInit } from '@angular/core';
import { defaultUser } from "../../domain/default/defaultUser";
import { ActivatedRoute } from "@angular/router";
import { AuthService } from "../../service/auth.service";
import { Authority } from "../../domain/Authority";

@Component({
    selector: 'app-profile',
    templateUrl: './profile.component.html',
    styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {

    public user = defaultUser();

    constructor(private act: ActivatedRoute,
                public authService: AuthService) {
        this.act.data.subscribe(value => {
            this.user = value.user;
        });
    }

    ngOnInit(): void {
    }

    hasAuthority(a: Authority) {
        return a.authority === this.authService.getCurrentAuth();
    }

    setAuthority(a: Authority) {
        this.authService.setCurrentAuth(a.authority);
    }
}
