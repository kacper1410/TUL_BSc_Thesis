import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from "@angular/router";
import { ConfirmService } from "../../../service/confirm.service";
import { ShelfService } from "../../../service/shelf.service";
import { defaultBook } from "../../../domain/default/defaultBook";
import { Shelf } from "../../../domain/Shelf";
import { BookService } from "../../../service/book.service";

@Component({
    selector: 'app-shelf-add-book',
    templateUrl: './shelf-add-book.component.html',
    styleUrls: ['./shelf-add-book.component.scss']
})
export class ShelfAddBookComponent implements OnInit {
    public shelves: Array<Shelf> = [];
    public book = defaultBook();

    constructor(private act: ActivatedRoute,
                private confirm: ConfirmService,
                private shelfService: ShelfService,
                private bookService: BookService,
                private router: Router) {
        this.act.data.subscribe(value => {
            this.shelves = value.shelves;
            this.book = value.book;
        });
    }

    ngOnInit(): void {
    }


    getShelvesAndBook() {
        this.bookService.getBook(this.book.id).subscribe(
            (book) => this.book = book
        )
        this.shelfService.getMyShelves().subscribe(
            (shelves) => this.shelves = shelves
        )
    }

    viewShelf(id: number) {
        this.router.navigateByUrl(`/shelves/details/${id}`)
    }
}
