import { Component } from '@angular/core';
import { TranslateService } from "@ngx-translate/core";
import { SyncService } from "./service/sync.service";
import { AuthService } from "./service/auth.service";

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: [ './app.component.scss' ]
})
export class AppComponent {
    title = 'Bookmind';

    constructor(public translateService: TranslateService,
                private syncService: SyncService,
                private authService: AuthService) {
        this.configTranslations();
        this.registerSyncSubscriber()
    }

    private configTranslations() {
        this.translateService.addLangs(['en', 'pl'])
        this.translateService.setDefaultLang('en')
        let browserLang = this.translateService.getBrowserLang();
        browserLang = browserLang ? browserLang : '';
        this.translateService.use(browserLang.match(/en|pl/) ? browserLang : 'en')
    }

    private registerSyncSubscriber() {
        this.syncService.registerSubscriber(this.authService);
    }
}
