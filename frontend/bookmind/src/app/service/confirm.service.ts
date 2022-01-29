import { Injectable } from '@angular/core';
import { NgxBootstrapConfirmService } from "ngx-bootstrap-confirm";

@Injectable({
    providedIn: 'root'
})
export class ConfirmService {

    constructor(private ngxBootstrapConfirmService: NgxBootstrapConfirmService) {
    }

    confirm(message: string, confirmFn: Function): void {
        const options = {
            title: message,
            confirmLabel: 'Okay',
            declineLabel: 'Cancel'
        }
        this.ngxBootstrapConfirmService.confirm(options).then((res: boolean) => {
            if (res) {
                confirmFn()
            }
        });
    }
}
