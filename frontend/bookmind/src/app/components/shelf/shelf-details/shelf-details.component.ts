import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from "@angular/router";
import { defaultShelf } from "../../../domain/default/defaultShelf";
import { ShelfService } from "../../../service/shelf.service";
import { ConfirmService } from "../../../service/confirm.service";

@Component({
    selector: 'app-shelf-details',
    templateUrl: './shelf-details.component.html',
    styleUrls: ['./shelf-details.component.scss']
})
export class ShelfDetailsComponent implements OnInit {

    public shelf = defaultShelf();

    constructor(private act: ActivatedRoute,
                private confirm: ConfirmService,
                private shelfService: ShelfService) {
        this.act.data.subscribe(value => {
            this.shelf = value.shelf;
        });
    }

    ngOnInit(): void {
    }

    removeFromShelf(id: number) {
        this.confirm.confirm('Are you sure you want to remove this book from shelf?', () =>
            this.shelfService.removeBookFromShelf(id, this.shelf.id).subscribe(
                () => this.getShelf()
            )
        )
    }

    getShelf() {
        this.shelfService.getShelf(this.shelf.id).subscribe(
            (shelf) => this.shelf = shelf
        )
    }
}
