import { Injectable } from '@angular/core';
import { NotifierService } from "angular-notifier";
import { TranslateService } from "@ngx-translate/core";

@Injectable({
    providedIn: 'root'
})
export class NotificationService {

    constructor(private notifierService: NotifierService, private translate: TranslateService) {
    }

    success(key: string) {
        const message = this.translate.instant(key);
        this.notifierService.notify('success', message);
    }

    error(key: string) {
        const message = this.translate.instant(key);
        this.notifierService.notify('error', message);
    }
}
