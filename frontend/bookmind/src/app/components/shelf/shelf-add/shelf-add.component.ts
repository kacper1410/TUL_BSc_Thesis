import { Component, OnInit } from '@angular/core';
import { defaultShelf } from "../../../domain/default/defaultShelf";
import { ShelfService } from "../../../service/shelf.service";
import { Router } from "@angular/router";

@Component({
    selector: 'app-shelf-add',
    templateUrl: './shelf-add.component.html',
    styleUrls: ['./shelf-add.component.scss']
})
export class ShelfAddComponent implements OnInit {
    shelf = defaultShelf();

    constructor(private shelfService: ShelfService,
                private router: Router) {
    }

    ngOnInit(): void {
    }

    addShelf() {
        this.shelfService.addShelf(this.shelf).subscribe(
            () => this.router.navigateByUrl('/shelves')
        )
    }
}
