import { Component, OnInit } from '@angular/core';
import { defaultUser } from "../../../domain/default/defaultUser";
import { UserService } from "../../../service/user.service";
import { NotificationService } from "../../../service/notification.service";
import { Router } from "@angular/router";

@Component({
    selector: 'app-register-form',
    templateUrl: './register-form.component.html',
    styleUrls: ['./register-form.component.scss']
})
export class RegisterFormComponent implements OnInit {

    user = defaultUser();

    constructor(private userService: UserService,
                private notify: NotificationService,
                private router: Router) {
    }

    ngOnInit(): void {
    }

    register() {
        this.userService.registerUser(this.user).subscribe(
            () => {
                this.notify.success("Success.register.form-sent");
                this.router.navigateByUrl('/home');
            }
        )
    }
}
