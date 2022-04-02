import { Component, OnInit } from '@angular/core';
import { defaultShelf } from "../../../domain/default/defaultShelf";
import { ActivatedRoute, Router } from "@angular/router";
import { ShelfService } from "../../../service/shelf.service";
import { NotificationService } from "../../../service/notification.service";

@Component({
    selector: 'app-shelf-edit',
    templateUrl: './shelf-edit.component.html',
    styleUrls: ['./shelf-edit.component.scss']
})
export class ShelfEditComponent implements OnInit {

    public shelf = defaultShelf();

    constructor(private act: ActivatedRoute,
                private shelfService: ShelfService,
                private router: Router,
                private notify: NotificationService) {
        this.act.data.subscribe(value => {
            this.shelf = value.shelf;
        });
    }

    ngOnInit(): void {
    }

    updateShelf() {
        this.shelfService.updateShelf(this.shelf, this.shelf.id).subscribe(
            () => {
                this.notify.success('Success.shelf.update');
                this.router.navigateByUrl(`/shelves/details/${this.shelf.id}`);
            }
        )
    }
}
