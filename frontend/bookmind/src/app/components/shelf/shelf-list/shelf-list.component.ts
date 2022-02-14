import { Component, OnInit } from '@angular/core';
import { Shelf } from "../../../domain/Shelf";
import { ActivatedRoute } from "@angular/router";
import { ShelfService } from "../../../service/shelf.service";

@Component({
    selector: 'app-shelf-list',
    templateUrl: './shelf-list.component.html',
    styleUrls: ['./shelf-list.component.scss']
})
export class ShelfListComponent implements OnInit {
    public shelves: Array<Shelf> = []

    constructor(private act: ActivatedRoute,
                private shelfService: ShelfService) {
        this.act.data.subscribe(value => {
            this.shelves = value.shelves;
        });
    }

    ngOnInit(): void {
    }

    getShelves() {
        this.shelfService.getMyShelves().subscribe(
            (shelves) => this.shelves = shelves
        )
    }

    delete(id: number) {

    }
}
