import { Component, OnInit } from '@angular/core';
import { defaultUser } from "../../domain/default/defaultUser";
import { ActivatedRoute } from "@angular/router";

@Component({
    selector: 'app-profile',
    templateUrl: './profile.component.html',
    styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {

    public user = defaultUser();

    constructor(private act: ActivatedRoute,) {
        this.act.data.subscribe(value => {
            this.user = value.user;
        });
    }

    ngOnInit(): void {
    }

}
