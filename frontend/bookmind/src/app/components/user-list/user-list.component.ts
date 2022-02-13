import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from "@angular/router";
import { User } from "../../domain/User";
import { UserService } from "../../service/user.service";

@Component({
    selector: 'app-user-list',
    templateUrl: './user-list.component.html',
    styleUrls: ['./user-list.component.scss']
})
export class UserListComponent implements OnInit {

    public users: Array<User> = []

    constructor(private act: ActivatedRoute,
                private userService: UserService) {
        this.act.data.subscribe(value => {
            this.users = value.users;
        });
    }

    ngOnInit(): void {
    }

    getUsers() {
        this.userService.getUsers().subscribe(
            (users) => this.users = users
        )
    }

    disable(id: number) {
        this.userService.disable(id).subscribe(
            () => this.getUsers()
        )
    }

    enable(id: number) {
        this.userService.enable(id).subscribe(
            () => this.getUsers()
        )
    }

    removeAuth(id: number, auth: string) {
        this.userService.removeAuth(id, {authority: auth}).subscribe(
            () => this.getUsers()
        )
    }

    addAuth(id: number, auth: string) {
        this.userService.addAuth(id, {authority: auth}).subscribe(
            () => this.getUsers()
        )
    }

    isAdmin(user: User): boolean {
        return user.authorities.some(auth => auth.authority === 'ROLE_ADMIN');
    }

    isModerator(user: User) {
        return user.authorities.some(auth => auth.authority === 'ROLE_MODERATOR');
    }

    isReader(user: User) {
        return user.authorities.some(auth => auth.authority === 'ROLE_READER');
    }
}
