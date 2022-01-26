import { Component } from '@angular/core';
import { TranslateService } from "@ngx-translate/core";

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: [ './app.component.scss' ]
})
export class AppComponent {
    title = 'Bookmind';

    constructor(public translateService: TranslateService) {
        translateService.addLangs(['en', 'pl'])
        translateService.setDefaultLang('en')
        let browserLang = translateService.getBrowserLang();
        browserLang = browserLang? browserLang : '';
        translateService.use(browserLang.match(/en|pl/) ? browserLang: 'en')
    }
}
